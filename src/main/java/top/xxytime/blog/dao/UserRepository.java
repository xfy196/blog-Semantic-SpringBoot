package top.xxytime.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.xxytime.blog.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsernameAndPassword(String username, String password);

}
