package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.annotations.isAdmin;
import org.practice.basicmangodb.service.admin.AdminService;
import org.practice.basicmangodb.service.admin.AdminServiceI;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/admincontroller")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminServiceI adminService){
        this.adminService = adminService;
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@isAdmin WebRequest webRequest,
                           @RequestParam String user){
        adminService.deleteUser(webRequest, user);
    }
}
