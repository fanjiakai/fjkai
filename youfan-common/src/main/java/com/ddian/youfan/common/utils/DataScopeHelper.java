package com.ddian.youfan.common.utils;

import com.ddian.youfan.common.Constants.CommonConstants;

/**
 * @Author: WenheZhu
 * @Description: 数据权限范围过滤工具
 * @Date: Created in 15:302018/8/20
 * @Modified By:
 */
@SuppressWarnings("all")
public abstract class DataScopeHelper {
    /***
     * 增加同级和下级数据过滤条件
     * @param userId
     * @param deptId
     * @param roleType
     * @param busiAlias
     * @return
     */
    public static String dataScopeSubFilter(String userId, String deptId, String roleType, String busiAlias, String field) {
        StringBuilder scopeFilterSQL = new StringBuilder();
        if (!isSuperAdmin(userId)) { // 不是管理员增加权限控制
            if (roleType.equals(CommonConstants.DATA_SCOPE_DEPT_AND_CHILD)) {
                scopeFilterSQL.append(" and " + (StringUtils.isEmpty(busiAlias) ? "" : busiAlias + ".") + (StringUtils.isEmpty(field) ? "create_dept_id" : field) + " in (select ds2.dept_id from sys_dept ds1 ,sys_dept ds2 where ds1.dept_id = "+deptId+" and (ds2.parent_ids LIKE '%'||ds1.dept_id||'%' or ds1.dept_id = ds2.dept_id)) ");
            } else if (roleType.equals(CommonConstants.DATA_SCOPE_DEPT)) {
                scopeFilterSQL.append(" and " + (StringUtils.isEmpty(busiAlias) ? "" : busiAlias + ".") + (StringUtils.isEmpty(field) ? "create_dept_id" : field) + " =" + deptId + " ");
            } else if (roleType.equals(CommonConstants.DATA_SCOPE_SELF)) {
                scopeFilterSQL.append(" and " + (StringUtils.isEmpty(busiAlias) ? "" : busiAlias + ".") + (StringUtils.isEmpty(field) ? "create_user_id" : field) + " =" + userId + " ");
            }
        }
        return scopeFilterSQL.toString();
    }

    public static String dataScopeAreaFilter(String userId, String deptId){
        StringBuilder scopeFilterSQL = new StringBuilder();
        if (!isSuperAdmin(userId)) {

        }
        return "";
    }

    private static boolean isSuperAdmin(String userId) {
        //  userId为1的默认为超级管理员
        return userId.equals("1");
    }
}
