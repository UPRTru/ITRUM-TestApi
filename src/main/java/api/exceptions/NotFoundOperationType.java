package api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundOperationType extends RuntimeException {
    public NotFoundOperationType(String operationType) {
        super("operationType :" + operationType + " not found");
    }
}
