package api.hastobe.csms.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValueProvidedNotCorrectException extends RuntimeException {

    public ValueProvidedNotCorrectException() {
        super();
    }
    public ValueProvidedNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }
    public ValueProvidedNotCorrectException(String message) {
        super(message);
    }
    public ValueProvidedNotCorrectException(Throwable cause) {
        super(cause);
    }
}
