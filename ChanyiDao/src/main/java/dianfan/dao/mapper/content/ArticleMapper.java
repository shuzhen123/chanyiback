package dianfan.dao.mapper.content;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.advertorial.Advertorial;
import dianfan.entities.banner.AdvertorialClassifyModels;

/**
 * @ClassName ArticleMapper
 * @Description 文章管理dao
 * @author sz
 * @date 2018年7月20日 上午9:48:05
 */
@Repository
public interface ArticleMapper {

	/**
	 * @Title: findArticleCount
	 * @Description: 获取文章分类列表数量
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 上午10:09:35
	 */
	@Select(" SELECT COUNT(*) FROM t_advertorial_classify WHERE entkbn != 9 ")
	int findClassifyCount();

	
	/**
	 * @Title: findClassifyList
	 * @Description: 获取文章分类列表
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 上午10:16:06
	 */
	List<AdvertorialClassifyModels> findClassifyList(String desc);


	/**
	 * @Title: getCheckClassify
	 * @Description: 检查分类 名称是否存在
	 * @param name 分类的名称
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午12:00:39
	 */
	@Select(" SELECT COUNT(*) FROM t_advertorial_classify WHERE classify_name = #{name} and entkbn != 9 ")
	int getCheckClassify(String name);

	/**
	 * @Title: getCheckClassifyOwn
	 * @Description: 检查分类 名称是否存在,去除自己
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午1:28:12
	 */
	@Select(" SELECT COUNT(*) FROM t_advertorial_classify WHERE classify_name = #{name} and classify_id != #{id} and entkbn != 9 ")
	int getCheckClassifyOwn(Map<String, Object> param);

	/**
	 * @Title: addClassifyList
	 * @Description: 添加文章分类
	 * @param param 
	 * @throws:
	 * @time: 2018年7月20日 下午12:37:34
	 */
	@Insert("INSERT INTO t_advertorial_classify (classify_id, classify_name, classify_nav_sort, create_time, create_by) "
			+ "VALUES (REPLACE(uuid(),'-',''), #{name} ,  #{sort} ,  NOW() ,  #{userid} )")
	void addClassifyList(Map<String, Object> param);


	/**
	 * @Title: updataClassify
	 * @Description: 更新分类
	 * @param param
	 * @throws:
	 * @time: 2018年7月20日 下午1:41:38
	 */
	@Update(" UPDATE t_advertorial_classify SET classify_name = #{name} , classify_nav_sort = #{sort} , update_time = NOW() , update_by = #{userid} WHERE classify_id = #{id} ")
	void updataClassify(Map<String, Object> param);


	/**
	 * @Title: updataEditClassifyType
	 * @Description: 修改分类状态
	 * @param param 
	 * @throws:
	 * @time: 2018年7月20日 下午2:13:06
	 */
	void updataEditClassifyType(Map<String, Object> param);


	/**
	 * @Title: findAticleList
	 * @Description: 获取文章数量
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午3:04:07
	 */
	int findAticleListCount();


	/**
	 * @Title: findAticleList
	 * @Description: 获取文章列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午3:17:26
	 */
	List<Advertorial> findAticleList(Map<String, Object> param);


	/**
	 * @Title: getCheckAticleRepetition
	 * @Description: 添加文章检测是否重名
	 * @param title
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午5:01:47
	 */
	@Select(" SELECT COUNT(*) FROM t_advertorial WHERE title = #{title} and entkbn != 9 ")
	int getCheckAticleRepetition(String title);


	/**
	 * @Title: addAticle
	 * @Description: 添加文章
	 * @param param
	 * @throws:
	 * @time: 2018年7月20日 下午5:14:30
	 */
	@Insert(" INSERT INTO t_advertorial (id, classify_id, title, content, pic_addr ,sort ,create_time, create_by,thumb_up_num_n,visit_counts,entkbn)  "
			+ "VALUES (#{id}, #{classify}, #{title}, #{content}, #{pic}, #{sort}, NOW(), #{userid}, #{numN},0,#{entkbn}) ")
	void addAticle(Map<String, Object> param);


	/**
	 * @Title: getCheckAticleRepetitionButMe
	 * @Description: 添加文章检测是否重名
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午9:47:58
	 */
	@Select(" SELECT COUNT(*) FROM t_advertorial WHERE title = #{title} and id != #{id} and entkbn != 9 ")
	int getCheckAticleRepetitionButMe(Map<String, Object> param);


	/**
	 * @Title: updatAticle
	 * @Description: 更新文章
	 * @param param
	 * @throws:
	 * @time: 2018年7月23日 上午9:50:54
	 */
	@Update(" UPDATE t_advertorial SET classify_id = #{classify} ,  title = #{title} , content = #{content} , pic_addr = #{pic} , thumb_up_num_n = #{numN} , sort = #{sort}, update_time = NOW(), update_by = #{userid} WHERE id = #{id} ")
	void updatAticle(Map<String, Object> param);


	/**
	 * @Title: delAticle
	 * @Description: 修改文章的状态
	 * @param param
	 * @throws:
	 * @time: 2018年7月23日 上午10:10:47
	 */
	void delAticle(Map<String, Object> param);

}
