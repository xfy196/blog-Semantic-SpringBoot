package top.xxytime.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xxytime.blog.NotFoundException;
import top.xxytime.blog.domain.Blog;
import top.xxytime.blog.service.BlogService;
import top.xxytime.blog.service.TagService;
import top.xxytime.blog.service.TypeService;
import top.xxytime.blog.vo.BlogQuery;

/**
 * @Author: 小小荧
 * @Description: 
 * @Date: 2020/1/2
 * @time: 21:14
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    /**
     * @Author: 小小荧
     * @Description: 博客的首页
     * @Date: 2020/1/30
     * @time: 17:58
     * @param: []
     * @return: java.lang.String
     */
    @GetMapping("/")
    public String index (@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, Model model){
//        首页页面博客的列表数据
        model.addAttribute("page", blogService.listBlog(pageable));
//        首页页面列表数据
        model.addAttribute("types", typeService.listTypeTop(6));
//        首页的标签列表数据
        model.addAttribute("tags", tagService.listTagTop(10));
//        最新推荐，按照更新时间降序排序，最多显示8个数据，同时必须是推荐的博客
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }
    /**
     * @Author: 小小荧
     * @Description: 跳转博客详情页面的Controller方法
     * @Date: 2020/1/15
     * @time: 22:30
     * @param: []
     * @return: java.lang.String
     */
    @GetMapping(value = "/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
//        通过id拿到了博客，但是每一次点击浏览博客之后呢我们需要更新浏览次数
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog", blog);
        return "blog";
    }


    /**
     * @Author: 小小荧
     * @Description: 通过关键字query查询出对应博客结果集
     * @Date: 2020/1/30
     * @time: 22:02
     * @param: [pageable, model, query]
     * @return: java.lang.String
     */
    @PostMapping(value = "/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                 Pageable pageable, Model model, String query){
       model.addAttribute("page",  blogService.listBlogByQuery("%"+query+"%", pageable));
       model.addAttribute("query", query);
        return "search";
    }

    /**
     * @Author: 小小荧
     * @Description: 这个方法是用户界面footer下面文章最新推荐的方法
     * @Date: 2020/2/2
     * @time: 17:40
     * @param: []
     * @return: java.lang.String
     */
    @GetMapping(value = "footer/newBlogs")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
