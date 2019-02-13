/**  
* @Title: CityManagerApplyService.java
* @Package dianfan.service.our
* @Description: TODO
* @author yl
* @date 2018年6月29日 下午2:59:21
* @version V1.0  
*/ 
package dianfan.service.our;

import dianfan.models.ResultBean;

/** @ClassName CityManagerApplyService
 * @Description 
 * @author yl
 * @date 2018年6月29日 下午2:59:21
 */
public interface CityManagerApplyService {
	
	/**
	 * @Title: addCityManagerApply
	 * @Description: 
	 * @param userid 用户id
	 * @param applyname 姓名
	 * @param applyphonenum 手机号
	 * @param applyaddr 地址
	 * @param realname 真实姓名
	 * @param idcardno 身份证号码
	 * @param idcardfont 身份证正面
	 * @param idcardback 身份证正面
	 * @param idcardvaliddate 有效期
	 * @param handleidcard 
	 * @throws:
	 * @time: 2018年6月29日 下午6:40:47
	 */
	ResultBean addCityManagerApply(String userid,String applyphonenum,String applyname,String realname,String idcardno,String idcardfont,String idcardback,String idcardvaliddate,String handleidcard,String status,String cityCode);
	
	/**
	 * @Title: updateCityManagerApply
	 * @Description: 
	 * @param userid 用户id
	 * @param applyname 姓名
	 * @param applyphonenum 手机号
	 * @param applyaddr 地址
	 * @param realname 真实姓名
	 * @param idcardno 身份证号码
	 * @param idcardfont 身份证正面
	 * @param idcardback 身份证正面
	 * @param idcardvaliddate 有效期
	 * @param handleidcard 
	 * @throws:
	 * @time: 2018年7月11日 下午4:06:22
	 */
	void updateCityManagerApply(String userid,String applyphonenum,String applyname,String realname,String idcardno,String idcardfont,String idcardback,String idcardvaliddate,String handleidcard,String cityCode);
	
	/**
	 * @Title: updateOperaServerApply
	 * @Description: 
	 * @param userid
	 * @param operaserverid
	 * @param applyUserId
	 * @param applystatus
	 * @param fReason
	 * @throws:
	 * @time: 2018年7月23日 下午5:48:00
	 */
	ResultBean updateBgCityManagerApply(String userid, String citymangerid, String applystatus,String fReason,String roledistinguish,String contractUrl);
	
	/**
	 * @Title: getCooperationApply
	 * @Description: 判断用户是否已存在
	 * @param userid 用户id
	 * @return 存在数量
	 * @throws:
	 * @time: 2018年6月29日 下午6:43:25
	 */
	int getCityManagerApply(String userid);
	/**
	 * @Title: findCityManagerApplyList
	 * @Description: 
	 * @param status 状态
	 * @param applyname 申请人姓名
	 * @param createTimeStart 创建时间start
	 * @param createTimeEnd 创建时间end
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:34:44
	 */
	ResultBean findCityManagerApplyList(String status,String applyname,String realname,String idcardno,String createTimeStart,String createTimeEnd,int page,int pageSize);

}
