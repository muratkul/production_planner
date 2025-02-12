package com.production_planner.exception.handler;


import com.production_planner.dto.ResponseDto;
import com.production_planner.exception.NameAlreadyExistsException;
import com.production_planner.exception.NotFoundException;
import com.production_planner.exception.ProjectConfigurationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> notFoundException(NotFoundException exception) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(NameAlreadyExistsException.class)
    public ResponseEntity<ResponseDto> notFoundException(NameAlreadyExistsException exception) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto> constraintViolationException(ConstraintViolationException exception) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> dataIntegrityViolationException(DataIntegrityViolationException exception) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(ProjectConfigurationException.class)
    public ResponseEntity<ResponseDto> constraintViolationException(ProjectConfigurationException exception) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ResponseDto> generateErrorResponse(HttpStatus httpStatus, Throwable exception) {
        generateErrorLog(exception);
        return new ResponseEntity<>(new ResponseDto(httpStatus.value(), exception.getMessage()), httpStatus);
    }

    private void generateErrorLog(Throwable exception) {
        logger.error("ErrorMessage: {}", exception);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> vaidationErrorList = ex.getBindingResult().getAllErrors();

        vaidationErrorList.forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
}
