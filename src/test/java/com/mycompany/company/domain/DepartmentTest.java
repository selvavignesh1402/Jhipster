package com.mycompany.company.domain;

import static com.mycompany.company.domain.CreateuserTestSamples.*;
import static com.mycompany.company.domain.DepartmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.company.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Department.class);
        Department department1 = getDepartmentSample1();
        Department department2 = new Department();
        assertThat(department1).isNotEqualTo(department2);

        department2.setId(department1.getId());
        assertThat(department1).isEqualTo(department2);

        department2 = getDepartmentSample2();
        assertThat(department1).isNotEqualTo(department2);
    }

    @Test
    void createuserTest() {
        Department department = getDepartmentRandomSampleGenerator();
        Createuser createuserBack = getCreateuserRandomSampleGenerator();

        department.setCreateuser(createuserBack);
        assertThat(department.getCreateuser()).isEqualTo(createuserBack);

        department.createuser(null);
        assertThat(department.getCreateuser()).isNull();
    }
}
