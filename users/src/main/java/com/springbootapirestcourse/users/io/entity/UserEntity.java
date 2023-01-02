package com.springbootapirestcourse.users.io.entity;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "users")
@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 4312023915966814242L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;
}
