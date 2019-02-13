package dianfan.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.syslog.SysLogsMapper;
import dianfan.entities.SysLogs;

/**
 * 
 * @Title: LogOpService.java
 * @Package dianfan.service.impl
 * @Description: log记录日志
 * @author Administrator
 * @date 2018年5月11日 上午11:00:50
 * @version V1.0
 */
@Service
public class LogOpService {
	/**
	 * 系统日志mapper
	 */
	@Autowired
	private SysLogsMapper sysLogsMapper;

	/**
	 * 写系统日志
	 * 
	 * @param logtype
	 *            日志类型
	 * @param description
	 *            描述
	 * @param method
	 *            方法
	 * @param userid
	 *            用户id
	 * @param params
	 *            参数
	 * @param requestip
	 *            请求的ip地址
	 * @throws Exception
	 *             异常
	 */
	@Transactional
	public void writeLog(Long logtype, String description, String method, String userid, String params,
			String requestip) throws Exception {
		SysLogs info = new SysLogs();
		info.setLogtype(logtype);
		info.setMethod(method);
		info.setCreatetime(new Timestamp(new Date().getTime()));
		info.setCreateid(userid);
		info.setDescription(description);
		info.setParams(params);
		info.setRequestip(requestip);
		sysLogsMapper.saveSysLogs(info);
	}
}
