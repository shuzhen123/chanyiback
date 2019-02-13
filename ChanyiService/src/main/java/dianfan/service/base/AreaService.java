package dianfan.service.base;

import java.util.List;

import dianfan.entities.base.Area;

/**
 * @ClassName AreaService
 * @Description 城市地区服务
 * @author cjy
 * @date 2018年6月30日 下午3:34:35
 */
public interface AreaService {

	/**
	 * @Title: findAllArea
	 * @Description: 获取全部可用城市
	 * @return List<Area>城市列表
	 * @throws:
	 * @time: 2018年6月30日 下午3:43:36
	 */
	List<Area> findAllArea();
}
