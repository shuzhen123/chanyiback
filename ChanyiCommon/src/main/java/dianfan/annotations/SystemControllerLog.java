/**  
* @Title: SystemControllerLog.java
* @Package dianfan.annotations
* @Description: TODO
* @author Administrator
* @date 2018年5月19日 下午1:56:13
* @version V1.0  
*/
package dianfan.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: SystemControllerLog.java
 * @Package dianfan.annotations
 * @Description: TODO
 * @author Administrator
 * @date 2018年5月19日 下午1:56:13
 * @version V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SystemControllerLog {
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
