package com.xinyao.util;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Maple
 * @date 2018/9/19
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        redisUtil = this;
    }


    public List<Object> getValueList(Set<String> keys) {
        List<Object> values = redisTemplate.opsForValue().multiGet(keys);
        return values;
    }

    public Set<String> getKeys(String keys){
        Set<String> keys1 = redisTemplate.keys(keys);
        return keys1;
    }
    /**
     * 写入缓存
     */
    public boolean set(final String key, Object value) {
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
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 哈希 添加
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希 添加整个map
     */
    public void hmSetAll(String key, Map<String, BigDecimal> value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        if (hash.size(key) > 0) {
            //先删除map
            hmRemoveAll(key);
        }
        hash.putAll(key,value);
    }

    /**
     * 哈希 删除整个map
     */
    public void hmRemoveAll(String key){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        Set<Object> keySet = hash.keys(key);
        if(keySet.size() > 0){
            for (Object o : keySet){
                hash.delete(key, o);
            }
        }
    }

    /**
     * 哈希获取数据
     */
    public Map<Object, Object> hmGetAll(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    /**
     * 哈希获取数据
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 列表添加
     */
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     * 列表获取
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     */
    public void addSet(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 删除集合下的所有值
     */
    public void removeSetAll(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        Set<Object> objectSet = set.members(key);
        if(objectSet != null && objectSet.size() > 0){
            for (Object o : objectSet){
                set.remove(key, o);
            }
        }
    }

    /**
     * 判断集合里面是否包含某个元素
     */
    public Boolean isMember(String key, Object member){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, member);
    }

    /**
     * 集合获取
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * 从redis中获取key对应的过期时间;
     * 如果该值有过期时间，就返回相应的过期时间;
     * 如果该值没有设置过期时间，就返回-1;
     * 如果没有该值，就返回-2;
     */
    public Long getExpire(String key){
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }

    /**
     *
     * @param key 编号对应的key值
     * @param zeroNum 需要补0的数量
     * @return 补0后的编号
     */
    public String getIncr(String key, Integer zeroNum){
        Long id = redisTemplate.opsForValue().increment(key, 1);
        if(id == null){
            throw new RuntimeException("获取编号失败");
        }
        StringBuilder noFlag = new StringBuilder(String.valueOf(id));
        int length = noFlag.length();
        int lose = zeroNum - length;
        for (int i = 0; i < lose; i++) {
            noFlag.insert(0, "0");
        }
        return noFlag.toString();
    }
}