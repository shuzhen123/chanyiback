package dianfan.service.usersite;

import dianfan.models.ResultBean;

/**
 * @ClassName UsersiteService
 * @Description 用户地址Service （接口）
 * @author sz
 * @date 2018年6月30日 下午3:29:23
 */
public interface UsersiteService {

	/**
	 * @Title: findUserSiteList
	 * @param userid  用户的id
	 * @Description: 获取用户收获地址list
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月30日 下午3:47:18
	 */
	ResultBean findUserSiteList(String userid);

	/**
	 * @Title: addUserSite
	 * @Description: 添加用户收货地址
	 * @param userid 用户id
	 * @param name 收件人姓名
	 * @param telno 收件人手机好号码
	 * @param areaCode 收件人所在区域
	 * @param detailAddr 详细地址
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月1日 下午12:11:38
	 */
	ResultBean addUserSite(String userid, String name, String telno, String areaCode, String detailAddr);

	/**
	 * @Title: updateUserSite
	 * @Description: 修改用户收货地址
	 * @param siteid 收货地址id
	 * @param name 收件人姓名
	 * @param telno 收件人手机好号码
	 * @param areaCode 收件人所在区域
	 * @param detailAddr 详细地址
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月1日 下午12:11:38
	 */
	ResultBean updateUserSite(String name, String telno, String areaCode, String detailAddr,String siteid,String userid);

	/**
	 * @Title: delUserSiteByid
	 * @Description: 删除收货地址
	 * @param siteid 收货地址id
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月1日 下午1:37:20
	 */
	ResultBean delUserSiteByid(String siteid , String userid);

}
