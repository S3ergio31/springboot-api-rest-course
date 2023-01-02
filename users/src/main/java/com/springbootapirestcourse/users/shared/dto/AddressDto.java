package com.springbootapirestcourse.users.shared.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AddressDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -3721305955995671161L;
    private Long id;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private UserDto userDetails;
    private String addressId;
}
