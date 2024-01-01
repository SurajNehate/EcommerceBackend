package com.rishabh.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private int userId;

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private String userName;

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotEmpty(message = "is required")
    @Size(min = 5, message = "is required")
    private String password;

}
