package dianfan.controller.content;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.content.BannerAdminService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.StringUtility;


/**
 * @ClassName BannerManage
 * @Description banner相关
 * @author sz
 * @date 2018年7月19日 下午1:23:26
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/adminbanner")
public class BannerManage {
	
	/**
	 * 注入： #BannerAdminService
	 */
	@Autowired
	private BannerAdminService bannerAdminService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	
	
	/**
	 * @Title: findBannerPic
	 * @Description: 获取首页轮播图列表
	 * @param accesstoken accesstoken
	 * @param page 请求页
	 * @param length 每页条数
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午2:32:47
	 */
	@SystemControllerLog(method = "findBannerPic", logtype = ConstantIF.LOG_TYPE_2, description = "获取首页轮播图列表")
	@RequestMapping(value = "/findBannerPic", method = RequestMethod.POST)
	public @ResponseBody ResultBean findBannerPic (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			@ApiParam(value="正倒序") @RequestParam(value="desc",required=false,defaultValue="0") String desc
			) {
		
		// 构建返回模型
		ResultBean result = new ResultBean();
		
		// 调库查询
		result = bannerAdminService.findBannerPic(page, pageSize,desc);
		// 返回
		return result;
	}
	
	/**
	 * @Title: addBannerPic
	 * @Description: 添加首页轮播图列表
	 * @param accesstoken accesstoken
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片
	 * @param desc 描述
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年7月19日 下午4:26:46
	 */
	@SystemControllerLog(method = "addBannerPic", logtype = ConstantIF.LOG_TYPE_2, description = "添加首页轮播图列表")
	@RequestMapping(value = "/addBannerPic", method = RequestMethod.POST)
	public @ResponseBody ResultBean addBannerPic (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="标题") @RequestParam(value="title") String title,
			@ApiParam(value="内容") @RequestParam(value="content") String content,
			@ApiParam(value="图片") @RequestParam(value="pic") String pic,
			@ApiParam(value="描述") @RequestParam(value="desc") String desc,
			@ApiParam(value="排序") @RequestParam(value="sort") String sort
			) throws IOException {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 获取登陆者的ID
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (StringUtils.isEmpty(token)) {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		String userid = token.getUserid();		
		// 入参验证
		if (StringUtility.isNull(title) || StringUtility.isNull(content) || StringUtility.isNull(pic) || StringUtility.isNull(desc)) {
			// 请将添加信息填写完整
			return new ResultBean("4019",ResultBgMsg.C_4019);
		}
		// 调库查询
		result = bannerAdminService.addBannerPic(title,content,pic,desc,userid,sort);
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: addBannerPic
	 * @Description: 修改首页轮播图列表
	 * @param accesstoken accesstoken
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片
	 * @param desc 描述
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年7月19日 下午4:26:46
	 */
	@SystemControllerLog(method = "editBannerPic", logtype = ConstantIF.LOG_TYPE_2, description = "修改首页轮播图列表")
	@RequestMapping(value = "/editBannerPic", method = RequestMethod.POST)
	public @ResponseBody ResultBean editBannerPic (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="轮播图ID") @RequestParam(value="id") String id,
			@ApiParam(value="标题") @RequestParam(value="title") String title,
			@ApiParam(value="内容") @RequestParam(value="content") String content,
			@ApiParam(value="图片") @RequestParam(value="pic") String pic,
			@ApiParam(value="描述") @RequestParam(value="desc") String desc,
			@ApiParam(value="排序") @RequestParam(value="sort") String sort
			) throws IOException {
		
		// 构建返回模型
		ResultBean result = new ResultBean();		
		// 获取登陆者的ID
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (StringUtils.isEmpty(token)) {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		String userid = token.getUserid();		
		// 入参验证
		if (StringUtility.isNull(title) || StringUtility.isNull(content) || StringUtility.isNull(desc)) {
			// 请将添加信息填写完整
			return new ResultBean("4019",ResultBgMsg.C_4019);
		}
		// 调库查询
		result = bannerAdminService.updataBannerPic(id,title,content,pic,desc,userid,sort);		
		// 返回
		return result;		
	}
	
	/**
	 * @Title: delBannerPic
	 * @Description: 修改轮播图状态
	 * @param accesstoken accesstoken
	 * @param ids 轮播ids
	 * @param type 00 启用， 01 禁用 09 删除
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午6:56:30
	 */
	@SystemControllerLog(method = "delBannerPic", logtype = ConstantIF.LOG_TYPE_2, description = "删除首页轮播图列表")
	@RequestMapping(value = "/delBannerPic", method = RequestMethod.POST)
	public @ResponseBody ResultBean delBannerPic (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="轮播图IDs") @RequestParam(value="ids") String ids,
			@ApiParam(value="状态") @RequestParam(value="type") String type
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();		
		// 获取登陆者的ID
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (StringUtils.isEmpty(token)) {
			return new ResultBean("001", ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (StringUtility.isNull(ids)) {
			// 参数错误
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		result = bannerAdminService.delBannerPic(userid,ids,type);
		
		// 返回
		return result;	
	}

}
