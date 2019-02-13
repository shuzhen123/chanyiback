/**  
* @Title: CouponMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午2:38:37
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserInfo;
import dianfan.entities.our.UserInfoModel;

/** @ClassName PersonalInfoMapper
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午2:38:37
 */
@Repository
public interface PersonalInfoMapper {
	
	/**
	 * @Title: getUserInfo
	 * @Description: 
	 * @param userId 用户id
	 * @return 用户信息
	 * @throws:
	 * @time: 2018年6月28日 下午3:30:52
	 */
	UserInfoModel getUserInfo(@Param("userId") String userId);
	
	void updateUserInfo(UserInfo userinfo);

}
