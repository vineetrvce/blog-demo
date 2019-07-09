package com.vineet.blogdemo.controller;

import com.vineet.blogdemo.model.Blog;
import com.vineet.blogdemo.service.serviceBlog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
        path = "/api/v1/blogs"
)
@Api(value = "restControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class restController {
    private serviceBlog oServiceBlog;

    public restController( ) {
        this.oServiceBlog = new serviceBlog();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation("Gets the Blog based on the Blog ID")
    public ResponseEntity<?> getBlog(@PathVariable("id") int id) throws Exception {
        // need to change this after POST implementation
        Optional<Blog> _blog = oServiceBlog.fetchBlog(id);
        if (_blog.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(_blog.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new statusMessage("Blog " + Integer.toString(id) + " not found!"));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation("Gets all the Blogs")
    public List<Blog> getBlogs() throws Exception {
        // need to change this after POST implementation
        return oServiceBlog.fetchBlogs();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation("Create a Blog")
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) throws Exception {
        int id = oServiceBlog.insertBlog(blog);
        if (id != 0) {
            return ResponseEntity.status(HttpStatus.OK).body(new statusMessage("Blog with id: " + Integer.toString(id) + " created"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new statusMessage("Blog creation failed"));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation("Update a Blog")
    public void updateBlog(@RequestBody Blog blog) throws Exception {
        oServiceBlog.updateBlog(blog);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}")
    @ResponseBody
    @ApiOperation("Delete a Blog")
    public void deleteBlog(@PathVariable("id") int id) throws Exception {
        oServiceBlog.removeBlog(id);
    }
}
