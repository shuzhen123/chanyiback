package dianfan.service.login.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.login.UserLoginMapper;
import dianfan.date.DateUtility;
import dianfan.entities.FileUploadBean;
import dianfan.entities.FileUploadType;
import dianfan.entities.UserInfo;
import dianfan.entities.goods.CouponModels;
import dianfan.entities.sms.SmsTemplate;
import dianfan.entities.user.UserRoleModel;
import dianfan.models.ResultBean;
import dianfan.service.impl.FileUploadService;
import dianfan.service.impl.RedisService;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.login.UserService;
import dianfan.service.sms.SmsService;
import dianfan.util.AESUtil;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.HttpsUtil;
import dianfan.util.PropertyUtil;
import dianfan.util.StringUtility;
import dianfan.util.UUIDUtil;

/**
 * @ClassName UserServicelmpl
 * @Description 用户登陆相关业务
 * @author sz
 * @date 2018年6月28日 下午3:06:50
 */
@Service
public class UserServicelmpl implements UserService {

	/**
	 * 注入：#RedisTokenService
	 */
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * 注入：#RedisService
	 */
	@Autowired
	private RedisService<?,?> redisService;
	
	/**
	 * 注入：#UserLoginMapper
	 */
	@Autowired
	private UserLoginMapper userLoginMapper;
	
	/**
	* 注入: #FileUploadService
	*/
	@Autowired
	private FileUploadService fileUploadService;
	
	/**
	 * 注入：#SmsService
	 */
	@Autowired
	private SmsService smsService;
	
	
	/*
	 * (non-Javadoc)
	 * <p>Title: getUserInfo</p>
	 * <p>Description: 验证用户登陆，获取用户的信息 </p>
	 * @param code 
	 * 			code 
	 * @param avatarUrl 
	 * 			用户头像
	 * @param encryptedData 
	 * 			用户加密的信息
	 * @param iv 
	 * 			解密工具
	 * @return
	 * link: @see dianfan.service.login.UserService#getUserInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "getUserInfo",description = "验证用户登陆，获取用户数据 ")
	public ResultBean getUserInfo(String code, String encryptedData, String iv) throws IOException {
		
		// 获取code后，请求以下链接获取access_token
		String basepath = PropertyUtil.getProperty("wx.applet.basepath");
		
		// 更换basepath中的 appid和secret属性值,再加上code
		String urlpath = basepath.replace("APPID", PropertyUtil.getProperty("wx.applet.appid"))
				.replace("SECRET", PropertyUtil.getProperty("wx.applet.secret")).replace("JSCODE", code);
		
		// 发送请求
		String data = HttpsUtil.httpsRequestToString(urlpath, "GET", null);
		// 判断是否成功发送
		if (!data.contains("errcode")&& !data.contains("errmsg") && data.contains("openid")) {
			
			// 判断返回值中是否存在 unionId
			if (data.contains("unionId") || data.contains("openid")) {
				// 将unionId 和 openid 组成一个map
				Map<String, Object> datas = new HashMap<>();
				datas.put("unionId", JSONObject.parseObject(data).getString("unionId"));
				datas.put("openid", JSONObject.parseObject(data).getString("openid"));
				// 查询用户的信息
				UserInfo userinfo = userLoginMapper.checkUserByUnionId(datas);
				if (userinfo != null) {
					if(userinfo.getLocked() != 0) {
						//账号被锁
						return new ResultBean("4022", ResultApiMsg.C_4022);
					}
					// 获取用户的ID
					String userid = userinfo.getId();
					// 组成token
					String token = redisTokenService.createToken(userid);
					// 成功返回
					return new ResultBean(token);
				}
			} 
			
			// 获取返回值中的 session_key 
			String sessionkey = JSONObject.parseObject(data).getString("session_key");
			// 解密用户的信息操作
			String userData = AESUtil.getUserInfoFromEncryptedData(encryptedData, sessionkey, iv);

			// 获取用户的unionId
			String unionId = JSONObject.parseObject(userData).getString("unionId");
			// 通过上面获取的unionId验证用户是否注册过
			UserInfo userInfo = userLoginMapper.checkUserByOpenid(unionId);
			
			// 判断用户是否存在
			if (userInfo != null) {
				if(userInfo.getLocked() != 0) {
					//账号被锁
					return new ResultBean("4022", ResultApiMsg.C_4022);
				}
				// 用户存在：获取用户的userid
				String userid = userInfo.getId();
				// 组成token
				String token = redisTokenService.createToken(userid);
				// 成功返回
				return new ResultBean(token);
			} else {
				/* 用户不存在：帮助用户默认完成注册*/
				Map<String, Object> param = new HashMap<>();
				//解密后的数据
				JSONObject infos = JSONObject.parseObject(userData);
				// 确认数据库中是否存在解密后的unionId对应的数据
				String userUnion = infos.getString("unionId");
				String openId = infos.getString("openId");
				UserInfo userinfo = userLoginMapper.checkUserByuserUnion(userUnion);
				if (userinfo != null) {
					// 更新用户的openId
					Map<String, Object> map = new HashMap<>();
					map.put("userUnion", userUnion);
					map.put("openId", openId);
					userLoginMapper.updateopenId(map);
					// 获取用户的ID
					String userid = userinfo.getId();
					// 组成token
					String token = redisTokenService.createToken(userid);
					// 成功返回
					return new ResultBean(token);
				}
				
