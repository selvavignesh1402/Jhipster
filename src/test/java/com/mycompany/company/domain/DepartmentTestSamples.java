package com.mycompany.company.domain;

import java.util.UUID;

public class DepartmentTestSamples {

    public static Department getDepartmentSample1() {
        return new Department().id("id1").dept_name("dept_name1").dept_sname("dept_sname1");
    }

    public static Department getDepartmentSample2() {
        return new Department().id("id2").dept_name("dept_name2").dept_sname("dept_sname2");
    }

    public static Department getDepartmentRandomSampleGenerator() {
        return new Department()
            .id(UUID.randomUUID().toString())
            .dept_name(UUID.randomUUID().toString())
            .dept_sname(UUID.randomUUID().toString());
    }
}
