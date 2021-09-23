package com.attlasiam.tracker.service.users;

import com.attlasiam.tracker.repository.users.UserEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    private Long id;
    @NotBlank
    private String name;
    @Email
    @NotNull
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
