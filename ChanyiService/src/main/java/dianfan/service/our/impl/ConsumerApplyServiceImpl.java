/**  
* @Title: ConsumerApplyServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午1:27:36
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

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.our.ConsumerApplyMapper;
import dianfan.entities.our.ConsumerApplyModel;
import dianfan.entities.role.Role;
import dianfan.entities.role.UserRole;
import dianfan.models.ResultBean;
import dianfan.service.our.ConsumerApplyService;
import dianfan.util.PropertyUtil;

/** @ClassName ConsumerApplyServiceImpl
 * @Description 
 * @author yl
 * @date 2018年6月30日 下午1:27:36
 */
@Service
public class ConsumerApplyServiceImpl implements ConsumerApplyService{
	
	@Autowired
	private ConsumerApplyMapper consumerApplyMapper;

	/* (non-Javadoc)
	 * <p>Title: addConsumerApply</p>
	 * <p>Description: 申请成为消费商</p>
	 * @param userid 用户id
	 * link: @see dianfan.service.our.ConsumerApplyService#addConsumerApply(java.lang.String)
	 */ 
	@Override
	@Transactional
	public void addConsumerApply(String userid,String applystatus,String roledistinguish) {
		// TODO Auto-generated method stub
		ConsumerApplyModel cam = new ConsumerApplyModel();
		UserRole ur = new UserRole();
		cam.setApplyUserId(userid);
		cam.setStatus(applystatus);
		if (ConstantIF.APPLY_STATUS1.equals(applystatus)) {
			Role r = consumerApplyMapper.getUserRole(roledistinguish);
			ur.setUserid(userid);
			ur.setRoleid(r.getId());
			ur.setDescption(r.getRoleName());
			consumerApplyMapper.updateConsumerApplyRole(ur);
		}	
		cam.setCreateBy(userid);
		consumerApplyMapper.addConsumerApply(cam);
		
	}

	/* (non-Javadoc)
	 * <p>Title: getConsumerApply</p>
	 * <p>Description: 判断是否已经是消费商</p>
	 * @param userid 用户id
	 * @return
	 * link: @see dianfan.service.our.ConsumerApplyService#getConsumerApply(java.lang.String)
	 */ 
	@Override
	public int getConsumerApply(String userid) {
		// TODO Auto-generated method stub
		return consumerApplyMapper.getConsumerApply(userid);
	}

	/* (non-Javadoc)
	 * <p>Title: findConsumerList</p>
	 * <p>Description: </p>
	 * @param status
	 * @param nickName
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @return
	 * link: @see dianfan.service.our.ConsumerApplyService#findConsumerList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	public ResultBean findConsumerList(String status, String nickName, String createTimeStart, String createTimeEnd,int page,int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		ConsumerApplyModel cam = new ConsumerApplyModel();
		cam.setStatus(status);
		cam.setNickName(nickName);
		cam.setCreateTimeStart(createTimeStart);
		cam.setCreateTimeEnd(createTimeEnd);
		int count = consumerApplyMapper.getConsumerNum(cam);
		// 设置总页数
		data.put("totalcount", count);
		if (count < (page - 1) * pageSize || count == 0) {
			// 空的返回实体
			data.put("consumerlist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		cam.setStart((page - 1) * pageSize);
		// 分页偏移量 10
		cam.setOffset(pageSize);
		List<ConsumerApplyModel> camlist = consumerApplyMapper.findConsumerList(cam);
		if (camlist !=null && camlist.size()>0) {
			for (int i = 0; i < camlist.size(); i++) {
				camlist.get(i).setAvatarUrl(PropertyUtil.getProperty("domain")+camlist.get(i).getAvatarUrl());
			}
		}
		data.put("consumerlist", camlist);
		return new ResultBean(data);
	}

	/* (non-Javadoc)
	 * <p>Title: updateConsumerApply</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param applystatus
	 * @param fReason
	 * @param roledistinguish
	 * link: @see dianfan.service.our.ConsumerApplyService#updateConsumerApply(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateConsumerApply(String userid,String consumerid,String applyUserId,String applystatus, String fReason, String roledistinguish) {
		// TODO Auto-generated method stub
		ConsumerApplyModel cam = new ConsumerApplyModel();
		String version = consumerApplyMapper.getConsumerVersion(consumerid);
		UserRole ur = new UserRole();
		cam.setId(consumerid);
		cam.setApplyUserId(applyUserId);
		cam.setStatus(applystatus);
		if (ConstantIF.APPLY_STATUS1.equals(applystatus)) {
			Role r = consumerApplyMapper.getUserRole(roledistinguish);
			ur.setUserid(applyUserId);
			ur.setRoleid(r.getId());
			ur.setDescption(r.getRoleName());
			consumerApplyMapper.updateConsumerApplyRole(ur);
		}else {
			cam.setfReason(fReason);
		}
		cam.setVersion(Integer.valueOf(version));
		cam.setUpdateBy(userid);
		consumerApplyMapper.updateConsumerApply(cam);
		
	}

}
