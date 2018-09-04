package com.ddian.youfan.common.context;

import com.ddian.youfan.common.Constants.CommonConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class FilterContextHandler implements Serializable {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getUserID() {
        Object value = get(CommonConstants.CONTEXT_USER_ID);
        return returnObjectValue(value);
    }

    public static String getUsername() {
        Object value = get(CommonConstants.CONTEXT_USERNAME);
        return returnObjectValue(value);
    }


    public static String getName() {
        Object value = get(CommonConstants.CONTEXT_NAME);
        return returnObjectValue(value);
    }

    public static String getToken() {
        Object value = get(CommonConstants.CONTEXT_TOKEN);
        return returnObjectValue(value);
    }

    public static String getDeptID() {
        Object value = get(CommonConstants.CONTEXT_DEPT_ID);
        return returnObjectValue(value);
    }

    public static void setToken(String token) {
        set(CommonConstants.CONTEXT_TOKEN, token);
    }

    public static void setName(String name) {
        set(CommonConstants.CONTEXT_NAME, name);
    }

    public static void setUserID(String userID) {
        set(CommonConstants.CONTEXT_USER_ID, userID);
    }

    public static void setUsername(String username) {
        set(CommonConstants.CONTEXT_USERNAME, username);
    }

    public static void setDeptID(String deptID) {
        set(CommonConstants.CONTEXT_DEPT_ID, deptID);
    }

    public static boolean isAdmin() {
        Object value = get(CommonConstants.CONTEXT_USERNAME);
        return returnObjectValue(value).equals("admin");
    }


    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
