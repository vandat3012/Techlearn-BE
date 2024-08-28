package com.techzen.techlearn.exception;

import com.techzen.techlearn.dto.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, RuntimeException.class, AppException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseData> handlerValidationException(Exception e, WebRequest request) {

        ResponseData error = new ResponseData();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setPath(request.getDescription(false).replace("uri=", ""));

        String message = e.getMessage();
        if(e instanceof MethodArgumentNotValidException){
            int start = message.lastIndexOf("[");
            int end = message.lastIndexOf("]");
            message = message.substring(start + 1, end -1 );
            error.setError("Payload Invalid");
            error.setCode(1008);
            error.setMessage(message);

            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                error.additionalProperty(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }
        else if(e instanceof AppException appException){
            error.setError("Data Invalid");
            error.setCode(appException.getErrorCode().getCode());
            error.setMessage(appException.getMessage());
        }
        else if(e instanceof RuntimeException){
            error.setError("PathVariable Invalid");
            error.setMessage(message);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
