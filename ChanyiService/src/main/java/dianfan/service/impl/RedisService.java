package dianfan.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

/**
 * 
 * @Title: RedisService.java
 * @Package dianfan.service.impl
 * @Description: redis 服务操作类
 * @param <K>
 *            <键>
 * @param <V>
 *            <值>
 * @author Administrator
 * @date 2018年5月11日 上午11:15:26
 * @version V1.0
 */
@Service
public class RedisService<K, V> {

	/**
	 * 注入redis模板操作类
	 */
	@Autowired
	private RedisTemplate<K, V> redisTemplate;

	/**
	 * @Title: getRedisSerializer
	 * @Description: 获取 RedisSerializer
	 * @return RedisSerializer<String>
	 * @throws:
	 * @time: 2017年12月21日 上午9:10:37
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/* ---------------------------------------- */
	/* ---------Redis 字符串(String) 操作 -------- */
	/* ---------------------------------------- */

	/**
	 * @Title: set
	 * @Description: 新增 (存在则覆盖旧值，不设置过期)
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 上午11:17:07
	 */
	public boolean set(final String key, final String value) {
		return set(key, value, true);
	}

	/**
	 * @Title: set
	 * @Description: 新增 (不设置过期)
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cover
	 *            若存在键值对是否覆盖（true覆盖，false不覆盖）
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 上午11:17:07
	 */
	public boolean set(final String key, final String value, boolean cover) {
		if (key != null && !"".equals(key)) {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] keybytes = serializer.serialize(key);
					byte[] name = serializer.serialize(value);
					if (cover) {
						connection.set(keybytes, name);
					} else {
						connection.setNX(keybytes, name);
					}
					return true;
				}
			}, false, true);
		} else {
			return false;
		}
	}

	/**
	 * @Title: set
	 * @Description: 新增 (存在则覆盖旧值，设置过期)
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param seconds
	 *            秒
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 上午9:12:24
	 */
	public boolean set(final String key, final String value, final long seconds) {
		return setEx(key, value, seconds);
	}

	/**
	 * @Title: setEx
	 * @Description: 设置键的生存时间 (为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。 )
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param seconds
	 *            秒
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 上午11:53:29
	 */
	public boolean setEx(final String key, final String value, final long seconds) {
		if (key != null && !"".equals(key)) {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] keybytes = serializer.serialize(key);
					byte[] name = serializer.serialize(value);
					connection.setEx(keybytes, seconds, name);
					return true;
				}

			}, false, true);
		} else {
			return false;
		}
	}

	/**
	 * @Title: setEx
	 * @Description: 设置键的生存时间 (当 key不存在或者不能为 key 返回false)
	 * @param key
	 *            键
	 * @param seconds
	 *            秒
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 下午1:31:29
	 */
	public boolean setEx(final String key, final long seconds) {
		if (key != null && !"".equals(key)) {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] keybytes = serializer.serialize(key);
					return connection.expire(keybytes, seconds);
				}
			}, false, true);
		} else {
			return false;
		}
	}

	/**
	 * @Title: mSet
	 * @Description: 批量新增(不设置过期)
	 * @param maps
	 *            键值对集合
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 上午11:26:20
	 */
	public boolean mSet(final Map<String, String> maps) {
		if (maps != null && maps.size() > 0) {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					Map<byte[], byte[]> bytemap = new HashMap<>();
					for (Entry<String, String> m : maps.entrySet()) {
						byte[] key = serializer.serialize(m.getKey());
						byte[] value = serializer.serialize(m.getValue());
						bytemap.put(key, value);
					}
					connection.mSet(bytemap);
					return true;
				}
			}, false, true);
		} else {
			return false;
		}
	}

	/**
	 * @Title: set
	 * @Description: 批量新增 -设置过期
	 * @param maps
	 *            键值对集合
	 * @param seconds
	 *            秒
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 下午1:50:43
	 */
	public boolean set(final Map<String, String> maps, final long seconds) {
		if (maps != null && maps.size() > 0) {
			boolean tag = true;
			for (Entry<String, String> m : maps.entrySet()) {
				if (!setEx(m.getKey(), m.getValue(), seconds)) {
					tag = false;
					break;
				}
			}
			return tag;
		} else {
			return false;
		}
	}

	/**
	 * @Title: delete
	 * @Description: 删除key
	 * @param key
	 *            键
	 * @throws:
	 * @time: 2017年12月21日 下午1:54:47
	 */
	public void del(final String key) {
		if (key != null && !"".equals(key)) {
			redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] keyByte = serializer.serialize(key);
					connection.del(keyByte);
					return true;
				}
			}, false, true);
		}
	}

	/**
	 * @Title: delete
	 * @Description: 删除多个key
	 * @param keys
	 *            多个键
	 * @throws:
	 * @time: 2017年12月21日 上午9:13:59
	 */
	public void del(final List<String> keys) {
		if (keys != null && keys.size() > 0) {
			for (String key : keys) {
				del(key);
			}
		}
	}

	/**
	 * @Title: get
	 * @Description: 通过key获取值
	 * @param keyId
	 *            键的id
	 * @return 字符串
	 * @throws:
	 * @time: 2017年12月21日 上午9:14:31
	 */
	public String get(final String keyId) {
		if (keyId != null && !"".equals(keyId)) {
			return redisTemplate.execute(new RedisCallback<String>() {
				@Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);

					byte[] bs = connection.get(key);
					if (bs == null) {
						return null;
					} else {
						return new String(bs);
					}
				}
			});
		} else {
			return null;
		}
	}

	/* ---------------------------------------- */
	/* ---------- Redis 哈希(Hash) 操作 ---------- */
	/* ---------------------------------------- */

	/**
	 * @Title: hSet
	 * @Description: 为哈希表中的字段赋值 。如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
	 *               如果字段已经存在于哈希表中，旧值将被覆盖。
	 * @param keyId
	 *            键
	 * @param field
	 *            字段
	 * @param value
	 *            值
	 * @return true/false
	 * @throws:
	 * @time: 2017年12月21日 下午2:57:41
	 */
	public boolean hSet(final String keyId, final String field, final String value) {
		if (keyId != null && !"".equals(keyId) && field != null && !"".equals(field)) {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					byte[] field_byte = serializer.serialize(field);
					byte[] value_byte = serializer.serialize(value);
					return connection.hSet(key, field_byte, value_byte);
				}
			});
		} else {
			return false;
		}
	}

	/**
	 * @Title: hGet
	 * @Description: 获取哈希表中指定字段的值。
	 * @param keyId
	 *            键
	 * @param field
	 *            字段
	 * @return 字符串
	 * @throws:
	 * @time: 2017年12月21日 下午3:56:37
	 */
	public String hGet(final String keyId, final String field) {
		if (keyId != null && !"".equals(keyId) && field != null && !"".equals(field)) {
			return redisTemplate.execute(new RedisCallback<String>() {
				@Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					byte[] field_byte = serializer.serialize(field);
					byte[] value = connection.hGet(key, field_byte);
					if (value == null) {
						return null;
					}
					return new String(value);
				}
			});
		} else {
			return null;
		}
	}

	/**
	 * @Title: hGetAll
	 * @Description: 获取哈希表中Map列表。
	 * @param keyId
	 *            键
	 * @return map集合
	 * @throws:
	 * @time: 2017年12月21日 下午3:56:37
	 */
	public Map<String, String> hGetAll(final String keyId) {
		if (keyId != null && !"".equals(keyId)) {
			return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
				@Override
				public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					Map<byte[], byte[]> map = connection.hGetAll(key);
					if (map == null) {
						return null;
					}
					Map<String, String> revertMap = new HashMap<String, String>();
					Set entries = map.entrySet();
					if (entries != null) {
						Iterator iterator = entries.iterator();
						while (iterator.hasNext()) {
							Map.Entry<byte[], byte[]> entry = (Entry) iterator.next();
							String returnkey = new String(entry.getKey());
							String returnvalue = new String(entry.getValue());
							revertMap.put(returnkey, returnvalue);
						}
					}
					return revertMap;
				}
			});
		} else {
			return null;
		}
	}

	/**
	 * @Title: hDel
	 * @Description: 删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
	 * @param keyId
	 *            键
	 * @param field
	 *            字段
	 * @return long
	 * @throws:
	 * @time: 2017年12月21日 下午4:00:08
	 */
	public Long hDel(final String keyId, final String field) {
		if (keyId != null && !"".equals(keyId)) {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					byte[] field_byte = serializer.serialize(field);
					return connection.hDel(key, field_byte);
				}
			});
		} else {
			return 0L;
		}
	}

	/* ---------------------------------------- */
	/* --------------Redis 其它操作-------------- */
	/* ---------------------------------------- */

	/**
	 * @Title: getExpire
	 * @Description: 获取key的剩余时间（秒）
	 * @param keyId
	 *            键的id
	 * @return long
	 * @throws:
	 * @time: 2017年12月21日 上午9:14:55
	 */
	public Long getExpire(final String keyId) {
		Long result = -2L;
		if (keyId != null && !"".equals(keyId)) {
			result = redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(keyId);
					return connection.ttl(key);
				}
			});
		}
		return result;
	}
}