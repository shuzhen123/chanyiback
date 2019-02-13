package dianfan.service.jd.transport.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ECLP.EclpOpenService.CoResult;
import com.jd.open.api.sdk.domain.ECLP.EclpOpenService.Result;
import com.jd.open.api.sdk.request.ECLP.EclpCoCancelLwbMainRequest;
import com.jd.open.api.sdk.request.ECLP.EclpCoQueryLwbByConditionRequest;
import com.jd.open.api.sdk.request.ECLP.EclpCoTransportLasWayBillRequest;
import com.jd.open.api.sdk.response.ECLP.EclpCoCancelLwbMainResponse;
import com.jd.open.api.sdk.response.ECLP.EclpCoQueryLwbByConditionResponse;
import com.jd.open.api.sdk.response.ECLP.EclpCoTransportLasWayBillResponse;

import dianfan.logger.Logger;
import dianfan.models.ResultBean;
import dianfan.service.jd.transport.JdTransportService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName JdTransportServiceImpl
 * @Description 京东物流服务实现
 * @author cjy
 * @date 2018年7月12日 下午12:53:11
 */
@Service
public class JdTransportServiceImpl implements JdTransportService {

	/*
	 * (non-Javadoc) <p>Title: transportLasWayBill</p> <p>Description: 大件纯配运单导入</p>
	 * 
	 * @param data link: @see
	 * dianfan.service.jd.transport.JdTransportService#transportLasWayBill(java.lang
	 * .String)
	 */
	@Override
	public ResultBean transportLasWayBill(EclpCoTransportLasWayBillRequest data) {
		JdClient client = new DefaultJdClient(PropertyUtil.getProperty("jd.api.url"), // SERVER_URL
				PropertyUtil.getProperty("jd.app.access_token"), // accessToken
				PropertyUtil.getProperty("jd.app.key"), // appKey
				PropertyUtil.getProperty("jd.app.secret"));// appSecret

		data.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo")); // 事业部编号

		try {
			try {
				Logger.error(data.getAppJsonParams());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EclpCoTransportLasWayBillResponse response = client.execute(data);
			System.err.println("+++++++++++++++++" + response + "++++++++++++++++");

			/* return new ResultBean(response); */
			CoResult v1 = response.getV1();
			if (StringUtils.isEmpty(v1.getLwbNo())) {
				// 下单失败
				Logger.error(String.valueOf(v1.getResultCode()) + "\r\n" + v1.getResultMsg());
				return new ResultBean(String.valueOf(v1.getResultCode()), v1.getResultMsg());
			} else {
				// 下单成功
				return new ResultBean(v1.getLwbNo());
			}
		} catch (JdException e) {
			e.printStackTrace();
			return new ResultBean("500", e.getMessage());
		}
	}

	/*
	 * (non-Javadoc) <p>Title: cancelLwbMain</p> <p>Description: 大件取消接口</p>
	 * 
	 * @param orderid 商家订单编号 link: @see
	 * dianfan.service.jd.transport.JdTransportService#cancelLwbMain(java.lang.
	 * String)
	 */
	@Override
	public ResultBean cancelLwbMain(String orderid) {
		JdClient client = new DefaultJdClient(PropertyUtil.getProperty("jd.api.url"), // SERVER_URL
				PropertyUtil.getProperty("jd.app.access_token"), // accessToken
				PropertyUtil.getProperty("jd.app.key"), // appKey
				PropertyUtil.getProperty("jd.app.secret"));// appSecret

		EclpCoCancelLwbMainRequest request = new EclpCoCancelLwbMainRequest();

		request.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
		request.setOrderNo(orderid);

		try {
			EclpCoCancelLwbMainResponse response = client.execute(request);
			Result res = response.getCancelLwbMainResult();
			if (res != null && "1".equals(res.getResultCode())) {
				return new ResultBean(response);
			} else {
				return new ResultBean(response.getCode(), response.getMsg());
			}

		} catch (JdException e) {
			e.printStackTrace();
			return new ResultBean("500", e.getMessage());
		}
	}

	/*
	 * (non-Javadoc) <p>Title: queryLwbByCondition</p> <p>Description:
	 * 大件纯配运单状态查询</p>
	 * 
	 * @param orderid 商家订单编号 link: @see
	 * dianfan.service.jd.transport.JdTransportService#queryLwbByCondition(java.lang
	 * .String)
	 */
	@Override
	public ResultBean queryLwbByCondition(String orderid) {
		JdClient client = new DefaultJdClient(PropertyUtil.getProperty("jd.api.url"), // SERVER_URL
				PropertyUtil.getProperty("jd.app.access_token"), // accessToken
				PropertyUtil.getProperty("jd.app.key"), // appKey
				PropertyUtil.getProperty("jd.app.secret"));// appSecret

		EclpCoQueryLwbByConditionRequest request = new EclpCoQueryLwbByConditionRequest();

		request.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
		request.setOrderNo(orderid);

		try {
			EclpCoQueryLwbByConditionResponse response = client.execute(request);

			CoResult coResult = response.getCoResult();
			int ret_code = coResult.getResultCode();
			if (ret_code == 0) {
				// 查询失败
				return new ResultBean(String.valueOf(ret_code), coResult.getResultMsg());
			} else {
				// 查询成功
				String retData = response.getMsg();
				ObjectMapper mapper = new ObjectMapper();
				Map map = mapper.readValue(retData, Map.class);

				Map m1 = (Map) map.get("jingdong_eclp_co_queryLwbByCondition_responce");
				Map m2 = (Map) m1.get("coResult");

				try {
					Map m3 = (Map) m2.get("resultData");
					List<Map> m4 = (List) m3.get("lwbStatusInfo");
					return new ResultBean(m4);
				} catch (Exception e) {
					return new ResultBean("暂无物流信息");
				}
			}
		} catch (IOException e) {
			return new ResultBean("500", "物流信息解析出错！");
		} catch (JdException e) {
			return new ResultBean("500", e.getMessage());
		}
	}

}
