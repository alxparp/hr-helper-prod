package com.hrsol.helper.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotEmpty(message = "Username should not be empty")
    private String username;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date must be in the past")
    @NotNull(message = "Date should not be empty")
    private Date birthDate;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    private String userStatus;
    private String location;

}
