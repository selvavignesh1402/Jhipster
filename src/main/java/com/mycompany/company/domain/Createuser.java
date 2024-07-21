package com.mycompany.company.domain;

import com.mycompany.company.domain.enumeration.role;
import com.mycompany.company.domain.enumeration.status;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Createuser.
 */
@Document(collection = "createuser")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Createuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull(message = "must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Field("roll_no")
    private String rollNo;

    @NotNull(message = "must not be null")
    @Field("user_name")
    private String userName;

    @NotNull(message = "must not be null")
    @Field("password")
    private String password;

    // @NotNull(message = "must not be null")
    // @Field("department")
    // private String department;

    @NotNull(message = "must not be null")
    @Field("designation")
    private String designation;

    @NotNull(message = "must not be null")
    @Field("email")
    private String email;

    @Field("user_image")
    private byte[] userImage;

    @Field("user_image_content_type")
    private String userImageContentType;

    @NotNull(message = "must not be null")
    @Field("role_status")
    private status roleStatus;

    @NotNull(message = "must not be null")
    @Field("role")
    private role role;

    @DBRef
    @Field(name = "department")
    private Department department;

    private Set<Department> departments = new HashSet<>();

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Createuser addDepartment(Department department) {
        this.departments.add(department);
        return this;
    }

    public Createuser removeDepartment(Department department) {
        this.departments.remove(department);
        return this;
    }

    public Createuser departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Createuser id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRollNo() {
        return this.rollNo;
    }

    public Createuser rollNo(String rollNo) {
        this.setRollNo(rollNo);
        return this;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getUserName() {
        return this.userName;
    }

    public Createuser userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public Createuser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return this.department;
    }

    public Createuser department(Department string) {
        this.setDepartment(string);
        return this;
    }

    public void setDepartment(Department string) {
        this.department = string;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Createuser designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return this.email;
    }

    public Createuser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getUserImage() {
        return this.userImage;
    }

    public Createuser userImage(byte[] userImage) {
        this.setUserImage(userImage);
        return this;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public String getUserImageContentType() {
        return this.userImageContentType;
    }

    public Createuser userImageContentType(String userImageContentType) {
        this.userImageContentType = userImageContentType;
        return this;
    }

    public void setUserImageContentType(String userImageContentType) {
        this.userImageContentType = userImageContentType;
    }

    public status getRoleStatus() {
        return this.roleStatus;
    }

    public Createuser roleStatus(status roleStatus) {
        this.setRoleStatus(roleStatus);
        return this;
    }

    public void setRoleStatus(status roleStatus) {
        this.roleStatus = roleStatus;
    }

    public role getRole() {
        return this.role;
    }

    public Createuser role(role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(role role) {
        this.role = role;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Createuser)) {
            return false;
        }
        return getId() != null && getId().equals(((Createuser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Createuser{" +
            "id=" + getId() +
            ", rollNo='" + getRollNo() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", department='" + getDepartment() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", email='" + getEmail() + "'" +
            ", userImage='" + getUserImage() + "'" +
            ", userImageContentType='" + getUserImageContentType() + "'" +
            ", roleStatus='" + getRoleStatus() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }

    public Createuser department(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'department'");
    }
}
