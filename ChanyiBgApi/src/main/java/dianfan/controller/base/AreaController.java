package dianfan.controller.base;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.entities.base.Area;
import dianfan.models.ResultBean;
import dianfan.service.base.AreaService;

/**
 * @ClassName AreaController
 * @Description 地区接口控制器
 * @author cjy
 * @date 2018年6月30日 下午3:29:24
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/base")
public class AreaController {
	/**
	 * 注入: #AreaService
	 */
	@Autowired
	private AreaService areaService;
	
	/**
	 * @Title: findAllArea
	 * @Description: 获取全部省市区县
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年6月30日 下午3:30:59
	 */
	@SystemControllerLog(method = "findAllArea", logtype = ConstantIF.LOG_TYPE_1, description = "获取全部省市区县")
	@ApiOperation(value = "获取全部省市区县", httpMethod = "GET", notes = "获取全部省市区县", response = ResultBean.class)
	@RequestMapping(value = "/findAllArea", method = RequestMethod.GET)
	@ResponseBody
	@UnCheckedFilter
	public ResultBean findAllArea() {
		List<Area> allArea = areaService.findAllArea();
		return new ResultBean(allArea);
	}
	
	
}