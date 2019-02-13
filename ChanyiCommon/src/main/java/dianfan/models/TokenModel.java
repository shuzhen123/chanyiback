package dianfan.models;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * 
 * @author admin
 * @date 2017/11/17.
 */
public class TokenModel {

	// 用户id
	private String userid;

	// 随机生成的uuid
	private String token;
	public TokenModel(String userid, String token) {
		this.userid = userid;
		this.token = token;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
