package api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughMoney extends RuntimeException {
    public NotEnoughMoney(Long money) {
        super("Not enough " + money + " money.");
    }
}
