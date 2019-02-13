/**  
* @Title: GoodsCartServiceImpl.java
* @Package dianfan.service.goodscart.impl
* @Description: TODO
* @author yl
* @date 2018年7月3日 下午4:13:31
* @version V1.0  
*/ 
package dianfan.service.goodscart.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.goodscart.GoodsCartMapper;
import dianfan.entities.goods.GoodsPics;
import dianfan.entities.goods.GoodsPriceModel;
import dianfan.entities.goodscart.GoodsCartModel;
import dianfan.entities.goodscart.GoodsCartModels;
import dianfan.entities.our.GoodsModel;
import dianfan.models.ResultBean;
import dianfan.service.goodscart.GoodsCartService;
import dianfan.util.PropertyUtil;

/** @ClassName GoodsCartServiceImpl
 * @Description 
 * @author yl
 * @date 2018年7月3日 下午4:13:31
 */
@Service
public class GoodsCartServiceImpl implements GoodsCartService{
	
	@Autowired
	private GoodsCartMapper goodsCartMapper;
	
	Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * @Title: addShoppingCart
	 * @Description: 
	 * @param userid 用户id
	 * @param goodsid 商品id
	 * @param name 名称
	 * @param num 数量
	 * @param unit 单位
	 * @param picaddr 图片存储地址 此地址对应床垫成品图
	 * @param picpcmobile 图片类型01：PC端图片 02：Mobile端图片类型
	 * @throws:
	 * @time: 2018年7月3日 下午4:11:34
	 */
	@Override
	@Transactional
	public void addShoppingCart(String userid, String goodsid,String goodspriceid, Integer num) {
		GoodsCartModels gcms = new GoodsCartModels();
		gcms.setGoodsid(goodsid);
		gcms.setGoodspriceid(goodspriceid);
		gcms.setUserid(userid);
		GoodsCartModel gcss = goodsCartMapper.getGoodsCartByUserGidGpid(gcms);
		//如果不为空数量加1
		if (gcss !=null) {
			String goodspriceids = gcss.getGoodsPriceId();
			if (!StringUtils.isNotEmpty(goodspriceid) && !StringUtils.isNotEmpty(goodspriceids)) {
				params.put("id", gcss.getId());
				params.put("num", num+gcss.getNum());
				goodsCartMapper.updateGoodsNum(params);
			}
			if (StringUtils.isNotEmpty(goodspriceids) && StringUtils.isNotEmpty(goodspriceid)) {
				if (goodspriceids.equals(goodspriceid)) {
					params.put("id", gcss.getId());
					params.put("num", num+gcss.getNum());
					goodsCartMapper.updateGoodsNum(params);
				}
			}
				
		}else {
			GoodsCartModel gcm = new GoodsCartModel();
			gcm.setUserId(userid);
			gcm.setGoodsId(goodsid);
			gcm.setGoodsPriceId(goodspriceid);
			//商品图片信息
			GoodsPics gp = new GoodsPics();
			gp.setGoodsId(goodsid);
			gp.setPicType(ConstantIF.PIC_TYPE1);
			gp.setPicPcMobile(ConstantIF.PIC_MOBILE);
			GoodsPics gps = goodsCartMapper.getGoodsPicInfo(gp);
			//商品信息
			GoodsModel gs = goodsCartMapper.getGoodsInfo(goodsid);
			if (StringUtils.isNotEmpty(goodspriceid)) {
				BigDecimal gprices= goodsCartMapper.getGoodsSpecificationPrice(goodspriceid);
				gcm.setUnit(gprices);
				//商品价格信息
				GoodsPriceModel gpm = goodsCartMapper.getGoodsPriceInfo(goodspriceid);
				if (gpm !=null && StringUtils.isNotEmpty(gpm.getGoodsPic())) {
					gcm.setPicAddr(gpm.getGoodsPic());
				}else {
					if (gps !=null) {
						gcm.setPicAddr(gps.getPicAddr());
					}
					
				}
			}else {
				gcm.setPicAddr(gps.getPicAddr());
				BigDecimal prices= goodsCartMapper.getGoodsPrice(goodsid);
				gcm.setUnit(prices);
			}
			gcm.setName(gs.getGoodsTitle());
			gcm.setNum(num);
			goodsCartMapper.addShoppingCart(gcm);
		}
		
	}

