package com.attlasiam.tracker.service.tasks;

import com.attlasiam.tracker.repository.tasks.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Long reporterId;
    private Long assigneeId;

    public TaskEntity toEntity() {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setReporterId(reporterId);
        entity.setAssigneeId(assigneeId);
        return entity;
    }
}
