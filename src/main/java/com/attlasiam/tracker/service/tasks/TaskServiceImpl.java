package com.attlasiam.tracker.service.tasks;

import com.attlasiam.tracker.repository.tasks.TaskEntity;
import com.attlasiam.tracker.repository.tasks.TaskRepository;
import com.attlasiam.tracker.repository.users.UserEntity;
import com.attlasiam.tracker.repository.users.UserRepository;
import com.attlasiam.tracker.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class TaskServiceImpl implements TaskService {

    private final TaskValidator validator;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public TaskDTO create(CreateTaskRequest request) {
        validator.validateNew(request);
        TaskEntity toCreate = request.toEntity();
        TaskEntity created = taskRepository.create(toCreate);
        log.debug("Task created {}", created);
        return toDto(created);
    }

    @Override
    public void update(TaskDTO dto) {
        validator.validateUpdate(dto);
        TaskEntity entity = taskRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Task not found by id " + dto));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        taskRepository.update(entity);
    }

    @Override
    public void assignTaskTo(long taskId, long userId) {
        validator.validateAssign(taskId, userId);
        TaskEntity entity = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found by id " + taskId));
        entity.setAssigneeId(userId);
        taskRepository.update(entity);
    }

    @Override
    public List<TaskDTO> getAll() {
        Map<Long, TaskUserDTO> taskUsers = userRepository.fetchAll().stream()
                .collect(Collectors.toMap(UserEntity::getId, TaskUserDTO::of));
        return taskRepository.fetchAll().stream()
                .map(entity -> TaskDTO.of(entity,
                        taskUsers.get(entity.getReporterId()),
                        taskUsers.get(entity.getAssigneeId())))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getOne(long id) {
        return taskRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Task not found by id " + id));
    }

    private TaskDTO toDto(TaskEntity entity) {
        TaskUserDTO assignee = Optional.ofNullable(entity.getAssigneeId())
                .flatMap(userRepository::findById)
                .map(TaskUserDTO::of)
                .orElse(null);
        TaskUserDTO reporter = Optional.ofNullable(entity.getReporterId())
                .flatMap(userRepository::findById)
                .map(TaskUserDTO::of)
                .orElse(null);
        return TaskDTO.of(entity, reporter, assignee);
    }
}
