package learn.recipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class GlobalErrHandler {

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleException(HttpMediaTypeNotSupportedException ex) {
        return new ResponseEntity<>("Content-Type application/json required.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(SQLSyntaxErrorException.class)
//    public ResponseEntity<String> handleException(SQLSyntaxErrorException ex) {
//        return new ResponseEntity<>("whoops", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleException(AuthenticationException ex) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // logging.....
        return new ResponseEntity<>("oh noooooooo....", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
