package dianfan.entities.easyspelling;

import java.util.Date;

/** @ClassName EasySpellingModel
 * @Description 易拼返回model类
 * @author zwb
 * @date 2018年7月4日 下午3:01:18
 */ 
public class EasySpellingModel {
	
	//发起表id
	private String esId;
	
	//剩余人数
	private Integer surplusCount;
	
	//发起人id
	private String userId;
	
	//昵称
	private String nickName;
	
	//头像
	private String avatarUrl;
	
	//结束时间
	private Date endTime;

	public String getEsId() {
		return esId;
	}

	public void setEsId(String esId) {
		this.esId = esId;
	}

	public Integer getSurplusCount() {
		return surplusCount;
	}

	public void setSurplusCount(Integer surplusCount) {
		this.surplusCount = surplusCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "EasySpellingModel [esId=" + esId + ", surplusCount=" + surplusCount + ", userId=" + userId
				+ ", nickName=" + nickName + ", avatarUrl=" + avatarUrl + ", endTime=" + endTime + "]";
	}

	

	
	
	
	
	
	
	
	
	
	

}
