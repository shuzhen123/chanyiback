package dianfan.service.advertorial.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.dao.mapper.advertorial.AdvertorialClassifyMapper;
import dianfan.entities.advertorialclassify.AdvertorialClassify;
import dianfan.entities.advertorialclassify.AdvertorialClassifyModel;
import dianfan.service.advertorial.AdvertorialClassifyService;

/** @ClassName AdvertorialClassifyServiceImpl
 * @Description 文章分类接口实现类
 * @author zwb
 * @date 2018年7月4日 下午2:57:39
 */ 
@Service
public class AdvertorialClassifyServiceImpl implements AdvertorialClassifyService{
	
	/**
	 * 文章类别dao
	 */
	@Autowired
	AdvertorialClassifyMapper advertorialClassifyMapper;

	/* (non-Javadoc)
	 * <p>Title: findAdvertorialClassify</p>
	 * <p>Description: 查询文章类别列表</p>
	 * @return List<AdvertorialClassify>
	 * link: @see dianfan.service.advertorialClassify.AdvertorialClassifyService#findAdvertorialClassify()
	 */ 
	@Override
	public List<AdvertorialClassifyModel> findAdvertorialClassifys() {
		return advertorialClassifyMapper.findAdvertorialClassifys();
	}

}
