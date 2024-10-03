package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.exceptions.InsufficientPrivilagesException;
import org.practice.basicmangodb.service.admin.AdminService;
import org.practice.basicmangodb.service.admin.AdminServiceI;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@RestController
@RequestMapping("/admincontroller")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminServiceI adminService){
        this.adminService = adminService;
    }

    @MyValidator
    private WebRequest isAdmin;

    @DeleteMapping("/deleteUser")
    public void deleteUser(WebRequest webRequest,
                           @RequestParam String user){
        this.isAdmin = webRequest;

        adminService.deleteUser(webRequest, user);
    }
}
