package com.library.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private int userId;

    @Size(min = 3, max = 20, message = "Invalid Name")
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid Gender")
    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "About is required")
    private String about;

    private FileDataDTO image;

    private String roles;
}
