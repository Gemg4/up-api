package com.example.uep_planner.upserver.http;

import com.example.uep_planner.upserver.dtos.other.ApiResponse;
import com.example.uep_planner.upserver.dtos.other.Meta;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;


public class ResponseBuilder {

    public static <T> ResponseEntity<@NonNull ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, message, 200, data, new Meta(LocalDateTime.now())));
    }

    public static <T> ResponseEntity<@NonNull ApiResponse<T>> created(String message, T data) {
        return new ResponseEntity<>(new ApiResponse<>(true, message, 201, data, new Meta(LocalDateTime.now())), HttpStatus.CREATED);
    }

}
