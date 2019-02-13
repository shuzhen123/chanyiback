package dianfan.service.goods;

import dianfan.models.ResultBean;

/**
 * @ClassName GoodsApplyService
 * @Description 商品申请服务
 * @author cjy
 * @date 2018年7月23日 下午3:01:19
 */
public interface GoodsApplyService {

	/**
	 * @Title: findGoodsApplyList
	 * @Description: 获取商品申请
	 * @param applyStatus 申请状态（00:待审核 01：申请通过 02：未通过）
	 * @param startTime 起始时间筛选
	 * @param endTime 截止时间筛选
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午3:08:15
	 * @author cjy 
	 */
	ResultBean findGoodsApplyList(int page, int pageSize, String applyStatus, String startTime, String endTime);

	/**
	 * @Title: goodsApplyApprove
	 * @Description: 商品申请审批
	 * @param applyid 申请id
	 * @param applyStatus 申请状态（01：申请通过 02：未通过）
	 * @param remark 备注
	 * @param approver 审批者id
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午5:20:00
	 * @author cjy
	 */
	ResultBean goodsApplyApprove(String applyid, String applyStatus, String remark, String approver);
}
