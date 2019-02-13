package dianfan.service.order.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPayConfig;
import com.jd.open.api.sdk.request.ECLP.EclpCoTransportLasWayBillRequest;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.easyspelling.EasyspellingMapper;
import dianfan.dao.mapper.factory.FactoryMapper;
import dianfan.dao.mapper.goodscart.GoodsCartMapper;
import dianfan.dao.mapper.logistics.LogisticsMapper;
import dianfan.dao.mapper.order.GuidePriceMapper;
import dianfan.dao.mapper.order.OrderClassMapper;
import dianfan.dao.mapper.order.OrderCommissionMapper;
import dianfan.dao.mapper.our.CouponMapper;
import dianfan.dao.mapper.our.PersonalInfoMapper;
import dianfan.date.DateUtility;
import dianfan.entities.Coupon;
import dianfan.entities.UserCouponRelate;
import dianfan.entities.alipay.AppPayBean;
import dianfan.entities.alipay.PublicPayParam;
import dianfan.entities.alipay.WapPayBean;
import dianfan.entities.base.AreaModel;
import dianfan.entities.commission.UserBounsDetail;
import dianfan.entities.commission.UserLastMoney;
import dianfan.entities.easyspelling.EasySpelling;
import dianfan.entities.easyspelling.EasySpellingParameter;
import dianfan.entities.easyspelling.EasySpellingUserRelate;
import dianfan.entities.goods.GoodsPriceModel;
import dianfan.entities.goods.GoodsSpec;
import dianfan.entities.goodscart.GoodsCartModel;
import dianfan.entities.logistics.DeliveryModels;
import dianfan.entities.logistics.LogisticsModel;
import dianfan.entities.logistics.OrderDeliveryRelate;
import dianfan.entities.order.AfterSale;
import dianfan.entities.order.ConsigneeModel;
import dianfan.entities.order.Order;
import dianfan.entities.order.OrderCloseModel;
import dianfan.entities.order.OrderCloseOutEspEndTimeModel;
import dianfan.entities.order.OrderGoods;
import dianfan.entities.order.OrderModel;
import dianfan.entities.order.OrderSuperior;
import dianfan.entities.order.TradeSer;
import dianfan.entities.our.Goods;
import dianfan.entities.our.GoodsModel;
import dianfan.entities.our.UserInfoModel;
import dianfan.entities.role.Role;
import dianfan.entities.wx.UnifiedorderBean;
import dianfan.entities.wx.WxOrderRefundBean;
import dianfan.entities.wx.WxTradeType;
import dianfan.models.ResultBean;
import dianfan.pay.alipay.AlipayCore;
import dianfan.pay.config.wx.WxPayConfigImpl;
import dianfan.pay.wx.WxPayCore;
import dianfan.service.impl.RedisService;
import dianfan.service.jd.transport.JdTransportService;
import dianfan.service.order.OrderClassService;
import dianfan.util.GenRandomNumUtil;
import dianfan.util.PropertyUtil;
import dianfan.util.StringUtility;
import dianfan.util.UUIDUtil;

/**
 * @ClassName OrderClassServiceImpl
 * @Description 订单相关service
 * @author sz
 * @date 2018年7月5日 下午3:30:44
 */
@Service
public class OrderClassServiceImpl implements OrderClassService {

	/**
	 * 注入：#OrderClassMapper
	 */
	@Autowired
	private OrderClassMapper orderClassMapper;
	/**
	 * 注入：#GoodsCartMapper
	 */
	@Autowired
	private GoodsCartMapper goodsCartMapper;
	/**
	 * 注入：#PersonalInfoMapper
	 */
	@Autowired
	private PersonalInfoMapper personalInfoMapper;
	/**
	 * 注入：#CouponMapper
	 */
	@Autowired
	private CouponMapper couponMapper;
	/**
	 * 注入：#EasyspellingMapper
	 */
	@Autowired
	private EasyspellingMapper easyspellingMapper;

	@Autowired
	private OrderCommissionMapper orderCommissionMapper;

	@Autowired
	private FactoryMapper factoryMapper;

	@Autowired
	private JdTransportService jdTransportService;

	@Autowired
	private LogisticsMapper logisticsMapper;

	@Autowired
	private GuidePriceMapper guidePriceMapper;

	@Autowired
	private RedisService redisService;

