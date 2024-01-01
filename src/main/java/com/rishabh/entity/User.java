package com.rishabh.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name" , nullable = false)
    private String lastName;

    @Column(name = "user_name" , nullable = false,unique = true)
    private String userName;

    @Column(name = "email" , nullable = false , unique = true)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "enabled" ,nullable = false)
    private boolean enabled;

}
