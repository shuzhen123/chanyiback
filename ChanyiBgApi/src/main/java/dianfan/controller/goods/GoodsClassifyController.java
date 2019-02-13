package dianfan.controller.goods;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.entities.goods.GoodsClassify;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.goods.ClassifyService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName GoodsClassifyController
 * @Description 商品分类管理
 * @author cjy
 * @date 2018年7月16日 上午11:33:45
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods/classify")
public class GoodsClassifyController {
	@Autowired
	private ClassifyService classifyService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: findGoodsClassifyList
	 * @Description: 获取商品分类列表
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 上午11:38:10
	 */
	@SystemControllerLog(method = "findGoodsClassifyList", logtype = ConstantIF.LOG_TYPE_2, description = "获取商品分类列表")
	@ApiOperation(value = "获取商品分类列表", httpMethod = "GET", notes = "获取商品分类列表", response = ResultBean.class)
	@RequestMapping(value = "/classifyList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findGoodsClassifyList(@ApiParam(value="只获取可用分类（1可用）") @RequestParam(value="used", required=false) String used) {
		return classifyService.findGoodsClassifyList(used);
	}
	
	/**
	 * @Title: addGoodsClassify
	 * @Description: 新增商品分类
	 * @param accesstoken
	 * @param classifyParentid
	 * @param classifyName
	 * @param classifyNameEn
	 * @param sort
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午3:06:52
	 */
	@SystemControllerLog(method = "addGoodsClassify", logtype = ConstantIF.LOG_TYPE_2, description = "新增商品分类")
	@ApiOperation(value = "新增商品分类", httpMethod = "POST", notes = "新增商品分类", response = ResultBean.class)
	@RequestMapping(value = "/addGoodsClassify", method = RequestMethod.POST)
	public @ResponseBody ResultBean addGoodsClassify(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品父节点分类id") @RequestParam(value="classifyParentid", required=false) String classifyParentid,
			@ApiParam(value="商品分类名称") @RequestParam(value="classifyName") String classifyName,
			@ApiParam(value="商品分类英文名称") @RequestParam(value="classifyNameEn", required=false) String classifyNameEn,
			@ApiParam(value="商品分类排序") @RequestParam(value="sort") Integer sort) {
		GoodsClassify goodsClassify = new GoodsClassify();
		
		if(classifyName == null || StringUtils.isEmpty(classifyName.trim())) {
			return new ResultBean("3000", "商品分类名称" + ResultBgMsg.C_3000);
		}else {
			goodsClassify.setClassifyName(classifyName);
		}
		goodsClassify.setClassifyNameEn(classifyNameEn);
		
		if(classifyParentid == null || StringUtils.isEmpty(classifyParentid.trim())) {
			//无商品父节点分类id
			goodsClassify.setClassifyParentid("0");
			goodsClassify.setClassifyLevel("0");
		}else {
			//有商品父节点分类id
			goodsClassify.setClassifyParentid(classifyParentid.trim());
			goodsClassify.setClassifyLevel("1");
		}

		goodsClassify.setSort(sort);
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		goodsClassify.setCreateBy(token.getUserid());
		
		return classifyService.addGoodsClassify(goodsClassify);
	}
	
	/**
	 * @Title: editGoodsClassify
	 * @Description: 修改商品分类
	 * @param accesstoken
	 * @param classifyid
	 * @param classifyName
	 * @param classifyNameEn
	 * @param sort
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午3:06:34
	 */
	@SystemControllerLog(method = "editGoodsClassify", logtype = ConstantIF.LOG_TYPE_2, description = "修改商品分类")
	@ApiOperation(value = "修改商品分类", httpMethod = "POST", notes = "修改商品分类", response = ResultBean.class)
	@RequestMapping(value = "/editGoodsClassify", method = RequestMethod.POST)
	public @ResponseBody ResultBean editGoodsClassify(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品分类id") @RequestParam(value="classifyid") String classifyid,
			@ApiParam(value="商品分类名称") @RequestParam(value="classifyName", required=false) String classifyName,
			@ApiParam(value="商品分类英文名称") @RequestParam(value="classifyNameEn", required=false) String classifyNameEn,
			@ApiParam(value="商品分类排序") @RequestParam(value="sort", required=false) Integer sort) {
		GoodsClassify goodsClassify = new GoodsClassify();
		goodsClassify.setId(classifyid);
		
		if(classifyName == null || StringUtils.isEmpty(classifyName.trim())) {
			return new ResultBean("3000", "商品分类名称" + ResultBgMsg.C_3000);
		}else {
			goodsClassify.setClassifyName(classifyName);
		}
		goodsClassify.setClassifyNameEn(classifyNameEn);
		
		goodsClassify.setSort(sort);
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		goodsClassify.setCreateBy(token.getUserid());
		
		return classifyService.editGoodsClassify(goodsClassify);
	}
	
	/**
	 * @Title: delGoodsClassify
	 * @Description: 删除/禁用/启用 商品分类
	 * @param accesstoken
	 * @param classifyid
	 * @param status
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午3:31:59
	 */
	@SystemControllerLog(method = "delGoodsClassify", logtype = ConstantIF.LOG_TYPE_2, description = "删除/禁用/启用 商品分类")
	@ApiOperation(value = "删除/禁用/启用 商品分类", httpMethod = "POST", notes = "删除/禁用/启用 商品分类", response = ResultBean.class)
	@RequestMapping(value = "/delGoodsClassify", method = RequestMethod.POST)
	public @ResponseBody ResultBean delGoodsClassify(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品分类id") @RequestParam(value="classifyid") String classifyid,
			@ApiParam(value="操作状态(0启用，1禁用，9删除)") @RequestParam(value="status") int status) {
		
		if(classifyid == null || StringUtils.isEmpty(classifyid.trim())) {
			return new ResultBean("3002", ResultBgMsg.C_3002);
		}
		
		TokenModel token = redisTokenService.getToken(accesstoken);
		return classifyService.delGoodsClassify(classifyid.trim(), status, token.getUserid());
	}
}
