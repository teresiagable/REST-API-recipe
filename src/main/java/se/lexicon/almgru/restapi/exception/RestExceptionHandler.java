package se.lexicon.almgru.restapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(ValidationException ex, WebRequest request) {
        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(
                        LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                        ex.getMessage(), request.getDescription(false)
                ));
    }

    @ExceptionHandler(UniquenessViolationException.class)
    public ResponseEntity<ExceptionResponse> handleUniquenessViolation(UniquenessViolationException ex,
                                                                       WebRequest request) {
        return ResponseEntity
                .unprocessableEntity()
                .body(new ExceptionResponse(
                        LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        HttpStatus.UNPROCESSABLE_ENTITY.name(), ex.getMessage(),
                        request.getDescription(false)
                ));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String path = request.getDescription(false);
        String message;

        if (path.equalsIgnoreCase("uri=/api/recipes")) {
            message = "Couldn't parse request body. Expected formats are: 'name' : string, 'instructions': string, " +
                      "'ingredients' : JSON Array, 'categories': JSON Array.";
        } else if (path.equalsIgnoreCase("uri=/api/ingredients")) {
            message = "Couldn't parse request body. Expected formats are: 'name' : string";
        } else {
            message = ex.getMessage();
        }

        return ResponseEntity
                .badRequest()
                .body(new ExceptionResponse(
                        LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), message,
                        path
                ));
    }
}
