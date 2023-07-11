package com.loginjwt.demo.DTO;

import com.loginjwt.demo.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    User user;
    String accessToken;
}
