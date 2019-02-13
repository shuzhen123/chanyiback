/**  
* @Title: SupplierApplyMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午5:52:34
* @version V1.0  
*/
package dianfan.dao.mapper.our;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.our.SupplierApply;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;

/**
 * @ClassName SupplierApplyMapper
 * @Description
 * @author yl
 * @date 2018年6月29日 下午5:52:34
 */
@Repository
public interface SupplierApplyMapper {

	/**
	 * @Title: addSupplierApply
	 * @Description: 申请成功供应商
	 * @param sa
	 * @throws:
	 * @time: 2018年6月30日 上午10:04:00
	 */
	void addSupplierApply(SupplierApply sa);

	/**
	 * @Title: updateSupplierApply
	 * @Description: 修改供应商信息
	 * @param sa
	 * @throws:
	 * @time: 2018年7月11日 下午4:21:44
	 */
	void updateSupplierApply(SupplierApply sa);

	/**
	 * @Title: updateSupplierApplyStatus
	 * @Description:
	 * @param sa
	 * @throws:
	 * @time: 2018年7月23日 下午5:36:47
	 */
	void updateSupplierApplyStatus(SupplierApply sa);

	/**
	 * @Title: getSupplierApply
	 * @Description: 是否已经是供应商
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 上午10:04:17
	 */
	int getSupplierApply(String userid);

	/**
	 * @Title: getSupplierApply
	 * @Description: 判断手机号是否存在
	 * @param phonenum
	 *            手机号码
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 上午10:04:17
	 */
	int getSupplierApplyByPhone(String phonenum);

	/**
	 * @Title: getUserRole
	 * @Description:
	 * @param roledistinguish
	 *            权限有效区分
	 * @return Role
	 * @throws:
	 * @time: 2018年7月3日 下午6:15:02
	 */
	Role getUserRole(String roledistinguish);

	/**
	 * @Title: getUserRole
	 * @Description:
	 * @param roledistinguish
	 *            权限id
	 * @return Role
	 * @throws:
	 * @time: 2018年7月3日 下午6:15:02
	 */
	Role getUserRole2(String id);

	/**
	 * @Title: updateSupplierApplyRole
	 * @Description:
	 * @param cam
	 * @throws:
	 * @time: 2018年7月3日 下午7:19:16
	 */
	void updateSupplierApplyRole(UserRole cam);

	/**
	 * @Title: getVersionInfo
	 * @Description: 获取当前版本信息
	 * @param userid
	 *            用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月13日 下午3:12:31
	 */
	@Select("select version from t_supplier_apply where user_id=#{userid}")
	int getVersionInfo(String userid);

	/**
	 * @Title: getSupplierNum
	 * @Description: 根据条件获取供应商人数
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午10:49:47
	 */
	int getSupplierNum(SupplierApply sa);

	/**
	 * @Title: findSupplierList
	 * @Description: 根据条件获取供应商列表
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午10:51:08
	 */
	List<SupplierApply> findSupplierList(SupplierApply sa);

}
