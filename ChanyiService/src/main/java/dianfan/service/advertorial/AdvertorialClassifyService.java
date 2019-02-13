package dianfan.service.advertorial;

import java.util.List;

import dianfan.entities.advertorialclassify.AdvertorialClassifyModel;


/** @ClassName AdvertorialClassifyService
 * @Description 文章类别接口
 * @author zwb
 * @date 2018年6月28日 下午4:38:55
 */ 
public interface AdvertorialClassifyService {
	
	/** @Title: findAdvertorialClassify
	 * @Description: 查询文章类别列表
	 * @return List<AdvertorialClassify> 文章类别列表
	 * @throws:
	 * @time: 2018年6月28日 下午4:44:29
	 */ 
	List<AdvertorialClassifyModel> findAdvertorialClassifys();

}
