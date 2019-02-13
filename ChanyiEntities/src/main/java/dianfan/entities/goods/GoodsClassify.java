package dianfan.entities.goods;

import java.util.List;

/**
 * @ClassName GoodsClassify
 * @Description 商品分类 实体 (两层嵌套实体)
 * @author sz
 * @date 2018年7月2日 上午11:21:23
 */
public class GoodsClassify {
	
	private String id; //varchar(50) NOT NULL COMMENT '主键id',
	private String classifyParentid; //varchar(50) DEFAULT NULL COMMENT '商品父节点分类id',
	private String classifyName; //varchar(50) DEFAULT NULL COMMENT '商品分类名称',
	private String classifyNameEn; //varchar(150) DEFAULT NULL COMMENT '商品分类英文名称',
	private String classifyLevel; //varchar(50) DEFAULT NULL COMMENT '分类等级',
	private String createTime; //datetime DEFAULT NULL COMMENT '创建时间',
	private String createBy; //varchar(50) DEFAULT NULL COMMENT '创建者',
	private String updateBy; //varchar(50) DEFAULT NULL COMMENT '更新者',
	private Integer sort; //int(8) DEFAULT NULL COMMENT '排序',
	private Integer entkbn; //int(8) DEFAULT NULL COMMENT '数据有效区分(0:数据有效1:禁用9:逻辑删除)',
	
	private List<GoodsClassify> lowerClassify; // 下级分类实体

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassifyParentid() {
		return classifyParentid;
	}

	public void setClassifyParentid(String classifyParentid) {
		this.classifyParentid = classifyParentid;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getClassifyNameEn() {
		return classifyNameEn;
	}

	public void setClassifyNameEn(String classifyNameEn) {
		this.classifyNameEn = classifyNameEn;
	}

	public String getClassifyLevel() {
		return classifyLevel;
	}

	public void setClassifyLevel(String classifyLevel) {
		this.classifyLevel = classifyLevel;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getEntkbn() {
		return entkbn;
	}

	public void setEntkbn(Integer entkbn) {
		this.entkbn = entkbn;
	}

	public List<GoodsClassify> getLowerClassify() {
		return lowerClassify;
	}

	public void setLowerClassify(List<GoodsClassify> lowerClassify) {
		this.lowerClassify = lowerClassify;
	}

	@Override
	public String toString() {
		return "GoodsClassify [id=" + id + ", classifyParentid=" + classifyParentid + ", classifyName=" + classifyName
				+ ", classifyNameEn=" + classifyNameEn + ", classifyLevel=" + classifyLevel + ", createTime="
				+ createTime + ", createBy=" + createBy + ", updateBy=" + updateBy + ", sort=" + sort + ", entkbn="
				+ entkbn + ", lowerClassify=" + lowerClassify + "]";
	}
	
}
