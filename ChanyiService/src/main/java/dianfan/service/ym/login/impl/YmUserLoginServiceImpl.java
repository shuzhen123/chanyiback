package dianfan.service.ym.login.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.login.YmUserLoginMapper;
import dianfan.entities.UserInfo;
import dianfan.entities.role.Role;
import dianfan.models.ResultBean;
import dianfan.service.adminlogin.AdminLoginService;
import dianfan.service.impl.RedisTokenService;
import dianfan.service.ym.login.YmUserLoginService;
/**
 * @ClassName YmUserLoginServiceImpl
 * @Description 易盟用户登录服务实现
 * @author cjy
 * @date 2018年9月17日 下午2:13:59
 */
@Service
public class YmUserLoginServiceImpl implements YmUserLoginService {

	@Autowired
	private YmUserLoginMapper ymUserLoginMapper;
	@Autowired
	private RedisTokenService redisTokenService;
	@Autowired
	private AdminLoginService adminLoginService;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: ymUserLogin</p>
	 * <p>Description: 用户登录</p>
	 * @param account
	 * @param password
	 * @return
	 * @author cjy
	 * link: @see dianfan.service.ym.login.YmUserLoginService#ymUserLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultBean ymUserLogin(String account, String password) {
		UserInfo userInfo = ymUserLoginMapper.getYmUserInfo(account, password);
		if(userInfo == null) {
			return new ResultBean("4002", ResultBgMsg.C_4002);
		}
		
		if(userInfo.getLocked() == 1) {
			return new ResultBean("4011", ResultBgMsg.C_4011);
		}
		
		//登录成功
		
		//权限ids
		String power = ymUserLoginMapper.getYmUserRolePower(userInfo.getId());
		//token
		String accesstoken = redisTokenService.createToken(userInfo.getId());
		
		// 构建返回参数	
		Map<String, Object> data = new HashMap<>();
		// accesstoken
		data.put("accesstoken", accesstoken);
		// 角色
		data.put("role", userInfo.getRole());
		// 权限
		data.put("power", power);
		
		return new ResultBean(data);
	}

}
