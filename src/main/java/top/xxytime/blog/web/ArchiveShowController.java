package top.xxytime.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.xxytime.blog.service.BlogService;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;
    @GetMapping(value = "archives")
    public String archives(Model model){

        model.addAttribute("archiveMap", blogService.findBlogGroupByYear());
//        博客总数
        model.addAttribute("countBlog", blogService.getCountBlog());
        return "archives";
    }
}
