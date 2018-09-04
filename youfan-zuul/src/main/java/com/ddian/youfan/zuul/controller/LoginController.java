package com.ddian.youfan.zuul.controller;

import com.ddian.youfan.zuul.prc.admin.MenuService;
import java.util.List;

/**
 * @version V1.0
 */
@RestController
public class LoginController {
    @Autowired
    MenuService menuService;

    @GetMapping({"/test"})
    List<MenuDTO> login(HttpServletRequest request) {
        FilterContextHandler.setToken(request.getHeader(CommonConstants.CONTEXT_TOKEN));
        return menuService.userMenus();
    }
}
