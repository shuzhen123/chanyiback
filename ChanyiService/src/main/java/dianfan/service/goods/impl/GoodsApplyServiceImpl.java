package dianfan.service.goods.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.dao.mapper.goods.GoodsApplyMapper;
import dianfan.entities.goods.GoodsApply;
import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsApplyService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName GoodsApplyServiceImpl
 * @Description 商品申请服务实现
 * @author cjy
 * @date 2018年7月23日 下午3:01:49
 */
@Service
public class GoodsApplyServiceImpl implements GoodsApplyService {

	@Autowired
	private GoodsApplyMapper goodsApplyMapper;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: findGoodsApplyList</p>
	 * <p>Description: 获取商品申请列表</p>
	 * @param applyStatus 申请状态（00:待审核 01：申请通过 02：未通过）
	 * @param startTime 起始时间筛选
	 * @param endTime 截止时间筛选
	 * @return
	 * @author cjy
	 * link: @see dianfan.service.goods.GoodsApplyService#findGoodsApplyList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findGoodsApplyList",description = "获取商品申请列表")
	public ResultBean findGoodsApplyList(int page, int pageSize, String applyStatus, String startTime, String endTime) {
		Map<String, Object> ret = new HashMap<>();
		
		//获取满足筛选条件的商品申请数量
		int count = goodsApplyMapper.getGoodsApplyCountByParam(applyStatus, startTime, endTime);
		ret.put("total", count);
		//判断是否超页数
		if(count <= (page-1) * pageSize) {
			ret.put("applyList", new ArrayList<>());
		}else {
			List<GoodsApply> applyList = goodsApplyMapper.findGoodsApplyListByParam((page-1) * pageSize, pageSize, applyStatus, startTime, endTime);
			for(GoodsApply ga : applyList) {
				if(StringUtils.isNotEmpty(ga.getGoodsPic())) ga.setGoodsPic(PropertyUtil.getProperty("domain") + ga.getGoodsPic());
			}
			ret.put("applyList", applyList);
		}
		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: goodsApplyApprove</p>
	 * <p>Description: 商品申请审批</p>
	 * @param applyid 申请id
	 * @param applyStatus 申请状态（01：申请通过 02：未通过）
	 * @param remark 备注
	 * @param approver 审批者id
	 * @return
	 * @author cjy
	 * link: @see dianfan.service.goods.GoodsApplyService#goodsApplyApprove(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "goodsApplyApprove",description = "商品申请审批")
	public ResultBean goodsApplyApprove(String applyid, String applyStatus, String remark, String approver) {
		goodsApplyMapper.goodsApplyApprove(applyid, applyStatus, remark, approver);
		if(StringUtils.equals(applyStatus, "01")) {
			//申请通过
			//获取商品申请详情
			GoodsApply ga = goodsApplyMapper.getUserGoodsApply(applyid);
			ga.setApplyStaffid(approver);
			//获取当前用户的商品最大排序值
			int sort = goodsApplyMapper.getUserGoodsMaxSort(ga.getStaffid());
			//添加商品到用户商品表
			goodsApplyMapper.addUserGoods(ga, sort);
		}
		return new ResultBean();
	}
	
}
