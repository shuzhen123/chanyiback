package dianfan.util;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

/**
 * 
 * @Title: AppPushUtil.java
 * @Package dianfan.util
 * @Description: app个推推送工具类
 * @author Administrator
 * @date 2018年5月11日 上午11:42:22
 * @version V1.0
 */
public class AppPushUtil {

	/**
	 * getui_appId
	 */
	private static final String GETUI_APPID = PropertyUtil.getProperty("getui_appid");
	/**
	 * getui_appkey
	 */
	private static final String GETUI_APPKEY = PropertyUtil.getProperty("getui_appkey");
	/**
	 * getui_mastersecret
	 */
	private static String GETUI_MASTERSECRET = PropertyUtil.getProperty("getui_mastersecret");
	/**
	 * url
	 */
	private static String URL = "http://sdk.open.api.igexin.com/apiex.htm";

	/**
	 * 透传消息 字典模式使用
	 * 
	 * @param body
	 *            内容
	 * @param actionLockey
	 * @param lockey
	 * @param locargs
	 * @param launch_image
	 * @param title
	 *            标题
	 * @param titlelockey
	 * @param titlelocarg
	 * @return APN内容
	 */
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String body, String actionLockey, String lockey,
			String locargs, String launch_image, String title, String titlelockey, String titlelocarg) {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody(body);
		alertMsg.setActionLocKey(actionLockey);
		alertMsg.setLocKey(lockey);
		alertMsg.addLocArg(locargs);
		alertMsg.setLaunchImage(launch_image);
		// iOS8.2以上版本支持
		alertMsg.setTitle(title);
		alertMsg.setTitleLocKey(titlelockey);
		alertMsg.addTitleLocArg(titlelocarg);
		return alertMsg;
	}

	/**
	 * 内部透传内容消息【ios】模板
	 * 
	 * @param content
	 *            透传内容
	 * @param category
	 *            添加事件
	 * @param resurl
	 *            多媒体资源URL null将不添加
	 * @param mode
	 *            【true:为简单模式 false为字典模式】
	 * @param arg
	 *            【arg[0] 通知文本消息字符串
	 *            ,其他为actionLockey,lockey,locargs,launch_image,title,titlelockey,titlelocarg】
	 * @return 通知模板
	 */
	private static TransmissionTemplate getTemplate(String content, String category, String resurl, boolean mode,
			String... arg) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(GETUI_APPID);
		template.setAppkey(GETUI_APPKEY);
		template.setTransmissionContent(content);
		template.setTransmissionType(1);
		APNPayload payload = new APNPayload();
		// 在已有数字基础上加1显示， 设置为-1时， 在已有数字上减1显示， 设置为数字时， 显示指定数字
		payload.setAutoBadge("+1");
		// 推送直接带有透传数据
		payload.setContentAvailable(0);
		// 默认响铃
		payload.setSound("default");
		// 自定义通知按钮事件 在客户端通知栏触发特定的action和button显示
		payload.setCategory(category);

		if (mode) {
			// 简单模式APNPayload.SimpleMsg 通知消息体
			payload.setAlertMsg(new APNPayload.SimpleAlertMsg(arg[0]));
		} else {
			String[] temparg = { "", "", "", "", "", "", "", "" };
			// 字典模式使用APNPayload.DictionaryAlertMsg
			for (int i = 0; i < arg.length; i++) {
				temparg[i] = arg[i];
			}
			payload.setAlertMsg(getDictionaryAlertMsg(temparg[0], temparg[1], temparg[2], temparg[3], temparg[4],
					temparg[5], temparg[6], temparg[7]));
		}
		// 添加多媒体资源
		if (resurl != null) {
			payload.addMultiMedia(
					new MultiMedia().setResType(MultiMedia.MediaType.video).setResUrl(resurl).setOnlyWifi(true));
		}
		template.setAPNInfo(payload);
		return template;
	}

	/**
	 * 带通知透传内容消息【ios】模板
	 * 
	 * @param content
	 *            透传内容
	 * @param category
	 *            添加事件
	 * @param resurl
	 *            多媒体资源URL null将不添加
	 * @param mode
	 *            【true:为简单模式 false为字典模式】
	 * @param arg
	 *            【arg[0] 通知文本消息字符串,arg[5] 通知标题
	 *            ,其他为actionLockey,lockey,locargs,launch_image,title,titlelockey,titlelocarg】
	 * @return 通知模板
	 */
	private static APNTemplate getTemplateAndNotify(String content, String category, String resurl, boolean mode,
			String... arg) {
		APNTemplate template = new APNTemplate();
		template.setAppId(GETUI_APPID);
		template.setAppkey(GETUI_APPKEY);
		APNPayload payload = new APNPayload();
		// 在已有数字基础上加1显示， 设置为-1时， 在已有数字上减1显示， 设置为数字时， 显示指定数字
		payload.setAutoBadge("+1");
		// 推送直接带有透传数据
		payload.setContentAvailable(0);
		// 默认响铃
		payload.setSound("default");
		// 自定义通知按钮事件 在客户端通知栏触发特定的action和button显示
		payload.setCategory(category);

		if (mode) {
			// 简单模式APNPayload.SimpleMsg 通知消息体
			payload.setAlertMsg(new APNPayload.SimpleAlertMsg(arg[0]));
		} else {
			String[] temparg = { "", "", "", "", "", "", "", "" };
			// 字典模式使用APNPayload.DictionaryAlertMsg
			for (int i = 0; i < arg.length; i++) {
				temparg[i] = arg[i];
			}
			payload.setAlertMsg(getDictionaryAlertMsg(temparg[0], temparg[1], temparg[2], temparg[3], temparg[4],
					temparg[5], temparg[6], temparg[7]));

		}
		// 添加多媒体资源
		if (resurl != null) {
			payload.addMultiMedia(
					new MultiMedia().setResType(MultiMedia.MediaType.video).setResUrl(resurl).setOnlyWifi(true));
		}
		template.setAPNInfo(payload);
		return template;
	}

	/**
	 * 发送指定app内推送指定透传消息【ios和安卓】【所有用户】
	 * 
	 * @param offlineExpireTime
	 *            离线消息有效期(单位毫秒)
	 * @param content
	 *            透传内容
	 * @param category
	 *            添加事件
	 * @param resurl
	 *            多媒体资源URL null将不添加
	 * @param mode
	 *            【true:为简单模式 false为字典模式】
	 * @param arg
	 *            【arg[0] 通知文本消息字符串,arg[5] 通知标题
	 *            ,其他为actionLockey,lockey,locargs,launch_image,title,titlelockey,titlelocarg】
	 * @return IPushResult 发送状态
	 * @throws Exception
	 *             抛出异常
	 */
	public static IPushResult pushApp(int offlineExpireTime, String content, String category, String resurl,
			boolean mode, String... arg) throws Exception {
		IGtPush push = new IGtPush(URL, GETUI_APPKEY, GETUI_MASTERSECRET);
		// 定义"点击链接打开通知模板"，并设置标题、内容、链接
		TransmissionTemplate template = getTemplate(content, category, resurl, mode, arg);
		// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
		AppMessage message = new AppMessage();
		message.setData(template);
		List<String> appIds = new ArrayList<String>();
		appIds.add(GETUI_APPID);
		message.setAppIdList(appIds);
		message.setOfflineExpireTime(offlineExpireTime);
		IPushResult ret = push.pushMessageToApp(message);
		return ret;
	}

	/**
	 * 带通知发送推送指定透传消息【ios】【指定用户列表】
	 * 
	 * @param tokenlist
	 *            用户列表
	 * @param offlineExpireTime
	 *            离线消息有效期(单位毫秒)
	 * @param content
	 *            透传内容
	 * @param category
	 *            添加事件
	 * @param resurl
	 *            多媒体资源URL null将不添加
	 * @param mode
	 *            【true:为简单模式 false为字典模式】
	 * @param arg
	 *            【arg[0] 通知文本消息字符串,arg[5] 通知标题
	 *            ,其他为actionLockey,lockey,locargs,launch_image,title,titlelockey,titlelocarg】
	 * @throws Exception
	 *             抛出异常
	 */
	public static void pushAppAndNotify(List<String> tokenlist, int offlineExpireTime, String content, String category,
			String resurl, boolean mode, String... arg) throws Exception {
		IGtPush push = new IGtPush(URL, GETUI_APPKEY, GETUI_MASTERSECRET);
		// 定义"点击链接打开通知模板"，并设置标题、内容、链接
		APNTemplate template = getTemplateAndNotify(content, category, resurl, mode, arg);
		// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)

		SingleMessage message = new SingleMessage();
		message.setData(template);
		message.setOfflineExpireTime(offlineExpireTime);
		for (int i = 0; i < tokenlist.size(); i++) {
			push.pushAPNMessageToSingle(GETUI_APPID, tokenlist.get(i), message);
		}
	}

	/**
	 * 带通知发送推送指定透传消息【android】【指定用户列表】
	 * 
	 * @param cidList
	 *            用户列表
	 * @param title
	 *            标题
	 * @param text
	 *            内容
	 * @param transcontent
	 *            透传内容
	 */
	public static void pushMessageAndriod(List<String> cidList, String title, String text, String transcontent) {
		NotificationTemplate notificationTemplate = new NotificationTemplate();
		notificationTemplate.setTitle(title);
		notificationTemplate.setText(text);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		notificationTemplate.setTransmissionType(1);
		notificationTemplate.setTransmissionContent(transcontent);
		notificationTemplate.setAppId(GETUI_APPID);
		notificationTemplate.setAppkey(GETUI_APPKEY);
		IGtPush push = new IGtPush(URL, GETUI_APPKEY, GETUI_MASTERSECRET);
		ListMessage message = new ListMessage();
		message.setData(notificationTemplate);
		message.setOffline(true);
		message.setOfflineExpireTime(24 * 3600 * 1000);
		String taskId = push.getContentId(message);
		// 推送
		push.pushMessageToList(taskId, convertToTargets(cidList));
	}

	/**
	 * 转换cid成Targets
	 * 
	 * @param cidList
	 *            用户列表
	 * @return 返回目标用户的cid列表
	 */
	private static List<Target> convertToTargets(List<String> cidList) {
		List<Target> targetList = new ArrayList<>();
		for (String cid : cidList) {
			Target target = new Target();
			target.setAppId(GETUI_APPID);
			target.setClientId(cid);
			targetList.add(target);
		}
		return targetList;
	}

	/**
	 * 安卓通知推送
	 * 
	 * @param cidList
	 *            clientid
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static void singlePush(List<String> cidList, String title, String content) {
		IGtPush push = new IGtPush(URL, GETUI_APPKEY, GETUI_MASTERSECRET);
		LinkTemplate template = linkTemplateDemo(title, content);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		List<Target> tags = convertToTargets(cidList);
		for (int i = 0, ls = tags.size(); i < ls; i++) {
			try {
				push.pushMessageToSingle(message, tags.get(i));
			} catch (RequestException e) {
				push.pushMessageToSingle(message, tags.get(i), e.getRequestId());
			}
		}
	}

	/**
	 * 设置安卓通知模板
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @return 返回模板内容
	 */
	public static LinkTemplate linkTemplateDemo(String title, String content) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(GETUI_APPID);
		template.setAppkey(GETUI_APPKEY);

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(title);
		style.setText(content);
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		// 设置打开的网址地址
		template.setUrl("");
		return template;
	}

	/**
	 * @Title: setBadgeForDeviceToken
	 * @Description:设置角标
	 * @param deviceToken
	 *            机器的token
	 * @param badge
	 *            app上的角标
	 * @time: 2018年4月3日 下午4:45:03
	 */
	public static void setBadgeForDeviceToken(String deviceToken, String badge) {
		List<String> deviceTokenList = new ArrayList<String>();
		// 用户应用icon上显示的数字
		deviceTokenList.add(deviceToken);
		IGtPush push = new IGtPush(GETUI_APPKEY, GETUI_MASTERSECRET);
		// "+1"即在原有badge上加1；具体详情使用请参考该接口描述
		// badge = "+1";
		// "-1"即在原有badge上减1；具体详情使用请参考该接口描述
		// badge = "-1";
		// 直接设置badge数值，会覆盖原有数值；具体详情使用请参考该接口描述oooo
		// badge = "1";
		push.setBadgeForDeviceToken(badge, GETUI_APPID, deviceTokenList);
	}

}
