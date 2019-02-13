package dianfan.service.easyspelling.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.dao.mapper.easyspelling.EasyspellingMapper;
import dianfan.entities.easyspelling.EasySpellingModel;
import dianfan.models.ResultBean;
import dianfan.service.easyspelling.EasySpellingService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName EasySpellingServiceImpl
 * @Description 易拼业务层
 * @author zwb
 * @date 2018年7月4日 上午11:49:49
 */
@Service
public class EasySpellingServiceImpl implements EasySpellingService {

	/**
	 * 易拼dao
	 */
	@Autowired
	EasyspellingMapper easyspellingMapper;

	/*
	 * (non-Javadoc) <p>Title: findEasySpellingList</p> <p>Description: 查询易拼成团列表</p>
	 * 
	 * @param goodsId
	 * 
	 * @return List<EasySpellingModel> link: @see
	 * dianfan.service.easyspelling.EasySpellingService#findEasySpellingList(java.
	 * lang.String)
	 */
	@Override
	public ResultBean findEasySpellingList(String goodsId, Integer pageNum, Integer count) {
		// 返回的参数map
		Map<String, Object> result = new HashMap<String, Object>();
		// 查询列表的参数
		Map<String, Object> param = new HashMap<String, Object>();
		int easySpellingListCount = easyspellingMapper.getEasySpellingListCount(goodsId);
		// 起始的条数
		int start = (pageNum - 1) * count;
		// 没有数据
		if (easySpellingListCount == 0 || start > easySpellingListCount) {
			// 人员的总数
			result.put("surplusTotalCount", 0);
			result.put("easySpellingList", Collections.emptyList());
		}
		// 有数据
		param.put("goodsId", goodsId);// 商品id
		param.put("start", start);// 起始的条数
		param.put("count", count);// 一页条数
		// 易拼返回类列表
		List<EasySpellingModel> easySpellingList = easyspellingMapper.getEasySpellingList(param);
		if (easySpellingList.size() > 0) {
			// 拼接头像完整地址
			for (EasySpellingModel esm : easySpellingList) {
				esm.setAvatarUrl(PropertyUtil.getProperty("domain") + esm.getAvatarUrl());
			}
			// 拼团人员总数
			int surplusTotalCount = easyspellingMapper.getEasySpellingListPeopleCount(goodsId);
			// 人员总数
			result.put("surplusTotalCount", surplusTotalCount);
			// 拼团列表
			result.put("easySpellingList", easySpellingList);
			return new ResultBean(result);
		}
		// 人员总数
		result.put("surplusTotalCount", 0);
		// 拼团列表
		result.put("easySpellingList", Collections.emptyList());
		return new ResultBean(result);
	}

}
