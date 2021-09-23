package com.attlasiam.tracker.repository.users;

import com.attlasiam.tracker.utils.Resetable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class UserRepositoryImpl implements UserRepository, Resetable {

    private final Map<String, UserEntity> byEmail = new HashMap<>();
    private final Map<Long, UserEntity> byId = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public List<UserEntity> fetchAll() {
        return new ArrayList<>(byId.values());
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.ofNullable(byEmail.get(email));
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public UserEntity create(UserEntity entity) {
        entity.setId(counter.incrementAndGet());
        update(entity);
        return entity;
    }

    @Override
    public void update(UserEntity entity) {
        byEmail.put(entity.getEmail(), entity);
        byId.put(entity.getId(), entity);
    }

    @Override
    public void reset() {
        counter.set(0);
        byEmail.clear();
        byId.clear();
    }
}
