package dianfan.dao.mapper.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.base.Area;

/**
 * @ClassName AreaMapper
 * @Description 城市信息dao
 * @author cjy
 * @date 2018年6月30日 下午3:42:39
 */
@Repository
public interface AreaMapper {

	/**
	 * @Title: findAllArea
	 * @Description: 获取全部可用城市
	 * @return List<Area>城市列表
	 * @throws:
	 * @time: 2018年6月30日 下午3:44:40
	 */
	List<Area> findAllArea();

	/**
	 * @Title: findChiledCityInfo
	 * @Description: 根据父级code获取子地区列表
	 * @param sub_code 父级code
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午7:11:49
	 */
	@Select("select area_code areaCode, area_name areaName from m_area_code where sup_area_code=#{sub_code}")
	List<Area> findChiledCityBySubCityCode(String sub_code);

	/**
	 * @Title: findChiledCityBySubCityName
	 * @Description: 根据父级城市名称获取子地区列表
	 * @param city_name 城市名称
	 * @return
	 * @throws:
	 * @time: 2018年7月7日 上午10:32:57
	 */
	@Select("select area_code areaCode, area_name areaName from m_area_code where sup_area_code="
			+ "(select area_code from m_area_code where area_name=#{city_name} and sup_area_code is not null)")
	List<Area> findChiledCityBySubCityName(String city_name);

	/**
	 * @Title: getAddrByCode
	 * @Description: 根据地区code获取省市区
	 * @param cityCode 地区code
	 * @param level 1省，2市，3区县
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午11:09:15
	 * @author cjy 
	 */
	String getAddrByCode(@Param("cityCode")String cityCode, @Param("level")int level);
	
	/**
	 * @Title: getStaffRegionName
	 * @Description: 根据用户id获取所在大区
	 * @param staffid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月28日 上午11:33:04
	 * @author cjy
	 */
	@Select("select regname.name from t_user_regions reg,m_region_name regname where reg.region_id=regname.id and reg.user_id=#{staffid}")
	String getStaffRegionName(String staffid);

	/**
	 * @Title: findCityCodeByProvinceCode
	 * @Description: 根据省级code获取区县级code
	 * @param cityCode 省级code
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午4:24:48
	 * @author cjy
	 */
	@Select("select L3.area_code from m_area_code L1,m_area_code L2,m_area_code L3 "+
			"where L1.area_code=L2.sup_area_code and L2.area_code=L3.sup_area_code and L1.area_code=#{cityCode}")
	List<String> findCityCodeByProvinceCode(String cityCode);

	/**
	 * @Title: findCityCodeByCityCode
	 * @Description: 根据市级code获取区县级code
	 * @param cityCode 市级code
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午4:25:19
	 * @author cjy
	 */
	@Select("select L3.area_code, L3.area_name from m_area_code L2,m_area_code L3" +
			"where L2.area_code=L3.sup_area_code and L2.area_code=#{cityCode}")
	List<String> findCityCodeByCityCode(String cityCode);

	/**
	 * @Title: findCityInfo
	 * @Description: 获取城市信息
	 * @param area 城市信息
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 上午9:34:17
	 * @author cjy
	 */
	Area findCityInfo(Area area);
}
