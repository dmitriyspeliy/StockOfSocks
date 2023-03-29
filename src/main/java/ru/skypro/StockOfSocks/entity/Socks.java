package ru.skypro.StockOfSocks.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Класс сущность для таблицы носков
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(schema = "public", name = "socks")
@IdClass(SocksId.class)
public class Socks {

    /**
     * Составной ключ, первая часть цвет
     */
    @Column(name = "socks_color")
    @Id
    String socksColor;
    /**
     * Составной ключ, вторая часть количества хлопка
     */
    @Id
    @Column(name = "socks_cotton")
    Integer socksCotton;
    /**
     * Колчиество на складе
     */
    @Column(name = "socks_count")
    Integer socksCount;

}
