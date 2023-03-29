package ru.skypro.StockOfSocks.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

/**
 * Контроллер для всех эксепш
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * эксепш, если элемента нет в базе
     */
    @ExceptionHandler(ElemNotFoundChecked.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(ElemNotFoundChecked ex) {
        String incorrectRequest = "Такого элемента нет";
        ErrorResponse error = new ErrorResponse(incorrectRequest,ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Отловка спринга
     */
    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(MethodArgumentNotValidException ex) {
        String badRequest = "Какие-то данные были введены неправильно";
        ErrorResponse error = new ErrorResponse(badRequest,ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Отловка спринга
     */
    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(MethodArgumentTypeMismatchException ex) {
        String badRequest = "Null ввести нельзя";
        ErrorResponse error = new ErrorResponse(badRequest,ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Отловка спринга
     */
    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(ConstraintViolationException ex) {
        String badRequest = "Какие-то данные были введены неправильно";
        ErrorResponse error = new ErrorResponse(badRequest,ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}