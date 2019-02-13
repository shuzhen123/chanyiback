package dianfan.constant;

/**
 * @ClassName ResultApiMsg
 * @Description 前端接口返回码
 * @author cjy
 * @date 2018年6月28日 下午6:52:37
 */
public class ResultApiMsg {
	public static final String C_200 = "OK";
	public static final String C_500 = "服务器忙";
	public static final String C_501 = "参数错误";
	public static final String C_404 = "服务器不存在该资源";

	public static final String C_900 = "文件资源不存在";

	public static final String C_001 = "登录信息已过期，请重新登录";
	public static final String C_002 = "输入的手机号码格式不正确";

	public static final String C_4001 = "该用户不存在";
	public static final String C_4002 = "code为空";
	public static final String C_4004 = "输入的原密码不正确";
	public static final String C_4005 = "输入的手机号码格式不正确或密码为空";
	public static final String C_4006 = "输入的手机号码或密码错误";
	public static final String C_4007 = "该手机号码已经注册，请直接登录";
	public static final String C_4008 = "您输入的手机号码格式不正确";
	public static final String C_4009 = "该用户已存在，请直接登录";
	public static final String C_4010 = "短信发送失败";
	public static final String C_4011 = "您输入的短信验证码不正确";
	public static final String C_4012 = "请输入完整的收货地址信息";
	public static final String C_4013 = "该收货地址不存在";
	public static final String C_4021 = "您已经绑定过其他微信账号";
	public static final String C_4022 = "您的账号已禁用";

	public static final String C_4014 = "暂无商品规格";
	public static final String C_4015 = "暂无商品价格";

	public static final String C_4016 = "该订单已经被删除，请重新下单";
	public static final String C_4017 = "支付失败";
	public static final String C_4018 = "未知的支付方式";
	public static final String C_4019 = "未知的支付类型";

	public static final String C_4100 = "该商品或已被下架";
	public static final String C_4102 = "当前订单不可以被关闭";
	public static final String C_4108 = "当前订单不可以操作";

	public static final String C_4105 = "输入的收件人姓名过长";
	public static final String C_4106 = "输入的收货详细地址过长";
	public static final String C_4107 = "无法绑定关系";
	public static final String C_4109 = "微信信息获取失败,登陆失败";

	public static final String C_2001 = "用户不存在";
	public static final String C_2009 = "您输入的身份证号码格式不正确";
	public static final String C_2013 = "您输入的邮箱格式不正确";
	public static final String C_2014 = "拼团活动已结束";
	public static final String C_2012 = "该团拼单已结束";
	public static final String C_2015 = "您输入的银行卡号不正确";
	public static final String C_2016 = "支付金额不正确";
	public static final String C_2017 = "该团拼团人数已满";
	public static final String C_2018 = "您已经是该拼团的成员";
	public static final String C_2019 = "单件商品的数量不能超过999";
	public static final String C_2020 = "昵称不能超过50个字符";
	public static final String C_2021 = "申请人姓名不能超过25个字符";
	public static final String C_2022 = "地址不能超过45个字符";
	public static final String C_2023 = "真实姓名不能超过64个字符";
	public static final String C_2024 = "位置不能超过65个字符";
	public static final String C_2025 = "图片地址不能超过20000个字符";
	public static final String C_2026 = "反馈内容不能超过256个字符";
	public static final String C_2027 = "公司名称不能超过25个字符";
	public static final String C_2028 = "注册资金不能超过25个字符";
	public static final String C_2029 = "供应品类不能超过64个字符";
	public static final String C_2030 = "合作案例不能超过110个字符";
	public static final String C_2031 = "联系人不能超过10个字符";
	public static final String C_2032 = "申请店主营业务不能超过75个字符";
	public static final String C_2034 = "您不是导购";
	public static final String C_2035 = "已经是最低价格了";
	public static final String C_2036 = "超出了优惠的最大值";
	public static final String C_2037 = "该订单不可享受优惠(原因:下单人的角色不是普通人)";
	public static final String C_2038 = "申请人姓名不能超过25个字符";
	public static final String C_2039 = "商品规格已发生改变";

	public static final String C_3000 = "您已经点赞过";
	public static final String C_3001 = "没有点赞过";
	public static final String C_3002 = "该文章已经被删除";
	public static final String C_3005 = "该文章已经被删除";

	/* 一下返回码仅供京东物流使用 */
	// public static final String C_7000 = "寄件人手机和座机必须填一个";
	// public static final String C_7001 = "收货人手机和座机必须填一个";
	// public static final String C_7002 = "易碎标志填写错误，请填写1（是）或2（否）";

}
