/**  
* @Title: LogisticsService.java
* @Package dianfan.service.logistics
* @Description: TODO
* @author yl
* @date 2018年7月20日 上午10:56:16
* @version V1.0  
*/
package dianfan.service.logistics;

import dianfan.models.ResultBean;

/**
 * @ClassName LogisticsService
 * @Description
 * @author yl
 * @date 2018年7月20日 上午10:56:16
 */
public interface LogisticsService {

	/**
	 * @Title: findLogisticsInfo
	 * @Description: 根据以下条件获取物流列表
	 * @param mId
	 *            商户单号(不重复)
	 * @param deliveryNo
	 *            物流单号
	 * @param deptNo
	 *            事业部编号
	 * @param expressNo
	 *            快递单号
	 * @param senderName
	 *            寄件人姓名
	 * @param senderMob
	 *            寄件人手机
	 * @param senderPhone
	 *            收件人电话
	 * @param rtnReceiverName
	 *            返单收件人姓名
	 * @param rtnReceiverMob
	 *            返单收件人手机号
	 * @param rtnReceiverPhone
	 *            返单收件人电话
	 * @param createtimestart
	 *            Start(创建时间)
	 * @param createtimeend
	 *            End(创建时间)
	 * @param page
	 *            请求页
	 * @param pageSize
	 *            每页条数
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 上午11:29:02
	 */
	ResultBean findLogisticsInfo(String mId,String jdNo, String deliveryNo, String deptNo, String expressNo, String senderName,
			String senderMob, String senderPhone, String receiverName, String receiverMob, String receiverPhone,
			String rtnReceiverName, String rtnReceiverMob, String rtnReceiverPhone, String createtimestart,
			String createtimeend, int page, int pageSize);

	/**
	 * @Title: queryBgLwbByCondition
	 * @Description:
	 * @param orderid
	 * @return
	 * @throws:
	 * @time: 2018年7月31日 上午10:52:21
	 */
	ResultBean queryBgLwbByCondition(String orderid);

	/**
	 * @Title: addLogistics
	 * @Description: 
	 * @param userid 用户id
	 * @param mId 商户单号(不重复)
	 * @param deliveryNo 物流单号
	 * @param deptNo 事业部编号
	 * @param expressNo 快递单号
	 * @param senderName 寄件人姓名
	 * @param senderMob 寄件人手机
	 * @param senderPhone 寄件人电话
	 * @param senderAddr 寄件人地址
	 * @param receiverName 收件人姓名
	 * @param receiverMob 收件人手机
	 * @param receiverPhone 收件人电话
	 * @param receiverAddr 收货人地址
	 * @param remark 订单备注
	 * @param isFragile 是否易碎（1：是2：否）
	 * @param sendTo 始发转运中心名称
	 * @param predictDate 预计到仓时间
	 * @param isCod 是否货到付款（1：是 0：否）
	 * @param receiveable 代收金额
	 * @param onDoorPickUp 上门揽件标记（1：是2：否）
	 * @param expressTimeReq 送货时间
	 * @param isGuarantee 是否保价（1：是2：否）
	 * @param jdNo 京东单号
	 * @param guaranteeMoney 保价金额
	 * @param receiptFlag 签单返还（0无签单，1纸质签单，2电子签单，3纸质签单和电子签单）
	 * @param pickupDate 上门揽件时间
	 * @param paperFrom 纸质来源（1带单2取单3带单和取单）
	 * @param rtnReceiverName 返单收件人姓名
	 * @param rtnReceiverMob 返单收件人手机号
	 * @param rtnReceiverAddr 返单收件人地址
	 * @param rtnReceiverPhone 返单收件人电话
	 * @param weight 重量（kg）
	 * @param length 长度(mm)
	 * @param width 宽度(mm)
	 * @param height 高度(mm)
	 * @param installFlag 是否安维
	 * @param thridCategoryNo 三级分类编码（安维必填）
	 * @param brandNo 品牌id
	 * @param productSku 商品sku
	 * @param packageName 物品内容
	 * @return
	 * @throws:
	 * @time: 2018年7月31日 上午11:17:44
	 */
	ResultBean addLogistics(String userid,String mId, String deliveryNo, String deptNo, String expressNo, String senderName,
			String senderMob, String senderPhone, String senderAddr, String receiverName, String receiverMob,
			String receiverPhone, String receiverAddr, String remark, String isFragile, String sendTo,
			String predictDate, String isCod, String receiveable, String onDoorPickUp, String expressTimeReq,
			String isGuarantee, String jdNo, String guaranteeMoney, String receiptFlag, String pickupDate,
			String paperFrom, String rtnReceiverName, String rtnReceiverMob, String rtnReceiverAddr,
			String rtnReceiverPhone, String weight, String length, String width, String height, String installFlag,
			String thridCategoryNo, String brandNo, String productSku, String packageName);

}
