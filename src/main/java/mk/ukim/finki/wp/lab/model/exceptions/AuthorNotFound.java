package mk.ukim.finki.wp.lab.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // This makes Spring return 404
public class AuthorNotFound extends RuntimeException {
    public AuthorNotFound(String message) {
        super(message); // use the custom message
    }
}
