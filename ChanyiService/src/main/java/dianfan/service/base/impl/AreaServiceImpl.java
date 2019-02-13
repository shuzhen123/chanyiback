package dianfan.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantECVIF;
import dianfan.dao.mapper.base.AreaMapper;
import dianfan.entities.base.Area;
import dianfan.service.base.AreaService;

/**
 * @ClassName AreaServiceImpl
 * @Description 城市地区服务实现
 * @author cjy
 * @date 2018年6月30日 下午3:34:51
 */
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;

	/*
	 * (non-Javadoc) <p>Title: findAllArea</p> <p>Description: 获取全部可用城市</p>
	 * 
	 * @return List<Area>城市列表 link: @see
	 * dianfan.service.base.AreaService#findAllArea()
	 */
	@Override
	@Cacheable(value = ConstantECVIF.ECV_AREA_LIST)
	// @CacheEvict(allEntries=true,value=ConstantECVIF.ECV_AREA_LIST) 清除数据库缓存
	public List<Area> findAllArea() {
		List<Area> allArea = areaMapper.findAllArea();
		return allArea;
	}

}
