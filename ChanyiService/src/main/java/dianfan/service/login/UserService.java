package dianfan.service.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;

import dianfan.models.ResultBean;

/**
 * @ClassName UserService
 * @Description 用户登陆服务
 * @author sz
 * @date 2018年6月28日 下午3:05:39
 */
public interface UserService {

	/**
	 * @Title: getUserInfo
	 * @Description: 验证用户登陆，获取用户数据
	 * @param code 
	 * 			code
	 * @param avatarUrl
	 * 			 微信头像
	 * @param encryptedData 
	 * 			用户信息的加密数据
	 * @param iv 
	 * 			加密算法
	 * @return ResultBean
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年6月28日 下午4:02:27
	 */
	ResultBean getUserInfo(String code, String encryptedData, String iv) throws IOException;

	/**
	 * @Title: updatePassword
	 * @Description: 修改登录密码
	 * @param userid 
	 * 			用户的userid
	 * @param oldPassword 
	 * 			原密码
	 * @param newPassword 
	 * 			新密码
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 上午12:06:54
	 */
	ResultBean updatePassword(String userid, String oldPassword, String newPassword);

	
	/**
	 * @Title: getUserBytelno
	 * @Description: 根据telno获取用户的信息数据
	 * @param telno
	 * 			手机号码
	 * @param pwd
	 * 			登录密码
	 * @param iv 
	 * @param encryptedData 
	 * @param code 
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 上午11:58:19
	 */
	ResultBean getUserBytelno(String telno, String pwd, String code, String encryptedData, String iv);


	/**
	 * @Title: registerSMS
	 * @Description: 发送注册手机验证码
	 * @param telno 
	 * 			手机号码
	 * @param accesstoken 
	 * @return
	 * @throws ClientException 
	 * @throws JsonProcessingException 
	 * @throws UnsupportedEncodingException 
	 * @throws:
	 * @time: 2018年6月29日 下午4:20:36
	 */
	ResultBean registerSMS(String telno) throws UnsupportedEncodingException, JsonProcessingException, ClientException;

	/**
	 * @Title: getResetSMS
	 * @Description: 获取重置密码短信验证码
	 * @param telno
	 * @return
	 * @throws ClientException 
	 * @throws JsonProcessingException 
	 * @throws UnsupportedEncodingException 
	 * @throws:
	 * @time: 2018年6月29日 下午4:55:26
	 */
	ResultBean getResetSMS(String telno) throws UnsupportedEncodingException, JsonProcessingException, ClientException;

	/**
	 * @Title: addTelnoBysms
	 * @Description: 绑定手机号
	 * @param userid 用户id
	 * @param telno 手机号码
	 * @param smscode 短信验证码
	 * @param pwd 
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 下午6:13:25
	 */
	ResultBean addTelnoBysms(String userid, String telno, String smscode, String pwd);

	/**
	 * @Title: updatePasswordBysms
	 * @Description: 短信接收方式 更新密码
	 * @param userid
	 * @param telno
	 * @param smscode
	 * @param newPassword 
	 * @return
	 * @throws:
	 * @time: 2018年6月29日 下午10:36:18
	 */
	ResultBean updatePasswordBysms(String telno, String smscode, String newPassword);

	/**
	 * @Title: addBindingWX
	 * @Description: 绑定微信账号
	 * @param userid
	 * @param encryptedData
	 * @param code
	 * @param iv
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年8月17日 上午11:41:16
	 */
	ResultBean addBindingWX(String userid, String encryptedData, String code, String iv) throws IOException;
}
