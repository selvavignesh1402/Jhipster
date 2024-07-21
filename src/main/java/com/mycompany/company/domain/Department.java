package com.mycompany.company.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.company.domain.enumeration.dept_st;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Department.
 */
@Document(collection = "department")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Field("dept_name")
    private String dept_name;

    @NotNull(message = "must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Field("dept_sname")
    private String dept_sname;

    @NotNull(message = "must not be null")
    @Field("dept_status")
    private dept_st dept_status;

    @Field("date")
    private Instant date;

    @Field("createuser")
    @JsonIgnoreProperties(value = { "departments" }, allowSetters = true)
    private Createuser createuser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Department id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept_name() {
        return this.dept_name;
    }

    public Department dept_name(String dept_name) {
        this.setDept_name(dept_name);
        return this;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_sname() {
        return this.dept_sname;
    }

    public Department dept_sname(String dept_sname) {
        this.setDept_sname(dept_sname);
        return this;
    }

    public void setDept_sname(String dept_sname) {
        this.dept_sname = dept_sname;
    }

    public dept_st getDept_status() {
        return this.dept_status;
    }

    public Department dept_status(dept_st dept_status) {
        this.setDept_status(dept_status);
        return this;
    }

    public void setDept_status(dept_st dept_status) {
        this.dept_status = dept_status;
    }

    public Instant getDate() {
        return this.date;
    }

    public Department date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Createuser getCreateuser() {
        return this.createuser;
    }

    public void setCreateuser(Createuser createuser) {
        this.createuser = createuser;
    }

    public Department createuser(Createuser createuser) {
        this.setCreateuser(createuser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return getId() != null && getId().equals(((Department) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", dept_name='" + getDept_name() + "'" +
            ", dept_sname='" + getDept_sname() + "'" +
            ", dept_status='" + getDept_status() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
