package org.practice.basicmangodb.service.admin;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

public interface AdminService {

    void deleteUser(WebRequest webRequest, String user);
}
