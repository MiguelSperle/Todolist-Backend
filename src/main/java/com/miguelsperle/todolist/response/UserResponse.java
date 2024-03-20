package com.miguelsperle.todolist.response;

import com.miguelsperle.todolist.entities.UsersEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class UserResponse {
    public static Object generateResponse(Object user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);

        return map;
    }

}