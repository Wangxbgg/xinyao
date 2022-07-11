package com.xinyao.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * fastjson工具类
 *
 * @version:1.0.0
 */
public class JsonUtil {


    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
//        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
//        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    /**
     * 输出空置字段
     */
    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty
    };

    //转为特定对象
    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }


    /**
     * 对象转json字符串
     * 格式化数据
     *
     * @param object
     * @return
     */
    public static String objectToJsonStr(Object object) {
        return JSON.toJSONString(object, config, features);
    }

    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }

    /**
     * json字符串转object
     *
     * @param text
     * @return
     */
    public static Object jsonStrToObject(String text) {
        return JSON.parse(text);
    }

    /**
     * json字符串转object（泛型）
     *
     * @param text
     * @param clazz
     * @return
     */
    public static <T> T jsonStrToObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    // 转换为数组

    /**
     * json字符串转数组
     *
     * @param text
     * @param <T>
     * @return
     */
    public static <T> Object[] jsonStrToArray(String text) {
        return jsonStrToArray(text, null);
    }

    /**
     * json字符串转数组（泛型）
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object[] jsonStrToArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    /**
     * json字符串转列表
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonStrToList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * List<String>转List<T>
     * @param list
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> listToListObject(List<String> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (list.size() > 0) {
            for (String str : list) {
                T object = jsonStrToObject(str, clazz);
                result.add(object);
            }
        }
        return result;
    }

    /**
     * list转json字符串
     *
     * @param list
     */
    public static String listToJsonStr(List<String> list) {
        return JSON.toJSONString(list);
    }

    /**
     * list转json字符串
     *
     * @param list
     */
    public static String listIntegerToJsonStr(List<Integer> list) {
        return JSON.toJSONString(list);
    }


    /**
     * json字符串转json数组
     *
     * @param jsonStr
     */
    public static JSONArray jsonStrToJsonArray(String jsonStr) {
        return JSONArray.parseArray(jsonStr);
    }

    /**
     * list转jsonArray
     *
     * @param list
     */
    public static JSONArray listToJsonArray(List<String> list) {
        String jsonStr = JSON.toJSONString(list);
        return JSONArray.parseArray(jsonStr);
    }

    /**
     * json字符串转化为map
     *
     * @param s
     * @return
     */
    public static <K, V> Map<K, V> jsonStrToMap(String s) {
        Map<K, V> m = (Map<K, V>) JSONObject.parseObject(s);
        return m;
    }

    /**
     * 将map转化为string
     *
     * @param m
     * @return
     */
    public static <K, V> String mapToJsonStr(Map<K, V> m) {
        String s = JSONObject.toJSONString(m);
        return s;
    }


    /**
     * 把一个字符串转换成bean对象
     * @param str
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}

