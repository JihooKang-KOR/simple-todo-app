package com.simpletodoapp.todo.controller;

import com.simpletodoapp.todo.dto.TodoDto;
import com.simpletodoapp.todo.entity.Todo;
import com.simpletodoapp.todo.mapper.TodoMapper;
import com.simpletodoapp.todo.service.TodoService;
import com.simpletodoapp.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/todos")
@Validated
public class TodoController {
    private final static String TODO_DEFAULT_URL = "/v1/todos";
    private final TodoService todoService;
    private final TodoMapper mapper;

    public TodoController(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoDto.Post requestBody) {
        Todo createdTodo = todoService.createTodo(mapper.todoPostToTodo(requestBody));
        URI location = UriCreator.createUri(TODO_DEFAULT_URL, createdTodo.getId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchTodo(@Positive @PathVariable("id") long id,
                                    @Valid @RequestBody TodoDto.Patch requestBody) {
        requestBody.setId(id);
        Todo updatedTodo = todoService.updateTodo(mapper.todoPatchToTodo(requestBody));
        return new ResponseEntity<>(mapper.todoToTodoResponse(updatedTodo), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTodo(@Positive @PathVariable("id") long id) {
        Todo obtainedTodo = todoService.findTodo(id);
        return new ResponseEntity<>(mapper.todoToTodoResponse(obtainedTodo),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTodos() {
        List<Todo> allTodos = todoService.findTodos();
        return new ResponseEntity(mapper.todosToTodoResponses(allTodos),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@Positive @PathVariable("id") long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteTodos() {
        todoService.deleteTodos();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
