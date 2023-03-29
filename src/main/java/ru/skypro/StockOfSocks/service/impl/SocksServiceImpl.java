package ru.skypro.StockOfSocks.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.StockOfSocks.entity.SocksId;
import ru.skypro.StockOfSocks.exception.ElemNotFoundChecked;
import ru.skypro.StockOfSocks.loger.FormLogInfo;
import ru.skypro.StockOfSocks.mapper.SocksMapper;
import ru.skypro.StockOfSocks.record.SocksRecord;
import ru.skypro.StockOfSocks.repository.SocksRepository;
import ru.skypro.StockOfSocks.service.SocksService;


/**
 * Реализация {@link ru.skypro.StockOfSocks.service.SocksService}
 */

@Service
@Slf4j
public class SocksServiceImpl implements SocksService {

    private final SocksRepository socksRepository;
    private final SocksMapper socksMapper;
    private final String OPERATION_LESSTHAN = "lessThan";
    private final String OPERATION_MORETHAN = "moreThan";
    private final String OPERATION_EQUALS = "equals";

    public SocksServiceImpl(SocksRepository socksRepository, SocksMapper socksMapper) {
        this.socksRepository = socksRepository;
        this.socksMapper = socksMapper;
    }

    public Integer findCountOfSocks(String color, String operation, Integer cotton) {
        log.info(FormLogInfo.getInfo());
        int result = 0;
        switch (operation) {
            case (OPERATION_MORETHAN):
                log.info("Was invoked method for get all Socks where operation is " + OPERATION_MORETHAN + "color is " + color + " and cotton greater then " + cotton);
                result += socksMapper.toRecordList(socksRepository.findSocksBySocksColorIgnoreCaseAndAndSocksCottonGreaterThan(color, cotton)).stream().mapToInt(SocksRecord::getSocksCount).sum();
                break;
            case (OPERATION_LESSTHAN):
                log.info("Was invoked method for get all Socks where operation is " + OPERATION_LESSTHAN + "color is " + color + " and cotton less then " + cotton);
                result += socksMapper.toRecordList(socksRepository.findSocksBySocksColorIgnoreCaseAndAndSocksCottonIsLessThan(color, cotton)).stream().mapToInt(SocksRecord::getSocksCount).sum();
                break;
            case (OPERATION_EQUALS):
                log.info("Was invoked method for get all Socks where operation is " + OPERATION_EQUALS + " color is " + color + " and cotton equals " + cotton);
                result += socksMapper.toRecordList(socksRepository.findSocksBySocksColorIgnoreCaseAndSocksCottonEquals(color, cotton)).stream().mapToInt(SocksRecord::getSocksCount).sum();
                break;
            default:
                log.error("No found element where color operation is " + operation + " + color is " + color + " and cotton equals " + cotton);
                throw new ElemNotFoundChecked("No found element where color operation is " + operation);
        }
        if (result == 0) {
            log.error("No found element where color operation is " + operation + " + color is " + color + " and cotton equals " + cotton);
            throw new ElemNotFoundChecked("Носок с запросом, содержание хлопка:" + cotton + " и цвета:" + color + " нет");
        }
        return result;
    }

    public void addCountSocks(SocksRecord socksRecord) {
        log.info(FormLogInfo.getInfo());
        SocksRecord check = socksMapper.socksToSocksRecord(socksRepository.findById(new SocksId(socksRecord.getSocksColor(), socksRecord.getSocksCotton())).orElse(null));
        if (check == null) {
            socksRepository.save(socksMapper.socksRecordToSocks(socksRecord));
            return;
        }
        int checkCount = check.getSocksCount() + socksRecord.getSocksCount();
        check.setSocksCount(checkCount);
        socksRepository.save(socksMapper.socksRecordToSocks(check));

    }

    public void removeCountSocks(SocksRecord socksRecord) {
        log.info(FormLogInfo.getInfo());
        SocksRecord check = socksMapper.socksToSocksRecord(socksRepository.findById(new SocksId(socksRecord.getSocksColor(), socksRecord.getSocksCotton()))
                .orElseThrow(() ->
                        new ElemNotFoundChecked("Не возможно осуществить отпуск, так как такого товара нет" + socksRecord)));
        int checkCount = check.getSocksCount() - socksRecord.getSocksCount();
        if (checkCount == 0) {
            log.info("Удаляем носок так как количество ровно 0");
            socksRepository.delete(socksMapper.socksRecordToSocks(check));
            return;
        } else if (checkCount > 0) {
            log.info("Сохраняем носки");
            check.setSocksCount(checkCount);
            socksRepository.save(socksMapper.socksRecordToSocks(check));
            return;
        }
        throw new ElemNotFoundChecked("Не возможно осуществить отпуск, так как фактически носок меньше");
    }

}
