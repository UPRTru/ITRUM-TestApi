package api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundValletId extends RuntimeException {
    public NotFoundValletId(String valletId) {
        super("valletId :" + valletId + " not found");
    }
}
