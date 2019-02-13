package dianfan.controller.advertorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.advertorial.AdvertorialClassifyService;
import dianfan.service.advertorial.AdvertorialService;
import dianfan.service.impl.RedisTokenService;

/** @ClassName AdvertorialController
 * @Description 文章控制器
 * @author zwb
 * @date 2018年6月28日 下午7:45:18
 */ 
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/advertorial")
public class AdvertorialController {
	
	/** 
	 * 文章接口
	 */
	@Autowired
	AdvertorialService advertorialService;
	
	/**
	 * redisService
	 */
	@Autowired
	RedisTokenService redisTokenService;
	
	/**
	 * 文章类别相关service
	 */
	@Autowired
	AdvertorialClassifyService advertorialClassifyService;
	
	/** @Title: findAdvertorialList
	 * @Description: 根据分类id来获取文章列表(分页)
	 * @param classifyId
	 * @return ResultBean
	 * @author zwb
	 * @throws:
	 * @time: 2018年6月28日 下午7:46:10
	 */ 
	@SystemControllerLog(method = "findAdvertorialList", logtype = ConstantIF.LOG_TYPE_1, description = "根据分类id来获取文章列表")
	@ApiOperation(value = "根据分类id来获取文章列表", httpMethod = "GET", notes = "根据分类id来获取文章列表", response = ResultBean.class)
	@RequestMapping(value = "/findAdvertorialList", method = RequestMethod.GET)
	@ResponseBody
	@UnCheckedFilter
	public ResultBean findAdvertorialList(
			@ApiParam(value = "asscesstoken") @RequestParam(value = ConstantIF.ACCESSTOKEN, required=false) String asscesstoken,
			@ApiParam(value = "分类id") @RequestParam(value = "classifyId") String classifyId,
			@ApiParam(value = "第几页") @RequestParam(value = "pageNum") Integer pageNum,
			@ApiParam(value = "每页的条数") @RequestParam(value = "count") Integer count
			) {
		//判断参数
		if(StringUtils.isEmpty(classifyId)) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		if(StringUtils.isEmpty(pageNum)) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		if(StringUtils.isEmpty(count)) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		
		TokenModel token = redisTokenService.getToken(asscesstoken);
		return new ResultBean(advertorialService.findAdvertorialsByClassifyId(classifyId,pageNum,count, token == null?null:token.getUserid()));
	}
	
	
	/** @Title: getAdvertorialDetail
	 * @Description: 获取文章详情，浏览量+1,有userid的情况下返回是否点赞
	 * @param id
	 * @param userid
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月29日 上午11:49:42
	 */ 
	@SystemControllerLog(method = "getAdvertorialDetail", logtype = ConstantIF.LOG_TYPE_1, description = "获取文章详情，浏览量+1,有userid的情况下返回是否点赞")
	@ApiOperation(value = "获取文章详情，浏览量+1,有userid的情况下返回是否点赞", httpMethod = "GET", notes = "获取文章详情，浏览量+1,有userid的情况下返回是否点赞", response = ResultBean.class)
	@RequestMapping(value = "/getAdvertorialDetail", method = RequestMethod.GET)
	@ResponseBody
	@UnCheckedFilter
	public ResultBean getAdvertorialDetail(@ApiParam(value = "文章id") @RequestParam(value = "id") String id,
										   @ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN,required = false) String accesstoken) {
		//判断参数
		if(StringUtils.isEmpty(id)) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		//获取token
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		//有token
		if(!StringUtils.isEmpty(tokens)) {
			//查询用户id
			String userId = tokens.getUserid();
			//有userid，返回文章信息和是否点赞
			if(!StringUtils.isEmpty(userId)) {
				return advertorialService.getAdvertorialDetailAndThumbsup(id,userId);
			}
		}
		//没传userid,就返回文章信息
		return advertorialService.getAdvertorialById(id);
		
	}
	
	/** @Title: findAdvertorialClassifys
	 * @Description: 获取文章类别列表
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月28日 下午5:17:55
	 */ 
	@SystemControllerLog(method = "findAdvertorialClassifys", logtype = ConstantIF.LOG_TYPE_1, description = "获取文章类别列表")
	@ApiOperation(value = "获取文章类别列表", httpMethod = "GET", notes = "获取文章类别列表", response = ResultBean.class)
	@RequestMapping(value = "/findAdvertorialClassifys", method = RequestMethod.GET)
	@ResponseBody
	@UnCheckedFilter
	public ResultBean findAdvertorialClassifys() {
		return new ResultBean(advertorialClassifyService.findAdvertorialClassifys());
	}
	
	/**
	 * @Title: praise
	 * @Description: 点赞/取消点赞
	 * @param accesstoken
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 上午10:21:09
	 * @author cjy
	 */
	@SystemControllerLog(method = "praise", logtype = ConstantIF.LOG_TYPE_1, description = "点赞/取消点赞")
	@ApiOperation(value = "点赞/取消点赞", httpMethod = "POST", notes = "点赞/取消点赞", response = ResultBean.class)
	@RequestMapping(value = "praise", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean praise(
			@ApiParam(value = "token值") @RequestParam(value = ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value = "文章id") @RequestParam(value = "id") String id,
			@ApiParam(value = "动作（0取消，1点赞）") @RequestParam(value = "action") int action) {
		TokenModel tokens = redisTokenService.getToken(accesstoken);
		advertorialService.praiseAdvertorial(id, action, tokens.getUserid());
		return new ResultBean();
	}
	
	
}
