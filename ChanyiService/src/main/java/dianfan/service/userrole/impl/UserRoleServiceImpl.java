/**  
* @Title: UserRoleServiceImpl.java
* @Package dianfan.service.userrole.impl
* @Description: TODO
* @author yl
* @date 2018年7月24日 上午10:59:22
* @version V1.0  
*/ 
package dianfan.service.userrole.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.userrole.UserRoleMapper;
import dianfan.entities.role.Role;
import dianfan.models.ResultBean;
import dianfan.service.userrole.UserRoleService;

/** @ClassName UserRoleServiceImpl
 * @Description 
 * @author yl
 * @date 2018年7月24日 上午10:59:22
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleMapper userRoleMapper;

	/* (non-Javadoc)
	 * <p>Title: findUserRoleList</p>
	 * <p>Description: </p>
	 * @param nickname
	 * @param descption
	 * @return
	 * link: @see dianfan.service.userrole.UserRoleService#findUserRoleList(java.lang.String, java.lang.String)
	 */ 
	@Override
	public ResultBean findUserRoleList() {
		// TODO Auto-generated method stub
		List<Role> urlist = userRoleMapper.findUserRoleList();
		if (urlist !=null && urlist.size()>0) {
			for (int i = 0; i < urlist.size(); i++) {
				int num = userRoleMapper.getRoleUserNum(urlist.get(i).getId());
				List<String> popedomids = userRoleMapper.findPopedomids(urlist.get(i).getId());
				if (popedomids.size()>0) {
					String popedomidlist = StringUtils.join(popedomids, ",");
				    urlist.get(i).setPopedomid(popedomidlist);
				}
				urlist.get(i).setRolenum(num);
			}
		}
		
		return new ResultBean(urlist);
	}

	/* (non-Javadoc)
	 * <p>Title: findUserDiscountList</p>
	 * <p>Description: </p>
	 * @return
	 * link: @see dianfan.service.userrole.UserRoleService#findUserDiscountList()
	 */ 
	@Override
	public ResultBean findUserDiscountList() {
		// TODO Auto-generated method stub
		List<Role> rlist = userRoleMapper.findUserDiscountList();
		return new ResultBean(rlist);
	}

	/* (non-Javadoc)
	 * <p>Title: updateUserDiscount</p>
	 * <p>Description: </p>
	 * @param id
	 * @param salediscount
	 * link: @see dianfan.service.userrole.UserRoleService#updateUserDiscount(java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateUserDiscount(String id, String salediscount) {
		// TODO Auto-generated method stub
		Role rl = new Role();
		rl.setId(id);
		//rl.setSaleDiscount(new BigDecimal(salediscount));
		userRoleMapper.updateUserDiscount(rl);
	}

}
