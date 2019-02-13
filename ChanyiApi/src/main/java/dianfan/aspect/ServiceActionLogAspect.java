/**  
* @Title: SystemLogAspect.java
* @Package dianfan.aspect
* @Description: TODO
* @author Administrator
* @date 2018年5月19日 下午1:41:31
* @version V1.0  
*/
package dianfan.aspect;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dianfan.service.impl.LogOpService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName ServiceActionLogAspect
 * @Description 服务层详细动作日志记录
 * @author cjy
 * @date 2018年6月27日 下午1:35:07
 */
@Component
@Aspect
public class ServiceActionLogAspect {
	/**
	 * 注入token类
	 */
	@Autowired
	private RedisTokenService manager;
	/**
	 * 注入写日志类
	 */
	@Autowired
	private LogOpService logInfoService;

	// Service层切点
	@Pointcut("execution(* dianfan.service.*.impl.*.update* (..)) || execution(* dianfan.service.*.impl.*.insert* (..)) || execution(* dianfan.service.*.impl.*.delete* (..))")
	public void serviceAspect() {}

	//前置通知：在目标方法开始之前执行  
    @Before("serviceAspect()")  
    public void beforeMethod(JoinPoint joinPoint){  
    	System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" +        joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }  
      
    //后置通知：在目标方法之后(无论是否发生异常)，执行的通知，  
    //在后置通知中还不能访问目标方法执行的结果。执行结果须在返回通知中访问。  
    @After("serviceAspect()")  
    public void afterMethod(JoinPoint joinPoint){  
        String methodName = joinPoint.getSignature().getName();  
        System.out.println("后置通知："+ methodName+" ends");  
    }
    
    //返回通知：在目标方法正常结束执行后的通知  
    //返回通知是可以访问到目标方法的返回值的  
    @AfterReturning(value="serviceAspect()", returning = "result")  
    public Object afterRunningMethod(JoinPoint joinPoint , Object result){  
        String methodName = joinPoint.getSignature().getName();  
        System.out.println("返回通知："+ methodName+" ends with the Result "+ result);
        return result;
    }  
      
      
    //在目标方法出现异常时会执行的代码，  
    //可以访问到异常对象，且可以指定在出现特定异常时在执行通知代码  
    //如下面Exception ex，若是指定为NullpointerException ex就不会执行通知代码  
    @AfterThrowing(value="serviceAspect()", throwing="ex")  
    public void afterThrowingMethod(JoinPoint joinPoint,Exception ex){  
        String methodName = joinPoint.getSignature().getName();  
        System.out.println("异常通知："+ methodName+"occurs exception:"+ex);  
    }  
      
    //坏绕通知：需要携带ProceedingJoinPoint类型的参数  
    //环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法  
    //且环绕通知必须有返回值，返回值即目标方法的返回值。  
    @Around("serviceAspect()")  
    public Object aroundMethod(ProceedingJoinPoint pjd) throws Exception{  
        Object result = null;  
        String methodName = pjd.getSignature().getName();  
        Object args = Arrays.asList(pjd.getArgs());  
        //执行目标方法  
        try {  
            //坏绕前置通知  
            System.out.println("坏绕前置通知："+methodName +" begins with "+ args);              
            result = pjd.proceed();  
            //坏绕后置通知  
            System.out.println("坏绕通知："+ methodName+" ends");  
        } catch (Throwable e) {  
            e.printStackTrace();  
            //异常通知  
            System.out.println("坏绕后置通知："+ methodName+"occurs exception:"+e);  
            //不抛出异常的话，异常就被上面抓住，执行下去，返回result，result值为null，转换为int  
            throw new NullPointerException();  
        }  
        //返回通知  
        System.out.println("坏绕通知返回："+ methodName+" ends with the Result "+ result);  
          
        return result;  
    }  
}
