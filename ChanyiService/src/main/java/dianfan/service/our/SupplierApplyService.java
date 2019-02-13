/**  
* @Title: SupplierApplyService.java
* @Package dianfan.service.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午5:47:58
* @version V1.0  
*/ 
package dianfan.service.our;

import dianfan.models.ResultBean;

/** @ClassName SupplierApplyService
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午5:47:58
 */
public interface SupplierApplyService {
	
	/**
	 * @Title: addSupplierApply
	 * @Description: 
	 * @param userid 用户id 申请通过后拥有的分配账号的id
	 * @param companyname 公司名称
	 * @param registeredcapitalmoney 注册资金
	 * @param supplycategory 供应品类
	 * @param cooperationcase 合作案例
	 * @param contacts 联系人
	 * @param contactsphone 联系电话
	 * @param email 电子邮箱
	 * @throws:
	 * @time: 2018年6月29日 下午5:48:11
	 */
	void addSupplierApply(String userid,String companyname,String registeredcapitalmoney,String supplycategory,String cooperationcase,String contacts,String contactsphone,String email,String status);
	/**
	 * @Title: updateSupplierApply
	 	 * @Description: 
	 * @param userid 用户id 申请通过后拥有的分配账号的id
	 * @param companyname 公司名称
	 * @param registeredcapitalmoney 注册资金
	 * @param supplycategory 供应品类
	 * @param cooperationcase 合作案例
	 * @param contacts 联系人
	 * @param contactsphone 联系电话
	 * @param email 电子邮箱
	 * @throws:
	 * @time: 2018年7月11日 下午4:18:43
	 */
	void updateSupplierApply(String userid,String companyname,String registeredcapitalmoney,String supplycategory,String cooperationcase,String contacts,String contactsphone,String email);
	/**
	 * @Title: updateSupplierApplyStatus
	 * @Description: 供应商申请审批
	 * @param userid
	 * @param supplierid
	 * @param applyUserId
	 * @param applystatus
	 * @param fReason
	 * @param roledistinguish
	 * @throws:
	 * @time: 2018年7月23日 下午5:27:07
	 */
	void updateSupplierApplyStatus(String userid,String supplierid,String applyUserId,String applystatus,String fReason);
	
	/**
	 * @Title: getSupplierApply
	 * @Description: 是否已经是供应商
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 上午10:05:37
	 */
	int getSupplierApply(String userid);
	
	/**
	 * @Title: getSupplierApplyByPhone
	 * @Description: 验证手机号是否存在
	 * @param phonenum 手机号码
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 下午4:17:52
	 */
	int getSupplierApplyByPhone(String phonenum);
	/**
	 * @Title: findSupplierList 
	 * @Description:  根据条件获取供应商列表
	 * @param status 状态
	 * @param contacts 联系人
	 * @param companyName 公司名称
	 * @param registeredCapitalMoney 注册资金
	 * @param supplyCategory 品类
	 * @param createTimeStart 创建时间start
	 * @param createTimeEnd  创建时间end
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午2:05:56
	 */
	ResultBean findSupplierList(String status,String contacts,String companyName,String supplyCategory,String createTimeStart,String createTimeEnd,int page,int pageSize);


}
