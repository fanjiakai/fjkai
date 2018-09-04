package com.ddian.youfan.common.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: WenheZhu
 * @Description:
 * @Date: Created in 13:172018/7/2
 * @Modified By:
 */
public class BaseDO implements Serializable {

    private String dbName; // 数据库类型

    public Long createDeptId; // 创建部门ID

    public Long createUserId; // 创建用户ID

    public Timestamp createTime; // 创建时间

    public Timestamp updateTime; // 修改时间

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public Long getCreateDeptId() {
        return createDeptId;
    }

    public void setCreateDeptId(Long createDeptId) {
        this.createDeptId = createDeptId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
