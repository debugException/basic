package com.xh.basic.model;

import lombok.Data;

import java.util.Date;

@Data
public class StudentInfo {
    private Integer id;

    private String userName;

    private String password;

    private Date birthday;

    private Byte sex;
}