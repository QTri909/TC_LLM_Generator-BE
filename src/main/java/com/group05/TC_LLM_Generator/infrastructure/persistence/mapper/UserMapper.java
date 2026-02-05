package com.group05.TC_LLM_Generator.infrastructure.persistence.mapper;

import com.group05.TC_LLM_Generator.domain.model.entity.User;
import com.group05.TC_LLM_Generator.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null)
            return null;
        return User.builder()
                .id(entity.getUserId())
                .email(entity.getEmail())
                .name(entity.getFullName())
                .password(entity.getPasswordHash())
                .provider(entity.getAuthProvider())
                .status(entity.getStatus())
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null)
            return null;
        return UserEntity.builder()
                .userId(domain.getId())
                .email(domain.getEmail())
                .fullName(domain.getName())
                .passwordHash(domain.getPassword())
                .authProvider(domain.getProvider())
                .status(domain.getStatus())
                .build();
    }
}
