package com.jsan.jvaif.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * @description: json工具类
 * @author: jcwang
 * @create: 2019-08-04 21:59
 **/
public class JsonUtil {
    /***
     * 将map<String,Object>转成JSONObject
     * @param map 待转换map
     * @return map的JSONObject形式
     */
    public static JSONObject mapToJsonObject(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        JSONObject json = new JSONObject(map.size());

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            String jsonKey = TypeUtils.castToString(key);
            Object jsonValue = JSON.toJSON(entry.getValue());
            json.put(jsonKey, jsonValue);
        }

        return json;
    }
}
