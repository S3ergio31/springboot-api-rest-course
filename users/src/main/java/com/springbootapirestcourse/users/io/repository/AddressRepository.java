package com.springbootapirestcourse.users.io.repository;

import com.springbootapirestcourse.users.io.entity.AddressEntity;
import com.springbootapirestcourse.users.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    public List<AddressEntity> findAllByUserDetails(UserEntity userDetails);
}
