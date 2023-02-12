package com.simpletodoapp.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriCreator {
    public static URI createUri(String defaultUrl, long id) {
        URI uri = UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{id}")
                .buildAndExpand(id)
                .toUri();

        return uri;
    }
}
