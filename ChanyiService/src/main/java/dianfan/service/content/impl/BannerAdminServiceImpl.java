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
import dianfan.dao.mapper.content.BannerAdminMapper;
import dianfan.entities.banner.Banner;
import dianfan.models.ResultBean;
import dianfan.service.content.BannerAdminService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName BannerAdminService
 * @Description Banner相关 （后台）
 * @author sz
 * @date 2018年7月19日 下午1:28:34
 */
@Service
public class BannerAdminServiceImpl implements BannerAdminService {

	/**
	 * 注入： #BannerAdminMapper
	 */
	@Autowired
	private BannerAdminMapper bannerAdminMapper;

	/*
	 * (non-Javadoc) <p>Title: findBannerPic</p> <p>Description: 获取首页轮播图列表</p>
	 * 
	 * @param page 请求页
	 * 
	 * @param pageSize 每页条数
	 * 
	 * @return link: @see
	 * dianfan.service.content.BannerAdminService#findBannerPic(int, int)
	 */
	@Override
	@SystemServiceLog(method = "findBannerPic", description = "获取首页轮播图列表 ")
	public ResultBean findBannerPic(int page, int pageSize, String desc) {
		// 构建返回数据实体
		Map<String, Object> data = new HashMap<>();

		// 创建入参模型
		Map<String, Object> param = new HashMap<>();
		param.put("start", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		param.put("desc", desc);
		// 获取总条数
		int count = bannerAdminMapper.findBannerPicCount();
		// 添加总条数
		data.put("count", count);
		if (count == 0 || count < (page - 1) * pageSize) {
			// 没有数据，返回一个空的list给前段
			data.put("bannerPicList", new ArrayList<>());
			return new ResultBean();
		}

		// 获取轮播图列表
		List<Banner> bannerPicList = bannerAdminMapper.findBannerPicList(param);

		// 整体图片返回路径
		for (Banner b : bannerPicList) {
			b.setPicAddr(PropertyUtil.getProperty("domain") + b.getPicAddr());
		}
		// 将列表加入到返回容器中
		data.put("bannerPicList", bannerPicList);
		// 成功返回
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc) <p>Title: findBannerPic</p> <p>Description: 添加首页轮播图列表</p>
	 * 
	 * @param title 标题
	 * 
	 * @param content 内容
	 * 
	 * @param pic 图片
	 * 
	 * @param desc 描述
	 * 
	 * @param userid 用户ID
	 * 
	 * @return link: @see
	 * dianfan.service.content.BannerAdminService#findBannerPic(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "findBannerPic", description = "添加首页轮播图列表 ")
	public ResultBean addBannerPic(String title, String content, String pic, String desc, String userid, String sort)
			throws IOException {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("title", title);
		param.put("content", content);
		param.put("desc", desc);
		param.put("userid", userid);
		param.put("sort", sort);
		// 添加图片路径 ,后台的图片上传不需要
		param.put("pic", pic);

		// 插入操作
		bannerAdminMapper.addBannerPic(param);
		// 成功
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: editBannerPic</p> <p>Description: 修改首页轮播图列表</p>
	 * 
	 * @param id 轮播图的ID
	 * 
	 * @param title 标题
	 * 
	 * @param content 内容
	 * 
	 * @param pic 图片
	 * 
	 * @param desc 描述
	 * 
	 * @param userid 用户ID
	 * 
	 * @return link: @see
	 * dianfan.service.content.BannerAdminService#editBannerPic(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updataBannerPic", description = "修改首页轮播图列表 ")
	public ResultBean updataBannerPic(String id, String title, String content, String pic, String desc, String userid,
			String sort) throws IOException {
		Map<String, Object> param = new HashMap<>();
		// 修改图片的路径
		int indexOf = pic.indexOf("upload");
		String spic = pic.substring(indexOf);

		// 容器内添加图片信息
		param.put("pic", spic);
		param.put("id", id);
		param.put("title", title);
		param.put("content", content);
		param.put("desc", desc);
		param.put("userid", userid);
		param.put("sort", sort);

		bannerAdminMapper.updataBannerPic(param);
		// 成功
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: delBannerPic</p> <p>Description: 修改轮播图</p>
	 * 
	 * @param userid
	 * 
	 * @param ids
	 * 
	 * @return link: @see
	 * dianfan.service.content.BannerAdminService#delBannerPic(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updataBannerPic", description = "删除轮播图 ")
	public ResultBean delBannerPic(String userid, String ids, String type) {
		// ids整理
		List<String> idList = Arrays.asList(ids.split(","));
		// 创建入参模型
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("idList", idList);
		param.put("type", type);
		// 修改操作
		bannerAdminMapper.delBannerPic(param);
		// 返回
		return new ResultBean();
	}

}
