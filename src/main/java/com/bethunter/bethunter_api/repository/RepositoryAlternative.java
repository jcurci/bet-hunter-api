package com.bethunter.bethunter_api.repository;

import com.bethunter.bethunter_api.model.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryAlternative extends JpaRepository<Alternative, String> {
}
