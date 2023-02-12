package com.simpletodoapp.todo.controller;

import com.google.gson.Gson;
import com.simpletodoapp.generalhelper.MockTestData;
import com.simpletodoapp.todo.dto.TodoDto;
import com.simpletodoapp.todo.entity.Todo;
import com.simpletodoapp.todo.helper.TodoControllerTestHelper;
import com.simpletodoapp.todo.mapper.TodoMapper;
import com.simpletodoapp.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TodoController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class TodoControllerTest implements TodoControllerTestHelper {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private TodoService todoService;

    @MockBean
    private TodoMapper mapper;

    @Test
    public void postTodoTest() throws Exception {
        // Given
        TodoDto.Post postDto = MockTestData.MockTodo.getPostTodoDto();
        String postContent = gson.toJson(postDto);

        given(mapper.todoPostToTodo(Mockito.any(TodoDto.Post.class)))
                .willReturn(new Todo());
        given(todoService.createTodo(Mockito.any(Todo.class)))
                .willReturn(MockTestData.MockTodo.getTodo());

        // When
        ResultActions actions =
                mockMvc.perform(
                        postRequestBuilder("/v1/todos", postContent)
                );

        // Then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/v1/todos/"))))
                .andDo(document("post-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                getFieldDescriptorsForTodoPostRequest()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION)
                                        .description("Header for Location")
                        )
                ));
    }

    @Test
    public void patchTodoTest() throws Exception {
        // Given
        TodoDto.Patch patchDto = MockTestData.MockTodo.getPatchTodoDto();
        String patchContent = gson.toJson(patchDto);
        Todo obtainedTodo = MockTestData.MockTodo.getTodo();

        given(mapper.todoPatchToTodo(Mockito.any(TodoDto.Patch.class)))
                .willReturn(new Todo());
        given(todoService.updateTodo(Mockito.any(Todo.class)))
                .willReturn(obtainedTodo);
        given(mapper.todoToTodoResponse(Mockito.any(Todo.class)))
                .willReturn(MockTestData.MockTodo.getResponseTodoDto());

        // When
        ResultActions actions =
                mockMvc.perform(
                        patchRequestBuilder("/v1/todos/{id}", obtainedTodo.getId(), patchContent)
                );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.title").value(patchDto.getTitle()))
                .andExpect(jsonPath("$.priority").value(patchDto.getPriority()))
                .andExpect(jsonPath("$.completed").value(patchDto.getCompleted()))
                .andDo(document("patch-todo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                getParameterDescriptorForPathVariable()
                        ),
                        requestFields(
                                getFieldDescriptorsForTodoPatchRequest()
                        ),
                        responseFields(
                                getFieldDescriptorsForTodoResponse(DataType.ITEM)
                        )
                ));
    }

    @Test
    public void getTodoTest() throws Exception {
        // Given
        Long id = 1L;
        TodoDto.Response responseDto = MockTestData.MockTodo.getResponseTodoDto();

        given(todoService.findTodo(Mockito.anyLong()))
                .willReturn(new Todo());
        given(mapper.todoToTodoResponse(Mockito.any(Todo.class)))
                .willReturn(responseDto);

        // When
        ResultActions actions =
                mockMvc.perform(
                        getRequestBuilder("/v1/todos/{id}", id)
                );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(id))
                .andDo(document("get-todo",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                getParameterDescriptorForPathVariable()
                        ),
                        responseFields(
                                getFieldDescriptorsForTodoResponse(DataType.ITEM)
                        )
                ));
    }

    @Test
    public void getQuestionsTest() throws Exception {
        // Given
        List<TodoDto.Response> responses = MockTestData.MockTodo.getMultiResponseTodoDtos();

        given(todoService.findTodos())
                .willReturn(MockTestData.MockTodo.getMultiTodos());
        given(mapper.todosToTodoResponses(Mockito.anyList()))
                .willReturn(responses);

        // When
        ResultActions actions =
                mockMvc.perform(
                        getRequestBuilder("/v1/todos")
                );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").exists())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andDo(document("get-todos",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                getFieldDescriptorsForTodoResponse(DataType.LIST)
                        )
                ));
    }

    @Test
    public void deleteTodoTest() throws Exception {
        // Given
        Long id = 1L;

        doNothing().when(todoService).deleteTodo(id);

        // When
        ResultActions actions =
                mockMvc.perform(
                        deleteRequestBuilder("/v1/todos/{id}", id)
                );

        // Then
        actions
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("delete-todo",
                        pathParameters(
                                getParameterDescriptorForPathVariable()
                        )
                ));
    }

    @Test
    public void deleteTodosTest() throws Exception {
        // Given
        doNothing().when(todoService).deleteTodos();

        // When
        ResultActions actions =
                mockMvc.perform(
                        deleteRequestBuilder("/v1/todos")
                );

        // Then
        actions
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(document("delete-todos"));
    }
}
