package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "username is required")
    @Size(min=3, message="username must be at least 3 characters long")
    private String name;

    @Email(message = "please enter a valid email address")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min=3, message="password must be at least 3 characters long")
    private String password;

    @NotBlank(message = "phone number is required")
    private String phoneNumber;

    @NotBlank(message = "about is required")
    private String about;
}
