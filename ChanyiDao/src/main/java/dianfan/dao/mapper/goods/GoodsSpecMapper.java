package dianfan.dao.mapper.goods;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.GoodsAttr;
import dianfan.entities.goods.GoodsPrice;
import dianfan.entities.goods.GoodsSpec;

/**
 * @ClassName GoodsSpecMapper
 * @Description 商品规格dao
 * @author cjy
 * @date 2018年7月3日 下午2:50:14
 */
@Repository
public interface GoodsSpecMapper {

	/**
	 * @Title: findGoodsSpecByGoodsid
	 * @Description: 根据商品id获取规格
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午2:54:48
	 */
	@Select("select id, spec_name specName, spec_catagory specCatagory from t_goods_spec where goods_id=#{goodsid} and entkbn=0 order by spec_catagory,spec_name")
	List<GoodsSpec> findGoodsSpecByGoodsid(String goodsid);
	
	/**
	 * @Title: findGoodsPriceByGoodsid
	 * @Description: 根据商品id获取商品价格详情
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午3:10:19
	 */
	@Select("select id, spec_ids specIds, goods_pic goodsPic, price , price_easy_spelling priceEasySpelling from t_goods_price where goods_id=#{goodsid} and entkbn=0")
	List<GoodsPrice> findGoodsPriceByGoodsid(String goodsid);

	/* *****************后台*************** */
	
	/**
	 * @Title: findGoodsAttrList
	 * @Description: 获取商品属性列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午4:51:42
	 */
	@Select("select id, param_key paramKey, param_name paramName from t_goods_attributes where entkbn=0 order by create_time desc")
	List<GoodsAttr> findGoodsAttrList();

	/**
	 * @Title: checkGoodsAttrName
	 * @Description: 商品属性重复性检测
	 * @param attrid 商品属性id
	 * @param paramKey 商品属性名
	 * @param paramName 商品属性值
	 * @throws:
	 * @time: 2018年7月17日 下午5:25:55
	 */
	boolean checkGoodsAttrName(@Param("attrid")String attrid, @Param("paramKey")String paramKey, @Param("paramName")String paramName);

	/**
	 * @Title: addGoodsAttr
	 * @Description: 新增商品属性
	 * @param paramKey 商品属性名
	 * @param paramName 商品属性值
	 * @param userid 添加者id
	 * @throws:
	 * @time: 2018年7月17日 下午5:53:24
	 */
	@Insert("insert into t_goods_attributes (id, param_key, param_name, create_time, create_by) values "
			+ "(replace(uuid(),'-',''), #{paramKey}, #{paramName}, now(), #{userid})")
	void addGoodsAttr(@Param("paramKey")String paramKey, @Param("paramName")String paramName, @Param("userid")String userid);

	/**
	 * @Title: editGoodsAttr
	 * @Description: 修改商品属性
	 * @param attrid 商品属性id
	 * @param paramKey 商品属性名
	 * @param paramName 商品属性值
	 * @param userid 修改者id
	 * @throws:
	 * @time: 2018年7月17日 下午5:56:20
	 */
	@Update("update t_goods_attributes set param_key=#{paramKey}, param_name=#{paramName}, update_by=#{userid}, version=version+1 where id=#{attrid}")
	void editGoodsAttr(@Param("attrid")String attrid, @Param("paramKey")String paramKey, @Param("paramName")String paramName, @Param("userid")String userid);

	/**
	 * @Title: delGoodsAttr
	 * @Description: 删除商品属性
	 * @param attrids 商品属性id数组
	 * @param userid 修改者id
	 * @throws:
	 * @time: 2018年7月17日 下午6:13:40
	 */
	void delGoodsAttr(@Param("attrids")String[] attrids, @Param("userid")String userid);
	void delGoodsAttrRelation(String[] attrids);

	
}
