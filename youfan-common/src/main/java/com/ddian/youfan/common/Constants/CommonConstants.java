package com.ddian.youfan.common.Constants;

public class CommonConstants {
    public final static String CONTEXT_TOKEN="Authorization";
    public final static String CONTEXT_USERNAME="contextUsername";
    public final static String CONTEXT_USER_ID="contextUserId";
    public final static String CONTEXT_NAME="contextName";
    public final static String CONTEXT_DEPT_ID="contextDeptId";
    public final static String JWT_PRIVATE_KEY ="misnice";


    /***
     * 数据权限常量
     */
    public static final String DATA_SCOPE_ALL = "1"; // 所有权限
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "2"; // 所在机构及以下数据
    public static final String DATA_SCOPE_DEPT = "3"; // 所在机构及以下数据
    public static final String DATA_SCOPE_SELF = "4"; // 仅本人数据
}