	/**
	 * @Title: findShoppingCartList
	 * @Description: 
	 * @param userid 用户id
	 * @return 购物车列表
	 * @throws:
	 * @time: 2018年7月3日 下午8:03:48
	 */
	@Override
	public ResultBean findShoppingCartList(String userid,Integer page,Integer pagecounts) {
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		List<GoodsCartModel> gcmlists = new ArrayList<>();
		int totalcount;
		//统计购物车总数
		int count = goodsCartMapper.getGoodsCartNum(userid);
		if (pagecounts == null) {
			// 设置总条数
			int start = (page - 1) * ConstantIF.PAGE_OFFSET;
			totalcount = count;
			if(totalcount == 0 || start > totalcount) {
				//没有数据
				params.put("count", 0);
				params.put("gmlist", Collections.emptyList());
			}
			// 分页启示页面
			params.put("start", start);
			// 分页偏移量 10
			params.put("offset", ConstantIF.PAGE_OFFSET);
			
		}else {
			//起始的条数
			int start = (page - 1) * pagecounts;
			totalcount = count;
			if(totalcount == 0 || start > totalcount) {
				//没有数据
				params.put("count", 0);
				params.put("gmlist", Collections.emptyList());
			}
			//有数据
			params.put("start", start);//起始的条数
			params.put("offset", pagecounts);//一页条数
		}
		params.put("userid", userid);
		//购物车商品列表
		List<GoodsCartModel> gcmlist = goodsCartMapper.findShoppingCartList(params);
		//商品价格表id列表
		List<String> strgoodsprices = new ArrayList<String>();
		//商品表id列表
		List<String> strgoodss = new ArrayList<String>();
		//商品价格列表
		List<GoodsCartModel> goodspriceids = new ArrayList<GoodsCartModel>();
		//商品列表
		List<GoodsCartModel> goodsids = new ArrayList<GoodsCartModel>();
		if (gcmlist !=null && gcmlist.size()>0) {
			String goodsid = null;
			String goodspiceid = null;
			for (int i = 0; i < gcmlist.size(); i++) {
				goodspiceid = gcmlist.get(i).getGoodsPriceId();
				if (StringUtils.isNotEmpty(goodspiceid)) {
					goodspriceids.add(gcmlist.get(i));
					strgoodsprices.add(gcmlist.get(i).getGoodsPriceId());
					
				}
				goodsid = gcmlist.get(i).getGoodsId();
				if (StringUtils.isNotEmpty(goodsid) && !StringUtils.isNotEmpty(goodspiceid)) {
					goodsids.add(gcmlist.get(i));
					strgoodss.add(gcmlist.get(i).getGoodsId());
				}
			}
			if (goodsids !=null && goodsids.size()>0) {
				//获取商品信息（无规格）
				List<GoodsModel> gmodel = goodsCartMapper.findGoodsList(strgoodss);
				GoodsCartModel gcmodel = null;
				for (int i = 0; i < goodsids.size(); i++) {
					gcmodel = new GoodsCartModel();
					gcmodel.setId(goodsids.get(i).getId());
					gcmodel.setName(goodsids.get(i).getName());
					gcmodel.setPicAddr(goodsids.get(i).getPicAddr());
					gcmodel.setGoodsId(goodsids.get(i).getGoodsId());
					gcmodel.setNum(goodsids.get(i).getNum());
					if (gmodel !=null && gmodel.size()>0) {
						for (int j = 0; j < gmodel.size(); j++) {
							String gsid = goodsids.get(i).getGoodsId();
							String gsmid = gmodel.get(j).getId();
							if (gsid.equals(gsmid)) {
								gcmodel.setPrice(gmodel.get(j).getPrice());
								gcmodel.setGoodsClassifyId(gmodel.get(j).getGoodsClassifyId());
								gcmodel.setGoodsTitleEn(gmodel.get(j).getGoodsTitleEn());
							}
						}
					}	
					gcmlists.add(gcmodel);
				}
			} 
			if (goodspriceids !=null && goodspriceids.size()>0) {
				List<GoodsPriceModel> gpmodel = goodsCartMapper.findGoodsPriceList(strgoodsprices);	
				GoodsCartModels gcartm = null;
				GoodsCartModel gcartms = null;
				List<GoodsCartModels> gss = new ArrayList<GoodsCartModels>();
				for (int i = 0; i < goodspriceids.size(); i++) {
					if (gpmodel !=null && gpmodel.size()>0) {
						for (int j = 0; j < gpmodel.size(); j++) {
							if (goodspriceids.get(i).getGoodsPriceId().equals(gpmodel.get(j).getId())) {
								String[] specids = gpmodel.get(j).getSpecIds().split(",");
								/*int[] ints = new int[specids.length];
								if (specids !=null && specids.length>0) {
									for (int k = 0; k < specids.length; k++) {
										  ints[k] = Integer.parseInt(specids[k]);
									}
								}*/
								gcartm = new GoodsCartModels();
								gcartm.setUserid(userid);
								gcartm.setGoodsspecids(specids);
								gcartm.setGoodspriceid(gpmodel.get(j).getId());
								gcartm.setGoodsid(gpmodel.get(j).getGoodsId());
								gss.add(gcartm);
							}
						}
						
					}	
				}
				if (gss !=null && gss.size()>0) {
					for (int i = 0; i < gss.size(); i++) {
						gcartms = goodsCartMapper.getGoodsCartSpecList(gss.get(i));
						gcmlists.add(gcartms);
					}
				}
				
			} 
			if (gcmlist !=null && gcmlist.size()>0) {
				if (gcmlists !=null && gcmlists.size()>0) {
					for (GoodsCartModel gcm : gcmlist) {
						for (GoodsCartModel gcart : gcmlists) {
							if (gcm.getId().equals(gcart.getId())) {
								gcm.setGoodsClassifyId(gcart.getGoodsClassifyId());
								gcm.setGoodsId(gcart.getGoodsId());
								gcm.setGoodsPriceId(gcart.getGoodsPriceId());
								gcm.setId(gcart.getId());
								gcm.setName(gcart.getName());
								gcm.setNum(gcart.getNum());
								gcm.setPicAddr(PropertyUtil.getProperty("domain")+gcart.getPicAddr());
								gcm.setPrice(gcart.getPrice());
								gcm.setSpecName(gcart.getSpecName());
								gcm.setSpecIds(gcart.getSpecIds());
								gcm.setGoodsTitleEn(gcart.getGoodsTitleEn());
							}
						}
					}
				}
			}
		}
		/* 整理返回参数  */
		// 设置总页数
		data.put("totalcount", totalcount);
		// 设置当前页面
		data.put("page", page);
		// 添加返回的 购物车列表
		data.put("gcmlists", gcmlist);
		return new ResultBean(data);
	}

