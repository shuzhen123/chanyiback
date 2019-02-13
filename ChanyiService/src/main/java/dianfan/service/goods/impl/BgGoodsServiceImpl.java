package dianfan.service.goods.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.goods.BgGoodsMapper;
import dianfan.entities.FileUploadType;
import dianfan.entities.goods.BgGoods;
import dianfan.entities.goods.GoodsAttr;
import dianfan.entities.goods.GoodsLabels;
import dianfan.entities.goods.GoodsModels;
import dianfan.entities.goods.GoodsPics;
import dianfan.entities.goods.GoodsPrice;
import dianfan.entities.goods.GoodsSpec;
import dianfan.entities.goods.StaffGoods;
import dianfan.md5.MD5;
import dianfan.models.ResultBean;
import dianfan.service.goods.BgGoodsService;
import dianfan.util.PropertyUtil;
import dianfan.util.UUIDUtil;

/**
 * @ClassName BgGoodsServiceImpl
 * @Description 商品服务
 * @author cjy
 * @date 2018年7月17日 下午3:46:47
 */
@Service
public class BgGoodsServiceImpl implements BgGoodsService {
	@Autowired
	private BgGoodsMapper bgGoodsMapper;

	private ObjectMapper mapper = new ObjectMapper();

	/*
	 * (non-Javadoc) <p>Title: findGoodsList</p> <p>Description: </p>
	 * 
	 * @param page 请求页
	 * 
	 * @param pageSize 每页条数
	 * 
	 * @param goodsTitle 商品标题
	 * 
	 * @param easySpelling 是否易拼(0否，1是，默认全部)
	 * 
	 * @param goodsClassifyid 商品分类id
	 * 
	 * @param l_price 非定制的商品价格-低价区间
	 * 
	 * @param h_price 非定制的商品价格-高价区间
	 * 
	 * @param upperlower 上下架(0下架，1上架，默认全部)
	 * 
	 * @param sales_sort 是否按销售数量排序(1是，默认否)
	 * 
	 * @param coll_sort 是否按收藏数量排序(1是，默认否)
	 * 
	 * @param labelid_arr 商品标签数组
	 * 
	 * @param attrid_arr 商品属性数组
	 * 
	 * @param staffid 员工id
	 * 
	 * @return link: @see dianfan.service.goods.BgGoodsService#findGoodsList(int,
	 * int, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 * java.lang.String[], java.lang.String[])
	 */
	@Override
	@SystemServiceLog(method = "findGoodsList", description = "获取商品列表")
	public ResultBean findGoodsList(int page, int pageSize, String goodsTitle, Integer easySpelling,
			String goodsClassifyid, String l_price, String h_price, Integer upperlower, Integer sales_sort,
			Integer coll_sort, Integer price_sort, String[] labelid_arr, String[] attrid_arr, String staffid) {
		Map<String, Object> ret = new HashMap<>();

		// 根据条件获取筛选后的商品数量
		int count = bgGoodsMapper.getScreenGoodsCount(goodsTitle, easySpelling, goodsClassifyid, l_price, h_price,
				upperlower, labelid_arr, attrid_arr, staffid);
		ret.put("total", count);

		// 判断是否超页数
		if (count < (page - 1) * pageSize) {
			ret.put("goodsList", new ArrayList<>());
		} else {
			List<BgGoods> goodsList = bgGoodsMapper.findScreenGoodsList((page - 1) * pageSize, pageSize, goodsTitle,
					easySpelling, goodsClassifyid, l_price, h_price, upperlower, sales_sort, coll_sort, price_sort,
					labelid_arr, attrid_arr, staffid);
			for (BgGoods bg : goodsList) {
				bg.setThumbPic(PropertyUtil.getProperty("domain") + bg.getThumbPic());
			}
			ret.put("goodsList", goodsList);
		}
		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: getGoodsInfoDetail</p> <p>Description: 获取商品详情</p>
	 * 
	 * @param goodsid 商品id
	 * 
	 * @return link: @see
	 * dianfan.service.goods.BgGoodsService#getGoodsInfoDetail(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getGoodsInfoDetail", description = "获取商品详情")
	public ResultBean getGoodsInfoDetail(String goodsid) {
		// 获取商品基本信息
		BgGoods goodsDetail = bgGoodsMapper.getGoodsInfoDetailById(goodsid);
		// 获取商品标签
		List<GoodsLabels> labels = bgGoodsMapper.findGoodsLabelById(goodsid);
		// 获取商品属性
		List<GoodsAttr> attrs = bgGoodsMapper.findGoodsAttrById(goodsid);
		Map<String, Object> attr_cache = new HashMap<>();
		for (GoodsAttr ga : attrs) {
			if (attr_cache.containsKey(ga.getParamKey())) {
				// 存在
				List<GoodsAttr> list = (List<GoodsAttr>) attr_cache.get(ga.getParamKey());
				list.add(ga);
			} else {
				List<GoodsAttr> list = new ArrayList<>();
				list.add(ga);
				attr_cache.put(ga.getParamKey(), list);
			}
		}
		List<Map<String, Object>> attr_list = new ArrayList<>();
		if (!attr_cache.isEmpty()) {
			for (Entry<String, Object> m : attr_cache.entrySet()) {
				Map<String, Object> map_cache = new HashMap<>();
				map_cache.put("attr", m.getKey());
				map_cache.put("attr_vals", m.getValue());
				attr_list.add(map_cache);
			}
		}

		// 获取商品规格
		List<GoodsSpec> specs = bgGoodsMapper.findGoodsSpecById(goodsid);
		Map<String, Object> spec_cache = new HashMap<>();
		for (GoodsSpec gs : specs) {
			if (spec_cache.containsKey(gs.getSpecCatagory())) {
				// 存在
				List<GoodsSpec> list = (List<GoodsSpec>) spec_cache.get(gs.getSpecCatagory());
				list.add(gs);
			} else {
				List<GoodsSpec> list = new ArrayList<>();
				list.add(gs);
				spec_cache.put(gs.getSpecCatagory(), list);
			}
		}
		List<Map<String, Object>> spec_list = new ArrayList<>();
		if (!spec_cache.isEmpty()) {
			for (Entry<String, Object> m : spec_cache.entrySet()) {
				Map<String, Object> map_cache = new HashMap<>();
				map_cache.put("spec", m.getKey());
				map_cache.put("spec_vals", m.getValue());
				spec_list.add(map_cache);
			}
		}

		//

		// 图片域名
		String domain = PropertyUtil.getProperty("domain");

		// 获取商品规格对应的价格
		List<GoodsPrice> prices = bgGoodsMapper.findGoodsPriceById(goodsid);
		for (GoodsPrice gpm : prices) {
			gpm.setGoodsPic((gpm.getGoodsPic() == null || StringUtils.isEmpty(gpm.getGoodsPic().trim())) ? null
					: domain + gpm.getGoodsPic());
		}

		// 获取商品图片
		List<GoodsPics> goodsPic = bgGoodsMapper.findGoodsPicById(goodsid);
		for (GoodsPics gp : goodsPic) {
			gp.setPicAddr((gp.getPicAddr() == null || StringUtils.isEmpty(gp.getPicAddr().trim())) ? null
					: domain + gp.getPicAddr());
		}

		Map<String, Object> ret = new HashMap<>();
		ret.put("goods", goodsDetail);
		ret.put("label", labels);
		ret.put("attr", attr_list);
		ret.put("spec", spec_list);
		ret.put("price", prices);
		ret.put("pic", goodsPic);
		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: addGoods</p> <p>Description: 新增商品</p>
	 * 
	 * @param gm 商品基础数据
	 * 
	 * @param labelid_arr 商品标签数组
	 * 
	 * @param attrid_arr 商品属性数组
	 * 
	 * @param pics 商品图片（json字符串格式）
	 * 
	 * @param specs 商品规格（json字符串格式）
	 * 
	 * @param goodsPrices 商品价格（json字符串格式）
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.goods.BgGoodsService#addGoods(dianfan.entities.goods.
	 * GoodsModels, java.lang.String[], java.lang.String[], java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addGoods", description = "新增商品")
	public ResultBean addGoods(BgGoods gm, String[] labelid_arr, String[] attrid_arr, String pics, String specs,
			String goodsPrices) {
		// 商品id
		String goodsid = UUIDUtil.getUUID();

		// 新增商品基础数据
		gm.setId(goodsid);
		bgGoodsMapper.addGoods(gm);

		if (labelid_arr != null && labelid_arr.length > 0) {
			// 绑定商品与标签关系
			bgGoodsMapper.addGoodsLabelRelation(labelid_arr, goodsid);
		}

		if (attrid_arr != null && attrid_arr.length > 0) {
			// 绑定商品与属性关系
			bgGoodsMapper.addGoodsAttrRelation(attrid_arr, goodsid);
		}

		FileUploadType fut = new FileUploadType();

		// 解析商品图片json字符串
		if (StringUtils.isNotEmpty(pics)) {
			List<Map<String, Object>> pic_list;
			try {
				pic_list = mapper.readValue(pics, List.class);
			} catch (IOException e) {
				// 事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				// 商品图片数据解析失败
				return new ResultBean("3003", "商品图片" + ResultBgMsg.C_3003);
			}
			if (pic_list != null && pic_list.size() > 0) {
				// 图片域名清除
				for (Map<String, Object> map : pic_list) {
					String url = (String) map.get("picAddr");
					if (StringUtils.isNotEmpty(url)) {
						map.put("picAddr", url.substring(url.indexOf(fut.FILE_DIR, 0), url.length()));
					}
				}
				// 添加商品图片
				bgGoodsMapper.addGoodsPics(pic_list, goodsid, gm.getCreateBy());
			}
		}

		if (StringUtils.isNotEmpty(specs) && StringUtils.isNotEmpty(goodsPrices)) {
			// 解析商品规格json字符串
			List<Map<String, Object>> spec_list;
			try {
				spec_list = mapper.readValue(specs, List.class);
			} catch (IOException e) {
				// 事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				// 商品规格数据解析失败
				return new ResultBean("3003", "商品规格" + ResultBgMsg.C_3003);
			}
			// 解析商品规格列表
			List<GoodsSpec> spec_list_data = new ArrayList<>();
			for (Map<String, Object> map : spec_list) {
				List<String> catagory = (List<String>) map.get("catagory");
				for (String cat : catagory) {
					GoodsSpec gs = new GoodsSpec();
					gs.setId(UUIDUtil.getUUID());
					gs.setSpecName(cat);
					gs.setSpecCatagory((String) map.get("name"));
					spec_list_data.add(gs);
				}
			}
			// 添加商品规格
			bgGoodsMapper.addGoodsSpecs(spec_list_data, goodsid, gm.getCreateBy());

			// 字典排序商品规格
			Collections.sort(spec_list_data, new Comparator<GoodsSpec>() {
				@Override
				public int compare(GoodsSpec gs1, GoodsSpec gs2) {
					return (gs1.getSpecName() + gs1.getSpecCatagory())
							.compareTo(gs2.getSpecName() + gs2.getSpecCatagory());
//	                return gs1.getId().compareTo(gs2.getId());
				}
			});

			// 解析商品价格json字符串
			List<Map<String, Object>> price_list;
			try {
				price_list = mapper.readValue(goodsPrices, List.class);
			} catch (IOException e) {
				// 事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				// 商品价格数据解析失败
				return new ResultBean("3003", "商品价格" + ResultBgMsg.C_3003);
			}
			// 解析商品价格列表
			List<GoodsPrice> price_list_data = new ArrayList<>();

			StringBuffer sbid = new StringBuffer();

			for (Map<String, Object> map : price_list) {
				// 清空商品规格
				sbid.delete(0, sbid.length());

				GoodsPrice gp = new GoodsPrice();
				gp.setPrice(new BigDecimal(String.valueOf(map.get("price"))));

				String object = String.valueOf(map.get("price_easy_spelling"));
				if (StringUtils.isNotEmpty(object)) {
					gp.setPriceEasySpelling(new BigDecimal(String.valueOf(map.get("price_easy_spelling"))));
				}
				// 图片域名清除
				String url = (String) map.get("picurl");
				if (StringUtils.isNotEmpty(url)) {
					gp.setGoodsPic(url.substring(url.indexOf(fut.FILE_DIR, 0), url.length()));
				}

				// 商品规格映射
				List<String> spec_l = (List<String>) map.get("spec");

				List<String> specids = new ArrayList<>();

				for (GoodsSpec spec : spec_list_data) {
					if (spec_l.contains(spec.getSpecName())) {
						specids.add(spec.getId());
						sbid.append(spec.getSpecName());
					}
				}
				Collections.sort(specids);
				gp.setSpecIds(StringUtils.join(specids, ","));
				gp.setOnlyIdentity(MD5.string2MD5(sbid.append(goodsid).toString()));
				price_list_data.add(gp);
			}
			// 添加商品价格
			bgGoodsMapper.addGoodsPrices(price_list_data, goodsid, gm.getCreateBy());
		}
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: editGoods</p> <p>Description: 修改商品</p>
	 * 
	 * @param gm 商品基础数据
	 * 
	 * @param labelid_arr 商品标签数组
	 * 
	 * @param attrid_arr 商品属性数组
	 * 
	 * @param pics 商品图片（json字符串格式）
	 * 
	 * @param specs 商品规格（json字符串格式）
	 * 
	 * @param goodsPrices 商品价格（json字符串格式）
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.goods.BgGoodsService#editGoods(dianfan.entities.goods.
	 * GoodsModels, java.lang.String[], java.lang.String[], java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "editGoods", description = "修改商品")
	public ResultBean editGoods(BgGoods gm, String[] labelid_arr, String[] attrid_arr, String pics, String specs,
			String goodsPrices) {
		// 修改商品基础数据
		bgGoodsMapper.editGoods(gm);

		// 商品下架
		if (gm.getUpperlower() == 0) {
			// 将购物车中此价格的商品置为已失效
			bgGoodsMapper.updateGoodsCartStatus1(new String[] { gm.getId() });
			// 将未付款的订单中包含此价格的商品置为被动关闭
			bgGoodsMapper.updateOrderStatus1(new String[] { gm.getId() }, gm.getCreateBy());
		}

		// 删除旧商品与标签关系
		bgGoodsMapper.delGoodsLabelRelation(gm.getId());
		if (labelid_arr != null && labelid_arr.length > 0) {
			// 绑定新商品与标签关系
			bgGoodsMapper.addGoodsLabelRelation(labelid_arr, gm.getId());
		}

		// 删除旧商品与属性关系
		bgGoodsMapper.delGoodsAttrRelation(gm.getId());
		if (attrid_arr != null && attrid_arr.length > 0) {
			// 绑定新商品与属性关系
			bgGoodsMapper.addGoodsAttrRelation(attrid_arr, gm.getId());
		}

		// 删除所有旧图片
		bgGoodsMapper.delGoodsPicsById(gm.getId(), gm.getCreateBy());

		FileUploadType fut = new FileUploadType();

		List<Map<String, Object>> pic_list = null;
		// 解析商品图片json字符串
		if (StringUtils.isNotEmpty(pics)) {
			try {
				pic_list = mapper.readValue(pics, List.class);
			} catch (IOException e) {
				// 事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				// 商品图片数据解析失败
				return new ResultBean("3003", "商品图片" + ResultBgMsg.C_3003);
			}
			if (pic_list != null && pic_list.size() > 0) {
				// 图片域名清除
				for (Map<String, Object> map : pic_list) {
					String url = (String) map.get("picAddr");
					if (StringUtils.isNotEmpty(url)) {
						map.put("picAddr", url.substring(url.indexOf(fut.FILE_DIR, 0), url.length()));
					}
				}
				// 添加商品图片
				bgGoodsMapper.addGoodsPics(pic_list, gm.getId(), gm.getCreateBy());
			}
		}

		if (StringUtils.isNotEmpty(specs) && StringUtils.isNotEmpty(goodsPrices)) {
			// 商品有 规格 和 价格 数据

			// 解析商品规格json字符串
			List<Map<String, Object>> spec_list;
			try {
				spec_list = mapper.readValue(specs, List.class);
			} catch (IOException e) {
				// 事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				// 商品规格数据解析失败
				return new ResultBean("3003", "商品规格" + ResultBgMsg.C_3003);
			}
			// 解析商品规格列表
			List<GoodsSpec> spec_list_data = new ArrayList<>();
			for (Map<String, Object> map : spec_list) {
				List<String> catagory = (List<String>) map.get("catagory");
				for (String cat : catagory) {
					GoodsSpec gs = new GoodsSpec();
					gs.setId(UUIDUtil.getUUID());
					gs.setSpecName(cat);
					gs.setSpecCatagory((String) map.get("name"));
					spec_list_data.add(gs);
				}
			}
			// 删除商品规格
			bgGoodsMapper.delGoodsSpecs(gm.getId(), gm.getCreateBy());
			// 添加商品规格
			bgGoodsMapper.addGoodsSpecs(spec_list_data, gm.getId(), gm.getCreateBy());

			// 字典排序商品规格
			Collections.sort(spec_list_data, new Comparator<GoodsSpec>() {
				@Override
				public int compare(GoodsSpec gs1, GoodsSpec gs2) {
					return (gs1.getSpecName() + gs1.getSpecCatagory())
							.compareTo(gs2.getSpecName() + gs2.getSpecCatagory());
//	                return gs1.getId().compareTo(gs2.getId());
				}
			});

			// 解析商品价格json字符串
			List<Map<String, Object>> price_list;
			try {
				price_list = mapper.readValue(goodsPrices, List.class);
			} catch (IOException e) {
				// 事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				// 商品价格数据解析失败
				return new ResultBean("3003", "商品价格" + ResultBgMsg.C_3003);
			}
			// 解析商品价格列表
			List<GoodsPrice> price_list_data = new ArrayList<>();

			StringBuffer sbid = new StringBuffer();

			for (Map<String, Object> map : price_list) {
				sbid.delete(0, sbid.length());

				GoodsPrice gp = new GoodsPrice();
				gp.setPrice(new BigDecimal(String.valueOf(map.get("price"))));

				String object = String.valueOf(map.get("price_easy_spelling"));
				if (StringUtils.isNotEmpty(object)) {
					gp.setPriceEasySpelling(new BigDecimal(String.valueOf(map.get("price_easy_spelling"))));
				}

				String url = (String) map.get("picurl");
				if (StringUtils.isNotEmpty(url))
					gp.setGoodsPic(url.substring(url.indexOf(fut.FILE_DIR, 0), url.length()));

				// 商品规格映射
				List<String> spec_l = (List<String>) map.get("spec");

				List<String> specids = new ArrayList<>();

				for (GoodsSpec spec : spec_list_data) {
					if (spec_l.contains(spec.getSpecName())) {
						specids.add(spec.getId());
						sbid.append(spec.getSpecName());
					}
				}
				Collections.sort(specids);
				gp.setSpecIds(StringUtils.join(specids, ","));
				gp.setOnlyIdentity(MD5.string2MD5(sbid.append(gm.getId()).toString()));
				price_list_data.add(gp);
			}

			// 获取所有可用商品价格
			List<GoodsPrice> all = bgGoodsMapper.findAvailableGoodsPrice(gm.getId());

			if (all.isEmpty()) {
				// 全为新增
				// 添加新增商品价格
				bgGoodsMapper.addGoodsPrices(price_list_data, gm.getId(), gm.getCreateBy());
				// 将购物车中此价格的商品置为已失效
				bgGoodsMapper.updateGoodsCartStatus1(new String[] {gm.getId()});
				// 将未付款的订单中包含此价格的商品置为被动关闭
				bgGoodsMapper.updateOrderStatus1(new String[] {gm.getId()}, gm.getCreateBy());
				return new ResultBean();
			}

			//获取商品图片
			String pic = null;
			
			if(pic_list != null) {
				for(Map<String, Object> map : pic_list) {
					if(StringUtils.equals(String.valueOf(map.get("picType")), "01")) {
						pic = String.valueOf(map.get("picAddr"));
					}
				}
			}
			
			// 获取保留的商品价格
			List<GoodsPrice> reserve = bgGoodsMapper.findReserveGoodsPrice(price_list_data);
			if (!reserve.isEmpty()) {
				
				for (GoodsPrice gp : price_list_data) {
					Boolean isAdd = true; // 默认是新增的
					for (GoodsPrice gp1 : reserve) {
						if (StringUtils.equals(gp.getOnlyIdentity(), gp1.getOnlyIdentity())) {
							isAdd = false; // 如果。。。则不是新增的
							// 保留数据，更新
							gp.setId(gp1.getId());
							if(StringUtils.isEmpty(gp.getGoodsPic())) gp.setGoodsPic(pic);
							// 更新商品价格
							bgGoodsMapper.updateGoodsPrice(gp, gm.getCreateBy());
							// 更新购物车中此价格的商品
							bgGoodsMapper.updateGoodsCart(gp, gm.getId());
						}
					}
					if (isAdd) {
						// 新增数据，标记
						if(StringUtils.isEmpty(gp.getGoodsPic())) gp.setGoodsPic(pic);
						gp.setId("add");
					}
				}
			}else {
				for(GoodsPrice gp : price_list_data) {
					if(StringUtils.isEmpty(gp.getGoodsPic())) gp.setGoodsPic(pic);
					gp.setId("add");
				}
			}

			// 获取无效的商品价格id列表
			List<String> invalid = new ArrayList<>();
			if (!reserve.isEmpty()) {
				// 部分无效
				for (GoodsPrice gp : all) {
					Boolean isExist = false; // 默认不存在
					for (GoodsPrice gp1 : reserve) {
						if (StringUtils.equals(gp.getId(), gp1.getId())) {
							isExist = true; // 如果有改成存在
						}
					}
					if (!isExist) { // 如果还是不存在
						// 无效
						invalid.add(gp.getId());
					}
				}
			} else {
				// 全部无效
				for (GoodsPrice gp : all) {
					invalid.add(gp.getId());
				}
			}

			if (!invalid.isEmpty()) {
				// 将商品价格逻辑删除
				bgGoodsMapper.delGoodsPriceById(invalid, gm.getCreateBy());
				// 将购物车中此价格的商品置为已失效
				bgGoodsMapper.updateGoodsCartStatus(invalid, gm.getId());
				// 将未付款的订单中包含此价格的商品置为被动关闭
				bgGoodsMapper.updateOrderStatus(invalid, gm.getId(), gm.getCreateBy());
			}

			// 部分新增
			List<GoodsPrice> add_price_data = new ArrayList<>();
			for (GoodsPrice gp : price_list_data) {
				if (StringUtils.equals("add", gp.getId()))
					add_price_data.add(gp);
			}
			if (!add_price_data.isEmpty())
				bgGoodsMapper.addGoodsPrices(add_price_data, gm.getId(), gm.getCreateBy());
		} else {
			// 商品无 规格 和 价格 数据

			// 获取所有可用商品价格
			List<GoodsPrice> allgp = bgGoodsMapper.findAvailableGoodsPrice(gm.getId());
			if (!allgp.isEmpty()) {
				List<String> all = new ArrayList<>();
				for (GoodsPrice gp : allgp) {
					all.add(gp.getId());
				}

				// 将购物车中此价格的商品置为已失效
				bgGoodsMapper.updateGoodsCartStatus(all, gm.getId());

				// 将未付款的订单中包含此价格的商品置为被动关闭
				bgGoodsMapper.updateOrderStatus(all, gm.getId(), gm.getCreateBy());

				// 删除所有商品规格和价格
				bgGoodsMapper.delGoodsSpecAndPriceById(gm.getId(), gm.getCreateBy());
			}
		}
		return new ResultBean();

	}

	/*
	 * (non-Javadoc) <p>Title: delGoods</p> <p>Description: 批量删除商品</p>
	 * 
	 * @param goodsids 商品id数组
	 * 
	 * @param userid 操作者id link: @see
	 * dianfan.service.goods.BgGoodsService#delGoods(java.lang.String[],
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delGoods", description = "批量删除商品")
	public void delGoods(String[] goodsids, String userid) {
		bgGoodsMapper.delGoods(goodsids, userid);
		// 将购物车中此价格的商品置为已失效
		bgGoodsMapper.updateGoodsCartStatus1(goodsids);
		// 将未付款的订单中包含此价格的商品置为被动关闭
		bgGoodsMapper.updateOrderStatus1(goodsids, userid);
	}

	/*
	 * (non-Javadoc) <p>Title: upDownGoods</p> <p>Description: 商品上下架</p>
	 * 
	 * @param goodsids 商品id数组
	 * 
	 * @param action 上下架动作（0下架，1上架）
	 * 
	 * @param operator 操作者id
	 * 
	 * @author cjy link: @see
	 * dianfan.service.goods.BgGoodsService#upDownGoods(java.lang.String[],
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "upDownGoods", description = "商品上下架")
	public void upDownGoods(String[] goodsids, int action, String operator) {
		bgGoodsMapper.updateGoodsUpperLower(goodsids, action, operator);
		if (action == 0) {
			// 将购物车中此价格的商品置为已失效
			bgGoodsMapper.updateGoodsCartStatus1(goodsids);
			// 将未付款的订单中包含此价格的商品置为被动关闭
			bgGoodsMapper.updateOrderStatus1(goodsids, operator);
		}
	}

	/*
	 * (non-Javadoc) <p>Title: findStaffBindGoods</p> <p>Description:
	 * 员工下的商品列表（简单列表）</p>
	 * 
	 * @param staffid 员工id
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.goods.BgGoodsService#findStaffBindGoods(java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "findStaffBindGoods", description = "员工下的商品列表（简单列表）")
	public ResultBean findStaffBindGoods(String staffid) {
		List<StaffGoods> bindGoods = bgGoodsMapper.findStaffBindGoods(staffid);
		return new ResultBean(bindGoods);
	}

	/*
	 * (non-Javadoc) <p>Title: bindStaffGoods</p> <p>Description: 绑定 员工下的商品</p>
	 * 
	 * @param goodsids 商品
	 * 
	 * @param staffid 员工id
	 * 
	 * @param operator 操作者id
	 * 
	 * @author cjy link: @see
	 * dianfan.service.goods.BgGoodsService#bindStaffGoods(java.lang.String[],
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "bindStaffGoods", description = "绑定 员工下的商品")
	public ResultBean bindStaffGoods(String goodsids, String staffid, String operator) {
		List<Map<String, Object>> goods_list = new ArrayList<>();
		try {
			goods_list = mapper.readValue(goodsids, List.class);
		} catch (IOException e) {
			// 商品数据解析失败
			return new ResultBean("3003", "商品数据" + ResultBgMsg.C_3003);
		}

		// 删除原用户绑定的商品
		bgGoodsMapper.unbindGoods(null, staffid, operator);
		if (goods_list != null && goods_list.size() > 0) {
			// 绑定员工商品
			bgGoodsMapper.bindGoods(goods_list, staffid, operator);
		}
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: unbindStaffGoods</p> <p>Description: 解绑 员工下的商品</p>
	 * 
	 * @param goodsids 商品id数组
	 * 
	 * @param staffid 员工id
	 * 
	 * @param operator 操作者id
	 * 
	 * @author cjy link: @see
	 * dianfan.service.goods.BgGoodsService#unbindStaffGoods(java.lang.String[],
	 * java.lang.String, int, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "unbindStaffGoods", description = "解绑 员工下的商品")
	public void unbindStaffGoods(String[] goodsids, String staffid, String operator) {
		bgGoodsMapper.unbindGoods(goodsids, staffid, operator);
	}

	/*
	 * (non-Javadoc) <p>Title: sortStaffGoods</p> <p>Description: 排序员工下的商品</p>
	 * 
	 * @param goodsSortData 商品排序信息
	 * 
	 * @param staffid 员工id
	 * 
	 * @param operator 操作者id
	 * 
	 * @author cjy link: @see
	 * dianfan.service.goods.BgGoodsService#sortStaffGoods(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "sortStaffGoods", description = "排序员工下的商品")
	public ResultBean sortStaffGoods(String goodsSortData, String staffid, String operator) {
		// 解析商品排序json数据
		List<Map<String, Object>> sort_list = new ArrayList<>();
		try {
			sort_list = mapper.readValue(goodsSortData, List.class);
		} catch (IOException e) {
			// 商品图片数据解析失败
			return new ResultBean("3003", "商品排序数据" + ResultBgMsg.C_3003);
		}

		if (sort_list.size() > 0) {
			List<StaffGoods> data = new ArrayList<>();
			for (Map<String, Object> map : sort_list) {
				StaffGoods sg = new StaffGoods();
				sg.setGoodsid((String) map.get("goodsid"));
				sg.setSort((int) map.get("sort"));
				data.add(sg);
			}
			// 更新商品排序数据
			bgGoodsMapper.updateStaffGoodsSort(data, staffid, operator);
		}
		return new ResultBean();
	}

}
