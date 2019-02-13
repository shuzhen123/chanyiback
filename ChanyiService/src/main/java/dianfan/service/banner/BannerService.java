package dianfan.service.banner;

import java.util.List;

import dianfan.entities.banner.Banner;
import dianfan.entities.banner.BannerModel;


/** @ClassName BannerService
 * @Description 轮播图接口
 * @author zwb
 * @date 2018年6月28日 下午2:09:19
 */ 
public interface BannerService {
	
	/** @Title: findBanners
	 * @Description: 查询轮播图列表
	 * @return List<Banner> 轮播图列表
	 * @throws:
	 * @time: 2018年6月28日 下午2:10:06
	 */ 
	List<BannerModel> findBanners();

}
