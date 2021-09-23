package com.attlasiam.tracker.service.tasks;

import com.attlasiam.tracker.repository.tasks.TaskRepository;
import com.attlasiam.tracker.repository.users.UserEntity;
import com.attlasiam.tracker.repository.users.UserRepository;
import com.attlasiam.tracker.service.BaseValidator;
import com.attlasiam.tracker.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TaskValidator extends BaseValidator {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public void validateNew(CreateTaskRequest request) {
        validate(!StringUtils.isEmpty(request.getReporterId()), "Reporter is mandatory");
        validate(!StringUtils.isEmpty(request.getDescription()), "Description is mandatory");
        validate(!StringUtils.isEmpty(request.getTitle()), "Title is mandatory");

        UserEntity reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new NotFoundException("Reporter not found " + request.getReporterId()));
        validate(!reporter.isDeleted(), "Reporter may not be deleted");

        if (request.getAssigneeId() != null) {
            UserEntity assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new NotFoundException("Assignee not found " + request));
            validate(!assignee.isDeleted(), "Assignee may not be deleted");
        }
    }

    public void validateUpdate(TaskDTO dto) {
        validate(!StringUtils.isEmpty(dto.getId()), "Id is mandatory");
        validate(!StringUtils.isEmpty(dto.getDescription()), "Description is mandatory");
        validate(!StringUtils.isEmpty(dto.getTitle()), "Title is mandatory");
        taskRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Task not found by id " + dto.getId()));

    }

    public void validateAssign(long taskId, long userId) {
        UserEntity assignee = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Assignee not found " + userId));
        validate(!assignee.isDeleted(), "Assignee may not be deleted");
        taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found by id " + taskId));
    }
}
