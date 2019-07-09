package com.vineet.blogdemo.dao;

import com.vineet.blogdemo.model.Blog;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.sql.*;
import java.util.List;

public class mySQLDaoBlogTest {

    @Mock
    private Statement statement = null;
    private Connection connect = null;
    private ResultSet resultSet = null;

    @InjectMocks
    private mySQLDaoBlog oMySQLDaoBlog;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public mySQLDaoBlogTest() {
        this.oMySQLDaoBlog = new mySQLDaoBlog();
    }

    @Before
    public void setUp() throws Exception {

        deleteDB();
        // have one entry in database
        Blog _blog = new Blog(0, "Blog Header 1", "Blog Text 1");
        oMySQLDaoBlog.insertBlog(_blog);
    }

    @Test
    public void selectAllBlogs_ExistingDatainDatabase_ShouldReturnBlogList() {
        try {
            List<Blog> _bloglist = oMySQLDaoBlog.selectBlogs();
            assert (_bloglist.size() != 0);
            assert (_bloglist.get(0).getBlogHeader().equals("Blog Header 1"));
            assert (_bloglist.get(0).getBlogText().equals("Blog Text 1"));
        }
        catch(Exception e){
        }
    }

    @Test
    public void selectBlog_ExistingDatainDatabase_ShouldReturnaBlog() {
        try {
            List<Blog> _bloglist = oMySQLDaoBlog.selectBlogs();
            Blog _blog = oMySQLDaoBlog.selectBlog(_bloglist.get(0).getBlogID());
            assert (_blog.getBlogHeader().equals("Blog Header 1"));
            assert (_blog.getBlogText().equals("Blog Text 1"));
        }
        catch(Exception e){
        }
    }

    @Test
    public void insertBlog_ExistingDatainDatabase_ShouldInsertaBlog() {
        Blog _blog = new Blog(0, "Blog Header 2", "Blog Text 2");
        try {
            int id = oMySQLDaoBlog.insertBlog(_blog);
            assert (id != 0);
            _blog = oMySQLDaoBlog.selectBlog(id);
            assert (_blog.getBlogHeader().equals("Blog Header 2"));
            assert (_blog.getBlogText().equals("Blog Text 2"));
        } catch (Exception e) {
        }
    }

    @Test
     public void updateBlog_ExistingDatainDatabase_ShouldUpdateaBlog() {

        try {
            List<Blog> _bloglist = oMySQLDaoBlog.selectBlogs();
            Blog _blog = new Blog(_bloglist.get(_bloglist.size() - 1).getBlogID() , "Blog New Header 2", "Blog New Text 2");
            int id = oMySQLDaoBlog.updateBlog(_blog);
            assert (id != 0);
            _blog = oMySQLDaoBlog.selectBlog(_bloglist.get(_bloglist.size() - 1).getBlogID());
            assert (_blog.getBlogHeader().equals("Blog New Header 2"));
            assert (_blog.getBlogText().equals("Blog New Text 2"));
        } catch (Exception e) {
        }
    }

    @Test
    public void updateBlog_NoDatainDatabase_ShouldReturnError() {
        try {
            List<Blog> _bloglist = oMySQLDaoBlog.selectBlogs();
            int id = _bloglist.get(_bloglist.size() - 1).getBlogID();
            assert (id != 0);
            id = id + 1; // unknown ID in database
            Blog _blog = new Blog(id , "Blog New Header id + 1", "Blog New Text id + 1");
            id = oMySQLDaoBlog.updateBlog(_blog);
            assert (id == 0);
        } catch (Exception e) {

        }
    }

    @Test
    public void deleteBlogExistingDatainDatabase_ShouldDeleteaBlog() {
        try {
            Blog _blog;
            List<Blog> _bloglist = oMySQLDaoBlog.selectBlogs();
            int id = oMySQLDaoBlog.deleteBlog(_bloglist.get(_bloglist.size() - 1).getBlogID());
            assert (id != 0);
            _blog = oMySQLDaoBlog.selectBlog(_bloglist.get(_bloglist.size() - 1).getBlogID());
            assert (_blog == null);
        } catch (Exception e) {
        }
    }

    @Test
    public void deleteBlog_NoDatainDatabase_ShouldReturnError() {

        try {
            List<Blog> _bloglist = oMySQLDaoBlog.selectBlogs();
            int id = _bloglist.get(_bloglist.size() - 1).getBlogID();
            assert (id != 0);
            id = id + 1; // unknown ID in database
            id = oMySQLDaoBlog.deleteBlog(id);
            assert (id == 0);
        } catch (Exception e) {
        }
    }

    // Support Functions for test (move later)
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

    // delete all data from DB
    private void deleteDB() throws SQLException, ClassNotFoundException {
        try {
            openMySQLConnection();

            // Result set get the result of the SQL query
            statement.executeUpdate("delete from blogdb.blogs" );

        } catch (Exception e) {
            throw e;
        } finally {
            closeMySQLConnection();
        }
    }
}