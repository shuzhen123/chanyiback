package dianfan.service.base;

import dianfan.models.ResultBean;

/**
 * @ClassName RegionService
 * @Description 区域管理相关 后台接口
 * @author sz
 * @date 2018年7月26日 上午10:45:29
 */
public interface RegionService {

	/**
	 * @Title: findRegionList
	 * @Description: 获取大区列表
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:10:34
	 */
	ResultBean findRegionList();

	/**
	 * @Title: updataRegionList
	 * @Description: 更新大区和省关系
	 * @param regionid 大区id
	 * @param codeids 省 ids
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:46:50
	 */
	ResultBean updataRegionList(String regionid, String codeids);

	
	
	
}
