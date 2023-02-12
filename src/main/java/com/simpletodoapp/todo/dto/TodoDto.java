package com.simpletodoapp.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TodoDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String title;
        @Positive
        private int priority;
        @NotNull
        private boolean completed;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        @Setter
        @Positive
        private Long id;

        private String title;

        @Positive
        private Integer priority;

        private Boolean completed;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long id;
        private String title;
        private int priority;
        private boolean completed;
    }
}
