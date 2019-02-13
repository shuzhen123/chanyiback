package dianfan.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: LogOp.java
 * @Package dianfan.annotations
 * @Description: 日志记录annotations
 * @author Administrator
 * @date 2018年5月11日 上午9:57:25
 * @version V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestLogOp {
	/**
	 * 方法名
	 * 
	 * @return 必填
	 */
	String method();

	/**
	 * LOG类型
	 * 
	 * @return 默认""
	 */
	String logtype() default "";

	/**
	 * 用户id
	 * 
	 * @return 默认""
	 */
	String userid() default "";

	/**
	 * 描述
	 * 
	 * @return 默认""
	 */
	String description() default "";
}
