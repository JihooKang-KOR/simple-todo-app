package com.simpletodoapp.todo.mapper;

import com.simpletodoapp.todo.dto.TodoDto;
import com.simpletodoapp.todo.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo todoPostToTodo(TodoDto.Post requestBody);

    Todo todoPatchToTodo(TodoDto.Patch requestBody);

    TodoDto.Response todoToTodoResponse(Todo todo);

    List<TodoDto.Response> todosToTodoResponses(List<Todo> todos);
}
