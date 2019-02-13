package dianfan.dao.mapper.easyspelling;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.easyspelling.EasySpelling;
import dianfan.entities.easyspelling.EasySpellingModel;
import dianfan.entities.easyspelling.EasySpellingParameter;
import dianfan.entities.easyspelling.EasySpellingUserRelate;

/** @ClassName EasyspellingMapper
 * @Description 易拼表dao
 * @author zwb
 * @date 2018年7月4日 下午2:40:44
 */ 
@Repository
public interface EasyspellingMapper {
	
	/** @Title: getEasySpellingList
	 * @Description: 查询易拼成团列表
	 * @param goodsId
	 * @return List<EasySpellingModel>
	 * @throws:
	 * @time: 2018年7月5日 上午9:29:46
	 */ 
	List<EasySpellingModel> getEasySpellingList(Map<String,Object> map);
	
	/** @Title: getEasySpellingListCount
	 * @Description: 查询易拼成团人员总数量
	 * @return Integer
	 * @throws:
	 * @time: 2018年7月5日 下午4:56:40
	 */ 
	int getEasySpellingListPeopleCount(String goodsId);
	
	/** @Title: getEasySpellingListCount
	 * @Description: 查询易拼发起的数量
	 * @param goodsId
	 * @return Integer
	 * @throws:
	 * @time: 2018年7月5日 下午5:34:25
	 */ 
	int getEasySpellingListCount(String goodsId);
	
	/**
	 * @Title: addEasySpelling
	 * @Description: 发起易拼
	 * @param es 
	 * @throws:
	 * @time: 2018年7月9日 下午3:09:19
	 */
	@Insert("insert into t_easy_spelling (id,user_id,easy_spelling_parameter_id,start_time,end_time,create_time,entkbn) values (#{id},#{userId},#{easySpellingParameterId},#{startTime},#{endTime},#{createTime},0)")
	void addEasySpelling(EasySpelling es);
	/**
	 * @Title: addEasySpellingUserRelate
	 * @Description: 添加易拼人员关联
	 * @param esur
	 * @throws:
	 * @time: 2018年7月9日 下午4:22:43
	 */
	@Insert("insert into t_easy_spelling_user_relate (id,easy_spelling_id,user_id,entkbn) values (replace(uuid(),'-',''),#{easySpellingId},#{userId},0)")
	void addEasySpellingUserRelate(EasySpellingUserRelate esur);
	
	@Update("update t_easy_spelling_user_relate set user_id=#{userId} where id=#{id}")
	void updateEasySpellingUserRelate(EasySpellingUserRelate esur);
	
	/**
	 * @Title: getEasySpellingParameter
	 * @Description: 获取易拼参数
	 * @param goodsid 商品id
	 * @return
	 * @throws:
	 * @time: 2018年7月9日 下午3:32:12
	 */
	EasySpellingParameter getEasySpellingParameter(String goodsid);
	
	/**
	 * @Title: getSpellingNum
	 * @Description: 获取拼团人数
	 * @param esid
	 * @return
	 * @throws:
	 * @time: 2018年7月10日 下午6:39:00
	 */
	@Select("select esp.limit_num as limitnum from t_easy_spelling_parameter esp inner join t_easy_spelling es on esp.id = es.easy_spelling_parameter_id where es.entkbn=0 and es.id=#{esid}")
	int getSpellingNum(String esid);
	
	/**
	 * @Title: getSpellingNum
	 * @Description: 获取已经拼团人数
	 * @param esid
	 * @return
	 * @throws:
	 * @time: 2018年7月10日 下午6:39:00
	 */
	@Select("select count(esur.id) as spellednum from t_easy_spelling_user_relate esur where esur.easy_spelling_id=#{esid}")
	int getUserSpellingNum(@Param("esid") String esid);
	
	/**
	 * @Title: getPersonSpellingNum
	 * @Description: 判断拼团的人是否是团长
	 * @param userid 用户id
	 * @return
	 * @throws:
	 * @time: 2018年7月10日 下午9:15:09
	 */
	@Select("select user_id from t_easy_spelling where id = #{esid}")
	String getPersonSpellingNum(@Param("esid") String esid);
	/**
	 * @Title: getSpellingPersonStatus
	 * @Description: 
	 * @param userid 用户id
	 * @param esid 拼团id
	 * @return
	 * @throws:
	 * @time: 2018年7月10日 下午9:23:58
	 */
	@Select("select count(*) from t_easy_spelling_user_relate where user_id=#{userid} and easy_spelling_id=#{esid}")
	int getSpellingPersonStatus(@Param("userid") String userid,@Param("esid") String esid);
	

}
