package dianfan.dao.mapper.advertorial;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.advertorial.Advertorial;
import dianfan.entities.advertorial.AdvertorialModel;


/** @ClassName AdvertorialMapper
 * @Description 文章相关dao
 * @author zwb
 * @date 2018年6月28日 下午6:46:01
 */ 
@Repository
public interface AdvertorialMapper {
	
	/** @Title: findAdvertorialsByClassifyId
	 * @Description: 通过分类id查询文章
	 * @param classifyId
	 * @return List<Advertorial>
	 * @throws:
	 * @time: 2018年6月28日 下午6:46:34
	 */ 
	List<AdvertorialModel> findAdvertorialsByClassifyId(Map<String,Object> param);
	
	/** @Title: getAdvertorialsDetailById
	 * @Description: 根据文章id来查询文章详情
	 * @param id
	 * @return Advertorial
	 * @throws:
	 * @time: 2018年6月28日 下午8:29:19
	 */ 
	AdvertorialModel getAdvertorialDetailById(String id);
	
	/** @Title: findAdvertorialsCount
	 * @Description: 查询文类id下的文章的数量
	 * @param classify_id
	 * @return int
	 * @throws:
	 * @time: 2018年6月29日 下午6:44:12
	 */ 
	int findAdvertorialsCount(String classifyId);
	
	/** @Title: updateAdvertorialVisitCounts
	 * @Description: 更新文章浏览量
	 * @param map
	 * @throws:
	 * @time: 2018年7月3日 下午2:37:33
	 */ 
	void updateAdvertorialVisitCounts(Advertorial advertorial);
	
	/** @Title: getAdvertorialDetailAllById
	 * @Description: 查询文章所有的详情
	 * @param id
	 * @return
	 * @throws:
	 * @time: 2018年7月3日 下午3:06:23
	 */ 
	Advertorial getAdvertorialDetailAllById(String id); 
	
	/** @Title: updateAdvertorialThumbUp
	 * @Description: 更新点赞数量
	 * @param advertorial
	 * @throws:
	 * @time: 2018年7月3日 下午3:13:57
	 */ 
	void updateAdvertorialThumbUp(Advertorial advertorial);

	/**
	 * @Title: checkPraiseStatus
	 * @Description: 查看有没有点赞
	 * @param id
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年7月30日 上午10:00:20
	 * @author cjy
	 */
	@Select("select count(*) from t_advertorial_thumb_up where user_id=#{userid} and advertorial_id=#{aid} and entkbn=0")
	boolean checkPraiseStatus(@Param("aid")String id, @Param("userid")String userid);

	/**
	 * @Title: praiseAdvertorial
	 * @Description: 取消点赞
	 * @param id
	 * @param userid
	 * @throws:
	 * @time: 2018年7月30日 上午10:04:20
	 * @author cjy
	 */
	@Update("update t_advertorial_thumb_up set entkbn=9 where user_id=#{userid} and advertorial_id=#{aid}")
	void unpraiseAdvertorial(@Param("aid")String id, @Param("userid")String userid);

	/**
	 * @Title: praiseCount
	 * @Description: 点赞数减
	 * @param id
	 * @throws:
	 * @time: 2018年7月30日 上午10:05:50
	 * @author cjy
	 */
	@Update("update t_advertorial set thumb_up_num=thumb_up_num-1 where id=#{aid}")
	void praiseCountDec(String id);
	
	/*
	* @Title: praiseCount
	* @Description: 点赞数加减
	* @param id
	* @throws:
	* @time: 2018年7月30日 上午10:05:50
	* @author cjy
	*/
	@Update("update t_advertorial set thumb_up_num=thumb_up_num+1 where id=#{aid}")
	void praiseCountInc(String id);

	/**
	 * @Title: praiseAdvertorial
	 * @Description: 点赞
	 * @param id
	 * @param userid
	 * @throws:
	 * @time: 2018年7月30日 上午10:07:31
	 * @author cjy
	 */
	@Insert("insert into t_advertorial_thumb_up (id, user_id, advertorial_id, create_time, create_by) values "
			+ "(replace(uuid(),'-',''), #{userid}, #{aid}, now(), #{userid})")
	void praiseAdvertorial(@Param("aid")String id, @Param("userid")String userid);


}
