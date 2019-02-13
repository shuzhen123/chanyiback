/**  
* @Title: ExperApplyService.java
* @Package dianfan.service.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午7:11:23
* @version V1.0  
*/ 
package dianfan.service.our;

import dianfan.models.ResultBean;

/** @ClassName ExperApplyService
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午7:11:23
 */
public interface ExperApplyService {
	/**
	 * @Title: addExperApply
	 * @Description: 
	 * @param applyuserid 申请者id
	 * @param applyaddr 地址
	 * @param area 区域
	 * @param goodsclassifyid 
	 * @param applyphonenum 手机号
	 * @param doorheadurl 门头图片
	 * @param innerurl00 店内图片一
	 * @param innerurl01 店内图片二
	 * @param businesslicenceurl 
	 * @param realname 姓名
	 * @param idcardno 身份证号
	 * @param idcardfont 身份证正面
	 * @param idcardback 身份证反面
	 * @param idcardvaliddate 身份证有效期
	 * @param handleidcard
	 * @param cityCode 城市code
	 * @param businessWeeklyStart 营业日起始
	 * @param businessWeeklyEnd 营业日结束
	 * @param businessTimeStart 营业时间起始
	 * @param businessTimeEnd 营业时间结束
	 * @param applyCurrentBusiness 主营业务
	 * @param goodsClassifyId 体验店分类
	 * @throws:
	 * @time: 2018年6月30日 上午9:42:32
	 */
	ResultBean addExperApply(String applyuserid,String applyname,String applyaddr,String area,String applycurrentbusiness,String applyphonenum,String doorheadurl,String innerurl00,String innerurl01,String businesslicenceurl,String status,String cityCode,String businessWeeklyStart,String businessWeeklyEnd,String businessTimeStart,String businessTimeEnd,String goodsClassifyId);
	
	/**
	 * @Title: updateExperApply
	 * @Description: 
	 * @param applyuserid 申请者id
	 * @param applyaddr 地址
	 * @param area 区域
	 * @param goodsclassifyid 
	 * @param applyphonenum 手机号
	 * @param doorheadurl 门头图片
	 * @param innerurl00 店内图片一
	 * @param innerurl01 店内图片二
	 * @param businesslicenceurl 
	 * @param realname 姓名
	 * @param idcardno 身份证号
	 * @param idcardfont 身份证正面
	 * @param idcardback 身份证反面
	 * @param idcardvaliddate 身份证有效期
	 * @param handleidcard
	 * @param cityCode 城市code
	 * @param businessWeeklyStart 营业日起始
	 * @param businessWeeklyEnd 营业日结束
	 * @param businessTimeStart 营业时间起始
	 * @param businessTimeEnd 营业时间结束
	 * @param applyCurrentBusiness 主营业务
	 * @param goodsClassifyId 体验店分类
	 * @throws:
	 * @time: 2018年6月30日 上午9:42:32
	 */
	ResultBean updateExperApply(String applyuserid,String applyname,String applyaddr,String area,String applycurrentbusiness,String applyphonenum,String doorheadurl,String innerurl00,String innerurl01,String businesslicenceurl,String cityCode,String businessWeeklyStart,String businessWeeklyEnd,String businessTimeStart,String businessTimeEnd,String goodsClassifyId);
	
	/**
	 * @Title: updateBgExperApply
	 * @Description: 体验店申请审批
	 * @param userid 用户id
	 * @param experienid 体验店id
	 * @param applyUserId 申请人id
	 * @param status 状态
	 * @param fReason 失败原因
	 * @param roledistinguish 角色
	 * @throws:
	 * @time: 2018年7月30日 上午11:03:41
	 */
	ResultBean updateBgExperApply(String userid,String experienid,String applyname,String applyUserId,String status,String fReason, String roledistinguish,String applyaddr,String area,String applycurrentbusiness,String applyphonenum,
			String doorheadurl,String innerurl00,String innerurl01,String businesslicenceurl,String areaCode,String businessWeeklyStart,String businessWeeklyEnd,String businessTimeStart,String businessTimeEnd,
			String goodsClassifyId);
	
	/**
	 * @Title: getExperApply
	 * @Description: 是否已经是体验店
	 * @param applyuserid
	 * @return
	 * @throws:
	 * @time: 2018年6月30日 上午9:44:34
	 */
	int getExperApply(String applyuserid);
	/**
	 * @Title: getExperApplyByPhone
	 * @Description: 验证手机号是否存在
	 * @param phone 手机号码
	 * @return 
	 * @throws:
	 * @time: 2018年6月30日 下午3:42:38
	 */
	int getExperApplyByPhone(String phoneno);
	/**
	 * @Title: findConsumerList
	 * @Description: 
	 * @param status 状态
	 * @param nickName 昵称
	 * @param createTimeStart 创建时间start
	 * @param createTimeEnd 创建时间end
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:34:44
	 */
	ResultBean findExperApplyList(String status, String applyname,String citycode, String applyphonenum,String areastart,String areaend,String createTimeStart,String createTimeEnd,String applyCurrentBusiness,int page,int pageSize);
	
	/**
	 * @Title: updateConsumerApply
	 * @Description: 后台审批修改
	 * @param userid 申请人id
	 * @throws:
	 * @time: 2018年6月30日 下午1:25:39
	 *//*
	void updateExperApply(String userid,String experid,String applyUserId,String applystatus,String fReason,String roledistinguish);*/

}
