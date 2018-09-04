package com.ddian.youfan.admin.service;

import com.ddian.youfan.admin.domain.AreaDO;
import com.ddian.youfan.admin.domain.Tree;
import com.ddian.youfan.admin.domain.UserDO;

import java.util.List;
import java.util.Map;

/**
 * 区域管理service
 *
 * @author grandtech
 * @email 1992lcg@163.com
 * @date 2018-06-06 14:40:36
 */
public interface AreaService {
	
	AreaDO get(Long areaId);
	
	List<AreaDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AreaDO sysArea);
	
	int update(AreaDO sysArea);
	
	int remove(Long areaId);
	
	int batchRemove(Long[] areaIds);

	Tree<AreaDO> getTree();
	
	boolean checkAreaHasUser(Long areaId);

    AreaDO findAreaByDeptId(Long deptId);

	List<Tree<AreaDO>> getTreeByUserId(UserDO userDO);

    List<AreaDO> getChildAreaByAreaId(Long areaId);

	List<Long> getAreaIdsByUserId(Long userId);
}
