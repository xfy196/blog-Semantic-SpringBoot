package top.xxytime.blog.interceptor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.xxytime.blog.interceptor.LoginInterceptor;

/**
 * @Author: 小小荧
 * @Description: 将登录拦截器加入spring中的配置文件
 * @Date: 2020/1/17
 * @time: 15:56
 * @param:  * @param null:
 * @return:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        拦截那些地址，和那些地址不被拦截
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**")
                .excludePathPatterns("/admin").excludePathPatterns("/admin/login");
    }
}
