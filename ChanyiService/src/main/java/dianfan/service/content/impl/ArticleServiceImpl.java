package dianfan.service.content.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.content.ArticleMapper;
import dianfan.entities.advertorial.Advertorial;
import dianfan.entities.banner.AdvertorialClassifyModels;
import dianfan.models.ResultBean;
import dianfan.service.content.ArticleService;
import dianfan.service.impl.FileUploadService;
import dianfan.util.PropertyUtil;
import dianfan.util.UUIDUtil;

/**
 * @ClassName ArticleServiceImpl
 * @Description 文章相关
 * @author sz
 * @date 2018年7月20日 上午9:44:37
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	
	
	/**
	 * 注入： #ArticleMapper
	 */
	@Autowired
	private ArticleMapper articleMapper;

	
	/**
	* 注入: #FileUploadService
	*/
	@Autowired
	private FileUploadService fileUploadService;
	
	/*
	 * /(non-Javadoc)
	 * <p>Title: findArticleList</p>
	 * <p>Description: 获取文章分类</p>
	 * @return
	 * link: @see dianfan.service.content.ArticleService#findArticleList()
	 */
	@SystemServiceLog(method = "findArticleList",description = "获取文章分类列表 ")
	@Override
	public ResultBean findClassifyList(String desc) {
		// 创建返回模型
		Map<String, Object> data = new HashMap<>();
		// 获取分类数量
		int count = articleMapper.findClassifyCount();
		// 返回数据中添加总数
		data.put("count", count);
		if (count == 0) {
			data.put("articleList", new ArrayList<>());
			return new ResultBean(data);
		}
		// 获取分类列表
		List<AdvertorialClassifyModels> articleList = articleMapper.findClassifyList(desc);
		// 修改回传图片路径
		for (AdvertorialClassifyModels adv : articleList) {
			adv.setPicAddr(PropertyUtil.getProperty("domain") + adv.getPicAddr());
		}
		// 添加返回参数
		data.put("articleList", articleList);
		//  成功返回
		return new ResultBean(data);
	}

	
	/*
	 * (non-Javadoc)
	 * <p>Title: addClassifyList</p>
	 * <p>Description: 增加文章分类</p>
	 * @return
	 * link: @see dianfan.service.content.ArticleService#addClassifyList()
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addClassifyList",description = "增加文章分类 ")
	public ResultBean addClassifyList(String userid, String name, Integer sort) throws IOException {
		// 检查是否有重复的分类名称 
		int count = articleMapper.getCheckClassify(name);
		if (count != 0) {
			// !此分类名称已经存在
			return new ResultBean("4024",ResultBgMsg.C_4024);
		}
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		// 添加其他参数
		param.put("userid",userid);
		param.put("name",name);
		param.put("sort",sort);
		// 添加操作
		articleMapper.addClassifyList(param);
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: updataClassifyList</p>
	 * <p>Description: </p>
	 * @param userid 用户
	 * @param name 分类名
	 * @param id ID
	 * @param sort 排序
	 * @param pic 图片
	 * @return
	 * @throws IOException
	 * link: @see dianfan.service.content.ArticleService#updataClassifyList(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updataClassifyList",description = "修改文章分类 ")
	public ResultBean updataClassifyList(String userid, String name, String id, Integer sort)
			throws IOException {
		Map<String, Object> param = new HashMap<>();
		param.put("name", name);
		param.put("id", id);
		// 检查是否有重复的分类名称 
		int count = articleMapper.getCheckClassifyOwn(param);
		if (count != 0) {
			// !此分类名称已经存在
			return new ResultBean("4024",ResultBgMsg.C_4024);
		}
		// 添加其他参数
		param.put("userid",userid);
		param.put("sort",sort);
		// 修改操作 
		articleMapper.updataClassify(param);
		// 成功
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: updataClassifyList</p>
	 * <p>Description: 修改文章分类状态</p>
	 * @param userid userid
	 * @param type 状态
	 * @param ids 分类ID
	 * @return
	 * link: @see dianfan.service.content.ArticleService#updataClassifyList(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updataClassifyList",description = "修改文章分类状态")
	public ResultBean updataEditClassifyType(String userid, Integer type, String ids) {
		// 整理IDs
		List<String> list = Arrays.asList(ids.split(","));
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("list", list);
		param.put("type", type);
		// 修改状态操作
		articleMapper.updataEditClassifyType(param);
		return new ResultBean();
	}

	
	/*
	 * (non-Javadoc)
	 * <p>Title: findaAticleList</p>
	 * <p>Description: 获取文章列表</p>
	 * @param userid 用户ID
	 * @return
	 * link: @see dianfan.service.content.ArticleService#findaAticleList(java.lang.String)
	 */
	@SystemServiceLog(method = "findaAticleList",description = "获取文章列表")
	@Override
	public ResultBean findAticleList(String userid, int page, int pageSize,String title,String typeid,String starttime,String endtime,String desc) {
		// 返回数据模型
		Map<String, Object> data = new HashMap<>();
		
		// 构建入参数据模型
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		// 获取文章数量
		int count = articleMapper.findAticleListCount();
		data.put("count", count);
		if (count == 0 || count < (page - 1 ) * pageSize ) {
			// 没有数据，返回一个空的arraylist
			data.put("aticleList", new ArrayList<>());
			return new ResultBean(data);
		}
		param.put("start", (page - 1 ) * pageSize );
		param.put("length", pageSize );
		param.put("title", title );
		param.put("typeid", typeid );
		param.put("starttime", starttime );
		param.put("endtime", endtime );
		param.put("desc", desc);
		// 获取文章列表
		List<Advertorial> aticleList = articleMapper.findAticleList(param);
		
		for (Advertorial adv :  aticleList) {
			adv.setPicAddr(PropertyUtil.getProperty("domain") + adv.getPicAddr());
		}
		data.put("aticleList", aticleList);
		
		return new ResultBean(data);
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: addAticleList</p>
	 * <p>Description: 添加文章</p>
	 * @param userid 用户ID
	 * @param classify 文章分类ID
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片地址
	 * @return
	 * link: @see dianfan.service.content.ArticleService#addAticleList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addAticleList",description = "添加文章")
	public ResultBean addAticleList(String userid, String classify, String title, String content, String pic,Integer sort,Integer numN,int entkbn) {
		// 验证新增的标题是否存在重复
//		int count = articleMapper.getCheckAticleRepetition(title);
//		if (count != 0) {
//			// 存在标题相同的文章
//			return new ResultBean("4025",ResultBgMsg.C_4025);
//		}
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("classify", classify);
		param.put("title", title);
		param.put("content", content);
		param.put("pic", pic);
		param.put("sort", sort);
		param.put("numN", numN);
		param.put("entkbn", entkbn);
		// 创建一个新的uuid
		param.put("id", UUIDUtil.getUUID());
		// 添加操作
		articleMapper.addAticle(param);
		// 成功
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: updatAticle</p>
	 * <p>Description: 修改文章</p>
	 * @param userid 用户ID
	 * @param classify 文章分类ID
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片地址
	 * @param sort 排序
	 * @param id 文章ID
	 * @return
	 * link: @see dianfan.service.content.ArticleService#updatAticle(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updatAticle",description = "修改文章")
	public ResultBean updatAticle(String userid, String classify, String title, String content, String pic,
			Integer sort, String id, Integer numN) {
		// 检验修改的标题是否存在重复，排除自身
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("title", title);
		param.put("id", id);
//		int count = articleMapper.getCheckAticleRepetitionButMe(param);
//		if (count != 0) {
//			// 存在标题相同的文章
//			return new ResultBean("4025",ResultBgMsg.C_4025);
//		}
		// 修改图片的路径
		int indexOf = pic.indexOf("upload");
		String spic = pic.substring(indexOf);
		
		param.put("userid", userid);
		param.put("classify", classify);
		param.put("content", content);
		param.put("pic", spic);
		param.put("sort", sort);
		param.put("numN", numN);
		// 修改文章操作
		articleMapper.updatAticle(param);
		// 成功
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: delAticle</p>
	 * <p>Description: 修改文章状态</p>
	 * @param userid
	 * @param ids
	 * @param type
	 * @return
	 * link: @see dianfan.service.content.ArticleService#delAticle(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@SystemServiceLog(method = "delAticle",description = "修改文章状态")
	@Override
	public ResultBean delAticle(String userid, String ids, Integer type) {
		//IDs整理
		List<String> lids = Arrays.asList(ids.split(","));
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("lids", lids);
		param.put("type", type);
		// 修改状态操作
		articleMapper.delAticle(param);
		// 成功
		return new ResultBean();
	}
	
	
	
	
	
	
	
	

}
