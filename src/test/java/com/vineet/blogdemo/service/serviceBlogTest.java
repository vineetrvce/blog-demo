package com.vineet.blogdemo.service;

import com.vineet.blogdemo.dao.mySQLDaoBlog;
import com.vineet.blogdemo.model.Blog;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class serviceBlogTest {
    @Mock
    private mySQLDaoBlog oMySQLDaoBlog;

    @InjectMocks
    private serviceBlog oServiceBlog;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public serviceBlogTest() {
        oMySQLDaoBlog = new mySQLDaoBlog();
        oServiceBlog = new serviceBlog();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchBlogs_ExistingDatainDB_ShouldReturnBlogList() throws Exception {
        Blog _blog = new Blog(0, "Blog Header 1","Blog Text 1");

        List<Blog> _bloglist = new ArrayList<Blog>();
        _bloglist.add(_blog);

        BDDMockito.given(oMySQLDaoBlog.selectBlogs()).willReturn(_bloglist);

        List<Blog> _fetchBlogs = oServiceBlog.fetchBlogs();

        assert(_fetchBlogs.size() == 1);
        assert (_fetchBlogs.get(0).getBlogHeader().equals("Blog Header 1"));
        assert (_fetchBlogs.get(0).getBlogText().equals("Blog Text 1"));
    }

    @Test
    public void fetchBlogs_NoDatainDB_ShouldReturnEmptyBlogList() throws Exception {
        List<Blog> _bloglist = new ArrayList<Blog>();
        BDDMockito.given(oMySQLDaoBlog.selectBlogs()).willReturn(_bloglist);
        List<Blog> _fetchBlogs = oServiceBlog.fetchBlogs();

        assert(_fetchBlogs.size() == 0);
        assert (_fetchBlogs.isEmpty());
    }

    @Test
    public void fetchBlog_ExistingDatainDB_ShouldReturnBlog() throws Exception {
        Blog _blog = new Blog(1, "Blog Header 1","Blog Text 1");

        BDDMockito.given(oMySQLDaoBlog.selectBlog(1)).willReturn(_blog);
        Optional<Blog> _fetchBlog = oServiceBlog.fetchBlog(1);

        assert(_fetchBlog.isPresent() == true);
        assert (_fetchBlog.get().getBlogHeader().equals("Blog Header 1"));
        assert (_fetchBlog.get().getBlogText().equals("Blog Text 1"));
    }

    @Test
    public void updateBlog_ExistingDatainDB_ShouldReturnNonZero() throws Exception {

        Blog _blog = new Blog(1, "Blog Header 1","Blog Text 1");
        Blog _updatedblog = new Blog(1, "Blog Header 1 updated","Blog Text 1 updated");

        BDDMockito.given(oMySQLDaoBlog.selectBlog(1)).willReturn(_blog);
        BDDMockito.given(oMySQLDaoBlog.updateBlog(_updatedblog)).willReturn(1);

        assert(oServiceBlog.updateBlog(_updatedblog) != 0);
    }

    @Test
    public void updateBlog_NoDatainDB_ShouldReturnError() throws Exception {
        Blog _updatedblog = new Blog(1, "Blog Header 1 updated","Blog Text 1 updated");

        BDDMockito.given(oMySQLDaoBlog.selectBlog(1)).willReturn(null);

        exceptionRule.expect(NotFoundException.class);
        exceptionRule.expectMessage("Blog 1 not found");

        oServiceBlog.updateBlog(_updatedblog);
    }

    @Test
    public void removeBlog_ExistingDatainDB_ShouldReturnNonZero() throws Exception {
        Blog _blog = new Blog(1, "Blog Header 1","Blog Text 1");

        BDDMockito.given(oMySQLDaoBlog.selectBlog(1)).willReturn(_blog);
        BDDMockito.given(oMySQLDaoBlog.deleteBlog(1)).willReturn(1);

        assert(oServiceBlog.removeBlog(1) != 0);
    }

    @Test
    public void removeBlog_NoDatainDB_ShouldReturnError() throws Exception {

        BDDMockito.given(oMySQLDaoBlog.selectBlog(1)).willReturn(null);

        exceptionRule.expect(NotFoundException.class);
        exceptionRule.expectMessage("Blog 1 not found");

        oServiceBlog.removeBlog(1);
    }

    @Test
    public void insertBlog_NoDatainDB_ShouldReturnNonZero() throws Exception {

        Blog _blog = new Blog(1, "Blog Header 1","Blog Text 1");

        BDDMockito.given(oMySQLDaoBlog.insertBlog(_blog)).willReturn(1);

        assert(oServiceBlog.insertBlog(_blog) != 0);
    }
}