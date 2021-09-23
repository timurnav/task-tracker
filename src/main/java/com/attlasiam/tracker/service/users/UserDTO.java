package com.attlasiam.tracker.service.users;

import com.attlasiam.tracker.repository.users.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private boolean deleted;

    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setEmail(email);
        entity.setDeleted(deleted);
        return entity;
    }

    public static UserDTO of(UserEntity created) {
        UserDTO dto = new UserDTO();
        dto.id = created.getId();
        dto.name = created.getName();
        dto.email = created.getEmail();
        dto.deleted = created.isDeleted();
        return dto;
    }
}
