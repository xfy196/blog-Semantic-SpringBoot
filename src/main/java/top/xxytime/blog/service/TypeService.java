package top.xxytime.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.xxytime.blog.domain.Type;

import java.util.List;

/**
 * @Author: 小小荧
 * @Description: 博客业务层接口
 * @Date: 2020/1/17
 * @time: 20:46
 * @param:  * @param null:
 * @return:
 */
public interface TypeService {
//    通过分类名称获取分类对象
    Type getTypeByName(String name);

    Type getType(Long id);
    Page<Type> listType(Pageable pageable);
    Type saveType(Type type);
//    查询出所有的分类数据
    List<Type> listType();
    void removeTypeById(Long id);
    Type updateType(Long id, Type type);

    /**
     * @Author: 小小荧
     * @Description: size代表查询的数量大小
     * @Date: 2020/1/30
     * @time: 18:06
     * @param: [size]
     * @return: java.util.List<top.xxytime.blog.domain.Type>
     */
    List<Type> listTypeTop(Integer size);
}
