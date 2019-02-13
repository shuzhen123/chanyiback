package dianfan.service.usermanage.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.userManage.BasicsMapper;
import dianfan.entities.user.AdminPopedoms;
import dianfan.entities.user.SettingSwitchs;
import dianfan.models.ResultBean;
import dianfan.service.usermanage.BasicsService;
import dianfan.util.UUIDUtil;

/**
 * @ClassName BasicsServiceImpl
 * @Description 系统数据
 * @author sz
 * @date 2018年7月23日 下午3:11:10
 */
@Service
public class BasicsServiceImpl implements BasicsService {
	
	/**
	 * 注入： #BasicsMapper
	 */
	@Autowired
	private BasicsMapper basicsMapper;

	/*
	 * (non-Javadoc)
	 * <p>Title: findBasicsData</p>
	 * <p>Description: 获取系统数据 </p>
	 * @return
	 * link: @see dianfan.service.usermanage.BasicsService#findBasicsData()
	 */
	@Override
	@SystemServiceLog(method = "findBasicsData",description = "获取系统数据 ")
	public ResultBean findBasicsData(String userid) {
		// 获取今入新注册用户
		int registered = basicsMapper.findRegisteredData();
		// 获取今日浏览量
		int browse = basicsMapper.findBrowseData();
		// 获取今日订单量
		int order = basicsMapper.findOrderData();
		// 获取今日交易金额 
		Integer transaction = basicsMapper.findTransactionData();
		if (transaction == null) {
			transaction = 0;
		}
		// 创建返回参数模型
		Map<String, Object> data = new HashMap<>();
		data.put("registered", registered);
		data.put("browse", browse);
		data.put("order", order);
		data.put("transaction", transaction);
		
		return new ResultBean(data);
	}

	
	/*
	 * (non-Javadoc)
	 * <p>Title: findSettingSwitchs</p>
	 * <p>Description: 获取系统设置开关</p>
	 * @param userid
	 * @return
	 * link: @see dianfan.service.usermanage.BasicsService#findSettingSwitchs(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findBasicsData",description = "获取系统设置开关")
	public ResultBean findSettingSwitchs(String userid) {
		// 创建返回参数模型
		Map<String, Object> data = new HashMap<>();
		// 获取系统设置开关列表
		List<SettingSwitchs> SettingSwitchs = basicsMapper.findSettingSwitchs();
		data.put("SettingSwitchs", SettingSwitchs);
		
		return new ResultBean(data);
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: findPopedomList</p>
	 * <p>Description: 获取权限列表 自用接口</p>
	 * @param userid
	 * @return
	 * link: @see dianfan.service.usermanage.BasicsService#findPopedomList(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findPopedomList",description = "获取权限列表 自用接口")
	public ResultBean findPopedomList(String userid) {
		// 创建返回参数模型
		Map<String, Object> data = new HashMap<>();
		// 获取权限列表
		List<AdminPopedoms> adminPopedom = basicsMapper.findPopedomList();
		data.put("adminPopedom", adminPopedom);
		// 成功
		return new ResultBean(data);
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: addAdminPopedoms</p>
	 * <p>Description: 添加管理员权限自用</p>
	 * @param userid 用户ID
	 * @param popedomname 权限名称
	 * @param popedomfatherid 权限父ID
	 * @param popedomurl 树的连接路径
	 * @param sort 排序
	 * @param kind kind
	 * @return
	 * link: @see dianfan.service.usermanage.BasicsService#addAdminPopedoms(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "addAdminPopedoms",description = "添加管理员权限自用 自用接口")
	public ResultBean addAdminPopedoms(String userid, String popedomname, String popedomfatherid, String popedomurl,
			String sort, String kind) {
		// 创建入参模型
		Map<String, Object> param = new HashMap<>();
		// 添加入参
		param.put("userid", userid);
		param.put("popedomname", popedomname);
		param.put("popedomfatherid", popedomfatherid);
		param.put("popedomurl", popedomurl);
		param.put("sort", sort);
		param.put("kind", kind);
		// 判断新添加的权限数据库中是否存在
		int count = basicsMapper.getAdminPopedomsCount(param);
		if (count != 0 ) {
			return new ResultBean("4027",ResultBgMsg.C_4027);
		}
		param.put("id", UUIDUtil.getUUID());
		// 入库操作
		basicsMapper.addAdminPopedoms(param);
		// 成功
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: updataAdminPopedoms</p>
	 * <p>Description: 修改管理员权限自用</p>
	 * @param userid 用户ID
	 * @param popedomname 权限名称
	 * @param popedomfatherid 权限父ID
	 * @param popedomurl 树的连接路径
	 * @param sort 排序
	 * @param kind kind
	 * @param id id
	 * @return
	 * link: @see dianfan.service.usermanage.BasicsService#updataAdminPopedoms(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "updataAdminPopedoms",description = "修改管理员权限自用 自用接口")
	public ResultBean updataAdminPopedoms(String userid, String popedomname, String popedomfatherid, String popedomurl,
			String sort, String kind, String id) {
		// 检查修改的权限，在数据库中是否存在
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("popedomname", popedomname);
		int count = basicsMapper.getCheckPopedoms(param);
		if (count != 0) {
			// 该权限已存在
			return new ResultBean("4027",ResultBgMsg.C_4027);
		}
		param.put("popedomfatherid", popedomfatherid);
		param.put("popedomurl", popedomurl);
		param.put("sort", sort);
		param.put("kind", kind);
		basicsMapper.updataAdminPopedoms(param);
		// 成功返回
		return null;
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: delAdminPopedoms</p>
	 * <p>Description: 删除管理员权限自用 自用接口  </p>
	 * @param userid 管理员ID
	 * @param ids 权限IDs
	 * @return
	 * link: @see dianfan.service.usermanage.BasicsService#delAdminPopedoms(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "updataAdminPopedoms",description = "删除管理员权限自用 自用接口")
	public ResultBean delAdminPopedoms(String userid, String ids) {
		//整理ids
		List<String> list = Arrays.asList(ids.split(","));
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("list", list);
		// 删除操作
		/* = 注意：对应表中没有“entkbn”字段，此处的删除为物理删除,慎重！ = */
		basicsMapper.delAdminPopedoms(param);	
		// 成功返回
		return new ResultBean();
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: editSettingSwitchs</p>
	 * <p>Description:修改系统设置</p>
	 * @param userid 用户ID
	 * @param ids 设置ID
	 * @return
	 * link: @see dianfan.service.usermanage.BasicsService#editSettingSwitchs(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	@SystemServiceLog(method = "editSettingSwitchs",description = "修改系统设置")
	public ResultBean updataeditSettingSwitchs(String userid, String ids, int flag,String des) {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("ids", ids);
		param.put("flag", flag);
		param.put("des", des);
		// 确认修改开关 是否重名
		int count = basicsMapper.getCheckSwitch(param);
		if (count != 0 ) {
			// 该系统开关已存在
			return new ResultBean("4028",ResultBgMsg.C_4028);
		}
		// 修改操作
		basicsMapper.updataeditSettingSwitchs(param);	
		// 成功返回
		return new ResultBean();
	}
	
	
	

}
