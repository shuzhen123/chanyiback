package dianfan.entities.goods;

import java.sql.Timestamp;

/**
 * @ClassName GoodsApply
 * @Description 商品申请
 * @author cjy
 * @date 2018年7月23日 下午3:43:31
 */
public class GoodsApply {
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String goodsid; //varchar(50) NOT NULL COMMENT '商品id',
	private String goodsName; //varchar(50) NOT NULL COMMENT '商品名称',
	private String goodsPic; //varchar(50) NOT NULL COMMENT '商品图片',
	
	private String staffid; //varchar(50) DEFAULT NULL COMMENT '员工id',
	private String staffName; //varchar(50) DEFAULT NULL COMMENT '员工姓名',
	private String roleName; //varchar(50) DEFAULT NULL COMMENT '员工角色名称',
	
	private String applyStatus; //varchar(2) DEFAULT NULL COMMENT '申请状态（00:待审核 01：申请通过 02：未通过）',
	private String remark; //varchar(150) DEFAULT NULL COMMENT '备注',
	private String applyStaffid; //varchar(50) NOT NULL COMMENT '审核人员id',
	private String applyStaffName; //varchar(50) NOT NULL COMMENT '审核人员姓名',
	private Timestamp createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsPic() {
		return goodsPic;
	}
	public void setGoodsPic(String goodsPic) {
		this.goodsPic = goodsPic;
	}
	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApplyStaffid() {
		return applyStaffid;
	}
	public void setApplyStaffid(String applyStaffid) {
		this.applyStaffid = applyStaffid;
	}
	public String getApplyStaffName() {
		return applyStaffName;
	}
	public void setApplyStaffName(String applyStaffName) {
		this.applyStaffName = applyStaffName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "GoodsApply [id=" + id + ", goodsid=" + goodsid + ", goodsName=" + goodsName + ", goodsPic=" + goodsPic
				+ ", staffid=" + staffid + ", staffName=" + staffName + ", roleName=" + roleName + ", applyStatus="
				+ applyStatus + ", remark=" + remark + ", applyStaffid=" + applyStaffid + ", applyStaffName="
				+ applyStaffName + ", createTime=" + createTime + "]";
	}

}