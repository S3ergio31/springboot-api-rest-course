package com.springbootapirestcourse.users.service;

import com.springbootapirestcourse.users.shared.dto.AddressDto;
import java.util.List;

public interface AddressService {
    public List<AddressDto> findUserAddresses(String id);

    public AddressDto findByAddressId(String addressId);
}
