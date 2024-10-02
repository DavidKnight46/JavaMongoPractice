package org.practice.basicmangodb.controller;

import jakarta.validation.constraints.NotNull;
import org.practice.basicmangodb.exceptions.InsufficientPrivilagesException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpRequest;
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
