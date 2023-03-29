package ru.skypro.StockOfSocks.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.StockOfSocks.entity.Socks;
import ru.skypro.StockOfSocks.entity.SocksId;
import ru.skypro.StockOfSocks.exception.ElemNotFoundChecked;
import ru.skypro.StockOfSocks.mapper.SocksMapper;
import ru.skypro.StockOfSocks.record.SocksRecord;
import ru.skypro.StockOfSocks.repository.SocksRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


/**
 * Тесты на сервис {@link SocksServiceImpl}
 */

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class SocksServiceImplTest {

    @InjectMocks
    SocksServiceImpl service;
    @Mock
    SocksRepository repository;
    @Mock
    SocksMapper socksMapper;

    //переменные для теста
    final String OPERATION_MORETHAN = "moreThan";
    SocksRecord socksRecord;
    SocksRecord socksRecordWithZeroCount;
    Socks socks;
    Collection<Socks> collection;
    Collection<SocksRecord> socksRecords;
    Collection<SocksRecord> socksRecordsWithZeroCount;
    String color;
    Integer cotton;
    Integer socksCount;

    @BeforeEach
    void setUp() {
        color = "red";
        cotton = 100;
        socksCount = 100;
        socksRecord = new SocksRecord(color, cotton, socksCount);
        socks = new Socks(color, cotton, socksCount);
        collection = new ArrayList<>();
        collection.add(socks);
        socksRecords = new ArrayList<>();
        socksRecords.add(socksRecord);
        socksRecordsWithZeroCount = new ArrayList<>();
        socksRecordWithZeroCount = new SocksRecord(color, 12, 0);
        socksRecordsWithZeroCount.add(socksRecordWithZeroCount);
    }

    @AfterEach
    void tearDown() {
        color = null;
        cotton = null;
        socksCount = null;
        socksRecord = null;
        socks = null;
        collection = null;
        socksRecords = null;
        socksRecordsWithZeroCount = null;
        socksRecordWithZeroCount = null;
    }


    @Test
    void findCountOfSocks() {
        when(repository.findSocksBySocksColorIgnoreCaseAndAndSocksCottonGreaterThan(color, cotton))
                .thenReturn(collection);
        when(socksMapper.toRecordList(collection)).thenReturn(socksRecords);

        assertEquals(socksCount, service.findCountOfSocks(color, OPERATION_MORETHAN, socksCount));

        verify(repository, times(1)).findSocksBySocksColorIgnoreCaseAndAndSocksCottonGreaterThan(color, cotton);

    }

    @Test
    void findCountOfSocksNeagtiveWithINCORRECT_OPERATION() {

        assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
                        () -> service.findCountOfSocks(color, "INCORRECT_OPERATION", socksCount)).
                withMessageContaining("No found element where color operation is");

    }

    @Test
    void findCountOfSocksWithZeroResult() {
        when(repository.findSocksBySocksColorIgnoreCaseAndAndSocksCottonGreaterThan(color, cotton))
                .thenReturn(collection);
        when(socksMapper.toRecordList(collection)).thenReturn(socksRecordsWithZeroCount);

        assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
                        () -> service.findCountOfSocks(color, OPERATION_MORETHAN, socksCount)).
                withMessageContaining("Носок с запросом, содержание хлопка:" + cotton + " и цвета:" + color + " нет");

    }

    @Test
    void findCountOfSocksWithIvalidArg() {

        assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
                () -> service.findCountOfSocks("IVALID_COLOR", OPERATION_MORETHAN, socksCount));

        assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
                () -> service.findCountOfSocks(color, OPERATION_MORETHAN, -12));

        assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
                () -> service.findCountOfSocks(color, OPERATION_MORETHAN, 0));

        assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
                () -> service.findCountOfSocks(color, OPERATION_MORETHAN, 101));

    }

    @Test
    void addCountSocks() {
        when(repository.findById(any(SocksId.class))).thenReturn(Optional.ofNullable(socks));
        when(socksMapper.socksToSocksRecord(socks)).thenReturn(socksRecord);
        when(socksMapper.socksRecordToSocks(socksRecord)).thenReturn(socks);
        when(repository.save(socks)).thenReturn(socks);
        service.addCountSocks(socksRecord);

        assertEquals(socksRecord.getSocksCount(), socksCount + socksCount);
        verify(repository, times(1)).save(socks);
    }

    @Test
    void addCountSocksNotExist() {
        when(repository.findById(any(SocksId.class))).thenReturn(Optional.ofNullable(socks));
        when(socksMapper.socksToSocksRecord(socks)).thenReturn(null);
        when(socksMapper.socksRecordToSocks(socksRecord)).thenReturn(socks);
        when(repository.save(socks)).thenReturn(socks);
        service.addCountSocks(socksRecord);

        assertEquals(socksRecord.getSocksCount(), socksCount);
        verify(repository, times(1)).save(socks);
    }

    @Test
    void removeCountSocksIfExistAndCountZero() {
        when(repository.findById(any(SocksId.class))).thenReturn(Optional.ofNullable(socks));
        when(socksMapper.socksToSocksRecord(socks)).thenReturn(socksRecord);
        when(socksMapper.socksRecordToSocks(socksRecord)).thenReturn(socks);
        service.removeCountSocks(socksRecord);
        verify(repository, times(1)).delete(socks);
    }

    @Test
    void removeCountSocksIfExistAndCountMoreThanZero() {
        SocksRecord socksRecord1 = new SocksRecord(color, cotton, 1000);
        when(repository.findById(any(SocksId.class))).thenReturn(Optional.ofNullable(socks));
        when(socksMapper.socksToSocksRecord(socks)).thenReturn(socksRecord1);
        when(socksMapper.socksRecordToSocks(any(SocksRecord.class))).thenReturn(socks);
        when(repository.save(any(Socks.class))).thenReturn(socks);

        service.removeCountSocks(socksRecord);

        verify(repository, times(1)).save(socks);
    }

    @Test
    void removeCountSocksIfExistAndCountLessThanZero() {
        SocksRecord socksRecord1 = new SocksRecord(color, cotton, 30);
        when(repository.findById(any(SocksId.class))).thenReturn(Optional.ofNullable(socks));
        when(socksMapper.socksToSocksRecord(socks)).thenReturn(socksRecord1);

        assertThatExceptionOfType(ElemNotFoundChecked.class).isThrownBy(
                        () -> service.removeCountSocks(socksRecord)).
                withMessageContaining(" Не возможно осуществить отпуск, так как фактически носок меньше");

    }

}