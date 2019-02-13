package dianfan.service.banner.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.banner.BannerMapper;
import dianfan.entities.banner.BannerModel;
import dianfan.service.banner.BannerService;
import dianfan.util.PropertyUtil;

/** @ClassName BannerServiceImpl
 * @Description 轮播图业务层实现类
 * @author zwb
 * @date 2018年6月28日 下午2:11:55
 */ 
@Service
public class BannerServiceImpl implements BannerService{

	/**
	 * 轮播图相关dao
	 */
	@Autowired
	private BannerMapper bannerMapper;
	
	/* (non-Javadoc)
	 * <p>Title: findBanners</p>
	 * <p>Description: 查询轮播图列表</p>
	 * @return List<Banner> 轮播图列表
	 * link: @see dianfan.service.banner.BannerService#findBanners()
	 */ 
	@Override
	public List<BannerModel> findBanners() {
		//查询出banner图信息
		List<BannerModel> banners = bannerMapper.findBanners();
		for(BannerModel b : banners) {
			//修改图片的url
			b.setPicAddr(PropertyUtil.getProperty("domain") + b.getPicAddr());
		}
		return banners;
	}
	
	

}
