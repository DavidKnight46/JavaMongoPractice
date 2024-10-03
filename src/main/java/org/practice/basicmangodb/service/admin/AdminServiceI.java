package org.practice.basicmangodb.service.admin;

import org.practice.basicmangodb.exceptions.InsufficientPrivilagesException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@Service
public class AdminServiceI implements AdminService{
    @Override
    public void deleteUser(WebRequest webRequest, String user) {
        if(Objects.requireNonNull(webRequest.getHeader("isAdmin")).contentEquals("true")){
            System.out.println("smurf3");
        } else {
            throw new InsufficientPrivilagesException(String.format("%s is not admin. You should not be here!!!!", user));
        }
    }
}
