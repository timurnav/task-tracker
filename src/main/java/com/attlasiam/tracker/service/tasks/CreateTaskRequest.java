package com.attlasiam.tracker.service.tasks;

import com.attlasiam.tracker.repository.tasks.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest {

    private String title;
    private String description;
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
