/**  
* @Title: DynamicPlugin.java
* @Package dianfan.interceptor
* @Description: TODO
* @author Administrator
* @date 2018年6月9日 上午11:41:54
* @version V1.0  
*/
package dianfan.interceptor;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import dianfan.datasource.DynamicDataSourceGlobal;
import dianfan.datasource.DynamicDataSourceHolder;

/**
 * @Title: DynamicPlugin.java
 * @Package dianfan.interceptor
 * @Description: mybatis plugin类
 * @author Administrator
 * @date 2018年6月9日 上午11:41:54
 * @version V1.0
 */
@Intercepts({ 
//		@Signature(type = Executor.class, method = "update*", args = { MappedStatement.class, Object.class }),
//		@Signature(type = Executor.class, method = "delete*", args = { MappedStatement.class, Object.class }),
//		@Signature(type = Executor.class, method = "insert*", args = { MappedStatement.class, Object.class }),
//		@Signature(type = Executor.class, method = "query*", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
//		@Signature(type = Executor.class, method = "select*", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) 
		})
public class DynamicPlugin implements Interceptor {

	private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

	private static final Map<String, DynamicDataSourceGlobal> cacheMap = new ConcurrentHashMap<>();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
		if (!synchronizationActive) {
			Object[] objects = invocation.getArgs();
			MappedStatement ms = (MappedStatement) objects[0];

			DynamicDataSourceGlobal dynamicDataSourceGlobal = null;

			if ((dynamicDataSourceGlobal = cacheMap.get(ms.getId())) == null) {
				// 读方法
				if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
					// !selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
					if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
						dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
					} else {
						BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
						String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
						if (sql.matches(REGEX)) {
							dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
						} else {
							dynamicDataSourceGlobal = DynamicDataSourceGlobal.READ;
						}
					}
				} else {
					dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
				}
				System.out.println("设置方法[{}] use [{}] Strategy, SqlCommandType [{}].." + ms.getId() + ","
						+ dynamicDataSourceGlobal.name() + "," + ms.getSqlCommandType().name());
				cacheMap.put(ms.getId(), dynamicDataSourceGlobal);
			}
			DynamicDataSourceHolder.putDataSource(dynamicDataSourceGlobal);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
