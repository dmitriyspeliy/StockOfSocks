package ru.skypro.StockOfSocks.record;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.skypro.StockOfSocks.entity.Socks} entity
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocksRecord implements Serializable {
    @NotNull(message = "Название носков должно быть заполнено")
    @NotBlank(message = "Название носков не должно быть пустым")
    String socksColor;
    @NotNull(message = "Количество хлопка не может быть пустым")
    @Min(value = 1, message = "от 1")
    @Max(value = 100, message = "до 100")
    Integer socksCotton;
    @NotNull(message = "Количество носков не может быть пустым")
    @Min(value = 1, message = "Количество носков не меньше 1") @Max(value = 400, message = "Количество носков до 400")
    Integer socksCount;

}