	/*
	 * (non-Javadoc) <p>Title: fildOrderList</p> <p>Description: 获取用户订单列表</p>
	 * 
	 * @param userid userid
	 * 
	 * @param status 订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#fildOrderList(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "fildOrderList", description = "获取用户订单列表 ")
	public ResultBean fildOrderList(String userid, String status, Integer page, Integer pagecounts) {
		// 响应map 容器
		Map<String, Object> data = new HashMap<>();

		List<String> status_arr = Arrays.asList(status.split(","));

		// 创建入参容器
		Map<String, Object> prama = new HashMap<>();
		prama.put("userid", userid);
		prama.put("status", status_arr);

		int count = 0;

		if (status_arr.contains("99")) {
			// 如果传入的状态是99 ，说明用户是想看被删除的订单，那就要去查 entkbn = 9 的数量
			count = orderClassMapper.fildOrderCountOmit(prama);
		} else if (status_arr.contains("00")) {
			// 全部订单
			prama.put("status", null);
			count = orderClassMapper.fildOrderCount(prama);
		} else {
			// 查询数量
			count = orderClassMapper.fildOrderCount(prama);
		}

		// 设置总页数
		data.put("totalcount", count);
		if (count < (page - 1) * pagecounts || count == 0) {
			// 空的返回实体
			data.put("orderlist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		prama.put("start", (page - 1) * pagecounts);
		// 分页偏移量 10
		prama.put("offset", pagecounts);
		// 调库查询订单列表
		List<OrderModel> orderlist = null;

		if (status_arr.contains("99")) {
			// 如果传入的状态是99 ，说明用户是想看被删除的订单，那就要去查 entkbn = 9 的数量
			orderlist = orderClassMapper.fildOrderListOmit(prama);
		} else {
			orderlist = orderClassMapper.fildOrderList(prama);
		}

//		// 获取订单列表中的所有订单的id
//		List<String> oid = new ArrayList<>();
//		for (OrderModel g : orderlist) {
//			oid.add(g.getOrderId());
//		}
//		// 获取订单下面的商品
//		List<OrderGoods> goodss = orderClassMapper.findGoodss(oid);
//		Map<String, List<OrderGoods>> goodsl = new HashMap<>();
//		if (!goodss.isEmpty() && goodss.size() > 0) {
//			// 下面的操作是将订单下面的商品 转换成一个 key是订单的id，值是订单下的商品的一个map
//			for (OrderGoods g : goodss) {
//				// 将List<OrderGoods> goodss，去转化成一个已orderid 为key，list<对象0>为值得一个map
//				if (goodsl.containsKey(g.getOrderId())) {
//					// 如果map中有对应orderID为key的list，就将当前循环出来的对象放置到这个list中
//					List<OrderGoods> list = goodsl.get(g.getOrderId());
//					list.add(g);
//				} else {
//					// 如果不存在，就新 建一个list，存放当前的对象，再已orderID为key，list<对象0>为值，放置到map中
//					List<OrderGoods> list = new ArrayList<>();
//					list.add(g);
//					goodsl.put(g.getOrderId(), list);
//				}
//			}
//			// 将订单下的商品信息补全
//			for (OrderModel order : orderlist) {
//				String orderid = order.getOrderId();
//				if (goodsl.containsKey(orderid)) {
//					order.setOrderGoods(goodsl.get(orderid));
//				}
//			}
//		}

		// 获取所有的规格和对应名称
		List<GoodsSpec> specList = orderClassMapper.fildSpecList();
		// 将规格和名称转化成一个map格式
		Map<String, String> size = new HashMap<>();
		for (GoodsSpec gs : specList) {
			size.put(gs.getId(), gs.getTypeAndName());
		}
		/* string类型返回值整理 */
		for (OrderModel pic : orderlist) {
			// 循环出订单中的每一个商品
			if (!org.springframework.util.StringUtils.isEmpty(pic.getOrderGoods())) {
				List<OrderGoods> orderGoods = pic.getOrderGoods();
				for (OrderGoods order : orderGoods) {
					// 创建一个空的string，用于下面存放商品的规格
					String str = "";
					String specIds = order.getSpecIds();
					if (!StringUtility.isNull(specIds)) {
						String[] s1 = specIds.split(",");
						for (int i = 0; i < s1.length; i++) {
							for (String s2 : size.keySet()) {
								if (s1[i].equals(s2)) {
									str += size.get(s2) +" ";
								}
							}
						}
						// 将商品规格加入 string类型
						order.setSpecList(str);
					}

					// 将商品的中的图片的路径修改一下
					order.setPicAddr(PropertyUtil.getProperty("domain") + order.getPicAddr());
				}
			}
		}
		/* 整理返回参数 */
		// 设置当前页面
		data.put("page", page);
		// 添加返回的 订单列表列表
		data.put("orderlist", orderlist);
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc) <p>Title: closeOrder</p> <p>Description: 关闭支付订单</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param orderid 订单id
	 * 
	 * @return ResultBean link: @see
	 * dianfan.service.order.OrderClassService#closeOrder(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "closeOrder", description = "关闭订单 ")
	public ResultBean delOrder(String userid, String orderid) {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("orderid", orderid);

		// 首先确认该订单的状态（非 （01-待付款）情况下，不可以关闭订单）
		String state = orderClassMapper.getOrderState(param);
		if (!"01".equals(state)) {
			// !当前订单不可以关闭
			return new ResultBean("4102", ResultApiMsg.C_4102);
		}
		// 关闭对应订单
		orderClassMapper.delOrder(param);
		// 成功返回
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: fildOrderInfo</p> <p>Description: 获取用户订单详情</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param orderid 订单id
	 * 
	 * @return ResultBean link: @see
	 * dianfan.service.order.OrderClassService#fildOrderInfo(java.lang.String,
	 * java.lang.String)
	 */
	@SystemServiceLog(method = "fildOrderInfo", description = "获取订单详情")
	@Override
	public ResultBean fildOrderInfo(String userid, String orderid) {
		// 1.创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("orderid", orderid);
		// 2.获取详情
		OrderModel info = orderClassMapper.fildOrderInfo(param);
		// 3.获取订单列表中的区域code，去省市区县表中将完整信息返回出来
		if (org.springframework.util.StringUtils.isEmpty(info)) {
			return new ResultBean(new ArrayList<>());
		}
		String code = info.getAreaCode();
		String addressCode = orderClassMapper.findAreaCode(code);
		// 将查出来的地域信息添加到详情中
		info.setAddressCode(addressCode);
		// 获取所有的规格和对应名称
		List<GoodsSpec> specList = orderClassMapper.fildSpecList();
		// 将上面查出来的数据转化成一个map 方便下面去转化
		Map<String, String> size = new HashMap<>();
		for (GoodsSpec gs : specList) {
			size.put(gs.getId(), gs.getTypeAndName());
		}
		// 获取其中的specIds
		List<OrderGoods> orderGoods = info.getOrderGoods();
		if (!org.springframework.util.StringUtils.isEmpty(orderGoods)) {
			for (OrderGoods order : orderGoods) {
				String str = "";
				String specIds = order.getSpecIds();
				if (specIds != null && (!"".equals(specIds))) {
					String[] s1 = specIds.split(",");
					for (int i = 0; i < s1.length; i++) {
						for (String s2 : size.keySet()) {
							if (s1[i].equals(s2)) {
								str += size.get(s2)+" ";
							}
						}
					}
					order.setSpecList(str);
				}
			}
		}

		// 3.将商品的图片的途径修改一下
		if (info != null) {
			List<OrderGoods> pic = info.getOrderGoods();
			for (OrderGoods url : pic) {
				url.setPicAddr(PropertyUtil.getProperty("domain") + url.getPicAddr());
			}
		}
		// 4.成功
		return new ResultBean(info);
	}

	/**
	 * @Title: confirmOrder
	 * @Description:
	 * @param userid
	 *            用户id
	 * @param addressids
	 *            地址id
	 * @param goodscartids
	 *            购物车id
	 * @param couponrelateid
	 *            用户优惠券相关id
	 * @param couponid
	 *            优惠券id
	 * @throws:
	 * @time: 2018年7月7日 下午4:37:05
	 */
	@Override
	@Transactional
	public ResultBean addConfirmOrder(String userid, String addressids, String goodscartids, String couponrelateid,
			String couponid, String payprice, String source) {
		// TODO Auto-generated method stub
		// 获取角色信息
		Role rinfo = orderClassMapper.getRoleInfos(userid);
		List<OrderGoods> oglist = new ArrayList<OrderGoods>();
		OrderGoods og = null;
		Order om = new Order();
		String orderid = GenRandomNumUtil.getOrderNo();
		om.setOrderid(orderid);
		om.setUserid(userid);
		om.setAddressId(addressids);
		// 购物车id
		String[] goodsCartIds = goodscartids.split(",");
		BigDecimal crm = null;
		Coupon cp = null;
		// 获取优惠券信息
		String coupontype = null;
		if (StringUtils.isNotEmpty(couponrelateid) && StringUtils.isNotEmpty(couponid)) {
			cp = couponMapper.getCoupon(couponid);
			if (cp != null) {
				// 获取优惠券减免金额
				String crmoney = cp.getCouponReduceMoney();
				// 优惠券类型(01:商家优惠券02：注册优惠券03：红包)
				coupontype = cp.getCouponType();
				crm = new BigDecimal(crmoney);
			} else {
				crm = new BigDecimal("0.00");
			}
		} else {
			crm = new BigDecimal("0.00");
		}

		// 获取购物车信息（价格、数量）
		// 最终价（不打折）
		BigDecimal totalAmount = new BigDecimal(0);
		//// 普通商品和（不打折）
		BigDecimal totalAmount1 = new BigDecimal(0);
		// 多规格商品和（不打折）
		BigDecimal totalAmount2 = new BigDecimal(0);
		// 最终价（不打折）
		BigDecimal notfinaldiscount = new BigDecimal(0);
		// 最终折扣价（打折）
		BigDecimal finaldiscount = new BigDecimal(0);
		// 普通商品和（打折）
		BigDecimal discountprice1 = new BigDecimal(0);
		// 多规格商品和（打折）
		BigDecimal discountprice2 = new BigDecimal(0);
		// 普通商品和（不打折）
		BigDecimal notdiscountprice1 = new BigDecimal(0);
		// 多规格商品和（不打折）
		BigDecimal notdiscountprice2 = new BigDecimal(0);
		// 数量
		BigDecimal amount = null;
		// 获取购物车中商品信息
		List<GoodsCartModel> gcmodel = goodsCartMapper.findGoodsCartLists(goodsCartIds);
		// 遍历购物车商品信息插入到订单商品表
		if (gcmodel != null && gcmodel.size() > 0) {
			for (int i = 0; i < gcmodel.size(); i++) {
				og = new OrderGoods();
				og.setId(UUIDUtil.getUUID());
				og.setGoodsId(gcmodel.get(i).getGoodsId());
				og.setGoodsPriceId(gcmodel.get(i).getGoodsPriceId());
				og.setSpecIds(gcmodel.get(i).getSpecIds());
				og.setName(gcmodel.get(i).getName());
				og.setNum(gcmodel.get(i).getNum());
				og.setOrderId(orderid);
				if (StringUtils.isNotEmpty(gcmodel.get(i).getGoodsPriceId())) {
					GoodsPriceModel gcm = goodsCartMapper.getGoodsPriceInfo(gcmodel.get(i).getGoodsPriceId());
					og.setUnitPrice(gcm.getPrice());
				} else {
					GoodsModel gm = goodsCartMapper.getGoodsInfomation(gcmodel.get(i).getGoodsId());
					og.setUnitPrice(gm.getPrice());
				}
				og.setPicAddr(gcmodel.get(i).getPicAddr());
				og.setCreateBy(userid);
				oglist.add(og);
			}
			orderClassMapper.addOrderGoods(oglist);
		}
		// 普通商品价格（单件不打折价格）
		BigDecimal initprice1 = new BigDecimal(0);
		// 多规格商品价格（单件不打折价格）
		BigDecimal initprice2 = new BigDecimal(0);
		// 普通商品价格（单件没有折扣价格）
		BigDecimal notdiscount1 = new BigDecimal(0);
		// 多规格商品价格（单件没有折扣价格）
		BigDecimal notdiscount2 = new BigDecimal(0);
		// 普通商品价格（单件折扣价格）
		BigDecimal realpay1 = new BigDecimal(0);
		// 多规格商品价格（单件折扣价格）
		BigDecimal realpay2 = new BigDecimal(0);

		if (gcmodel != null && gcmodel.size() > 0) {
			// 遍历购物车获取普通商品信息、带有规格属性商品的信息
			for (int i = 0; i < gcmodel.size(); i++) {
				// 获取带有规格属性商品的信息
				if (StringUtils.isNotEmpty(gcmodel.get(i).getGoodsPriceId())) {
					GoodsPriceModel gpms = guidePriceMapper.getGoodsPrice(gcmodel.get(i).getGoodsPriceId());
					// 商品单价
					BigDecimal bg = gpms.getPrice();
					// 商品数量
					int num = gcmodel.get(i).getNum();
					amount = new BigDecimal(num);
					// 不打折价格
					initprice1 = bg.multiply(amount);
					totalAmount1 = totalAmount1.add(initprice1);
				} else {
					// 获取普通商品信息
					Goods gpms = guidePriceMapper.getGoodsInfo(gcmodel.get(i).getGoodsId());
					BigDecimal bg = gpms.getPrice();
					int num = gcmodel.get(i).getNum();
					amount = new BigDecimal(num);
					// 不打折价格
					initprice2 = bg.multiply(amount);
					totalAmount2 = totalAmount2.add(initprice2);
				}
			}
			totalAmount = totalAmount1.add(totalAmount2);
			// 总金额
			om.setTotalFee(totalAmount);
			for (int i = 0; i < gcmodel.size(); i++) {
				// 获取带有规格属性商品的信息
				if (StringUtils.isNotEmpty(gcmodel.get(i).getGoodsPriceId())) {
					GoodsPriceModel gpms = guidePriceMapper.getGoodsPrice(gcmodel.get(i).getGoodsPriceId());
					BigDecimal spg = null;
					// 城市经理/市场开发经理/体验店折扣
					if (ConstantIF.ROLE_DISTINGUISH03.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH04.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH05.equals(rinfo.getRoleDistinguish())) {
						spg = gpms.getExpSaleDiscount();
					}
					// 运营服务商/大区经理/合伙人折扣
					if (ConstantIF.ROLE_DISTINGUISH01.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH02.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH09.equals(rinfo.getRoleDistinguish())) {
						spg = gpms.getCpsSaleDiscount();
					}
					// 导购折扣
					if (ConstantIF.ROLE_DISTINGUISH06.equals(rinfo.getRoleDistinguish())) {
						spg = gpms.getSpgSaleDiscount();
					}
					// 普通人/消费商
					if (ConstantIF.ROLE_DISTINGUISH07.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH08.equals(rinfo.getRoleDistinguish())) {
						spg = new BigDecimal("1");
					}
					// 商品单价
					BigDecimal bg = gpms.getPrice();
					// 商品数量
					int num = gcmodel.get(i).getNum();
					amount = new BigDecimal(num);
					// //不打折价格
					// initprice1 = bg.multiply(amount);
					// totalAmount1 = totalAmount1.add(initprice1);
					// 减去188
					// 没有折扣价格
					notdiscount1 = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN))
							.multiply(amount);
					// 优惠券比例金额 = 商品原价 * 优惠券金额 / 订单总价（total_fee）
					realpay1 = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN))
							.multiply(amount).multiply(spg);
					// bg = bg.subtract(fmbd);
					// 乘以各角色价格
					// realpay1 = bg.multiply(amount).multiply(spg);
					// 求和
					notdiscountprice1 = notdiscountprice1.add(notdiscount1);
					discountprice1 = discountprice1.add(realpay1);
				} else {
					// 获取普通商品信息
					Goods gpms = guidePriceMapper.getGoodsInfo(gcmodel.get(i).getGoodsId());
					BigDecimal spg = null;
					// 城市经理/市场开发经理/体验店折扣
					if (ConstantIF.ROLE_DISTINGUISH03.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH04.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH05.equals(rinfo.getRoleDistinguish())) {
						spg = gpms.getExpSaleDiscount();
					}
					// 运营服务商/大区经理/合伙人折扣
					if (ConstantIF.ROLE_DISTINGUISH01.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH02.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH09.equals(rinfo.getRoleDistinguish())) {
						spg = gpms.getCpsSaleDiscount();
					}
					// 导购折扣
					if (ConstantIF.ROLE_DISTINGUISH06.equals(rinfo.getRoleDistinguish())) {
						spg = gpms.getSpgSaleDiscount();
					}
					// 普通人/消费商
					if (ConstantIF.ROLE_DISTINGUISH07.equals(rinfo.getRoleDistinguish())
							|| ConstantIF.ROLE_DISTINGUISH08.equals(rinfo.getRoleDistinguish())) {
						spg = new BigDecimal("1");
					}
					BigDecimal bg = gpms.getPrice();
					int num = gcmodel.get(i).getNum();
					amount = new BigDecimal(num);
					// //不打折价格
					// initprice2 = bg.multiply(amount);
					// totalAmount2 = totalAmount2.add(initprice2);
					// 没有折扣价格
					notdiscount2 = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN))
							.multiply(amount);
					realpay2 = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN))
							.multiply(amount).multiply(spg);
					// fmbd = bg.multiply(crm).divide(totalAmount,2, RoundingMode.HALF_UP);
					// //减去188
					// bg = bg.subtract(fmbd);
					// //乘以各角色价格
					// realpay2 = bg.multiply(amount).multiply(spg);
					// 求和
					notdiscountprice2 = notdiscountprice2.add(notdiscount2);
					discountprice2 = discountprice2.add(realpay2);
				}
			}

			// ( [（商品A原价 - 188）* 商品A数量 * 商品A角色最低折扣 + （商品B原价 - 188）*商品B数量 * 商品B角色最低折扣)+.....
			notfinaldiscount = notdiscountprice1.add(notdiscountprice2);
			notfinaldiscount = notfinaldiscount.setScale(2, RoundingMode.HALF_UP);
			finaldiscount = discountprice1.add(discountprice2);
			finaldiscount = finaldiscount.setScale(2, RoundingMode.HALF_UP);
		}
		// 角色减免价
		BigDecimal userPrices = null;
		if ((!ConstantIF.ROLE_DISTINGUISH07.equals(rinfo.getRoleDistinguish()))
				&& (!ConstantIF.ROLE_DISTINGUISH08.equals(rinfo.getRoleDistinguish()))) {
			// 角色减免金额总
			userPrices = notfinaldiscount.subtract(finaldiscount);
		} else {
			// 角色减免金额总
			userPrices = new BigDecimal(0);
		}
		om.setRoleReduceFee(userPrices);

		om.setSource(source);
		// 判断是否满足减免金额
		if (cp != null) {
			BigDecimal bgcc = new BigDecimal(cp.getCouponCondtion());
			int compareto = totalAmount.compareTo(bgcc);
			// a = -1,表示小于a = 0,表示等于a = 1,表示大于
			if (compareto != -1) {
				// finaldiscount = finaldiscount.subtract(crm);
				// 修改优惠券使用状态
				UserCouponRelate ucr = new UserCouponRelate();
				ucr.setId(couponrelateid);
				ucr.setUserUsed(ConstantIF.COUPON_USE_STATUS02);
				ucr.setUsedDate(new Timestamp(new Date().getTime()));
				ucr.setUpdateBy(userid);
				couponMapper.updateCoupon(ucr);
			}
		}
		// 判断前端计算的价格是否等于后台计算的价格
		BigDecimal payprices = new BigDecimal(payprice);
		int paymoney = payprices.compareTo(totalAmount.subtract(crm));
		if (paymoney != 0) {
			return new ResultBean("2016", ResultApiMsg.C_2016);
		}
		// om.setCouponReduceFee(crm);
		om.setCouponId(couponid);
		om.setCouponRelateId(couponrelateid);
		// 实付金额
		om.setPayFee(finaldiscount);
		// 优惠金额(优惠券减免金额)
		om.setCouponReduceFee(totalAmount.subtract(finaldiscount).subtract(userPrices));
		/** 订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货 */
		om.setOrderStatus(ConstantIF.ORDER_STATUS01);
		/** 01:正常下单02：易拼 03：易团 */
		om.setGoodsType(ConstantIF.GOODS_TYPE01);
		// 添加收货人信息
		ConsigneeModel cm = orderClassMapper.getConsigneeInfo(addressids);
		if (cm != null) {
			om.setName(cm.getName());
			om.setTelno(cm.getTelno());
			om.setDetailAddr(cm.getDetailAddr());
			om.setAreaCode(cm.getAreaCode());
		}
		om.setCreateBy(userid);
		orderClassMapper.addConfirmOrder(om);

		// 清空购物车
		goodsCartMapper.delShoppingCart(goodscartids.split(","));
		return new ResultBean(orderid);
	}

	/*
	 * (non-Javadoc) <p>Title: goodsOrderPayment</p> <p>Description: 调起支付</p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @param payWays
	 * 
	 * @param paySource
	 * 
	 * @param payType
	 * 
	 * @param ip
	 * 
	 * @return
	 * 
	 * @throws Exception link: @see
	 * dianfan.service.order.OrderClassService#goodsOrderPayment(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	public ResultBean goodsOrderPayment(String userid, String orderid, String payWays, String paySource, String payType,
			String ip, String accesstoken) throws Exception {
		// 1、获取订单实付金额
		BigDecimal pay_fee = orderClassMapper.getOrderPayfeeByOrderid(userid, orderid);
		if (pay_fee == null) {
			// 无此订单
			return new ResultBean("4016", ResultApiMsg.C_4016);
		}
		// 订单存在

		// 2、更新交易流水表
		TradeSer ser = new TradeSer();
		ser.setOrderid(orderid);
		ser.setSerialNumber(UUIDUtil.getUUID());
		ser.setPayWays(payWays);
		ser.setPaySource(paySource);
		ser.setDepositFee(pay_fee);
//		 ser.setDepositFee(new BigDecimal(0.01));
		// 更新交易流水表（存在则更新不存在则插入）
		orderClassMapper.updateTradeStrData(ser);

		// 支付方式(01-ALI(支付宝) 02-WX（微信） 03-BC（银行卡）)
		// 支付类型，固定值(支付宝（"app", "web"）微信（"app", "web", "wxpub", "applet"）)
		if (StringUtils.equals(payWays, "01")) {
			// 支付宝支付
			PublicPayParam base = new PublicPayParam();
			base.setApp_id(PropertyUtil.getProperty("alipay.appid"));
			base.setApp_private_key(PropertyUtil.getProperty("alipay.private_key"));
			base.setAlipay_public_key(PropertyUtil.getProperty("alipay.public_key"));
			base.setNotify_url(PropertyUtil.getProperty("alipay.notify_url"));

			if (StringUtils.equals(payType, "APP")) {
				// app支付
				AppPayBean biz = new AppPayBean();
				// byte[] project =
				// PropertyUtil.getProperty("order.subject").getBytes("iso-8859-1");
				// biz.setSubject(new String(project, "utf-8"));
				biz.setSubject(PropertyUtil.getProperty("order.subject"));
				biz.setOut_trade_no(ser.getSerialNumber());
				biz.setTotal_amount(ser.getDepositFee().toString());
				try {
					String pay_ret = new AlipayCore(base).alipayAppTrade(biz);
					return new ResultBean(pay_ret);
				} catch (AlipayApiException e) {
					// 支付失败
					return new ResultBean("4017", ResultApiMsg.C_4017);
				}
			} else if (StringUtils.equals(payType, "WEB")) {
				// web支付
				base.setReturn_url(PropertyUtil.getProperty("alipay.h5.return_url") + "?orderid=" + orderid);
				// web h5 支付
				WapPayBean biz = new WapPayBean();
				// byte[] project =
				// PropertyUtil.getProperty("order.subject").getBytes("iso-8859-1");
				// biz.setSubject(new String(project, "utf-8"));
				biz.setSubject(PropertyUtil.getProperty("order.subject"));
				biz.setOut_trade_no(ser.getSerialNumber());
				biz.setTotal_amount(ser.getDepositFee().toString());

				try {
					String pay_ret = new AlipayCore(base).alipayWapTrade(biz);
					return new ResultBean(pay_ret);
				} catch (AlipayApiException e) {
					// 支付失败
					return new ResultBean("4017", ResultApiMsg.C_4017);
				}
			} else {
				// 未知的支付类型
				return new ResultBean();
			}
		} else if (StringUtils.equals(payWays, "02")) {
			// 微信支付
			WXPayConfig conf = null;

			UnifiedorderBean order = new UnifiedorderBean();
			order.setBody(PropertyUtil.getProperty("order.subject"));
			// order.setBody(new
			// String(PropertyUtil.getProperty("order.subject").getBytes("iso-8859-1"),
			// "utf-8"));
			order.setOut_trade_no(ser.getSerialNumber());
			BigDecimal money = ser.getDepositFee().multiply(new BigDecimal(100));
			order.setTotal_fee(money.setScale(0, BigDecimal.ROUND_DOWN).intValue());
			order.setSpbill_create_ip(ip);
			order.setNotify_url(PropertyUtil.getProperty("wx.notify_url"));
			order.setAttach(payType);

			if (StringUtils.equals(payType, "APP")) {
				// app支付
				conf = new WxPayConfigImpl(PropertyUtil.getProperty("wx.app.appid"),
						PropertyUtil.getProperty("wx.app.mchid"), PropertyUtil.getProperty("wx.app.key"));
				// 微信app支付
				order.setTrade_type(WxTradeType.APP);
				// 调起支付
				Map<String, String> unifiedOrder = new WxPayCore(conf).unifiedOrder(order, WxTradeType.APP);

				ObjectMapper mapper = new ObjectMapper();
				String str = mapper.writeValueAsString(unifiedOrder);
				return new ResultBean(str);
			} else if (StringUtils.equals(payType, "WEB")) {
				// web支付
				conf = new WxPayConfigImpl(PropertyUtil.getProperty("wx.mp.appid"),
						PropertyUtil.getProperty("wx.mp.mchid"), PropertyUtil.getProperty("wx.mp.key"));
				// 微信H5支付
				order.setTrade_type(WxTradeType.WEB);
				// 场景信息必填
				Map<String, String> scene_info = new HashMap<>();
				scene_info.put("type", "Wap");// 场景类型
				scene_info.put("wap_url", PropertyUtil.getProperty("wx.scene_info.wap_url") + "?orderid=" + orderid);// WAP网站URL地址
				scene_info.put("wap_name", PropertyUtil.getProperty("wx.scene_info.wap_name"));// WAP 网站名
				order.setScene_info(scene_info);
				// 调起支付
				Map<String, String> unifiedOrder = new WxPayCore(conf).unifiedOrder(order, WxTradeType.WEB);

				unifiedOrder.put("mweb_url", unifiedOrder.get("mweb_url") + "&redirect_url="
						+ URLEncoder.encode(PropertyUtil.getProperty("wx.redirect_url")));

				ObjectMapper mapper = new ObjectMapper();
				String str = mapper.writeValueAsString(unifiedOrder);
				return new ResultBean(str);
			} else if (StringUtils.equals(payType, "WXPUB")) {
				// 微信公众号支付
			} else if (StringUtils.equals(payType, "APPLET")) {
				// 小程序支付
				conf = new WxPayConfigImpl(PropertyUtil.getProperty("wx.applet.appid"),
						PropertyUtil.getProperty("wx.mp.mchid"), PropertyUtil.getProperty("wx.mp.key"));
				// 微信小程序
				order.setTrade_type(WxTradeType.JSAPI);
				// JSAPI时 OPENID必传(微信个人标志)
				// 获取用户openid
				String openid = redisService.get(accesstoken + ConstantIF.OPENID_APPLET);
				if (openid == null) {
					openid = orderClassMapper.getUserOpenidByUserid(userid);
				}
				order.setOpenid(openid);
				// 调起支付
				Map<String, String> unifiedOrder = new WxPayCore(conf).unifiedOrder(order, WxTradeType.JSAPI);

				ObjectMapper mapper = new ObjectMapper();
				String str = mapper.writeValueAsString(unifiedOrder);
				return new ResultBean(str);
			} else {
				// 未知的支付类型
				return new ResultBean("C_4019", ResultApiMsg.C_4019);
			}
		} else if (org.apache.commons.lang.StringUtils.equals(payWays, "03")) {
			// 银联支付

		} else {
			// 未知的支付方式
			return new ResultBean("4018", ResultApiMsg.C_4018);
		}
		return null;
	}

	/*
	 * (non-Javadoc) <p>Title: addOrdinaryBuyNow</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param addressids
	 * 
	 * @param goodsids
	 * 
	 * @param goodspriceids
	 * 
	 * @param couponrelateid
	 * 
	 * @param couponid link: @see
	 * dianfan.service.order.OrderClassService#addOrdinaryBuyNow(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean addOrdinaryBuyNow(String userid, String addressids, String goodsids, String goodspriceids,
			Integer num, String couponrelateid, String couponid, String isesflag, String esid, String payprice,
			String source) {
		// 获取角色信息
		Role rinfo = orderClassMapper.getRoleInfos(userid);
		List<OrderGoods> oglist = new ArrayList<OrderGoods>();
		// TODO Auto-generated method stub
		Order om = new Order();
		String orderid = GenRandomNumUtil.getOrderNo();
		om.setOrderid(orderid);
		om.setUserid(userid);
		om.setAddressId(addressids);
		BigDecimal crm = null;
		Coupon cp = null;
		String coupontype = null;
		// 获取优惠券信息
		if (StringUtils.isNotEmpty(couponrelateid) && StringUtils.isNotEmpty(couponid)) {
			cp = couponMapper.getCoupon(couponid);
			if (cp != null) {
				// 获取优惠券减免金额
				String crmoney = cp.getCouponReduceMoney();
				crm = new BigDecimal(crmoney);
				coupontype = cp.getCouponType();
			} else {
				crm = new BigDecimal(0);
			}
		} else {
			crm = new BigDecimal(0);
		}
		GoodsModel gm = goodsCartMapper.getGoodsInfomation(goodsids);
		// 获取商品信息（价格、数量）
		BigDecimal totalAmount = new BigDecimal(0);
		// 实付价
		BigDecimal realpay = new BigDecimal(0);
		// 员工不打折价
		BigDecimal notdiscount = new BigDecimal(0);
		BigDecimal amount = new BigDecimal(num);

		OrderGoods og = null;
		// 获取图片地址
		String goodspic = goodsCartMapper.getGoodsPicAddress(goodsids);
		// 获取普通商品信息
		Goods gpms = guidePriceMapper.getGoodsInfo(goodsids);
		BigDecimal spg = null;
		if (gpms != null) {
			// 城市经理/市场开发经理/体验店折扣
			if (ConstantIF.ROLE_DISTINGUISH03.equals(rinfo.getRoleDistinguish())
					|| ConstantIF.ROLE_DISTINGUISH04.equals(rinfo.getRoleDistinguish())
					|| ConstantIF.ROLE_DISTINGUISH05.equals(rinfo.getRoleDistinguish())) {
				spg = gpms.getExpSaleDiscount();
			}
			// 运营服务商/大区经理/合伙人折扣
			if (ConstantIF.ROLE_DISTINGUISH01.equals(rinfo.getRoleDistinguish())
					|| ConstantIF.ROLE_DISTINGUISH02.equals(rinfo.getRoleDistinguish())
					|| ConstantIF.ROLE_DISTINGUISH09.equals(rinfo.getRoleDistinguish())) {
				spg = gpms.getCpsSaleDiscount();
			}
			// 导购折扣
			if (ConstantIF.ROLE_DISTINGUISH06.equals(rinfo.getRoleDistinguish())) {
				spg = gpms.getSpgSaleDiscount();
			}
			// 普通人/消费商
			if (ConstantIF.ROLE_DISTINGUISH07.equals(rinfo.getRoleDistinguish())
					|| ConstantIF.ROLE_DISTINGUISH08.equals(rinfo.getRoleDistinguish())) {
				spg = new BigDecimal("1");
			}
		}
		if (StringUtils.isNotEmpty(goodsids) && !StringUtils.isNotEmpty(goodspriceids)) {

			BigDecimal bg = new BigDecimal(0);
			if (gm != null) {
				if (StringUtils.isNotEmpty(isesflag)) {
					// 获取易拼价格
					bg = gm.getPriceEasySpelling();
				} else {
					// 普通价格
					bg = gm.getPrice();
				}

				// 总价（不参与任何优惠的价格）
				totalAmount = totalAmount.add(bg.multiply(amount));
				// 不打折
				notdiscount = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN))
						.multiply(amount);
				// 减去固定值的单价乘以数量乘以各角色折扣
				// BigDecimal fmbd = new BigDecimal(0);
				realpay = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN)).multiply(amount)
						.multiply(spg);
				realpay = realpay.setScale(2, RoundingMode.HALF_UP);
				notdiscount = notdiscount.setScale(2, RoundingMode.HALF_UP);
				// BigDecimal finalprice = bg.subtract(fmbd);
				// //
				// realpay = finalprice.multiply(amount);
				//
				// realpay = realpay.multiply(spg);
				// 建立订单与商品关系
				og = new OrderGoods();
				og.setId(UUIDUtil.getUUID());
				og.setGoodsId(goodsids);
				og.setName(gm.getGoodsTitle());
				og.setNum(num);
				og.setOrderId(orderid);
				og.setPicAddr(goodspic);
				// 获取拼团价格
				if (StringUtils.isNotEmpty(isesflag)) {
					og.setUnitPrice(gm.getPriceEasySpelling());
				} else {
					// 获取普通价格
					og.setUnitPrice(gm.getPrice());
				}
				oglist.add(og);
				orderClassMapper.addOrderGoods(oglist);
			}
		}

		if (StringUtils.isNotEmpty(goodspriceids)) {
			GoodsPriceModel gpm = goodsCartMapper.getGoodsPriceInfo(goodspriceids);
			og = new OrderGoods();
			og.setId(UUIDUtil.getUUID());
			og.setGoodsId(goodsids);
			og.setGoodsPriceId(goodspriceids);
			og.setSpecIds(gpm.getSpecIds());
			og.setName(gm.getGoodsTitle());
			og.setNum(num);
			og.setOrderId(orderid);
			if (StringUtils.isNotEmpty(gpm.getGoodsPic())) {
				og.setPicAddr(gpm.getGoodsPic());
			} else {
				og.setPicAddr(goodspic);
			}

			if (StringUtils.isNotEmpty(isesflag)) {
				og.setUnitPrice(gpm.getPriceEasySpelling());
			} else {
				og.setUnitPrice(gpm.getPrice());
			}
			oglist.add(og);
			orderClassMapper.addOrderGoods(oglist);

			if (gpm != null) {

				BigDecimal bg = null;
				if (StringUtils.isNotEmpty(isesflag)) {
					// 获取易拼价格
					bg = gpm.getPriceEasySpelling();
				} else {
					// 普通价格
					bg = gpm.getPrice();
				}

				// 总价（不参与任何优惠的价格）
				totalAmount = bg.multiply(amount);
				// //固定减免价格
				// BigDecimal fmbd = new BigDecimal(0);
				// fmbd = bg.multiply(crm);
				// fmbd = fmbd.divide(totalAmount,2, RoundingMode.HALF_UP);
				// //减去固定值的单价
				// BigDecimal finalprice = bg.subtract(fmbd);
				// //乘以数量
				// realpay = finalprice.multiply(amount);
				// //乘以各角色折扣
				// realpay = realpay.multiply(spg);
				// 不打折
				notdiscount = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN))
						.multiply(amount);
				realpay = bg.subtract(bg.multiply(crm).divide(totalAmount, 4, RoundingMode.HALF_DOWN)).multiply(amount)
						.multiply(spg);
				realpay = realpay.setScale(2, RoundingMode.HALF_UP);
				notdiscount = notdiscount.setScale(2, RoundingMode.HALF_UP);
			}
		}
		// 总金额
		om.setTotalFee(totalAmount);
		// 角色减免价
		BigDecimal userPrices = null;
		if ((!ConstantIF.ROLE_DISTINGUISH07.equals(rinfo.getRoleDistinguish()))
				&& (!ConstantIF.ROLE_DISTINGUISH08.equals(rinfo.getRoleDistinguish()))) {
			// 角色减免金额总
			userPrices = notdiscount.subtract(realpay);
		} else {
			// 角色减免金额总
			userPrices = new BigDecimal(0);
		}
		om.setRoleReduceFee(userPrices);
		// 判断是否满足减免金额
		if (cp != null) {
			BigDecimal bgcc = new BigDecimal(cp.getCouponCondtion());
			int compareto = totalAmount.compareTo(bgcc);
			// a = -1,表示小于a = 0,表示等于a = 1,表示大于
			if (compareto != -1) {
				// 修改优惠券使用状态
				UserCouponRelate ucr = new UserCouponRelate();
				ucr.setId(couponrelateid);
				ucr.setUserUsed(ConstantIF.COUPON_USE_STATUS02);
				ucr.setUsedDate(new Timestamp(new Date().getTime()));
				ucr.setUpdateBy(userid);
				couponMapper.updateCoupon(ucr);
				// realpay = realpay.subtract(crm);
			}
		}
		BigDecimal payprices = new BigDecimal(payprice);
		int paymoney = payprices.compareTo(totalAmount.subtract(crm));
		if (paymoney != 0) {
			return new ResultBean("2016", ResultApiMsg.C_2016);
		}

		// 实付金额
		om.setPayFee(realpay);
		// 优惠金额(优惠券减免金额)
		om.setCouponReduceFee(totalAmount.subtract(realpay).subtract(userPrices));
		/** 订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货 */
		om.setOrderStatus(ConstantIF.ORDER_STATUS01);
		/** 01:正常下单02：易拼 03：易团 */
		if (StringUtils.isNotEmpty(isesflag)) {
			om.setGoodsType(ConstantIF.GOODS_TYPE02);
		} else {
			om.setGoodsType(ConstantIF.GOODS_TYPE01);
		}
		om.setCouponId(couponid);
		om.setCouponRelateId(couponrelateid);
		om.setSource(source);

		if (StringUtils.isNotEmpty(isesflag)) {
			// 发起易拼
			String esids = UUIDUtil.getUUID();
			EasySpellingUserRelate esur = new EasySpellingUserRelate();
			if (!StringUtils.isNotEmpty(esid)) {
				Timestamp nousedate = new Timestamp(new Date().getTime());
				EasySpelling es = new EasySpelling();
				es.setId(esids);
				es.setUserId(userid);
				// 通过goodsid获取易拼参数表信息
				EasySpellingParameter esp = easyspellingMapper.getEasySpellingParameter(goodsids);
				if (esp != null) {
					String espid = esp.getId();
					es.setEasySpellingParameterId(espid);
					// 开始时间
					// Timestamp starttime = esp.getStartTime();
					// 结束时间
					Timestamp endtime = esp.getEndTime();
					// 易拼开始时间
					es.setStartTime(nousedate);
					Long espendtime = endtime.getTime();
					Long esendtime = nousedate.getTime() + 1000 * 60 * 60 * 24L;
					// 易拼结束时间
					if (esendtime < espendtime) {
						es.setEndTime(new Timestamp(esendtime));
					} else if ((nousedate.getTime() < espendtime) && (esendtime >= espendtime)) {
						es.setEndTime(endtime);
					} else {
						return new ResultBean("2014", ResultApiMsg.C_2014);
					}
					// 订单结束时间
					Long orderentime = nousedate.getTime() + 1000 * 60 * 30L;
					// 订单表的结束时间
					om.setOrderStartTime(nousedate);
					if ((orderentime <= esendtime) && (orderentime <= espendtime)) {
						om.setOrderEndTime(new Timestamp(orderentime));
					} else {
						return new ResultBean("2012", ResultApiMsg.C_2012);
					}
				}
				es.setCreateTime(nousedate);
				// 添加易拼
				easyspellingMapper.addEasySpelling(es);
				esur.setId(UUIDUtil.getUUID());
				esur.setEasySpellingId(esids);
				esur.setUserId(userid);
				// 添加易拼人员关系
				easyspellingMapper.addEasySpellingUserRelate(esur);
				om.setEasySpellingId(esids);

			} else {
				// 拼团限制人数
				int spellingnum = easyspellingMapper.getSpellingNum(esid);
				// 已经拼团的人数
				int spellednum = easyspellingMapper.getUserSpellingNum(userid);
				if (spellingnum <= spellednum) {
					return new ResultBean("2017", ResultApiMsg.C_2017);
				}
				String personspelling = easyspellingMapper.getPersonSpellingNum(esid);
				int personspellings = easyspellingMapper.getSpellingPersonStatus(userid, esid);
				if (userid.equals(personspelling) || personspellings > 0) {
					return new ResultBean("2018", ResultApiMsg.C_2018);
				}
				om.setEasySpellingId(esid);
				esur.setId(UUIDUtil.getUUID());
				esur.setUserId(userid);
				esur.setEasySpellingId(esid);
				easyspellingMapper.addEasySpellingUserRelate(esur);
			}
		}
		// 添加收货人信息
		ConsigneeModel cm = orderClassMapper.getConsigneeInfo(addressids);
		if (cm != null) {
			om.setName(cm.getName());
			om.setTelno(cm.getTelno());
			om.setDetailAddr(cm.getDetailAddr());
			om.setAreaCode(cm.getAreaCode());
		}
		om.setCreateBy(userid);
		orderClassMapper.addConfirmOrder(om);
		return new ResultBean(orderid);
	}

	/*
	 * (non-Javadoc)</p> <p>Description: 更新待支付超时订单</p>
	 * 
	 * @see dianfan.service.order.OrderClassService#espOrderCloseOutPay()
	 */
	@Override
	@Transactional
	public void espOrderCloseOutPay() {
		// 查询符合条件的订单(易拼订单)【待付款超过截止时间的】
		// 1.创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("goodsType", ConstantIF.GOODS_TYPE02);
		param.put("orderStatus", ConstantIF.ORDER_STATUS01);
		param.put("nowDateTime", DateUtility.getCurrentDate(true));
		List<OrderCloseModel> ls = orderClassMapper.findAllOrders(param);
		// 订单关闭
		for (int i = 0; i < ls.size(); i++) {
			// 订单关闭
			Map<String, Object> param2 = new HashMap<>();
			param2.put("orderId", ls.get(i).getOrderId());
			param2.put("goodsType", ls.get(i).getGoodsType());
			param2.put("orderStatus", ConstantIF.ORDER_STATUS01);
			param2.put("toUpdateStatus", ConstantIF.ORDER_STATUS21);
			param2.put("version", ls.get(i).getVersion());
			int j = orderClassMapper.updateOrders(param2);
			// if (j > 0) {
			// // 订单关闭成功将查询优惠券存在将更新返还
			// if (!StringUtility.isNull(ls.get(i).getCouponRelateId())) {
			// UserCouponRelate ucr = new UserCouponRelate();
			// ucr.setUserId(ls.get(i).getUserId());
			// ucr.setId(ls.get(i).getCouponRelateId());
			// ucr.setUserUsed(0);
			// couponMapper.updateResverCoupon(ucr);
			// }
			// }
		}
	}

	/*
	 * (non-Javadoc)</p> <p>Description: 更新未拼满超时订单</p>
	 * 
	 * @see dianfan.service.order.OrderClassService#espOrderCloseOutEspEndTime()
	 */
	@Override
	@Transactional
	public void espOrderCloseOutEspEndTime() {
		Map<String, Object> param = new HashMap<>();
		param.put("goodsType", ConstantIF.GOODS_TYPE02);
		param.put("orderStatus", ConstantIF.ORDER_STATUS61);
		param.put("currentDate", DateUtility.getCurrentDate(true));
		List<OrderCloseOutEspEndTimeModel> ls = orderClassMapper.findCloseOutEspEndTimeOrders(param);

		// 订单关闭
		for (int i = 0; i < ls.size(); i++) {
			Map<String, Object> param2 = new HashMap<>();
			param2.put("orderId", ls.get(i).getOrderId());
			param2.put("goodsType", ls.get(i).getGoodsType());
			param2.put("orderStatus", ConstantIF.ORDER_STATUS61);
			param2.put("toUpdateStatus", ConstantIF.ORDER_STATUS21);
			param2.put("version", ls.get(i).getVersion() + 1);
			// 订单关闭
			int j = orderClassMapper.updateOrders(param2);
			if (j > 0) {
				/*
				 * // 订单关闭成功将查询优惠券存在将更新返还 if
				 * (!StringUtility.isNull(ls.get(i).getCouponRelateId())) { UserCouponRelate ucr
				 * = new UserCouponRelate(); ucr.setUserId(ls.get(i).getUserId());
				 * ucr.setId(ls.get(i).getCouponRelateId()); ucr.setUserUsed(0);
				 * couponMapper.updateResverCoupon(ucr); // 将订单优惠券relateid置空
				 * couponMapper.updateOrderReateIdClear(ls.get(i).getOrderId()); }
				 */

				// 查询流水表为支付成功的状态
				Map<String, Object> param3 = new HashMap<>();
				param3.put("orderId", ls.get(i).getOrderId());
				param3.put("newOrderStatus", ConstantIF.PAY_STATUS_01);
				TradeSer ts = orderClassMapper.selectTradeSerPayStatusByOrderId(param3);
				if (ts != null) {
					// 修改流水状态为发起退款
					Map<String, Object> param4 = new HashMap<>();
					param4.put("orderId", ts.getOrderid());
					param4.put("newOrderStatus", ConstantIF.PAY_STATUS_04);
					param4.put("oldOrderStatus", ts.getOrderStatus());
					param4.put("version", ts.getVersion());
					orderClassMapper.updateTradeSerPayStatus(param4);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)</p> <p>Description: 更新交易流水表-退款</p>
	 * 
	 * @see dianfan.service.order.OrderClassService#updateTradeSerPayStatus(java.
	 * lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void updateTradeSerPayStatus() {
		// 检索交易流水表(发起退款)
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("newOrderStatus", ConstantIF.PAY_STATUS_04);
		List<TradeSer> tss = orderClassMapper.selectTradeSerPayStatus(param);

		// 所有带退货的流水
		for (int i = 0; i < tss.size(); i++) {
			// 更新退钱流水(退钱中,添加理由,金额)
			Map<String, Object> param2 = new HashMap<String, Object>();
			param2.put("orderId", tss.get(i).getOrderid());
			param2.put("newOrderStatus", ConstantIF.PAY_STATUS_04);
			param2.put("oldOrderStatus", tss.get(i).getOrderStatus());
			param2.put("version", tss.get(i).getVersion());
			param2.put("refundReason", "易拼发起退款-订单号" + tss.get(i).getOrderid());
			param2.put("refundMoney", tss.get(i).getDepositFee().doubleValue());
			orderClassMapper.updateTradeSerPayStatus(param2);
		}
	}

	/*
	 * (non-Javadoc) <p>Title: delOrderForever</p> <p>Description: 删除订单</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param orderid 订单id
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#delOrderForever(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delOrderForever", description = "删除订单")
	public ResultBean delOrderForever(String userid, String orderid) {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("orderid", orderid);

		// 首先确认该订单是否可以操作 包括 订单是否存在，且订单的状态是否是 06或者21或者22
		int count = orderClassMapper.getOrderByid(orderid);
		if (count < 1) {
			// !当前订单不可以操作
			return new ResultBean("4108", ResultApiMsg.C_4108);
		}

		// 关闭对应订单
		orderClassMapper.delOrderForever(param);
		// 成功
		return new ResultBean();

	}

	/*
	 * (non-Javadoc) <p>Title: acceptOrder</p> <p>Description: 订单确认收货完成</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param orderid 订单id
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#acceptOrder(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "acceptOrder", description = "订单确认收货完成")
	public void acceptOrder(String userid, String orderid) {
		// 修改订单状态
		orderClassMapper.updateAcceptOrderStatus(userid, orderid, "06");
		// 根据订单id获取佣金分成
		List<UserBounsDetail> bouns = orderClassMapper.findCommissionInfoByOrderid(orderid);

		if (bouns.isEmpty()) {
			return;
		}
		// 根据用户id获取用户的剩余体现额度
		List<String> users = new ArrayList<>();
		Map<String, BigDecimal> m = new HashMap<>();
		for (UserBounsDetail ubd : bouns) {
			users.add(ubd.getUserId());
			m.put(ubd.getUserId(), ubd.getBounsFee());
		}
		List<UserLastMoney> user_last_money = orderCommissionMapper.findUserLastMoney(users);
		// 计算佣金
		for (UserLastMoney ulm : user_last_money) {
			BigDecimal lastMoney = ulm.getLastMoney();
			lastMoney = lastMoney.add(m.get(ulm.getUserid()));
			ulm.setLastMoney(lastMoney.setScale(1, BigDecimal.ROUND_HALF_UP));
		}
		// 更新用户佣金
		orderCommissionMapper.updateUserLastMoney(user_last_money);
		// 更新佣金流水表状态
		orderClassMapper.updateBounsStatusByOrderid(orderid);
	}

	/*-----------------------------------------------------------------------*/
	/*
	 * (non-Javadoc) <p>Title: acceptOrder</p> <p>Description: 系统自动订单确认收货完成</p>
	 * 
	 * @param userid 用户id
	 * 
	 * @param orderid 订单id
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#acceptOrder(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delOrderForever", description = "系统订单确认收货完成")
	public void acceptOrderForXt(String orderid) {
		// 修改订单状态
		orderClassMapper.updateAcceptOrderStatusxt(orderid, "06");
		// 根据订单id获取佣金分成
		List<UserBounsDetail> bouns = orderClassMapper.findCommissionInfoByOrderid(orderid);

		if (bouns.isEmpty()) {
			return;
		}
		// 根据用户id获取用户的剩余体现额度
		List<String> users = new ArrayList<>();
		Map<String, BigDecimal> m = new HashMap<>();
		for (UserBounsDetail ubd : bouns) {
			users.add(ubd.getUserId());
			m.put(ubd.getUserId(), ubd.getBounsFee());
		}
		List<UserLastMoney> user_last_money = orderCommissionMapper.findUserLastMoney(users);
		// 计算佣金
		for (UserLastMoney ulm : user_last_money) {
			BigDecimal lastMoney = ulm.getLastMoney();
			lastMoney = lastMoney.add(m.get(ulm.getUserid()));
			ulm.setLastMoney(lastMoney.setScale(1, BigDecimal.ROUND_HALF_UP));
		}
		// 更新用户佣金
		orderCommissionMapper.updateUserLastMoney(user_last_money);
		// 更新佣金流水表状态
		orderClassMapper.updateBounsStatusByOrderid(orderid);
	}
	/*-----------------------------------------------------------------------*/

	/*
	 * (non-Javadoc) <p>Title: orderRefund</p> <p>Description: 订单退款</p>
	 * 
	 * @param orderids 订单id列表 link: @see
	 * dianfan.service.order.OrderClassService#orderRefund(java.util.List)
	 */
	@Override
	public void orderRefund(List<String> orderids) {
		// 获取待退款订单
		List<TradeSer> refundOrders = orderClassMapper.findRefundOrders(orderids);
		if (refundOrders.isEmpty()) {
			return;
		}

		// 退款请求
		for (TradeSer ts : refundOrders) {
			if (StringUtils.equals(ts.getPayWays(), ConstantIF.PAY_WAYS_01)) {
				// 支付宝退款
				if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_02)) {
					// app
				} else if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_03)) {
					// web
				}
			} else if (StringUtils.equals(ts.getPayWays(), ConstantIF.PAY_WAYS_02)) {
				WXPayConfig conf = null;
				// 微信退款
				if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_01)) {
					// 小程序
					conf = new WxPayConfigImpl(PropertyUtil.getProperty("wx.applet.appid"),
							PropertyUtil.getProperty("wx.mp.mchid"), PropertyUtil.getProperty("wx.mp.key"),
							PropertyUtil.getProperty("wx.mp.cret"));
				} else if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_02)) {
					// app
				} else if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_03)) {
					// web
				}

				WxOrderRefundBean orderRefund = new WxOrderRefundBean();
				orderRefund.setTransaction_id(ts.getTradeNo());
				orderRefund.setOut_refund_no(ts.getPayid());
				orderRefund.setRefund_desc(ts.getRefundReason());
				BigDecimal depositFee = ts.getDepositFee();
				orderRefund.setTotal_fee(depositFee.multiply(new BigDecimal(100)).intValue());
				BigDecimal refundMoney = ts.getRefundMoney();
				orderRefund.setRefund_fee(refundMoney.multiply(new BigDecimal(100)).intValue());
				orderRefund.setNotify_url(PropertyUtil.getProperty("wx.refund_notify_url"));
				// 请求退款
				try {
					Map<String, String> refund = new WxPayCore(conf).refund(orderRefund);
					if (StringUtils.equals(refund.get("return_code"), "SUCCESS")) {
						// 退款申请成功
						ts.setOrderStatus(ConstantIF.PAY_STATUS_05);
					} else {
						// 退款申请失败
						ts.setOrderStatus(ConstantIF.PAY_STATUS_07);
						ts.setRefundFailReason(refund.get("err_code_des"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					// 退款申请失败
					ts.setOrderStatus(ConstantIF.PAY_STATUS_07);
					ts.setRefundFailReason("退款申请发起失败");
				}
			} else if (StringUtils.equals(ts.getPayWays(), ConstantIF.PAY_WAYS_03)) {
				// 银联退款
			} else {
				// 类型未知
				continue;
			}
			// 更新流水状态
			orderClassMapper.updateOrderRefundTradeSer(ts);
		}
	}

	public void afterSaleOrderRefund(List<String> orderids) {
		// 获取待退款订单
		List<TradeSer> refundOrders = orderClassMapper.findRefundOrders(orderids);
		if (refundOrders.isEmpty()) {
			return;
		}
		// 退款请求
		for (TradeSer ts : refundOrders) {
			if (StringUtils.equals(ts.getPayWays(), ConstantIF.PAY_WAYS_01)) {
				// 支付宝退款
				if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_02)) {
					// app
				} else if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_03)) {
					// web
				}
			} else if (StringUtils.equals(ts.getPayWays(), ConstantIF.PAY_WAYS_02)) {
				WXPayConfig conf = null;
				// 微信退款
				if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_01)) {
					// 小程序
					conf = new WxPayConfigImpl(PropertyUtil.getProperty("wx.mp.appid"),
							PropertyUtil.getProperty("wx.mp.mchid"), PropertyUtil.getProperty("wx.mp.key"),
							PropertyUtil.getProperty("wx.mp.cret"));
				} else if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_02)) {
					// app
				} else if (StringUtils.equals(ts.getPaySource(), ConstantIF.PAY_SOURCE_03)) {
					// web
				}
				// 退款理由
				ts.setRefundReason("售后退款-订单号" + ts.getOrderid());
				// 将实付金额->退款金额插入
				ts.setRefundMoney(ts.getDepositFee());
				WxOrderRefundBean orderRefund = new WxOrderRefundBean();
				orderRefund.setTransaction_id(ts.getTradeNo());
				orderRefund.setOut_refund_no(ts.getPayid());
				orderRefund.setRefund_desc(ts.getRefundReason());
				BigDecimal depositFee = ts.getDepositFee();
				// 插入退款金额
				orderRefund.setTotal_fee(depositFee.multiply(new BigDecimal(100)).intValue());
				BigDecimal refundMoney = ts.getRefundMoney();
				orderRefund.setRefund_fee(refundMoney.multiply(new BigDecimal(100)).intValue());
				orderRefund.setNotify_url(PropertyUtil.getProperty("wx.refund_notify_url"));
				// 请求退款
				try {
					Map<String, String> refund = new WxPayCore(conf).refund(orderRefund);
					if (StringUtils.equals(refund.get("return_code"), "SUCCESS")) {
						// 退款申请成功
						ts.setOrderStatus(ConstantIF.PAY_STATUS_05);

					} else {
						// 退款申请失败
						ts.setOrderStatus(ConstantIF.PAY_STATUS_07);
						ts.setRefundFailReason(refund.get("err_code_des"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					// 退款申请失败
					ts.setOrderStatus(ConstantIF.PAY_STATUS_07);
					ts.setRefundFailReason("退款申请发起失败");
				}
			} else if (StringUtils.equals(ts.getPayWays(), ConstantIF.PAY_WAYS_03)) {
				// 银联退款
			} else {
				// 类型未知
				continue;
			}
			// 更新流水状态
			orderClassMapper.updateAfterSaleradeSer(ts);
		}
	}

	/*
	 * (non-Javadoc) <p>Title: orderRefund</p> <p>Description: 订单查询</p>
	 * 
	 * @see
	 * dianfan.service.order.OrderClassService#selectTradeSerPayStatus(java.lang.
	 * String)
	 */
	@Override
	public List<TradeSer> selectTradeSerPayStatus(String payStatus) {
		// 检索交易流水表(发起退款)
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("newOrderStatus", payStatus);
		return orderClassMapper.selectTradeSerPayStatus(param);
	}

	/**
	 * @Title: findOrderInfoList
	 * @Description:
	 * @param goodstype
	 *            订单类型(01:正常下单02：易拼 03：易团)
	 * @param starmoney
	 *            起始价格(价格区间)
	 * @param endmoney
	 *            终了价格(价格区间)
	 * @param factoryid
	 *            工厂id
	 * @param orderstatus
	 *            订单的状态(01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货)
	 * @param goodsstatus
	 *            商品的状态(10-自动分配 11-异常订单(生产不了)12-手动分配订单到工厂(内部专用)13-待生产 14-生产完成 )
	 * @param payways
	 *            支付方式 01-ALI(支付宝) 02-WX(微信) 03-BC(银行卡
	 * @param page
	 *            第几页
	 * @param pagecounts
	 *            每页的条数
	 * @return
	 * @throws:
	 * @time: 2018年7月16日 下午2:24:54
	 */
	@Override
	public ResultBean findOrderInfoList(String userid, String orderid, String goodstype, String starmoney,
			String endmoney, String startime, String endtime, String factoryid, String orderstatus, String goodsstatus,
			String payways, String deliverystatus, String consignee, String ordersource, String paystatus, Integer page,
			Integer pagecounts) {
		// TODO Auto-generated method stub
		// 响应map 容器
		Map<String, Object> data = new HashMap<>();
		// 创建入参容器
		Map<String, Object> params = new HashMap<>();
		params.put("orderid", orderid);
		params.put("goodstype", goodstype);
		// 判断要查询的价格区间是否为空
		if (StringUtils.isNotEmpty(starmoney) && StringUtils.isNotEmpty(endmoney)) {
			BigDecimal stmoney = new BigDecimal(starmoney);
			BigDecimal edmoney = new BigDecimal(endmoney);
			params.put("stmoney", stmoney);
			params.put("edmoney", edmoney);
		} else {
			params.put("stmoney", null);
			params.put("edmoney", null);
		}
		// 判断要查询的时间区间是否为空
		if (StringUtils.isNotEmpty(startime) && StringUtils.isNotEmpty(endtime)) {
			params.put("startime", startime);
			params.put("endtime", endtime);
		} else {
			params.put("startime", null);
			params.put("endtime", null);
		}

		// 获取工厂账号
		List<String> adminid = orderClassMapper.getFactoryAdmin(factoryid);
		if (adminid != null && adminid.size() > 0) {
			for (int i = 0; i < adminid.size(); i++) {
				if (StringUtils.isNotEmpty(adminid.get(i)) && userid.equals(adminid.get(i))) {
					params.put("factoryid", factoryid);
				} else {
					if (StringUtils.isNotEmpty(factoryid)) {
						params.put("factoryid", factoryid);
					} else {
						params.put("factoryid", null);
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(orderstatus) && !"00".equals(orderstatus)) {
			String[] ords = orderstatus.split(",");
			params.put("orderstatus", ords);
		} else {
			params.put("orderstatus", null);
		}

		String[] goodsta = goodsstatus.split(",");
		int len = goodsta.length;
		if (len == 1) {
			if ("00".equals(goodsta[0])) {
				// 传入三个商品状态
				params.put("ogoodsstatus", null);
				params.put("tgoodsstatus", null);
				params.put("wgoodsstatus", null);
			} else {
				params.put("ogoodsstatus", goodsta[0]);
				params.put("tgoodsstatus", null);
				params.put("wgoodsstatus", null);
			}

		}
		if (len == 2) {
			// 传入三个商品状态
			params.put("ogoodsstatus", goodsta[0]);
			params.put("tgoodsstatus", goodsta[1]);
			params.put("wgoodsstatus", null);
		}
		if (len == 3) {
			// 传入三个商品状态
			params.put("ogoodsstatus", goodsta[0]);
			params.put("tgoodsstatus", goodsta[1]);
			params.put("wgoodsstatus", goodsta[2]);
		}

		params.put("payways", payways);
		params.put("deliverystatus", deliverystatus);
		if (StringUtils.isNotEmpty(consignee)) {
			params.put("consignee", consignee);
		} else {
			params.put("consignee", null);
		}
		params.put("source", ordersource);
		params.put("paystatus", paystatus);
		// 查询数量
		int count = orderClassMapper.findOrderListCount(params);
		// 设置总页数
		data.put("totalcount", count);
		if (count < (page - 1) * pagecounts || count == 0) {
			// 空的返回实体
			data.put("orderlist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		params.put("start", (page - 1) * pagecounts);
		// 分页偏移量 10
		params.put("offset", pagecounts);
		// 调库查询订单列表
		List<OrderModel> orderlist = orderClassMapper.findOrderInfoList(params);
		// 获取订单列表中的所有订单的id
		List<String> oid = new ArrayList<>();
		for (OrderModel g : orderlist) {
			oid.add(g.getOrderId());
		}
		/*
		 * // 获取订单下面的商品 List<OrderGoods> goodss = orderClassMapper.findGoodss(oid);
		 * Map<String, List<OrderGoods>> goodsl = new HashMap<>(); if (!goodss.isEmpty()
		 * && goodss.size() > 0) { // 下面的操作是将订单下面的商品 转换成一个 key是订单的id，值是订单下的商品的一个map for
		 * (OrderGoods g : goodss) { // 将List<OrderGoods> goodss，去转化成一个已orderid
		 * 为key，list<对象0>为值得一个map if (goodsl.containsKey(g.getOrderId())) { //
		 * 如果map中有对应orderID为key的list，就将当前循环出来的对象放置到这个list中 List<OrderGoods> list =
		 * goodsl.get(g.getOrderId()); list.add(g); } else { // 如果不存在，就新
		 * 建一个list，存放当前的对象，再已orderID为key，list<对象0>为值，放置到map中 List<OrderGoods> list = new
		 * ArrayList<>(); list.add(g); goodsl.put(g.getOrderId(), list); } } //
		 * 将订单下的商品信息补全 for (OrderModel order : orderlist) { String orderid =
		 * order.getOrderId(); if (goodsl.containsKey(orderid)) {
		 * order.setOrderGoods(goodsl.get(orderid)); } } }
		 */

		/*
		 * // 获取所有的规格和对应名称 List<GoodsSpec> specList = orderClassMapper.fildSpecList();
		 * // 将规格和名称转化成一个map格式 Map<String, String> size = new HashMap<>(); for
		 * (GoodsSpec gs : specList) { size.put(gs.getId(), gs.getTypeAndName()); }
		 */
		/* string类型返回值整理 */
		for (OrderModel pic : orderlist) {
			/*
			 * // 循环出订单中的每一个商品 if
			 * (!org.springframework.util.StringUtils.isEmpty(pic.getOrderGoods())) {
			 * List<OrderGoods> orderGoods = pic.getOrderGoods(); for (OrderGoods order :
			 * orderGoods) { // 创建一个空的string，用于下面存放商品的规格 String str = ""; String specIds =
			 * order.getSpecIds(); if (!StringUtility.isNull(specIds)) { String[] s1 =
			 * specIds.split(","); for (int i = 0; i < s1.length; i++) { for (String s2 :
			 * size.keySet()) { if (s1[i].equals(s2)) { str += size.get(s2); } } } //
			 * 将商品规格加入 string类型 order.setSpecList(str); }
			 * 
			 * // 将商品的中的图片的路径修改一下 order.setPicAddr(PropertyUtil.getProperty("domain") +
			 * order.getPicAddr()); } }
			 */
			// 获取地区name
			String areacode = pic.getAreaCode();
			String pcareaname = "";
			if (StringUtils.isNotEmpty(areacode)) {
				AreaModel areaName = orderClassMapper.getAreaName(areacode);
				// 区域name
				String aname = areaName.getAreaCode();
				AreaModel cityName = orderClassMapper.getAreaName(areaName.getSupAreaCode());
				// 城市name
				String cname = cityName.getAreaCode();
				AreaModel prName = orderClassMapper.getAreaName(cityName.getSupAreaCode());
				// 省份name
				String pname = prName.getAreaCode();
				pcareaname = pname + "/" + cname + "/" + aname;
			}
			pic.setAreaName(pcareaname);
			// 如果工厂id不为空则取出工厂名称
			if (StringUtils.isNotEmpty(pic.getFactoryListId())) {
				String factoryname = factoryMapper.getFactoryName(pic.getFactoryListId());
				pic.setFactoryName(factoryname);
			}
			String pcaname = orderClassMapper.getPCityAreaName(areacode);
			pic.setDetailAddr(pcaname + pic.getDetailAddr());

			/*
			 * //获取订单用户信息 String userids = pic.getUserId(); UserInfoModel um =
			 * personalInfoMapper.getUserInfo(userids); pic.setUserInfoModel(um); //获取订单上级信息
			 * OrderSuperior os = orderClassMapper.getSuperiorInfo(pic.getOrderId());
			 * pic.setOrderSuperior(os);
			 */
		}
		/* 整理返回参数 */
		// 设置当前页面
		data.put("page", page);
		// 添加返回的 订单列表列表
		data.put("orderlist", orderlist);
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc) <p>Title: updateBgOrderPrice</p> <p>Description: </p>
	 * 
	 * @param orderid
	 * 
	 * @param money link: @see
	 * dianfan.service.order.OrderClassService#updateBgOrderPrice(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public void updateBgOrderPriceOrMessage(String userid, String orderid, String money, String custmessage,
			String orderstatus, String goodsstatus, String paystatus, String factoryid) {
		// TODO Auto-generated method stub
		// 获取订单版本号
		String version = orderClassMapper.getVersionInfo(orderid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", orderid);

		// 2.获取详情
		OrderModel info = orderClassMapper.getBgOrderInfo(orderid);
		// 需要修改的金额
		if (StringUtils.isNotEmpty(money)) {
			BigDecimal bd = new BigDecimal(money);
			BigDecimal pfee = info.getPayFee();
			if (pfee.compareTo(bd) != -1) {
				BigDecimal discount = info.getPayFee().subtract(bd);
				BigDecimal disfee = info.getDiscountFee();
				if (disfee == null) {
					disfee = new BigDecimal("0.00");
				}
				BigDecimal discounts = disfee.add(discount);
				params.put("money", bd);
				params.put("discount", discounts);
			}

		} else {
			params.put("money", null);
		}
		// 客户备注信息
		if (StringUtils.isNotEmpty(custmessage)) {
			params.put("custmessage", custmessage);
		} else {
			params.put("custmessage", null);
		}
		// 订单状态
		if (StringUtils.isNotEmpty(orderstatus)) {
			params.put("orderstatus", orderstatus);
		} else {
			params.put("orderstatus", null);
		}
		// 商品状态
		if (StringUtils.isNotEmpty(goodsstatus)) {
			params.put("goodsstatus", goodsstatus);
		} else {
			params.put("goodsstatus", null);
		}
		// 修改工厂
		if (StringUtils.isNotEmpty(factoryid)) {
			params.put("factoryid", factoryid);
		} else {
			params.put("factoryid", null);
		}
		params.put("userid", userid);
		params.put("version", version);
		orderClassMapper.updateBgOrderPriceOrMessage(params);
		/*
		 * // 交易流水表支付状态修改 TradeSer ts = orderClassMapper.getTradeSerInfo(orderid);
		 * TradeSer tser = new TradeSer(); tser.setPayid(ts.getPayid());
		 * tser.setOrderid(ts.getOrderid()); tser.setVersion(ts.getVersion());
		 * tser.setOrderStatus(paystatus);
		 * orderClassMapper.updateBgTradeSerPayStatus(tser);
		 */
	}

	/**
	 * @Title: updateBgOrderConsigneeInfo
	 * @Description:
	 * @param orderid
	 *            订单id
	 * @param areacode
	 *            区域code
	 * @param name
	 *            姓名
	 * @param telno
	 *            手机号
	 * @param detailaddr
	 *            详细地址
	 * @throws:
	 * @time: 2018年7月17日 上午9:43:37
	 */
	@Override
	@Transactional
	public void updateBgOrderConsigneeInfo(String userid, String orderid, String areacode, String name, String telno,
			String detailaddr) {
		// TODO Auto-generated method stub
		String version = orderClassMapper.getVersionInfo(orderid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", orderid);
		if (StringUtils.isNotEmpty(areacode)) {
			params.put("areacode", areacode);
		} else {
			params.put("areacode", null);
		}
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", name);
		} else {
			params.put("name", null);
		}
		if (StringUtils.isNotEmpty(telno)) {
			params.put("telno", telno);
		} else {
			params.put("telno", null);
		}
		if (StringUtils.isNotEmpty(detailaddr)) {
			params.put("detailaddr", detailaddr);
		} else {
			params.put("detailaddr", null);
		}
		params.put("userid", userid);
		params.put("version", version);
		orderClassMapper.updateBgOrderConsigneeInfo(params);
	}

	/*
	 * (non-Javadoc) <p>Title: getOrderInfo</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#getOrderInfo(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ResultBean getOrderInfo(String userid, String orderid) {
		// 1.创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("orderid", orderid);
		// 2.获取详情
		OrderModel info = orderClassMapper.getBgOrderInfo(orderid);
		// 3.获取订单列表中的区域code，去省市区县表中将完整信息返回出来
		String code = info.getAreaCode();
		String pcareaname = "";
		if (StringUtils.isNotEmpty(code)) {
			AreaModel areaName = orderClassMapper.getAreaName(code);
			// 区域name
			String aname = areaName.getAreaCode();
			AreaModel cityName = orderClassMapper.getAreaName(areaName.getSupAreaCode());
			// 城市name
			String cname = cityName.getAreaCode();
			AreaModel prName = orderClassMapper.getAreaName(cityName.getSupAreaCode());
			// 省份name
			String pname = prName.getAreaCode();
			pcareaname = pname + "/" + cname + "/" + aname;
		}
		info.setAreaName(pcareaname);
		// 如果工厂id不为空则取出工厂名称
		if (StringUtils.isNotEmpty(info.getFactoryListId())) {
			String factoryname = factoryMapper.getFactoryName(info.getFactoryListId());
			info.setFactoryName(factoryname);
		}
		String addressCode = orderClassMapper.findAreaCode(code);
		// 将查出来的地域信息添加到详情中
		info.setAddressCode(addressCode);
		// 获取优惠券id
		String couponid = info.getCouponId();
		if (StringUtils.isNotEmpty(couponid)) {
			Coupon cn = couponMapper.getBgCouponDetail(couponid);
			if (cn != null) {
				String ccid = cn.getCouponClassifyId();
				if (StringUtils.isNotEmpty(ccid)) {
					String[] ccids = ccid.split(",");
					String gcn = couponMapper.getGoodsClassifyName(ccids);
					if (StringUtils.isNotEmpty(gcn)) {
						cn.setCouponClassifyName(gcn);
					}
				}
				info.setCoupon(cn);
			}
		}
		// 获取所有的规格和对应名称
		List<GoodsSpec> specList = orderClassMapper.fildSpecList();
		// 将上面查出来的数据转化成一个map 方便下面去转化
		Map<String, String> size = new HashMap<>();
		for (GoodsSpec gs : specList) {
			size.put(gs.getId(), gs.getTypeAndName());
		}
		// 获取其中的specIds
		List<OrderGoods> orderGoods = info.getOrderGoods();
		if (!org.springframework.util.StringUtils.isEmpty(orderGoods)) {
			for (OrderGoods order : orderGoods) {
				String str = "";
				String specIds = order.getSpecIds();
				if (StringUtils.isNotEmpty(specIds)) {
					String[] s1 = specIds.split(",");
					for (int i = 0; i < s1.length; i++) {
						for (String s2 : size.keySet()) {
							if (s1[i].equals(s2)) {
								str += size.get(s2);
							}
						}
					}
					order.setSpecList(str);
				}
			}
		}

		// 3.将商品的图片的途径修改一下
		if (info != null) {
			List<OrderGoods> pic = info.getOrderGoods();
			for (OrderGoods url : pic) {
				url.setPicAddr(PropertyUtil.getProperty("domain") + url.getPicAddr());
			}
		}
		// 获取订单用户信息
		String userids = info.getUserId();
		UserInfoModel um = personalInfoMapper.getUserInfo(userids);
		info.setUserInfoModel(um);
		// 获取订单上级信息
		List<OrderSuperior> os = orderClassMapper.getSuperiorInfo(info.getOrderId());
		info.setOrderSuperior(os);
		List<DeliveryModels> dmlist = orderClassMapper.findDeliveryList(orderid);
		info.setDmlist(dmlist);
		// 4.成功
		return new ResultBean(info);
	}

	/*
	 * (non-Javadoc) <p>Title: updateConfirmDelivery</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @param orderstatus link: @see
	 * dianfan.service.order.OrderClassService#updateConfirmDelivery(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean updateConfirmDelivery(String userid, String orderid, String orderstatus, String pickupDate,
			String receiptFlag, String receiverPhone, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String predictDate, String senderName, String senderMob,
			String senderPhone, String senderAddr, String sendTo, String paperFrom) {
		// TODO Auto-generated method stub
		// 获取商品信息
		List<Goods> glist = orderClassMapper.findGoodsInfoByOrderId(orderid);
		OrderModel gm = orderClassMapper.getBgOrderInfo(orderid);
		LogisticsModel lm = null;
		if (glist != null && glist.size() > 0) {
			for (int i = 0; i < glist.size(); i++) {
				// 获取同种商品的数量
				int numlen = glist.get(i).getNum();
				for (int j = 0; j < numlen; j++) {
					EclpCoTransportLasWayBillRequest data = new EclpCoTransportLasWayBillRequest();
					// 生成运单号
					String waybillNo = GenRandomNumUtil.getOrderNo();
					System.out.println("+++++++++++++++++++++" + waybillNo + "++++++++++++++++++++++++++");
					data.setOrderNo(waybillNo);
					data.setSenderName(senderName);
					data.setSenderMobile(senderMob);
					data.setSenderPhone(senderPhone);
					data.setSenderAddress(senderAddr);
					data.setReceiverName(gm.getGainName());
					data.setReceiverMobile(gm.getGainTelno());
					data.setReceiverPhone(receiverPhone);
					String cityareaName = "";
					if (gm != null) {
						String areacode = gm.getAreaCode();
						cityareaName = orderClassMapper.getCityAreaName(areacode);
					}
					// 拼接收件人地址
					data.setReceiverAddress(cityareaName + gm.getDetailAddr());
					data.setRemark(gm.getCustMessage());
					data.setIsFragile(glist.get(i).getIsFragile());
					data.setSenderTc(sendTo);
					data.setPredictDate(predictDate);
					data.setIsJDOrder("2");
					data.setIsCod(glist.get(i).getIsCod());
					data.setReceiveable(glist.get(i).getReceiveable());
					data.setOnDoorPickUp(glist.get(i).getOnDoorPickUp());
					data.setPickUpDate(pickupDate);
					data.setIsGuarantee(glist.get(i).getIsGuarantee());
					data.setGuaranteeValue(glist.get(i).getGuaranteeMoney());
					data.setReceiptFlag(receiptFlag);
					data.setPaperFrom(paperFrom);
					data.setRtnReceiverName(rtnReceiverName);
					data.setRtnReceiverMobile(rtnReceiverMob);
					data.setRtnReceiverAddress(rtnReceiverAddr);
					data.setRtnReceiverPhone(rtnReceiverPhone);
					data.setWeight(glist.get(i).getWeight());
					data.setLength(glist.get(i).getLength());
					data.setWidth(glist.get(i).getWidth());
					data.setHeight(glist.get(i).getHeight());
					data.setInstallFlag(glist.get(i).getInstallFlag());
					data.setThirdCategoryNo(glist.get(i).getThridCategoryNo());
					data.setBrandNo(glist.get(i).getBrandNo());
					data.setProductSku(glist.get(i).getProductSku());
					data.setPackageName(glist.get(i).getGoodsTitle());
					ResultBean jbstatus = jdTransportService.transportLasWayBill(data);
					if ("200".equals(jbstatus.getCode())) {
						lm = new LogisticsModel();
						lm.setId(waybillNo);
						lm.setBrandNo(glist.get(i).getBrandNo());
						lm.setCreateBy(userid);
						lm.setDeliveryNo(GenRandomNumUtil.getOrderNo());
						lm.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
						// lm.setExpressNo(expressNo);
						lm.setHeight(glist.get(i).getHeight());
						lm.setInstallFlag(glist.get(i).getInstallFlag());
						lm.setThridCategoryNo(glist.get(i).getThridCategoryNo());
						lm.setIsCod(glist.get(i).getIsCod());
						lm.setIsFragile(glist.get(i).getIsFragile());
						lm.setIsGuarantee(glist.get(i).getIsGuarantee());
						lm.setGuaranteeMoney(glist.get(i).getGuaranteeMoney());
						lm.setJdNo(jbstatus.getData().toString());
						lm.setLength(glist.get(i).getLength());
						// lm.setmId(mId);
						lm.setOnDoorPickUp(glist.get(i).getOnDoorPickUp());
						lm.setPackageName(glist.get(i).getGoodsTitle());
						lm.setPaperFrom(paperFrom);

						SimpleDateFormat sdf = new SimpleDateFormat(("yyyy/MM/dd"));
						java.util.Date date = null;
						if (StringUtils.isNotEmpty(pickupDate)) {
							try {
								date = sdf.parse(pickupDate);
								lm.setPickupDate(new java.sql.Date(date.getTime()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

						if (StringUtils.isNotEmpty(predictDate)) {
							try {
								date = sdf.parse(predictDate);
								lm.setPredictDate(new java.sql.Date(date.getTime()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						lm.setProductSku(glist.get(i).getProductSku());
						lm.setReceiptFlag(receiptFlag);
						lm.setReceiveable(glist.get(i).getReceiveable());
						lm.setReceiverAddr(cityareaName + gm.getDetailAddr());
						lm.setReceiverMob(gm.getGainTelno());
						lm.setReceiverName(gm.getGainName());
						lm.setReceiverPhone(receiverPhone);
						lm.setRemark(gm.getCustMessage());
						lm.setRtnReceiverAddr(rtnReceiverAddr);
						lm.setRtnReceiverMob(rtnReceiverMob);
						lm.setRtnReceiverName(rtnReceiverName);
						lm.setRtnReceiverPhone(rtnReceiverPhone);
						lm.setSenderAddr(senderAddr);
						lm.setSenderMob(senderMob);
						lm.setSenderName(senderName);
						lm.setSenderPhone(senderPhone);
						lm.setSendTo(sendTo);
						lm.setWeight(glist.get(i).getWeight());
						lm.setWidth(glist.get(i).getWidth());
						logisticsMapper.addLogistics(lm);
						// 添加订单物流
						OrderDeliveryRelate odr = new OrderDeliveryRelate();
						odr.setId(UUIDUtil.getUUID());
						odr.setDeliveryId(waybillNo);
						odr.setDeliveryStatus(ConstantIF.DELIVERY_STATUS01);
						odr.setDeliveryType(ConstantIF.DELIVERY_TYPE01);
						odr.setExpressType(ConstantIF.EXPRESS_TYPE01);
						odr.setOrderId(orderid);
						orderClassMapper.addOrderDeliveryRelate(odr);
					} else {
						return jbstatus;
					}
				}

			}

		}
		// 获取订单版本号
		String version = orderClassMapper.getVersionInfo(orderid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", orderid);
		// 订单状态
		if (StringUtils.isNotEmpty(orderstatus)) {
			params.put("orderstatus", orderstatus);
		} else {
			params.put("orderstatus", null);
		}
		params.put("userid", userid);
		params.put("version", version);
		orderClassMapper.updateBgOrderPriceOrMessage(params);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: updataRecoverOrder</p> <p>Description: 恢复订单</p>
	 * 
	 * @param userid 用户ID
	 * 
	 * @param orderid 订单ID
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#updataRecoverOrder(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ResultBean updataRecoverOrder(String userid, String orderid) {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("orderid", orderid);
		// 恢复订单操作
		orderClassMapper.updataRecoverOrder(param);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: delLwbMain</p> <p>Description: </p>
	 * 
	 * @param orderid
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#delLwbMain(java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean delLwbMain(String orderid) {
		// TODO Auto-generated method stub
		// 删除物流信息
		orderClassMapper.delDelivery(orderid);
		// 删除订单物流关系信息
		orderClassMapper.delOrderDeliveryRelate(orderid);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: getDeliveryId</p> <p>Description: </p>
	 * 
	 * @param orderid
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#getDeliveryId(java.lang.String)
	 */
	@Override
	public List<String> getDeliveryId(String orderid, String deliverytype) {
		// TODO Auto-generated method stub
		List<String> deliveryNo = orderClassMapper.getDeliveryIds(orderid, deliverytype);
		return deliveryNo;
	}

	/*
	 * (non-Javadoc) <p>Title: addReturnGoods</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @param reason
	 * 
	 * @param price
	 * 
	 * @param instructions
	 * 
	 * @param credentials link: @see
	 * dianfan.service.order.OrderClassService#addReturnGoods(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public void addReturnGoods(String userid, String orderid, String reason, String price, String instructions,
			String credentials) {
		// TODO Auto-generated method stub
		AfterSale as = new AfterSale();
		as.setCreateBy(userid);
		as.setId(UUIDUtil.getUUID());
		as.setOrderId(orderid);
		as.setReason(reason);
		as.setRemark(instructions);
		as.setHandleStatus(ConstantIF.HANDLE_STATUS02);
		as.setResult(ConstantIF.RESULT01);
		as.setPicUrls(credentials);
		Order o = new Order();
		o.setOrderid(orderid);
		o.setOrderStatus(ConstantIF.ORDER_STATUS41);
		// 修改订单状态
		orderClassMapper.updateOrderRefundStatus(o);
		orderClassMapper.addAfterSale(as);
	}

	/*
	 * (non-Javadoc) <p>Title: updateReturnGoodsApprove</p> <p>Description:
	 * 审批通过：客户发货、审批不通过：告诉失败的原因</p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @param result
	 * 
	 * @param pickupDate
	 * 
	 * @param resultfreason
	 * 
	 * @param predictDate
	 * 
	 * @param sendTo
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#updateReturnGoodsApprove(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean updateReturnGoodsApprove(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String senderName, String senderMob, String senderPhone,
			String senderAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo) {
		// TODO Auto-generated method stub
		AfterSale as = new AfterSale();
		as.setId(afterSaleId);
		as.setResult(result);
		as.setOrderId(orderid);
		as.setUpdateBy(userid);
		Order o = new Order();
		o.setOrderid(orderid);
		if (ConstantIF.RESULT03.equals(result)) {
			// 获取物流id
			List<String> deliveryid = orderClassMapper.getConfirmDeliveryId(orderid);
			for (int i = 0; i < deliveryid.size(); i++) {
				LogisticsModel lm = logisticsMapper.getLogisticsInfo(deliveryid.get(i));
				// 调取京东物流
				EclpCoTransportLasWayBillRequest data = new EclpCoTransportLasWayBillRequest();
				// 生成运单号
				String waybillNo = GenRandomNumUtil.getOrderNo();
				// 添加退货物流id
				as.setDeliveryId(waybillNo);
				System.out.println("+++++++++++++++++++++" + waybillNo + "++++++++++++++++++++++++++");
				data.setOrderNo(waybillNo);
				data.setSenderName(senderName);
				data.setSenderMobile(senderMob);
				data.setSenderPhone(senderPhone);
				data.setSenderAddress(senderAddr);
				data.setReceiverName(lm.getSenderName());
				data.setReceiverMobile(lm.getSenderMob());
				data.setReceiverPhone(lm.getReceiverPhone());
				data.setReceiverAddress(lm.getSenderAddr());
				data.setRemark(lm.getRemark());
				data.setIsFragile(lm.getIsFragile());
				data.setSenderTc(sendTo);
				data.setPredictDate(predictDate);
				data.setIsJDOrder("2");
				data.setIsCod(lm.getIsCod());
				data.setReceiveable(lm.getReceiveable());
				data.setOnDoorPickUp(lm.getOnDoorPickUp());
				data.setPickUpDate(pickupDate);
				data.setIsGuarantee(lm.getIsGuarantee());
				data.setGuaranteeValue(lm.getGuaranteeMoney());
				data.setReceiptFlag(receiptFlag);
				data.setPaperFrom(paperFrom);
				data.setRtnReceiverName(rtnReceiverName);
				data.setRtnReceiverMobile(rtnReceiverMob);
				data.setRtnReceiverAddress(rtnReceiverAddr);
				data.setRtnReceiverPhone(rtnReceiverPhone);
				data.setWeight(lm.getWeight());
				data.setLength(lm.getLength());
				data.setWidth(lm.getWidth());
				data.setHeight(lm.getHeight());
				data.setInstallFlag(lm.getInstallFlag());
				data.setThirdCategoryNo(lm.getThridCategoryNo());
				data.setBrandNo(lm.getBrandNo());
				data.setProductSku(lm.getProductSku());
				data.setPackageName(lm.getPackageName());
				ResultBean jbstatus = jdTransportService.transportLasWayBill(data);
				if ("200".equals(jbstatus.getCode())) {
					LogisticsModel lms = new LogisticsModel();
					lms.setId(waybillNo);
					lms.setBrandNo(lm.getBrandNo());
					lms.setCreateBy(userid);
					lms.setDeliveryNo(GenRandomNumUtil.getOrderNo());
					lms.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
					// lm.setExpressNo(expressNo);
					lms.setHeight(lm.getHeight());
					lms.setInstallFlag(lm.getInstallFlag());
					lms.setThridCategoryNo(lm.getThridCategoryNo());
					lms.setIsCod(lm.getIsCod());
					lms.setIsFragile(lm.getIsFragile());
					lms.setIsGuarantee(lm.getIsGuarantee());
					lms.setGuaranteeMoney(lm.getGuaranteeMoney());
					lms.setJdNo(jbstatus.getData().toString());
					lms.setLength(lm.getLength());
					// lm.setmId(mId);
					lms.setOnDoorPickUp(lm.getOnDoorPickUp());
					lms.setPackageName(lm.getPackageName());

					SimpleDateFormat sdf = new SimpleDateFormat(("yyyy/MM/dd"));
					java.util.Date date = null;
					if (StringUtils.isNotEmpty(pickupDate)) {
						try {
							date = sdf.parse(pickupDate);
							lms.setPickupDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					if (StringUtils.isNotEmpty(predictDate)) {
						try {
							date = sdf.parse(predictDate);
							lms.setPredictDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					lms.setProductSku(lm.getProductSku());
					lms.setReceiptFlag(receiptFlag);
					lms.setReceiveable(lm.getReceiveable());
					lms.setReceiverAddr(lm.getSenderAddr());
					lms.setReceiverMob(lm.getReceiverMob());
					lms.setReceiverName(lm.getReceiverName());
					lms.setReceiverPhone(lm.getReceiverPhone());
					lms.setRemark(lm.getRemark());
					lms.setPaperFrom(paperFrom);
					lms.setRtnReceiverAddr(rtnReceiverAddr);
					lms.setRtnReceiverMob(rtnReceiverMob);
					lms.setRtnReceiverName(rtnReceiverName);
					lms.setRtnReceiverPhone(rtnReceiverPhone);
					lms.setSenderAddr(senderAddr);
					lms.setSenderMob(senderMob);
					lms.setSenderName(senderName);
					lms.setSenderPhone(senderPhone);
					lms.setSendTo(sendTo);
					lms.setWeight(lm.getWeight());
					lms.setWidth(lm.getWidth());
					logisticsMapper.addLogistics(lms);
					// 添加订单物流
					OrderDeliveryRelate odr = new OrderDeliveryRelate();
					odr.setId(UUIDUtil.getUUID());
					odr.setDeliveryId(waybillNo);
					odr.setDeliveryStatus(ConstantIF.DELIVERY_STATUS01);
					odr.setDeliveryType(ConstantIF.DELIVERY_TYPE02);
					odr.setExpressType(ConstantIF.EXPRESS_TYPE01);
					odr.setOrderId(orderid);
					orderClassMapper.addOrderDeliveryRelate(odr);
				} else {
					return jbstatus;
				}
			}
			o.setOrderStatus(ConstantIF.ORDER_STATUS42);
		} else if (ConstantIF.RESULT02.equals(result)) {
			o.setOrderStatus(ConstantIF.ORDER_STATUS06);
			// 拒绝的原因
			as.setResultFReason(resultfreason);
		}
		// 修改订单状态
		orderClassMapper.updateOrderRefundStatus(o);
		orderClassMapper.updateAfterSale(as);
		return new ResultBean();
	}

	@Override
	@Transactional
	public ResultBean updateBarterGoodsApprove(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String senderName, String senderMob, String senderPhone,
			String senderAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo) {
		// TODO Auto-generated method stub
		AfterSale as = new AfterSale();
		as.setId(afterSaleId);
		as.setResult(result);
		as.setOrderId(orderid);
		as.setUpdateBy(userid);
		Order o = new Order();
		o.setOrderid(orderid);
		if (ConstantIF.RESULT03.equals(result)) {
			// 获取物流id
			List<String> deliveryid = orderClassMapper.getConfirmDeliveryId(orderid);
			for (int i = 0; i < deliveryid.size(); i++) {
				LogisticsModel lm = logisticsMapper.getLogisticsInfo(deliveryid.get(i));
				// 调取京东物流
				EclpCoTransportLasWayBillRequest data = new EclpCoTransportLasWayBillRequest();
				// 生成运单号
				String waybillNo = GenRandomNumUtil.getOrderNo();
				// 添加退货物流id
				as.setDeliveryId(waybillNo);
				System.out.println("+++++++++++++++++++++" + waybillNo + "++++++++++++++++++++++++++");
				data.setOrderNo(waybillNo);
				data.setSenderName(senderName);
				data.setSenderMobile(senderMob);
				data.setSenderPhone(senderPhone);
				data.setSenderAddress(senderAddr);
				data.setReceiverName(lm.getSenderName());
				data.setReceiverMobile(lm.getSenderMob());
				data.setReceiverPhone(lm.getReceiverPhone());
				data.setReceiverAddress(lm.getSenderAddr());
				data.setRemark(lm.getRemark());
				data.setIsFragile(lm.getIsFragile());
				data.setSenderTc(sendTo);
				data.setPredictDate(predictDate);
				data.setIsJDOrder("2");
				data.setIsCod(lm.getIsCod());
				data.setReceiveable(lm.getReceiveable());
				data.setOnDoorPickUp(lm.getOnDoorPickUp());
				data.setPickUpDate(pickupDate);
				data.setIsGuarantee(lm.getIsGuarantee());
				data.setGuaranteeValue(lm.getGuaranteeMoney());
				data.setReceiptFlag(receiptFlag);
				data.setPaperFrom(paperFrom);
				data.setRtnReceiverName(rtnReceiverName);
				data.setRtnReceiverMobile(rtnReceiverMob);
				data.setRtnReceiverAddress(rtnReceiverAddr);
				data.setRtnReceiverPhone(rtnReceiverPhone);
				data.setWeight(lm.getWeight());
				data.setLength(lm.getLength());
				data.setWidth(lm.getWidth());
				data.setHeight(lm.getHeight());
				data.setInstallFlag(lm.getInstallFlag());
				data.setThirdCategoryNo(lm.getThridCategoryNo());
				data.setBrandNo(lm.getBrandNo());
				data.setProductSku(lm.getProductSku());
				data.setPackageName(lm.getPackageName());
				ResultBean jbstatus = jdTransportService.transportLasWayBill(data);
				if ("200".equals(jbstatus.getCode())) {
					LogisticsModel lms = new LogisticsModel();
					lms.setId(waybillNo);
					lms.setBrandNo(lm.getBrandNo());
					lms.setCreateBy(userid);
					lms.setDeliveryNo(GenRandomNumUtil.getOrderNo());
					lms.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
					// lm.setExpressNo(expressNo);
					lms.setHeight(lm.getHeight());
					lms.setInstallFlag(lm.getInstallFlag());
					lms.setThridCategoryNo(lm.getThridCategoryNo());
					lms.setIsCod(lm.getIsCod());
					lms.setIsFragile(lm.getIsFragile());
					lms.setIsGuarantee(lm.getIsGuarantee());
					lms.setGuaranteeMoney(lm.getGuaranteeMoney());
					lms.setJdNo(jbstatus.getData().toString());
					lms.setLength(lm.getLength());
					// lm.setmId(mId);
					lms.setOnDoorPickUp(lm.getOnDoorPickUp());
					lms.setPackageName(lm.getPackageName());

					SimpleDateFormat sdf = new SimpleDateFormat(("yyyy/MM/dd"));
					java.util.Date date = null;
					if (StringUtils.isNotEmpty(pickupDate)) {
						try {
							date = sdf.parse(pickupDate);
							lms.setPickupDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					if (StringUtils.isNotEmpty(predictDate)) {
						try {
							date = sdf.parse(predictDate);
							lms.setPredictDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					lms.setProductSku(lm.getProductSku());
					lms.setReceiptFlag(receiptFlag);
					lms.setReceiveable(lm.getReceiveable());
					lms.setReceiverAddr(lm.getSenderAddr());
					lms.setReceiverMob(lm.getReceiverMob());
					lms.setReceiverName(lm.getReceiverName());
					lms.setReceiverPhone(lm.getReceiverPhone());
					lms.setRemark(lm.getRemark());
					lms.setPaperFrom(paperFrom);
					lms.setRtnReceiverAddr(rtnReceiverAddr);
					lms.setRtnReceiverMob(rtnReceiverMob);
					lms.setRtnReceiverName(rtnReceiverName);
					lms.setRtnReceiverPhone(rtnReceiverPhone);
					lms.setSenderAddr(senderAddr);
					lms.setSenderMob(senderMob);
					lms.setSenderName(senderName);
					lms.setSenderPhone(senderPhone);
					lms.setSendTo(sendTo);
					lms.setWeight(lm.getWeight());
					lms.setWidth(lm.getWidth());
					logisticsMapper.addLogistics(lms);
					// 添加订单物流
					OrderDeliveryRelate odr = new OrderDeliveryRelate();
					odr.setId(UUIDUtil.getUUID());
					odr.setDeliveryId(waybillNo);
					odr.setDeliveryStatus(ConstantIF.DELIVERY_STATUS01);
					odr.setDeliveryType(ConstantIF.DELIVERY_TYPE02);
					odr.setExpressType(ConstantIF.EXPRESS_TYPE01);
					odr.setOrderId(orderid);
					orderClassMapper.addOrderDeliveryRelate(odr);
				} else {
					return jbstatus;
				}
			}
			o.setOrderStatus(ConstantIF.ORDER_STATUS42);
		} else if (ConstantIF.RESULT02.equals(result)) {
			o.setOrderStatus(ConstantIF.ORDER_STATUS06);
			// 拒绝的原因
			as.setResultFReason(resultfreason);
		}
		// 修改订单状态
		orderClassMapper.updateOrderRefundStatus(o);
		orderClassMapper.updateAfterSale(as);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: updateReturnGoodsApproveResult 审批结果</p>
	 * <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @param result
	 * 
	 * @param pickupDate
	 * 
	 * @param resultfreason
	 * 
	 * @param predictDate
	 * 
	 * @param sendTo
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#updateReturnGoodsApproveResult(java.
	 * lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean updateReturnGoodsApproveResult(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String receiverName, String receiverMob, String receiverPhone,
			String receiverAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo) {
		AfterSale as = new AfterSale();
		as.setId(afterSaleId);
		as.setResult(result);
		as.setOrderId(orderid);
		if (ConstantIF.RESULT21.equals(result)) {
			// 获取物流id
			List<String> deliveryid = orderClassMapper.getReturnGoodsDeliveryId(orderid);
			for (int i = 0; i < deliveryid.size(); i++) {
				LogisticsModel lm = logisticsMapper.getLogisticsInfo(deliveryid.get(i));
				// 调取京东物流
				EclpCoTransportLasWayBillRequest data = new EclpCoTransportLasWayBillRequest();
				// 生成运单号
				String waybillNo = GenRandomNumUtil.getOrderNo();
				// 添加退货物流id
				as.setDeliveryId(waybillNo);
				System.out.println("+++++++++++++++++++++" + waybillNo + "++++++++++++++++++++++++++");
				data.setOrderNo(waybillNo);
				data.setSenderName(lm.getReceiverName());
				data.setSenderMobile(lm.getReceiverMob());
				data.setSenderPhone(lm.getReceiverPhone());
				data.setSenderAddress(lm.getReceiverAddr());
				data.setReceiverName(receiverName);
				data.setReceiverMobile(receiverMob);
				data.setReceiverPhone(receiverPhone);
				data.setReceiverAddress(receiverAddr);
				data.setRemark(lm.getRemark());
				data.setIsFragile(lm.getIsFragile());
				data.setSenderTc(sendTo);
				data.setPredictDate(predictDate);
				data.setIsJDOrder("2");
				data.setIsCod(lm.getIsCod());
				data.setReceiveable(lm.getReceiveable());
				data.setOnDoorPickUp(lm.getOnDoorPickUp());
				data.setPickUpDate(pickupDate);
				data.setIsGuarantee(lm.getIsGuarantee());
				data.setGuaranteeValue(lm.getGuaranteeMoney());
				data.setReceiptFlag(receiptFlag);
				data.setPaperFrom(paperFrom);
				data.setRtnReceiverName(rtnReceiverName);
				data.setRtnReceiverMobile(rtnReceiverMob);
				data.setRtnReceiverAddress(rtnReceiverAddr);
				data.setRtnReceiverPhone(rtnReceiverPhone);
				data.setWeight(lm.getWeight());
				data.setLength(lm.getLength());
				data.setWidth(lm.getWidth());
				data.setHeight(lm.getHeight());
				data.setInstallFlag(lm.getInstallFlag());
				data.setThirdCategoryNo(lm.getThridCategoryNo());
				data.setBrandNo(lm.getBrandNo());
				data.setProductSku(lm.getProductSku());
				data.setPackageName(lm.getPackageName());
				ResultBean jbstatus = jdTransportService.transportLasWayBill(data);
				if ("200".equals(jbstatus.getCode())) {
					LogisticsModel lms = new LogisticsModel();
					lms.setId(waybillNo);
					lms.setBrandNo(lm.getBrandNo());
					lms.setCreateBy(userid);
					lms.setDeliveryNo(GenRandomNumUtil.getOrderNo());
					lms.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
					// lm.setExpressNo(expressNo);
					lms.setHeight(lm.getHeight());
					lms.setInstallFlag(lm.getInstallFlag());
					lms.setThridCategoryNo(lm.getThridCategoryNo());
					lms.setIsCod(lm.getIsCod());
					lms.setIsFragile(lm.getIsFragile());
					lms.setIsGuarantee(lm.getIsGuarantee());
					lms.setGuaranteeMoney(lm.getGuaranteeMoney());
					lms.setJdNo(jbstatus.getData().toString());
					lms.setLength(lm.getLength());
					// lm.setmId(mId);
					lms.setOnDoorPickUp(lm.getOnDoorPickUp());
					lms.setPackageName(lm.getPackageName());

					SimpleDateFormat sdf = new SimpleDateFormat(("yyyy/MM/dd"));
					java.util.Date date = null;
					if (StringUtils.isNotEmpty(pickupDate)) {
						try {
							date = sdf.parse(pickupDate);
							lms.setPickupDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					if (StringUtils.isNotEmpty(predictDate)) {
						try {
							date = sdf.parse(predictDate);
							lms.setPredictDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					lms.setProductSku(lm.getProductSku());
					lms.setReceiptFlag(receiptFlag);
					lms.setReceiveable(lm.getReceiveable());
					lms.setReceiverAddr(receiverAddr);
					lms.setReceiverMob(receiverMob);
					lms.setReceiverName(receiverName);
					lms.setReceiverPhone(receiverPhone);
					lms.setRemark(lm.getRemark());
					lms.setPaperFrom(paperFrom);
					lms.setRtnReceiverAddr(rtnReceiverAddr);
					lms.setRtnReceiverMob(rtnReceiverMob);
					lms.setRtnReceiverName(rtnReceiverName);
					lms.setRtnReceiverPhone(rtnReceiverPhone);
					lms.setSenderAddr(lm.getReceiverAddr());
					lms.setSenderMob(lm.getReceiverMob());
					lms.setSenderName(lm.getReceiverName());
					lms.setSenderPhone(lm.getReceiverPhone());
					lms.setSendTo(sendTo);
					lms.setWeight(lm.getWeight());
					lms.setWidth(lm.getWidth());
					logisticsMapper.addLogistics(lms);
					// 添加订单物流
					OrderDeliveryRelate odr = new OrderDeliveryRelate();
					odr.setId(UUIDUtil.getUUID());
					odr.setDeliveryId(waybillNo);
					odr.setDeliveryStatus(ConstantIF.DELIVERY_STATUS01);
					odr.setDeliveryType(ConstantIF.DELIVERY_TYPE03);
					odr.setExpressType(ConstantIF.EXPRESS_TYPE01);
					odr.setOrderId(orderid);
					orderClassMapper.addOrderDeliveryRelate(odr);
					// 拒绝的原因
					as.setResultFReason(resultfreason);
					orderClassMapper.updateAfterSale(as);
				} else {
					return jbstatus;
				}
			}
		} else if (ConstantIF.RESULT41.equals(result)) {
			List<String> orderids = new ArrayList<>();
			orderids.add(orderid);
			String orderStatus = orderClassMapper.getRefundOrdersStatus(orderid);
			if (ConstantIF.PAY_STATUS06.equals(orderStatus)) {
				return new ResultBean();
			}
			afterSaleOrderRefund(orderids);

			if (ConstantIF.PAY_STATUS07.equals(orderStatus)) {
				return new ResultBean("2010", ResultBgMsg.C_2010);
			}
			// orderClassMapper.updateAfterSale(as);
		}

		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: addBarterGoods</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @param reason
	 * 
	 * @param instructions
	 * 
	 * @param credentials link: @see
	 * dianfan.service.order.OrderClassService#addBarterGoods(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void addBarterGoods(String userid, String orderid, String reason, String instructions, String credentials) {
		// TODO Auto-generated method stub
		AfterSale as = new AfterSale();
		as.setCreateBy(userid);
		as.setId(UUIDUtil.getUUID());
		as.setOrderId(orderid);
		as.setReason(reason);
		as.setRemark(instructions);
		as.setHandleStatus(ConstantIF.HANDLE_STATUS01);
		as.setResult(ConstantIF.RESULT01);
		as.setPicUrls(credentials);
		Order o = new Order();
		o.setOrderid(orderid);
		o.setOrderStatus(ConstantIF.ORDER_STATUS41);
		// 修改订单状态
		orderClassMapper.updateOrderRefundStatus(o);
		orderClassMapper.addAfterSale(as);

	}

	/*
	 * (non-Javadoc) <p>Title: updateBarterGoodsApproveResult</p> <p>Description:
	 * </p>
	 * 
	 * @param userid
	 * 
	 * @param orderid
	 * 
	 * @param result
	 * 
	 * @param pickupDate
	 * 
	 * @param resultfreason
	 * 
	 * @param predictDate
	 * 
	 * @param receiverName
	 * 
	 * @param receiverMob
	 * 
	 * @param receiverPhone
	 * 
	 * @param receiverAddr
	 * 
	 * @param receiptFlag
	 * 
	 * @param rtnReceiverName
	 * 
	 * @param rtnReceiverMob
	 * 
	 * @param rtnReceiverAddr
	 * 
	 * @param rtnReceiverPhone
	 * 
	 * @param paperFrom
	 * 
	 * @param sendTo
	 * 
	 * @return link: @see
	 * dianfan.service.order.OrderClassService#updateBarterGoodsApproveResult(java.
	 * lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ResultBean updateBarterGoodsApproveResult(String userid, String orderid, String result, String pickupDate,
			String resultfreason, String predictDate, String receiverName, String receiverMob, String receiverPhone,
			String receiverAddr, String receiptFlag, String rtnReceiverName, String rtnReceiverMob,
			String rtnReceiverAddr, String rtnReceiverPhone, String paperFrom, String afterSaleId, String sendTo) {
		// TODO Auto-generated method stub
		AfterSale as = new AfterSale();
		as.setId(afterSaleId);
		as.setResult(result);
		as.setOrderId(orderid);
		if (ConstantIF.RESULT61.equals(result) || ConstantIF.RESULT81.equals(result)) {
			// 获取物流id
			List<String> deliveryid = orderClassMapper.getReturnGoodsDeliveryId(orderid);

			for (int i = 0; i < deliveryid.size(); i++) {
				LogisticsModel lm = logisticsMapper.getLogisticsInfo(deliveryid.get(i));
				// 调取京东物流
				EclpCoTransportLasWayBillRequest data = new EclpCoTransportLasWayBillRequest();
				// 生成运单号
				String waybillNo = GenRandomNumUtil.getOrderNo();
				// 添加退货物流id
				as.setDeliveryId(waybillNo);
				System.out.println("+++++++++++++++++++++" + waybillNo + "++++++++++++++++++++++++++");
				data.setOrderNo(waybillNo);
				data.setSenderName(lm.getReceiverName());
				data.setSenderMobile(lm.getReceiverMob());
				data.setSenderPhone(lm.getReceiverPhone());
				data.setSenderAddress(lm.getReceiverAddr());
				data.setReceiverName(receiverName);
				data.setReceiverMobile(receiverMob);
				data.setReceiverPhone(receiverPhone);
				data.setReceiverAddress(receiverAddr);
				data.setRemark(lm.getRemark());
				data.setIsFragile(lm.getIsFragile());
				data.setSenderTc(sendTo);
				data.setPredictDate(predictDate);
				data.setIsJDOrder("2");
				data.setIsCod(lm.getIsCod());
				data.setReceiveable(lm.getReceiveable());
				data.setOnDoorPickUp(lm.getOnDoorPickUp());
				data.setPickUpDate(pickupDate);
				data.setIsGuarantee(lm.getIsGuarantee());
				data.setGuaranteeValue(lm.getGuaranteeMoney());
				data.setReceiptFlag(receiptFlag);
				data.setPaperFrom(paperFrom);
				data.setRtnReceiverName(rtnReceiverName);
				data.setRtnReceiverMobile(rtnReceiverMob);
				data.setRtnReceiverAddress(rtnReceiverAddr);
				data.setRtnReceiverPhone(rtnReceiverPhone);
				data.setWeight(lm.getWeight());
				data.setLength(lm.getLength());
				data.setWidth(lm.getWidth());
				data.setHeight(lm.getHeight());
				data.setInstallFlag(lm.getInstallFlag());
				data.setThirdCategoryNo(lm.getThridCategoryNo());
				data.setBrandNo(lm.getBrandNo());
				data.setProductSku(lm.getProductSku());
				data.setPackageName(lm.getPackageName());
				ResultBean jbstatus = jdTransportService.transportLasWayBill(data);
				if ("200".equals(jbstatus.getCode())) {
					LogisticsModel lms = new LogisticsModel();
					lms.setId(waybillNo);
					lms.setBrandNo(lm.getBrandNo());
					lms.setCreateBy(userid);
					lms.setDeliveryNo(GenRandomNumUtil.getOrderNo());
					lms.setDeptNo(PropertyUtil.getProperty("jd.api.deptNo"));
					// lm.setExpressNo(expressNo);
					lms.setHeight(lm.getHeight());
					lms.setInstallFlag(lm.getInstallFlag());
					lms.setThridCategoryNo(lm.getThridCategoryNo());
					lms.setIsCod(lm.getIsCod());
					lms.setIsFragile(lm.getIsFragile());
					lms.setIsGuarantee(lm.getIsGuarantee());
					lms.setGuaranteeMoney(lm.getGuaranteeMoney());
					lms.setJdNo(jbstatus.getData().toString());
					lms.setLength(lm.getLength());
					// lm.setmId(mId);
					lms.setOnDoorPickUp(lm.getOnDoorPickUp());
					lms.setPackageName(lm.getPackageName());

					SimpleDateFormat sdf = new SimpleDateFormat(("yyyy/MM/dd"));
					java.util.Date date = null;
					if (StringUtils.isNotEmpty(pickupDate)) {
						try {
							date = sdf.parse(pickupDate);
							lms.setPickupDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					if (StringUtils.isNotEmpty(predictDate)) {
						try {
							date = sdf.parse(predictDate);
							lms.setPredictDate(new java.sql.Date(date.getTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					lms.setProductSku(lm.getProductSku());
					lms.setReceiptFlag(receiptFlag);
					lms.setReceiveable(lm.getReceiveable());
					lms.setReceiverAddr(receiverAddr);
					lms.setReceiverMob(receiverMob);
					lms.setReceiverName(receiverName);
					lms.setReceiverPhone(receiverPhone);
					lms.setRemark(lm.getRemark());
					lms.setPaperFrom(paperFrom);
					lms.setRtnReceiverAddr(rtnReceiverAddr);
					lms.setRtnReceiverMob(rtnReceiverMob);
					lms.setRtnReceiverName(rtnReceiverName);
					lms.setRtnReceiverPhone(rtnReceiverPhone);
					lms.setSenderAddr(lm.getReceiverAddr());
					lms.setSenderMob(lm.getReceiverMob());
					lms.setSenderName(lm.getReceiverName());
					lms.setSenderPhone(lm.getReceiverPhone());
					lms.setSendTo(sendTo);
					lms.setWeight(lm.getWeight());
					lms.setWidth(lm.getWidth());
					logisticsMapper.addLogistics(lms);
					// 添加订单物流
					OrderDeliveryRelate odr = new OrderDeliveryRelate();
					odr.setId(UUIDUtil.getUUID());
					odr.setDeliveryId(waybillNo);
					odr.setDeliveryStatus(ConstantIF.DELIVERY_STATUS01);
					odr.setExpressType(ConstantIF.EXPRESS_TYPE01);
					if (ConstantIF.RESULT61.equals(result)) {
						odr.setDeliveryType(ConstantIF.DELIVERY_TYPE05);
						// 拒绝的原因
						as.setResultFReason(resultfreason);
					}
					if (ConstantIF.RESULT81.equals(result)) {
						odr.setDeliveryType(ConstantIF.DELIVERY_TYPE04);
					}
					odr.setOrderId(orderid);
					orderClassMapper.addOrderDeliveryRelate(odr);

				} else {
					return jbstatus;
				}
			}
		}
		orderClassMapper.updateAfterSale(as);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: closeOrder</p> <p>Description: 订单关闭</p>
	 * 
	 * @param operater
	 * 
	 * @param orderid
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.order.OrderClassService#closeOrder(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	@Override
	public ResultBean closeOrder(String operater, String orderid) {
		// 首先确认该订单的状态（非 （01-待付款）情况下，不可以关闭订单）
		Boolean boo = orderClassMapper.getOrderStateByOrderid(orderid);
		if (!boo) {
			// !当前订单不可以关闭
			return new ResultBean("4102", ResultApiMsg.C_4102);
		}
		// 关闭对应订单
		orderClassMapper.delOrderByOrderid(orderid, operater);
		// 成功返回
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dianfan.service.order.OrderClassService#returnCouponCloseOrders()
	 */
	@Override
	public void returnCouponCloseOrders() {
		// 查询返还主动关闭和被动关闭的非易拼订单,返还主动关闭的和被动关闭的易拼订单的优惠券
		// 创建入参容器
		// 返还未支付的订单的优惠券
		List<Order> ls = orderClassMapper.returnCouponCloseOrders();
		// 订单关闭成功将查询优惠券存在将更新返还
		for (int j = 0; j < ls.size(); j++) {
			if (!StringUtility.isNull(ls.get(j).getCouponRelateId())) {
				UserCouponRelate ucr = new UserCouponRelate();
				ucr.setUserId(ls.get(j).getUserid());
				ucr.setId(ls.get(j).getCouponRelateId());
				ucr.setUserUsed(0);
				couponMapper.updateResverCoupon(ucr);
				// 将订单优惠券relateid置空
				couponMapper.updateOrderReateIdClear(ls.get(j).getOrderid());
			}
		}
	}
}
