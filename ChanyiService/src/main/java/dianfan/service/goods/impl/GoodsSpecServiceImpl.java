package dianfan.service.goods.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.goods.GoodsSpecMapper;
import dianfan.entities.goods.GoodsAttr;
import dianfan.entities.goods.GoodsPrice;
import dianfan.entities.goods.GoodsSpec;
import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsSpecService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName GoodsSpecServiceImpl
 * @Description 商品规格服务
 * @author cjy
 * @date 2018年7月3日 下午2:40:46
 */
@Service
public class GoodsSpecServiceImpl implements GoodsSpecService {

	@Autowired
	private GoodsSpecMapper goodsSpecMapper;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: findGoodsSpecByGoodsid</p>
	 * <p>Description: 根据商品id获取商品规格列表</p>
	 * @param goodsid 商品id
	 * @return
	 * link: @see dianfan.service.goods.GoodsSpecService#findGoodsSpecByGoodsid(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findGoodsSpecByGoodsid",description = "根据商品id获取商品规格列表 ")
	public ResultBean findGoodsSpecByGoodsid(String goodsid) {
		//根据商品id获取规格
		List<GoodsSpec> spec_list = goodsSpecMapper.findGoodsSpecByGoodsid(goodsid);
		if(spec_list.isEmpty()) {
			//暂无规格
			return new ResultBean(new ArrayList());
		}
		//根据商品id获取商品价格详情
		List<GoodsPrice> good_price = goodsSpecMapper.findGoodsPriceByGoodsid(goodsid);
		if(good_price.isEmpty()) {
			//暂无价格
			return new ResultBean(new ArrayList());
		}
		/************************逻辑处理开始***************************/
		// 规格 价格  的整理
		Map<String, Object> spec_map = new HashMap<>();
		// 循环spec_list
		for (GoodsSpec gs : spec_list) {
			// 判断map中是否存在对应的key
			if (spec_map.containsKey(gs.getSpecCatagory())) {
				List<GoodsSpec> list = (List<GoodsSpec>) spec_map.get(gs.getSpecCatagory());
				// map中存在，就将对应对象存到这个key下面
				list.add(gs);
			} else {
				// 如果不存在，就新建一个list存放对象
				List<GoodsSpec> list = new ArrayList<>();
				list.add(gs);
				// 使用规格名做key，存入map中
				spec_map.put(gs.getSpecCatagory(), list);
			}
		}
		// 整理价格
		Map<String, GoodsPrice> price_map = new HashMap<>();
		// 将上面查询出来的list整理成一个已ids为key,数据为值得map，同时将图片路径该一下
		for (GoodsPrice gp : good_price) {
			price_map.put(gp.getSpecIds(), gp);
			if(StringUtils.isNotEmpty(gp.getGoodsPic()))
				gp.setGoodsPic(PropertyUtil.getProperty("domain")+ gp.getGoodsPic());
		}
		// 创建一个最后需要返回的list
		List<Map<String, Object>> backlist = new ArrayList<>();
		// 整理上面的spec_map 
		if (!spec_map.isEmpty()) {
			for (Entry<String, Object> en : spec_map.entrySet()) {
				Map<String, Object> maps = new HashMap<>();
				maps.put("key", en.getKey());
				maps.put("value", en.getValue());
				backlist.add(maps);
			}
		}
		Map<String, Object> ret = new HashMap<>();
		ret.put("spec", backlist);
		ret.put("price", price_map);
		
		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: findGoodsAttrList</p>
	 * <p>Description: 获取商品属性列表</p>
	 * @return
	 * link: @see dianfan.service.goods.GoodsSpecService#findGoodsAttrList()
	 */
	@Override
	@SystemServiceLog(method = "findGoodsAttrList",description = "获取商品属性列表")
	public ResultBean findGoodsAttrList() {
		List<GoodsAttr> attrList = goodsSpecMapper.findGoodsAttrList();
		// 规格 价格  的整理
		Map<String, List<GoodsAttr>> attr_map = new HashMap<>();
		// 循环spec_list
		for (GoodsAttr ga : attrList) {
			// 判断map中是否存在对应的key
			if (attr_map.containsKey(ga.getParamKey())) {
				List<GoodsAttr> list = (List<GoodsAttr>) attr_map.get(ga.getParamKey());
				// map中存在，就将对应对象存到这个key下面
				list.add(ga);
			} else {
				// 如果不存在，就新建一个list存放对象
				List<GoodsAttr> list = new ArrayList<>();
				list.add(ga);
				// 使用规格名做key，存入map中
				attr_map.put(ga.getParamKey(), list);
			}
		}
		return new ResultBean(attr_map);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: addGoodsAttr</p>
	 * <p>Description: 新增商品属性</p>
	 * @param paramKey 商品属性名
	 * @param paramName 商品属性值
	 * @param userid 添加者id
	 * @return
	 * link: @see dianfan.service.goods.GoodsSpecService#addGoodsAttr(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addGoodsAttr",description = "新增商品属性")
	public ResultBean addGoodsAttr(String paramKey, String paramName, String userid) {
		//商品属性重复性检测
		boolean bool = goodsSpecMapper.checkGoodsAttrName(null, paramKey, paramName);
		if(bool) {
			//商品属性重复
			return new ResultBean("3001", "该商品属性" + ResultBgMsg.C_3001);
		}
		//检测通过，可以添加
		goodsSpecMapper.addGoodsAttr(paramKey, paramName, userid);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: editGoodsAttr</p>
	 * <p>Description: 修改商品属性</p>
	 * @param attrid 商品属性id
	 * @param paramKey 商品属性名
	 * @param paramName 商品属性值
	 * @param userid 修改者id
	 * @return
	 * link: @see dianfan.service.goods.GoodsSpecService#editGoodsAttr(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "editGoodsAttr",description = "修改商品属性")
	public ResultBean editGoodsAttr(String attrid, String paramKey, String paramName, String userid) {
		//商品属性重复性检测
		boolean bool = goodsSpecMapper.checkGoodsAttrName(attrid, paramKey, paramName);
		if(bool) {
			//商品属性重复
			return new ResultBean("3001", "该商品属性" + ResultBgMsg.C_3001);
		}
		//检测通过，可以修改
		goodsSpecMapper.editGoodsAttr(attrid, paramKey, paramName, userid);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: delGoodsAttr</p>
	 * <p>Description: 删除商品属性</p>
	 * @param attrids 商品属性id数组
	 * @param userid 修改者id
	 * @return
	 * link: @see dianfan.service.goods.GoodsSpecService#delGoodsAttr(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delGoodsAttr",description = "删除商品属性")
	public ResultBean delGoodsAttr(String[] attrids, String userid) {
		goodsSpecMapper.delGoodsAttr(attrids, userid);
		goodsSpecMapper.delGoodsAttrRelation(attrids);
		return new ResultBean();
	}

}









