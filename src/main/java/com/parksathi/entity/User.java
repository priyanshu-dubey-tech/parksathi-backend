package com.parksathi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    @Column(unique = true)
    private String mobile;

    private String password;

    private Boolean verified = true;

    @Column(unique = true)
    private String email;

    private Boolean emailVerified = false;


}
