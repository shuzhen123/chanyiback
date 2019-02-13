package dianfan.constant;

/**
 * 
 * @Title: ConstantIF.java
 * @Package dianfan.constant
 * @Description: hardcode
 * @author Administrator
 * @date 2018年5月11日 下午2:24:04
 * @version V1.0
 */
public interface ConstantIF {
	/** 设置登录认证Token过期时间(s) = 7天 */
	public final static long TOKEN_EXPIRES_SECONDS = 604800L;
	/** pc_session_key */
	public final static String PC_SESSION_KEY = "pc_session_key";

	/** accesstoken */
	public final static String ACCESSTOKEN = "accesstoken";
	/** encoding 编码 */
	public final static String ENCODEING = "encoding";

	/** 编号类型【条形码:IMGBAR_PNG 二维码:IMGQR_PNG】 */
	public static final String IMGBAR_PNG = "png";
	/** 编号类型【条形码:IMGBAR_PNG 二维码:IMGQR_PNG】 */
	public static final String IMGQR_PNG = "png";

	/** 优惠券时间偏移量 */
	public static final int CUOPON_DAY = 30;

	/** 分页偏移量 */
	public final static Integer PAGE_OFFSET = 10;

	/** 后台接口-请求起始页 */
	public final static String PAGE = "page";
	public final static String PAGE_START_STR = "1";
	/** 后台接口-分页偏移量 */
	public final static String PAGE_SIZE = "pageSize";
	public final static String PAGE_OFFSET_STR = "10";

	/** 消费商申请设置开关 */
	public static final Integer APPLY_FLAG = 1;
	/** 申请状态 01通过 02-未通过03待审核 */
	public static final String APPLY_STATUS1 = "01";
	/** 申请状态 01通过 02-未通过03待审核 */
	public static final String APPLY_STATUS2 = "02";
	/** 申请状态 01通过 02-未通过03待审核 */
	public static final String APPLY_STATUS3 = "03";
	/**
	 * 角色区分 01: 大区经理 02: 运营服务商 03: 市场开发经理 04:城市经理 05:体验店 06:导购 07:消费商 08:普通人 09:合伙人
	 */
	public static final String ROLE_DISTINGUISH01 = "01";
	public static final String ROLE_DISTINGUISH02 = "02";
	public static final String ROLE_DISTINGUISH03 = "03";
	public static final String ROLE_DISTINGUISH04 = "04";
	public static final String ROLE_DISTINGUISH05 = "05";
	public static final String ROLE_DISTINGUISH06 = "06";
	public static final String ROLE_DISTINGUISH07 = "07";
	public static final String ROLE_DISTINGUISH08 = "08";
	public static final String ROLE_DISTINGUISH09 = "99";

	/** 01：商品缩略图 分类list页面 02：商品内容详情图片 商品详情banner轮播图 03：详情图 */
	public static final String PIC_TYPE1 = "01";
	public static final String PIC_TYPE2 = "02";
	public static final String PIC_TYPE3 = "03";
	/** 图片类型 01：PC端图片 02：Mobile端图片类型 */
	public static final String PIC_PC = "01";
	public static final String PIC_MOBILE = "02";

	/** 1:提交到银行2：提现失败3：提现成功4：撤销提交 */
	public static final String BANK_STATUS01 = "1";
	public static final String BANK_STATUS02 = "2";
	public static final String BANK_STATUS03 = "3";
	public static final String BANK_STATUS04 = "4";
	/** 订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货 */
	public static final String ORDER_STATUS01 = "01";
	public static final String ORDER_STATUS02 = "02";
	public static final String ORDER_STATUS03 = "03";
	public static final String ORDER_STATUS04 = "04";
	public static final String ORDER_STATUS05 = "05";
	public static final String ORDER_STATUS06 = "06";
	/** 01:正常下单02：易拼 03：易团 */
	public static final String GOODS_TYPE01 = "01";
	public static final String GOODS_TYPE02 = "02";
	public static final String GOODS_TYPE03 = "03";

	/** 0:未使用 1：已使用 */
	public static final Integer COUPON_USE_STATUS01 = 0;
	public static final Integer COUPON_USE_STATUS02 = 1;

	/** 优惠券类型(01:商家优惠券02：注册优惠券03：红包) */
	public static final String COUPON_TYPE1 = "01";
	public static final String COUPON_TYPE2 = "02";
	public static final String COUPON_TYPE3 = "03";
	/**
	 * 订单状态 01-待付款 02-待审核 03-正在生产 04 待发货 05 待收货 06 已收货 21被动关闭 22主动关闭 41发起售后 42待退货
	 * 43退货中 44售后完成 61付款未拼满
	 */
	public static final String ORDER_STATUS21 = "21";
	public static final String ORDER_STATUS22 = "22";
	public static final String ORDER_STATUS41 = "41";
	public static final String ORDER_STATUS42 = "42";
	public static final String ORDER_STATUS43 = "43";
	public static final String ORDER_STATUS44 = "44";
	public static final String ORDER_STATUS61 = "61";
	/** 提现申请状态 */
	/** 0:待审批 1:审批通过 2:审批不通过 */
	public static final String WITHDRAW_APPLY_STATUS01 = "0";
	public static final String WITHDRAW_APPLY_STATUS02 = "1";
	public static final String WITHDRAW_APPLY_STATUS03 = "2";
	/***************** 日志相关固定值START ********************/
	/** 1:请求 api接口 */
	public final static String LOG_TYPE_1 = "1";
	/** 2:请求 后台pc */
	public final static String LOG_TYPE_2 = "2";
	/** 3:api接口 controller层日志 */
	public static final String LOG_TYPE_3 = "3";
	/** 4:api接口 service层日志 */
	public static final String LOG_TYPE_4 = "4";
	/** 5:后台pc controller层日志 */
	public static final String LOG_TYPE_5 = "5";
	/** 6:后台pc service层日志 */
	public static final String LOG_TYPE_6 = "6";
	/** 7:后台易盟pc controller层日志 */
	public static final String LOG_TYPE_7 = "7";
	/** 8:后台易盟pc service层日志 */
	public static final String LOG_TYPE_8 = "8";
	/***************** 日志相关固定值END ********************/

