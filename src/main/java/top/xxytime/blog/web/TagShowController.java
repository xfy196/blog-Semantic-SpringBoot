package top.xxytime.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.xxytime.blog.domain.Blog;
import top.xxytime.blog.domain.Tag;
import top.xxytime.blog.service.BlogService;
import top.xxytime.blog.service.TagService;
import top.xxytime.blog.vo.BlogQuery;

import java.util.List;

/**
 * @Author: 小小荧
 * @Description: Tag标签显示
 * @Date: 2020/2/1
 * @time: 20:59
 * @param:  * @param null:
 * @return:
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping(value = "tags/{id}")
    public String tags(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model, @PathVariable Long id){

        List<Tag> tags = tagService.listTagTop(10000);
        if(id == -1){
            id = tags.get(0).getId();
        }
        Page<Blog> page = blogService.listBlog(id, pageable);
        model.addAttribute("tags", tags);
        model.addAttribute("page", page);
        model.addAttribute("tagActiveId", id);
        return "tags";
    }
}
