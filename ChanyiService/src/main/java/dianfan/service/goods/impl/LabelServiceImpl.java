package dianfan.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.goods.GoodsLabelMapper;
import dianfan.entities.goods.GoodsLabels;
import dianfan.models.ResultBean;
import dianfan.service.goods.LabelService;

/**
 * @ClassName LabelServiceImpl
 * @Description 商品标签服务实现
 * @author cjy
 * @date 2018年7月17日 上午11:12:05
 */
@Service
public class LabelServiceImpl implements LabelService {
	
	/**
	 * 注入： #GoodsLabelMapper
	 */
	@Autowired
	private GoodsLabelMapper goodsLabelMapper;

	/*
	 * (non-Javadoc)
	 * <p>Title: findGoodsLabelList</p>
	 * <p>Description: 获取商品标签列表</p>
	 * @return
	 * link: @see dianfan.service.goods.LabelService#findGoodsLabelList()
	 */
	@Override
	@SystemServiceLog(method = "findGoodsLabelList",description = "获取商品标签列表")
	public List<GoodsLabels> findGoodsLabelList() {
		return goodsLabelMapper.findGoodsLabelList();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: addGoodsLabel</p>
	 * <p>Description: 新增商品标签</p>
	 * @param labelName 商品标签名称
	 * @param userid 添加者userid
	 * @return
	 * link: @see dianfan.service.goods.LabelService#addGoodsLabel(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addGoodsLabel",description = "新增商品标签")
	public ResultBean addGoodsLabel(String labelName, String userid) {
		//商品标签名称重复性检测
		boolean bool = goodsLabelMapper.checkGoodsLabelName(null, labelName);
		if(bool) {
			//名称重复
			return new ResultBean("3001", "该商品标签名称" + ResultBgMsg.C_3001);
		}
		//检测通过，可以添加
		goodsLabelMapper.addGoodsLabel(labelName, userid);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: editGoodsLabel</p>
	 * <p>Description: 商品标签修改</p>
	 * @param labelid 商品标签id
	 * @param labelName 商品标签名称
	 * @param userid 添加者userid
	 * @return
	 * link: @see dianfan.service.goods.LabelService#editGoodsLabel(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "editGoodsLabel",description = "商品标签修改")
	public ResultBean editGoodsLabel(String labelid, String labelName, String userid) {
		//商品标签名称重复性检测
		boolean bool = goodsLabelMapper.checkGoodsLabelName(labelid, labelName);
		if(bool) {
			//名称重复
			return new ResultBean("3001", "该商品标签名称" + ResultBgMsg.C_3001);
		}
		//检测通过，可以修改
		goodsLabelMapper.editGoodsLabel(labelid, labelName, userid);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: delGoodsLabel</p>
	 * <p>Description: 商品标签删除</p>
	 * @param labelids 商品标签id数组
	 * @param userid 更新者userid
	 * @return
	 * link: @see dianfan.service.goods.LabelService#delGoodsLabel(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delGoodsLabel",description = "商品标签删除")
	public ResultBean delGoodsLabel(String[] labelids, String userid) {
		goodsLabelMapper.delGoodsLabel(labelids, userid);
		goodsLabelMapper.delGoodsLabelRelation(labelids);
		return new ResultBean();
	}
}