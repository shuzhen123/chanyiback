/**  
* @Title: UserRoleModel.java
* @Package dianfan.entities.role
* @Description: TODO
* @author yl
* @date 2018年7月24日 上午11:03:51
* @version V1.0  
*/ 
package dianfan.entities.role;

import java.sql.Timestamp;

/** @ClassName UserRoleModel
 * @Description 
 * @author yl
 * @date 2018年7月24日 上午11:03:51
 */
public class BgUserRoleModel {
	
	private String id;
	private String userid;
	private String roleid;
	private String descption;
	private String nickName;
	private String sex;
	private String avatarUrl;
	private String telno;
	private Timestamp createTime;
	private String createTimeStart;
	private String createTimeEnd;
	private Integer start;
	private Integer offset;
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
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getDescption() {
		return descption;
	}
	public void setDescption(String descption) {
		this.descption = descption;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	@Override
	public String toString() {
		return "BgUserRoleModel [id=" + id + ", userid=" + userid + ", roleid=" + roleid + ", descption=" + descption
				+ ", nickName=" + nickName + ", sex=" + sex + ", avatarUrl=" + avatarUrl + ", telno=" + telno
				+ ", createTime=" + createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", start=" + start + ", offset=" + offset + "]";
	}

}
