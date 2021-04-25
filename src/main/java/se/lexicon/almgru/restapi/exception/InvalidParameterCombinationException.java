package se.lexicon.almgru.restapi.exception;

public class InvalidParameterCombinationException extends IllegalArgumentException {
    public InvalidParameterCombinationException(String message) {
        super(message);
    }
}
