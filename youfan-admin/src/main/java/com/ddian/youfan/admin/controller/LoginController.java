package com.ddian.youfan.admin.controller;

import com.ddian.youfan.admin.domain.UserDO;
import com.ddian.youfan.admin.service.*;
import com.ddian.youfan.admin.utils.MD5Utils;
import com.ddian.youfan.common.context.FilterContextHandler;
import com.ddian.youfan.common.dto.LoginDTO;
import com.ddian.youfan.common.dto.UserToken;
import com.ddian.youfan.common.utils.JwtUtils;
import com.ddian.youfan.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 * @author gykj
 * @version V1.0
 */
@RequestMapping()
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    MenuService menuService;
    @Autowired
    DeptService deptService;
    @Autowired
    AreaService areaService;

    @PostMapping("/login")
    R login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        String username = loginDTO.getUsername().trim();
        String password = loginDTO.getPwd().trim();
        password = MD5Utils.encrypt(username, password);
        Map<String, Object> param = new HashMap<>();
        param.put("username", username);
        UserDO userDO = userService.getByUserName(username);
        if(userDO==null){
            return R.error("用户或密码错误");
        }
        if (null == userDO || !userDO.getPassword().equals(password)) {
            return R.error("用户或密码错误");
        }
        UserToken userToken = new UserToken(userDO.getUsername(), userDO.getUserId().toString(), userDO.getName(),userDO.getDeptId().toString());
        String token="";
        try {
            token = JwtUtils.generateToken(userToken, 300*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //首先清除用户缓存权限
        menuService.clearCache(userDO.getUserId());
     /*   List<Long> areaList = userService.getUserAreaList(userDO.getUserId());*/
        return R.ok("登录成功").put("token", token).put("user",userDO).put("router",menuService.RouterDTOsByUserId(userDO.getUserId()))/*.put("area",areaList).put("area",areaService.findAreaByDeptId(userDO.getDeptId()))*//*.put("areaTree",areaService.getTreeByUserId(userDO))*/;
    }


    @RequestMapping("/logout")
    R logout(HttpServletRequest request, HttpServletResponse response) {
        menuService.clearCache(Long.parseLong(FilterContextHandler.getUserID()));
        return R.ok();
    }


}
