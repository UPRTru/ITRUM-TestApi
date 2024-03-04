package api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundValletId extends RuntimeException {
    public NotFoundValletId(UUID valletId) {
        super("valletId :" + valletId + " not found");
    }
}
