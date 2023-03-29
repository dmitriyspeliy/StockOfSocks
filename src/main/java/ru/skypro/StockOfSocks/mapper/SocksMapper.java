package ru.skypro.StockOfSocks.mapper;

import org.mapstruct.Mapper;
import ru.skypro.StockOfSocks.entity.Socks;
import ru.skypro.StockOfSocks.record.SocksRecord;

import java.util.Collection;

/**
 * маппер для {@link Socks} готовый DTO {@link ru.skypro.StockOfSocks.record.SocksRecord}
 */
@Mapper(componentModel = "spring")
public interface SocksMapper {

    Collection<Socks> toEntityList(Collection<SocksRecord> socksRecords);

    Collection<SocksRecord> toRecordList(Collection<Socks> socks);

    Socks socksRecordToSocks(SocksRecord socksRecord);

    SocksRecord socksToSocksRecord(Socks socks);

}
