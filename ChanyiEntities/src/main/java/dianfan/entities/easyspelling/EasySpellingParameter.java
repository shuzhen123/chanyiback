package dianfan.entities.easyspelling;

import java.sql.Timestamp;

/** @ClassName EasySpellingParameter
 * @Description 易拼参数实体类
 * @author zwb
 * @Timestamp 2018年7月4日 下午3:02:08
 */ 
public class EasySpellingParameter {
	
//	`id` varchar(50) NOT NULL COMMENT '主键id',
//	  `goods_id` varchar(50) NOT NULL COMMENT '商品id',
//	  `limit_num` int(8) NOT NULL COMMENT '人数限定',
//	  `start_time` Timestamptime DEFAULT NULL COMMENT '开始时间',
//	  `end_time` Timestamptime DEFAULT NULL COMMENT '结束时间',
//	  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
//	  `create_time` Timestamptime DEFAULT NULL COMMENT '创建时间',
//	  `upTimestamp_by` varchar(50) DEFAULT NULL COMMENT '更新者',
//	  `upTimestamp_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPTimestamp CURRENT_TIMESTAMP COMMENT '更新时间',
//	  `entkbn` int(1) DEFAULT '0' COMMENT '数据有效区分(0:数据有效1:数据无效9:逻辑删除)',
//	  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
	
	//主键id
	private String id;
	
	//商品id
	private String goodsId;
	
	//人数限定
	private Integer limitNum;
	
	//开始时间
	private Timestamp startTime;
	
	//结束时间
	private Timestamp endTime;
	
	//创建者
	private String createBy;
	
	//创建时间
	private Timestamp create_time;
	
	//更新者
	private String update_by;
	
	//更新时间
	private Timestamp update_time;
	
	//数据有效区分(0:数据有效1:数据无效9:逻辑删除)
	private Integer entkbn;
	
	//版本号
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public Integer getEntkbn() {
		return entkbn;
	}

	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "EasySpellingParameter [id=" + id + ", goodsId=" + goodsId + ", limitNum=" + limitNum + ", startTime="
				+ startTime + ", endTime=" + endTime + ", createBy=" + createBy + ", create_time=" + create_time
				+ ", update_by=" + update_by + ", update_time=" + update_time + ", entkbn=" + entkbn + ", version="
				+ version + "]";
	}

}
