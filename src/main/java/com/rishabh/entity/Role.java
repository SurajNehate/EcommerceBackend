package com.rishabh.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor_={@NonNull})
@NoArgsConstructor
@Entity
@Builder
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int roleId;

    @Column(name = "role_name" , nullable = false)
    @NonNull
    private String roleName;

}
