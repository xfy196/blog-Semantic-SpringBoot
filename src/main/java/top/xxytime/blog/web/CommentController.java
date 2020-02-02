package top.xxytime.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.xxytime.blog.domain.Comment;
import top.xxytime.blog.domain.User;
import top.xxytime.blog.service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: 小小荧
 * @Description: 评论操作的控制器
 * @Date: 2020/1/31
 * @time: 21:31
 * @param:  * @param null:
 * @return:
 */
@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;
    /**
     * @Author: 小小荧
     * @Description: 加载评论信息的方法
     * @Date: 2020/2/1
     * @time: 13:32      
     * @param: [blogId, model]
     * @return: java.lang.String 
     */
    @GetMapping(value = "/comments/{blogId}")
    public String commnets(@PathVariable Long blogId, Model model){
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    @PostMapping(value = "/comments/save")
    public String save(Comment comment, HttpSession session){
//        判断是否登录成功
        User user = (User) session.getAttribute("user");
//        管理员已登录的情况
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }
        commentService.save(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }
}
