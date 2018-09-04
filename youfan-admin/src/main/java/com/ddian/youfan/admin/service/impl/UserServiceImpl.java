package com.ddian.youfan.admin.service.impl;


import com.ddian.youfan.admin.dao.DeptDao;
import com.ddian.youfan.admin.dao.UserAreaDao;
import com.ddian.youfan.admin.dao.UserDao;
import com.ddian.youfan.admin.dao.UserRoleDao;
import com.ddian.youfan.admin.domain.*;
import com.ddian.youfan.admin.service.UserService;
import com.ddian.youfan.admin.utils.*;
import com.ddian.youfan.admin.utils.BuildTree;
import com.ddian.youfan.admin.utils.MD5Utils;
import com.ddian.youfan.admin.vo.UserVO;
import com.ddian.youfan.common.Constants.CommonConstants;
import com.ddian.youfan.common.context.FilterContextHandler;
import com.ddian.youfan.common.utils.DataScopeHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Author Zhuwenhe
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
	@Autowired
    UserDao userMapper;
	@Autowired
    UserRoleDao userRoleMapper;
	@Autowired
	UserAreaDao userAreaMapper;
	@Autowired
	DeptDao deptMapper;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDO get(Long id) {
		List<Long> roleIds = userRoleMapper.listRoleId(id);
		UserDO user = userMapper.get(id);
		user.setDeptName(deptMapper.get(user.getDeptId()).getName());
		user.setroleIds(roleIds);
		return user;
	}

	@Override
	public UserDO getByUserName(String userName) {
		return userMapper.getByUserName(userName);
	}

	@Override
	public List<UserDO> list(Map<String, Object> map) {
		map.put("scopeFilter", DataScopeHelper.dataScopeSubFilter(FilterContextHandler.getUserID(),FilterContextHandler.getDeptID(), CommonConstants.DATA_SCOPE_DEPT_AND_CHILD,"u","dept_id"));
		return  userMapper.list(map);
	}

	@Override
	public List<Long> getUserAreaList(Long userId) {
		List<Long> result = userAreaMapper.listAreaId(userId);
		return result;
	}

	@Override
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}

	@Override
	public int save(UserDO user) {
		int count = userMapper.save(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		List<Long> areas = user.getAreaIds();
		userRoleMapper.removeByUserId(userId);
		userAreaMapper.removeByUserId(userId);
		List<UserRoleDO> list = new ArrayList<>();
		List<UserAreaDO> areaList = new ArrayList<>();
		for (Long roleId : roles) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		for (Long areaId : areas) {
			UserAreaDO ua = new UserAreaDO();
			ua.setUserId(userId);
			ua.setAreaId(areaId);
			areaList.add(ua);
		}
		if (list.size() > 0) {
			userRoleMapper.batchSave(list);
		}
		if (areaList.size() > 0) {
			userAreaMapper.batchSave(areaList);
		}
		return count;
	}

	@Override
	public int update(UserDO user) {
		int r = userMapper.update(user);
		Long userId = user.getUserId();
		List<Long> roles = user.getroleIds();
		List<Long> areas = user.getAreaIds();
		if(null!=roles){
			userRoleMapper.removeByUserId(userId);
			List<UserRoleDO> list = new ArrayList<>();
			for (Long roleId : roles) {
				UserRoleDO ur = new UserRoleDO();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				userRoleMapper.batchSave(list);
			}
		}
		if(null!=areas){
			userAreaMapper.removeByUserId(userId);
			List<UserAreaDO> list = new ArrayList<>();
			for (Long areaId : areas) {
				UserAreaDO ua = new UserAreaDO();
				ua.setUserId(userId);
				ua.setAreaId(areaId);
				list.add(ua);
			}
			if (list.size() > 0) {
				userAreaMapper.batchSave(list);
			}
		}
		return r;
	}

	@Override
	public int remove(Long userId) {
		userRoleMapper.removeByUserId(userId);
		return userMapper.remove(userId);
	}

	@Override
	public boolean exits(Map<String, Object> params) {
		boolean exits = userMapper.list(params).size() > 0;
		return exits;
	}

	@Override
	public Set<String> listRoles(Long userId) {
		return null;
	}

	@Override
	public int resetPwd(UserVO userVO) {
		UserDO oldUser = userMapper.get(Long.valueOf(FilterContextHandler.getUserID()));
		if(oldUser.getPassword().equals(MD5Utils.encrypt(oldUser.getUsername(),userVO.getPwdOld()))){
			oldUser.setPassword(MD5Utils.encrypt(oldUser.getUsername(),userVO.getPwdNew()));
			return userMapper.update(oldUser);
		}else{
			return -1;
		}
	}

	@Override
	public int adminResetPwd(UserVO userVO) throws Exception {
		return 0;


	}

	@Transactional
	@Override
	public int batchremove(Long[] userIds) {
		int count = userMapper.batchRemove(userIds);
		userRoleMapper.batchRemoveByUserId(userIds);
		return count;
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
		Long[] pDepts = deptMapper.listParentDept();
		Long[] uDepts = userMapper.listAllDept();
		Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
		for (DeptDO dept : depts) {
			if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
				continue;
			}
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(dept.getDeptId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "dept");
			tree.setState(state);
			trees.add(tree);
		}
		List<UserDO> users = userMapper.list(new HashMap<String, Object>(16));
		for (UserDO user : users) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(user.getUserId().toString());
			tree.setParentId(user.getDeptId().toString());
			tree.setText(user.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public int updatePersonal(UserDO userDO) {
		return userMapper.update(userDO);
	}

	@Override
	public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
		return null;
	}


}
