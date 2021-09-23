package com.attlasiam.tracker.service.tasks;

import com.attlasiam.tracker.repository.tasks.TaskEntity;
import com.attlasiam.tracker.repository.tasks.TaskRepository;
import com.attlasiam.tracker.repository.users.UserEntity;
import com.attlasiam.tracker.repository.users.UserRepository;
import com.attlasiam.tracker.service.exceptions.NotFoundException;
import com.attlasiam.tracker.service.exceptions.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private final TaskRepository taskRepository = mock(TaskRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final TaskServiceImpl impl;

    {
        TaskValidator validator = new TaskValidator(userRepository, taskRepository);
        impl = new TaskServiceImpl(validator, taskRepository, userRepository);
    }

    @After
    public void tearDown() {
        Mockito.reset(taskRepository, userRepository);
    }

    @Test
    public void createTask() {
        when(taskRepository.create(any(TaskEntity.class)))
                .thenReturn(new TaskEntity(123L, "Title", "Description", 1L, 2L));

        when(userRepository.findById(1))
                .thenReturn(Optional.of(new UserEntity(1L, "Name1", null, false)));
        when(userRepository.findById(2))
                .thenReturn(Optional.of(new UserEntity(2L, "Name2", null, false)));

        CreateTaskRequest request = new CreateTaskRequest("Title", "Description", 1L, 2L);
        TaskDTO result = impl.create(request);

        Assertions.assertThat(result).isEqualTo(new TaskDTO(123L, "Title", "Description",
                new TaskUserDTO(1L, "Name1", false), new TaskUserDTO(2L, "Name2", false)));

        ArgumentCaptor<TaskEntity> taskCaptor = ArgumentCaptor.forClass(TaskEntity.class);
        verify(taskRepository).create(taskCaptor.capture());

        Assertions.assertThat(taskCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new TaskEntity(null, "Title", "Description", 1L, 2L));
    }

    @Test
    public void failedTaskCreateMissingReporter() {
        CreateTaskRequest request = new CreateTaskRequest("Title", "Descr", null, null);
        ValidationException thrown = assertThrows(ValidationException.class, () -> impl.create(request));
        Assertions.assertThat(thrown)
                .hasMessage("Reporter is mandatory");
        verify(taskRepository, never()).create(any());
    }

    @Test
    public void failedTaskCreateMissingDescription() {
        CreateTaskRequest request = new CreateTaskRequest("Title", null, 1L, null);
        ValidationException thrown = assertThrows(ValidationException.class, () -> impl.create(request));
        Assertions.assertThat(thrown)
                .hasMessage("Description is mandatory");
        verify(taskRepository, never()).create(any());
    }

    @Test
    public void failedTaskCreateMissingTitle() {
        CreateTaskRequest request = new CreateTaskRequest(null, "Descr", 1L, null);
        ValidationException thrown = assertThrows(ValidationException.class, () -> impl.create(request));
        Assertions.assertThat(thrown)
                .hasMessage("Title is mandatory");
        verify(taskRepository, never()).create(any());
    }

    @Test
    public void failedTaskCreateReporterNotFound() {
        CreateTaskRequest request = new CreateTaskRequest("Title", "Descr", 1L, null);
        when(userRepository.findById(1))
                .thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> impl.create(request));
        Assertions.assertThat(thrown)
                .hasMessage("Reporter not found 1");
        verify(taskRepository, never()).create(any());
    }

    @Test
    public void failedTaskCreateReporterDeleted() {
        CreateTaskRequest request = new CreateTaskRequest("Title", "Descr", 1L, null);
        when(userRepository.findById(1))
                .thenReturn(Optional.of(new UserEntity(1L, "Name1", null, true)));
        ValidationException thrown = assertThrows(ValidationException.class, () -> impl.create(request));
        Assertions.assertThat(thrown)
                .hasMessage("Reporter may not be deleted");
        verify(taskRepository, never()).create(any());
    }
}
