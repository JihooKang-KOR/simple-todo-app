package com.simpletodoapp.todo.helper;

import com.simpletodoapp.generalhelper.ControllerTestHelper;
import com.simpletodoapp.todo.dto.TodoDto;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.snippet.Attributes.key;

public interface TodoControllerTestHelper extends ControllerTestHelper {
    String TODO_URL = "/v1/todos";
    String ID_URI = "/{id}";

    default List<ParameterDescriptor> getParameterDescriptorForPathVariable() {
        return List.of(parameterWithName("id").description("Id for To-do Item."));
    }

    default List<FieldDescriptor> getFieldDescriptorsForTodoPostRequest() {
        ConstraintDescriptions postTodoConstraints =
                new ConstraintDescriptions(TodoDto.Post.class);
        List<String> titleDescription = postTodoConstraints.descriptionsForProperty("title");
        List<String> priorityDescription = postTodoConstraints.descriptionsForProperty("priority");
        List<String> completedDescription = postTodoConstraints.descriptionsForProperty("completed");

        return List.of(
                fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("To-do Item")
                        .attributes(key("constraints").value(titleDescription)),
                fieldWithPath("priority").type(JsonFieldType.NUMBER)
                        .description("Priority of To-do Item")
                        .attributes(key("constraints").value(priorityDescription)),
                fieldWithPath("completed").type(JsonFieldType.BOOLEAN)
                        .description("Check Completion of To-do Item")
                        .attributes(key("constraints").value(completedDescription))
        );
    }

    default List<FieldDescriptor> getFieldDescriptorsForTodoPatchRequest() {
        ConstraintDescriptions patchTodoConstraints =
                new ConstraintDescriptions(TodoDto.Patch.class);
        List<String> titleDescription = patchTodoConstraints.descriptionsForProperty("title");
        List<String> priorityDescription = patchTodoConstraints.descriptionsForProperty("priority");
        List<String> completedDescription = patchTodoConstraints.descriptionsForProperty("completed");

        return List.of(
                fieldWithPath("id").type(JsonFieldType.NUMBER)
                        .description("Id of To-do Item").ignored(),
                fieldWithPath("title").type(JsonFieldType.STRING)
                        .description("To-do Item")
                        .attributes(key("constraints").value(titleDescription))
                        .optional(),
                fieldWithPath("priority").type(JsonFieldType.NUMBER)
                        .description("Priority of To-do Item")
                        .attributes(key("constraints").value(priorityDescription))
                        .optional(),
                fieldWithPath("completed").type(JsonFieldType.BOOLEAN)
                        .description("Check Completion of To-do Item")
                        .attributes(key("constraints").value(completedDescription))
                        .optional()
        );
    }

    default List<FieldDescriptor> getFieldDescriptorsForTodoResponse(DataType dataType) {
        String jsonPath = getParentJsonPath(dataType);

        return List.of(
                fieldWithPath(jsonPath.concat("id")).type(JsonFieldType.NUMBER)
                        .description("Id of To-do Item"),
                fieldWithPath(jsonPath.concat("title")).type(JsonFieldType.STRING)
                        .description("To-do Item"),
                fieldWithPath(jsonPath.concat("priority")).type(JsonFieldType.NUMBER)
                        .description("Priority of To-do Item"),
                fieldWithPath(jsonPath.concat("completed")).type(JsonFieldType.BOOLEAN)
                        .description("Check Completion of To-do Item")
        );
    }
}
