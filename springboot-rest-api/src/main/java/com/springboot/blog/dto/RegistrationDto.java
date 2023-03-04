package com.springboot.blog.dto;

import lombok.Data;

@Data
public class RegistrationDto {
     private String name;
     private String username;
     private String email;
     private String password;
}
