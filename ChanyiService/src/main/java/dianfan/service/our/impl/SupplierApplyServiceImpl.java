/**  
* @Title: SupplierApplyServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午5:50:30
* @version V1.0  
*/ 
package dianfan.service.our.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.our.SupplierApplyMapper;
import dianfan.entities.our.SupplierApply;
import dianfan.models.ResultBean;
import dianfan.service.our.SupplierApplyService;

/** @ClassName SupplierApplyServiceImpl
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午5:50:30
 */
@Service
public class SupplierApplyServiceImpl implements SupplierApplyService{
	
	@Autowired
	private SupplierApplyMapper supplierApplyMapper;

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
	@Override
	@Transactional
	public void addSupplierApply(String userid, String companyname, String registeredcapitalmoney,
			String supplycategory, String cooperationcase, String contacts, String contactsphone, String email,String status) {
		// TODO Auto-generated method stub
		SupplierApply sa = new SupplierApply();
		sa.setUserId(userid);
		sa.setCompanyName(companyname);
		sa.setRegisteredCapitalMoney(registeredcapitalmoney);
		sa.setSupplyCategory(supplycategory);
		sa.setCooperationCase(cooperationcase);
		sa.setContacts(contacts);
		sa.setContactsPhone(contactsphone);
		sa.seteMail(email);
		sa.setStatus(status);
		sa.setCreateBy(userid);
		supplierApplyMapper.addSupplierApply(sa);
	}

	/* (non-Javadoc)
	 * <p>Title: getSupplierApply</p>
	 * <p>Description: 是否已经是供应商</p>
	 * @param userid
	 * @return 
	 * link: @see dianfan.service.our.SupplierApplyService#getSupplierApply(java.lang.String)
	 */ 
	@Override
	public int getSupplierApply(String userid) {
		// TODO Auto-generated method stub
		return supplierApplyMapper.getSupplierApply(userid);
	}

	/* (non-Javadoc)
	 * <p>Title: getSupplierApplyByPhone</p>
	 * <p>Description: </p>
	 * @param phonenum
	 * @return
	 * link: @see dianfan.service.our.SupplierApplyService#getSupplierApplyByPhone(java.lang.String)
	 */ 
	@Override
	public int getSupplierApplyByPhone(String phonenum) {
		// TODO Auto-generated method stub
		return supplierApplyMapper.getSupplierApplyByPhone(phonenum);
	}

	/* (non-Javadoc)
	 * <p>Title: updateSupplierApply</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param companyname
	 * @param registeredcapitalmoney
	 * @param supplycategory
	 * @param cooperationcase
	 * @param contacts
	 * @param contactsphone
	 * @param email
	 * link: @see dianfan.service.our.SupplierApplyService#updateSupplierApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateSupplierApply(String userid, String companyname, String registeredcapitalmoney,
			String supplycategory, String cooperationcase, String contacts, String contactsphone, String email) {
		// TODO Auto-generated method stub
		int versionInfo = supplierApplyMapper.getVersionInfo(userid);
		SupplierApply sa = new SupplierApply();
		sa.setUserId(userid);
		sa.setCompanyName(companyname);
		sa.setRegisteredCapitalMoney(registeredcapitalmoney);
		sa.setSupplyCategory(supplycategory);
		sa.setCooperationCase(cooperationcase);
		sa.setContacts(contacts);
		sa.setContactsPhone(contactsphone);
		sa.seteMail(email);
		sa.setUpdateBy(userid);
		sa.setVersion(versionInfo);
		supplierApplyMapper.updateSupplierApply(sa);
	}

	/* (non-Javadoc)
	 * <p>Title: findSupplierList</p>
	 * <p>Description: </p>
	 * @param status
	 * @param contacts
	 * @param companyName
	 * @param registeredCapitalMoney
	 * @param supplyCategory
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param page
	 * @param pageSize
	 * @return
	 * link: @see dianfan.service.our.SupplierApplyService#findSupplierList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */ 
	@Override
	public ResultBean findSupplierList(String status, String contacts, String companyName,String supplyCategory, String createTimeStart, String createTimeEnd,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		SupplierApply sa = new SupplierApply();
		sa.setStatus(status);
		sa.setContacts(contacts);
		sa.setCompanyName(companyName);
		//sa.setRegisteredCapitalMoney(registeredCapitalMoney);
		sa.setSupplyCategory(supplyCategory);
		sa.setCreateTimeStart(createTimeStart);
		sa.setCreateTimeEnd(createTimeEnd);
		int count = supplierApplyMapper.getSupplierNum(sa);
		data.put("totalcount", count);
		if (count < (page - 1) * pageSize || count == 0) {
			// 空的返回实体
			data.put("salist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		sa.setStart((page - 1) * pageSize);
		// 分页偏移量 10
		sa.setOffset(pageSize);
		List<SupplierApply> salist = supplierApplyMapper.findSupplierList(sa);
		data.put("salist", salist);
		return new ResultBean(data);
	}

	/* (non-Javadoc)
	 * <p>Title: updateSupplierApplyStatus</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param supplierid
	 * @param applyUserId
	 * @param applystatus
	 * @param fReason
	 * @param roledistinguish
	 * link: @see dianfan.service.our.SupplierApplyService#updateSupplierApplyStatus(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateSupplierApplyStatus(String userid, String supplierid, String applyUserId, String applystatus,String fReason) {
		// TODO Auto-generated method stub
		SupplierApply sa = new SupplierApply();
		sa.setId(supplierid);
		sa.setUpdateBy(userid);
		sa.setfReason(fReason);
		sa.setStatus(applystatus);
		sa.setUserId(applyUserId);
		supplierApplyMapper.updateSupplierApplyStatus(sa);
	}

}
