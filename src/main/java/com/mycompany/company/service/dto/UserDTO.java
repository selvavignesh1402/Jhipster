package com.mycompany.company.service.dto;

import com.mycompany.company.domain.User;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String login;
    private String companyName; // Added field
    private String companyShortName; // Added field
    private String licence; // Added field

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        // Populate new fields from the User entity
        this.companyName = user.getCompanyName();
        this.companyShortName = user.getCompanyShortName();
        this.licence = user.getLicence();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDTO userDTO = (UserDTO) o;
        if (userDTO.getId() == null || getId() == null) {
            return false;
        }

        return (
            Objects.equals(getId(), userDTO.getId()) &&
            Objects.equals(getLogin(), userDTO.getLogin()) &&
            Objects.equals(getCompanyName(), userDTO.getCompanyName()) &&
            Objects.equals(getCompanyShortName(), userDTO.getCompanyShortName()) &&
            Objects.equals(getLicence(), userDTO.getLicence())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getCompanyName(), getCompanyShortName(), getLicence());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", companyName='" + companyName + '\'' +
            ", companyShortName='" + companyShortName + '\'' +
            ", licence='" + licence + '\'' +
            "}";
    }
}
