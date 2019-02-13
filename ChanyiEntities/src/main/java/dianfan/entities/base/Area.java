package dianfan.entities.base;

import java.util.List;

/**
 * @ClassName AreaList
 * @Description 城市服务列表
 * @author cjy
 * @date 2018年6月30日 下午3:36:02
 */
public class Area {
	private String areaCode; // 地区代码
	private String areaName; // 地区名称
	private String regionId; // 区域ID
	private List<Area> lowerCity; //下级城市列表
	
	
	
	
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public List<Area> getLowerCity() {
		return lowerCity;
	}
	public void setLowerCity(List<Area> lowerCity) {
		this.lowerCity = lowerCity;
	}
	@Override
	public String toString() {
		return "Area [areaCode=" + areaCode + ", areaName=" + areaName + ", regionId=" + regionId + ", lowerCity="
				+ lowerCity + "]";
	}


}
