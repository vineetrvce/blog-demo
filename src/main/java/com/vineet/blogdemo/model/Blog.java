package com.vineet.blogdemo.model;

public class Blog {

    private Integer blogID;
    private String blogHeader;
    private String blogText;

    public Blog() {
    }

    public Blog(int blogID, String blogHeader, String blogText) {
        this.blogID = blogID;
        this.blogHeader = blogHeader;
        this.blogText = blogText;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getBlogHeader() {
        return blogHeader;
    }

    public void setBlogHeader(String blogHeader) {
        this.blogHeader = blogHeader;
    }

    public String getBlogText() {
        return blogText;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }

}
