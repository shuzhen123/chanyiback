package dianfan.service.goods.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultApiMsg;
import dianfan.dao.mapper.goods.GoodsMapper;
import dianfan.entities.goods.GoodsAttr;
import dianfan.entities.goods.GoodsLabels;
import dianfan.entities.goods.GoodsList;
import dianfan.entities.goods.GoodsListLabels;
import dianfan.entities.goods.GoodsModels;
import dianfan.entities.goods.GoodsPics;
import dianfan.entities.goods.GoodsPicsQx;
import dianfan.entities.goods.JoinGoods;
import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName GoodsServiceImpl
 * @Description 商品相关实现
 * @author sz
 * @date 2018年7月3日 上午11:13:52
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	/**
	 * 注入: #GoodsMapper
	 */
	@Autowired
	private GoodsMapper goodsMapper;

	/*
	 * (non-Javadoc) <p>Title: fildGoodsList</p> <p>Description: 获取商品列表 </p>
	 * 
	 * @param joinGoods
	 * 
	 * @return link: @see
	 * dianfan.service.goods.GoodsService#fildGoodsList(dianfan.entities.goods.
	 * JoinGoods)
	 */
	@SystemServiceLog(method = "findGoodsList", description = "获取商品列表 ")
	@Override
	public ResultBean findGoodsList(JoinGoods joinGoods) {
		Map<String, Object> ret = new HashMap<>();
		int flag = 0;
		// 判断是否是有角色的用户
		if (joinGoods.getUserid() != null) {
			// 获取用户下的商品列表
			List<String> goodsids = goodsMapper.findGoodsidsByUserid(joinGoods.getUserid());
			if (!goodsids.isEmpty()) {
				joinGoods.setGoodsids(goodsids);
				flag = 1;
			}
		}

		// 商品属性id筛选
		if (!StringUtils.isEmpty(joinGoods.getAttr())) {
			joinGoods.setAttr_arr(joinGoods.getAttr().split(","));
			// 获取满足商品属性的商品
			List<GoodsAttr> attr = goodsMapper.findGoodsByAttr(joinGoods);
			if (attr.isEmpty()) {
				// 无满足筛选条件的商品
				ret.put("count", 0);
				ret.put("flag", flag);
				ret.put("goods", new ArrayList<>());
				return new ResultBean(ret);
			} else {
				// 匹配满足条件的商品id
				List<String> ids = new ArrayList<>();
				String[] attr_arr = joinGoods.getAttr_arr();
				for (GoodsAttr ga : attr) {
					boolean boo = true;
					for (int i = 0; i < attr_arr.length; i++) {
						if (!ga.getParamName().contains(attr_arr[i])) {
							boo = false;
							break;
						}
					}
					if (boo)
						ids.add(ga.getId());
				}

				if (ids.isEmpty()) {
					// 无满足筛选条件的商品
					ret.put("count", 0);
					ret.put("flag", flag);
					ret.put("goods", new ArrayList<>());
					return new ResultBean(ret);
				} else {
					joinGoods.setGoodsids(ids);
				}
			}
		}

		// 根据条件获取商品数据条数
		int count = goodsMapper.findGoodsCount(joinGoods);
		ret.put("count", count);

		// 判断是否超页数
		if (count < (joinGoods.getPage() - 1) * joinGoods.getPagesize()) {
			ret.put("flag", flag);
			ret.put("goods", new ArrayList<>());
		} else {
			// 数据起始位置
			joinGoods.setPagestart((joinGoods.getPage() - 1) * joinGoods.getPagesize());
			List<GoodsList> goodsList = goodsMapper.findGoodsList(joinGoods);
			for (GoodsList gm : goodsList) {
				gm.setPicAddr(PropertyUtil.getProperty("domain") + gm.getPicAddr());
			}
			ret.put("flag", flag);
			ret.put("goods", goodsList);
		}

		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: findGoodsFiltrate</p> <p>Description: 获取商品筛选项</p>
	 * 
	 * @return link: @see dianfan.service.goods.GoodsService#findGoodsFiltrate()
	 */
	@Override
	@SystemServiceLog(method = "findGoodsFiltrate", description = "获取商品筛选项 ")
	public ResultBean findGoodsFiltrate(String classifyid, Integer easy_spelling) {
		List<GoodsAttr> filtrate = goodsMapper.findGoodsFiltrate(classifyid, easy_spelling);
		Map<String, Object> map = new HashMap<>();
		for (GoodsAttr ga : filtrate) {
			if (map.containsKey(ga.getParamKey())) {
				// 存在
				List<GoodsAttr> list = (List<GoodsAttr>) map.get(ga.getParamKey());
				list.add(ga);
			} else {
				List<GoodsAttr> list = new ArrayList<>();
				list.add(ga);
				map.put(ga.getParamKey(), list);
			}
		}

		List<Map<String, Object>> list = new ArrayList<>();
		if (!map.isEmpty()) {
			for (Entry<String, Object> m : map.entrySet()) {
				Map<String, Object> map_cache = new HashMap<>();
				map_cache.put("attr", m.getKey());
				map_cache.put("attr_vals", m.getValue());
				list.add(map_cache);
			}
		}

		return new ResultBean(list);
	}

	/*
	 * (non-Javadoc) <p>Title: addFavorites</p> <p>Description: 商品加入收藏夹</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param goodsid 商品id
	 * 
	 * @return link: @see
	 * dianfan.service.goods.GoodsService#addFavorites(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addGoodsFavorites", description = "商品加入收藏夹")
	public ResultBean addGoodsFavorites(String userid, String[] ids) {
		// 1.将前端传入的string[] 转成 list<string>
		List<String> goodids = Arrays.asList(ids);
		// 2.创建入参容器
		Map<String, Object> data = new HashMap<>();
		// 2.1加入入参
		data.put("userid", userid);
		data.put("goodids", goodids);
		// 3.检测商品是否已经被收藏过 （存在的商品id）
		List<String> existid = goodsMapper.checkGoodsFavorites(data);

		List<String> used_id = new ArrayList<>();
		// 4.循环查出来的id
		for (String id : goodids) {

			if (!existid.contains(id)) {
				used_id.add(id);
			}
		}
		// 判断是否为空
		if (used_id.size() == 0 || used_id.isEmpty()) {
			return new ResultBean();
		}
		
		//商品收藏数量+1
		goodsMapper.goodsCollectionNumInc(used_id);
		
		// 存在未收藏的id后 就加入收藏
		data.put("goodids", used_id);

		// 5.商品加入收藏夹
		goodsMapper.addGoodsFavorites(data);
		// 6.成功返回
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: delGoodsFavorites</p> <p>Description: 取消收藏</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param goodsid 商品id
	 * 
	 * @return ResultBean link: @see
	 * dianfan.service.goods.GoodsService#delGoodsFavorites(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delGoodsFavorites", description = "取消收藏")
	public ResultBean delGoodsFavorites(String userid, String goodsid) {
		// 2.创建入容器
		Map<String, Object> data = new HashMap<>();
		data.put("userid", userid);
		data.put("goodsid", goodsid);
		// 3.取消商品收藏操作
		goodsMapper.delGoodsFavorites(data);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: getGoodsDetails</p> <p>Description: 获取商品的详情</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param goodsid 商品id
	 * 
	 * @return ResultBean link: @see
	 * dianfan.service.goods.GoodsService#getGoodsDetails(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getGoodsDetails", description = "获取商品的详情")
	public ResultBean getGoodsDetails(String userid, String goodsid) {
		// 1.判断商品是否存在
		int count = goodsMapper.getGoodsByid(goodsid);
		if (count < 1) {
			// !商品已经下架
			return new ResultBean("4100", ResultApiMsg.C_4100);
		}
		// 2.创建入容器
		Map<String, Object> data = new HashMap<>();
		data.put("userid", userid);
		data.put("goodsid", goodsid);
		// 3.获取商品的详情
		GoodsModels goods = goodsMapper.getGoodsDetails(data);
		/* 商品的标签ids */
		// 获取用户的商品标签id
		List<GoodsListLabels> gls = goodsMapper.fildGoodsListLabels(goodsid);
		List<String> userLabelid = new ArrayList<>();
		for (GoodsListLabels g : gls) {
			userLabelid.add(g.getLabelId());
		}

		// 获取商品标签
		List<GoodsLabels> goodsLabels = goodsMapper.fildGoodsLabels();
		Map<String, String> Labelsmap = new HashMap<>();
		for (GoodsLabels g1 : goodsLabels) {
			// 将商品的标签转成一个<id，name> 的形式
			Labelsmap.put(g1.getId(), g1.getLabelName());
		}
		// 以string的形式去添加商品的标签
		String labName = "";
		for (Object userl : userLabelid) {
			for (Object labelsmap : Labelsmap.keySet()) {
				if (userl.equals(labelsmap)) {
					labName += Labelsmap.get(labelsmap);
				}
			}
		}
		goods.setLabelName(labName);

		if (!StringUtils.isEmpty(goods.getGoodsPics())) {
			goods.setGoodsThumbnail(PropertyUtil.getProperty("domain") + goods.getGoodsThumbnail());
			// 获取详情中的商品的轮播图
			List<GoodsPics> Pics = goods.getGoodsPics();
			// 图片返回到前端前，先将图片的域名加一下
			for (GoodsPics p : Pics) {
				if (!StringUtils.isEmpty(p.getPicAddr())) {
					p.setPicAddr(PropertyUtil.getProperty("domain") + p.getPicAddr());
				}
			}

			// 获取详情中的商品的描述图
			List<GoodsPicsQx> PicsQX = goods.getGoodsDetails();
			for (GoodsPicsQx p : PicsQX) {
				if (!StringUtils.isEmpty(p.getPicAddr())) {
					p.setPicAddr(PropertyUtil.getProperty("domain") + p.getPicAddr());
				}
			}

		}
		return new ResultBean(goods);
	}

	/*
	 * (non-Javadoc) <p>Title: updateGoodsSort</p> <p>Description: 商品自定义排序</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param goodsid 商品id link: @see
	 * dianfan.service.goods.GoodsService#updateGoodsSort(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	public void updateGoodsSort(String userid, String goodsid) {
		Integer maxSort = goodsMapper.getMaxGoodsSort(userid);
		if (maxSort == null) {
			goodsMapper.updateGoodsSort(userid, goodsid, 0);
		} else {
			goodsMapper.updateGoodsSort(userid, goodsid, maxSort + 1);
		}
	}
}
