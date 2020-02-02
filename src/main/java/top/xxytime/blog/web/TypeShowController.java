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
import top.xxytime.blog.domain.Type;
import top.xxytime.blog.service.BlogService;
import top.xxytime.blog.service.TypeService;
import top.xxytime.blog.vo.BlogQuery;

import java.util.List;

/**
 * @Author: 小小荧
 * @Description: 显示分类桶显示按照分类查询出对应的博客
 * @Date: 2020/2/1
 * @time: 20:35
 * @param:  * @param null:
 * @return:
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;
    @GetMapping(value = "/types/{id}")
    public String types(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model, @PathVariable Long id){
//        当我们将查询top的size足够大那么就可以查询出全部的type数据
        List<Type> types = typeService.listTypeTop(10000);
//        拿到按照博客数量大小排序的type的id,然后通过这个id查询博客
        BlogQuery blogQuery = new BlogQuery();
        if(id == -1){
            id = types.get(0).getId();
        }
        blogQuery.setTypeId(id);
        Page<Blog> page = blogService.listBlog(pageable, blogQuery);
//        将拿到数据设置到model中
        model.addAttribute("types", types);
        model.addAttribute("page", page);
        model.addAttribute("typeActiveId", id);
        return "types";
    }
}
