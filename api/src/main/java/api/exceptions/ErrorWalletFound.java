package api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorWalletFound extends RuntimeException {
    public ErrorWalletFound(String message) {
        super("Wallet found: " + message);
    }
}
