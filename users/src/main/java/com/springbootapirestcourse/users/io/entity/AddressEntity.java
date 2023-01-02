package com.springbootapirestcourse.users.io.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity(name = "addresses")
@Data
public class AddressEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 6761659378780387962L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 30, nullable = false)
    private String addressId;
    @Column(length = 15, nullable = false)
    private String city;
    @Column(length = 15, nullable = false)
    private String country;
    @Column(length = 100, nullable = false)
    private String streetName;
    @Column(length = 7, nullable = false)
    private String postalCode;
    @Column(length = 20, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;
}
