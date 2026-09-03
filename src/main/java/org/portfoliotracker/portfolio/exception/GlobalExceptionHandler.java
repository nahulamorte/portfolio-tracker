package org.portfoliotracker.portfolio.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.portfoliotracker.portfolio.auth.exception.EmailAlreadyExistsException;
import org.portfoliotracker.portfolio.auth.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Generic error --> Internal Server 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(RuntimeException exc){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    //Jwt - Auth flow errors
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsernameExists(UsernameAlreadyExistsException exc){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailExists(EmailAlreadyExistsException exc){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(BadCredentialsException exc){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwt(ExpiredJwtException exc) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(AccessDeniedException exc){
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({MalformedJwtException.class, SignatureException.class, UnsupportedJwtException.class})
    public ResponseEntity<ErrorResponseDTO> handleInvalidJwt(Exception exc) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(), status.getReasonPhrase(),
                "Token inválido", LocalDateTime.now());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMediaType(HttpMediaTypeException exc){
        HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //Exception of entities
    //Portfolio:
    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePortfolioNotFound(PortfolioNotFoundException exc){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    //Asset:
    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAssetNotFound(AssetNotFoundException exc){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                exc.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }


}
