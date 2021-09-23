package com.attlasiam.tracker.service.users;

import java.util.List;

public interface UserService {

    UserDTO create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(long id);

    List<UserDTO> getAll();

    UserDTO getOne(long id);
}
