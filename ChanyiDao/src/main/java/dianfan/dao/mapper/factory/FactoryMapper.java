/**  
* @Title: FactoryMapper.java
* @Package dianfan.dao.mapper.factory
* @Description: TODO
* @author yl
* @date 2018年7月17日 下午1:17:55
* @version V1.0  
*/ 
package dianfan.dao.mapper.factory;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.factory.FactoryAdminRelate;
import dianfan.entities.factory.FactoryArea;
import dianfan.entities.factory.FactoryInfo;

/** @ClassName FactoryMapper
 * @Description 
 * @author yl
 * @date 2018年7月17日 下午1:17:55
 */
@Repository
public interface FactoryMapper {
	
	/**
	 * @Title: getFactoryNum
	 * @Description: 总条数
	 * @param areacode 区域code
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午1:49:29
	 */
	int getFactoryNum(@Param("areacode") String areacode);
	
	/**
	 * @Title: findFactoryList
	 * @Description: 获取工厂列表
	 * @param params
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午1:57:06
	 */
	List<FactoryInfo> findFactoryList(Map<String, Object> params);
	/**
	 * @Title: addFactory
	 * @Description: 添加工厂
	 * @param finfo
	 * @throws:
	 * @time: 2018年7月17日 下午3:15:50
	 */
	@Insert("insert into t_factory_list (id,factory_name,longitude,latitude,factory_addr,create_time,create_by,entkbn) value (#{id},#{factoryName},#{longitude},#{latitude},#{factoryAddr},now(),#{createBy},0)")
	void addFactory(FactoryInfo finfo);
	
	/**
	 * @Title: checkFactoryArea
	 * @Description: 检测工厂-区域关系
	 * @param areaCodes
	 * @param action 0添加，1修改
	 * @return
	 * @throws:
	 * @time: 2018年8月8日 下午2:31:01
	 * @author cjy
	 */
	boolean checkFactoryArea(@Param("areaCodes")String[] areaCodes, @Param("factoryid")String factoryid);
	
	/**
	 * @Title: addFactoryArea
	 * @Description: 添加工厂区域
	 * @param fa
	 * @throws:
	 * @time: 2018年7月17日 下午3:15:50
	 */
	void addFactoryArea(FactoryArea fa);
	
	/**
	 * @Title: bindAdminFactoryRealtion
	 * @Description: 绑定管理员工厂关系
	 * @param factoryid  工厂id
	 * @param adminids 管理员ids
	 * @throws:
	 * @time: 2018年8月8日 上午11:13:22
	 * @author cjy
	 */
	@Delete("delete from t_factory_admin_relate where factory_id=#{factoryid}")
	void unbindAdminFactoryRealtion(String factoryid);
	
	/**
	 * @Title: checkBindAdminFactoryRealtion
	 * @Description: 检测绑定管理员工厂关系
	 * @param factoryid 工厂id
	 * @param adminids 管理员ids
	 * @return
	 * @throws:
	 * @time: 2018年8月9日 上午10:44:02
	 * @author cjy
	 */
	boolean checkBindAdminFactoryRealtion(@Param("factoryid")String factoryid, @Param("adminids")String[] adminids);
	
	/**
	 * @Title: bindAdminFactoryRealtion
	 * @Description: 绑定管理员工厂关系
	 * @param factoryid  工厂id
	 * @param adminids 管理员ids
	 * @throws:
	 * @time: 2018年8月8日 上午11:13:22
	 * @author cjy
	 */
	void bindAdminFactoryRealtion(@Param("factoryid")String factoryid, @Param("adminids")String[] adminids);
	
	/**
	 * @Title: getVersion
	 * @Description: 获取工厂版本
	 * @param id 工厂id
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:48:03
	 */
	@Select("select version from t_factory_list where id=#{id}")
	String getFactoryVersion(String id);
	/**
	 * @Title: findFactoryVersion
	 * @Description: 
	 * @param ids 工厂id list
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午9:52:08
	 */
	List<FactoryInfo> findFactoryVersion(@Param("ids") String[] ids);
	/**
	 * @Title: getVersion
	 * @Description: 获取工厂区域版本
	 * @param id 工厂id
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:48:03
	 */
	@Select("select version from t_factory_list_area_list where factory_id=#{id}")
	String getFactoryAreaVersion(String id);
	/**
	 * @Title: findFactoryVersion
	 * @Description: 
	 * @param ids 工厂id list
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 上午9:52:08
	 */
	List<FactoryArea> findFactoryAreaVersion(@Param("ids") String[] ids);
	/**
	 * @Title: updateFactory
	 * @Description: 修改工厂信息
	 * @param finfo
	 * @throws:
	 * @time: 2018年7月17日 下午5:02:13
	 */
	void updateFactory(FactoryInfo finfo);
	/**
	 * @Title: updateFactory
	 * @Description: 修改工厂信息
	 * @param finfo
	 * @throws:
	 * @time: 2018年7月17日 下午5:02:13
	 */
	void updateFactoryArea(FactoryArea fa);
	/**
	 * @Title: delFactory
	 * @Description:  批量删除工厂
	 * @param factoryid 工厂id
	 * @throws:
	 * @time: 2018年7月18日 上午9:16:02
	 */
	void delFactory(Map<String, Object> params);
	/**
	 * @Title: delFactoryArea
	 * @Description: 删除工厂区域
	 * @param factoryid 工厂id
	 * @throws:
	 * @time: 2018年7月18日 上午9:18:12
	 */
	void delFactoryArea(Map<String, Object> params);
	/**
	 * @Title: deleteFactoryArea
	 * @Description: 删除工厂区域(物理删除)
	 * @param params
	 * @throws:
	 * @time: 2018年8月7日 下午4:57:27
	 */
	void deleteFactoryArea(String factoryid);
	
	/**
	 * @Title: addFactoryAdminRelate
	 * @Description: 
	 * @param far
	 * @throws:
	 * @time: 2018年7月18日 下午6:08:16
	 */
	@Insert("insert into t_factory_admin_relate (id,admin_id,factory_id) values (replace(uuid(),'-',''),#{adminId},#{factoryId})")
	void addFactoryAdminRelate(FactoryAdminRelate far);
	/**
	 * @Title: getFactoryAdminRelateNum
	 * @Description: 
	 * @param far
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午6:15:54
	 */
	@Select("select count(*) from t_factory_admin_relate where factory_id=#{factoryId} and admin_id=#{adminId}")
	int getFactoryAdminRelateNum(FactoryAdminRelate far);
	
	/**
	 * @Title: getFactoryName
	 * @Description: 
	 * @param factoryid
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午4:40:38
	 */
	@Select("select factory_name factoryName from t_factory_list where id=#{factoryid}")
	String getFactoryName(String factoryid);
	
	/**
	 * @Title: deleteFactoryAdminRelate
	 * @Description: 工厂管理相关表
	 * @param params
	 * @throws:
	 * @time: 2018年7月19日 上午9:12:15
	 */
	void deleteFactoryAdminRelate(Map<String, Object> params);
	

}
