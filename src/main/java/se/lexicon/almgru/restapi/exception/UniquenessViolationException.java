package se.lexicon.almgru.restapi.exception;

public class UniquenessViolationException extends RuntimeException {
    public UniquenessViolationException(String message) {
        super(message);
    }
}
