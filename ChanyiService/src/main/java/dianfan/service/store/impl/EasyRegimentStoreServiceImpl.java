package dianfan.service.store.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.base.AreaMapper;
import dianfan.dao.mapper.our.SupplierApplyMapper;
import dianfan.dao.mapper.store.EasyRegimentMapper;
import dianfan.dao.mapper.userManage.StaffMapper;
import dianfan.date.DateUtility;
import dianfan.entities.Coupon;
import dianfan.entities.ExperiencestoreApply;
import dianfan.entities.UserInfo;
import dianfan.entities.base.Area;
import dianfan.entities.goods.GoodsClassify;
import dianfan.entities.goods.GoodsModels;
import dianfan.entities.role.Role;
import dianfan.entities.store.ESSearchParam;
import dianfan.entities.store.ExperienceStore;
import dianfan.entities.store.Store;
import dianfan.map.tencent.GeocoderAttribute;
import dianfan.map.tencent.GeocoderRet;
import dianfan.map.tencent.TencentMapApi;
import dianfan.models.ResultBean;
import dianfan.service.store.EasyRegimentStoreService;
import dianfan.util.PropertyUtil;
import dianfan.util.StringUtility;
import dianfan.util.UUIDUtil;

/**
 * @ClassName EasyRegimentStoreServiceImpl
 * @Description 易团服务实现
 * @author cjy
 * @date 2018年7月6日 下午2:45:56
 */
@Service
public class EasyRegimentStoreServiceImpl implements EasyRegimentStoreService {
	@Autowired
	private EasyRegimentMapper easyRegimentMapper;
	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private SupplierApplyMapper supplierApplyMapper;

