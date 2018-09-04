package com.ddian.youfan.zuul.prc.admin;

import com.ddian.youfan.common.dto.MenuDTO;
import com.ddian.youfan.common.intercepter.FeignIntercepter;
import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Headers("Content-Type:application/json")
@FeignClient(name = "api-admin", configuration = FeignIntercepter.class)
public interface MenuService {
    @GetMapping("/menu/userMenus")
    List<MenuDTO> userMenus();
}