				// 新增一个userid
				param.put("id",UUIDUtil.getUUID());
				// 获取用户的openId
				param.put("openId", infos.getString("openId"));
				// 获取用户的nickName
				// 从用户信息中将昵称获取出来
				String nickName = infos.getString("nickName");
				if (nickName.length() > 50) {
					nickName = nickName.substring(0, 50);
				}
				param.put("nickName", nickName);
				// 获取用户的avatarUrl
				FileUploadType flt= new FileUploadType();
				// 保存头像文件
				FileUploadBean saveFileData = fileUploadService.saveFileData(infos.getString("avatarUrl"), flt.AVATOR);
				// 文件保存在本地的路径
				String PUrl  = PropertyUtil.getProperty("domain") + infos.getString("avatarUrl");
				param.put("avatarUrl", saveFileData.getFileRelativePath());
				// 获取用户的unionId
				param.put("unionId", infos.getString("unionId"));
				// 获取用户的性别
				param.put("gender", infos.getString("gender"));
				// 用户二维码随机数
				param.put("qrNum", UUIDUtil.getUUID());
				// 获取用户的所在国家
				param.put("country", infos.getString("country"));
				
				// 新增用户
				userLoginMapper.addUserInfo(param);
				
				//新增完成后，将用户的userID获取出来
				String userid = (String) param.get("id");
				
				// 获取普通用角色的rolecode和描述
				UserRoleModel role = userLoginMapper.getRole();
				// 增加用户的角色
				Map<String, Object> userRole = new HashMap<>();
				userRole.put("roleid", role.getId());
				userRole.put("descption", role.getRoleDescription());
				userRole.put("userid", userid);
				userLoginMapper.addUserRole(userRole);
				/*-------------------赠送新用户 优惠券------------------*/
				// 获取注册优惠券列表id 
				List<CouponModels> coupon = userLoginMapper.fildFreeCouponList();
				// 获取当前时间+30天的偏移量
				Date addDay = DateUtility.getAddDayToTimeEnd(new Date(),ConstantIF.CUOPON_DAY);
				// 将date类型转换成Timestamp 类型
				Timestamp nowtime = new Timestamp(addDay.getTime());
				
