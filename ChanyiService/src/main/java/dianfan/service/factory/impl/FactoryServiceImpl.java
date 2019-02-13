/**  
* @Title: FactoryServiceImpl.java
* @Package dianfan.service.factory.impl
* @Description: TODO
* @author yl
* @date 2018年7月17日 下午1:14:57
* @version V1.0  
*/ 
package dianfan.service.factory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.factory.FactoryMapper;
import dianfan.dao.mapper.order.OrderClassMapper;
import dianfan.entities.factory.FactoryAdminRelate;
import dianfan.entities.factory.FactoryArea;
import dianfan.entities.factory.FactoryInfo;
import dianfan.map.tencent.GeocoderAttribute;
import dianfan.map.tencent.TencentMapApi;
import dianfan.models.ResultBean;
import dianfan.service.factory.FactoryService;
import dianfan.util.UUIDUtil;

/** @ClassName FactoryServiceImpl
 * @Description 
 * @author yl
 * @date 2018年7月17日 下午1:14:57
 */
@Service
public class FactoryServiceImpl implements FactoryService{
	
	@Autowired
	private FactoryMapper factoryMapper;
	@Autowired
	private OrderClassMapper orderClassMapper;

	/* (non-Javadoc)
	 * <p>Title: findFactoryList</p>
	 * <p>Description: </p>
	 * @param page
	 * @param pagecounts
	 * @return
	 * link: @see dianfan.service.factory.FactoryService#findFactoryList(int, int)
	 */ 
	@Override
	public ResultBean findFactoryList(String areacode,int page, int pagecounts) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		int count = factoryMapper.getFactoryNum(areacode);
		// 设置总页数
		data.put("totalcount", count);
		if (count < (page - 1) * pagecounts || count == 0) {
			// 空的返回实体
			data.put("factorylist", new ArrayList<>());
			// 未删选到数据
			return new ResultBean(data);
		}
		// 起始的条数
		params.put("start", (page - 1) * pagecounts);
		// 分页偏移量 10
		params.put("offset", pagecounts);
		params.put("areacode", areacode);
		List<FactoryInfo> flist = factoryMapper.findFactoryList(params);
		/*if (flist !=null && flist.size()>0) {
			for (int i = 0; i < flist.size(); i++) {
				if (StringUtils.isNotEmpty(flist.get(i).getAreaCode())) {
					AreaModel areaName = orderClassMapper.getPCityName(flist.get(i).getAreaCode());
					flist.get(i).setPcareaName(areaName.getAreaCode());
					flist.get(i).setDetailAreaName(areaName.getAreaName());
				}
				
			}
		}*/
		data.put("factorylist", flist);
		return new ResultBean(data);
	}

	/* (non-Javadoc)
	 * <p>Title: addFactory</p>
	 * <p>Description: </p>
	 * @param adminid
	 * @param longitude
	 * @param latitude
	 * @param factoryaddr
	 * @param areacode
	 * link: @see dianfan.service.factory.FactoryService#addFactory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public ResultBean addFactory(String userid,String factoryname,String factoryaddr, String areacode, String adminids) {
		// TODO Auto-generated method stub
		//工厂entity
		FactoryInfo finfo = new FactoryInfo();
		String factoryid = UUIDUtil.getUUID();
		finfo.setId(factoryid);
		finfo.setFactoryName(factoryname);
		finfo.setCreateBy(userid);
		finfo.setFactoryAddr(factoryaddr);
		//详细地理信息解析成经纬度
		TencentMapApi tma = new TencentMapApi();
		GeocoderAttribute ga = tma.getLngLatLaction(factoryaddr);
		if (ga !=null && (ga.getLng() !=null && ga.getLat() !=null)) {		
			finfo.setLatitude(String.valueOf(ga.getLat()));
			finfo.setLongitude(String.valueOf(ga.getLng()));
		   }else {
			   return new ResultBean("2008",ResultBgMsg.C_2008);
		   }
		//工厂区域entity
		FactoryArea fa = new FactoryArea();
		//fa.setAreaCode(areacode);
		fa.setAreacodes(areacode.split(","));
		//fa.setId(UUIDUtil.getUUID());
		fa.setFactoryId(factoryid);
		fa.setCreateBy(userid);
		factoryMapper.addFactory(finfo);
		
		//检测工厂-区域关系
		boolean boo = factoryMapper.checkFactoryArea(areacode.split(","), null);
		if(boo) {
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new ResultBean("2011",ResultBgMsg.C_2011);
		}
		factoryMapper.addFactoryArea(fa);
		
		if(StringUtils.isNotEmpty(adminids)) {
			//检测绑定管理员工厂关系
			boolean bool = factoryMapper.checkBindAdminFactoryRealtion(null, adminids.split(","));
			if(bool) {
				//事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new ResultBean("2012",ResultBgMsg.C_2012);
			}
			//绑定管理员工厂关系
			factoryMapper.bindAdminFactoryRealtion(factoryid, adminids.split(","));
		}
		return new ResultBean();
		
	}

	/* (non-Javadoc)
	 * <p>Title: updateFactory</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param factoryname
	 * @param adminid
	 * @param longitude
	 * @param latitude
	 * @param factoryaddr
	 * @param areacode
	 * link: @see dianfan.service.factory.FactoryService#updateFactory(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public ResultBean updateFactory(String userid,String factoryid, String factoryname,String adminid, String factoryaddr, String areacode, String adminids) {
		// TODO Auto-generated method stub
		//获取工厂版本号
		String fversion = factoryMapper.getFactoryVersion(factoryid);
//		//获取工厂区域版本号
//		String faversion = factoryMapper.getFactoryAreaVersion(factoryid);
		//工厂entity
		FactoryInfo finfo = new FactoryInfo();
		finfo.setId(factoryid);
		finfo.setFactoryName(factoryname);
		finfo.setUpdateBy(userid);
		finfo.setFactoryAddr(factoryaddr);
		//详细地理信息解析成经纬度
		TencentMapApi tma = new TencentMapApi();
		GeocoderAttribute ga = tma.getLngLatLaction(factoryaddr);
		if (ga !=null && (ga.getLng() !=null && ga.getLat() !=null)) {		
			finfo.setLatitude(String.valueOf(ga.getLat()));
			finfo.setLongitude(String.valueOf(ga.getLng()));
	   }else {
		   return new ResultBean("2008",ResultBgMsg.C_2008);
	   }
		finfo.setVersion(Integer.valueOf(fversion));
		factoryMapper.updateFactory(finfo);
		//工厂管理相关表
		FactoryAdminRelate far = new FactoryAdminRelate();
		far.setAdminId(adminid);
		far.setFactoryId(factoryid);
		int farcounts = factoryMapper.getFactoryAdminRelateNum(far);
		if (farcounts==0) {
			factoryMapper.addFactoryAdminRelate(far);
		}
		if (StringUtils.isNotEmpty(areacode)) {
			//获取工厂区域版本号
			String[] areacodes = areacode.split(",");
			
			//检测工厂-区域关系
			boolean boo = factoryMapper.checkFactoryArea(areacodes, factoryid);
			if(boo) {
				//事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new ResultBean("2011",ResultBgMsg.C_2011);
			}
			
			factoryMapper.deleteFactoryArea(factoryid);
			//工厂区域entity
			FactoryArea fa = new FactoryArea();
			fa.setAreacodes(areacodes);
			fa.setFactoryId(factoryid);
			fa.setCreateBy(userid);
			factoryMapper.addFactoryArea(fa);
		}
		if(StringUtils.isNotEmpty(adminids)) {
			//检测绑定管理员工厂关系
			boolean bool = factoryMapper.checkBindAdminFactoryRealtion(factoryid, adminids.split(","));
			if(bool) {
				//事务回滚
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new ResultBean("2012",ResultBgMsg.C_2012);
			}
			
			factoryMapper.unbindAdminFactoryRealtion(factoryid);
			//绑定管理员工厂关系
			factoryMapper.bindAdminFactoryRealtion(factoryid, adminids.split(","));
		}
		return new ResultBean();
	}

	/* (non-Javadoc)
	 * <p>Title: delFactory</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param factoryid
	 * link: @see dianfan.service.factory.FactoryService#delFactory(java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void delFactory(String userid, String factoryid) {
		// TODO Auto-generated method stub
		String[] fids = factoryid.split(",");
		Map<String, Object> params = new HashMap<String, Object>();
		//获取工厂版本号
		List<FactoryInfo> fversion = factoryMapper.findFactoryVersion(fids);
		
		//获取工厂区域版本号
		List<FactoryArea> faversion = factoryMapper.findFactoryAreaVersion(fids);
		
		params.put("fversion", fversion);
		params.put("faversion", faversion);
		params.put("userid", userid);
		factoryMapper.delFactory(params);
		factoryMapper.delFactoryArea(params);
		
		
	}

}
