package dianfan.entities;

import java.sql.Timestamp;

/**
 * 系统日志表
 * 
 * @author Administrator
 *
 */
public class SysLogs {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 方法
	 */
	private String method;
	/**
	 * log类型
	 */
	private Long logtype;
	/**
	 * 请求ip
	 */
	private String requestip;
	/**
	 * 参数
	 */
	private String params;
	/**
	 * 创建者id
	 */
	private String createid;
	/**
	 * 创建时间
	 */
	private Timestamp createtime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @return the logtype
	 */
	public Long getLogtype() {
		return logtype;
	}

	/**
	 * @return the requestip
	 */
	public String getRequestip() {
		return requestip;
	}

	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @return the createid
	 */
	public String getCreateid() {
		return createid;
	}

	/**
	 * @return the createtime
	 */
	public Timestamp getCreatetime() {
		return createtime;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @param logtype
	 *            the logtype to set
	 */
	public void setLogtype(Long logtype) {
		this.logtype = logtype;
	}

	/**
	 * @param requestip
	 *            the requestip to set
	 */
	public void setRequestip(String requestip) {
		this.requestip = requestip;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * @param createid
	 *            the createid to set
	 */
	public void setCreateid(String createid) {
		this.createid = createid;
	}

	/**
	 * @param createtime
	 *            the createtime to set
	 */
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SysLogs [id=" + id + ", description=" + description + ", method=" + method + ", logtype=" + logtype
				+ ", requestip=" + requestip + ", params=" + params + ", createid=" + createid + ", createtime="
				+ createtime + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createid == null) ? 0 : createid.hashCode());
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((logtype == null) ? 0 : logtype.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		result = prime * result + ((requestip == null) ? 0 : requestip.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysLogs other = (SysLogs) obj;
		if (createid == null) {
			if (other.createid != null)
				return false;
		} else if (!createid.equals(other.createid))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logtype == null) {
			if (other.logtype != null)
				return false;
		} else if (!logtype.equals(other.logtype))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		if (requestip == null) {
			if (other.requestip != null)
				return false;
		} else if (!requestip.equals(other.requestip))
			return false;
		return true;
	}

}