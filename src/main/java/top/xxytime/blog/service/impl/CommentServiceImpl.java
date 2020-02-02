package top.xxytime.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xxytime.blog.dao.CommentRepository;
import top.xxytime.blog.domain.Comment;
import top.xxytime.blog.service.CommentService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 小小荧
 * @Description: CommentService的功能实现类
 * @Date: 2020/2/1
 * @time: 13:37
 * @param:  * @param null:
 * @return:
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Value("${comment.avatar}")
    private String avatar;
    /**
     * @Author: 小小荧
     * @Description: 通过blog的id查找这个博客的评论信息
     * @Date: 2020/2/1
     * @time: 13:38
     * @param: [blogId]
     * @return: java.util.List<top.xxytime.blog.domain.Comment>
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
//       查询根级评论，这里的根级评论在数据库中就是代表parentCommentId为null
        List<Comment> list = commentRepository.findCommentByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(list);
    }

    /**
     * @Author: 小小荧
     * @Description: 评论的保存方法
     * @Date: 2020/2/1
     * @time: 13:39
     * @param: [comment]
     * @return: void
     */
    @Override
    public void save(Comment comment) {
//        首先需要判断这个评论的parentCommentId是不是-1
        Long parentCommnetId = comment.getParentComment().getId();
        if(parentCommnetId != -1){
//            说明他有是回复内容，为这个评论内容设置父级对象
            comment.setParentComment(commentRepository.getOne(parentCommnetId));
        }else {
//            否则说明这个评论没有回复用户而是根级回复将parentComment设置为null
            comment.setParentComment(null);
        }
//       设置默认头像
        comment.setAvatar(avatar);
//        设置创建时间
        comment.setCreateTime(new Date());
//        保存对象
        commentRepository.save(comment);
    }


    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
