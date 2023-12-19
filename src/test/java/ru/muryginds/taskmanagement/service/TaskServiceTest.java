package ru.muryginds.taskmanagement.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.muryginds.taskmanagement.dto.request.TaskRequestDTO;
import ru.muryginds.taskmanagement.dto.response.TaskDTO;
import ru.muryginds.taskmanagement.dto.response.UserDTO;
import ru.muryginds.taskmanagement.entity.Task;
import ru.muryginds.taskmanagement.entity.User;
import ru.muryginds.taskmanagement.enumerated.TaskPriority;
import ru.muryginds.taskmanagement.enumerated.TaskStatus;
import ru.muryginds.taskmanagement.exception.TaskModificationException;
import ru.muryginds.taskmanagement.exception.TaskNotFoundException;
import ru.muryginds.taskmanagement.exception.UserNotFoundException;
import ru.muryginds.taskmanagement.mapstruct.TaskMapper;
import ru.muryginds.taskmanagement.repository.TaskRepository;
import ru.muryginds.taskmanagement.repository.UserRepository;
import ru.muryginds.taskmanagement.util.CurrentUserUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private final MockedStatic<CurrentUserUtils> utilsMockedStatic = Mockito.mockStatic(CurrentUserUtils.class);

    private User currentTestUser;

    @BeforeEach
    void setUp() {
        currentTestUser = User.builder().id(1L).email("test@test.ru").build();
    }

    @AfterEach
    void tearDown() {
        utilsMockedStatic.close();
    }

    @Test
    void testAddTask_whenValidRequest_thenReturnTaskDTO() {
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);

        TaskRequestDTO request = TaskRequestDTO.builder()
                .description("Test description")
                .title("Title")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.LOW)
                .build();

        UserDTO currentUserDTO = UserDTO.builder()
                .name(currentTestUser.getName())
                .email(currentTestUser.getEmail())
                .build();
        TaskDTO expectedTaskDTO = TaskDTO.builder()
                .description(request.getDescription())
                .title(request.getTitle())
                .status(request.getStatus())
                .priority(request.getPriority())
                .author(currentUserDTO)
                .build();
        when(taskMapper.taskToTaskDTO(any(Task.class))).thenReturn(expectedTaskDTO);

        TaskDTO resultTaskDTO = taskService.addTask(request);

        assertNotNull(resultTaskDTO);
        assertEquals(expectedTaskDTO.getTitle(), resultTaskDTO.getTitle());
        assertEquals(expectedTaskDTO.getDescription(), resultTaskDTO.getDescription());
        assertEquals(expectedTaskDTO.getStatus(), resultTaskDTO.getStatus());
        assertEquals(expectedTaskDTO.getPriority(), resultTaskDTO.getPriority());
        assertEquals(expectedTaskDTO.getAuthor().getEmail(), resultTaskDTO.getAuthor().getEmail());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testAddTask_whenWrongExecutorId_thenReturnUserNotFoundException() {
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);
        long wrongUserId = 999L;
        TaskRequestDTO request = TaskRequestDTO.builder()
                .description("Test description")
                .title("Title")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.LOW)
                .executorId(wrongUserId)
                .build();

        when(userRepository.findById(wrongUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> taskService.addTask(request));
    }

    @Test
    void testGetTask_whenTaskExists_thenReturnTaskDTO() {
        long taskId = 1L;
        Task task = Task.builder()
                .id(taskId)
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskDTO expectedTaskDTO = TaskDTO.builder()
                .id(taskId)
                .build();
        when(taskMapper.taskToTaskDTO(task)).thenReturn(expectedTaskDTO);

        TaskDTO resultTaskDTO = taskService.getTask(taskId);

        assertNotNull(resultTaskDTO);
        assertEquals(expectedTaskDTO.getId(), resultTaskDTO.getId());
    }

    @Test
    void testGetTask_whenTaskNotExist_thenThrowTaskNotFoundException() {
        long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(taskId));
    }

    @Test
    void testEditTask_whenValidRequest_thenReturnTaskDTO() {
        long taskId = 10L;
        Task task = Task.builder()
                .author(currentTestUser)
                .status(TaskStatus.PENDING)
                .build();
        TaskRequestDTO request = TaskRequestDTO.builder()
                .status(TaskStatus.IN_PROGRESS)
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);

        TaskDTO expectedTaskDTO = TaskDTO.builder()
                .status(TaskStatus.IN_PROGRESS).build();
        when(taskMapper.taskToTaskDTO(any(Task.class))).thenReturn(expectedTaskDTO);

        TaskDTO resultTaskDTO = taskService.editTask(taskId, request);

        assertNotNull(resultTaskDTO);
        assertEquals(expectedTaskDTO.getStatus(), resultTaskDTO.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testEditTask_whenNotAuthorModifying_thenReturnTaskModificationException() {
        long taskId = 10L;
        Task task = Task.builder()
                .author(new User())
                .build();
        TaskRequestDTO request = TaskRequestDTO.builder()
                .status(TaskStatus.IN_PROGRESS)
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);

        assertThrows(TaskModificationException.class, () -> taskService.editTask(taskId, request));
    }

    @Test
    void testDeleteTask_whenValidRequest_thenReturnTaskDTO() {
        long taskId = 1L;
        Task task = Task.builder()
                .author(currentTestUser)
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);

        TaskDTO expectedTaskDTO = TaskDTO.builder()
                .isDeleted(true)
                .build();
        when(taskMapper.taskToTaskDTO(any(Task.class))).thenReturn(expectedTaskDTO);

        TaskDTO resultTaskDTO = taskService.deleteTask(taskId);

        assertNotNull(resultTaskDTO);
        assertTrue(resultTaskDTO.isDeleted());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testDeleteTask_whenNotAuthor_thenThrowTaskModificationException() {
        long taskId = 1L;
        Task task = Task.builder()
                .author(new User())
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);

        assertThrows(TaskModificationException.class, () -> taskService.deleteTask(taskId));
    }

    @Test
    void testRecoverTask_whenValidRequest_thenReturnTaskDTO() {
        long taskId = 1L;
        Task task = Task.builder()
                .author(currentTestUser)
                .isDeleted(true)
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);

        TaskDTO expectedTaskDTO = TaskDTO.builder()
                .isDeleted(false)
                .build();
        when(taskMapper.taskToTaskDTO(any(Task.class))).thenReturn(expectedTaskDTO);

        TaskDTO resultTaskDTO = taskService.deleteTask(taskId);

        assertNotNull(resultTaskDTO);
        assertFalse(resultTaskDTO.isDeleted());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testRecoverTask_whenNotAuthor_thenThrowTaskModificationException() {
        long taskId = 1L;
        Task task = Task.builder()
                .author(new User())
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        utilsMockedStatic.when(CurrentUserUtils::getCurrentUser).thenReturn(currentTestUser);

        assertThrows(TaskModificationException.class, () -> taskService.recoverTask(taskId));
    }

    @Test
    void testGetTasks_whenValidRequestWithAuthorId_thenReturnTaskDTOList() {
        long authorId = 1L;
        int pagesOffset = 0;
        int itemPerPage = 10;
        boolean showDeleted = false;

        PageRequest pageable = PageRequest.of(pagesOffset, itemPerPage);

        when(taskRepository.findByAuthorIdAndIsDeleted(authorId, showDeleted, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        TaskDTO expectedTaskDTO = new TaskDTO();
        when(taskMapper.tasksToTaskDTOList(anyList())).thenReturn(Collections.singletonList(expectedTaskDTO));

        List<TaskDTO> resultTaskDTOList = taskService.getTasks(authorId, null, pagesOffset, itemPerPage, showDeleted);

        assertNotNull(resultTaskDTOList);
        assertEquals(1, resultTaskDTOList.size());
        assertEquals(expectedTaskDTO, resultTaskDTOList.get(0));
    }

    @Test
    void testGetTasks_whenValidRequestWithExecutorId_thenReturnTaskDTOList() {
        long executorId = 2L;
        int pagesOffset = 0;
        int itemPerPage = 10;
        boolean showDeleted = false;

        PageRequest pageable = PageRequest.of(pagesOffset, itemPerPage);

        when(taskRepository.findByExecutorIdAndIsDeleted(executorId, showDeleted, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        TaskDTO expectedTaskDTO = new TaskDTO();
        when(taskMapper.tasksToTaskDTOList(anyList())).thenReturn(Collections.singletonList(expectedTaskDTO));

        List<TaskDTO> resultTaskDTOList = taskService.getTasks(null, executorId, pagesOffset, itemPerPage, showDeleted);

        assertNotNull(resultTaskDTOList);
        assertEquals(1, resultTaskDTOList.size());
        assertEquals(expectedTaskDTO, resultTaskDTOList.get(0));
    }

    @Test
    void testGetTasks_whenValidRequest_thenReturnTaskDTOList() {
        int pagesOffset = 0;
        int itemPerPage = 10;
        boolean showDeleted = false;

        PageRequest pageable = PageRequest.of(pagesOffset, itemPerPage);

        when(taskRepository.findByIsDeleted(showDeleted, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        TaskDTO expectedTaskDTO = new TaskDTO();
        when(taskMapper.tasksToTaskDTOList(anyList())).thenReturn(Collections.singletonList(expectedTaskDTO));

        List<TaskDTO> resultTaskDTOList = taskService.getTasks(null, null, pagesOffset, itemPerPage, showDeleted);

        assertNotNull(resultTaskDTOList);
        assertEquals(1, resultTaskDTOList.size());
        assertEquals(expectedTaskDTO, resultTaskDTOList.get(0));
    }
}
