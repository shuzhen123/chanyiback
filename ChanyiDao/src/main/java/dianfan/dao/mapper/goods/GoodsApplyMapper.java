package dianfan.dao.mapper.goods;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.GoodsApply;


/**
 * @ClassName GoodsApplyMapper
 * @Description 商品申请dao
 * @author cjy
 * @date 2018年7月23日 下午3:16:34
 */
@Repository
public interface GoodsApplyMapper {

	/**
	 * @Title: getGoodsApplyCountByParam
	 * @Description: 根据筛选条件获取商品申请数量
	 * @param applyStatus 申请状态（00:待审核 01：申请通过 02：未通过）
	 * @param startTime 起始时间筛选
	 * @param endTime 截止时间筛选
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午3:18:08
	 * @author cjy
	 */
	int getGoodsApplyCountByParam(@Param("applyStatus")String applyStatus, @Param("startTime")String startTime, @Param("endTime")String endTime);

	/**
	 * @Title: findGoodsApplyListByParam
	 * @Description: 根据筛选条件获取商品申请列表
	 * @param pageStart 起始条数
	 * @param pageSize 偏移量
	 * @param applyStatus 申请状态（00:待审核 01：申请通过 02：未通过）
	 * @param startTime 起始时间筛选
	 * @param endTime 截止时间筛选
	 * @throws:
	 * @time: 2018年7月23日 下午3:42:22
	 * @author cjy
	 */
	List<GoodsApply> findGoodsApplyListByParam(@Param("pageStart")int pageStart, @Param("pageSize")int pageSize, @Param("applyStatus")String applyStatus, 
			@Param("startTime")String startTime, @Param("endTime")String endTime);

	/**
	 * @Title: goodsApplyApprove
	 * @Description: 商品申请审批
	 * @param applyid 申请id
	 * @param applyStatus 申请状态（01：申请通过 02：未通过）
	 * @param remark 备注
	 * @param approver 审批者id
	 * @throws:
	 * @time: 2018年7月23日 下午5:23:22
	 * @author cjy
	 */
	void goodsApplyApprove(@Param("applyid")String applyid, @Param("applyStatus")String applyStatus, @Param("remark")String remark, @Param("approver")String approver);

	/**
	 * @Title: getUserGoodsApply
	 * @Description: 获取商品申请详情
	 * @param applyid 商品申请id
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午5:33:55
	 * @author cjy
	 */
	@Select("select goods_id goodsid, staff_id staffid from t_goods_apply where id=#{applyid}")
	GoodsApply getUserGoodsApply(String applyid);

	/**
	 * @Title: getUserGoodsMaxSort
	 * @Description: 获取当前用户的商品最大排序值
	 * @param staffid
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 下午5:38:50
	 * @author cjy
	 */
	@Select("select ifnull(max(sort)+1,1) from t_goods_user where user_id=#{staffid} and entkbn=0")
	int getUserGoodsMaxSort(String staffid);
	
	/**
	 * @Title: addUserGoods
	 * @Description: 添加商品到用户商品表 
	 * @param ga 商品申请数据
	 * @throws:
	 * @time: 2018年7月23日 下午5:35:35
	 * @author cjy
	 */
	@Insert("insert into t_goods_user (id, user_id, goods_id, sort, create_time, create_by) values "
			+ "(replace(uuid(),'-',''), #{ga.staffid}, #{ga.goodsid}, #{sort}, now(), #{ga.applyStaffid})")
	void addUserGoods(@Param("ga")GoodsApply ga, @Param("sort")int sort);

	
}
