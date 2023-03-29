package ru.skypro.StockOfSocks.record;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.skypro.StockOfSocks.entity.Socks} entity
 */

public class SocksRecord implements Serializable {
    @NotNull(message = "Название носков должно быть заполнено")
    @NotBlank(message = "Название носков не должно быть пустым")
    private String socksColor;
    @NotNull(message = "Количество хлопка не может быть пустым")
    @Min(value = 1, message = "от 1")
    @Max(value = 100, message = "до 100")
    private Integer socksCotton;
    @NotNull(message = "Количество носков не может быть пустым")
    @Min(value = 1, message = "не меньше 1") @Max(value = 400, message = "до 400")
    private Integer socksCount;

    public SocksRecord(String socksColor, Integer socksCotton, Integer socksCount) {
        this.socksColor = socksColor;
        this.socksCotton = socksCotton;
        this.socksCount = socksCount;
    }

    public SocksRecord() {
    }

    public String getSocksColor() {
        return socksColor;
    }

    public void setSocksColor(String socksColor) {
        this.socksColor = socksColor;
    }

    public Integer getSocksCotton() {
        return socksCotton;
    }

    public void setSocksCotton(Integer socksCotton) {
        this.socksCotton = socksCotton;
    }

    public Integer getSocksCount() {
        return socksCount;
    }

    public void setSocksCount(Integer socksCount) {
        this.socksCount = socksCount;
    }

    @Override
    public String toString() {
        return "SocksRecord{" +
                "socksColor='" + socksColor + '\'' +
                ", socksCotton=" + socksCotton +
                ", socksCount=" + socksCount +
                '}';
    }
}
