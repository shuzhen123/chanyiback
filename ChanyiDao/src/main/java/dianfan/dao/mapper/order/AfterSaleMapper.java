package dianfan.dao.mapper.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dianfan.entities.order.AfterSaleList;

/**
 * @ClassName AfterSaleMapper
 * @Description 售后dao
 * @author cjy
 * @date 2018年8月7日 上午10:28:52
 */
@Repository
public interface AfterSaleMapper {

	/**
	 * @Title: getAfterSaleCount
	 * @Description: 根据条件获取售后列表数据
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年8月7日 下午1:17:51
	 * @author cjy
	 */
	int getAfterSaleCount(Map<String, Object> param);

	/**
	 * @Title: findAfterSaleList
	 * @Description: 根据条件获取售后列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年8月7日 下午1:18:03
	 * @author cjy
	 */
	List<AfterSaleList> findAfterSaleList(Map<String, Object> param);


}
