package com.attlasiam.tracker.service.users;

import com.attlasiam.tracker.repository.users.UserEntity;
import com.attlasiam.tracker.repository.users.UserRepository;
import com.attlasiam.tracker.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService {

    private final UserValidator validator;
    private final UserRepository userRepository;

    @Override
    public UserDTO create(UserDTO userDTO) {
        validator.validateNew(userDTO);
        UserEntity toCreate = userDTO.toEntity();
        UserEntity created = userRepository.create(toCreate);
        log.debug("User created {}", created);
        return UserDTO.of(created);
    }

    @Override
    public void update(UserDTO userDTO) {
        validator.validateExisting(userDTO);
        UserEntity entity = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new NotFoundException("User not found by id " + userDTO.getId()));
        entity.setEmail(userDTO.getEmail());
        entity.setName(userDTO.getName());
        userRepository.update(entity);
        log.debug("User updated {}", entity);
    }

    @Override
    public void delete(long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found by id " + id));
        entity.setDeleted(true);
        userRepository.update(entity);
        log.debug("User deleted {}", entity);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.fetchAll().stream()
                .filter(u -> !u.isDeleted())
                .map(UserDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getOne(long id) {
        return userRepository.findById(id)
                .map(UserDTO::of)
                .orElseThrow(() -> new NotFoundException("User not found by id" + id));
    }
}
