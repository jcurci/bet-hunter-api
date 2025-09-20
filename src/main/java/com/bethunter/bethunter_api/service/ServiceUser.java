package com.bethunter.bethunter_api.service;

import com.bethunter.bethunter_api.dto.user.RouleteResponseReward;
import com.bethunter.bethunter_api.dto.user.UserRequestUpdate;
import com.bethunter.bethunter_api.dto.user.UserResponse;
import com.bethunter.bethunter_api.exception.InsufficientPoints;
import com.bethunter.bethunter_api.exception.InvalidToken;
import com.bethunter.bethunter_api.exception.UserNotFound;
import com.bethunter.bethunter_api.infra.security.ServiceToken;
import com.bethunter.bethunter_api.mapper.UserMapper;
import com.bethunter.bethunter_api.model.User;
import com.bethunter.bethunter_api.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserMapper userMapper;

    public List<UserResponse> findAll() {
        return repositoryUser.findAll().stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    public Optional<UserResponse> findById(String id) {
        return repositoryUser.findById(id)
                .map(userMapper::toResponseDTO);
    }

    public Optional<UserResponse> update(String id, UserRequestUpdate dto) {
        return repositoryUser.findById(id)
                .map(user -> {
                    userMapper.updateEntity(user, dto);
                    User updated = repositoryUser.save(user);
                    return userMapper.toResponseDTO(updated);
                });
    }

    public boolean delete(String id) {
        if (repositoryUser.existsById(id)) {
            repositoryUser.deleteById(id);
            return true;
        }

        return false;
    }

    public RouleteResponseReward spinRoulete(String token) {
        var email = serviceToken.validateToken(token.replace("Bearer ", ""));
        if (email.isEmpty()) {
            throw new InvalidToken();
        }

        User user = repositoryUser.findByEmail(email).orElseThrow(() -> new UserNotFound());

        if (user.getPoints() < 10) {
            throw new InsufficientPoints();
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

        return new RouleteResponseReward(reward);
    }
}
