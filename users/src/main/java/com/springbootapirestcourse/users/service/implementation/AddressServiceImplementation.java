package com.springbootapirestcourse.users.service.implementation;

import com.google.common.reflect.TypeToken;
import com.springbootapirestcourse.users.io.entity.AddressEntity;
import com.springbootapirestcourse.users.io.entity.UserEntity;
import com.springbootapirestcourse.users.io.repository.AddressRepository;
import com.springbootapirestcourse.users.io.repository.UserRepository;
import com.springbootapirestcourse.users.model.response.AddressRest;
import com.springbootapirestcourse.users.service.AddressService;
import com.springbootapirestcourse.users.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class AddressServiceImplementation implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AddressDto> findUserAddresses(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);
        List<AddressEntity> userAddresses = addressRepository.findAllByUserDetails(userEntity);
        Type listType = new TypeToken<List<AddressDto>>() {}.getType();
        return modelMapper.map(userAddresses, listType);
    }
}
