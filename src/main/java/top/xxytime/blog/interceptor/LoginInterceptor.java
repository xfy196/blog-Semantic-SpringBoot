package top.xxytime.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.xxytime.blog.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 小小荧
 * @Description: 登录拦截器
 * @Date: 2020/1/17
 * @time: 15:50
 * @param:  * @param null:
 * @return:
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * @Author: 小小荧
     * @Description: 执行前的预处理
     * @Date: 2020/1/17
     * @time: 15:50
     * @param: [request, response, handler]
     * @return: boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
//        如果user对象为空，说明没有登录，将地址重定向到登录页面
        if(user == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
