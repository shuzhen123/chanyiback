package dianfan.dao.mapper.operatelog;



import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName OperateLogMapper
 * @Description 后台全局 业务日志 捕获dao
 * @author sz
 * @date 2018年7月26日 下午5:15:50
 */
@Repository
public interface OperateLogMapper {
	
	/**
	 * @Title: InsertOperateLog
	 * @Description:  添加后台管理员操作日志
	 * @param userid 用户id,记录操作用户
	 * @param popedomid 模块 (模块名称 只存子id)
	 * @param roleid 角色
	 * @param operate 操作  (用户名+角色+操作内容)
	 * @param ip ip地址
	 * @throws:
	 * @time: 2018年7月26日 下午5:23:45
	 */
	@Insert(" INSERT INTO t_operate_log (id,user_id,popedom_id,role_id,operate,time,ip) VALUES (REPLACE(uuid(),'-',''), #{userid},#{popedomid},#{roleid},#{operate},now(),#{ip}) ")
	void InsertOperateLog (@Param("userid") String userid, @Param("popedomid") String popedomid,@Param("roleid") String roleid, @Param("operate")String operate, @Param("ip") String ip );
	
	
	
	
	
	/*--模板--*/
	
	//@Autowired  
	//private HttpServletRequest request; 
	//  从请求中获取ip
	//String ip = IpUtil.getIpAddr(request);
	// 插入系统日志
	// operateLogMapper.InsertOperateLog(adminInfo.getId(), "【登陆】", "用户名+角色+操作内容“例如：后台管理系统.......”", IpUtil.getIpAddr(request));
	
	
	/* ↓↓↓↓插入系统日志↓↓↓↓ */
//	Date date = new Date();
//	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	operateLogMapper.InsertOperateLog(adminInfo.getId(), "【登陆】",dateFormat.format(date)+":账号："+adminInfo.getAccount() +",角色："+ adminInfo.getRoleName() +",操作内容："+"后台管理系统,验证用户登陆,获取用户数据", IpUtil.getIpAddr(request));
	/* ↑↑↑↑插入系统日志↑↑↑↑ */
	
	
}
