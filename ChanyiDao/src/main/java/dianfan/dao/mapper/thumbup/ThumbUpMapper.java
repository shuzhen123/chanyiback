package dianfan.dao.mapper.thumbup;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dianfan.entities.advertorialthumbup.AdvertorialThumbUp;


/** @ClassName ThumbUpMapper
 * @Description 点赞dao
 * @author zwb
 * @date 2018年7月4日 下午2:48:56
 */ 
@Repository
public interface ThumbUpMapper {
	
	/** @Title: getAdvertorialThumbUp
	 * @Description: 通过参数查询文章点赞表
	 * @param param
	 * @return AdvertorialThumbUp
	 * @throws:
	 * @time: 2018年6月29日 下午4:50:15
	 */ 
	AdvertorialThumbUp getAdvertorialThumbUp(Map<String,Object> param);
	
	/** @Title: insertThumbUp
	 * @Description: 增加文章点赞表记录
	 * @param advertorialThumbUp
	 * @throws:
	 * @time: 2018年6月29日 下午4:50:45
	 */ 
	void insertThumbUp(AdvertorialThumbUp advertorialThumbUp);
	
	/** @Title: updataThumbUp
	 * @Description: 更新文章点赞记录(取消点赞)
	 * @param advertorialThumbUp
	 * @throws:
	 * @time: 2018年6月29日 下午4:51:02
	 */ 
	void updataThumbUp(AdvertorialThumbUp advertorialThumbUp);
	
	

}
