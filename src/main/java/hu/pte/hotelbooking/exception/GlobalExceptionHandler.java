package hu.pte.hotelbooking.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404: id or search result not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 400: wrong input
    // if the price <=0, checkout before checkin  or wrong payment status
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalid(InvalidRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 409: duplicate e-mail or room number, or deleting a row that others still in use
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleConflict(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(
                "Duplicate value, or this record is still used by another record.",
                HttpStatus.CONFLICT);
    }
}
