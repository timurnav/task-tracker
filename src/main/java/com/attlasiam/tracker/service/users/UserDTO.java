package com.attlasiam.tracker.service.users;

import com.attlasiam.tracker.repository.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
