package com.attlasiam.tracker.controller;

import com.attlasiam.tracker.service.tasks.CreateTaskRequest;
import com.attlasiam.tracker.service.tasks.TaskDTO;
import com.attlasiam.tracker.service.tasks.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PutMapping
    public TaskDTO save(@RequestBody CreateTaskRequest request) {
        return service.create(request);
    }

    @PatchMapping
    public void update(@RequestBody TaskDTO dto) {
        service.update(dto);
    }

    @PatchMapping("{taskId}/user/{userId}")
    public void assign(@PathVariable("taskId") long taskId,
                       @PathVariable("userId") long userId) {
        service.assignTaskTo(taskId, userId);
    }

    @GetMapping
    public List<TaskDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public TaskDTO getOne(@PathVariable("id") long id) {
        return service.getOne(id);
    }
}
