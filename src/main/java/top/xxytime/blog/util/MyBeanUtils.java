package top.xxytime.blog.util;

import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;

/**
 * @Author: 小小荧
 * @Description: 处理实体类字段的类
 * @Date: 2020/1/30
 * @time: 14:10
 * @param:  * @param null:
 * @return:
 */
public class MyBeanUtils {

    /**
     * @Author: 小小荧
     * @Description: 获取对象中那些元素的值未null的字段
     * @Date: 2020/1/30
     * @time: 14:15
     * @param: [source]
     * @return: java.lang.String[]
     */
    public static String[] getNullPropertyNames(Object source){
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        ArrayList<Object> arrayList = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String pdName = pd.getName();
            if(beanWrapper.getPropertyValue(pdName) == null){
                arrayList.add(pdName);
            }
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }
}
