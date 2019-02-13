package dianfan.models;

public class ResultBean {
	
	private String code;//返回码
	private String msg;//返回信息
	private Object data;//返回的实体数据
	
	public ResultBean() {
		this.code = "200";
		this.msg = "OK";
	}
	
	public ResultBean(Object data) {
		this.code = "200";
		this.msg = "OK";
		this.data = data;
	}
	
	public ResultBean(String code,String msg,Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public ResultBean(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toString() {
		return "ResultBean[code="+code+",msg="+msg+",data="+data+"]";
	}
	
	

}
