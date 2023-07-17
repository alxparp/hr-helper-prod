package com.hrsol.helper.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotEmpty(message = "Username should not be empty")
    private String username;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private String firstName;
    private String lastName;
    private Date hireDate;
    private Date birthDate;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    private String userStatus;
    private String location;

}
