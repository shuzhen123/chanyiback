/**  
* @Title: UserRolePopedomServiceImpl.java
* @Package dianfan.service.urpopedom.impl
* @Description: TODO
* @author yl
* @date 2018年7月25日 上午10:18:16
* @version V1.0  
*/ 
package dianfan.service.urpopedom.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.urpopedom.UserRolePopedomMapper;
import dianfan.dao.mapper.userrole.UserRoleMapper;
import dianfan.entities.role.AdminPopedom;
import dianfan.entities.role.Role;
import dianfan.models.ResultBean;
import dianfan.service.urpopedom.UserRolePopedomService;

/** @ClassName UserRolePopedomServiceImpl
 * @Description 
 * @author yl
 * @date 2018年7月25日 上午10:18:16
 */
@Service
public class UserRolePopedomServiceImpl implements UserRolePopedomService{
	
	@Autowired
	private UserRolePopedomMapper userRolePopedomMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	/* (non-Javadoc)
	 * <p>Title: findUrpopedomList</p>
	 * <p>Description: </p>
	 * @param roleName
	 * @param nickName
	 * @param popedomname
	 * @param page
	 * @param pageSize
	 * @return
	 * link: @see dianfan.service.urpopedom.UserRolePopedomService#findUrpopedomList(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */ 
	@Override
	public ResultBean findUrpopedomList() {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String,Object>();
		int total = userRolePopedomMapper.getUrpopedomNum();
		List<AdminPopedom> urplist = userRolePopedomMapper.findUrpopedomList();
		data.put("total", total);
		data.put("urplist", urplist);
		return new ResultBean(data);
	}

	/* (non-Javadoc)
	 * <p>Title: updateBgUserRolePopedom</p>
	 * <p>Description: </p>
	 * @param mrpid
	 * @param popedomid
	 * link: @see dianfan.service.urpopedom.UserRolePopedomService#updateBgUserRolePopedom(java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateBgUserRolePopedom(String mrpid,String description,String popedom, String popedomid) {
		// TODO Auto-generated method stub
		//先去除原有的关系
		userRolePopedomMapper.deleteUserRolePopedom(mrpid);
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("roleid", mrpid);
		param.put("popedom", popedom);
		param.put("popedomids", popedomid.split(","));
		if (StringUtils.isNotEmpty(description)) {
			Role rl = new Role();
			rl.setId(mrpid);
			rl.setRoleDescription(description);
			//修改角色描述
			userRoleMapper.updateUserDiscount(rl);
		}
		//在添加关系
		userRolePopedomMapper.updateBgUserRolePopedom(param);
		
	}

}
