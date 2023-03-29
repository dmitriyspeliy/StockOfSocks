package ru.skypro.StockOfSocks.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * эксепш - класс обертка
 * {@link CustomExceptionHandler#handleUserNotFoundException(ElemNotFoundChecked)}
 */

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String exMessage;

}