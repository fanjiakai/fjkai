package com.ddian.youfan.admin.domain;

import com.ddian.youfan.common.dto.BaseDO;

import java.util.Date;

/**
 * @Author: WenheZhu
 * @Description:
 * @Date: Created in 14:012018/6/21
 * @Modified By:
 */
public class DictDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    //编号
    private Long id;
    //标签名
    private String name;
    //数据值
    private String value;
    // 类型
    private String type;
    //描述
    private String description;
    //排序
    private String sort;
    //父级编号
    private Long parentId;
    // 创建者
    private String createBy;
    // 创建日期
    private Date createDate;
    // 更新者
    private String updateBy;
    // 更新日期
    private Date updateDate;
    // 删除标记（0：正常；1：删除；2：审核）
    private String delFlag;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


}
