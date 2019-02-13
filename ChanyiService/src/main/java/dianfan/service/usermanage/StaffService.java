package dianfan.service.usermanage;

import java.util.List;

import dianfan.entities.UserInfo;
import dianfan.entities.user.StaffExtra;
import dianfan.models.ResultBean;

/**
 * @ClassName StaffServiceManage
 * @Description 易盟员工服务
 * @author cjy
 * @date 2018年7月25日 上午11:02:39
 */
public interface StaffService {

	/**
	 * @Title: findStaffList
	 * @Description: 易盟员工列表
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 上午11:19:04
	 * @author cjy
	 */
	ResultBean findStaffList(int page, int pageSize, String name, String telno, String role, String areacode, String regionscode);

	/**
	 * @Title: getStaffDetail
	 * @Description: 易盟员工详情
	 * @param staffid 易盟员工id
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 上午11:11:20
	 * @author cjy
	 */
	ResultBean getStaffDetail(String staffid);
	
	/**
	 * @Title: addStaff
	 * @Description: 添加易盟员工
	 * @param user 员工数据
	 * @param se 员工扩展数据
	 * @param role_tag 角色标记
	 * @param regionid 大区id
	 * @return
	 * @throws:
	 * @time: 2018年7月25日 下午2:31:44
	 * @author cjy
	 */
	void addStaff(UserInfo user, StaffExtra se, String role_tag, String regionid);
	
	/**
	 * @Title: editStaff
	 * @Description: 修改易盟员工
	 * @param user 员工数据
	 * @param se 员工扩展数据
	 * @param role_tag 角色标记
	 * @param regionid 大区id
	 * @throws:
	 * @time: 2018年7月27日 上午11:15:42
	 * @author cjy
	 */
	void editStaff(UserInfo user, StaffExtra se, String role_tag, String regionid);

	/**
	 * @Title: updateFreezeStaff
	 * @Description: 冻结/解冻 易盟员工
	 * @param staffid 员工id
	 * @param action 动作（0解冻，1冻结）
	 * @param operater 操作者
	 * @throws:
	 * @time: 2018年7月27日 上午9:43:26
	 * @author cjy
	 */
	void updateFreezeStaff(String staffid, int action, String operater);

	/**
	 * @Title: migrateStaff
	 * @Description: 迁移员工
	 * @param inStaffid 数据流入员工id
	 * @param outStaffid 数据流出员工id
	 * @param roleid 角色id
	 * @param resaon 迁移原因
	 * @param operater 操作者
	 * @throws:
	 * @time: 2018年7月27日 上午10:12:13
	 * @author cjy
	 */
	void migrateStaff(String inStaffid, String outStaffid, String roleid,String resaon, String operater);

	/**
	 * @Title: migrateStaffList
	 * @Description: 迁移员工列表
	 * @return
	 * @throws:
	 * @time: 2018年7月27日 下午4:37:41
	 * @author cjy
	 */
	List<UserInfo> migrateStaffList(String staffid, String roleid);

	

	

}
