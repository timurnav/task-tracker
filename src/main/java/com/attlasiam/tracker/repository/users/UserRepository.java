package com.attlasiam.tracker.repository.users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<UserEntity> fetchAll();

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(long id);

    UserEntity create(UserEntity entity);

    void update(UserEntity entity);
}
