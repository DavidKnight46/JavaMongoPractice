package org.practice.basicmangodb.controller;

import org.practice.basicmangodb.exceptions.InsufficientPrivilagesException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@RestController
@RequestMapping("/admincontroller")
public class AdminController {

    @MyValidator
    private WebRequest isAdmin;

    @DeleteMapping("/deleteUser")
    public void deleteUser(WebRequest webRequest,
                           @RequestParam String user){
        this.isAdmin = webRequest;

        if(Objects.requireNonNull(webRequest.getHeader("isAdmin")).contentEquals("true")){
            System.out.println("smurf3");
        } else {
            throw new InsufficientPrivilagesException("");
        }

        System.out.println("smurf");
    }
}
