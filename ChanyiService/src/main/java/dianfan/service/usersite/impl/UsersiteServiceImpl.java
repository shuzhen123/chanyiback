package dianfan.service.usersite.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultApiMsg;
import dianfan.dao.mapper.usersite.UsersiteMapper;
import dianfan.entities.UserAddress;
import dianfan.models.ResultBean;
import dianfan.service.usersite.UsersiteService;
import dianfan.util.UUIDUtil;

/**
 * @ClassName UsersiteService
 * @Description 用户地址相关服务
 * @author sz
 * @date 2018年6月30日 下午3:29:23
 */
@Service
public class UsersiteServiceImpl implements UsersiteService {

	/**
	 *  注入 #UsersiteMapper
	 */
	@Autowired
	private UsersiteMapper usersiteMapper;
 
	
	/*
	 * (non-Javadoc)
	 * <p>Title: findUserSiteList</p>
	 * <p>Description: 获取用户的收获地址列表 </p>
	 * @param userid 
	 * 			用户的id
	 * @return
	 * link: @see dianfan.service.usersite.UsersiteService#findUserSiteList(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findUserSiteList",description = "获取用户收获地址list")
	public ResultBean findUserSiteList(String userid) {
		// 获取用户的收货列表信息
		List<UserAddress> userSiteList = usersiteMapper.findUserSiteList(userid);
		// 成功返回
		return new ResultBean(userSiteList);
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: addUserSite</p>
	 * <p>Description: 添加用户的收货地址</p>
	 * @param userid 
	 * 			用户id
	 * @param name 
	 * 			收货人姓名
	 * @param telno 
	 * 			收货人手机号码
	 * @param areaCode 
	 * 			区域code
	 * @param detailAddr 
	 * 			详细地址
	 * @return ResultBean
	 * link: @see dianfan.service.usersite.UsersiteService#addUserSite(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addUserSite",description = "添加用户收货地址")
	public ResultBean addUserSite(String userid, String name, String telno, String areaCode, String detailAddr) {
		// ---创建入参容器----
		Map<String, Object> param = new HashMap<>();
		// 1.创建一个新的id(UUID)
		param.put("id", UUIDUtil.getUUID());
		// 2.加入用户的userid
		param.put("userid", userid);
		// 3.加入收件人姓名
		param.put("name", name);
		// 4.加入收件人联系电话
		param.put("telno", telno);
		// 5.加入收件人的区域code
		param.put("areaCode", areaCode);
		// 6.加入收件人的详细地址
		param.put("detailAddr", detailAddr);
		// 7.录库
		usersiteMapper.addUserSite(param);
		// 8.成功！
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: addUserSite</p>
	 * <p>Description: 修改用户的收货地址</p>
	 * @param name 
	 * 			收货人姓名
	 * @param 收货地址id 
	 * 			siteid
	 * @param telno 
	 * 			收货人手机号码
	 * @param areaCode 
	 * 			区域code
	 * @param detailAddr 
	 * 			详细地址
	 * @return ResultBean
	 * link: @see dianfan.service.usersite.UsersiteService#addUserSite(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updateUserSite",description = "修改用户收货地址")
	public ResultBean updateUserSite(String name, String telno, String areaCode, String detailAddr,String siteid, String userid) {
		// 1.确认数据库中是否存在该用户的收货地址
		Map<String, Object> data = new HashMap<>();
		data.put("siteid", siteid);
		data.put("userid", userid);
		int count = usersiteMapper.fildUserSiteByid(data);
		if (count < 1) {
			// !不存在该条收货地址
			return new ResultBean("4013",ResultApiMsg.C_4013);
		}
		// ---创建入参容器----
		Map<String, Object> param = new HashMap<>();
		// 1.加入用户id
		param.put("userid", userid);
		// 2.加入收货地址id
		param.put("siteid", siteid);
		// 3.加入收件人姓名
		param.put("name", name);
		// 4.加入收件人联系电话
		param.put("telno", telno);
		// 5.加入收件人的区域code
		param.put("areaCode", areaCode);
		// 6.加入收件人的详细地址
		param.put("detailAddr", detailAddr);
		// 7.更新该条收货地址
		usersiteMapper.updateUserSite(param);
		// 8.成功！
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: delUserSiteByid</p>
	 * <p>Description: 删除收货地址</p>
	 * @param siteid 收货地址id
	 * @return ResultBean
	 * link: @see dianfan.service.usersite.UsersiteService#delUserSiteByid(java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delUserSiteByid",description = " 删除收货地址")
	public ResultBean delUserSiteByid(String siteid ,String userid) {
		//创建入参容器
		Map<String, Object> param = new HashMap<>();
		//加入入参
		param.put("siteid", siteid);
		param.put("userid", userid);
		
		// 1.确认数据库中是否存在该条数据
		int count = usersiteMapper.checkUserSiteByid(param);
		if (count < 1) {
			// !错误，不存在对应的收货地址
			return new ResultBean("4013",ResultApiMsg.C_4013);
		}
		// 库操作
		usersiteMapper.delUserSiteByid(param);
		// 成功返回
		return new ResultBean();
	}
	
	
	
	
	
	
	
}
