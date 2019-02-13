package dianfan.controller.content;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
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
import dianfan.service.content.ArticleService;
import dianfan.service.impl.RedisTokenService;
import dianfan.util.StringUtility;

/**
 * @ClassName ArticleClassifyManage
 * @Description 文章管理相关
 * @author sz
 * @date 2018年7月20日 上午9:40:43
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/article")
public class ArticleManage {
	
	/**
	 * 注入： #ArticleService
	 */
	@Autowired
 	private ArticleService articleService;
	
	/**
	 * 注入: #RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	
	/**
	 * @Title: findArticleList
	 * @Description: 获取文章分类
	 * @param accesstoken accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 上午9:51:48
	 */
	@SystemControllerLog(method = "findClassifyList", logtype = ConstantIF.LOG_TYPE_2, description = "获取文章分类列表")
	@RequestMapping(value = "/findClassifyList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findClassifyList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="正倒序") @RequestParam(value="desc",required=false,defaultValue="0") String desc
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		// 调库查询
		result = articleService.findClassifyList(desc);
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: addClassifyList
	 * @Description: 增加文章分类
	 * @param accesstoken
	 * @param name 分类名
	 * @param sort 排序
	 * @param pic 图片 
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年7月20日 上午11:08:48
	 */
	@SystemControllerLog(method = "addClassifyList", logtype = ConstantIF.LOG_TYPE_2, description = "增加文章分类")
	@RequestMapping(value = "/addClassifyList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean addClassifyList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="分类名称") @RequestParam(value="name") String name,
			@ApiParam(value="排序") @RequestParam(value="sort") Integer sort
			//@ApiParam(value="图片") @RequestParam(value="pic") String pic
			) throws IOException {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		// 非空验证
		if (StringUtility.isNull(name) ) {
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		// 调库查询
		result = articleService.addClassifyList(userid,name,sort);
		// 返回
		return result;
	}
	
	
	
	/**
	 * @Title: editClassifyList
	 * @Description: 修改文章分类
	 * @param accesstoken accesstoken
	 * @param name 分类名
	 * @param id ID
	 * @param sort 排序
	 * @param pic 图片 
	 * @return 
	 * @throws IOException
	 * @throws:
	 * @time: 2018年7月20日 下午1:35:54
	 */
	@SystemControllerLog(method = "editClassifyList", logtype = ConstantIF.LOG_TYPE_2, description = "修改文章分类")
	@RequestMapping(value = "/editClassifyList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean editClassifyList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="分类名称") @RequestParam(value="name") String name,
			@ApiParam(value="分类的ID") @RequestParam(value="id") String id,
			@ApiParam(value="排序") @RequestParam(value="sort") Integer sort
			) throws IOException {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		// 非空验证
		if (StringUtility.isNull(name)|| StringUtility.isNull(id) ||org.springframework.util.StringUtils.isEmpty(sort)) {
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		// 调库
		result = articleService.updataClassifyList(userid,name,id,sort);
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: editClassifyListType
	 * @Description: 修改文章分类状态
	 * @param accesstoken accesstoken
	 * @param type 状态
	 * @param ids 分类ID
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年7月20日 下午2:03:24
	 */
	@SystemControllerLog(method = "editClassifyListType", logtype = ConstantIF.LOG_TYPE_2, description = "修改文章分类状态")
	@RequestMapping(value = "/editClassifyListType", method = RequestMethod.POST)	
	public @ResponseBody ResultBean editClassifyListType (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="分类状态") @RequestParam(value="type") Integer type,
			@ApiParam(value="分类的IDs") @RequestParam(value="id") String ids
			) throws IOException {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		// 非空验证
		if ( StringUtility.isNull(ids)) {
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		// 调库
		result = articleService.updataEditClassifyType(userid,type,ids);
		// 返回
		return result;
		}
		
		
	/**
	 * @Title: findaAticleList
	 * @Description: 获取文章列表
	 * @param accesstoken accesstoken
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:51:11
	 */
	@SystemControllerLog(method = "findaAticleList", logtype = ConstantIF.LOG_TYPE_2, description = "获取文章列表")
	@RequestMapping(value = "/findaAticleList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean findaAticleList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			@ApiParam(value="按标题搜索") @RequestParam(value="title",required=false) String title,
			@ApiParam(value="按分类搜索") @RequestParam(value="typeid",required=false) String typeid,
			@ApiParam(value="按开始时间") @RequestParam(value="starttime",required=false) String starttime,
			@ApiParam(value="按结束时间") @RequestParam(value="endtime",required=false) String endtime,
			@ApiParam(value="正倒序") @RequestParam(value="desc",required=false,defaultValue="1") String desc
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		// 调库
		result = articleService.findAticleList(userid,page,pageSize,title,typeid,starttime,endtime,desc);
		// 返回
		return result;
	}
	

	/**
	 * @Title: addAticleList
	 * @Description: 添加文章
	 * @param accesstoken accesstoken
	 * @param classify 文章分类ID
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片地址
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午4:47:04
	 */
	@SystemControllerLog(method = "addAticleList", logtype = ConstantIF.LOG_TYPE_2, description = "添加文章")
	@RequestMapping(value = "/addAticleList", method = RequestMethod.POST)	
	public @ResponseBody ResultBean addAticleList (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="类型id") @RequestParam(value="classify") String classify,
			@ApiParam(value="标题") @RequestParam(value="title") String title,
			@ApiParam(value="内容") @RequestParam(value="content") String content,
			@ApiParam(value="图片地址") @RequestParam(value="pic") String pic,
			@ApiParam(value="假点赞") @RequestParam(value="numN") Integer numN,
			@ApiParam(value="排序") @RequestParam(value="sort") Integer sort,
			@ApiParam(value="警用：1/启用：0") @RequestParam(value="entkbn") int entkbn
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (StringUtility.isNull(classify) || StringUtility.isNull(title) || StringUtility.isNull(content) || StringUtility.isNull(pic) ) {
			// 参数错误
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		// 调库
		result = articleService.addAticleList(userid,classify,title,content,pic,sort,numN,entkbn);
		// 返回
		return result;
	}
	
	
	/**
	 * @Title: editAticleList
	 * @Description: 修改文章
	 * @param accesstoken accesstoken
	 * @param classify 文章分类ID
	 * @param id 文章id
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片地址
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午4:47:04
	 */
	@SystemControllerLog(method = "editAticle", logtype = ConstantIF.LOG_TYPE_2, description = "修改文章")
	@RequestMapping(value = "/editAticle", method = RequestMethod.POST)	
	public @ResponseBody ResultBean editAticle (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="文章id") @RequestParam(value="id") String id,
			@ApiParam(value="类型id") @RequestParam(value="classify") String classify,
			@ApiParam(value="标题") @RequestParam(value="title") String title,
			@ApiParam(value="内容") @RequestParam(value="content") String content,
			@ApiParam(value="图片地址") @RequestParam(value="pic") String pic,
			@ApiParam(value="排序") @RequestParam(value="sort") Integer sort,
			@ApiParam(value="假点赞") @RequestParam(value="numN") Integer numN
			) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (StringUtility.isNull(classify) || StringUtility.isNull(title) || StringUtility.isNull(content) || StringUtility.isNull(pic) ) {
			// 参数错误
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		// 调库
		result = articleService.updatAticle(userid,classify,title,content,pic,sort,id,numN);
		// 返回
		return result;
	}
	
	
	
	
	/**
	 * @Title: delAticle
	 * @Description: 删除文章
	 * @param accesstoken accesstoken
	 * @param ids 文章ID
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午10:02:22
	 */
	@SystemControllerLog(method = "delAticle", logtype = ConstantIF.LOG_TYPE_2, description = "删除文章")
	@RequestMapping(value = "/delAticle", method = RequestMethod.POST)	
	public @ResponseBody ResultBean delAticle (
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="文章状态") @RequestParam(value="type") Integer type,
			@ApiParam(value="文章ids") @RequestParam(value="ids") String ids) {
		// 构建返回模型
		ResultBean result = new ResultBean();
		TokenModel token = redisTokenService.getToken(accesstoken);
		if (org.springframework.util.StringUtils.isEmpty(token)) {
			// 登陆信息已经过期
			return new ResultBean("001",ResultBgMsg.C_001);
		}
		String userid = token.getUserid();
		if (StringUtility.isNull(ids)) {
			// 参数错误
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		
		// 调库
		result = articleService.delAticle(userid,ids,type);
		
		// 返回
		return result;
		
	}
	
	
	
	

}
