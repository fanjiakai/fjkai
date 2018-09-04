package com.ddian.youfan.admin.controller;

import com.ddian.youfan.admin.domain.DictDO;
import com.ddian.youfan.admin.service.DictService;
import com.ddian.youfan.common.context.FilterContextHandler;
import com.ddian.youfan.common.utils.PageUtils;
import com.ddian.youfan.common.utils.Query;
import com.ddian.youfan.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: WenheZhu
 * @Description: 字典表维护
 * @Date: Created in 13:582018/6/21
 * @Modified By:
 */
@RequestMapping("/dict")
@RestController
public class DictController extends BaseController {

    @Autowired
    private DictService sysDictService;


    /**
     * 分页查询字典
     *
     * @param params
     * @return
     */
    @GetMapping()
    R listByPage(@RequestParam Map<String, Object> params) {
        Query query = new Query(params,false);
        List<Map> userList = sysDictService.list(query);
        int total = sysDictService.count(query);
        PageUtils pageUtil = new PageUtils(userList, total);
        return R.ok().put("page", pageUtil);
    }


    /**
     * 保存字典信息
     *
     * @param dict
     * @return
     */
    @PostMapping()
    @RequiresPermissions("admin:dict:edit")
    R save(@RequestBody DictDO dict) {
        dict.setCreateBy(FilterContextHandler.getUserID());
        dict.setCreateDate(new Date());
        return R.operate(sysDictService.save(dict) > 0);
    }


    @DeleteMapping()
    @RequiresPermissions("admin:dict:edit")
    R remove( Long id) {
        return R.operate (sysDictService.remove(id) > 0);
    }

    @PutMapping()
    @RequiresPermissions("admin:dict:edit")
    R update(@RequestBody DictDO dict) {
        return R.operate(sysDictService.update(dict) > 0);
    }



}
