package top.xxytime.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.xxytime.blog.domain.Type;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findTypeByName(String name);
//    用户端首页查询已对应博客数据排序的数据
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
