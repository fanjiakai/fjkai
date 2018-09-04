package com.ddian.youfan.admin.domain;

import com.ddian.youfan.common.dto.BaseDO;

public class AreaDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    private Long areaId;
    // 父级编号
    private Long parentId;
    // 父级集合
    private String parentIds;

    // 区域编码
    private String code;
    //塔式编码
    private Long tCode;
    // 区域名称
    private String name;
    //拼音
    private String spell;
    // 排序
    private Integer orderNum;
    // 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县；5：乡镇；6：村级）
    private Integer type;


    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public Long gettCode() {
        return tCode;
    }

    public void settCode(Long tCode) {
        this.tCode = tCode;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
