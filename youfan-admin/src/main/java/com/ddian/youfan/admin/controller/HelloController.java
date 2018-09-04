package com.ddian.youfan.admin.controller;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @PostMapping("/getChildAreaByAreaId")
    public String index(@RequestParam Long areaId) {
        System.out.println("hello "+areaId+"，this is first messge");
        return "hello "+areaId+"，this is first messge";
    }
}
