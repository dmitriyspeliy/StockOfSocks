package ru.skypro.StockOfSocks.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.StockOfSocks.entity.Socks;
import ru.skypro.StockOfSocks.entity.SocksId;

import java.util.Collection;


/**
 * Репозиторий для таблицы носков
 */
@Repository
public interface SocksRepository extends JpaRepository<Socks, SocksId> {

    @Query("select s from Socks s where upper(s.socksColor) = upper(:color) and s.socksCotton < :cotton")
    Collection<Socks> findSocksBySocksColorIgnoreCaseAndAndSocksCottonIsLessThan(@Param("color") String color, @Param("cotton") Integer cotton);

    @Query("select s from Socks s where upper(s.socksColor) = upper(:color) and s.socksCotton > :cotton")
    Collection<Socks> findSocksBySocksColorIgnoreCaseAndAndSocksCottonGreaterThan(@Param("color") String color, @Param("cotton") Integer cotton);

    @Query("select s from Socks s where upper(s.socksColor) = upper(:color) and s.socksCotton = :cotton")
    Collection<Socks> findSocksBySocksColorIgnoreCaseAndSocksCottonEquals(@Param("color") String color, @Param("cotton") Integer cotton);

}
