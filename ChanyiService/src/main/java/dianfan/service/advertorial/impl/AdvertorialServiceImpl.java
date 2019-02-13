package dianfan.service.advertorial.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import dianfan.constant.ResultApiMsg;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.advertorial.AdvertorialMapper;
import dianfan.dao.mapper.thumbup.ThumbUpMapper;
import dianfan.entities.advertorial.Advertorial;
import dianfan.entities.advertorial.AdvertorialModel;
import dianfan.entities.advertorialthumbup.AdvertorialThumbUp;
import dianfan.models.ResultBean;
import dianfan.service.advertorial.AdvertorialService;
import dianfan.util.PropertyUtil;
import dianfan.util.UUIDUtil;

/** @ClassName AdvertorialServiceImpl
 * @Description 文章相关业务层实现类
 * @author zwb
 * @date 2018年6月28日 下午7:00:23
 */ 
@Service
public class AdvertorialServiceImpl implements AdvertorialService{
	
	/**
	 * 文章dao
	 */
	@Autowired
	AdvertorialMapper advertorialMapper;
	
	/**
	 * 点赞dao
	 */
	@Autowired
	ThumbUpMapper thumbUpMapper;

	/*count (non-Javadoc)
	 * <p>Title: getAdvertorialsByClassifyId</p>
	 * <p>Description: 根据分类id来查询文章列表</p>
	 * @param classifyId
	 * @return Map<String,Object>
	 * link: @see dianfan.service.advertorial.AdvertorialService#getAdvertorialsByClassifyId(java.lang.String)
	 */ 
	@Override
	public Map<String,Object> findAdvertorialsByClassifyId(String classifyId,Integer pageNum,Integer count,String userid) {
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		//查询总共多少条
		int totalCount = advertorialMapper.findAdvertorialsCount(classifyId); 
		//起始的条数
		int start = (pageNum - 1) * count;
		if(totalCount == 0 || start > totalCount) {
			//没有数据
			result.put("totalCount", 0);
			result.put("advertorials", Collections.emptyList());
		}
		//有数据
		param.put("classifyId", classifyId);//分类id
		param.put("start", start);//起始的条数
		param.put("count", count);//一页条数
		param.put("userid", userid);//
		//放入总条数
		result.put("totalCount", totalCount);//总条数
		//查询出文章列表
		List<AdvertorialModel> advertorials = advertorialMapper.findAdvertorialsByClassifyId(param);
		//拼接图片的url
		for(AdvertorialModel a : advertorials) {
			a.setPicAddr(PropertyUtil.getProperty("domain") + a.getPicAddr());
		}
		//放入文章列表
		result.put("advertorials", advertorials);//文章列表
		return result;
	}

	/* (non-Javadoc)
	 * <p>Title: getAdvertorialDetail</p>
	 * <p>Description: 查询文章详情，并且返回是否点赞</p>
	 * @param id
	 * @return  Map<String,Object>
	 * link: @see dianfan.service.advertorial.AdvertorialService#getAdvertorialDetail(java.lang.String)
	 */ 
	@Override
	public ResultBean getAdvertorialDetailAndThumbsup(String id,String userId) {
		//查询参数
		Map<String,Object> param = new HashMap<String,Object>();
		//返回参数
		Map<String,Object> result = new HashMap<String,Object>();
		//用文章id查出文章类
		Advertorial advertorial = advertorialMapper.getAdvertorialDetailAllById(id);
		if(StringUtils.isEmpty(advertorial)) {
			return new ResultBean("3005",ResultApiMsg.C_3005);
		}
		//文章浏览量+ 1
		advertorial.setVisitCounts(advertorial.getVisitCounts() + 1);
		//更新文章浏览量
		advertorialMapper.updateAdvertorialVisitCounts(advertorial);
		//文章id
		param.put("advertorialId", id);
		//用户id
		param.put("userId", userId);
		//传入用户id和文章id查出是否有点赞数据
		AdvertorialThumbUp advertorialThumbUp = thumbUpMapper.getAdvertorialThumbUp(param);
		//初始化是否点赞
		Boolean flag = false;
		//在点赞表里有数据
		if(advertorialThumbUp != null) {
			flag = true;
		}
		//文章信息
//		AdvertorialModel advertorialModel = new AdvertorialModel();
//		advertorialModel.setClassifyId(advertorial.getClassifyId());
//		advertorialModel.setContent(advertorial.getContent());
//		advertorialModel.setCreateTime(advertorial.getCreateTime());
//		advertorialModel.setId(advertorial.getId());
//		advertorialModel.setPicAddr(PropertyUtil.getProperty("domain") + advertorial.getPicAddr());
//		advertorialModel.setThumbUpTotal(thumbUpTotal);
		//查询文章返回类
		AdvertorialModel  advertorialModel = advertorialMapper.getAdvertorialDetailById(id);
		//设置图片地址
		advertorialModel.setPicAddr(PropertyUtil.getProperty("domain") + advertorialModel.getPicAddr());
		result.put("advertorialModel", advertorialModel);
		//是否点赞
		result.put("thumbUp", flag);
		return new ResultBean(result);
	}

