package com.attlasiam.tracker.service.tasks;

import com.attlasiam.tracker.repository.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUserDTO {

    private Long id;
    private String name;
    private boolean deleted;

    public static TaskUserDTO of(UserEntity entity) {
        TaskUserDTO dto = new TaskUserDTO();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.deleted = entity.isDeleted();
        return dto;
    }
}
