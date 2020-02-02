package top.xxytime.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xxytime.blog.NotFoundException;
import top.xxytime.blog.dao.TagRepository;
import top.xxytime.blog.domain.Tag;
import top.xxytime.blog.service.TagService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void removeTagById(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if(t == null){
            throw  new NotFoundException("不存在此种类型");
        }
        BeanUtils.copyProperties(tag, t);
        Tag newTag = tagRepository.save(t);
        return newTag;
    }

    /**
     * @Author: 小小荧
     * @Description: 查询出所以标签数据
     * @Date: 2020/1/23
     * @time: 18:00
     * @param: []
     * @return: java.util.List<top.xxytime.blog.domain.Tag>
     */
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String tagIds) {
        if(!"".equals(tagIds) && tagIds !=null){
            String[] ids = tagIds.split(",");
            List<Tag> list = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                try{
                    Tag one = tagRepository.getOne(Long.valueOf(ids[i]));
               /**
                * @Author: 小小荧
                * @Description: 数据库不存在的标签的时候我们需要保存新的标签
                * 如果前端传来的是一个非数值类型那么会出现类型转换异常，如果是数值类型，那么会出现查询的对象为null
                * 我们需要将标签的name重新设置然后保存
                * @Date: 2020/1/29
                * @time: 17:11
                * @param: [tagIds]
                * @return: java.util.List<top.xxytime.blog.domain.Tag>
                */
                    if(one!=null){
                        list.add(one);
                    }else {
                        one.setName(ids[i]);
                        Tag tag = saveTag(one);
                        list.add(tag);
                    }
                }catch (Exception e){
//                    出现异常时的操作（类型转换失败）
                    Tag newTag = new Tag();
                    newTag.setName(ids[i]);
                    Tag tag = saveTag(newTag);
                    list.add(tag);
                }
            }
            return list;
        }
        return  null;
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
//        降序排序
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }


}
