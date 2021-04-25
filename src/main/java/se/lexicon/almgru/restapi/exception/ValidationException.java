package se.lexicon.almgru.restapi.exception;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(String message) {
        super(message);
    }
}
