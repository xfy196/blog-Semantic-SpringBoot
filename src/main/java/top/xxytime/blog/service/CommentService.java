package top.xxytime.blog.service;

import top.xxytime.blog.domain.Comment;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @Author: 小小荧
 * @Description: 博客评论的Comment
 * @Date: 2020/2/1
 * @time: 13:35
 * @param:  * @param null:
 * @return:
 */
public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    void save(Comment comment);
}
