package top.xxytime.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
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
import top.xxytime.blog.domain.Type;
import top.xxytime.blog.service.TypeService;

import javax.validation.Valid;

/**
 * @Author: 小小荧
 * @Description: 博客分类的Controller层
 * @Date: 2020/1/17
 * @time: 21:14
 * @param:  * @param null:
 * @return:
 */
@Controller
@RequestMapping(value = "/admin")
public class TypeCotroller {

    @Autowired
    private TypeService typeService;

    @GetMapping(value = "types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        Page page = typeService.listType(pageable);
        model.addAttribute("page", page);
        return "admin/types";
    }

    /*
     * @Author: 小小荧
     * @Description: 跳转新增页面
     * @Date: 2020/1/17
     * @time: 22:24
     * @param:  * @param null:
     * @return:
     */
    @GetMapping(value = "type/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * @Author: 小小荧
     * @Description: 跳转修改页面
     * @Date: 2020/1/19
     * @time: 12:44
     * @param: [id]
     * @return: java.lang.String
     */
    @GetMapping(value = "type/input/{id}")
    public String input(@PathVariable Long id, Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    /**
     * @Author: 小小荧
     * @Description: type的删除方法
     * @Date: 2020/1/21
     * @time: 14:00
     * @param: [id]
     * @return: java.lang.String
     */
    @GetMapping(value = "type/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
            typeService.removeTypeById(id);
            redirectAttributes.addFlashAttribute("message", "删除成功");
            return "redirect:/admin/types";
    }

    /**
     * @Author: 小小荧
     * @Description: 保存一个新得分类的方法
     * @Date: 2020/1/19
     * @time: 12:49
     * @param: [type]
     * @return: java.lang.String
     */
    @PostMapping(value = "type/save")
    public String save(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
//        分类名称重复验证
        Type newType = typeService.getTypeByName(type.getName());
        if(newType != null){
//            如果不等于null说明分类名称重复我们需要返回页面
            result.rejectValue("name", "nameError", "分类名称不能重复");
        }
//        判断BindingResult是否存在错误，有，反回结果页面
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type resultType = null;
//        如果type的id为null代表的是新增一个数据
        if(type.getId() == null){
            resultType = typeService.saveType(type);
            if(resultType == null){
                redirectAttributes.addFlashAttribute("message", "新增失败");
            }else {
                redirectAttributes.addFlashAttribute("message", "新增成功");
            }
        }else {
//            否则代表的是修改一个数据
            resultType = typeService.updateType(type.getId(), type);
            if(resultType ==  null){
                redirectAttributes.addFlashAttribute("message", "修改失败");
            }else {
                redirectAttributes.addFlashAttribute("message", "修改成功");
            }
        }
        return "redirect:/admin/types";
    }
}
