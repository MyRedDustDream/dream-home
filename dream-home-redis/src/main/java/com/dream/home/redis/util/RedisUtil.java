package com.dream.home.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author hhz
 * @date 2020-08-18 17:45:48
 */
@Slf4j
@Service
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 写入缓存
     *
     * @param key   key
     * @param value 值
     * @return true/false
     */
    public boolean setValue(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key        key
     * @param value      值
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return true/false
     */
    public boolean setValueWithExpireTime(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置时效时间
     *
     * @param key        key
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return true/false
     */
    public boolean expire(final String key, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern 匹配符
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key key
     */
    public void remove(final String key) {
        if (hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key key
     * @return true/false/null
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return 值
     */
    public Object getValue(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 哈希获取数据
     *
     * @param key     key
     * @param hashKey hashKey
     * @return 值
     */
    public Object getHashValue(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希添加
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   值
     */
    public void setHashValue(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 设置map缓存
     *
     * @param key key
     * @param map 值
     */
    public void setMapHash(String key, Map<?, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 设置map缓存、并设置过期时间
     *
     * @param key      key
     * @param map      值
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     */
    public void setMapHashWithTimeout(String key, Map<?, ?> map, Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        if (timeout != null && timeUnit != null) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }

    /**
     * 根据key获得map
     *
     * @param key key
     * @return map
     */
    public Map<Object, Object> getMapHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 集合添加
     *
     * @param key   key
     * @param value 值
     */
    public void addSet(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key key
     * @return Set集合
     */
    public Set<Object> getSetMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key    key
     * @param value  值
     * @param source source
     */
    public void zSetAdd(String key, Object value, double source) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, source);
    }

    /**
     * 有序集合获取
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return Set集合
     */
    public Set<Object> rangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 将一个值插入到列表头部，value可以重复，返回列表的长度
     *
     * @param key   key
     * @param value String
     * @return 返回List的长度
     */
    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将一个值插入到列表尾部，value可以重复，返回列表的长度
     *
     * @param key   key
     * @param value String
     * @return 返回List的长度
     */
    public Long rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将多个值插入到列表头部，value可以重复
     *
     * @param key    key
     * @param values String[]
     * @return 返回List的数量size
     */
    public Long leftPushAll(String key, Object[] values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 将多个值插入到列表尾部，value可以重复
     *
     * @param key    key
     * @param values String[]
     * @return 返回List的数量size
     */
    public Long rightPushAll(String key, Object[] values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 获取List范围列表
     *
     * @param key   key
     * @param start long，开始索引
     * @param end   long， 结束索引
     * @return List<Object> 集合
     */
    public List<Object> getRangeList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 删除并返回列表中存储在{@code key}的第一个元素。
     *
     * @param key 键不能是{@literal null}。
     *            当键不存在或在管道/事务中使用时@return {@literal null}。
     * @return 被移除的元素
     * @see <a href="https://redis。io /命令/ lpop”>复述,文档:lpop < / >
     */
    @Nullable
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 删除并返回列表中存储在{@code key}的第一个元素。
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
     *
     * @param key     key 键不能是{@literal null}。
     *                当键不存在或在管道/事务中使用时@return {@literal null}。
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 被移除的元素
     */
    public Object leftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 删除并返回列表中存储在{@code key}的最后一个元素。
     *
     * @param key 键不能是{@literal null}。
     *            当键不存在或在管道/事务中使用时@return {@literal null}。
     * @return 被移除的元素
     * @see <a href="https://redis。io /命令/ rpop”>复述,文档:rpop < / >
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 删除并返回列表中存储在{@code key}的最后一个元素。
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
     *
     * @param key     key 键不能是{@literal null}。
     *                当键不存在或在管道/事务中使用时@return {@literal null}。
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 被移除的元素
     */
    public Object rightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 通过索引获取列表中的元素
     *
     * @param key                 key
     * @param index，索引，0表示最新的一个元素
     * @return Object
     */
    public Object findObjectByKeyAndIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表长度，key为空时返回0
     *
     * @param key key
     * @return Long
     */
    public Long getSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 移除列表元素，返回移除的元素数量
     *
     * @param key                 key
     * @param count，标识，表示动作或者查找方向 <li>当count=0时，移除所有匹配的元素；</li>
     *                            <li>当count为负数时，移除方向是从尾到头；</li>
     *                            <li>当count为正数时，移除方向是从头到尾；</li>
     * @param value               匹配的元素
     * @return Long
     */
    public Long removeAndReturnSize(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param key   key
     * @param start <li>可以为负数（-1是列表的最后一个元素，-2是列表倒数第二的元素。）</li>
     *              <li>如果start大于end，则返回一个空的列表，即列表被清空</li>
     * @param end   <li>可以为负数（-1是列表的最后一个元素，-2是列表倒数第二的元素。）</li>
     *              <li>可以超出索引，不影响结果</li>
     */
    public void trim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获得当前db下标
     *
     * @return 当前db下标
     */
    public int getCurrentDbIndex() {
        LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate
                .getConnectionFactory();
        if (jedisConnectionFactory != null) {
            int database = jedisConnectionFactory.getDatabase();
            log.info("this database:{}", database);
            return jedisConnectionFactory.getDatabase();
        }
        throw new RuntimeException("获取当前db出错");
    }

    /**
     * 设置数据库索引
     *
     * @param dbIndex 数据库index
     */
    public void selectDb(Integer dbIndex) {
        if (dbIndex == null || dbIndex > 15 || dbIndex < 0) {
            dbIndex = 0;
        }
        LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate
                .getConnectionFactory();
        if (jedisConnectionFactory != null) {
            int database = jedisConnectionFactory.getDatabase();
            log.info("old database:{}", database);
            jedisConnectionFactory.setDatabase(dbIndex);
            redisTemplate.setConnectionFactory(jedisConnectionFactory);
            jedisConnectionFactory.afterPropertiesSet();
            jedisConnectionFactory.resetConnection();
            database = jedisConnectionFactory.getDatabase();
            log.info("new database:{}", database);
        }
    }

}