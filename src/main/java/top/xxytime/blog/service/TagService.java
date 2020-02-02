package top.xxytime.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.xxytime.blog.domain.Tag;

import java.util.List;

public interface TagService {
    //    通过分类名称获取分类对象
    Tag getTagByName(String name);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag saveTag(Tag tag);

    void removeTagById(Long id);

    Tag updateTag(Long id, Tag tag);

//    查询出所有的标签的数据
    List<Tag> listTag();

    //通过对应的tagIds查询出对应的tag对象
    List<Tag> listTag(String tagIds);

//    排序查询，返回的数据用原来在首页展示标签数据
    List<Tag> listTagTop(Integer size);
}
