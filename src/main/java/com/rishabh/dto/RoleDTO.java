package com.rishabh.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private int roleId;

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private String roleName;

}
