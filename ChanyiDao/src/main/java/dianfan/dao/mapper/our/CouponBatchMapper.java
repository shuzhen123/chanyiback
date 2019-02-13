/**  
* @Title: CouponBatchMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年7月26日 下午3:34:55
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dianfan.entities.UserCouponRelate;

/** @ClassName CouponBatchMapper
 * @Description 
 * @author yl
 * @date 2018年7月26日 下午3:34:55
 */
@Repository
public class CouponBatchMapper {
	
	@Autowired
	SqlSessionFactory sqlSessionFactoryBean;
	
	public void addUserCoupon(List<UserCouponRelate> cr){
		SqlSession batchSqlSession = null;
		try {
				batchSqlSession = sqlSessionFactoryBean.openSession(ExecutorType.BATCH, false);
				int batchCount = 2000;// 每批commit的个数
				int batchLastIndex = batchCount;// 每批最后一个的下标
				for (int i = 0; i < cr.size();) {
					if (batchLastIndex >= cr.size()) {
						batchLastIndex = cr.size();
						batchSqlSession.insert("dianfan.dao.mapper.our.CouponMapper.batchAddUCRelate",cr.subList(i, batchLastIndex));
						batchSqlSession.commit();
						System.out.println("index:" + i + " batchLastIndex:" + batchLastIndex);
						break;// 数据插入完毕，退出循环
					}else {
						batchSqlSession.insert("dianfan.dao.mapper.our.CouponMapper.batchAddUCRelate",
								cr.subList(i, batchLastIndex));
						batchSqlSession.commit();
						System.out.println("index:" + i + " batchLastIndex:" + batchLastIndex);
						i = batchLastIndex;// 设置下一批下标
						batchLastIndex = i + (batchCount - 1);
					}
				}
				batchSqlSession.commit();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (batchSqlSession !=null) {
				batchSqlSession.close();
			}	
		}
	}
}
