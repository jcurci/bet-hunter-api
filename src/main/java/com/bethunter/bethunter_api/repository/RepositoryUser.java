package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryUser extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCellphone(String cellphone);
}
