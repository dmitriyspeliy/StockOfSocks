package ru.skypro.StockOfSocks.service;

import ru.skypro.StockOfSocks.record.SocksRecord;

/**
 * Сервис для носков
 */

public interface SocksService {

    /**
     * Возвращает общее количество носков на складе
     * @param color цвет
     * @param operation операция
     * @param cotton числовое значение хлопка в составе
     * @return количество носков на складе
     */
    Integer findCountOfSocks(String color, String operation, Integer cotton);

    /**
     *  Регистрирует приход носков на склад
     * @param socksRecord  - сущность носки
     */
    void addCountSocks(SocksRecord socksRecord);

    /**
     * Регистрирует отпуск носков со склада
     * @param socksRecord - сущность носки
     */
    void removeCountSocks(SocksRecord socksRecord);
}
