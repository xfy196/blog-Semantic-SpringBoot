package top.xxytime.blog.service;

import org.springframework.stereotype.Service;
import top.xxytime.blog.domain.User;

/**
 * @Author: 小小荧
 * @Description: 用户的业务层
 * @Date: 2020/1/16
 * @time: 15:44
 * @param:  * @param null:
 * @return:
 */
public interface UserService {
    User checkUser(String username, String password);

    User updateUser(String password, User user);
}
