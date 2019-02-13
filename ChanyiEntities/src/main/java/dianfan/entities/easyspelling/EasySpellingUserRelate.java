/**  
* @Title: EasySpellingUserRelate.java
* @Package dianfan.entities.easyspelling
* @Description: TODO
* @author yl
* @date 2018年7月9日 下午4:20:51
* @version V1.0  
*/ 
package dianfan.entities.easyspelling;

/** @ClassName EasySpellingUserRelate
 * @Description 
 * @author yl
 * @date 2018年7月9日 下午4:20:51
 */
public class EasySpellingUserRelate {
	
	private String id;
	private String easySpellingId;
	private String userId;
	private Integer entkbn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEasySpellingId() {
		return easySpellingId;
	}
	public void setEasySpellingId(String easySpellingId) {
		this.easySpellingId = easySpellingId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getEntkbn() {
		return entkbn;
	}
	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}
	@Override
	public String toString() {
		return "EasySpellingUserRelate [id=" + id + ", easySpellingId=" + easySpellingId + ", userId=" + userId
				+ ", entkbn=" + entkbn + "]";
	}
	
}
