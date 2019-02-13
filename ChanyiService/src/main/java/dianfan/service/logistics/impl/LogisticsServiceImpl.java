/**  
* @Title: LogisticsServiceImpl.java
* @Package dianfan.service.logistics.impl
* @Description: TODO
* @author yl
* @date 2018年7月20日 上午11:28:21
* @version V1.0  
*/
package dianfan.service.logistics.impl;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.ECLP.EclpOpenService.CoResult;
import com.jd.open.api.sdk.request.ECLP.EclpCoQueryLwbByConditionRequest;
import com.jd.open.api.sdk.response.ECLP.EclpCoQueryLwbByConditionResponse;

import dianfan.dao.mapper.logistics.LogisticsMapper;
import dianfan.dao.mapper.order.OrderClassMapper;
import dianfan.entities.logistics.DeliveryShow;
import dianfan.entities.logistics.LogisticsModel;
import dianfan.entities.logistics.OrderDeliveryRelate;
import dianfan.models.ResultBean;
import dianfan.service.logistics.LogisticsService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName LogisticsServiceImpl
 * @Description
 * @author yl
 * @date 2018年7月20日 上午11:28:21
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

	@Autowired
	private LogisticsMapper logisticsMapper;
	@Autowired
	private OrderClassMapper orderClassMapper;

	/*
	 * (non-Javadoc) <p>Title: findLogisticsInfo</p> <p>Description: </p>
	 * 
	 * @param mId 商户单号(不重复)
	 * 
	 * @param deliveryNo 物流单号
	 * 
	 * @param deptNo 事业部编号
	 * 
	 * @param expressNo 快递单号
	 * 
	 * @param senderName 寄件人姓名
	 * 
	 * @param senderMob 寄件人手机
	 * 
	 * @param senderPhone 收件人电话
	 * 
	 * @param rtnReceiverName 返单收件人姓名
	 * 
	 * @param rtnReceiverMob 返单收件人手机号
	 * 
	 * @param rtnReceiverPhone 返单收件人电话
	 * 
	 * @param createtimestart Start(创建时间)
	 * 
	 * @param createtimeend End(创建时间)
	 * 
	 * @param page 请求页
	 * 
	 * @param pageSize 每页条数
	 * 
	 * @return link: @see
	 * dianfan.service.logistics.LogisticsService#findLogisticsInfo(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int,
	 * int)
	 */
	@Override
	public ResultBean findLogisticsInfo(String mId,String jdNo, String deliveryNo, String deptNo, String expressNo,
			String senderName, String senderMob, String senderPhone, String receiverName, String receiverMob,
			String receiverPhone, String rtnReceiverName, String rtnReceiverMob, String rtnReceiverPhone,
			String createtimestart, String createtimeend, int page, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<>();
		LogisticsModel lm = new LogisticsModel();
		lm.setmId(mId);
		lm.setJdNo(jdNo);
		lm.setDeliveryNo(deliveryNo);
		lm.setDeptNo(deptNo);
		lm.setExpressNo(expressNo);
		lm.setSenderName(senderName);
		lm.setSenderMob(senderMob);
		lm.setSenderPhone(senderPhone);
		lm.setReceiverName(receiverName);
		lm.setReceiverMob(receiverMob);
		lm.setReceiverPhone(receiverPhone);
		lm.setRtnReceiverName(rtnReceiverName);
		lm.setRtnReceiverMob(rtnReceiverMob);
		lm.setRtnReceiverPhone(rtnReceiverPhone);
		if (StringUtils.isNotEmpty(createtimestart) && StringUtils.isNotEmpty(createtimeend)) {
			Timestamp ts1 = Timestamp.valueOf(createtimestart);
			lm.setCreateTimeStart(ts1);
			Timestamp ts2 = Timestamp.valueOf(createtimeend);
			lm.setCreateTimeEnd(ts2);
		}
		Integer count = logisticsMapper.getLogisticsNum(lm);
		// 设置总页数
		data.put("totalcount", count);
		if (count < (page - 1) * pageSize || count == 0) {
			// 空的返回实体
			data.put("logisticslist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		lm.setStart((page - 1) * pageSize);
		// 分页偏移量 10
		lm.setOffset(pageSize);
		List<LogisticsModel> lmList = logisticsMapper.findLogisticsList(lm);
		data.put("logisticslist", lmList);
		return new ResultBean(data);
	}

	@Override
	public ResultBean queryBgLwbByCondition(String orderid) {
		List<DeliveryShow> dslist = new ArrayList<>();
		List<OrderDeliveryRelate> deliverid = orderClassMapper.getDeliveryId(orderid);
		List<List<Map>> logisticsinfo = new ArrayList<List<Map>>();
		DeliveryShow ds = null;
		if (deliverid !=null && deliverid.size()>0) {
			for (int i = 0; i < deliverid.size(); i++) {
				LogisticsModel lms = logisticsMapper.getLogisticsInfo(deliverid.get(i).getDeliveryId());
				ds = new DeliveryShow();
				ds.setLms(lms);
				JdClient client = new DefaultJdClient(PropertyUtil.getProperty("jd.api.url"), // SERVER_URL
						PropertyUtil.getProperty("jd.app.access_token"), // accessToken
						PropertyUtil.getProperty("jd.app.key"), // appKey
						PropertyUtil.getProperty("jd.app.secret"));// appSecret

				EclpCoQueryLwbByConditionRequest request = new EclpCoQueryLwbByConditionRequest();

				request.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
				request.setOrderNo(deliverid.get(i).getDeliveryId());

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
						Map m3 = (Map) m2.get("resultData");
						List<Map> m4 = (List) m3.get("lwbStatusInfo");
						logisticsinfo.add(m4);
						ds.setLogisticsinfo(m4);
						ds.setDeliveryStatus(deliverid.get(i).getDeliveryStatus());
						ds.setDeliveryType(deliverid.get(i).getDeliveryType());
						dslist.add(ds);
					}
				} catch (IOException e) {
					return new ResultBean("500", "物流信息解析出错！");
				} catch (JdException e) {
					return new ResultBean(e.getErrCode(), e.getErrMsg());
				}
			}
		}
		return new ResultBean(dslist);

	}

	/*
	 * (non-Javadoc) <p>Title: addLogistics</p> <p>Description: </p>
	 * 
	 * @param mId
	 * 
	 * @param deliveryNo
	 * 
	 * @param deptNo
	 * 
	 * @param expressNo
	 * 
	 * @param senderName
	 * 
	 * @param senderMob
	 * 
	 * @param senderPhone
	 * 
	 * @param senderAddr
	 * 
	 * @param receiverName
	 * 
	 * @param receiverMob
	 * 
	 * @param receiverPhone
	 * 
	 * @param receiverAddr
	 * 
	 * @param remark
	 * 
	 * @param isFragile
	 * 
	 * @param sendTo
	 * 
	 * @param predictDate
	 * 
	 * @param isCod
	 * 
	 * @param receiveable
	 * 
	 * @param onDoorPickUp
	 * 
	 * @param expressTimeReq
	 * 
	 * @param isGuarantee
	 * 
	 * @param jdNo
	 * 
	 * @param guaranteeMoney
	 * 
	 * @param receiptFlag
	 * 
	 * @param pickupDate
	 * 
	 * @param paperFrom
	 * 
	 * @param rtnReceiverName
	 * 
	 * @param rtnReceiverMob
	 * 
	 * @param rtnReceiverAddr
	 * 
	 * @param rtnReceiverPhone
	 * 
	 * @param weight
	 * 
	 * @param length
	 * 
	 * @param width
	 * 
	 * @param height
	 * 
	 * @param installFlag
	 * 
	 * @param thridCategoryNo
	 * 
	 * @param brandNo
	 * 
	 * @param productSku
	 * 
	 * @param packageName
	 * 
	 * @return link: @see
	 * dianfan.service.logistics.LogisticsService#addLogistics(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean addLogistics(String userid, String mId, String deliveryNo, String deptNo, String expressNo,
			String senderName, String senderMob, String senderPhone, String senderAddr, String receiverName,
			String receiverMob, String receiverPhone, String receiverAddr, String remark, String isFragile,
			String sendTo, String predictDate, String isCod, String receiveable, String onDoorPickUp,
			String expressTimeReq, String isGuarantee, String jdNo, String guaranteeMoney, String receiptFlag,
			String pickupDate, String paperFrom, String rtnReceiverName, String rtnReceiverMob, String rtnReceiverAddr,
			String rtnReceiverPhone, String weight, String length, String width, String height, String installFlag,
			String thridCategoryNo, String brandNo, String productSku, String packageName) {
		// TODO Auto-generated method stub
		LogisticsModel lm = new LogisticsModel();
		lm.setBrandNo(brandNo);
		lm.setCreateBy(userid);
		lm.setDeliveryNo(deliveryNo);
		lm.setDeptNo(deptNo);
		lm.setExpressNo(expressNo);
		if (StringUtils.isNotEmpty(expressTimeReq)) {
			lm.setExpressTimeReq(Timestamp.valueOf(expressTimeReq));
		}
		lm.setHeight(height);
		lm.setInstallFlag(installFlag);
		if (StringUtils.isNotEmpty(installFlag) && "1".equals(installFlag)) {
			lm.setThridCategoryNo(thridCategoryNo);
		}
		
		lm.setIsCod(isCod);
		lm.setIsFragile(isFragile);
		lm.setIsGuarantee(isGuarantee);
		if (StringUtils.isNotEmpty(isGuarantee) && "1".equals(isGuarantee)) {
			lm.setGuaranteeMoney(guaranteeMoney);
		}
		lm.setJdNo(jdNo);
		lm.setLength(length);
		lm.setmId(mId);
		lm.setOnDoorPickUp(onDoorPickUp);
		lm.setPackageName(packageName);
		lm.setPaperFrom(paperFrom);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dates = null;
		if (StringUtils.isNotEmpty(pickupDate)) {
			try {
				dates = new Date(format.parse(pickupDate).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lm.setPickupDate(dates);
		}
		if (StringUtils.isNotEmpty(predictDate)) {
			try {
				dates = new Date(format.parse(predictDate).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lm.setPredictDate(dates);
		}
		lm.setProductSku(productSku);
		lm.setReceiptFlag(receiptFlag);
		lm.setReceiveable(receiveable);
		lm.setReceiverAddr(rtnReceiverAddr);
		lm.setReceiverMob(rtnReceiverMob);
		lm.setReceiverName(rtnReceiverName);
		lm.setReceiverPhone(rtnReceiverPhone);
		lm.setRemark(remark);
		lm.setRtnReceiverAddr(rtnReceiverAddr);
		lm.setRtnReceiverMob(rtnReceiverMob);
		lm.setRtnReceiverName(rtnReceiverName);
		lm.setRtnReceiverPhone(rtnReceiverPhone);
		lm.setSenderAddr(senderAddr);
		lm.setSenderMob(senderMob);
		lm.setSenderName(senderName);
		lm.setSenderPhone(senderPhone);
		lm.setSendTo(sendTo);
		lm.setWeight(weight);
		lm.setWidth(width);
		logisticsMapper.addLogistics(lm);
		return new ResultBean();
	}

}
