/**  
* @Title: CouponServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午2:35:25
* @version V1.0  
*/ 
package dianfan.service.our.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.our.PersonalInfoMapper;
import dianfan.entities.UserInfo;
import dianfan.entities.our.UserInfoModel;
import dianfan.service.our.PersonalInfoService;

/** @ClassName CouponServiceImpl
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午2:35:25
 */
@Service
public class PersonalInfoServiceImpl implements PersonalInfoService{
	
	@Autowired
	private PersonalInfoMapper personalInfoMapper;

	/* (non-Javadoc)
	 * <p>Title: getUserInfo</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * link: @see dianfan.service.our.PersonalInfoService#getUserInfo(java.lang.String)
	 */ 
	@Override
	public UserInfoModel getUserInfo(String userid) {
		// TODO Auto-generated method stub
		return personalInfoMapper.getUserInfo(userid);
	}

	/* (non-Javadoc)
	 * <p>Title: updateUserInfo</p>
	 * <p>Description: </p>
	 * @param id
	 * @param sex
	 * @param nickname
	 * @param avatarurl
	 * link: @see dianfan.service.our.PersonalInfoService#updateUserInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateUserInfo(String id, String sex, String nickname, String avatarurl) {
		UserInfo users = new UserInfo();
		users.setId(id);
		users.setNickName(nickname);
		users.setSex(sex);
		users.setAvatarUrl(avatarurl);
		users.setUpdateBy(id);
		personalInfoMapper.updateUserInfo(users);
		
	}

}
