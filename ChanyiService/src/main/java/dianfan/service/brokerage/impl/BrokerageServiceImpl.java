package dianfan.service.brokerage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.dao.mapper.brokerage.BrokerageMapper;
import dianfan.entities.commission.UserBounsDetail;
import dianfan.entities.order.UserWithdrawDepositStream;
import dianfan.models.ResultBean;
import dianfan.service.brokerage.BrokerageService;

/**
 * @ClassName BrokerageServiceImpl
 * @Description 易盟佣金流水相关 Service
 * @author sz
 * @date 2018年7月25日 下午1:46:34
 */
@Service
public class BrokerageServiceImpl implements BrokerageService {

	/**
	 * 注入： #BrokerageMapper
	 */
	@Autowired
	private BrokerageMapper brokerageMapper;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: findBrokerageList</p>
	 * <p>Description: 获取佣金流水列表</p>
	 * @param userid 登陆者id
	 * @param page 请求页面
	 * @param pageSize 每页请求数 
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param role 角色
	 * @param lowfee 最底佣金
	 * @param upfee 最高佣金
	 * @param user 用户
	 * @return
	 * link: @see dianfan.service.brokerage.BrokerageService#findBrokerageList()
	 */
	@Override
	@SystemServiceLog(method = "findBrokerageList",description = "获取佣金流水列表 ")
	public ResultBean findBrokerageList(String userid, int page, int pageSize, String starttime, String endtime, String role,
			String lowfee, String upfee, String user,String code) {
		// 创建返回模型
		Map<String, Object> data = new HashMap<>();
		
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		// 登陆后台系统的管理员ID
		param.put("userid", userid);
		// 分页数据
		param.put("length", pageSize);
		param.put("start", (page - 1) * pageSize);
		
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		param.put("role", role);
		param.put("lowfee", lowfee);
		param.put("upfee", upfee);
		param.put("code", code);
		// 按用户筛选的条件
		param.put("user", user);
		// 获取列表数量
		int count = brokerageMapper.findBrokerageCount(param);
		data.put("count", count);
		
		if (count == 0 || count < (page - 1) * pageSize) {
			data.put("brokeragelist", new ArrayList<>());
			return new ResultBean(data);
		}
		// 获取佣金列表
		List<UserBounsDetail> brokeragelist = brokerageMapper.findBrokerageList(param);
		
		data.put("brokeragelist", brokeragelist);
		
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: findWithdrawDepositList</p>
	 * <p>Description: 获取用户提现流水表</p>
	 * @param userid 登陆者id
	 * @param page 请求页面
	 * @param pageSize 每页请求数 
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param role 角色
	 * @param lowfee 最底提现
	 * @param upfee 最高提现
	 * @param user 用户
	 * @return
	 * link: @see dianfan.service.brokerage.BrokerageService#findWithdrawDepositList(java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findWithdrawDepositList",description = "获取用户提现流水表")
	public ResultBean findWithdrawDepositList(String userid, int page, int pageSize, String starttime, String endtime,
			String role, String lowfee, String upfee, String user) {
		// 创建返回模型
		Map<String, Object> data = new HashMap<>();
		
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		// 登陆后台系统的管理员ID
		param.put("userid", userid);
		// 分页数据
		param.put("length", pageSize);
		param.put("start", (page - 1) * pageSize);
		
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		param.put("role", role);
		param.put("lowfee", lowfee);
		param.put("upfee", upfee);
		// 按用户筛选的条件
		param.put("user", user);
		// 获取列表数量
		int count = brokerageMapper.findWithdrawDepositCount(param);
		data.put("count", count);
		
		if (count == 0 || count < (page - 1) * pageSize) {
			data.put("list", new ArrayList<>());
			return new ResultBean(data);
		}
		// 获取佣金列表
		List<UserWithdrawDepositStream> list = brokerageMapper.findWithdrawDepositList(param);
		
		data.put("brokeragelist", list);
		
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: withdrawalApproval</p>
	 * <p>Description: 提现申请审批</p>
	 * @param userid  userid
	 * @param flag 状态（1:审批通过 2:审批不通过）
	 * @param id 流水ID
	 * @return
	 * link: @see dianfan.service.brokerage.BrokerageService#withdrawalApproval(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "withdrawalApproval",description = "提现申请审批")
	@Transactional
	public ResultBean updatawithdrawalApproval(String userid, String flag, String ids,String bankFReason,String bankReceiptNo) {
		// ids 整理
		List<String> list = Arrays.asList(ids.split(","));
		// 创建入参模型
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("list", list);
		param.put("flag", flag);
		param.put("bankFReason", bankFReason.trim());
		if (StringUtils.isNotEmpty(bankReceiptNo.trim())) {
			param.put("bankno", bankReceiptNo);
		}else {
			param.put("bankno", null);
		}
		
		// 审批操作
		brokerageMapper.updatawithdrawalApproval(param);
		// 成功返回
		return new ResultBean();
	}
}
