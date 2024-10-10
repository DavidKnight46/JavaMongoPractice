package org.practice.basicmangodb.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class User {

    private String username;
    private String alias;
    private String email;
    private boolean isAdmin;
}