	/* (non-Javadoc)
	 * <p>Title: getAdvertorialById</p>
	 * <p>Description: 根据文章id查询文章详情</p>
	 * @param id
	 * @return Advertorial 文章
	 * link: @see dianfan.service.advertorial.AdvertorialService#getAdvertorialById(java.lang.String)
	 */ 
	@Override
	public ResultBean getAdvertorialById(String id) {
		Map<String,Object> result = new HashMap<String,Object>();
		//根据文章id查出文章
		Advertorial advertorial = advertorialMapper.getAdvertorialDetailAllById(id);
		//文章浏览量+ 1
		if(advertorial == null) {
			return new ResultBean("3002",ResultApiMsg.C_3002);
		}
		advertorial.setVisitCounts(advertorial.getVisitCounts() + 1);
		//更新浏览量
		advertorialMapper.updateAdvertorialVisitCounts(advertorial);
		//返回文章信息
//		return advertorialMapper.getAdvertorialDetailById(id);
		//查询文章返回类
		AdvertorialModel advertorialModel = advertorialMapper.getAdvertorialDetailById(id);
		//点赞-未点赞
		Boolean flag = false;
		result.put("advertorialModel", advertorialModel);
		result.put("thumbUp", flag);
		return new ResultBean(result);
	}

	/* (non-Javadoc)
	 * <p>Title: confirmThumbUp</p>
	 * <p>Description: 点赞</p>
	 * @param id
	 * @param userId
	 * @return ResultBean
	 * link: @see dianfan.service.advertorial.AdvertorialService#confirmThumbUp(java.lang.String, java.lang.String)
	 */ 
	@Override
	public ResultBean confirmThumbUp(String id, String userId) {
		//查询参数
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("advertorialId", id);
		param.put("userId", userId);
		//在文章点赞表中去查询
		AdvertorialThumbUp advertorialThumbUp = thumbUpMapper.getAdvertorialThumbUp(param);
		//没有数据
		if(advertorialThumbUp == null) {
			//查出这篇文章
			Advertorial advertorial = advertorialMapper.getAdvertorialDetailAllById(id);
			//点赞数+1
			advertorial.setThumbUpNum(advertorial.getThumbUpNum() + 1);
			//执行更新操作
			advertorialMapper.updateAdvertorialThumbUp(advertorial);
			//新建文章点赞实体类
			AdvertorialThumbUp at= new AdvertorialThumbUp();
			at.setAdvertorialId(id);//文章Id
			at.setCreateBy(userId);//创建者id
			at.setCreateTime(new Date());//创建时间
			at.setEntkbn(0);//数据有效性-有效
			at.setId(UUIDUtil.getUUID());//主键id
			at.setUserId(userId);//用户id
			//往文章点赞表中插入数据
			thumbUpMapper.insertThumbUp(at);
			//正常,返回
			return new ResultBean();
		}
		return new ResultBean("3000",ResultApiMsg.C_3000);
	}

	/* (non-Javadoc)
	 * <p>Title: cancelThumbUp</p>
	 * <p>Description: 取消点赞</p>
	 * @param id
	 * @param userId
	 * @return ResultBean
	 * link: @see dianfan.service.advertorial.AdvertorialService#cancelThumbUp(java.lang.String, java.lang.String)
	 */ 
	@Override
	public ResultBean cancelThumbUp(String id, String userId) {
		//查询参数
		Map<String,Object> param = new HashMap<String,Object>();
		//文章id
		param.put("advertorialId", id);
		//用户id
		param.put("userId", userId);
		//在文章点赞表中去查询
		AdvertorialThumbUp advertorialThumbUp = thumbUpMapper.getAdvertorialThumbUp(param);
		if(advertorialThumbUp == null) {
			//该用户没有点赞过这篇文章
			return new ResultBean("3001",ResultBgMsg.C_3001);
		}
		//取消点赞
		//修改文章点赞表中数据有效性-改为9
		thumbUpMapper.updataThumbUp(advertorialThumbUp);
		//查出这篇文章
		Advertorial at = advertorialMapper.getAdvertorialDetailAllById(id);
		//文章点赞数-1
		at.setThumbUpNum(at.getThumbUpNum() -1);
		//更新点赞数操作
		advertorialMapper.updateAdvertorialThumbUp(at);
		//正常，返回200
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: praiseAdvertorial</p>
	 * <p>Description: 点赞/取消点赞</p>
	 * @param id 文章id
	 * @param userid 人员
	 * @author cjy
	 * link: @see dianfan.service.advertorial.AdvertorialService#praiseAdvertorial(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultBean praiseAdvertorial(String id, int action, String userid) {
		//查看有没有点赞
		boolean bool = advertorialMapper.checkPraiseStatus(id, userid);
		if(bool) {
			if(action == 0) {
				//已点过赞，需取消点赞
				advertorialMapper.unpraiseAdvertorial(id, userid);
				//点赞数减一
				advertorialMapper.praiseCountDec(id);
				return new ResultBean();
			}else {
				return new ResultBean("3000",ResultApiMsg.C_3000);
			}
		}else {
			if(action == 1) {
				//未点过赞，需点赞
				advertorialMapper.praiseAdvertorial(id, userid);
				//点赞数加一
				advertorialMapper.praiseCountInc(id);
				return new ResultBean();
			}else {
				return new ResultBean("3001",ResultApiMsg.C_3001);
			}
		}
		
	}
	
	
	
	

	

}
