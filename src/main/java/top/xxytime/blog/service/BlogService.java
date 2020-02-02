package top.xxytime.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.xxytime.blog.domain.Blog;
import top.xxytime.blog.vo.BlogQuery;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
//    管理员
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
//    用户端
    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Blog saveBlog(Blog blog);

    void removeBlogById(Long id);

//    通过年份将博客数据分类
    Map<String, List<Blog>> findBlogGroupByYear();

//    按照已推荐的博客降序查询
    List<Blog> listRecommendBlogTop(Integer size);

    //search功能通过对应的query的key查询对应的结果
    Page<Blog> listBlogByQuery(String query, Pageable pageable);

//    用户页面查看博客
    Blog getAndConvert(Long id);

//    博客总数
    Long getCountBlog();
}
