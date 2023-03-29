package ru.skypro.StockOfSocks.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.skypro.StockOfSocks.loger.FormLogInfo;


/**
 * эксепш - класс
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElemNotFoundChecked extends RuntimeException {
    public ElemNotFoundChecked(String textMessage) {
        super("Exception: " + textMessage + FormLogInfo.getInfo());
        System.err.println("Exception: " + textMessage + FormLogInfo.getException());
    }
}

