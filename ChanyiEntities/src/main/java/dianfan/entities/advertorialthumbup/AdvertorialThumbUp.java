package dianfan.entities.advertorialthumbup;

import java.util.Date;

/** @ClassName AdvertorialThumbUp
 * @Description 文章点赞表实体类
 * @author zwb
 * @date 2018年6月28日 下午6:09:58
 */ 
public class AdvertorialThumbUp {
	
	private String id;//主键id
	
	private String userId;//用户id
	
	private String advertorialId;//软文id
	
	private Date createTime;//创建时间
	
	private String createBy;//创建者
	
	private Date updateTime;//更新时间
	
	private String updateBy;//更新者
	
	private Integer entkbn;//数据有效区分
	
	private Integer version;//版本

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdvertorialId() {
		return advertorialId;
	}

	public void setAdvertorialId(String advertorialId) {
		this.advertorialId = advertorialId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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
		return "AdvertorialThumbUp [id=" + id + ", userId=" + userId + ", advertorialId=" + advertorialId
				+ ", createTime=" + createTime + ", createBy=" + createBy + ", updateTime=" + updateTime + ", updateBy="
				+ updateBy + ", entkbn=" + entkbn + ", version=" + version + "]";
	}
	
	
	
	

}
