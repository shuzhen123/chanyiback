package dianfan.dao.mapper.goods;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.goods.GoodsClassify;

/**
 * @ClassName GoodsClassifyMapper
 * @Description 商品分类dao
 * @author cjy
 * @date 2018年7月17日 下午3:40:02
 */
@Repository
public interface GoodsClassifyMapper {
	/**
	 * @Title: findGoodsClassifyList
	 * @Description: 获取商品分类信息列表
	 * @param used
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午3:55:59
	 */
	List<GoodsClassify> findGoodsClassifyList(@Param("used")String used);
	
	/**
	 * @Title: checkGoodsClassifyName
	 * @Description: 商品分类名称重复性检测
	 * @param goodsClassify 商品分类信息
	 * @param status 操作类型（0新增，1修改）
	 * @throws:
	 * @time: 2018年7月16日 上午11:49:43
	 */
	boolean checkGoodsClassifyName(@Param("gc") GoodsClassify goodsClassify, @Param("status") int status);
	
	/**
	 * @Title: addGoodsClassify
	 * @Description: 添加商品分类
	 * @param goodsClassify 商品分类信息
	 * @throws:
	 * @time: 2018年7月16日 上午11:48:09
	 */
	@Insert("insert into t_goods_classify (id, classify_parentid, classify_name, classify_name_en, classify_level, create_time, create_by, sort) values "
			+ "(replace(uuid(),'-',''), #{classifyParentid}, #{classifyName}, #{classifyNameEn}, #{classifyLevel}, now(), #{createBy}, #{sort})")
	void addGoodsClassify(GoodsClassify goodsClassify);

	/**
	 * @Title: editGoodsClassify
	 * @Description: 修改商品分类
	 * @param goodsClassify
	 * @throws:
	 * @time: 2018年7月16日 下午2:47:58
	 */
	void editGoodsClassify(GoodsClassify goodsClassify);

	/**
	 * @Title: delGoodsClassify
	 * @Description: 删除/禁用/启用 商品分类
	 * @param cids
	 * @throws:
	 * @time: 2018年7月16日 下午4:51:07
	 */
	void delGoodsClassify(@Param("id") String id, @Param("entkbn") int status, @Param("updateBy") String update_userid);

	/**
	 * @Title: getPClassifyid
	 * @Description: 获取上级分类id
	 * @param classifyid
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午3:03:02
	 */
	@Select("select classify_parentid from t_goods_classify where id=#{classifyid}")
	String getPClassifyid(String classifyid);

	/**
	 * @Title: checkGoodsClassifySort
	 * @Description: 分类排序号检测
	 * @param goodsClassify
	 * @return
	 * @throws:
	 * @time: 2018年7月31日 上午10:17:20
	 * @author cjy
	 */
	boolean checkGoodsClassifySort(GoodsClassify goodsClassify);

	/**
	 * @Title: findSubGoodsClassify
	 * @Description: 获取大分类下的所有子分类
	 * @param classifyid 分类id
	 * @return
	 * @throws:
	 * @time: 2018年8月1日 上午10:10:35
	 * @author cjy
	 */
	@Select("select id from t_goods_classify where id=#{classifyid} or classify_parentid=#{classifyid}")
	List<String> findSubGoodsClassify(String classifyid);
	
	/**
	 * @Title: cleanGoodsClassify
	 * @Description: 清空商品分类
	 * @param classifyid 分类id
	 * @throws:
	 * @time: 2018年8月1日 上午9:48:08
	 * @author cjy
	 */
	void cleanGoodsClassify(@Param("classifyids")List<String> classifyids, @Param("updateBy") String update_userid);


}
