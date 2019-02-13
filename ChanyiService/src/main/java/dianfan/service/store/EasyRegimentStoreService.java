package dianfan.service.store;

import java.io.IOException;
import java.text.ParseException;

import dianfan.entities.UserInfo;
import dianfan.entities.store.ESSearchParam;
import dianfan.entities.store.Store;
import dianfan.models.ResultBean;

/**
 * @ClassName EasyRegimentStoreService
 * @Description 易团服务
 * @author cjy
 * @date 2018年7月6日 下午2:44:33
 */
public interface EasyRegimentStoreService {

	/**
	 * @Title: findEasyRegimentStore
	 * @Description: 易团体验店列表(按搜索项)
	 * @param essp 搜索参数
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午2:51:02
	 */
	ResultBean findEasyRegimentStore(ESSearchParam essp) throws IOException ;

	/**
	 * @Title: findStoreFiltrate
	 * @Description: 体验店筛选项
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return
	 * @throws:
	 * @time: 2018年7月6日 下午7:05:54
	 */
	ResultBean findStoreFiltrate(String longitude, String latitude, String cityCode);
	
	/* ********************后台******************** */
	
	/**
	 * @Title: findStoreList
	 * @Description: 获取体验店列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午2:15:03
	 */
	ResultBean findStoreList(int page, int pageSize, Store store);

	/**
	 * @Title: getStoreDetail
	 * @Description: 体验店详情
	 * @param storeid 体验店id
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 下午1:14:09
	 * @author cjy
	 */
	ResultBean getStoreDetail(String storeid);
	
	/**
	 * @Title: addStore
	 * @Description: 添加体验店
	 * @param store 体验店数据
	 * @param classifyids 体验店分类id数组
	 * @param user 体验店角色数据
	 * @return
	 * @throws:
	 * @time: 2018年7月24日 上午11:02:26
	 * @author cjy
	 */
	ResultBean addStore(Store store, String[] classifyids, UserInfo user);
	
	/**
	 * @Title: editStore
	 * @Description: 编辑体验店
	 * @param store 体验店数据
	 * @param classifyids 体验店分类id数组
	 * @throws:
	 * @time: 2018年7月24日 下午2:41:11
	 * @author cjy
	 */
	ResultBean editStore(Store store, String[] classifyids);

	/**
	 * @Title: startStopStore
	 * @Description: 禁用/启用 体验店
	 * @param storeids 体验店id列表
	 * @param action 动作
	 * @param operater 操作者id
	 * @throws:
	 * @time: 2018年7月28日 下午12:12:44
	 * @author cjy
	 */
	void startStopStore(String[] storeids, int action, String operater);

	/**
	 * @Title: findExperienceInfo
	 * @Description: 获取体验店详情
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年8月2日 下午1:30:22
	 */
	ResultBean findExperienceInfo(String id);

	/**
	 * @Title: getUserGetCoupon
	 * @Description: 用户领取优惠券 
	 * @param userid 用户的ID
	 * @param id 优惠券的ID
	 * @return
	 * @throws ParseException 
	 * @throws:
	 * @time: 2018年8月3日 上午10:43:21
	 */
	ResultBean getUserGetCoupon(String userid, String id) throws ParseException;

	
}
