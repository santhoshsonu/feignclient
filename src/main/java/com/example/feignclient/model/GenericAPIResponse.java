package com.example.feignclient.model;

import java.util.Map;

public record GenericAPIResponse(
    Integer status, String message, Object data, Map<String, String> errors) {}
