/**  
* @Title: FactoryService.java
* @Package dianfan.service.factory
* @Description: TODO
* @author yl
* @date 2018年7月17日 下午12:57:57
* @version V1.0  
*/ 
package dianfan.service.factory;

import dianfan.models.ResultBean;

/** @ClassName FactoryService
 * @Description 
 * @author yl
 * @date 2018年7月17日 下午12:57:57
 */
public interface FactoryService {
	/**
	 * @Title: findFactoryList
	 * @Description: 
	 * @param areacode 区域code
	 * @param page 页数
	 * @param pagecounts 每页条数
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午2:03:22
	 */
	ResultBean findFactoryList(String areacode,int page,int pagecounts);
	
	/**
	 * @Title: addFactory
	 * @Description:  添加工厂
	 * @param adminid 工厂账号
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param factoryaddr 工厂地址
	 * @param areacode 区域code
	 * @throws:
	 * @time: 2018年7月17日 下午3:06:55
	 */
	ResultBean addFactory(String userid,String factoryname,String factoryaddr,String areacode, String adminids);
	/**
	 * @Title: updateFactory
	 * @Description:  修改工厂
	 * @param adminid 工厂账号
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param factoryaddr 工厂地址
	 * @param areacode 区域code
	 * @throws:
	 * @time: 2018年7月17日 下午3:06:55
	 */
	ResultBean updateFactory(String userid,String factoryid,String factoryname,String adminid,String factoryaddr,String areacode, String adminids);
	/**
	 * @Title: delFactory
	 * @Description: 
	 * @param userid 用户id
	 * @param factoryid 工厂id
	 * @throws:
	 * @time: 2018年7月18日 上午9:14:00
	 */
	void delFactory(String userid,String factoryid);

}
