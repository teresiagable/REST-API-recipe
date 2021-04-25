package se.lexicon.almgru.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InvalidParameterCombinationException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidParameterCombination(InvalidParameterCombinationException ex,
                                                                               WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(
                        LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                        ex.getMessage(), request.getDescription(false))
                );
    }
}
