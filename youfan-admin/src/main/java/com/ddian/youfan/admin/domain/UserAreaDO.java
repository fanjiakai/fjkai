package com.ddian.youfan.admin.domain;

import com.ddian.youfan.common.dto.BaseDO;

public class UserAreaDO extends BaseDO {
    private Long id;
    private Long userId;
    private Long areaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "UserAreaDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + areaId +
                '}';
    }
}
