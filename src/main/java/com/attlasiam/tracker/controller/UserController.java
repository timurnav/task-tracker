package com.attlasiam.tracker.controller;

import com.attlasiam.tracker.service.users.UserDTO;
import com.attlasiam.tracker.service.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PutMapping
    public UserDTO save(@Valid @RequestBody UserDTO userDTO) {
        return service.create(userDTO);
    }

    @PatchMapping
    public void update(@Valid @RequestBody UserDTO userDTO) {
        service.update(userDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public UserDTO getOne(@PathVariable("id") long id) {
        return service.getOne(id);
    }
}
