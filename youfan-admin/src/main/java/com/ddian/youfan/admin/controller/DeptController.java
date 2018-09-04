package com.ddian.youfan.admin.controller;

import com.ddian.youfan.admin.domain.DeptDO;
import com.ddian.youfan.admin.domain.Tree;
import com.ddian.youfan.admin.service.DeptService;
import com.ddian.youfan.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author grandtech
 * @email 1992lcg@163.com
 * @date 2018-06-06 14:40:36
 */

@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {
	private String prefix = "system/dept";
	@Autowired
	private DeptService sysDeptService;

	@GetMapping()
	@RequiresPermissions("admin:dept:dept")
	String dept() {
		return prefix + "/dept";
	}

	@ResponseBody
	@GetMapping("/list")
	public List<DeptDO> list() {
		Map<String, Object> query = new HashMap<>(16);
		List<DeptDO> sysDeptList = sysDeptService.list(query);
		return sysDeptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("admin:dept:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总部门");
		} else {
			model.addAttribute("pName", sysDeptService.get(pId).getName());
		}
		return  prefix + "/add";
	}


	/**
	 * 保存
	 */

	@PostMapping()
	@RequiresPermissions("admin:dept:add")
	public R save(@RequestBody DeptDO sysDept) {
		if (sysDeptService.save(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@PutMapping()
	public R update(@RequestBody DeptDO sysDept) {
		if (sysDeptService.update(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@DeleteMapping
	@ResponseBody
	public R remove(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", String.valueOf(id));
		if(sysDeptService.count(map)>0) {
			return R.error(1, "包含下级机构,不允许修改");
		}
		if(sysDeptService.checkDeptHasUser(id)) {
			if (sysDeptService.remove(id) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "机构包含用户,不允许删除");
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("admin:dept:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] deptIds) {
		sysDeptService.batchRemove(deptIds);
		return R.ok();
	}

	@GetMapping("/tree")
	@ResponseBody
	public List<Tree<DeptDO>> tree() {
		Tree<DeptDO> tree = sysDeptService.getTree();
		if(tree.getId() != null && "-1".equals(tree.getId())){
			return tree.getChildren();
		}else{
			List<Tree<DeptDO>> treeList = new ArrayList<>();
			treeList.add(tree);
			return treeList;
		}
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/deptTree";
	}

}
