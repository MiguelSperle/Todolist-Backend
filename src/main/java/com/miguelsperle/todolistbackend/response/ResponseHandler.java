package com.miguelsperle.todolistbackend.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status) {
        return new ResponseEntity<Object>(responseObj, status);
    }

    public static ResponseEntity<Object> generateResponse(List responseList, HttpStatus status) {
        return new ResponseEntity<Object>(responseList, status);
    }
}
