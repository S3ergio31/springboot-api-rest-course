package com.springbootapirestcourse.users.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UserDatailsRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<AddressRequestModel> addresses;
}
