package dianfan.dao.mapper.advertorial;

import java.util.List;

import org.springframework.stereotype.Repository;

import dianfan.entities.advertorialclassify.AdvertorialClassifyModel;


/** @ClassName AdvertorialClassifyMapper
 * @Description 文章类别dao
 * @author zwb
 * @date 2018年6月28日 下午4:19:03
 */ 
@Repository
public interface AdvertorialClassifyMapper {
	
	/** @Title: findAdvertorialClassifys
	 * @Description: 查询文章类别列表
	 * @return List<AdvertorialClassify> 文章类别列表
	 * @throws:
	 * @time: 2018年6月28日 下午4:21:15
	 */ 
	List<AdvertorialClassifyModel> findAdvertorialClassifys();

}
