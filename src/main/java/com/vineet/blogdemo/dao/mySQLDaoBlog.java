package com.vineet.blogdemo.dao;

import com.vineet.blogdemo.model.Blog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class mySQLDaoBlog implements daoBlog {

    private Connection connect = null;
    public Statement statement = null;
    private ResultSet resultSet = null;

    public Statement getStatement() {
        return statement;
    }

    @Override
    public List<Blog> selectBlogs() throws Exception {
        try {
            openMySQLConnection();

            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from blogdb.blogs");

            ArrayList<Blog> blogList = new ArrayList<>();
            while (resultSet.next()) {
                Blog _blog = new Blog();
                _blog.setBlogID(resultSet.getInt("id"));
                _blog.setBlogHeader(resultSet.getString("header"));
                _blog.setBlogText(resultSet.getString("text"));
                blogList.add(_blog);
            }

            return blogList;

        } catch (Exception e) {
            throw e;
        } finally {
            closeMySQLConnection();
        }
    }

    @Override
    public Blog selectBlog(int blogID) throws Exception {
        try {
            Blog _blog = new Blog();

            openMySQLConnection();            // Result set get the result of the SQL query

            resultSet = statement
                    .executeQuery("select * from blogdb.blogs where id = " + Integer.toString(blogID) );

            // Parameters start with 1
            if (resultSet.next() == true){
                _blog.setBlogID(resultSet.getInt("id"));
                _blog.setBlogHeader(resultSet.getString("header"));
                _blog.setBlogText(resultSet.getString("text"));

                return _blog;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            closeMySQLConnection();
        }
    }

    @Override
    public int insertBlog(Blog blog) throws Exception{

        int id;

        try {
            openMySQLConnection();

            connect.setAutoCommit(false);
            statement.executeUpdate("insert into blogdb" +
                        ".blogs (header, text)" +
                        "values('" + blog.getBlogHeader() + "','" + blog.getBlogText()  + "')");
                resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() as id");

                resultSet.next();
                id  = resultSet.getInt("id");
                connect.commit();

            return id;

        } catch (Exception e) {
            throw e;
        } finally {
            closeMySQLConnection();
        }
    }

    @Override
    public int updateBlog(Blog blog)  throws Exception{
        try {
            openMySQLConnection();

            // Result set get the result of the SQL query
            return statement
                    .executeUpdate("update blogdb.blogs set " + "header = '" + blog.getBlogHeader() +
                            "', text = '" + blog.getBlogText() + "' where id = " + Integer.toString(blog.getBlogID()));

        } catch (Exception e) {
            throw e;
        } finally {
            closeMySQLConnection();
        }
    }

    @Override
    public int deleteBlog(int blogID)  throws Exception {

        try {
            openMySQLConnection();

            // Result set get the result of the SQL query
            return statement
                    .executeUpdate("delete from blogdb.blogs where id = " + Integer.toString(blogID) );

        } catch (Exception e) {
            throw e;
        } finally {
            closeMySQLConnection();
        }
    }

    private void openMySQLConnection() throws SQLException, ClassNotFoundException {

        try {
            // Load the MySQL driver.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/blogdb?"
                            + "user=root&password=password&serverTimezone=UTC");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();

        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        }
    }

    // close the resultSet
    private void closeMySQLConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
