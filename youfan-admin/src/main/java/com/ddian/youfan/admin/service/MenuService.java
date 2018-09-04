package com.ddian.youfan.admin.service;

import com.ddian.youfan.admin.domain.MenuDO;
import com.ddian.youfan.admin.domain.Tree;
import com.ddian.youfan.common.dto.RouterDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public interface MenuService {
	Tree<MenuDO> getSysMenuTree(Long id);

	/**
	 * 登录用户的权限
	 * @author bootdo 1992lcg@163.com
	 * @param userId
	 * @return
	 */
	List<MenuDO> userMenus(Long userId);

	List<Tree<MenuDO>> listMenuTree(Long id);

	Tree<MenuDO> getTree();

	Tree<MenuDO> getTree(Long id);

	@CacheEvict(value = "permission",key = "#userId")
	boolean clearCache(Long userId);

	List<MenuDO> list(Map<String, Object> params);

	int remove(Long id);

	int save(MenuDO menu);

	int update(MenuDO menu);

	MenuDO get(Long id);

	Set<String> listPerms(Long userId);

	/**
	 * 获取角色下的权限所有id
	 * @param roleId
	 * @return
	 */
	List<Long> MenuIdsByRoleId(Long roleId);

	/**
	 * 用户的路由
	 * @return
	 */
	List<RouterDTO> RouterDTOsByUserId(Long userId);
}
