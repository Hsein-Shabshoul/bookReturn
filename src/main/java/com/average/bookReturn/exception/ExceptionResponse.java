package com.average.bookReturn.exception;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
public record ExceptionResponse(LocalDateTime timestamp,
                                String message,
                                String details,
                                HttpStatus error,
                                int status) {

}
