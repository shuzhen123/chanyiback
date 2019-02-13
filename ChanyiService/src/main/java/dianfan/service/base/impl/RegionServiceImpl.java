package dianfan.service.base.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.dao.mapper.base.RegionMapper;
import dianfan.entities.base.Regionss;
import dianfan.models.ResultBean;
import dianfan.service.base.RegionService;
import dianfan.util.StringUtility;
import net.sf.ehcache.search.expression.And;

/**
 * @ClassName RegionService
 * @Description 区域管理相关 后台
 * @author sz
 * @date 2018年7月26日 上午10:45:29
 */
@Service
public class RegionServiceImpl implements RegionService {

	
	/**
	 * 注入： #RegionMapper
	 */
	@Autowired
	private RegionMapper regionMapper;

	
	
	/*
	 * (non-Javadoc)
	 * <p>Title: findRegionList</p>
	 * <p>Description: 获取大区列表 </p>
	 * @return
	 * link: @see dianfan.service.base.RegionService#findRegionList()
	 */
	@Override
	@SystemServiceLog(method = "findRegionList",description = "获取大区列表 ")
	public ResultBean findRegionList() {
		// 创建返回参数模型
		Map<String, Object> data = new HashMap<>();
		
		// 获取大区列表数量
		int count = regionMapper.findRegionListCount();
		// 添加大区数量
		data.put("count", count);
		if  (count == 0) {
			data.put("regionList", new ArrayList<>());
			// 返回
			return new ResultBean(data);
		}
		// 获取大区列表
		List<Regionss> regionList = regionMapper.findRegionList();
		// 添加大区列表
		data.put("regionList", regionList);
		// 成功返回
		return new ResultBean(data);
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: updataRegionList</p>
	 * <p>Description: 更新大区和省关系</p>
	 * @param regionid 大区id
	 * @param codeids 省 ids
	 * @return
	 * link: @see dianfan.service.base.RegionService#updataRegionList(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "updataRegionList",description = "更新大区和省关系")
	public ResultBean updataRegionList(String regionid, String codeids) {
		// 整理IDs
		List<String> list = new ArrayList<>();
		if (!StringUtility.isNull(codeids)) {
			list = Arrays.asList(codeids.split(","));
		}
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("regionid", regionid);
		// 先将区域表中，次regionid 下的区域先全部制空
		regionMapper.emptyRegion(regionid);
		// 库 修改操作
		if (list != null && list.size() > 0) {
			regionMapper.updataRegion(param);
		}
		//更新大区
		// 成功返回
		return new ResultBean();
	}
	
	
	
	
}
