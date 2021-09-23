package com.attlasiam.tracker.repository.tasks;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {
    TaskEntity create(TaskEntity entity);

    void update(TaskEntity entity);

    Collection<TaskEntity> fetchAll();

    Optional<TaskEntity> findById(long id);
}
