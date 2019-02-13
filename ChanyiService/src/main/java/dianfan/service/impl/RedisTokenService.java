package dianfan.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.constant.ConstantIF;
import dianfan.models.TokenModel;

/**
 * 
 * @Title: RedisTokenService.java
 * @Package dianfan.service.impl
 * @Description: redis token服务类
 * @author Administrator
 * @date 2018年5月11日 下午3:56:34
 * @version V1.0
 */
@Service
public class RedisTokenService {
	/**
	 * redis
	 */
	@Autowired
	private RedisService redisService;

	/**
	 * 创建Token令牌
	 * 
	 * @param userId
	 *            用户id
	 * @param clientid
	 *            设备id
	 * @return 令牌实体
	 */
	public String createToken(String userId) {
		// 使用uuid作为源token
		String token = UUID.randomUUID().toString().replace("-", "");
		// 存储到redis并设置过期时间
		redisService.set(token, userId, ConstantIF.TOKEN_EXPIRES_SECONDS);
		return token;
	}

	/**
	 * 获取token令牌
	 * 
	 * @param token
	 *            令牌
	 * @return token类
	 */
	public TokenModel getToken(String token) {
		if (token == null || token.length() == 0) {
			return null;
		}
		String userid = redisService.get(token);
		if (userid == null || userid.length() == 0) {
			return null;
		}
		return new TokenModel(userid, token);
	}

	/**
	 * 检查token是否有效、有效则延长返回true,无效则返回false
	 * 
	 * @param model
	 *            token模型
	 * @return 有效(true)/无效(false)
	 */
	public boolean checkToken(TokenModel token) {
		if (token == null) {
			return false;
		}
		String userid = redisService.get(token.getToken());
		if (userid == null || userid.length() == 0) {
			return false;
		}else {
			//延时
			redisService.setEx(token.getToken(), token.getUserid(), ConstantIF.TOKEN_EXPIRES_SECONDS);
			//opid延时
			String openid = redisService.get(token.getToken()+ConstantIF.OPENID_APPLET);
			if (openid != null && openid.length() != 0) {
				redisService.setEx(token.getToken()+ConstantIF.OPENID_APPLET, openid, ConstantIF.TOKEN_EXPIRES_SECONDS);
			}
		}
		return true;
	}

	/**
	 * 删除token
	 * 
	 * @param userId
	 *            用户id
	 */
	public void deleteToken(String token) {
		redisService.del(token);
	}
}
