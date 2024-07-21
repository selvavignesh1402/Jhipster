package com.mycompany.company.domain;

import java.util.UUID;

public class CreateuserTestSamples {

    public static Createuser getCreateuserSample1() {
        return new Createuser()
            .id("id1")
            .rollNo("rollNo1")
            .userName("userName1")
            .password("password1")
            .department("department1")
            .designation("designation1")
            .email("email1");
    }

    public static Createuser getCreateuserSample2() {
        return new Createuser()
            .id("id2")
            .rollNo("rollNo2")
            .userName("userName2")
            .password("password2")
            .department("department2")
            .designation("designation2")
            .email("email2");
    }

    public static Createuser getCreateuserRandomSampleGenerator() {
        return new Createuser()
            .id(UUID.randomUUID().toString())
            .rollNo(UUID.randomUUID().toString())
            .userName(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString())
            .department(UUID.randomUUID().toString())
            .designation(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