	/** 体验店默认市级coed（默认上海市） */
	public static final String DEFAULT_STREO_CODE = "320200";

	/** 图片验证码key */
	public final static String VERFY_CODE_KEY = "verCode";

	/** 01-支付成功 02-支付失败 03-支付中 04- 发起退款 05-退款中 06-退款成功 07-退款失败 */
	public static final String PAY_STATUS_01 = "01";
	public static final String PAY_STATUS_02 = "02";
	public static final String PAY_STATUS_03 = "03";
	public static final String PAY_STATUS_04 = "04";
	public static final String PAY_STATUS_05 = "05";
	public static final String PAY_STATUS_06 = "06";
	public static final String PAY_STATUS_07 = "07";

	/** 支付渠道01：小程序02：app 03 手机网站 04其他 */
	public static final String PAY_SOURCE_01 = "01";
	public static final String PAY_SOURCE_02 = "02";
	public static final String PAY_SOURCE_03 = "03";
	public static final String PAY_SOURCE_04 = "04";

	/** 01-ALI(支付宝) 02-WX（微信） 03-BC（银行卡） */
	public static final String PAY_WAYS_01 = "01";
	public static final String PAY_WAYS_02 = "02";
	public static final String PAY_WAYS_03 = "03";

	/** pc端cookie account */
	public final static String COOKIE_NAME = "account";
	/** pc端cookie password */
	public final static String COOKIE_PWD = "password";

	/** 手机号码对应openid */
	public final static String OPENID_APPLET = "_openid_applet";

	/** 系统自动确认收货时间 ：7天后自动确认收货 = 7天 */
	public final static int CONFIRM_RECEIPT_SECONDS = 7;

	/** 物流类型 01：订单发货 02：退/换货寄回仓库 03：退货验收失败退回 04：换货重新发货 05：换货验收失败退回 **/
	public final static String DELIVERY_TYPE01 = "01";
	public final static String DELIVERY_TYPE02 = "02";
	public final static String DELIVERY_TYPE03 = "03";
	public final static String DELIVERY_TYPE04 = "04";
	public final static String DELIVERY_TYPE05 = "05";

	/** 状态（01：在途02：到达签收03:延时再送） **/
	public final static String DELIVERY_STATUS01 = "01";
	public final static String DELIVERY_STATUS02 = "02";
	public final static String DELIVERY_STATUS03 = "03";

	/** 快递类型（1:京东大件物流） **/
	public final static String EXPRESS_TYPE01 = "1";

	/**
	 * 售后状态 01：待审核 02：拒绝 关联订单物流02类型 03：待取货 04：待收货 05：待验收
	 * 以下对应售后类型和验收结果选择不同路线关联订单物流03类型 21：退货验收不成功 - 待取货 22：退货验收不成功 - 待收货 41：退货验收成功 -
	 * 已退款 关联订单物流05类型 61：换货验收不成功 - 待取货 62：换货验收不成功 - 待收货 关联订单物流04类型 81：换货验收成功 - 待取货
	 * 82：换货验收成功 - 待收货
	 **/
	public final static String RESULT01 = "01";
	public final static String RESULT02 = "02";
	public final static String RESULT03 = "03";
	public final static String RESULT04 = "04";
	public final static String RESULT05 = "05";
	public final static String RESULT21 = "21";
	public final static String RESULT22 = "22";
	public final static String RESULT41 = "41";
	public final static String RESULT61 = "61";
	public final static String RESULT62 = "62";
	public final static String RESULT81 = "81";
	public final static String RESULT82 = "82";

	/** 售后类型（01：换货02：退货退款） **/
	public final static String HANDLE_STATUS01 = "01";
	public final static String HANDLE_STATUS02 = "02";

	/** 支付状态 01-支付成功 02-支付失败 03-支付中 04- 发起退款 05-退款中 06-退款成功 07-退款失败 **/
	public final static String PAY_STATUS01 = "01";
	public final static String PAY_STATUS02 = "02";
	public final static String PAY_STATUS03 = "03";
	public final static String PAY_STATUS04 = "04";
	public final static String PAY_STATUS05 = "05";
	public final static String PAY_STATUS06 = "06";
	public final static String PAY_STATUS07 = "07";

}
