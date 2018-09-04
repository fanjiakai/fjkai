package com.ddian.youfan.admin.controller;

import com.ddian.youfan.admin.domain.AreaDO;
import com.ddian.youfan.admin.domain.Tree;
import com.ddian.youfan.admin.service.AreaService;
import com.ddian.youfan.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

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
@RequestMapping("/area")
public class AreaController extends BaseController {
	private String prefix = "system/area";
	@Autowired
	private AreaService areaService;

	@GetMapping()
	@RequiresPermissions("admin:area:area")
	String area() {
		return prefix + "/area";
	}

	@ResponseBody
	@GetMapping("/list")
	public List<AreaDO> list() {
		Map<String, Object> query = new HashMap<>(16);
		List<AreaDO> sysAreaList = areaService.list(query);
		return sysAreaList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("admin:area:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总部门");
		} else {
			model.addAttribute("pName", areaService.get(pId).getName());
		}
		return  prefix + "/add";
	}


	/**
	 * 保存
	 */
	@PostMapping()
	@RequiresPermissions("admin:area:add")
	public R save(@RequestBody AreaDO sysArea) {
		if (areaService.save(sysArea) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@PutMapping()
	public R update(@RequestBody AreaDO sysArea) {
		if (areaService.update(sysArea) > 0) {
			return R.ok();
		}
		return R.error();
	}

	// 根据用户查询区域
	@GetMapping("/userId/{userId}")
	List<Long> areaIdByUserId(@PathVariable Long userId){
		return areaService.getAreaIdsByUserId(userId);
	}

	/**
	 * 删除
	 */
	@DeleteMapping
	@ResponseBody
	@RequiresPermissions("admin:area:remove")
	public R remove(@RequestParam Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", id);
		if(areaService.count(map)>0) {
			return R.error(1, "区域下级部门,不允许修改");
		}
		if(areaService.checkAreaHasUser(id)) {
			if (areaService.remove(id) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "区域包含用户,不允许修改");
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("admin:sysArea:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] areaIds) {
		areaService.batchRemove(areaIds);
		return R.ok();
	}

	@GetMapping("/tree")
	@ResponseBody
	public List<Tree<AreaDO>> tree() {
		Tree<AreaDO> tree = areaService.getTree();
		if(tree.getId() != null && "-1".equals(tree.getId())){
			return tree.getChildren();
		}else{
			List<Tree<AreaDO>> treeList = new ArrayList<>();
			treeList.add(tree);
			return treeList;
		}
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/areaTree";
	}


	@PostMapping("/getChildAreaByAreaId")
	public List<AreaDO> getChildAreaByAreaId(@RequestParam Long areaId) {
		System.out.println("hello "+areaId+"，this is first messge");
		List<AreaDO> areaList = areaService.getChildAreaByAreaId(areaId);
		return areaList;
	}

}
