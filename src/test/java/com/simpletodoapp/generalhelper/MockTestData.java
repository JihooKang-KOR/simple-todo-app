package com.simpletodoapp.generalhelper;

import com.simpletodoapp.todo.dto.TodoDto;
import com.simpletodoapp.todo.entity.Todo;

import java.util.List;

public class MockTestData {
    public static class MockTodo {
        public static TodoDto.Post getPostTodoDto() {
            return new TodoDto.Post("Coding", 1, false);
        }

        public static TodoDto.Patch getPatchTodoDto() {
            return new TodoDto.Patch(1L, "Coding", 1, false);
        }

        public static TodoDto.Response getResponseTodoDto() {
            return new TodoDto.Response(1L, "Coding", 1, false);
        }

        public static List<TodoDto.Response> getMultiResponseTodoDtos() {
            TodoDto.Response todoResponse1 = new TodoDto.Response(1L, "Coding", 1, false);
            TodoDto.Response todoResponse2 = new TodoDto.Response(2L, "Exercise", 2, false);
            return List.of(todoResponse1, todoResponse2);
        }

        public static Todo getTodo() {
            Todo todo = new Todo();
            todo.setId(1L);
            todo.setTitle("Coding");
            todo.setPriority(1);
            todo.setCompleted(false);
            return todo;
        }

        public static List<Todo> getMultiTodos() {
            Todo todo1 = new Todo();
            todo1.setId(1L);
            todo1.setTitle("Coding");
            todo1.setPriority(1);
            todo1.setCompleted(false);

            Todo todo2 = new Todo();
            todo2.setId(2L);
            todo2.setTitle("Exercise");
            todo2.setPriority(2);
            todo2.setCompleted(false);

            return List.of(todo1, todo2);
        }
    }
}
