package com.management.yuvro.config;

import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.exceptions.EntityExistsException;
import com.management.yuvro.exceptions.EntityNotFoundException;
import com.management.yuvro.exceptions.InvalidOptionException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<CommonApiResponse> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new CommonApiResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new CommonApiResponse("Internal Server Error: " + ex.getMessage(), false),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, EntityExistsException.class, InvalidOptionException.class})
    public ResponseEntity<CommonApiResponse> handleBadRequest(Exception ex) {
        return new ResponseEntity<>(new CommonApiResponse(ex.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}
