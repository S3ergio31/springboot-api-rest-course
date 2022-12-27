package com.springbootapirestcourse.users.io.repository;

import com.springbootapirestcourse.users.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);
}
