package dianfan.entities.goods;

import java.sql.Timestamp;

/**
 * @ClassTitle StaffGoods
 * @Description 员工绑定商品
 * @author cjy
 * @date 2018年7月23日 上午11:56:33
 */
public class StaffGoods {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String userid; //varchar(50) NOT NULL COMMENT '员工id',
	private String goodsid; //varchar(50) NOT NULL COMMENT '商品id',
	private String goodsTitle; //varchar(50) NOT NULL COMMENT '商品id',
	private Integer sort; //int(8) DEFAULT NULL COMMENT '排序',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "StaffGoods [id=" + id + ", userid=" + userid + ", goodsid=" + goodsid + ", goodsTitle=" + goodsTitle
				+ ", sort=" + sort + ", createTime=" + createTime + "]";
	}
}
