package com.rishabh.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor(onConstructor_={@NonNull} )
@ToString
@Builder
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int permissionId;

    @Column(name = "permission_name", nullable = false)
    @NonNull
    private String permissionName;

}
