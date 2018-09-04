package com.ddian.youfan.admin.controller;

import com.ddian.youfan.admin.domain.RoleDO;
import com.ddian.youfan.admin.service.RoleService;
import com.ddian.youfan.common.context.FilterContextHandler;
import com.ddian.youfan.common.utils.PageUtils;
import com.ddian.youfan.common.utils.Query;
import com.ddian.youfan.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: WenheZhu
 * @Description: 角色管理Controller
 * @Date: Created in 14:122018/8/20
 * @Modified By:
 */
@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping()
    PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params,true);
        List<RoleDO> roleDOS = roleService.list(query);
        int total = roleService.count(query);
        PageUtils pageUtil = new PageUtils(roleDOS, total);
        return pageUtil;
    }

    @GetMapping("/userId/{userId}")
    List<Long> roleIdByUserId(@PathVariable Long userId){
        return roleService.RoleIdsByUserId(userId);
    }

    @PostMapping()
    R save(@RequestBody RoleDO roleDO){
        roleDO.setCreateDeptId(Long.valueOf(FilterContextHandler.getDeptID()));
        roleDO.setCreateUserId(Long.valueOf(FilterContextHandler.getUserID()));
        if(roleService.save(roleDO)>0){
            return R.ok();
        }
        return R.error();
    }

    @PutMapping()
    R update(@RequestBody RoleDO roleDO){
        if(roleService.update(roleDO)>0){
            return R.ok();
        }
        return R.error();
    }

    @DeleteMapping()
    /*@ResponseBody*/
    R remove(Long id) {
        if(roleService.remove(id)>0){
            return R.ok();
        }
        return R.error();
    }

}