package dianfan.entities.base;

import java.util.List;

/**
 * @ClassName Region
 * @Description 区域
 * @author sz
 * @date 2018年7月26日 上午11:17:11
 */
public class Regionss {

	private String id; //int(8) NOT NULL,
	private String name; //varchar(50) DEFAULT NULL COMMENT '名称',
	private int entkbn; //int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
	
	private List<Area> area; // 大区下面的区域

	@Override
	public String toString() {
		return "Region [id=" + id + ", name=" + name + ", entkbn=" + entkbn + ", area=" + area + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEntkbn() {
		return entkbn;
	}

	public void setEntkbn(int entkbn) {
		this.entkbn = entkbn;
	}

	public List<Area> getArea() {
		return area;
	}

	public void setArea(List<Area> area) {
		this.area = area;
	}
	
	
	
	
}
