package com.sportsbook.sportsbookdemo.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registration", schema="sportsbook")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "ssn", nullable = false)
    private String ssn;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    String password;

}