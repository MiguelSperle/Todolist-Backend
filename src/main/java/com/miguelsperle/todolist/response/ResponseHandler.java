package com.miguelsperle.todolist.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(List responseList, HttpStatus status) {
        return new ResponseEntity<>(responseList, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", responseObj);
        map.put("status", status.value());

        return new ResponseEntity<Object>(map,status);
    }
}