package com.springbootapirestcourse.users.model.response;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class AddressRest extends RepresentationModel<AddressRest> {
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private String addressId;
}
