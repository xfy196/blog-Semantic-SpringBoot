package top.xxytime.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import top.xxytime.blog.dao.UserRepository;
import top.xxytime.blog.domain.User;
import top.xxytime.blog.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    public UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findUserByUsernameAndPassword(username,
                DigestUtils.md5DigestAsHex(password.getBytes()));
        return user;
    }

    @Override
    public User updateUser(String password, User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        return userRepository.save(user);
    }
}
