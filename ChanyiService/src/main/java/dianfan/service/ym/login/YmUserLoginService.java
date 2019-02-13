package dianfan.service.ym.login;

import dianfan.models.ResultBean;

/**
 * @ClassName YmUserLoginService
 * @Description 易盟用户登录服务
 * @author cjy
 * @date 2018年9月17日 下午2:13:03
 */
public interface YmUserLoginService {

	/**
	 * @Title: ymUserLogin
	 * @Description: 用户登录
	 * @param account
	 * @param password
	 * @return
	 * @throws:
	 * @time: 2018年9月17日 下午2:14:46
	 * @author cjy
	 */
	ResultBean ymUserLogin(String account, String password);

}
