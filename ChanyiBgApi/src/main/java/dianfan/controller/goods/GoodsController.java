package dianfan.controller.goods;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.entities.goods.BgGoods;
import dianfan.entities.goods.GoodsModels;
import dianfan.models.ResultBean;
import dianfan.models.TokenModel;
import dianfan.service.goods.BgGoodsService;
import dianfan.service.impl.RedisTokenService;

/**
 * @ClassName GoodsController
 * @Description 商品管理
 * @author cjy
 * @date 2018年7月17日 下午2:06:12
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
	@Autowired
	private BgGoodsService bgGoodsService;
	@Autowired
	private RedisTokenService redisTokenService;
	
	/**
	 * @Title: findGoodsList
	 * @Description: 获取商品列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午2:06:52
	 */
	@SystemControllerLog(method = "findGoodsLabelList", logtype = ConstantIF.LOG_TYPE_2, description = "获取商品列表")
	@ApiOperation(value = "获取商品列表", httpMethod = "GET", notes = "获取商品列表", response = ResultBean.class)
	@RequestMapping(value = "/goodsList", method = RequestMethod.GET)
	public @ResponseBody ResultBean findGoodsList(
			@ApiParam(value="请求页") @RequestParam(value=ConstantIF.PAGE, defaultValue=ConstantIF.PAGE_START_STR) int page,
			@ApiParam(value="每页条数") @RequestParam(value=ConstantIF.PAGE_SIZE, defaultValue=ConstantIF.PAGE_OFFSET_STR) int pageSize,
			
			@ApiParam(value="商品标题") @RequestParam(value="goodsTitle", required=false) String goodsTitle,
			@ApiParam(value="是否易拼(0否，1是，默认全部)") @RequestParam(value="easySpelling", required=false) Integer easySpelling,
			@ApiParam(value="商品分类id") @RequestParam(value="goodsClassifyid", required=false) String goodsClassifyid,
			
			@ApiParam(value="非定制的商品价格-低价区间") @RequestParam(value="l_price", required=false) String l_price,
			@ApiParam(value="非定制的商品价格-高价区间") @RequestParam(value="h_price", required=false) String h_price,
			
			@ApiParam(value="上下架(0下架，1上架，默认全部)") @RequestParam(value="upperlower", required=false) Integer upperlower,

			@ApiParam(value="是否按销售数量排序(0:asc,1:desc，默认否)") @RequestParam(value="sales_sort", required=false) Integer sales_sort,
			@ApiParam(value="是否按收藏数量排序(0:asc,1:desc，默认否)") @RequestParam(value="coll_sort", required=false) Integer coll_sort,
			@ApiParam(value="是否按价格排序(0:asc,1:desc，默认否)") @RequestParam(value="price_sort", required=false) Integer price_sort,
			
			//商品标签
			@ApiParam(value="商品标签（多个id使用英文','分隔）") @RequestParam(value="labelids", required=false) String labelids,
			//商品属性
			@ApiParam(value="商品属性（多个id使用英文','分隔）") @RequestParam(value="attrids", required=false) String attrids,
			
			@ApiParam(value="员工id") @RequestParam(value="staffid", required=false) String staffid
			) {
		if(goodsTitle == null || StringUtils.isEmpty(goodsTitle.trim())) goodsTitle = null;
		else goodsTitle = goodsTitle.trim();
		
		if(goodsClassifyid == null || StringUtils.isEmpty(goodsClassifyid.trim())) goodsClassifyid = null;
		else goodsClassifyid = goodsClassifyid.trim();
		
		if(l_price == null || StringUtils.isEmpty(l_price.trim())) l_price = null;
		else l_price = l_price.trim();
		
		if(h_price == null || StringUtils.isEmpty(h_price.trim())) h_price = null;
		else h_price = h_price.trim();
		
		String[] labelid_arr, attrid_arr;
		if(labelids == null || StringUtils.isEmpty(labelids.trim())) {
			labelid_arr = null;
		}else {
			labelid_arr = labelids.trim().split(",");
		}
		
		if(attrids == null || StringUtils.isEmpty(attrids.trim())) {
			attrid_arr = null;
		}else {
			attrid_arr = attrids.trim().split(",");
		}
		
		if(staffid == null || StringUtils.isEmpty(staffid.trim())) {
			attrid_arr = null;
		}else {
			staffid = staffid.trim();
		}
		
		return bgGoodsService.findGoodsList(page, pageSize, goodsTitle, easySpelling, goodsClassifyid, l_price, h_price, 
				upperlower, sales_sort, coll_sort, price_sort, labelid_arr, attrid_arr, staffid);
	}
	
	/**
	 * @Title: goodsDetail
	 * @Description: 商品详情
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午11:26:12
	 */
	@SystemControllerLog(method = "goodsDetail", logtype = ConstantIF.LOG_TYPE_2, description = "商品详情")
	@ApiOperation(value = "商品详情", httpMethod = "GET", notes = "商品详情", response = ResultBean.class)
	@RequestMapping(value = "/goodsDetail", method = RequestMethod.GET)
	public @ResponseBody ResultBean goodsDetail(@ApiParam(value="商品id") @RequestParam(value="goodsid") String goodsid) {
		return bgGoodsService.getGoodsInfoDetail(goodsid);
	}
	
	/**
	 * @Title: addGoods
	 * @Description: 新增商品
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午3:26:02
	 */
	@SystemControllerLog(method = "addGoods", logtype = ConstantIF.LOG_TYPE_2, description = "新增商品")
	@ApiOperation(value = "新增商品", httpMethod = "POST", notes = "新增商品", response = ResultBean.class)
	@RequestMapping(value = "/addGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean addGoods(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			//商品数据
			@ApiParam(value="商品标题") @RequestParam(value="goodsTitle") String goodsTitle,
			@ApiParam(value="商品英文标题") @RequestParam(value="goodsTitleEn", required=false) String goodsTitleEn,
			@ApiParam(value="是否易拼(0否，1是)") @RequestParam(value="easySpelling", defaultValue="0") int easySpelling,
			@ApiParam(value="员工是否可以易拼(0否，1是)") @RequestParam(value="staffEasySpelling", defaultValue="1") int staffEasySpelling,
			@ApiParam(value="商品分类id") @RequestParam(value="goodsClassifyid") String goodsClassifyid,
			@ApiParam(value="商品描述") @RequestParam(value="goodsDesc", required=false) String goodsDesc,
			@ApiParam(value="非定制的商品价格") @RequestParam(value="price", required=false) BigDecimal price,
			@ApiParam(value="上下架(0下架，1上架)") @RequestParam(value="upperlower", defaultValue="0") int upperlower,
			@ApiParam(value="商品类别（01：非定制 02：定制）") @RequestParam(value="goodsCategory") String goodsCategory,
			@ApiParam(value="易拼拼团显示的商品价格") @RequestParam(value="priceEasySpelling", required=false) BigDecimal priceEasySpelling,
			//商品标签
			@ApiParam(value="商品标签（多个id使用英文','分隔）") @RequestParam(value="labelids", required=false) String labelids,
			//商品属性
			@ApiParam(value="商品属性（多个id使用英文','分隔）") @RequestParam(value="attrids", required=false) String attrids,
			//商品图片 [{"picAddr":"upload/goods/20180718/asasa.png","picType":"01","picPcMobile":"01","picSort":1},{"picAddr":"upload/goods/20180718/qwqwqw.png","picType":"01","picPcMobile":"01","picSort":2}]
			@ApiParam(value="商品图片（json字符串格式）") @RequestParam(value="pics", required=false) String pics,
			//商品规格 [{"catagory":["150*180","180*200","200*250"],"name":"尺寸"},{"catagory":["红","黄","蓝"],"name":"颜色"}]
			@ApiParam(value="商品规格（json字符串格式）") @RequestParam(value="specs", required=false) String specs,
			//商品价格 [{"picurl":"upload/goods/20180723/ac.png","price":"1000.00","price_easy_spelling":"900.00","spec":["150*180","红"]},{"picurl":"upload/goods/20180723/sd.png","price":"3500.00","price_easy_spelling":"2999.99","spec":["180*200","黄"]}]
			@ApiParam(value="商品价格（json字符串格式）") @RequestParam(value="goodsPrices", required=false) String goodsPrices,
			
			@ApiParam(value="商品编码") @RequestParam(value="productSku") String productSku,
			@ApiParam(value="重量（kg）") @RequestParam(value="weight") String weight,
			@ApiParam(value="长度(mm)") @RequestParam(value="length") String length,
			@ApiParam(value="宽度(mm)") @RequestParam(value="width") String width,
			@ApiParam(value="高度(mm)") @RequestParam(value="height") String height,
			@ApiParam(value="三级分类编码（安维必填）") @RequestParam(value="thridCategoryNo") String thridCategoryNo,
			@ApiParam(value="是否安维（1是，2否）") @RequestParam(value="installFlag") String installFlag,
			
			@ApiParam(value="导购折扣") @RequestParam(value="spgSaleDiscount", defaultValue="0.00", required=false) BigDecimal spgSaleDiscount,
			@ApiParam(value="体验店折扣") @RequestParam(value="expSaleDiscount", defaultValue="0.00", required=false) BigDecimal expSaleDiscount,
			@ApiParam(value="运营服务商折扣") @RequestParam(value="cpsSaleDiscount", defaultValue="0.00", required=false) BigDecimal cpsSaleDiscount
			) {
		
		BgGoods gm = new BgGoods();
		
		if(goodsTitle == null || StringUtils.isEmpty(goodsTitle.trim())) {
			return new ResultBean("3000", "商品标题" + ResultBgMsg.C_3000);
		}
		gm.setGoodsTitle(goodsTitle.trim());
		gm.setGoodsTitleEn(goodsTitleEn == null?null:goodsTitleEn.trim());
		gm.setIsEasySpelling(easySpelling);
		gm.setIsStaffEasySpelling(staffEasySpelling);
		gm.setGoodsClassifyId(goodsClassifyid);
		gm.setGoodsDesc(goodsDesc == null?null:goodsDesc.trim());
		gm.setPrice(price);
		gm.setUpperlower(upperlower);
		gm.setGoodsCategory(goodsCategory);
		gm.setPriceEasySpelling(priceEasySpelling);
		TokenModel token = redisTokenService.getToken(accesstoken);
		gm.setCreateBy(token.getUserid());
		
		gm.setProductSku(productSku);
		gm.setWeight(weight);
		gm.setLength(length);
		gm.setWidth(width);
		gm.setHeight(height);
		gm.setThridCategoryNo(thridCategoryNo);
		gm.setInstallFlag(installFlag);
		
		gm.setSpgSaleDiscount(spgSaleDiscount);
		gm.setExpSaleDiscount(expSaleDiscount);
		gm.setCpsSaleDiscount(cpsSaleDiscount);
		
		String[] labelid_arr = null;
		if(labelids != null && !StringUtils.isEmpty(labelids.trim())) {
			//已选择商品标签
			labelid_arr = labelids.split(",");
		}
		
		String[] attrid_arr = null;
		if(attrids != null && !StringUtils.isEmpty(attrids.trim())) {
			//已选择商品属性
			attrid_arr = attrids.split(",");
		}
		
		if((StringUtils.isEmpty(specs) && StringUtils.isNotEmpty(goodsPrices)) ||
			(StringUtils.isNotEmpty(specs) && StringUtils.isEmpty(goodsPrices))) {
			//商品规格和价格不满足需求，必须同时存在才可以
			return new ResultBean("3004", ResultBgMsg.C_3004);
		}
		
		return bgGoodsService.addGoods(gm, labelid_arr, attrid_arr, pics, specs, goodsPrices);
	}
	
	/**
	 * @Title: editGoods
	 * @Description: 修改商品 
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午4:55:45
	 */
	@SystemControllerLog(method = "editGoods", logtype = ConstantIF.LOG_TYPE_2, description = "修改商品")
	@ApiOperation(value = "修改商品", httpMethod = "POST", notes = "修改商品", response = ResultBean.class)
	@RequestMapping(value = "/editGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean editGoods(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			//商品数据
			@ApiParam(value="商品id") @RequestParam(value="goodsid") String goodsid,
			@ApiParam(value="商品标题") @RequestParam(value="goodsTitle") String goodsTitle,
			@ApiParam(value="商品英文标题") @RequestParam(value="goodsTitleEn", required=false) String goodsTitleEn,
			@ApiParam(value="是否易拼(0否，1是)") @RequestParam(value="easySpelling", defaultValue="0") int easySpelling,
			@ApiParam(value="员工是否可以易拼(0否，1是)") @RequestParam(value="staffEasySpelling", defaultValue="1") int staffEasySpelling,
			@ApiParam(value="商品分类id") @RequestParam(value="goodsClassifyid") String goodsClassifyid,
			@ApiParam(value="商品描述") @RequestParam(value="goodsDesc", required=false) String goodsDesc,
			@ApiParam(value="非定制的商品价格") @RequestParam(value="price", required=false) BigDecimal price,
			@ApiParam(value="上下架(0下架，1上架)") @RequestParam(value="upperlower", defaultValue="0") int upperlower,
			@ApiParam(value="商品类别（01：非定制 02：定制）") @RequestParam(value="goodsCategory") String goodsCategory,
			@ApiParam(value="易拼拼团显示的商品价格") @RequestParam(value="priceEasySpelling", required=false) BigDecimal priceEasySpelling,
			//商品标签
			@ApiParam(value="商品标签（多个id使用英文','分隔）") @RequestParam(value="labelids", required=false) String labelids,
			//商品属性
			@ApiParam(value="商品属性（多个id使用英文','分隔）") @RequestParam(value="attrids", required=false) String attrids,
			
			//商品图片 [{"picAddr":"upload/goods/20180718/asasa.png","picType":"01","picPcMobile":"01","picSort":1},{"picAddr":"upload/goods/20180718/qwqwqw.png","picType":"01","picPcMobile":"01","picSort":2}]
			@ApiParam(value="商品图片（json字符串格式）") @RequestParam(value="pics", required=false) String pics,
			//商品规格 [{"catagory":["150*180","180*200","200*250"],"name":"尺寸"},{"catagory":["红","黄","蓝"],"name":"颜色"}]
			@ApiParam(value="商品规格（json字符串格式）") @RequestParam(value="specs", required=false) String specs,
			//商品价格 [{"picurl":"upload/goods/20180723/ac.png","price":"1000.00","price_easy_spelling":"900.00","spec":["150*180","红"]},{"picurl":"upload/goods/20180723/sd.png","price":"3500.00","price_easy_spelling":"2999.99","spec":["180*200","黄"]}]
			@ApiParam(value="商品价格（json字符串格式）") @RequestParam(value="goodsPrices", required=false) String goodsPrices,
			
			@ApiParam(value="商品编码") @RequestParam(value="productSku") String productSku,
			@ApiParam(value="重量（kg）") @RequestParam(value="weight") String weight,
			@ApiParam(value="长度(mm)") @RequestParam(value="length") String length,
			@ApiParam(value="宽度(mm)") @RequestParam(value="width") String width,
			@ApiParam(value="高度(mm)") @RequestParam(value="height") String height,
			@ApiParam(value="三级分类编码（安维必填）") @RequestParam(value="thridCategoryNo") String thridCategoryNo,
			@ApiParam(value="是否安维（1是，2否）") @RequestParam(value="installFlag") String installFlag,
			
			@ApiParam(value="导购折扣") @RequestParam(value="spgSaleDiscount", defaultValue="0.00", required=false) BigDecimal spgSaleDiscount,
			@ApiParam(value="体验店折扣") @RequestParam(value="expSaleDiscount", defaultValue="0.00", required=false) BigDecimal expSaleDiscount,
			@ApiParam(value="运营服务商折扣") @RequestParam(value="cpsSaleDiscount", defaultValue="0.00", required=false) BigDecimal cpsSaleDiscount
			) {
		
		BgGoods gm = new BgGoods();
		
		if(goodsTitle == null || StringUtils.isEmpty(goodsTitle.trim())) {
			return new ResultBean("3000", "商品标题" + ResultBgMsg.C_3000);
		}
		gm.setId(goodsid);
		gm.setGoodsTitle(goodsTitle.trim());
		gm.setGoodsTitleEn(goodsTitleEn == null?null:goodsTitleEn.trim());
		gm.setIsEasySpelling(easySpelling);
		gm.setIsStaffEasySpelling(staffEasySpelling);
		gm.setGoodsClassifyId(goodsClassifyid);
		gm.setGoodsDesc(goodsDesc == null?null:goodsDesc.trim());
		gm.setPrice(price);
		gm.setUpperlower(upperlower);
		gm.setGoodsCategory(goodsCategory);
		gm.setPriceEasySpelling(priceEasySpelling);
		TokenModel token = redisTokenService.getToken(accesstoken);
		gm.setCreateBy(token.getUserid());
		gm.setUpdateBy(token.getUserid());
		
		gm.setProductSku(productSku);
		gm.setWeight(weight);
		gm.setLength(length);
		gm.setWidth(width);
		gm.setHeight(height);
		gm.setThridCategoryNo(thridCategoryNo);
		gm.setInstallFlag(installFlag);
		
		gm.setSpgSaleDiscount(spgSaleDiscount);
		gm.setExpSaleDiscount(expSaleDiscount);
		gm.setCpsSaleDiscount(cpsSaleDiscount);
		
		String[] labelid_arr = null;
		if(labelids != null && !StringUtils.isEmpty(labelids.trim())) {
			//已选择商品标签
			labelid_arr = labelids.split(",");
		}
		
		String[] attrid_arr = null;
		if(attrids != null && !StringUtils.isEmpty(attrids.trim())) {
			//已选择商品属性
			attrid_arr = attrids.split(",");
		}
		
		if((StringUtils.isEmpty(specs) && StringUtils.isNotEmpty(goodsPrices)) ||
				(StringUtils.isNotEmpty(specs) && StringUtils.isEmpty(goodsPrices))) {
			//商品规格和价格不满足需求，必须同时存在才可以
			return new ResultBean("3004", ResultBgMsg.C_3004);
		}
		
		return bgGoodsService.editGoods(gm, labelid_arr, attrid_arr, pics, specs, goodsPrices);
	}
	
	/**
	 * @Title: delGoods
	 * @Description: 删除商品
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午3:29:00
	 */
	@SystemControllerLog(method = "delGoods", logtype = ConstantIF.LOG_TYPE_2, description = "删除商品")
	@ApiOperation(value = "删除商品", httpMethod = "POST", notes = "删除商品", response = ResultBean.class)
	@RequestMapping(value = "/delGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean delGoods(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品id列表（id之间使用英文','分隔）") @RequestParam(value="goodsids") String goodsids) {
		if(goodsids == null || StringUtils.isEmpty(goodsids.trim())) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		bgGoodsService.delGoods(goodsids.split(","), token.getUserid());
		return new ResultBean();
	}
	
	/**
	 * @Title: upDownGoods
	 * @Description: 商品上下架
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午9:22:56
	 * @author cjy
	 */
	@SystemControllerLog(method = "delGoods", logtype = ConstantIF.LOG_TYPE_2, description = "商品上下架")
	@ApiOperation(value = "商品上下架", httpMethod = "POST", notes = "商品上下架", response = ResultBean.class)
	@RequestMapping(value = "/upDownGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean upDownGoods(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品id列表（id之间使用英文','分隔）") @RequestParam(value="goodsids") String goodsids,
			@ApiParam(value="上下架动作（0下架，1上架）") @RequestParam(value="action") int action) {
		if(goodsids == null || StringUtils.isEmpty(goodsids.trim())) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		if(action != 0 && action != 1) {
			return new ResultBean("3006", ResultBgMsg.C_3006);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		bgGoodsService.upDownGoods(goodsids.split(","), action, token.getUserid());
		return new ResultBean();
	}
	
	/**
	 * @Title: staffBindGoods
	 * @Description: 员工下的商品列表（简单列表）
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:51:07
	 * @author cjy
	 */
	@SystemControllerLog(method = "bindStaffGoods", logtype = ConstantIF.LOG_TYPE_2, description = "员工下的商品列表（简单列表）")
	@ApiOperation(value = "员工下的商品列表（简单列表）", httpMethod = "POST", notes = "员工下的商品列表（简单列表）", response = ResultBean.class)
	@RequestMapping(value = "/staffBindGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean staffBindGoods(@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid) {
		return bgGoodsService.findStaffBindGoods(staffid);
	}
	
	/**
	 * @Title: bindStaffGoods
	 * @Description: 绑定员工下的商品
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午11:41:22
	 * @author cjy
	 */
	@SystemControllerLog(method = "bindStaffGoods", logtype = ConstantIF.LOG_TYPE_2, description = "绑定员工下的商品")
	@ApiOperation(value = "绑定 员工下的商品", httpMethod = "POST", notes = "绑定员工下的商品", response = ResultBean.class)
	@RequestMapping(value = "/bindStaffGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean bindStaffGoods(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			//格式  [{"goodsid":"abc","sort":1},{"goodsid":"dfg","sort":2}]
			@ApiParam(value="商品id列表") @RequestParam(value="goods") String goods,
			@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid) {
		if(goods == null || StringUtils.isEmpty(goods.trim())) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		return bgGoodsService.bindStaffGoods(goods, staffid, token.getUserid());
	}
	
	/**
	 * @Title: unbindStaffGoods
	 * @Description: 解绑 员工下的商品
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午12:12:59
	 * @author cjy
	 */
	@SystemControllerLog(method = "unbindStaffGoods", logtype = ConstantIF.LOG_TYPE_2, description = "解绑 员工下的商品")
	@ApiOperation(value = "解绑 员工下的商品", httpMethod = "POST", notes = "解绑 员工下的商品", response = ResultBean.class)
	@RequestMapping(value = "/unbindStaffGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean unbindStaffGoods(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			@ApiParam(value="商品id列表（id之间使用英文','分隔）") @RequestParam(value="goodsids") String goodsids,
			@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid) {
		if(goodsids == null || StringUtils.isEmpty(goodsids.trim())) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		bgGoodsService.unbindStaffGoods(goodsids.split(","), staffid, token.getUserid());
		return new ResultBean();
	}
	
	/**
	 * @Title: sortStaffGoods
	 * @Description: 排序员工下的商品
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午1:27:00
	 * @author cjy
	 */
	@SystemControllerLog(method = "sortStaffGoods", logtype = ConstantIF.LOG_TYPE_2, description = "排序员工下的商品")
	@ApiOperation(value = "排序员工下的商品", httpMethod = "POST", notes = "排序员工下的商品", response = ResultBean.class)
	@RequestMapping(value = "/sortStaffGoods", method = RequestMethod.POST)
	public @ResponseBody ResultBean sortStaffGoods(
			@ApiParam(value="accesstoken") @RequestParam(value=ConstantIF.ACCESSTOKEN) String accesstoken,
			//[{"goodsid":"abc","sort":1},{"goodsid":"dfg","sort":2}]
			@ApiParam(value="商品排序信息") @RequestParam(value="goodsSortData") String goodsSortData,
			@ApiParam(value="员工id") @RequestParam(value="staffid") String staffid) {
		if(goodsSortData == null || StringUtils.isEmpty(goodsSortData.trim())) {
			return new ResultBean("501", ResultBgMsg.C_501);
		}
		TokenModel token = redisTokenService.getToken(accesstoken);
		bgGoodsService.sortStaffGoods(goodsSortData, staffid, token.getUserid());
		return new ResultBean();
	}
}
