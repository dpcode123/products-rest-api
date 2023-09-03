package com.dpcode123.products.exception;

import com.dpcode123.products.exception.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoContentFoundException.class)
    public final ResponseEntity<ApiException> handleNoContentFoundException(NoContentFoundException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        return createApiExceptionResponseEntity(exception, request, httpStatus);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiException> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return createApiExceptionResponseEntity(exception, request, httpStatus);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<ApiException> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        return createApiExceptionResponseEntity(exception, request, httpStatus);
    }

    @ExceptionHandler(TransactionFailedException.class)
    public final ResponseEntity<ApiException> handleTransactionFailedException(TransactionFailedException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        return createApiExceptionResponseEntity(exception, request, httpStatus);
    }

    @ExceptionHandler(HnbApiRequestException.class)
    public final ResponseEntity<ApiException> handleHnbApiRequestException(HnbApiRequestException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return createApiExceptionResponseEntity(exception, request, httpStatus);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ApiException> handleCustomException(CustomException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return createApiExceptionResponseEntity(exception, request, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiException> handleAllExceptions(Exception exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return createApiExceptionResponseEntity(exception, request, httpStatus);
    }

    private ResponseEntity<ApiException> createApiExceptionResponseEntity(Exception exception, WebRequest request, HttpStatus httpStatus) {
        ApiException apiException = new ApiException(new Date(), exception.getMessage(), request.getDescription(false));
        log.warn("Exception: {} - {}", httpStatus.getReasonPhrase(), apiException);
        return new ResponseEntity<>(apiException, httpStatus);
    }

}
