package dianfan.service.our;

import dianfan.entities.our.UserInfoModel;

public interface PersonalInfoService {

	/**
	 * @Title: getUserInfo
	 * @Description: 根据id获取用户数据
	 * @param id 用户id
	 * @return UserInfo 用户信息
	 * @throws:
	 * @time: 2018年6月28日 上午10:57:51
	 */
	UserInfoModel getUserInfo(String id);
	
	/**
	 * @Title: updateUserInfo
	 * @Description: 根据id修改用户数据
	 * @param id 用户id
	 * @return UserInfo 用户信息
	 * @throws:
	 * @time: 2018年6月28日 下午5:30:49
	 */
	void updateUserInfo(String id,String sex,String nickname,String avatarurl);

	
}
