package com.umg.charly.nomina.Tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class FileExceptionAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleMaxSizeException(MaxUploadSizeExceededException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Maximo de tamaño 10 megas");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

