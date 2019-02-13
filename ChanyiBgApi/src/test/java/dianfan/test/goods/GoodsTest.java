package dianfan.test.goods;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.dao.mapper.goods.BgGoodsMapper;
import dianfan.entities.goods.BgGoods;
import dianfan.entities.goods.GoodsModels;
import dianfan.entities.goods.GoodsPics;
import dianfan.entities.goods.GoodsPrice;
import dianfan.entities.goods.GoodsSpec;
import dianfan.models.ResultBean;
import dianfan.service.goods.BgGoodsService;
import dianfan.util.UUIDUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-test.xml", "classpath:spring-redis.xml"})
public class GoodsTest {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private BgGoodsMapper bgGoodsMapper;
	@Autowired
	private BgGoodsService bgGoodsService;
	
	@Test
	public void specs() {
		List<GoodsSpec> specs = bgGoodsMapper.findGoodsSpecById("2c8076fe9ee6427da9b9746dfc541c74");
		Map<String, Object> spec_cache = new HashMap<>();
		for(GoodsSpec gs : specs) {
			if(spec_cache.containsKey(gs.getSpecCatagory())) {
				//存在
				List<GoodsSpec> list = (List<GoodsSpec>) spec_cache.get(gs.getSpecCatagory());
				list.add(gs);
			}else {
				List<GoodsSpec> list = new ArrayList<>();
				list.add(gs);
				spec_cache.put(gs.getSpecCatagory(), list);
			}
		}
		List<Map<String, Object>> spec_list = new ArrayList<>();
		if(!spec_cache.isEmpty()) {
			for(Entry<String, Object> m : spec_cache.entrySet()) {
				Map<String, Object> map_cache = new HashMap<>();
				map_cache.put("spec", m.getKey());
				map_cache.put("spec_vals", m.getValue());
				spec_list.add(map_cache);
			}
		}
		
		System.out.println(spec_list);
	}
	
	@Test
	public void addGoods1() throws JsonProcessingException {
		Map<String, Object> m1 = new HashMap<>();
		m1.put("picId", "081fb1448bc311e88dd352540054a904");
		m1.put("picType", null);
		m1.put("picSort", 1);
		m1.put("picAddr", null);
		m1.put("picPcMobile", null);
		
		Map<String, Object> m2 = new HashMap<>();
		m2.put("picId", null);
		m2.put("picType", "01");
		m2.put("picSort", 2);
		m2.put("picAddr", "upload/goods/20180718/qwqwqw.png");
		m2.put("picPcMobile", "01");
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		String string = mapper.writeValueAsString(list);
		System.out.println(string);
	}
	
	@Test
	public void addGoods2() throws JsonProcessingException {
		Map<String, Object> m1 = new HashMap<>();
		List<String> list1 = new ArrayList<>();
		list1.add("150*180");
		list1.add("180*200");
		list1.add("200*250");
		m1.put("name", "尺寸");
		m1.put("catagory", list1);
		
		Map<String, Object> m2 = new HashMap<>();
		List<String> list2 = new ArrayList<>();
		list2.add("红");
		list2.add("黄");
		list2.add("蓝");
		m2.put("name", "颜色");
		m2.put("catagory", list2);
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		String string = mapper.writeValueAsString(list);
		System.out.println(string);
	}
	
	@Test
	public void addGoods3() throws JsonProcessingException {
		Map<String, Object> m1 = new HashMap<>();
		List<String> list1 = new ArrayList<>();
		list1.add("150*180");
		list1.add("红");
		m1.put("spec", list1);
		m1.put("price", "1000.00");
		m1.put("price_easy_spelling", "900.00");
		m1.put("picurl", "upload/goods/20180723/ac.png");
		
		Map<String, Object> m2 = new HashMap<>();
		List<String> list2 = new ArrayList<>();
		list2.add("180*200");
		list2.add("黄");
		m2.put("spec", list2);
		m2.put("price", "3500.00");
		m2.put("price_easy_spelling", "2999.99");
		m2.put("picurl", "upload/goods/20180723/sd.png");
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		String string = mapper.writeValueAsString(list);
		System.out.println(string);
	}
	
	@Test
	public void addGoodsLabelRelation() {
		String[] labelid_arr = {"aaa", "bbb", "ccc"};
		bgGoodsMapper.addGoodsLabelRelation(labelid_arr , "xxx");
	}
	
	@Test
	public void addGoodsAttrRelation() {
		String[] attrid_arr = {"aaa", "bbb", "ccc"};
		bgGoodsMapper.addGoodsAttrRelation(attrid_arr , "xxx");
	}
	
