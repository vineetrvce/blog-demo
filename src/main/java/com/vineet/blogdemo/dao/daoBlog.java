package com.vineet.blogdemo.dao;

import com.vineet.blogdemo.model.Blog;

import java.util.List;

public interface daoBlog {

    List<Blog> selectBlogs() throws Exception;

    Blog selectBlog(int blogID) throws Exception;

    int insertBlog(Blog blog)throws Exception;

    int updateBlog(Blog blog)throws Exception;

    int deleteBlog(int blogID)throws Exception;

}
