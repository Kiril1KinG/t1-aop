package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserDto {
    private String name;
    private String email;
    private String phone;
}
