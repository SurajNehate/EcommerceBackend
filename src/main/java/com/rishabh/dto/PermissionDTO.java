package com.rishabh.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private int permissionId;

    @NotEmpty(message = "is required")
    @Size(min = 1, message = "is required")
    private String permissionName;

}
