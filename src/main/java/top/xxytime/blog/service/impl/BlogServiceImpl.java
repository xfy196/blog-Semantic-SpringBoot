package top.xxytime.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xxytime.blog.NotFoundException;
import top.xxytime.blog.dao.BlogRepository;
import top.xxytime.blog.domain.Blog;
import top.xxytime.blog.domain.Type;
import top.xxytime.blog.service.BlogService;
import top.xxytime.blog.util.MarkdownUtils;
import top.xxytime.blog.util.MyBeanUtils;
import top.xxytime.blog.vo.BlogQuery;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements
        BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
//        使用JPA为我们提供的一个接口匿名实现条件的封装查询
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
//                判断标题的查询条件
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
//                判断分类的查询条件
                if (blog.getTypeId() != null) {

                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
//                判断是否是推荐
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }

//               执行查询语
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {

        if (blog.getId() == null) {
            //        新增一个博客对象我们需要手动设置新增时间
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            return blogRepository.save(blog);
        }
        //       先通过id查询到一个对象
        Blog b = blogRepository.getOne(blog.getId());
        if (b == null) {
            throw new NotFoundException("不存在这种类型");
        }
//        这里我们需要把blog对象里为null的字段去除
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
//        如果这个对象出现那么我们就设置跟新时间
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void removeBlogById(Long id) {
        blogRepository.deleteById(id);
    }

    /**
     * @Author: 小小荧
     * @Description: 通过年份博客进行分类
     * @Date: 2020/2/2
     * @time: 15:06
     * @param: []
     * @return: java.util.Map<java.lang.String,java.util.List<top.xxytime.blog.domain.Blog>>
     */
    @Override
    public Map<String, List<Blog>> findBlogGroupByYear() {
//        获取年份降序排列
        List<String> years = blogRepository.findGroupYear();
//        创建存放map数据的对象
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findBlogByYear(year));
        }

        return map;
    }

    /**
     * @Author: 小小荧
     * @Description: 按照已推荐的博客降序排序查询数据
     * @Date: 2020/1/30
     * @time: 18:58
     * @param: [size]
     * @return: java.util.List<top.xxytime.blog.domain.Blog>
     */
    @Transactional
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    /**
     * @Author: 小小荧
     * @Description: 博客搜索功能，通过关键字查询
     * @Date: 2020/1/30
     * @time: 22:06
     * @param: [query, pageable]
     * @return: org.springframework.data.domain.Page<top.xxytime.blog.domain.Blog>
     */
    @Transactional
    @Override
    public Page<Blog> listBlogByQuery(String query, Pageable pageable) {
        return blogRepository.findBlogsByQuery(query,pageable);
    }

    /**
     * @Author: 小小荧
     * @Description: 用户查看博客需要将Markdown的文本转换为HTML
     * @Date: 2020/1/31
     * @time: 16:00
     * @param: [blog]
     * @return: top.xxytime.blog.domain.Blog
     */
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
//        通过id拿到一个blog
        Blog blog = blogRepository.getOne(id);

//        使用工具类将Markdown文本转为HTML文本
        String content = blog.getContent();
        Blog newBlog = new Blog();
        BeanUtils.copyProperties(blog, newBlog);
        newBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blog.setViews(blog.getViews()+1);
        return newBlog;
    }

    @Override
    public Long getCountBlog() {
        return blogRepository.count();
    }

}
