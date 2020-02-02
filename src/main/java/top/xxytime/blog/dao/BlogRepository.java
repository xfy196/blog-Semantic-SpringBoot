package top.xxytime.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import top.xxytime.blog.domain.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b where recommend=true")
    List<Blog> findTop(Pageable pageable);
    @Query("select b from Blog b where title like ?1 or content like ?1 or description like ?1")
    Page<Blog> findBlogsByQuery(String query, Pageable pageable);

    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by function('date_format', b.updateTime, '%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findBlogByYear(String year);

}
