package com.simpletodoapp.generalhelper;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

public interface ControllerTestHelper<T> {
    default RequestBuilder postRequestBuilder(String url, String content) {
        return post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }

    default RequestBuilder patchRequestBuilder(String url, long id, String content) {
        return patch(url, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }

    default RequestBuilder getRequestBuilder(String url, long id) {
        return get(url, id)
                .accept(MediaType.APPLICATION_JSON);
    }

    default RequestBuilder getRequestBuilder(String url) {
        return get(url)
                .accept(MediaType.APPLICATION_JSON);
    }

    default RequestBuilder deleteRequestBuilder(String url, long id) {
        return delete(url, id)
                .accept(MediaType.APPLICATION_JSON);
    }

    default RequestBuilder deleteRequestBuilder(String url) {
        return delete(url)
                .accept(MediaType.APPLICATION_JSON);
    }

    default String getParentJsonPath(DataType dataType) {
        return dataType == DataType.ITEM ? "" : "[].";
    }

    enum DataType {
        ITEM, LIST
    }
}
