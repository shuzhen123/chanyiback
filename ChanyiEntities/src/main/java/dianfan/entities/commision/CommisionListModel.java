/**  
* @Title: CommisionListModel.java
* @Package dianfan.entities.commision
* @Description: TODO
* @author yl
* @date 2018年7月26日 下午6:07:08
* @version V1.0  
*/ 
package dianfan.entities.commision;

import java.util.List;

/** @ClassName CommisionListModel
 * @Description 
 * @author yl
 * @date 2018年7月26日 下午6:07:08
 */
public class CommisionListModel {
	
	private String key;
	private List<CommisionModel> value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<CommisionModel> getValue() {
		return value;
	}
	public void setValue(List<CommisionModel> value) {
		this.value = value;
	}
	
	

}
