package com.rishabh.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotEmpty(message = "User Name is mandatory")
    private String userName;

    @NotEmpty(message = "Password is mandatory")
    private String password;

}
