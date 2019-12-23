package com.mloine.msuser.repository;

import com.mloine.msuser.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String userName, String password);
}
