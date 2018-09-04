package com.ddian.youfan.admin.dao;

import com.ddian.youfan.admin.domain.AreaDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * @author grandtech
 * @email 1992lcg@163.com
 * @date 2018-06-06 14:40:36
 */
@Mapper
public interface AreaDao {

	AreaDO get(Long areaId);
	
	List<AreaDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(AreaDO area);
	
	int update(AreaDO area);
	
	int remove(Long areaId);
	
	int batchRemove(Long[] areaIds);
	
	Long[] listParentArea();
	
	int getAreaDeptNumber(Long areaId);

	//获取同一父节点下的最大tCode
	AreaDO getMaxTCodeByParentId(Long parentId);

	//获取当前编码区域及其所有子区域
	List<AreaDO> listByTCode(Long aLong);

	//获取所有子节点
    List<AreaDO> getChildAreaByAreaId(Long areaId);
}
