package ru.skypro.StockOfSocks.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.StockOfSocks.entity.Socks;
import ru.skypro.StockOfSocks.mapper.SocksMapper;
import ru.skypro.StockOfSocks.record.SocksRecord;
import ru.skypro.StockOfSocks.repository.SocksRepository;
import ru.skypro.StockOfSocks.service.SocksService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Mvc тесты контроллера {@link SocksController}
 */

@WebMvcTest(SocksController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class SocksControllerTest {

    @InjectMocks
    SocksController controller;
    @MockBean
    SocksService socksService;
    @MockBean
    SocksRepository repository;
    @Spy
    SocksMapper socksMapper;
    @Autowired
    MockMvc mockMvc;

    //переменные для теста
    final String OPERATION_MORETHAN = "moreThan";
    SocksRecord socksRecord;
    SocksRecord socksRecordWithZeroCount;
    Socks socks;
    Collection<Socks> collection;
    Collection<SocksRecord> socksRecords;
    Collection<SocksRecord> socksRecordsWithZeroCount;
    String color = "red";
    Integer cotton = 100;
    Integer socksCount = 100;
    JSONObject socksDTOJSON;
    JSONObject socksDTOJSONNonValid;

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
        socksDTOJSON = new JSONObject();
        socksDTOJSON.put("socksColor", color);
        socksDTOJSON.put("socksCotton", cotton);
        socksDTOJSON.put("socksCount", socksCount);
        socksDTOJSONNonValid = new JSONObject();
        socksDTOJSONNonValid.put("socksColor", "");
        socksDTOJSONNonValid.put("socksCotton", -34);
        socksDTOJSONNonValid.put("socksCount", 5000);
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
        socksDTOJSON = null;
        socksDTOJSONNonValid = null;
    }

    @Test
    void findCountOfSocksByColorAndCottonPositive() throws Exception {
        String url = "/api/socks";

        when(socksService.findCountOfSocks(color, OPERATION_MORETHAN, cotton))
                .thenReturn(socksCount);

        mockMvc.perform(get(url)
                        .param("color", color)
                        .param("operation", OPERATION_MORETHAN)
                        .param("cottonPart", String.valueOf(cotton))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(socksCount))
                .andExpect(status().isOk());

    }

    @Test
    void findCountOfSocksByColorAndCottonNegative() throws Exception {
        String url = "/api/socks";

        mockMvc.perform(get(url)
                        .param("color", "")
                        .param("operation", OPERATION_MORETHAN)
                        .param("cottonPart", String.valueOf(cotton))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(url)
                        .param("color", color)
                        .param("operation", "test")
                        .param("cottonPart", String.valueOf(cotton))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(url)
                        .param("color", "")
                        .param("operation", OPERATION_MORETHAN)
                        .param("cottonPart", String.valueOf(-23))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void incomeSocksPositive() throws Exception {
        String url = "/api/socks/income";

        mockMvc.perform(multipart(url, HttpMethod.POST)
                        .content(socksDTOJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void incomeSocksNegative() throws Exception {
        String url = "/api/socks/income";

        mockMvc.perform(multipart(url, HttpMethod.POST)
                        .content(socksDTOJSONNonValid.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void outcomeSocksPositive() throws Exception {
        String url = "/api/socks/outcome";

        mockMvc.perform(multipart(url, HttpMethod.POST)
                        .content(socksDTOJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void outcomeSocksNegative() throws Exception {
        String url = "/api/socks/outcome";

        mockMvc.perform(multipart(url, HttpMethod.POST)
                        .content(socksDTOJSONNonValid.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}