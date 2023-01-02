package com.springbootapirestcourse.users.model.response;

import lombok.Data;

@Data
public class AddressRest {
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private String addressId;
}
