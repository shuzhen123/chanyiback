package dianfan.dao.mapper.content;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.banner.Banner;

/**
 * @ClassName BannerAdminService
 * @Description Banner相关 dao
 * @author sz
 * @date 2018年7月19日 下午1:28:34
 */
@Repository
public interface BannerAdminMapper {

	/**
	 * @Title: findBannerPicCount
	 * @Description: 获取首页轮播图列表数量
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午2:39:32
	 */
	@Select("SELECT COUNT(*) FROM t_index_banner WHERE entkbn != 9 ")
	int findBannerPicCount();

	
	/**
	 * @Title: findBannerPicList
	 * @Description: 获取首页轮播图列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午3:01:47
	 */
	List<Banner> findBannerPicList(Map<String, Object> param);


	/**
	 * @Title: getCheckPic
	 * @Description: 检测添加的图片，数据库中是否存在
	 * @param param 
	 * @return int
	 * @throws:
	 * @time: 2018年7月19日 下午4:40:53
	 */
	@Select("SELECT COUNT(*) FROM t_index_banner WHERE entkbn != 9 AND pic_addr = #{pic} ")
	int getCheckPic(Map<String, Object> param);


	/**
	 * @Title: addBannerPic
	 * @Description: 添加轮播图
	 * @param param
	 * @throws:
	 * @time: 2018年7月19日 下午5:04:54
	 */
	@Insert("INSERT INTO t_index_banner (id, title, content, pic_addr, ad_desc, create_by, update_time,sort) VALUES (REPLACE(uuid(),'-',''), #{title}, #{content}, #{pic} , #{desc}, #{userid}, NOW(),#{sort} )")
	void addBannerPic(Map<String, Object> param);


	/**
	 * @Title: getCheckPicOutOwn
	 * @Description: 检测添加的图片，数据库中是否存在,去除自身
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午6:25:35
	 */
	@Select(" SELECT COUNT(*) FROM t_index_banner WHERE entkbn != 9 AND pic_addr = #{pic} AND id != #{id} ")
	int getCheckPicOutOwn(Map<String, Object> param);


	/**
	 * @Title: updataBannerPic
	 * @Description: 更新图片信息
	 * @param param
	 * @throws:
	 * @time: 2018年7月19日 下午6:29:02
	 */
	@Update(" UPDATE t_index_banner SET title = #{title}, content = #{content}, pic_addr = #{pic}, ad_desc = #{desc}, update_time = NOW(), update_by = #{userid}, sort = #{sort}  WHERE id = #{id}  ")
	void updataBannerPic(Map<String, Object> param);


	/**
	 * @Title: delBannerPic
	 * @Description: 删除轮播图
	 * @param param
	 * @throws:
	 * @time: 2018年7月20日 上午9:27:37
	 */
	void delBannerPic(Map<String, Object> param);



}
