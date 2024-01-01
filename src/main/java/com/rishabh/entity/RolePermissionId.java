package com.rishabh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionId implements Serializable {

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "permission_id")
    private int permissionId;
}