	@Test
	public void addGoodsPics() {
		String pics = "[{\"picAddr\":\"upload/goods/20180718/asasa.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":1},{\"picAddr\":\"upload/goods/20180718/qwqwqw.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":2}]";
		List<GoodsPics> pic_list;
		try {
			pic_list = mapper.readValue(pics, List.class);
		} catch (IOException e) {
			System.err.println("商品图片数据解析失败");
			return;
		}
		
		System.out.println(pic_list);
		
		/*
		String pics = "[{\"picAddr\":\"upload/goods/20180718/asasa.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":1},{\"picAddr\":\"upload/goods/20180718/qwqwqw.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":2}]";
		List<Map<String, String>> pic_list;
		try {
			pic_list = mapper.readValue(pics, List.class);
		} catch (IOException e) {
			System.err.println("商品图片数据解析失败");
			return;
		}
		//解析商品数据列表
		List<GoodsPics> pic_list_data = new ArrayList<>();
		for(Map<String, String> map : pic_list) {
			GoodsPics gp = new GoodsPics();
			gp.setGoodsId("aaa");
			gp.setPicType(map.get("type"));
			gp.setPicAddr(map.get("addr"));
			gp.setPicPcMobile(map.get("pc_mobile"));
			gp.setCreateBy("xxx");
			pic_list_data.add(gp);
		}
		//添加商品图片
		bgGoodsMapper.addGoodsPics(pic_list_data);
		*/
	}
	
	@Test
	public void addGoodsSpecs() {
		String specs = "[{\"catagory\":[\"150*180\",\"180*200\",\"200*250\"],\"name\":\"尺寸\"},{\"catagory\":[\"红\",\"黄\",\"蓝\"],\"name\":\"颜色\"}]";
		//解析商品规格json字符串
		List<Map<String, Object>> spec_list;
		try {
			spec_list = mapper.readValue(specs, List.class);
		} catch (IOException e) {
			System.err.println("商品规格数据解析失败");
			return;
		}
		//解析商品规格列表
		List<GoodsSpec> spec_list_data = new ArrayList<>();
		for(Map<String, Object> map : spec_list) {
			List<String> catagory = (List<String>) map.get("catagory");
			for(String cat : catagory) {
				GoodsSpec gs = new GoodsSpec();
				gs.setId(UUIDUtil.getUUID());
				gs.setSpecName((String) map.get("name"));
				gs.setSpecCatagory(cat);
				spec_list_data.add(gs);
			}
		}
		//添加商品规格
		bgGoodsMapper.addGoodsSpecs(spec_list_data, "abc", "xxx");
		
		//字典排序商品规格
		Collections.sort(spec_list_data, new Comparator<GoodsSpec>() {
            @Override
            public int compare(GoodsSpec gs1, GoodsSpec gs2) {
                return gs1.getSpecName().compareTo(gs2.getSpecName());
            }
        });
		
		String goodsPrices = "[{\"price\":\"1000.00\",\"price_easy_spelling\":\"900.00\",\"spec\":[\"150*180\",\"红\"]},{\"price\":\"3500.00\",\"price_easy_spelling\":\"2999.99\",\"spec\":[\"180*200\",\"黄\"]}]";
		//解析商品价格json字符串
		List<Map<String, Object>> price_list;
		try {
			price_list = mapper.readValue(goodsPrices, List.class);
		} catch (IOException e) {
			System.err.println("商品价格数据解析失败");
			return;
		}
		//解析商品价格列表
		List<GoodsPrice> price_list_data = new ArrayList<>();
		 
		StringBuffer sb = new StringBuffer();
		
		for(Map<String, Object> map : price_list) {
			//清空商品规格
			sb.delete(0, sb.length());
			
			GoodsPrice gp = new GoodsPrice();
			gp.setPrice(new BigDecimal((String)map.get("price")));
			gp.setPriceEasySpelling(new BigDecimal((String)map.get("price_easy_spelling")));
			//商品规格映射
			List<String> spec_l = (List<String>) map.get("spec");
			
			for(GoodsSpec spec : spec_list_data){
				if(spec_l.contains(spec.getSpecCatagory())) {
					sb.append(spec.getId()).append(",");
				}
			}
			gp.setSpecIds(sb.substring(0, sb.lastIndexOf(",")-1).toString());
			price_list_data.add(gp);
		}
		
		//添加商品价格
		bgGoodsMapper.addGoodsPrices(price_list_data, "abc", "xxx");
	}
	
	@Test
	public void addGoods() {
		BgGoods gm = new BgGoods();
		gm.setGoodsTitle("我的商品");
		gm.setGoodsTitleEn("my test goods");
		gm.setIsEasySpelling(0);
		gm.setIsStaffEasySpelling(0);
		gm.setGoodsClassifyId("8dc943957db111e88dd352540054a904");
		gm.setGoodsDesc("我的商品描述");
		gm.setPrice(new BigDecimal(199.99));
		gm.setUpperlower(0);
		gm.setGoodsCategory("01");
		gm.setPriceEasySpelling(new BigDecimal(188.99));
		gm.setCreateBy("xxx");
		
		String[] labelid_arr = {"1", "2", "3"};
		String[] attrid_arr = {"73c7d31d831f11e88dd352540054a904", "783e1e9b831f11e88dd352540054a904"};
		String pics = "[{\"picAddr\":\"http://abc.com/upload/goods/20180718/asasa.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":1},{\"picAddr\":\"http://abc.com/upload/goods/20180718/qwqwqw.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":2}]";
		String specs = "[{\"catagory\":[\"150*180\",\"180*200\",\"200*250\"],\"name\":\"尺寸\"},{\"catagory\":[\"红\",\"黄\",\"蓝\"],\"name\":\"颜色\"}]";
		String goodsPrices = "[{\"price\":\"1000.00\",\"price_easy_spelling\":\"900.00\",\"spec\":[\"150*180\",\"红\"]},{\"price\":\"3500.00\",\"price_easy_spelling\":\"2999.99\",\"spec\":[\"180*200\",\"黄\"]}]";
		ResultBean bean = bgGoodsService.addGoods(gm, labelid_arr, attrid_arr, pics, specs, goodsPrices);
		System.err.println(bean);
	}
	
	@Test
	public void delGoods() {
		String[] goodsids = {"1ce55beef19a48f48ba42e55d4de03ff", "6790f76172bb4982837dbbad1b60528b"};
		bgGoodsService.delGoods(goodsids, "xxx");
	}
	
	@Test
	public void upDownGoods() {
		String[] goodsids = {"1", "10", "11"};
		bgGoodsService.upDownGoods(goodsids, 0, "cjy");
	}
	
	@Test
	public void findGoodsList() throws JsonProcessingException {
		ResultBean bean = bgGoodsService.findGoodsList(1, 5, null, null, null, "111", "1111", null, null, null, null, null, null, null);
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void getGoodsInfoDetail() throws JsonProcessingException {
		ResultBean bean = bgGoodsService.getGoodsInfoDetail("1895007b864c11e8936d00163e0076b2");
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void editGoods() throws JsonProcessingException {
		BgGoods gm = new BgGoods();
		gm.setId("1544352ad144474191a78cbf0a6da488");
		gm.setGoodsTitle("我的商品111");
		gm.setGoodsTitleEn("my test goods111");
		gm.setIsEasySpelling(0);
		gm.setIsStaffEasySpelling(0);
		gm.setGoodsClassifyId("8dc943957db111e88dd352540054a904");
		gm.setGoodsDesc("我的商品描述111");
		gm.setPrice(new BigDecimal(199.99));
		gm.setUpperlower(0);
		gm.setGoodsCategory("01");
		gm.setPriceEasySpelling(new BigDecimal(188.99));
		gm.setCreateBy("cjy");
		gm.setUpdateBy("cjy");
		
		String[] labelid_arr = {"1", "2", /*"3",*/ "4"};
		String[] attrid_arr = {"73c7d31d831f11e88dd352540054a904", /*"783e1e9b831f11e88dd352540054a904", */"d9d320b8831f11e88dd352540054a904"};
		String pics = "[{\"picAddr\":\"http://abc.com/upload/goods/20180718/aaaaaaa.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":1},{\"picAddr\":\"http://abc.com/upload/goods/20180718/bbbbbbb.png\",\"picType\":\"01\",\"picPcMobile\":\"01\",\"picSort\":2}]";
		String specs = "[{\"catagory\":[\"150*180\",\"180*200\",\"200*250\"],\"name\":\"尺寸\"},{\"catagory\":[\"红\",\"黄\",\"蓝\"],\"name\":\"颜色\"}]";
		String goodsPrices = "[{\"price\":\"1000.00\",\"price_easy_spelling\":\"900.00\",\"spec\":[\"150*180\",\"红\"]},{\"price\":\"3500.00\",\"price_easy_spelling\":\"2999.99\",\"spec\":[\"180*200\",\"黄\"]}]";
		
		ResultBean bean = bgGoodsService.editGoods(gm, labelid_arr, attrid_arr, pics, specs, goodsPrices);
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void findStaffBindGoods() throws JsonProcessingException {
		ResultBean bean = bgGoodsService.findStaffBindGoods("xxx");
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void bindStaffGoods() {
		String[] goodsids = {"1", "2", "4"};
//		bgGoodsService.bindStaffGoods(goodsids , "xxx", "cjy");
	}
	
	@Test
	public void unbindStaffGoods() {
		String[] goodsids = {"1", "2", "4"};
		bgGoodsService.unbindStaffGoods(goodsids , "xxx", "cjy");
	}
	
	@Test
	public void sortStaffGoods() {
		String goodsSortData = "[{\"goodsid\":\"1\",\"sort\":2},{\"goodsid\":\"2\",\"sort\":1}]";
		bgGoodsService.sortStaffGoods(goodsSortData, "xxx", "cjy1");
	}
	
}
