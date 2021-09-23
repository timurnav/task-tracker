package com.attlasiam.tracker.repository.tasks;

import com.attlasiam.tracker.utils.Resetable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class TaskRepositoryImpl implements TaskRepository, Resetable {

    private final Map<Long, TaskEntity> byId = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public List<TaskEntity> fetchAll() {
        return new ArrayList<>(byId.values());
    }

    @Override
    public Optional<TaskEntity> findById(long id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public TaskEntity create(TaskEntity entity) {
        entity.setId(counter.incrementAndGet());
        update(entity);
        return entity;
    }

    @Override
    public void update(TaskEntity entity) {
        byId.put(entity.getId(), entity);
    }

    @Override
    public void reset() {
        counter.set(0);
        byId.clear();
    }
}
