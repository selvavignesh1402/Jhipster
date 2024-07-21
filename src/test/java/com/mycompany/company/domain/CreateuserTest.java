package com.mycompany.company.domain;

import static com.mycompany.company.domain.CreateuserTestSamples.*;
import static com.mycompany.company.domain.DepartmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.company.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CreateuserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Createuser.class);
        Createuser createuser1 = getCreateuserSample1();
        Createuser createuser2 = new Createuser();
        assertThat(createuser1).isNotEqualTo(createuser2);

        createuser2.setId(createuser1.getId());
        assertThat(createuser1).isEqualTo(createuser2);

        createuser2 = getCreateuserSample2();
        assertThat(createuser1).isNotEqualTo(createuser2);
    }

    @Test
    void departmentTest() {
        Createuser createuser = getCreateuserRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        createuser.addDepartment(departmentBack);
        assertThat(createuser.getDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getCreateuser()).isEqualTo(createuser);

        createuser.removeDepartment(departmentBack);
        assertThat(createuser.getDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getCreateuser()).isNull();

        createuser.departments(new HashSet<>(Set.of(departmentBack)));
        assertThat(createuser.getDepartments()).containsOnly(departmentBack);
        assertThat(departmentBack.getCreateuser()).isEqualTo(createuser);

        createuser.setDepartments(new HashSet<>());
        assertThat(createuser.getDepartments()).doesNotContain(departmentBack);
        assertThat(departmentBack.getCreateuser()).isNull();
    }
}
