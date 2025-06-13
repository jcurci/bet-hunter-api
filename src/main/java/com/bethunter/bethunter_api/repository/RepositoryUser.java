package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface RepositoryUser extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}
