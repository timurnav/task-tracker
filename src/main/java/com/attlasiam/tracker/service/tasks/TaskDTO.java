package com.attlasiam.tracker.service.tasks;

import com.attlasiam.tracker.repository.tasks.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private TaskUserDTO reporter;
    private TaskUserDTO assignee;

    public static TaskDTO of(TaskEntity entity, TaskUserDTO reporter, TaskUserDTO assignee) {
        TaskDTO dto = new TaskDTO();
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.description = entity.getDescription();
        dto.reporter = reporter;
        dto.assignee = assignee;
        return dto;
    }
}
