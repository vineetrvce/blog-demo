package com.vineet.blogdemo.service;

import com.vineet.blogdemo.dao.mySQLDaoBlog;
import com.vineet.blogdemo.model.Blog;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

// Implements the business logic
public class serviceBlog {

    private mySQLDaoBlog oMySQLDaoBlog;

    public serviceBlog() {
        oMySQLDaoBlog = new mySQLDaoBlog();
    }

    public List<Blog> fetchBlogs() throws Exception{
        return oMySQLDaoBlog.selectBlogs();
    }

    public Optional<Blog> fetchBlog(int blogID) throws Exception{
        return Optional.ofNullable(oMySQLDaoBlog.selectBlog(blogID));
    }

    public int updateBlog(Blog blog) throws Exception {
        Optional<Blog> optionalBlog = fetchBlog(blog.getBlogID());
        if (optionalBlog.isPresent()) {
            return oMySQLDaoBlog.updateBlog(blog);
        }
        throw new NotFoundException("Blog " + blog.getBlogID() + " not found");
    }

    public int removeBlog(int blogID) throws Exception{
        Optional<Blog> optionalBlog = fetchBlog(blogID);
        if (optionalBlog.isPresent()) {
            return oMySQLDaoBlog.deleteBlog(blogID);
        }
        throw new NotFoundException("Blog " + blogID + " not found");
    }

    public int insertBlog(Blog blog) throws Exception{
        return oMySQLDaoBlog.insertBlog(blog);
    }
}
