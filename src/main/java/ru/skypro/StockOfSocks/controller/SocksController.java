package ru.skypro.StockOfSocks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skypro.StockOfSocks.loger.FormLogInfo;
import ru.skypro.StockOfSocks.record.SocksRecord;
import ru.skypro.StockOfSocks.service.SocksService;

import javax.validation.Valid;
import javax.validation.constraints.*;


/**
 * Контроллер
 */

@RestController
@RequestMapping("/api/socks")
@Tag(name = "Носки")
@Slf4j
@Validated
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }


    @Operation(summary = "Возвращает общее количество носков на складе")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, результат в теле ответа в виде строкового представления целого числа",
                    content = {
                            @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = Integer.class)))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)"
            )
    })
    @GetMapping
    public ResponseEntity<Integer> findCountOfSocksByColorAndCotton(
            @Parameter(description = "Цвет",
                    example = "Красный")
            @RequestParam("color")
            @NotNull(message = "Обязательно нужно заполнить поле")
            @NotBlank(message = "Обязательно нужно заполнить поле")
            @Size(min = 5, max = 50, message
                    = "Название должно быть от 5 до 50 символов")
            String color,
            @Parameter(description = "Операция",
                    example = "Из трех значений:  moreThan, lessThan, equal")
            @RequestParam(name = "operation")
            @NotNull(message = "Обязательно нужно заполнить поле")
            @NotBlank(message = "Обязательно нужно заполнить поле")
            @Size(min = 5, max = 50, message
                    = "Название должно быть от 5 до 50 символов")
            String operation,
            @RequestParam(name = "cottonPart")
            @NotNull
            @Parameter(description = "Значение процента хлопка в составе носков",
                    example = "35")
            @Max(value = 100, message = "max 100")
            @Min(value = 1, message = "min 1")
            Integer cotton) {
        log.info(FormLogInfo.getInfo());
        return ResponseEntity.ok(socksService.findCountOfSocks(color, operation, cotton));
    }

    @Operation(summary = "Регистрирует приход носков на склад")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось добавить приход"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)"
            )
    })
    @PostMapping("/income")
    public void incomeSocks(
            @Valid @RequestBody
            @Parameter(description = "Пара носков")
            SocksRecord socksRecord) {
        log.info(FormLogInfo.getInfo());
        socksService.addCountSocks(socksRecord);
    }


    @Operation(summary = "Регистрирует отпуск носков со склада")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось взять носки"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)"
            )
    })
    @PostMapping("/outcome")
    public void outcomeSocks(
            @Valid @RequestBody
            @Parameter(description = "Пара носков")
            SocksRecord socksRecord) {
        log.info(FormLogInfo.getInfo());
        socksService.removeCountSocks(socksRecord);
    }

}
