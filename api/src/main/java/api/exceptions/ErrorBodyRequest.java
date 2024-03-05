package api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorBodyRequest extends RuntimeException {
    public ErrorBodyRequest(String message) {
        super(message);
    }
}
