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
import top.xxytime.blog.dao.TypeRepository;
import top.xxytime.blog.domain.Type;
import top.xxytime.blog.service.TypeService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小小荧
 * @Description: 博客类型接口实现类
 * @Date: 2020/1/17
 * @time: 20:47
 * @param:  * @param null:
 * @return:
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeRepository typeRepository;

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findTypeByName(name);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public List<Type> listType() {
        return  typeRepository.findAll();
    }

    @Override
    public void removeTypeById(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type updateType(Long id, Type type) {
//        先通过id获取到type对象
        Type t = typeRepository.getOne(id);
        if(t == null){
            throw  new NotFoundException("不存在这种类型");
        }
        BeanUtils.copyProperties(type, t);
//        更新对象
        Type newType = typeRepository.save(t);

        return newType;
    }

    /**
     * @Author: 小小荧
     * @Description: 查询出首页的标签的，查询条件倒序，固定size已对应博客数量排序
     * @Date: 2020/1/30
     * @time: 18:09
     * @param: [size]
     * @return: java.util.List<top.xxytime.blog.domain.Type>
     */
    @Override
    public List<Type> listTypeTop(Integer size) {
//        排序倒序
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable  = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }
}
