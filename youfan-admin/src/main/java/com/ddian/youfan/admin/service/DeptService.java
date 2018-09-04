package com.ddian.youfan.admin.service;

import com.ddian.youfan.admin.domain.DeptDO;
import com.ddian.youfan.admin.domain.Tree;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author grandtech
 * @email 1992lcg@163.com
 * @date 2018-06-06 14:40:36
 */
public interface DeptService {
	
	DeptDO get(Long deptId);
	
	List<DeptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeptDO sysDept);
	
	int update(DeptDO sysDept);
	
	int remove(Long deptId);
	
	int batchRemove(Long[] deptIds);

	Tree<DeptDO> getTree();
	
	boolean checkDeptHasUser(Long deptId);
}