	/*
	 * (non-Javadoc) <p>Title: findEasyRegimentStore</p> <p>Description:
	 * 易团体验店列表(按搜索项)</p>
	 * 
	 * @param essp 搜索参数
	 * 
	 * @return link: @see
	 * dianfan.service.regiment.EasyRegimentService#findEasyRegimentStore(dianfan.
	 * entities.regiment.ESSearchParam)
	 */
	@Override
	public ResultBean findEasyRegimentStore(ESSearchParam essp) {

		// 检测是否指定筛选区县级下的体验店
		if (StringUtils.isEmpty(essp.getAreaCode())) {
			// 默认搜索定位市区下的全部区县
			// 地区列表
			List<Area> areas = null;
			List<String> areaCodes = new ArrayList<>();
			// 根据传入的经纬度获取所在市
			TencentMapApi tma = new TencentMapApi();
			GeocoderRet ret = tma.geocoder(essp.getLongitude(), essp.getLatitude());
			if (ret.getStatus() != 0) {
				// 未定位成功，使用默认城市code
				String addr_code = ConstantIF.DEFAULT_STREO_CODE;
				// 根据市级code获取子地区列表
				areas = areaMapper.findChiledCityBySubCityCode(addr_code);
			} else {
				// 定位成功,使用市级名称获取下面所有区县列表
				areas = areaMapper.findChiledCityBySubCityName(ret.getCity());
			}
			for (Area a : areas) {
				areaCodes.add(a.getAreaCode());
			}
			essp.setAllAreaCode(areaCodes);
		} else {
			if (StringUtils.lastIndexOf(essp.getAreaCode(), "00") != -1) {
				// 市级code
				// 根据市级code获取子地区列表
				List<Area> areas = areaMapper.findChiledCityBySubCityCode(essp.getAreaCode());
				List<String> areaCodes = new ArrayList<>();
				for (Area a : areas) {
					areaCodes.add(a.getAreaCode());
				}
				essp.setAllAreaCode(areaCodes);
				essp.setAreaCode(null);
			}
		}
		Map<String, Object> ret = new HashMap<>();
		// 按条件获取体验店列表数量
		int count = easyRegimentMapper.findEasyRegimentStoreCount(essp);
		ret.put("count", count);
		if (count <= (essp.getPage() - 1) * essp.getPagesize()) {
			// 未筛选到数据
			ret.put("stores", new ArrayList<>());
			return new ResultBean(ret);
		}

		// 设置起始条数位置
		if (essp.getMapShow() == 0)
			essp.setPagestart((essp.getPage() - 1) * essp.getPagesize());
		// 按条件获取体验店列表
		List<ExperienceStore> stores = easyRegimentMapper.findEasyRegimentStore(essp);

		for (ExperienceStore es : stores) {
			es.setStorePic(PropertyUtil.getProperty("domain") + es.getStorePic());
			// 判断体验店下面是否存在优惠券，有就标1，没有就标0
			int flag = easyRegimentMapper.getCheckExperienceStoreVoucher(es.getStoreid());
			if (flag != 0) {
				// 说明有优惠券，将优惠券的类型返回给前端
				List<String> coupon = easyRegimentMapper.findVouchers(es.getStoreid());
				// 创建一个新集合
				ArrayList<String> newList = new ArrayList<String>();
				// 遍历旧集合 ，去除旧集合中的重复字段
				Iterator it = coupon.iterator();
				while (it.hasNext()) {
					String s = (String) it.next();
					if (!newList.contains(s)) {
						newList.add(s);
					}
				}
				String type = String.join(",", newList);
				es.setVoucher(type);
			}
		}
		ret.put("stores", stores);
		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: findStoreFiltrate</p> <p>Description: 体验店筛选项</p>
	 * 
	 * @param cityCode 市级code
	 * 
	 * @return link: @see
	 * dianfan.service.store.EasyRegimentStoreService#findStoreFiltrate(java.lang.
	 * String)
	 */
	@Override
	public ResultBean findStoreFiltrate(String longitude, String latitude, String cityCode) {
		Area area = new Area();
		if (StringUtils.isNotEmpty(cityCode)) {
			// 根据城市code获取城市信息
			area.setAreaCode(cityCode);
			area = areaMapper.findCityInfo(area);
			// 根据市级code获取子地区列表
			List<Area> areas = areaMapper.findChiledCityBySubCityCode(cityCode);
			area.setLowerCity(areas);
		} else {
			TencentMapApi tma = new TencentMapApi();
			GeocoderRet gret = tma.geocoder(longitude, latitude);
			if (gret.getStatus() != 0) {
				// 未定位成功，使用默认城市code
				String addr_code = ConstantIF.DEFAULT_STREO_CODE;
				// 根据城市code获取城市信息
				area.setAreaCode(addr_code);
				area = areaMapper.findCityInfo(area);
				// 根据市级code获取子地区列表
				List<Area> areas = areaMapper.findChiledCityBySubCityCode(addr_code);
				area.setLowerCity(areas);
			} else {
				// 根据城市code获取城市信息
				area.setAreaName(gret.getCity());
				area = areaMapper.findCityInfo(area);
				// 定位成功,使用市级名称获取下面所有区县列表
				List<Area> areas = areaMapper.findChiledCityBySubCityName(gret.getCity());
				area.setLowerCity(areas);
			}
		}

		// 获取商品分类（顶级分类）
		List<GoodsClassify> classify = easyRegimentMapper.findGoodsClassifyByLevel(0);

		Map<String, Object> ret = new HashMap<>();
		ret.put("areaCode", area);
		ret.put("classify", classify);

		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: findStoreList</p> <p>Description: 获取体验店列表</p>
	 * 
	 * @return link: @see
	 * dianfan.service.store.EasyRegimentStoreService#findStoreList()
	 */
	@Override
	@SystemServiceLog(method = "findStoreList", description = "获取体验店列表")
	public ResultBean findStoreList(int page, int pageSize, Store store) {
		// 处理城市code
		List<String> cityCodes = new ArrayList<>();
		if (store.getCityCode() != null) {
			if (store.getCityCode().lastIndexOf("0000") != -1) {
				// 省级code
				cityCodes = areaMapper.findCityCodeByProvinceCode(store.getCityCode());
			} else if (store.getCityCode().lastIndexOf("00") != -1) {
				// 市级code
				cityCodes = areaMapper.findCityCodeByCityCode(store.getCityCode());
			} else {
				// 区县code
				cityCodes.add(store.getCityCode());
			}
		}

		Map<String, Object> ret = new HashMap<>();

		int count = easyRegimentMapper.getStoreCount(store, cityCodes);
		ret.put("total", count);

		if (count <= (page - 1) * pageSize) {
			ret.put("store", new ArrayList<>());
			return new ResultBean(ret);
		}

		List<Store> storeList = easyRegimentMapper.getStoreList((page - 1) * pageSize, pageSize, store, cityCodes);
		// 处理图片链接
		String domain = PropertyUtil.getProperty("domain");
		for (Store st : storeList) {
			if (st.getDoorheadUrl() != null && StringUtils.isNotEmpty(st.getDoorheadUrl().trim()))
				st.setDoorheadUrl(domain + st.getDoorheadUrl());
			st.setBusinessTimeStart(
					StringUtils.isNotEmpty(st.getBusinessTimeStart()) ? st.getBusinessTimeStart().substring(0, 5)
							: null);
			st.setBusinessTimeEnd(
					StringUtils.isNotEmpty(st.getBusinessTimeEnd()) ? st.getBusinessTimeEnd().substring(0, 5) : null);

			// 根据地区code获取省市区
			if (!StringUtils.equals(st.getStatus(), "03") && StringUtils.isNotEmpty(st.getCityCode())) {
				String addr = areaMapper.getAddrByCode(st.getCityCode(), 3);
				st.setCityCodeAddr(addr);
			}
		}

		ret.put("store", storeList);
		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: getStoreDetail</p> <p>Description: 体验店详情</p>
	 * 
	 * @param storeid 体验店id
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.store.EasyRegimentStoreService#getStoreDetail(java.lang.
	 * String)
	 */
	@Override
	@SystemServiceLog(method = "getStoreDetail", description = "体验店详情")
	public ResultBean getStoreDetail(String storeid) {
		Store store = easyRegimentMapper.getStoreDetail(storeid);
		if (store == null) {
			return new ResultBean("3008", ResultBgMsg.C_3008);
		}
		String domain = PropertyUtil.getProperty("domain");
		// 处理图片链接
		if (StringUtils.isNotEmpty(store.getDoorheadUrl()))
			store.setDoorheadUrl(domain + store.getDoorheadUrl());
		if (StringUtils.isNotEmpty(store.getInnerUrl00()))
			store.setInnerUrl00(domain + store.getInnerUrl00());
		if (StringUtils.isNotEmpty(store.getInnerUrl01()))
			store.setInnerUrl01(domain + store.getInnerUrl01());
		if (StringUtils.isNotEmpty(store.getBusinessLicenceUrl()))
			store.setBusinessLicenceUrl(domain + store.getBusinessLicenceUrl());
		if (StringUtils.isNotEmpty(store.getHandleIdcard()))
			store.setHandleIdcard(domain + store.getHandleIdcard());
		// 处理营业开始/结束时间
		store.setBusinessTimeStart(store.getBusinessTimeStart().substring(0, 5));
		store.setBusinessTimeEnd(store.getBusinessTimeEnd().substring(0, 5));

		// 获取体验店的分类
		List<GoodsClassify> classify = easyRegimentMapper.findStoreClassify(storeid);

		Map<String, Object> ret = new HashMap<>();
		ret.put("store", store);
		ret.put("classify", classify);

		return new ResultBean(ret);
	}

	/*
	 * (non-Javadoc) <p>Title: addStore</p> <p>Description: 添加体验店</p>
	 * 
	 * @param store 体验店数据
	 * 
	 * @param classifyids 体验店分类id数组
	 * 
	 * @param user 体验店角色数据
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.store.EasyRegimentStoreService#addStore(dianfan.entities.
	 * store.Store, java.lang.String[], java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addStore", description = "添加体验店")
	public ResultBean addStore(Store store, String[] classifyids, UserInfo user) {
		// 根据地区code获取省市区
		String addr = areaMapper.getAddrByCode(store.getCityCode(), 3);
		// 拼接地址获取经纬度
		TencentMapApi tma = new TencentMapApi();
		GeocoderAttribute ga = tma.getLngLatLaction(addr + store.getApplyAddr());
		if (ga.getStatus() == 0) {
			// 地址你解析成功
			store.setLongitude(String.valueOf(ga.getLng()));
			store.setLatitude(String.valueOf(ga.getLat()));
		}else {
			//无经纬度信息
			return new ResultBean("3012", ResultBgMsg.C_3012);
		}
				
		if (user != null) {
			// 添加体验店角色
			user.setId(UUIDUtil.getUUID());
			store.setApplyUserid(user.getId());
			staffMapper.addStaffInfo(user);
			// 角色信息
			Role role = supplierApplyMapper.getUserRole(ConstantIF.ROLE_DISTINGUISH05);
			// 添加体验店角色
			staffMapper.bindStaffRoleRelation(user.getId(), role.getId(), role.getRoleName());
			// 获取体验店上级（城市经理/运营服务商）
			String upperid = staffMapper.getStoreUpperid(store.getCityCode());
			staffMapper.bindUserLowerUpperRelate(upperid, user.getId(), user.getCreateBy());
		}

		
		store.setId(UUIDUtil.getUUID());
		// 添加体验店数据
		easyRegimentMapper.addStore(store);

		// 添加体验店分类
		easyRegimentMapper.addStoreClassify(classifyids, store.getId(), store.getCreateBy());
		
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: editStore</p> <p>Description: 编辑体验店</p>
	 * 
	 * @param store 体验店数据
	 * 
	 * @param classifyids 体验店分类id数组
	 * 
	 * @author cjy link: @see
	 * dianfan.service.store.EasyRegimentStoreService#editStore(dianfan.entities.
	 * store.Store, java.lang.String[])
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "editStore", description = "编辑体验店")
	public ResultBean editStore(Store store, String[] classifyids) {
		// 根据地区code获取省市区
		String addr = areaMapper.getAddrByCode(store.getCityCode(), 3);
		// 拼接地址获取经纬度
		TencentMapApi tma = new TencentMapApi();
		GeocoderAttribute ga = tma.getLngLatLaction(addr + store.getApplyAddr());
		if (ga.getStatus() == 0) {
			// 地址你解析成功
			store.setLongitude(String.valueOf(ga.getLng()));
			store.setLatitude(String.valueOf(ga.getLat()));
		}else {
			//无经纬度信息
			return new ResultBean("3012", ResultBgMsg.C_3012);
		}
		// 修改体验店数据
		easyRegimentMapper.updateStore(store);
		
		//修改用户表对应的城市code
		easyRegimentMapper.updateStoreUserAreaCode(store.getId());

		// 删除体验店分类
		easyRegimentMapper.delStoreClassify(new String[] { store.getId() }, store.getUpdateBy());

		// 添加体验店分类
		easyRegimentMapper.addStoreClassify(classifyids, store.getId(), store.getUpdateBy());
		return new ResultBean();
	}

	/*
	 * (non-Javadoc) <p>Title: startStopStore</p> <p>Description: 禁用/启用 体验店</p>
	 * 
	 * @param storeids 体验店id列表
	 * 
	 * @param action 动作
	 * 
	 * @param operater 操作者id
	 * 
	 * @author cjy link: @see
	 * dianfan.service.store.EasyRegimentStoreService#startStopStore(java.lang.
	 * String[], int, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "startStopStore", description = "startStopStore")
	public void startStopStore(String[] storeids, int action, String operater) {
		// 禁用/启用体验店
		easyRegimentMapper.startStopStore(storeids, action, operater);
		// 删除体验店分类
		// easyRegimentMapper.delStoreClassify(storeids, operater);
	}

	/*
	 * (non-Javadoc) <p>Title: findExperienceInfo</p> <p>Description: 获取体验店详情</p>
	 * 
	 * @param id 体验店ID
	 * 
	 * @return link: @see
	 * dianfan.service.store.EasyRegimentStoreService#findExperienceInfo(java.lang.
	 * String)
	 */
	@Override
	@SystemServiceLog(method = "findExperienceInfo", description = "获取体验店详情")
	public ResultBean findExperienceInfo(String id) {
		// 获取体验店的详情
		ExperiencestoreApply info = easyRegimentMapper.findExperienceInfo(id);
		if (org.springframework.util.StringUtils.isEmpty(info)) {
			// 没有该体验店的详情
			return new ResultBean("4029", ResultBgMsg.C_4029);
		}
		// 整理图片的url
		// 域名头
		String domain = PropertyUtil.getProperty("domain");
		if (!StringUtility.isNull(info.getDoorheadUrl())) {
			info.setDoorheadUrl(domain + info.getDoorheadUrl());
		}
		if (!StringUtility.isNull(info.getInnerUrl00())) {
			info.setInnerUrl00(domain + info.getInnerUrl00());
		}
		if (!StringUtility.isNull(info.getInnerUrl01())) {
			info.setInnerUrl01(domain + info.getInnerUrl01());
		}
		// 通过体验店的ID 查找出体验店下面的商品的ID以及商品的信息
		List<GoodsModels> goodsList = easyRegimentMapper.findGoodsListByExs(info.getId());
		// 整理商品图片的路径
		for (GoodsModels goodsModels : goodsList) {
			goodsModels.setGoodsThumbnail(domain + goodsModels.getGoodsThumbnail());
		}

		info.setGoodsList(goodsList);
		// 获取体验店下的代金券信息
		List<Coupon> coupon = easyRegimentMapper.findCoupons(info.getId());
		info.setCoupons(coupon);
		// 成功返回
		return new ResultBean(info);
	}

	/*
	 * (non-Javadoc) <p>Title: getUserGetCoupon</p> <p>Description: 用户领取体验店优惠券</p>
	 * 
	 * @param userid 用户ID
	 * 
	 * @param id 优惠券ID
	 * 
	 * @return link: @see
	 * dianfan.service.store.EasyRegimentStoreService#getUserGetCoupon(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	@SystemServiceLog(method = "getUserGetCoupon", description = "用户领取体验店优惠券")
	public ResultBean getUserGetCoupon(String userid, String id) throws ParseException {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", userid);
		param.put("id", id);
		// 确认用户是否领取或该优惠券
		int count = easyRegimentMapper.getCheckUserCoupon(param);
		if (count != 0) {
			// 如果用户领取了该优惠券，且并未使用。查看优惠券的
			Date usedendtime = easyRegimentMapper.getUsedEndTime(param);
			// 获取当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String time = df.format(new Date());
			Date date = df.parse(time);
			// 比较大小
			int i = usedendtime.compareTo(date);
			if (i > 0 || i == 0) {
				// 如果优惠全还没有过期
				return new ResultBean("4030", ResultBgMsg.C_4030);
			}

		}
		// 获取当前时间+30天的偏移量
		Date addDay = DateUtility.getAddDayToTimeEnd(new Date(), ConstantIF.CUOPON_DAY);
		// 将date类型转换成Timestamp 类型
		Timestamp nowtime = new Timestamp(addDay.getTime());
		param.put("userendtime", nowtime);
		// 领取优惠券操作
		easyRegimentMapper.getUserGetCoupon(param);
		//优惠券分类数量加1
		int couponnum = easyRegimentMapper.getCouponNum(id);
		couponnum = couponnum+1;
		easyRegimentMapper.updateCouponNum(couponnum,id);
		return new ResultBean();
	}
}
