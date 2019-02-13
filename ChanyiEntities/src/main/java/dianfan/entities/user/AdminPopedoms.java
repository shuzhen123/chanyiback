package dianfan.entities.user;

import java.util.List;

/**
 * @ClassName AdminPopedom
 * @Description 权限管理 自用接口 
 * @author sz
 * @date 2018年7月24日 上午10:06:57
 */
public class AdminPopedoms {
	
	private String popedomid;//varchar(50) NOT NULL COMMENT '权限Id ',
	private String popedomname;//varchar(100) DEFAULT NULL COMMENT '权限名称',
	private String popedomfatherid;//varchar(50) DEFAULT NULL COMMENT '权限父ID ',
	private String popedomurl;//varchar(100) DEFAULT NULL COMMENT '树的连接路径',
	private Integer sort;//int(8) DEFAULT NULL COMMENT '排序',
	private String kind;//varchar(2) DEFAULT NULL COMMENT '01：管理员 02：易盟',
	
	private List<AdminPopedoms>  lowPopedom ; // 子权限
	
	public String getPopedomid() {
		return popedomid;
	}
	public void setPopedomid(String popedomid) {
		this.popedomid = popedomid;
	}
	public String getPopedomname() {
		return popedomname;
	}
	public void setPopedomname(String popedomname) {
		this.popedomname = popedomname;
	}
	public String getPopedomfatherid() {
		return popedomfatherid;
	}
	public void setPopedomfatherid(String popedomfatherid) {
		this.popedomfatherid = popedomfatherid;
	}
	public String getPopedomurl() {
		return popedomurl;
	}
	public void setPopedomurl(String popedomurl) {
		this.popedomurl = popedomurl;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public List<AdminPopedoms> getLowPopedom() {
		return lowPopedom;
	}
	public void setLowPopedom(List<AdminPopedoms> lowPopedom) {
		this.lowPopedom = lowPopedom;
	}
	@Override
	public String toString() {
		return "AdminPopedoms [popedomid=" + popedomid + ", popedomname=" + popedomname + ", popedomfatherid="
				+ popedomfatherid + ", popedomurl=" + popedomurl + ", sort=" + sort + ", kind=" + kind + ", lowPopedom="
				+ lowPopedom + "]";
	}
	
	
	
	

}
