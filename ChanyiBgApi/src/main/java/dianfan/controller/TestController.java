/**  
* @Title: TestController.java
* @Package dianfan.controller
* @Description: TODO
* @author Administrator
* @date 2018年5月17日 下午2:17:06
* @version V1.0  
*/
package dianfan.controller;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.logger.Logger;
import dianfan.models.ResultBean;
import dianfan.util.PropertyUtil;

/**
 * @Title: TestController.java
 * @Package dianfan.controller
 * @Description: TODO
 * @author Administrator
 * @date 2018年5月17日 下午2:17:06
 * @version V1.0
 */
@Scope("request")
@Controller
@RequestMapping(value = "/")
public class TestController {
	/**
	 * @Title: getsample
	 * @Description:示例
	 * @param userid
	 *            用戶id
	 * @return ResultBean
	 * @time: 2018年4月27日上午10:50:47
	 */
	// @LogOp(method = "getsample", logtype = ConstantIF.LOG_TYPE_1, description =
	// "示例")
	@SystemControllerLog(method = "getsample", logtype = ConstantIF.LOG_TYPE_1, description = "示例")
	@ApiOperation(value = "示例", httpMethod = "GET", notes = "示例", response = Map.class)
	@RequestMapping(value = "/getsample", method = RequestMethod.GET)
	@ResponseBody
	public ResultBean getSample(
			@ApiParam(value = "用戶id") @RequestParam(value = "userid", required = true) String userid) {
		Logger.error(PropertyUtil.getProperty("uploadexcelroot"));
		return new ResultBean("success");
	}
}
