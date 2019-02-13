/**  
* @Title: CouponServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午2:35:25
* @version V1.0  
*/
package dianfan.service.our.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.base.AreaMapper;
import dianfan.dao.mapper.our.CouponBatchMapper;
import dianfan.dao.mapper.our.CouponMapper;
import dianfan.entities.Coupon;
import dianfan.entities.UserCouponDetail;
import dianfan.entities.UserCouponRelate;
import dianfan.entities.UserInfo;
import dianfan.entities.goods.GoodsClassify;
import dianfan.entities.our.CouponModel;
import dianfan.entities.our.GoodsModel;
import dianfan.entities.our.UserInfoModel;
import dianfan.entities.store.Store;
import dianfan.models.ResultBean;
import dianfan.service.our.CouponService;
import dianfan.util.PropertyUtil;
import dianfan.util.StringUtility;

/**
 * @ClassName CouponServiceImpl
 * @Description
 * @author yl
 * @date 2018年6月28日 下午2:35:25
 */
@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private CouponBatchMapper couponBatchMapper;
	@Autowired
	private AreaMapper areaMapper;

	/*
	 * (non-Javadoc) <p>Title: findCouponList</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @return link: @see
	 * dianfan.service.our.CouponService#findCouponList(java.lang.String)
	 */
	@Override
	public Map<String, Object> findCouponList(String userid, Integer userused, Integer pagenum, Integer count,
			String used) {
		List<CouponModel> cmlist = new ArrayList<CouponModel>();
		CouponModel cm = null;
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("userused", userused);
		params.put("used", used);
		params.put("pagenum", pagenum);

		// 查询总共多少条
		int totalCount = couponMapper.getCouponCount(params);
		// 起始的条数
		int start = (pagenum - 1) * count;
		if (totalCount == 0 || start > totalCount) {
			// 没有数据
			params.put("totalCount", 0);
			params.put("couponList", Collections.emptyList());
		}
		// 有数据
		params.put("start", start);// 起始的条数
		params.put("count", count);// 一页条数
		params.put("totalCount", totalCount);// 总条数

		List<UserCouponRelate> userCouponRelate = couponMapper.findCouponIdList(params);
		List<String> couponids = new ArrayList<>();
		String couponid = null;
		if (userCouponRelate != null && userCouponRelate.size() > 0) {
			for (int j = 0; j < userCouponRelate.size(); j++) {
				couponid = userCouponRelate.get(j).getCouponId();
				couponids.add(couponid);
				cm = new CouponModel();
				cm.setUcrid(userCouponRelate.get(j).getId());
				cm.setId(couponid);
				cm.setDrawDate(userCouponRelate.get(j).getDrawDate());
				cm.setUsedDate(userCouponRelate.get(j).getUsedDate());
				cm.setUsedEndTime(userCouponRelate.get(j).getUsedEndTime());
				cm.setUserUsed(userCouponRelate.get(j).getUserUsed());
				List<Coupon> coupons = couponMapper.findCouponList(couponids);
				for (int i = 0; i < coupons.size(); i++) {
					if (couponid.equals(coupons.get(i).getId())) {
						cm.setCouponApply(coupons.get(i).getCouponApply());

						cm.setCouponClassifyId(coupons.get(i).getCouponClassifyId());
						if (StringUtils.isNotEmpty(cm.getCouponClassifyId())) {
							List<GoodsClassify> list = couponMapper
									.findCouponClassify(cm.getCouponClassifyId().split(","));
							cm.setGoodsClassify(list);
						}
						cm.setGoodsid(coupons.get(i).getGoodsid());
						if (StringUtils.isNotEmpty(cm.getGoodsid())) {
							List<GoodsModel> goods = couponMapper.findGoods(cm.getGoodsid().split(","));
							for (GoodsModel gm : goods) {
								if (gm.getPicAddr() != null)
									gm.setPicAddr(PropertyUtil.getProperty("domain") + gm.getPicAddr());
							}
							cm.setGoods(goods);
						}

						cm.setStoreid(coupons.get(i).getStoreid());
						if (StringUtils.isNotEmpty(cm.getStoreid())) {
							List<Store> stores = couponMapper.findSotre(cm.getStoreid().split(","));
							for (Store s : stores) {
								if (!StringUtility.isNull(s.getCityCode())) {
									int level;
									if (s.getCityCode().lastIndexOf("0000") != -1) {
										// 省级code
										level = 1;
									} else if (s.getCityCode().lastIndexOf("00") != -1) {
										// 市级code
										level = 2;
									} else {
										// 区县code
										level = 3;
									}
									String addrByCode = areaMapper.getAddrByCode(s.getCityCode(), level);
									s.setCityCode(addrByCode + s.getCityCode());
								}
								s.setDoorheadUrl(PropertyUtil.getProperty("domain") + s.getDoorheadUrl());
							}
							cm.setStore(stores);
						}

						cm.setCouponDes(coupons.get(i).getCouponDes());
						cm.setCouponEndtime(coupons.get(i).getCouponEndtime());
						cm.setCouponName(coupons.get(i).getCouponName());
						cm.setCouponNum(coupons.get(i).getCouponNum());
						cm.setCouponStarttime(coupons.get(i).getCouponStarttime());
						cm.setCouponType(coupons.get(i).getCouponType());
						cm.setCreateTime(coupons.get(i).getCreateTime());
						cm.setCouponCondtion(coupons.get(i).getCouponCondtion());
						cm.setCouponReduceMoney(coupons.get(i).getCouponReduceMoney());
					}
				}
				cmlist.add(cm);
			}
		}
		params.put("couponList", cmlist);
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dianfan.service.our.CouponService#updateCoupon(dianfan.entities.
	 * UserCouponRelate)
	 */
	@Override
	public void updateCoupon(UserCouponRelate ucr) {
		couponMapper.updateCoupon(ucr);
	}

	/*
	 * (non-Javadoc) <p>Title: findBgCouponList</p> <p>Description: </p>
	 * 
	 * @param pageNum
	 * 
	 * @param count
	 * 
	 * @return link: @see
	 * dianfan.service.our.CouponService#findBgCouponList(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public ResultBean findBgCouponList(String couponname, String coupontype, String couponendtimestart,
			String couponendtimeend, String couponapply, String couponreducemoneystart, String couponreducemoneyend,
			String couponstarttimestart, String couponstarttimeend, String createtimestart, String createtimeend,
			int page, int pagecounts) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> sucessdata = new HashMap<String, Object>();
		Timestamp ts = null;
		if (StringUtils.isNotEmpty(couponname)) {
			params.put("couponname", couponname.trim());
		} else {
			params.put("couponname", null);
		}
		if (StringUtils.isNotEmpty(coupontype)) {
			params.put("coupontype", coupontype.trim());
		} else {
			params.put("coupontype", null);
		}
		if (StringUtils.isNotEmpty(couponendtimestart) && StringUtils.isNotEmpty(couponendtimeend)) {
			ts = Timestamp.valueOf(couponendtimestart);
			params.put("couponendtimestart", ts);
			ts = Timestamp.valueOf(couponendtimeend);
			params.put("couponendtimeend", ts);
		} else {
			params.put("couponendtimestart", null);
			params.put("couponendtimeend", null);
		}
		if (StringUtils.isNotEmpty(couponapply)) {
			params.put("couponapply", couponapply.trim());
		} else {
			params.put("couponapply", null);
		}
		if (StringUtils.isNotEmpty(couponreducemoneystart) && StringUtils.isNotEmpty(couponreducemoneyend)) {
			params.put("couponreducemoneystart", new BigDecimal(couponreducemoneystart));
			params.put("couponreducemoneyend", new BigDecimal(couponreducemoneyend));
		} else {
			params.put("couponreducemoneystart", null);
			params.put("couponreducemoneyend", null);
		}
		if (StringUtils.isNotEmpty(couponstarttimestart) && StringUtils.isNotEmpty(couponstarttimeend)) {
			ts = Timestamp.valueOf(couponstarttimestart);
			params.put("couponstarttimestart", ts);
			ts = Timestamp.valueOf(couponstarttimeend);
			params.put("couponstarttimeend", ts);
		} else {
			params.put("couponstarttimestart", null);
			params.put("couponstarttimeend", null);
		}
		if (StringUtils.isNotEmpty(createtimestart) && StringUtils.isNotEmpty(createtimeend)) {
			ts = Timestamp.valueOf(createtimestart);
			params.put("createtimestart", ts);
			ts = Timestamp.valueOf(createtimeend);
			params.put("createtimeend", ts);
		} else {
			params.put("createtimestart", null);
			params.put("createtimeend", null);
		}
		int count = couponMapper.getBgCouponNum(params);
		// 设置总页数
		data.put("totalcount", count);
		sucessdata.put("totalcount", count);
		if (count <= (page - 1) * pagecounts || count == 0) {
			// 空的返回实体
			sucessdata.put("couponlist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(sucessdata);
		}
		// 起始的条数
		params.put("start", (page - 1) * pagecounts);
		// 分页偏移量 10
		params.put("offset", pagecounts);
		List<Coupon> clist = couponMapper.findBgCouponList(params);
		sucessdata.put("couponlist", clist);
		return new ResultBean(sucessdata);
	}

	/*
	 * (non-Javadoc) <p>Title: addBgCoupon</p> <p>Description: </p>
	 * 
	 * @param couponname
	 * 
	 * @param coupontype
	 * 
	 * @param couponendtime
	 * 
	 * @param couponapply
	 * 
	 * @param couponreducemoney
	 * 
	 * @param couponstarttime
	 * 
	 * @param couponcondtion
	 * 
	 * @param coupondes
	 * 
	 * @param couponclassifyid
	 * 
	 * @param goodsid
	 * 
	 * @param storeid link: @see
	 * dianfan.service.our.CouponService#addBgCoupon(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void addBgCoupon(String userid, String couponname, String coupontype, String couponendtime,
			String couponapply, String couponreducemoney, String couponstarttime, String couponcondtion,
			String coupondes, String couponclassifyid, String goodsid, String storeid) {
		// TODO Auto-generated method stub
		Coupon coupon = new Coupon();
		Timestamp ts = null;
		coupon.setCouponApply(couponapply);
		coupon.setCouponClassifyId(couponclassifyid);
		coupon.setCouponCondtion(couponcondtion);
		coupon.setCouponDes(coupondes);
		if (StringUtils.isNotEmpty(couponendtime)) {
			ts = Timestamp.valueOf(couponendtime);
			coupon.setCouponEndtime(ts);
		}
		coupon.setCouponName(couponname);
		coupon.setCouponNum(0);
		coupon.setCouponReduceMoney(couponreducemoney);
		if (StringUtils.isNotEmpty(couponstarttime)) {
			ts = Timestamp.valueOf(couponstarttime);
			coupon.setCouponStarttime(ts);
		}
		coupon.setCouponType(coupontype);
		coupon.setCreateBy(userid);

		coupon.setGoodsid(goodsid);
		coupon.setStoreid(storeid);

		couponMapper.addBgCoupon(coupon);
	}

	/*
	 * (non-Javadoc) <p>Title: updateBgCoupon</p>
	 * 
	 * @Description: 修改优惠券
	 * 
	 * @param couponid 优惠券id
	 * 
	 * @param couponname 优惠券名称
	 * 
	 * @param coupontype 优惠券类型
	 * 
	 * @param couponendtime 优惠券结束时间
	 * 
	 * @param couponapply 使用(01:应用02：停止应用)
	 * 
	 * @param couponreducemoney 优惠券优惠金额
	 * 
	 * @param couponstarttime 优惠券投放时间
	 * 
	 * @param couponcondtion 优惠券满足条件
	 * 
	 * @param coupondes 优惠券描述
	 * 
	 * @param couponclassifyid 商品分类id（可多选，如果为空，全场通用） link: @see
	 * dianfan.service.our.CouponService#updateBgCoupon(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void updateBgCoupon(String userid, String couponid, String couponname, String coupontype,
			String couponendtime, String couponapply, String couponreducemoney, String couponstarttime,
			String couponcondtion, String coupondes, String couponclassifyid, String goodsid, String storeid) {
		// TODO Auto-generated method stub
		int cversion = couponMapper.getCouponVersion(couponid);
		Coupon coupon = new Coupon();
		Timestamp ts = null;
		coupon.setId(couponid);
		coupon.setCouponApply(couponapply);
		coupon.setCouponClassifyId(couponclassifyid);
		coupon.setCouponCondtion(couponcondtion);
		coupon.setCouponDes(coupondes);
		if (StringUtils.isNotEmpty(couponendtime)) {
			ts = Timestamp.valueOf(couponendtime);
			coupon.setCouponEndtime(ts);
		}
		coupon.setCouponName(couponname);
		coupon.setCouponNum(0);
		coupon.setCouponReduceMoney(couponreducemoney);
		if (StringUtils.isNotEmpty(couponstarttime)) {
			ts = Timestamp.valueOf(couponstarttime);
			coupon.setCouponStarttime(ts);
		}
		coupon.setCouponType(coupontype);
		coupon.setUpdateBy(userid);
		coupon.setVersion(cversion);
		coupon.setGoodsid(goodsid);
		coupon.setStoreid(storeid);
		couponMapper.updateBgCoupon(coupon);

	}

	/*
	 * (non-Javadoc) <p>Title: delBgCoupon</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param couponid link: @see
	 * dianfan.service.our.CouponService#delBgCoupon(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public void delBgCoupon(String userid, String couponid) {
		// TODO Auto-generated method stub
		String[] coupons = couponid.split(",");
		// 删除优惠券表
		couponMapper.delCoupons(userid, coupons);
		// 删除用户优惠券相关表
		couponMapper.delUserCouponRelates(userid, coupons);
	}

	/*
	 * (non-Javadoc) <p>Title: getBgCouponDetail</p> <p>Description: </p>
	 * 
	 * @param couponid
	 * 
	 * @return link: @see
	 * dianfan.service.our.CouponService#getBgCouponDetail(java.lang.String)
	 */
	@Override
	public ResultBean findBgCouponDetail(String couponname, String coupontype, String couponendtimestart,
			String couponendtimeend, String couponapply, String couponreducemoneystart, String couponreducemoneyend,
			String couponstarttimestart, String couponstarttimeend, String createtimestart, String createtimeend,
			String drawdatestart, String drawdateend, String useddatestart, String useddateend, String usedendtimestart,
			String usedendtimeend, String userused, String nickName, String telno, int page, int pagecounts) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> sucessdata = new HashMap<String, Object>();
		Timestamp ts = null;
		if (StringUtils.isNotEmpty(nickName)) {
			params.put("nickName", nickName);
		} else {
			params.put("nickName", null);
		}
		if (StringUtils.isNotEmpty(telno)) {
			params.put("telno", telno);
		} else {
			params.put("telno", null);
		}
		if (StringUtils.isNotEmpty(couponname)) {
			params.put("couponname", couponname);
		} else {
			params.put("couponname", null);
		}
		if (StringUtils.isNotEmpty(coupontype)) {
			params.put("coupontype", coupontype);
		} else {
			params.put("coupontype", null);
		}
		if (StringUtils.isNotEmpty(couponendtimestart) && StringUtils.isNotEmpty(couponendtimeend)) {
			ts = Timestamp.valueOf(couponendtimestart);
			params.put("couponendtimestart", ts);
			ts = Timestamp.valueOf(couponendtimeend);
			params.put("couponendtimeend", ts);
		} else {
			params.put("couponendtimestart", null);
			params.put("couponendtimeend", null);
		}
		if (StringUtils.isNotEmpty(couponapply)) {
			params.put("couponapply", couponapply);
		} else {
			params.put("couponapply", null);
		}
		if (StringUtils.isNotEmpty(couponreducemoneystart) && StringUtils.isNotEmpty(couponreducemoneyend)) {
			params.put("couponreducemoneystart", couponreducemoneystart);
			params.put("couponreducemoneyend", couponreducemoneyend);
		} else {
			params.put("couponreducemoneystart", null);
			params.put("couponreducemoneyend", null);
		}
		if (StringUtils.isNotEmpty(couponstarttimestart) && StringUtils.isNotEmpty(couponstarttimeend)) {
			ts = Timestamp.valueOf(couponstarttimestart);
			params.put("couponstarttimestart", ts);
			ts = Timestamp.valueOf(couponstarttimeend);
			params.put("couponstarttimeend", ts);
		} else {
			params.put("couponstarttimestart", null);
			params.put("couponstarttimeend", null);
		}
		if (StringUtils.isNotEmpty(createtimestart) && StringUtils.isNotEmpty(createtimeend)) {
			ts = Timestamp.valueOf(createtimestart);
			params.put("createtimestart", ts);
			ts = Timestamp.valueOf(createtimeend);
			params.put("createtimeend", ts);
		} else {
			params.put("createtimestart", null);
			params.put("createtimeend", null);
		}
		if (StringUtils.isNotEmpty(drawdatestart) && StringUtils.isNotEmpty(drawdateend)) {
			ts = Timestamp.valueOf(drawdatestart);
			params.put("drawdatestart", ts);
			ts = Timestamp.valueOf(drawdateend);
			params.put("drawdateend", ts);
		} else {
			params.put("drawdatestart", null);
			params.put("drawdateend", null);
		}
		if (StringUtils.isNotEmpty(useddatestart) && StringUtils.isNotEmpty(useddateend)) {
			ts = Timestamp.valueOf(useddatestart);
			params.put("useddatestart", ts);
			ts = Timestamp.valueOf(useddateend);
			params.put("useddateend", ts);
		} else {
			params.put("useddatestart", null);
			params.put("useddateend", null);
		}
		if (StringUtils.isNotEmpty(usedendtimestart) && StringUtils.isNotEmpty(usedendtimeend)) {
			ts = Timestamp.valueOf(usedendtimestart);
			params.put("usedendtimestart", ts);
			ts = Timestamp.valueOf(usedendtimeend);
			params.put("usedendtimeend", ts);
		} else {
			params.put("usedendtimestart", null);
			params.put("usedendtimeend", null);
		}
		if (StringUtils.isNotEmpty(userused)) {
			params.put("userused", userused);
		} else {
			params.put("userused", null);
		}
		int count = couponMapper.getBgCouponDetailNum(params);
		// 设置总页数
		data.put("totalcount", count);
		sucessdata.put("totalcount", count);
		if (count <= (page - 1) * pagecounts || count == 0) {
			// 空的返回实体
			sucessdata.put("couponlist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(sucessdata);
		}
		// 起始的条数
		params.put("start", (page - 1) * pagecounts);
		// 分页偏移量 10
		params.put("offset", pagecounts);
		List<UserCouponDetail> clist = couponMapper.findBgCouponDetail(params);
		sucessdata.put("couponlist", clist);
		return new ResultBean(sucessdata);
	}

	/*
	 * (non-Javadoc) <p>Title: addUCRelate</p> <p>Description: </p>
	 * 
	 * @param userid
	 * 
	 * @param couponid
	 * 
	 * @param usedendtime link: @see
	 * dianfan.service.our.CouponService#addUCRelate(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean addUCRelate(String userid, String couponid, String usedendtime, String name, String telno,
			String source, String starttime, String endtime, String role, String sex, String areacode) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		UserCouponRelate ucr = null;

		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("name", name);
		param.put("telno", telno);
		param.put("source", source);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		// 按角色
		param.put("role", role);
		// 性别
		param.put("sex", sex);
		// 区域code
		param.put("areacode", areacode);
		// 获取用户数量
		int usernums = couponMapper.getUserListNum(param);
		// 获取用户列表
		List<UserInfo> userList = couponMapper.findUserList(param);
		String couponuserid = "";
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				couponuserid += userList.get(i).getId() + ",";
			}
		}
		String[] usernum = couponuserid.split(",");
		params.put("userids", usernum);
		params.put("couponid", couponid);
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(usedendtime);
		params.put("usedendtime", ts);
		Coupon cn = couponMapper.getBgCouponDetail(couponid);
		int cnum = cn.getCouponNum();
		int finalnum = usernum.length + cnum;
		// 修改数量
		Coupon cou = new Coupon();
		cou.setVersion(cn.getVersion());
		cou.setId(couponid);
		cou.setUpdateBy(userid);
		cou.setCouponNum(finalnum);
		couponMapper.updateBgCouponNum(cou);
		List<UserCouponRelate> ucrlist = new ArrayList<UserCouponRelate>();
		if (usernum != null && usernum.length > 0) {
			for (int i = 0; i < usernum.length; i++) {
				ucr = new UserCouponRelate();
				ucr.setUserId(usernum[i]);
				ucr.setCouponId(couponid);
				ucr.setUsedEndTime(ts);
				ucr.setCreateBy(userid);
				ucrlist.add(ucr);
			}
		}
		couponBatchMapper.addUserCoupon(ucrlist);
		// couponMapper.addUCRelate(params);
		return new ResultBean(usernums);
	}

	/*
	 * (non-Javadoc) <p>Title: checkEnableCoupon</p> <p>Description: 卡券可用性检测</p>
	 * 
	 * @param couponid 优惠券id
	 * 
	 * @param userid 用户id
	 * 
	 * @return
	 * 
	 * @author cjy link: @see
	 * dianfan.service.our.CouponService#checkEnableCoupon(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean checkEnableCoupon(String couponid, String userid) {

		UserInfoModel ck1 = couponMapper.checkEnableCoupon1(userid);
		boolean b = false;
		boolean b1 = false;
		if (ck1 != null && ck1.getSalerQrNum() != null) {
			b1 = couponMapper.checkEnableSaler(couponid, userid);
		} else if (ck1 != null && ck1.getExperiencestoreQrNum() != null) {
			b = couponMapper.checkEnableCoupon(couponid, userid);
		}
		if (b || b1) {
			return true;
		} else {
			return false;
		}
	}

}
