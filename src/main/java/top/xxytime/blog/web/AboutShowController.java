package top.xxytime.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {

    @GetMapping(value = "about")
    public String about(){
        return "about";
    }
}
