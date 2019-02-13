package dianfan.service.jd.transport;

import com.jd.open.api.sdk.request.ECLP.EclpCoTransportLasWayBillRequest;

import dianfan.models.ResultBean;

/**
 * @ClassName JdTransport
 * @Description 京东物流服务
 * @author cjy
 * @date 2018年7月12日 下午12:50:47
 */
public interface JdTransportService {

	/**
	 * @Title: transportLasWayBill
	 * @Description: 大件纯配运单导入
	 * @param data
	 * @throws:
	 * @time: 2018年7月12日 下午12:54:45
	 */
	ResultBean transportLasWayBill(EclpCoTransportLasWayBillRequest data);
	
	/**
	 * @Title: cancelLwbMain
	 * @Description: 大件取消接口
	 * @param orderid 商家订单编号
	 * @throws:
	 * @time: 2018年7月12日 下午4:02:21
	 */
	ResultBean cancelLwbMain(String orderid);
	
	/**
	 * @Title: queryLwbByCondition
	 * @Description: 大件纯配运单状态查询
	 * @param orderid 商家订单编号
	 * @throws:
	 * @time: 2018年7月12日 下午4:04:28
	 */
	ResultBean queryLwbByCondition(String orderid);
	
}
