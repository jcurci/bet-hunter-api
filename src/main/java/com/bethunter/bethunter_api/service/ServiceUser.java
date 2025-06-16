package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.user.RouleteResponseReward;
import com.bethunter.bethunter_api.dto.user.UserRequestUpdate;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ServiceUser {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private ServiceToken serviceToken;

    public List<User> findAll() {
        return repositoryUser.findAll();
    }

    public Optional<User> findById(String id) {
        return repositoryUser.findById(id);
    }

    public Optional<User> update(String id, UserRequestUpdate dto) {
        repositoryUser.findById(id)
                .map(user -> {
                    user.setEmail(dto.email());
                    user.setName(dto.name());
                    user.setCellphone(dto.cellphone());
                    user.setMoney(dto.money());
                    user.setPoints(dto.points());
                    return repositoryUser.save(user);
                });

        return null;
    }

    public boolean delete(String id) {
        if (repositoryUser.existsById(id)) {
            repositoryUser.deleteById(id);
            return true;
        }

        return false;
    }

    @GetMapping
    public ResponseEntity<?> spinRoulete(String token) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = repositoryUser.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPoints() < 10) {
            return ResponseEntity.badRequest().build();
        }

        user.setPoints(user.getPoints() - 10);

        List<BigDecimal> rewards = List.of(
                BigDecimal.ZERO,
                new BigDecimal("10"),
                new BigDecimal("15"),
                new BigDecimal("20")
        );
        BigDecimal reward = rewards.get(ThreadLocalRandom.current().nextInt(rewards.size()));
        user.setMoney(user.getMoney().add(reward));

        repositoryUser.save(user);

        return ResponseEntity.ok().body(new RouleteResponseReward(reward));
    }

    public boolean deleteById(String id) {
        if (repositoryUser.existsById(id)) {
            repositoryUser.deleteById(id);
            return true;
        }

        return false;
    }
}
