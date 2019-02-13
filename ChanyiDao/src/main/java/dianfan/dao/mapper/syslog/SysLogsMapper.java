package dianfan.dao.mapper.syslog;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import dianfan.entities.SysLogs;

/**
 * @ClassName SysLogsMapper
 * @Description api日志服务
 * @author cjy
 * @date 2018年1月2日 上午9:15:50
 */
@Repository
public interface SysLogsMapper {
	/**
	 * @Title: saveSysLogs
	 * @Description: 记录API接口请求日志
	 * @param logsInfo
	 *            日志信息
	 * @throws:
	 * @time: 2018年1月2日 上午9:15:36
	 */
	@Insert("insert into t_syslogs(id,description,method,logtype,requestip,params,createid,createtime)  values (replace(uuid(),'-',''), #{description}, #{method}, #{logtype}, #{requestip},#{params}, #{createid}, #{createtime})")
	void saveSysLogs(SysLogs logsInfo);
}
