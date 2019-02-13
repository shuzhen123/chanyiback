package dianfan.dao.mapper.goods;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.GoodsLabels;

/**
 * @ClassName GoodsLabelMapper
 * @Description 商品标签dao
 * @author cjy
 * @date 2018年7月17日 上午11:10:25
 */
@Repository
public interface GoodsLabelMapper {

	/**
	 * @Title: findGoodsLabelList
	 * @Description: 获取商品标签列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:17:36
	 */
	@Select("select id, label_name labelName, create_time createTime from t_goods_labels where entkbn=0 order by create_time desc")
	List<GoodsLabels> findGoodsLabelList();

	/**
	 * @Title: checkGoodsLabelName
	 * @Description: 商品标签名称重复性检测
	 * @param labelid 标签id
	 * @param labelName 标签名称
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:51:53
	 */
	boolean checkGoodsLabelName(@Param("labelid")String labelid, @Param("labelName")String labelName);

	/**
	 * @Title: addGoodsLabel
	 * @Description: 新增商品标签
	 * @param labelName 标签名称
	 * @param userid 添加者userid
	 * @throws:
	 * @time: 2018年7月17日 上午11:59:31
	 */
	@Insert("insert into t_goods_labels (id, label_name, create_time, create_by) values "
			+ "(replace(uuid(),'-',''), #{labelName}, now(), #{userid})")
	void addGoodsLabel(@Param("labelName")String labelName, @Param("userid")String userid);

	/**
	 * @Title: editGoodsLabel
	 * @Description: 商品标签修改
	 * @param labelid 标签id
	 * @param labelName 标签名称
	 * @param userid 添加者userid
	 * @throws:
	 * @time: 2018年7月17日 下午12:15:47
	 */
	@Update("update t_goods_labels set label_name=#{labelName}, update_by=#{userid}, version=version+1 where id=#{labelid}")
	void editGoodsLabel(@Param("labelid")String labelid, @Param("labelName")String labelName, @Param("userid")String userid);

	/**
	 * @Title: delGoodsLabel
	 * @Description: 商品标签删除
	 * @param labelids 商品标签id数组
	 * @param userid 更新者userid
	 * @throws:
	 * @time: 2018年7月17日 下午1:44:23
	 */
	void delGoodsLabel(@Param("labelids")String[] labelids, @Param("userid")String userid);

	void delGoodsLabelRelation(String[] labelids);

}
