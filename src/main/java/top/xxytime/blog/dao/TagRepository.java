package top.xxytime.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.xxytime.blog.domain.Tag;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagByName(String name);
//    排序条件查询，展示在首页的标签列表
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
