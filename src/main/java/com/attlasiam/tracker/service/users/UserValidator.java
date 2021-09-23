package com.attlasiam.tracker.service.users;

import com.attlasiam.tracker.repository.users.UserEntity;
import com.attlasiam.tracker.repository.users.UserRepository;
import com.attlasiam.tracker.service.BaseValidator;
import com.attlasiam.tracker.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidator extends BaseValidator {

    private final UserRepository userRepository;

    public void validateNew(UserDTO dto) {
        validate(dto.getId() == null, "User id must be null");
        String email = dto.getEmail();
        validate(!StringUtils.isEmpty(email), "User email is mandatory");
        validate(!StringUtils.isEmpty(dto.getName()), "User name is mandatory");

        checkEmailIsUnique(email);
    }

    public void validateExisting(UserDTO dto) {
        validate(dto.getId() != null, "User id must be set");
        validate(!StringUtils.isEmpty(dto.getEmail()), "User email is mandatory");
        validate(!StringUtils.isEmpty(dto.getName()), "User name is mandatory");

        UserEntity existing = userRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("User not found by id " + dto.getId()));

        if (!Objects.equals(dto.getEmail(), existing.getEmail())) {
            checkEmailIsUnique(dto.getEmail());
        }
    }

    public void checkEmailIsUnique(String email) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        validate(userOpt.isEmpty(), "User email must be unique");
    }
}