	/* (non-Javadoc)
	 * <p>Title: delShoppingCart</p>
	 * <p>Description: </p>
	 * @param userid 用户id 预留
	 * @param goodscartid
	 * link: @see dianfan.service.goodscart.GoodsCartService#delShoppingCart(java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void delShoppingCart(String userid, String goodscartid) {
		// TODO Auto-generated method stub
		String[]  goodscartids = goodscartid.split(",");
		goodsCartMapper.delShoppingCart(goodscartids);
		
	}

	/* (non-Javadoc)
	 * <p>Title: updateShoppingCartGoodsNum</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param goodscartid 商品id
	 * @param num
	 * link: @see dianfan.service.goodscart.GoodsCartService#updateShoppingCartGoodsNum(java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateShoppingCartGoodsNum(String userid, String goodscartid, String num) {
		// TODO Auto-generated method stub
		int versionInfo = goodsCartMapper.getVersionInfo(goodscartid);
		params.put("id", goodscartid);
		params.put("num", Integer.valueOf(num));
		params.put("version", versionInfo);
		goodsCartMapper.updateGoodsNum(params);
		
	}

	/* (non-Javadoc)
	 * <p>Title: updateShoppingCartUpdateGoodsSpec</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param goodscartid
	 * @param specid
	 * link: @see dianfan.service.goodscart.GoodsCartService#updateShoppingCartUpdateGoodsSpec(java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateShoppingCartGoodsSpec(String userid, String goodscartid, String goodspriceid) {
		// TODO Auto-generated method stub
		int versionInfo = goodsCartMapper.getVersionInfo(goodscartid);
		GoodsCartModel gcml = goodsCartMapper.getShoppingCarGoodsInfo(goodscartid);
		//获取购物车信息
		List<GoodsCartModel> gcmlist = goodsCartMapper.findGoodsCart(userid);
		//要删除的商品
		List<String> goodscarid = new ArrayList<>();
		int num=0;
		if (gcmlist !=null && gcmlist.size()>0) {
			for (int i = 0; i < gcmlist.size(); i++) {
				if (gcml !=null && StringUtils.isNotEmpty(gcml.getGoodsPriceId())) {
					if (goodspriceid.equals(gcmlist.get(i).getGoodsPriceId())) {
						num = num+gcmlist.get(i).getNum();
						//删除购物车id
						goodscarid.add(gcmlist.get(i).getId());
					}
				}
			}
		}
		if (goodscarid !=null &&goodscarid.size()>0) {
			String[] strings = new String[goodscarid.size()];
			goodscarid.toArray(strings);
			//删除购物车
			goodsCartMapper.delShoppingCart(strings);
		}
		
		int finalnum = gcml.getNum()+num;
		params.put("num", finalnum);
		params.put("version", versionInfo);
		params.put("goodscartid", goodscartid);
		params.put("goodspriceid", goodspriceid);
		goodsCartMapper.updateShoppingCarGoodsSpec(params);
		
	}

	/* (non-Javadoc)
	 * <p>Title: getGoodsCartNums</p>
	 * <p>Description: </p>
	 * @param userid
	 * @return
	 * link: @see dianfan.service.goodscart.GoodsCartService#getGoodsCartNums(java.lang.String)
	 */ 
	@Override
	public int getGoodsCartNums(String userid,String goodsid,String goodspriceid) {
		// TODO Auto-generated method stub
		int gcnum = goodsCartMapper.getSingleGoodsNum(userid,goodsid,goodspriceid);
		return gcnum;
	}

}
