package com.ddian.youfan.admin.dao;

import com.ddian.youfan.admin.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 * @author grandtech
 * @email 1992lcg@163.com
 * @date 2018-06-06 14:40:36
 */
@Mapper
public interface UserDao {

	UserDO get(Long userId);

	UserDO getByUserName(String userName);
	
	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long userId);

	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();

}
