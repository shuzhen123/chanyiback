package dianfan.service.base.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ResultApiMsg;
import dianfan.dao.mapper.base.ConsumerRelationMapper;
import dianfan.entities.role.UserRole;
import dianfan.models.ResultBean;
import dianfan.service.base.ConsumerRelationService;

/**
 * @ClassName ConsumerRelationImpl
 * @Description 消费关系服务
 * @author cjy
 * @date 2018年7月3日 上午9:44:33
 */
@Service
public class ConsumerRelationImpl implements ConsumerRelationService {

	/**
	 * 注入： #ConsumerRelationMapper
	 */
	@Autowired
	private ConsumerRelationMapper consumerRelationMapper;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: bindConsumerRelation</p>
	 * <p>Description: 绑定消费关系</p>
	 * @param userid 用户id
	 * @param qr_num 对方二维码数据
	 * link: @see dianfan.service.base.ConsumerRelationService#bindConsumerRelation(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public void bindConsumerRelation(String userid, String qr_num) {
		//根据qr_num获取角色区分
		String roleDistinguish = consumerRelationMapper.getRoleDistinguish(qr_num, userid);
		/*
		 * 05 体验店
		 * 06 导购
		 * 07 消费商
		 */
		if(StringUtils.equals("05", roleDistinguish) || 
			StringUtils.equals("06", roleDistinguish) ||
			StringUtils.equals("07", roleDistinguish)) {
			//可绑定消费关系
			consumerRelationMapper.updateBindRelation(userid, qr_num, roleDistinguish);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: bindUpLowRelation</p>
	 * <p>Description: 绑定上下级关系</p>
	 * @param userid
	 * @param qr_num
	 * @return
	 * @author cjy
	 * link: @see dianfan.service.base.ConsumerRelationService#bindUpLowRelation(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public ResultBean bindUpLowRelation(String userid, String qr_num) {
		//根据qr_num获取用户角色信息
		UserRole ur = consumerRelationMapper.getRoleDistinguishByQrnum(qr_num);
		
		//获取用户角色
		String role = consumerRelationMapper.getUserRole(userid);
		if((StringUtils.equals("05", ur.getDescption()) && StringUtils.equals("08", role)) ||
				(StringUtils.equals("05", ur.getDescption()) && StringUtils.equals("07", role))) {
			//普通人/消费商 扫体验店码，成为导购
			consumerRelationMapper.bindRelation(userid, ur.getUserid());
			//更新角色
			consumerRelationMapper.updateRole(userid);
			return new ResultBean();
		}else if((StringUtils.equals("02", ur.getDescption()) && StringUtils.equals("08", role)) ||
				(StringUtils.equals("03", ur.getDescption()) && StringUtils.equals("08", role)) ||
				(StringUtils.equals("04", ur.getDescption()) && StringUtils.equals("08", role))) {
			//普通人扫描城市经理(04)、市场开发经理(03)、运营服务商(02)，成为体验店（待审核）
			String area_code;
			if(StringUtils.equals("03", ur.getDescption())) {
				//根据开发经理的上级（运营服务商），获取市级code
				area_code = consumerRelationMapper.getUserAreaCodeByUpper(ur.getUserid());
			}else {
				//获取用户的市级code
				area_code = consumerRelationMapper.getUserAreaCode(ur.getUserid());
			}
			
			//检测有没有申请过
			String status = consumerRelationMapper.checkApplyInfo(userid);
			if(StringUtils.equals("03", status)) {
				//待审核，更新开发人员
				consumerRelationMapper.UpdateRelationToDev(userid, area_code, ur.getUserid());
				return new ResultBean();
			}else if(StringUtils.equals("02", status)) {
				//未通过，可再次申请
				//清空旧记录
				consumerRelationMapper.cleanRelationToStore(userid);
			}
			consumerRelationMapper.bindRelationToStore(userid, area_code, ur.getUserid());
			return new ResultBean();
		}else {
			return new ResultBean("4107", ResultApiMsg.C_4107);
		}
		
	}
	

}
