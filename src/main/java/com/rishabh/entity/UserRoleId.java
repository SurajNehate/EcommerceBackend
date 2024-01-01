package com.rishabh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleId implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;

}
