package com.bethunter.bethunter_api.mapper;

import com.bethunter.bethunter_api.dto.user.RouleteResponseReward;
import com.bethunter.bethunter_api.dto.user.UserRequestUpdate;
import com.bethunter.bethunter_api.dto.user.UserResponse;
import com.bethunter.bethunter_api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponseDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCellphone(),
                user.getMoney(),
                user.getPoints()
        );
    }

    public void updateEntity(User user, UserRequestUpdate dto) {
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setCellphone(dto.cellphone());
        user.setMoney(dto.money());
        user.setPoints(dto.points());
    }

    public RouleteResponseReward toRouleteResponseReward(User user) {
        return new RouleteResponseReward(user.getMoney());
    }
}
