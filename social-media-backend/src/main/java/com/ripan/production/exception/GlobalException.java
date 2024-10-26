package com.ripan.production.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * The @ControllerAdvice annotation in Spring MVC is used for global exception handling, model attributes, and data binding. It applies to multiple controllers across the entire application rather than being confined to a single controller.
 * Here's what @ControllerAdvice can do:
 * Global Exception Handling: It allows you to define @ExceptionHandler methods that can handle exceptions thrown by any controller in your application.
 * Global Model Attributes: Methods annotated with @ModelAttribute in a class annotated with @ControllerAdvice can add attributes to the model for every request handled by controllers.
 * Global Data Binding: You can use @InitBinder methods to configure data binding for all controllers, such as date format conversion or adding custom editors.
 */
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception e, WebRequest request){
        ErrorDetails error = new ErrorDetails(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException userException, WebRequest request){
        ErrorDetails error = new ErrorDetails(userException.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
