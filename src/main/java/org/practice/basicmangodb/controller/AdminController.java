package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.annotations.isAdmin;
import org.practice.basicmangodb.service.admin.AdminService;
import org.practice.basicmangodb.service.admin.AdminServiceI;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/admincontroller")
@CrossOrigin(origins = "http://localhost:5173")
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
