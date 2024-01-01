package com.rishabh.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesDTO {

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private int userId;

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private int roleId;
}
