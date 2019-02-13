package dianfan.dao.mapper.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.base.Regionss;

/**
 * @ClassName RegionMapper
 * @Description 区域管理相关
 * @author sz
 * @date 2018年7月26日 上午10:48:44
 */
@Repository
public interface RegionMapper {

	/**
	 * @Title: findRegionList
	 * @Description: 获取大区列表数量
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:12:35
	 */
	@Select(" SELECT COUNT(*) FROM m_region_name WHERE entkbn = 0 ")
	int findRegionListCount();

	/**
	 * @Title: findRegionList
	 * @Description:  获取大区列表
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:19:45
	 */
	List<Regionss> findRegionList();

	/**
	 * @Title: updataRegion
	 * @Description: 修改大区和省相关
	 * @param param
	 * @throws:
	 * @time: 2018年7月26日 上午11:53:29
	 */
	void updataRegion(Map<String, Object> param);

	/**
	 * @Title: emptyRegion
	 * @Description: 置空原来的大区id
	 * @param regionid
	 * @throws:
	 * @time: 2018年7月26日 下午2:35:18
	 */
	@Update(" UPDATE m_area_code SET region_id = null WHERE region_id = #{regionid} ")
	void emptyRegion(String regionid);

}
