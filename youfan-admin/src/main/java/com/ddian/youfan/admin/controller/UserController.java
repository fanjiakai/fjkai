package com.ddian.youfan.admin.controller;

import com.ddian.youfan.admin.domain.UserDO;
import com.ddian.youfan.admin.dto.UserDTO;
import com.ddian.youfan.admin.dto.do2dto.UserConvert;
import com.ddian.youfan.admin.service.RoleService;
import com.ddian.youfan.admin.service.UserService;
import com.ddian.youfan.admin.utils.MD5Utils;
import com.ddian.youfan.admin.vo.UserVO;
import com.ddian.youfan.common.context.FilterContextHandler;
import com.ddian.youfan.common.dto.LoginUserDTO;
import com.ddian.youfan.common.utils.PageUtils;
import com.ddian.youfan.common.utils.Query;
import com.ddian.youfan.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *
 */
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	/**
	 * 登录的当前用户，前台需要验证用户登录的页面可以调用此方法
	 * @return
	 */
	@GetMapping("/currentUser")
	LoginUserDTO currentUser(){
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setUserId(FilterContextHandler.getUserID());
		loginUserDTO.setUsername(FilterContextHandler.getUsername());
		loginUserDTO.setDeptId(FilterContextHandler.getDeptID());
		loginUserDTO.setName(FilterContextHandler.getName());
		return loginUserDTO;
	}

	/**
	 * 根据用户id获取用户
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	R get(@PathVariable("id") Long id ){
		UserDTO userDTO = UserConvert.MAPPER.do2dto(userService.get(id));
		return R.ok().put("data",userDTO);
	}

	/**
	 * 分页查询用户
	 * @param params
	 * @return
	 */
	@GetMapping()
	R listByPage(@RequestParam Map<String, Object> params) {
		Query query = new Query(params,true);
		List<UserDTO> userDTOS = UserConvert.MAPPER.dos2dtos((userService.list(query)));
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(userDTOS, total);
		return R.ok().put("page",pageUtil);
	}

	@PostMapping("/updatePwd")
	R updatePwd(@RequestBody UserVO user){
		int result = userService.resetPwd(user);
		if(result==-1){
			return R.error("原密码不正确");
		}
		return R.ok();
	}

	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	@PostMapping()
	R save(@RequestBody UserDO user) {
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		return R.operate(userService.save(user) > 0);
	}

	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@PutMapping()
	R update(@RequestBody UserDO user) {
		return R.operate(userService.update(user) > 0);
	}


	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@DeleteMapping()
	R remove( Long id) {
		return R.operate (userService.remove(id) > 0);
	}


	/**
	 * 批量删除
	 * @param userIds
	 * @return
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exits")
	@ResponseBody
	boolean exits(@RequestParam Map<String, Object> params) {
		return !userService.exits(params);
	}
}
