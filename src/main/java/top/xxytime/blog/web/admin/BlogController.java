package top.xxytime.blog.web.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.xxytime.blog.domain.Blog;
import top.xxytime.blog.domain.User;
import top.xxytime.blog.service.BlogService;
import top.xxytime.blog.service.TagService;
import top.xxytime.blog.service.TypeService;
import top.xxytime.blog.vo.BlogQuery;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author: 小小荧
 * @Description: 博客列表的Controller层
 * @Date: 2020/1/17
 * @time: 16:00
 * @param:  * @param null:
 * @return:
 */
@Controller
@RequestMapping(value = "admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    /**
     * @Author: 小小荧
     * @Description: 默认显示的博客列表不使用任何的查询条件
     * @Date: 2020/1/19
     * @time: 18:16
     * @param: []
     * @return: java.lang.String
     */
    @GetMapping(value = "blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, BlogQuery blog, Model model){
//       需要分类的数据加载到前端去
        model.addAttribute("types", typeService.listType());
        Page page = blogService.listBlog(pageable, blog);
        model.addAttribute("page", page);
        return "admin/blogs";
    }
    /**
     * @Author: 小小荧
     * @Description: 使用查询条件调用的方法
     * @Date: 2020/1/22
     * @time: 17:42
     * @param: [pageable, blog, model]
     * @return: java.lang.String
     */
    @PostMapping(value = "blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, BlogQuery blog, Model model){
        Page page = blogService.listBlog(pageable, blog);
        model.addAttribute("page", page);
        return "admin/blogs :: blogList";
    }
    /**
     * @Author: 小小荧
     * @Description: 跳转新增的页面
     * @Date: 2020/1/19
     * @time: 18:15
     * @param: []
     * @return: java.lang.String
     */
    @GetMapping(value = "blog/input")
    public  String input(Model model){
//        初始化一个blog对象
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return "admin/blogs-input";
    }

    /**
     * @Author: 小小荧
     * @Description: 跳转修改页面
     * @Date: 2020/1/19
     * @time: 20:13
     * @param: [id]
     * @return: java.lang.String
     */
    @GetMapping(value = "blog/input/{id}")
    public String input(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
//        初始化Blog对象里tagIds用于前端页面显示
        blog.initTagIds();
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    /**
     * @Author: 小小荧
     * @Description: 删除blog文章的方法
     * @Date: 2020/1/19
     * @time: 20:16
     * @param: [id]
     * @return: java.lang.String
     */
    @GetMapping(value = "blog/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        blogService.removeBlogById(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return  "redirect:/admin/blogs";
    }


    /**
     * @Author: 小小荧
     * @Description: blog文章的保存方法
     * @Date: 2020/1/19
     * @time: 20:18
     * @param: [blog]
     * @return: java.lang.String
     */
    @PostMapping(value = "blog/save")
    public String save(@Valid Blog blog, BindingResult result, RedirectAttributes redirectAttributes, HttpSession session){
        if(result.hasErrors()){
            return "admin/blogs-input";
        }
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(blog.getType());
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = null;
        if(blog.getId()!=null){
            b= blogService.saveBlog(blog);
            if(b != null){
                redirectAttributes.addFlashAttribute("message", "修改成功");
            }else{
                redirectAttributes.addFlashAttribute("message", "修改失败");
            }
        }else {
            b= blogService.saveBlog(blog);
            if(b!=null){
                redirectAttributes.addFlashAttribute("message", "新增成功");
            }else {
                redirectAttributes.addFlashAttribute("message", "新增失败");
            }
        }

        return  "redirect:/admin/blogs";
    }
}
