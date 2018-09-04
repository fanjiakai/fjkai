package com.ddian.youfan.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WenheZhu
 * @Description: 系统参数缓存容器
 * @Date: Created in 15:332018/6/6
 * @Modified By:
 */
@SuppressWarnings("all")
public class CacheCup {
    public static final String DB_TYPE = "dbtype";
    public static Map<String, Object> map = new HashMap<String, Object>();

    public static void put(String k, Object v) {
        map.put(k, v);
    }


    public static Object get(String k, Object v) {
        return map.get(k);
    }

    public static String getStr(String k) {
        return String.valueOf(map.get(k) == null ? "" : map.get(k));
    }

    public static void clear() {
        map.clear();
    }
}
