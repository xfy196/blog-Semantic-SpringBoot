package top.xxytime.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.xxytime.blog.domain.Tag;
import top.xxytime.blog.service.TagService;

import javax.validation.Valid;

/**
 * @Author: 小小荧
 * @Description: 标签控制层
 * @Date: 2020/1/21
 * @time: 14:38
 * @param:  * @param null:
 * @return:
 */
@Controller
@RequestMapping(value = "admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * @Author: 小小荧
     * @Description: 返回列表数据的方法
     * @Date: 2020/1/21
     * @time: 14:39
     * @param: [pageable, model]
     * @return: java.lang.String
     */
    @GetMapping(value = "tags")
    public  String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){

        Page page = tagService.listTag(pageable);
        model.addAttribute("page", page);
        return "admin/tags";
    }

    /**
     * @Author: 小小荧
     * @Description: 跳转保存页面
     * @Date: 2020/1/21
     * @time: 14:39
     * @param: []
     * @return: java.lang.String
     */
    @GetMapping(value = "tag/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * @Author: 小小荧
     * @Description: 通过tag的id拿到tag的对象将对象数据返回页面进行修改
     * @Date: 2020/1/21
     * @time: 14:43
     * @param: [id, model]
     * @return: java.lang.String
     */
    @GetMapping(value = "tag/input/{id}")
    public String input(@PathVariable Long id, Model model){
//        通过id先拿到对应的tag对象
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    /**
     * @Author: 小小荧
     * @Description: 修改方法
     * @Date: 2020/1/21
     * @time: 16:39
     * @param: [tag, result, redirectAttributes]
     * @return: java.lang.String
     */
    @PostMapping(value = "tag/save")
    public String save(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
//        判断这个标签是否已存在
        Tag newTag = tagService.getTagByName(tag.getName());
        if(newTag != null){
//            这个标签已存在，我们需要将提示信息返回
            result.rejectValue("name", "nameError", "标签名称不能重复");
        }
//        如果这个绑定的BindingResul的对象有错误，那么将错误信息放回页面
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag resultTag = null;
//        判断这个对象的id是否存在，不存在就是新增操作，否则就是修改操作
        if(tag.getId() == null){
//            新增操作
            resultTag = tagService.saveTag(tag);
//            判断是否新增成功，然后设置对应的用户提示信息
            if(resultTag == null){
                redirectAttributes.addFlashAttribute("message", "新增失败");
            }else {
                redirectAttributes.addFlashAttribute("message", "新增成功");
            }
        }else {
//           判断是否修改成功，返回对应的用户提示信息
            resultTag =  tagService.updateTag(tag.getId(), tag);
            if(resultTag == null){
                redirectAttributes.addFlashAttribute("message", "修改失败");
            }else{
                redirectAttributes.addFlashAttribute("message", "修改成功");
            }
        }
        return  "redirect:/admin/tags";
    }

    /**
     * @Author: 小小荧
     * @Description: 删除tag的方法
     * @Date: 2020/1/21
     * @time: 16:55
     * @param: [id]
     * @return: java.lang.String
     */
    @GetMapping(value = "tag/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        tagService.removeTagById(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
