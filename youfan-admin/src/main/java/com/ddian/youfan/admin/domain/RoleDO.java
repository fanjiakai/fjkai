package com.ddian.youfan.admin.domain;

import com.ddian.youfan.common.dto.BaseDO;

import java.util.List;

/**
 * 角色管理Entity
 * @author grandtech
 * @date 2018-06-06 14:40:36
 * @modify  20180820 1、删除非必要字段、增加数据范围字段
 */
@SuppressWarnings("all")
public class RoleDO extends BaseDO {
	
	private Long roleId;  // 角色ID
	private String roleName;	//角色名称
	private String roleSign;
	private String remark; // 角色说明
	private List<Long> menuIds;
	private int roleType; // 数据范围类型


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "RoleDO{" +
				"roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				", roleSign='" + roleSign + '\'' +
				", remark='" + remark + '\'' +
				", menuIds=" + menuIds +
				'}';
	}
}
