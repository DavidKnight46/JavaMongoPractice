package org.practice.basicmangodb.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

@Service
public class AdminServiceI implements AdminService{
    @Override
    public void deleteUser(WebRequest webRequest, String user) {
        System.out.println("smurf3");
    }
}
