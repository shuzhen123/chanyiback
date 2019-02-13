package dianfan.dao.mapper.banner;

import java.util.List;

import org.springframework.stereotype.Repository;

import dianfan.entities.banner.Banner;
import dianfan.entities.banner.BannerModel;


/** @ClassName BannerMapper
 * @Description 轮播图dao
 * @author zwb
 * @date 2018年6月28日 下午1:52:26
 */ 
@Repository
public interface BannerMapper {
	
	/** @Title: findBanners
	 * @Description: 查询banner列表
	 * @return List<Banner> 轮播图列表
	 * @throws:
	 * @time: 2018年6月28日 下午2:05:54
	 */ 
	List<BannerModel> findBanners();
	
	
}
