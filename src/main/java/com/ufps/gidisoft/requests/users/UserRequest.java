package com.ufps.gidisoft.requests.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String usercode;
    private Long roleId;
}
