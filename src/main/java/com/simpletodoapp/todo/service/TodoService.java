package com.simpletodoapp.todo.service;

import com.simpletodoapp.todo.entity.Todo;
import com.simpletodoapp.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        verifyIfTodoExists(todo.getTitle());
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo) {
        Todo obtainedTodo = findVerifiedTodo(todo.getId());

        Optional.ofNullable(todo.getTitle())
                .ifPresent(title -> obtainedTodo.setTitle(title));
        Optional.ofNullable(todo.getPriority())
                .ifPresent(order -> obtainedTodo.setPriority(order));
        Optional.ofNullable(todo.getCompleted())
                .ifPresent(completed -> obtainedTodo.setCompleted(completed));

        return todoRepository.save(obtainedTodo);
    }

    public Todo findTodo(long id) {
        return findVerifiedTodo(id);
    }

    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }

    public void deleteTodo(long id) {
        Todo obtainedTodo = findVerifiedTodo(id);
        todoRepository.delete(obtainedTodo);
    }

    public void deleteTodos() {
        todoRepository.deleteAll();
    }

    private Todo findVerifiedTodo(long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        Todo obtainedTodo = optionalTodo.orElseThrow(() ->
                new RuntimeException());
        return obtainedTodo;
    }

    private void verifyIfTodoExists(String title) {
        Optional<Todo> todo = todoRepository.findByTitle(title);
        if (todo.isPresent())
            throw new RuntimeException();
    }
}
