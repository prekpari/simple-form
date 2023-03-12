package com.simpleform.repository;

import com.simpleform.model.Users;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByLoginAndPassword(String login, String password);

    @Query(value = "select count(id) from users where email=:email", nativeQuery = true)
    Integer countByEmail(String email);
}
