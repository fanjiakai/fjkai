package com.ddian.youfan.admin.dao;

import com.ddian.youfan.admin.domain.UserAreaDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 *
 * @author grandtech
 * @email
 * @date 2018-06-06 14:40:36
 */
@Mapper
public interface UserAreaDao {

	UserAreaDO get(Long id);

	List<UserAreaDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserAreaDO userArea);

	int update(UserAreaDO userArea);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<Long> listAreaId(Long userId);

	int removeByUserId(Long userId);

	int batchSave(List<UserAreaDO> list);

	int batchRemoveByUserId(Long[] ids);
}
