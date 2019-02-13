/**  
* @Title: CommissionServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月30日 下午12:43:50
* @version V1.0  
*/ 
package dianfan.service.our.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.our.CommissionMapper;
import dianfan.entities.commision.CommisionListModel;
import dianfan.entities.commision.CommisionModel;
import dianfan.entities.our.MoneyEntryExit;
import dianfan.models.ResultBean;
import dianfan.service.our.CommissionService;

/** @ClassName CommissionServiceImpl
 * @Description 用户佣金逻辑实现
 * @author yl
 * @date 2018年6月30日 下午12:43:50
 */
@Service
public class CommissionServiceImpl implements CommissionService{
	
	@Autowired
	private CommissionMapper commissionMapper;

	/* (non-Javadoc)
	 * <p>Title: findCommissionList</p>
	 * <p>Description:  佣金列表</p>
	 * @param userid 用户id
	 * @return 佣金列表
	 * link: @see dianfan.service.our.CommissionService#findCommissionList(java.lang.String)
	 */ 
	@Override
	public List<MoneyEntryExit> findUserMoneyEntryExit(String userid) {
		// TODO Auto-generated method stub
		List<MoneyEntryExit> mee = commissionMapper.findUserMoneyEntryExit(userid);
		return mee;
	}

	/* (non-Javadoc)
	 * <p>Title: findCommissionList</p>
	 * <p>Description: </p>
	 * @return
	 * link: @see dianfan.service.our.CommissionService#findCommissionList()
	 */ 
	@Override
	public ResultBean findCommissionList() {
		// TODO Auto-generated method stub
		Map<String,List<CommisionModel>> cmmap = new LinkedHashMap<String,List<CommisionModel>>();
		List<CommisionModel> cmlists = null;
		List<CommisionModel> cmlist = commissionMapper.findCommision();
		if (cmlist !=null && cmlist.size()>0) {		
			for (CommisionModel scene : cmlist) {
			if (cmmap.containsKey(scene.getScene())) {
				cmmap.get(scene.getScene()).add(scene);
			}else {
				cmlists =  new ArrayList<>();
				cmlists.add(scene);
				cmmap.put(scene.getScene(), cmlists);
			}
		}
	 }
	List<CommisionListModel> clmlist = new ArrayList<>();
	CommisionListModel clm = null;
	for (String in : cmmap.keySet()) {
		clm = new CommisionListModel();
		 List<CommisionModel> str = cmmap.get(in);//得到每个key多对用value的值
		 clm.setKey(in);
		 clm.setValue(str);
		 clmlist.add(clm);
	}
	return new ResultBean(clmlist);
  }

	/* (non-Javadoc)
	 * <p>Title: updateCommission</p>
	 * <p>Description: </p>
	 * @param id
	 * @param proportion
	 * link: @see dianfan.service.our.CommissionService#updateCommission(java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void updateCommission(String userid,String id, String proportion) {
		// TODO Auto-generated method stub
		CommisionModel cm = new CommisionModel();
		cm.setId(id);
		cm.setProportion(new BigDecimal(proportion));
		cm.setUpdateBy(userid);
		commissionMapper.updateCommision(cm);
		
	}
}
