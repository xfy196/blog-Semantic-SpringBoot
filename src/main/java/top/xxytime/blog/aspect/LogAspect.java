package top.xxytime.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: 小小荧
 * @Description: 日志的拦截器使用Aop拦截，处理系统的日志
 * @Date: 2020/1/15
 * @time: 15:08
 * @param:  * @param null:
 * @return:
 */
@Aspect
@Component
public class LogAspect {
//    定义日志类
    Logger logger = LoggerFactory.getLogger(this.getClass());
//    具体拦截那些包下面的类
    @Pointcut("execution(* top.xxytime.blog.web.*.*(..))")
    public  void log(){}
    /**
     * @Author: 小小荧
     * @Description: 执行方法之前的操作
     * @Date: 2020/1/15
     * @time: 15:45
     * @param: []
     * @return: void
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
//        获取请求的参数对象，从这个对象可以获取请求的一些相关的信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        获取请求对象
        HttpServletRequest request = attributes.getRequest();
//        获取访问的地址
        String url = request.getRequestURL().toString();
//       获取ip地址
        String ip = request.getRemoteAddr();
//        方法名的地址
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
//        参数
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("request {}", requestLog);
    }
    /**
     * @Author: 小小荧
     * @Description: 执行方法之后的操作
     * @Date: 2020/1/15
     * @time: 15:46      
     * @param: []
     * @return: void 
     */
    @After("log()")
    public void doAfter(){

    }

    @AfterReturning(pointcut = "log()",returning = "result")
    public void doAfterReturn(Object result){
        logger.info("result: {}", result);
    }

    /**
     * @Author: 小小荧
     * @Description: 将日志收集的ip,地址，调用的方法路径名，参数分装对象
     * @Date: 2020/1/15
     * @time: 15:48
     * @param:  * @param null:
     * @return:
     */
    class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
