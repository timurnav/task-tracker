package com.attlasiam.tracker.service.tasks;

import java.util.List;

public interface TaskService {

    TaskDTO create(CreateTaskRequest request);

    void update(TaskDTO dto);

    void assignTaskTo(long taskId, long userId);

    List<TaskDTO> getAll();

    TaskDTO getOne(long id);
}
