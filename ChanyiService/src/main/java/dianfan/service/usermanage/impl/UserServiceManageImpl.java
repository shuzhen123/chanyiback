package dianfan.service.usermanage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.dao.mapper.base.AreaMapper;
import dianfan.dao.mapper.userManage.UserManageMapper;
import dianfan.entities.UserInfo;
import dianfan.models.ResultBean;
import dianfan.service.usermanage.UserServiceManage;
import dianfan.util.StringUtility;

/**
 * @ClassName UserManageImpl
 * @Description 后台用户管理
 * @author sz
 * @date 2018年7月23日 上午11:17:25
 */
@Service
public class UserServiceManageImpl implements UserServiceManage {
	
	/**
	 * 注入： #UserManageMapper
	 */
	@Autowired
	private UserManageMapper userManageMapper;
	/**
	 * 注入： #AreaMapper
	 */
	@Autowired
	private AreaMapper areaMapper;

	

	/*
	 * (non-Javadoc)
	 * <p>Title: findUserList</p>
	 * <p>Description: 获取用户列表</p>
	 * @return
	 * link: @see dianfan.service.usermanage.UserServiceManage#findUserList()
	 */
	@Override
	@SystemServiceLog(method = "findUserList",description = "获取用户列表 ")
	public ResultBean findUserList(String userid, int page, int pageSize, String name, String telno, String source,
			String starttime, String endtime, String role, String sex, String areacode,Integer entkbn) {
		// 构建返回参数模型
		Map<String, Object> data = new HashMap<>();
		
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("start", (page - 1 ) * pageSize);
		param.put("pageSize", pageSize);
		param.put("name", name);
		param.put("telno", telno);
		param.put("source", source);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		param.put("entkbn", entkbn);
		// 按角色
		param.put("role", role);
		// 性别
		param.put("sex", sex);
		// 区域code
		param.put("areacode", areacode);
		
		// 获取用户列表总数
		int count = userManageMapper.findUserCount(param);
		data.put("count", count);
		if (count == 0 || count < (page - 1 ) * pageSize) {
			data.put("userList", new ArrayList<>());
			return new ResultBean(data);
		}
		
		//获取用户列表
		List<UserInfo> userList = userManageMapper.findUserList(param);
		// 将查出来的区域code，转成完整区域信息返回
		for (UserInfo info : userList) {
			if (!StringUtility.isNull(info.getAreaCode())) {
				int level;
				if(info.getAreaCode().lastIndexOf("0000") != -1) {
					//省级code
					level = 1;
				}else if(info.getAreaCode().lastIndexOf("00") != -1) {
					//市级code
					level = 2;
				}else {
					//区县code
					level = 3;
				}
				String addrByCode = areaMapper.getAddrByCode(info.getAreaCode(), level);
				info.setAreatext(addrByCode);
			}
		}
		
		data.put("userList", userList);
		return new ResultBean(data);
	}



	/*
	 * (non-Javadoc)
	 * <p>Title: updataUserPwd</p>
	 * <p>Description: 修改用户的密码</p>
	 * @param userid 用户ID
	 * @param id 被修改的ID
	 * @return
	 * link: @see dianfan.service.usermanage.UserServiceManage#updataUserPwd(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "updataUserPwd",description = "修改用户的密码")
	public ResultBean updataUserPwd(String userid, String id, String newPwd) {
		//创建入参模型
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("id", id);
		param.put("newPwd", newPwd);
		// 库操作
		userManageMapper.updataUserPwd(param);
		// 成功
		return new ResultBean();
	}



	/*
	 * (non-Javadoc)
	 * <p>Title: updataUserTelno</p>
	 * <p>Description: 修改用户的手机号码</p>
	 * @param userid 用户ID
	 * @param id 被修改的ID
	 * @param telno 新手机号
	 * @return
	 * link: @see dianfan.service.usermanage.UserServiceManage#updataUserTelno(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "updataUserTelno",description = "修改用户的手机号码")
	public ResultBean updataUserTelno(String userid, String id, String telno) {
		//创建入参模型
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("id", id);
		param.put("telno", telno);
		// 库操作
		userManageMapper.updataUserTelno(param);
		// 成功
		return new ResultBean();
	}



	/*
	 * (non-Javadoc)
	 * <p>Title: updataUserType</p>
	 * <p>Description: 修改用户状态</p>
	 * @param userid 
	 * @param ids
	 * @param type
	 * @return
	 * link: @see dianfan.service.usermanage.UserServiceManage#updataUserType(java.lang.String, java.lang.String, int)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updataUserType",description = "修改用户状态")
	public ResultBean updataUserType(String userid, String ids, int type) {
		// 整理IDs
		List<String> list = Arrays.asList(ids.split(","));
		// 创建入参模型
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("list", list);
		param.put("type", type);
		// 库操作
		userManageMapper.updataUserType(param);
		// 成功返回
		return new ResultBean();
	}
	

}