				// 判断并设置给到用户后，优惠券的设置时间
				for (CouponModels cn : coupon) {
					Timestamp endtime = cn.getCouponEndtime();
					if (nowtime.before(endtime)) {
						cn.setCouponEndtime(nowtime);
					}
				}
				// 给用户发送优惠券
				Map<String, Object> coupondata = new HashMap<>(); 
				coupondata.put("userid", userid);
				coupondata.put("coupon", coupon);
				// 写入用户与优惠券关系表中
				userLoginMapper.addUserCoupon(coupondata);
				// 注册优惠券+1
				userLoginMapper.addCouponCount();
				// 组成token
				String token = redisTokenService.createToken(userid);
				// 成功返回
				return new ResultBean(token);
			}
		} else {
			//发送请求失败，参数错误
			return new ResultBean("501",ResultBgMsg.C_501);
		}
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: updatePassword</p>
	 * <p>Description: 修改登录密码</p>
	 * @param userid 
	 * 			userid 
	 * @param oldPassword 
	 * 			原密码
	 * @param newPassword 
	 * 			新密码
	 * @return
	 * link: @see dianfan.service.login.UserService#updatePassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "updatePassword",description = " 修改登录密码 ")
	public ResultBean updatePassword(String userid, String oldPassword, String newPassword) {
		// 1.入参容器
		Map<String, Object> param = new HashMap<>();
		// 2.加入userid
		param.put("userid", userid);
		// 3.加入oldPassword
		param.put("oldPassword", oldPassword);
		// 4.加入newPassword
		param.put("newPassword", newPassword);
		
		// 5.调库查看，验证原密码是否正确
		int count = userLoginMapper.checkOldPassword(param);
		// 判断密码是否正确
		if (count == 0) {
			// !修改是被：原密码不正确
			return new ResultBean("4004",ResultApiMsg.C_4004);
		} else {
			// 6.原密码正确，进行更新密码操作
			userLoginMapper.updateNewPassword(param);
			// 修改密码成功
			return new ResultBean(); 
		}
		
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: getUserBytelno</p>
	 * <p>Description:  根据telno获取用户的信息数据</p>
	 * @param telno 
	 * 			手机号码
	 * @param pwd 
	 * 			登录密码
	 * @return
	 * link: @see dianfan.service.login.UserService#getUserBytelno(java.lang.String, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getUserBytelno",description = "根据telno获取用户的信息数据")
	public ResultBean getUserBytelno(String telno, String pwd, String code, String encryptedData, String iv) {
		// 1.创建入参容器
		Map<String, Object> data = new HashMap<>(); 
		// 2.加入入参
		data.put("telno", telno);
		data.put("pwd", pwd);
		
		// 3.调库验证登录信息,确认是否存在用户
		UserInfo info = userLoginMapper.getUserBytelno(data);
		
		// 4.判断用户是否存在
		if (info == null) {
			// !手机号码或登录密码错误
			return new ResultBean("4006" , ResultApiMsg.C_4006);
		} 
		
		if(info.getLocked() != 0) {
			//账号被锁
			return new ResultBean("4022", ResultApiMsg.C_4022);
		}
		
		// 获取code后，请求以下链接获取access_token
		String basepath = PropertyUtil.getProperty("wx.applet.basepath");
		
		// 更换basepath中的 appid和secret属性值,再加上code
		String urlpath = basepath.replace("APPID", PropertyUtil.getProperty("wx.applet.appid"))
				.replace("SECRET", PropertyUtil.getProperty("wx.applet.secret")).replace("JSCODE", code);
		
		// 发送请求
		String datas = HttpsUtil.httpsRequestToString(urlpath, "GET", null);
		String openid = JSONObject.parseObject(datas).getString("openid");
		//String unionId = JSONObject.parseObject(datas).getString("unionId");
		if (StringUtility.isNull(openid)) {
			// ！登陆失败
			return new ResultBean("4109",ResultApiMsg.C_4109);
		}
		
		// 5.获取用户的userid,组件token返回
		String token = redisTokenService.createToken(info.getId());
		// 手机号登陆，将用户的openid，存在redis中
		redisService.set(token+ConstantIF.OPENID_APPLET,openid,ConstantIF.TOKEN_EXPIRES_SECONDS);
		// 6.登录成功
		return new ResultBean(token);
		
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: registerSMS</p>
	 * <p>Description: 根据手机号码获取用户是否存在，不存在发送注册验证码</p>
	 * @param telno
	 * @return
	 * link: @see dianfan.service.login.UserService#registerSMS(java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "registerSMS",description = "发送注册手机验证码")
	public ResultBean registerSMS(String telno) throws UnsupportedEncodingException, JsonProcessingException, ClientException {
		// 1.查看手机号是否注册
		int count = userLoginMapper.getUserCountByPhone(telno);
		if (count > 0 ) {
			// !用户已存在，请直接登录
			return new ResultBean("4009" , ResultApiMsg.C_4009);
		}
		
		// 查看是否有验证码缓存
		String last_sms = (String) redisService.get(telno+PropertyUtil.getProperty("alisms_tplCode_2"));
		
		// 验证码有效时间(秒)
		int invalidSecs = Integer.parseInt(PropertyUtil.getProperty("alisms_alidity")) * 60;
		
		// 随机验证码
		String codeRadom = null;
		
		if (last_sms != null) {
			// 查看剩余时间(秒)
			Long time = redisService.getExpire(telno+PropertyUtil.getProperty("alisms_tplCode_2"));
			// 验证码发送间隔(秒)
			int sendInterval = Integer.parseInt(PropertyUtil.getProperty("smssendinterval")) * 60;
			//时间判断
			if(invalidSecs - time < sendInterval) {
				//发送频率为间隔时间内，无须重发
				return new ResultBean();
			}
			// 上次的验证码
			codeRadom = last_sms;
		} else {
			// 无记录，创建新的随机验证码
			codeRadom = GenRandomNumUtil.getRandomNum(6);
		}
		// 将获取到的验证码存入redis 
		redisService.set(telno+PropertyUtil.getProperty("alisms_tplCode_2"), codeRadom, invalidSecs);
		
		// 3.创建一个短信模板
		SmsTemplate sms =  new SmsTemplate();
		// 4.添加用户的手机号码
		sms.setPhoneNumbers(telno);
		// 5.加入 用户注册模板
		sms.setTemplateCode(PropertyUtil.getProperty("alisms_tplCode_2"));
		// 6.短信模板变量替换JSON串
		Map<String, String> map = new HashMap<>();
		map.put("code", codeRadom);
		sms.setTemplateParam(map);
		// 7.调起短信发送
		SendSmsResponse sendSms = smsService.sendSms(sms);
		// 8.判断短信是否发送成功
		if ("OK".equals(sendSms.getCode().toUpperCase())) {
			// 短信发送成功
			return new ResultBean();
		} else {
			// ！短信发送失败
			return new ResultBean("4010",sendSms.getMessage());
		}
	}

	
	/*
	 * (non-Javadoc)
	 * <p>Title: getResetSMS</p>
	 * <p>Description: 获取重置密码短信验证码</p>
	 * @param telno
	 * 			手机号码
	 * @return
	 * link: @see dianfan.service.login.UserService#getResetSMS(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getResetSMS",description = "获取重置密码短信验证码")
	public ResultBean getResetSMS(String telno) throws UnsupportedEncodingException, JsonProcessingException, ClientException {
		// 1.查看手机号是否注册
		int count = userLoginMapper.getUserCountByPhone(telno);
		// 判断是否注册
		if (count == 0 ) {
			// !用户不存在
			return new ResultBean("4001",ResultApiMsg.C_4001);
		}
		// 查看是否有验证码缓存
		String last_sms = (String) redisService.get(telno+PropertyUtil.getProperty("alisms_tplCode_1"));
		
		// 验证码有效时间(秒)
		int invalidSecs = Integer.parseInt(PropertyUtil.getProperty("alisms_alidity")) * 60;
		
		// 随机验证码
		String codeRadom = null;
		
		if (last_sms != null) {
			// 查看剩余时间(秒)
			Long time = redisService.getExpire(telno+PropertyUtil.getProperty("alisms_tplCode_1"));
			// 验证码发送间隔(秒)
			int sendInterval = Integer.parseInt(PropertyUtil.getProperty("smssendinterval")) * 60;
			//时间判断
			if(invalidSecs - time < sendInterval) {
				//发送频率为间隔时间内，无须重发
				return new ResultBean();
			}
			// 上次的验证码
			codeRadom = last_sms;
		} else {
			// 无记录，创建新的随机验证码
			codeRadom = GenRandomNumUtil.getRandomNum(6);
		}
		// 将获取到的验证码存入redis 
		redisService.set(telno+PropertyUtil.getProperty("alisms_tplCode_1"), codeRadom, invalidSecs);
		
		// 3.创建一个短信模板
		SmsTemplate sms =  new SmsTemplate();
		// 4.添加用户的手机号码
		sms.setPhoneNumbers(telno);
		// 5.加入 用户注册模板
		sms.setTemplateCode(PropertyUtil.getProperty("alisms_tplCode_1"));
		// 6.短信模板变量替换JSON串
		Map<String, String> map = new HashMap<>();
		map.put("code", codeRadom);
		sms.setTemplateParam(map);
		// 7.吊起短信发送
		SendSmsResponse sendSms = smsService.sendSms(sms);
		// 8.判断短信是否发送成功
		if ("OK".equals(sendSms.getCode().toUpperCase())) {
			// 9.接收验证码成功
			return new ResultBean();
		} else {
			// ！短信发送失败
			return new ResultBean("4010",sendSms.getMessage());
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: addTelnoBysms</p>
	 * <p>Description: 绑定手机号码</p>
	 * @param userid
	 * @param telno
	 * @param smscode
	 * @return
	 * link: @see dianfan.service.login.UserService#addTelnoBysms(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "addTelnoBysms",description = "绑定手机号")
	public ResultBean addTelnoBysms(String userid, String telno, String smscode , String pwd) {
		// 验证 输入的短信验证码是否正确
		String rediscode = redisService.get(telno+PropertyUtil.getProperty("alisms_tplCode_2"));
		if (!smscode.equals(rediscode)) {
			// 错误：短信验证码不正确
			return new ResultBean("4011", ResultApiMsg.C_4011);
		}
		
		// 短信发送成功，创建入参模型
		Map<String, Object> param = new HashMap<>();
		// 添加手机号码
		param.put("telno", telno);
		// 添加用户的ID
		param.put("userid", userid);
		// 添加用户输入的密码
		param.put("pwd", pwd);
		// 10.添加用户的手机号码
		userLoginMapper.updateUserphone(param);
		// 添加手机号码成功
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: updatePasswordBysms</p>
	 * <p>Description: 短信接收方式 更新密码</p>
	 * @param userid 
	 * 			用户id
	 * @param telno 
	 * 			手机号码
	 * @param smscode 
	 * 			重置短信验证码
	 * @return
	 * link: @see dianfan.service.login.UserService#updatePasswordBysms(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updatePasswordBysms",description = "短信接收方式 更新密码")
	public ResultBean updatePasswordBysms(String telno, String smscode, String newPassword) {
		// 1.验证 输入的短信验证码是否正确
		String rediscode = redisService.get(telno+PropertyUtil.getProperty("alisms_tplCode_1"));
		if (!smscode.equals(rediscode)) {
			// ！短信验证码不正确
			return new ResultBean("4011", ResultApiMsg.C_4011);
		}
		// 2.短信发送成功，创建入参模型
		Map<String, Object> param = new HashMap<>();
		// 添加用户的新密码
		param.put("newPassword", newPassword);
		// 添加用户的手机号码
		param.put("telno", telno);
		// 3.修改密码操作
		userLoginMapper.updateNewPassword(param);
		// 4.成功返回
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: addBindingWX</p>
	 * <p>Description: 绑定微信账号</p>
	 * @param userid 用户ID
	 * @param encryptedData 
	 * @param code
	 * @param iv
	 * @return
	 * link: @see dianfan.service.login.UserService#addBindingWX(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultBean addBindingWX(String userid, String encryptedData, String code, String iv) throws IOException {
		// 判断用户是否存在unionId 
		String unionId = userLoginMapper.getUseridUnionId(userid);
		if (!StringUtility.isNull(unionId)) {
			return new ResultBean("C_4021", ResultApiMsg.C_4021);
		}
		
		// 获取code后，请求以下链接获取access_token
		String basepath = PropertyUtil.getProperty("wx.applet.basepath");
		
		// 更换basepath中的 appid和secret属性值,再加上code
		String urlpath = basepath.replace("APPID", PropertyUtil.getProperty("wx.applet.appid"))
				.replace("SECRET", PropertyUtil.getProperty("wx.applet.secret")).replace("JSCODE", code);
		
		// 发送请求
		String data = HttpsUtil.httpsRequestToString(urlpath, "GET", null);
		// 成功请求后，获取用户的信息，将用户信息补全
		if (!data.contains("errcode")&& !data.contains("errmsg") && data.contains("openid")) {
			
			// 获取返回值中的 session_key 
			String sessionkey = JSONObject.parseObject(data).getString("session_key");
			// 解密用户的信息操作
			String userData = AESUtil.getUserInfoFromEncryptedData(encryptedData, sessionkey, iv);
			//解密后的数据
			JSONObject infos = JSONObject.parseObject(userData);
			UserInfo userinfo = userLoginMapper.checkUserByuserUnion(infos.getString("unionId"));
			if (!StringUtils.isEmpty(userinfo)) {
				return new ResultBean("4031",ResultBgMsg.C_4031);
			}
			// 创建一个参数模型
			Map<String, Object> param = new HashMap<>();
			// 获取用户的openId
			param.put("openId", infos.getString("openId"));
			// 获取用户的nickName
			// 从用户信息中将昵称获取出来
			String nickName = infos.getString("nickName");
			if (nickName.length() > 50) {
				nickName = nickName.substring(0, 50);
			}
			param.put("nickName", nickName);
			// 获取用户的avatarUrl
			FileUploadType flt= new FileUploadType();
			// 保存头像文件
			FileUploadBean saveFileData = fileUploadService.saveFileData(infos.getString("avatarUrl"), flt.AVATOR);
			// 文件保存在本地的路径
			//String PUrl  = PropertyUtil.getProperty("domain") + infos.getString("avatarUrl");
			param.put("avatarUrl", saveFileData.getFileRelativePath());
			// 获取用户的unionId
			param.put("unionId", infos.getString("unionId"));
			
			// 获取用户的性别
			param.put("gender", infos.getString("gender"));
			// 用户二维码随机数
			param.put("qrNum", UUIDUtil.getUUID());
			// 获取用户的所在国家
			param.put("country", infos.getString("country"));
			// 添加用户的ID
			param.put("id",userid);
			
			// 更新用户的信息
			userLoginMapper.updateUserInfo(param);
			
		} else {
			//发送请求失败，参数错误
			return new ResultBean("501",ResultBgMsg.C_501);
		}
		
		return new ResultBean();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
