package com.simpletodoapp.todo.service;

import com.simpletodoapp.generalhelper.MockTestData;
import com.simpletodoapp.todo.entity.Todo;
import com.simpletodoapp.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @Spy
    @InjectMocks
    private TodoService todoService;

    @Test
    public void createTodoTest() {
        // Given
        Todo todo = MockTestData.MockTodo.getTodo();

        ReflectionTestUtils.invokeMethod(todoService, "verifyIfTodoExists", todo.getTitle());
        given(todoRepository.save(Mockito.any(Todo.class)))
                .willReturn(todo);

        // When
        Executable executable = () -> todoService.createTodo(todo);

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    public void updateTodoTest() {
        // Given
        Todo todo = MockTestData.MockTodo.getTodo();

        given(todoRepository.findById(Mockito.anyLong()))
                .willReturn(Optional.of(todo));
        given(todoRepository.save(Mockito.any(Todo.class)))
                .willReturn(todo);

        // When
        Executable executable = () -> todoService.updateTodo(todo);

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    public void findTodoTest() {
        // Given
        Todo todo = MockTestData.MockTodo.getTodo();

        given(todoRepository.findById(Mockito.anyLong()))
                .willReturn(Optional.of(todo));

        // When
        Executable executable = () -> todoService.findTodo(todo.getId());

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    public void findTodosTest() {
        // Given
        List<Todo> todos = MockTestData.MockTodo.getMultiTodos();

        given(todoRepository.findAll())
                .willReturn(todos);

        // When
        Executable executable = () -> todoService.findTodos();

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    public void deleteTodoTest() {
        // Given
        Todo todo = MockTestData.MockTodo.getTodo();

        given(todoRepository.findById(Mockito.anyLong()))
                .willReturn(Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);

        // When
        Executable executable = () -> todoService.deleteTodo(todo.getId());

        // Then
        assertDoesNotThrow(executable);
    }

    @Test
    public void deleteTodosTest() {
        // Given
        List<Todo> todos = MockTestData.MockTodo.getMultiTodos();

        doNothing().when(todoRepository).deleteAll();

        // When
        Executable executable = () -> todoService.deleteTodos();

        // Then
        assertDoesNotThrow(executable);
    }
}
