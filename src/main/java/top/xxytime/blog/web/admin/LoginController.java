package top.xxytime.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.xxytime.blog.domain.User;
import top.xxytime.blog.service.BlogService;
import top.xxytime.blog.service.UserService;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    @Autowired
    public UserService userService;

    @Autowired
    private BlogService blogService;
    @GetMapping
    public String loginPage(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            return "admin/index";
        }
        return "admin/login";
    }
    @PostMapping(value = "login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
//        判断次用户是否存在，存在将信息设置到session中，把密码清空
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else {
//            将之放入栈中可以让thymeleaf可以去到，而不是放在地址栏中
            attributes.addFlashAttribute("message", "用户不存在，请重新登录");
            return "redirect:/admin";
        }
    }
    /**
     * @Author: 小小荧
     * @Description: 注销方法
     * @Date: 2020/1/17
     * @time: 13:45
     * @param: [session]
     * @return: java.lang.String
     */
    @GetMapping(value = "logout")
    public String logout(HttpSession session){
//        将用户信息从session中删除
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    /**
     * @Author: 小小荧
     * @Description: 这个方法代表的是管理页面的footer下面的最新博客推荐
     * @Date: 2020/2/2
     * @time: 17:42
     * @param: [model]
     * @return: java.lang.String
     */
    @GetMapping(value = "footer/newBlogs")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "admin/_fragments :: newBlogList";
    }


//    修改管理员密码
    @PostMapping(value = "updatePassword")
    public String updatePassword(Model model, String username, String oldPassword, String newPassword,
                                 HttpSession session, RedirectAttributes redirectAttributes){
//        相同过username和oldPassword找到管理员用户
        User user = userService.checkUser(username, oldPassword);
//        找到这个用户说明旧密码匹配正确，进行修改密码操作
        if(user != null){
            User updateUser = userService.updateUser(newPassword, user);
//            然后清空session重新跳转登录页面
            session.removeAttribute("user");
        }else{
            redirectAttributes.addFlashAttribute("message", "旧密码错误，请重新输入");
        }
        return "redirect:/admin";
    }
}
