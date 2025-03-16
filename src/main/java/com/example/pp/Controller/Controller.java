package com.example.pp.Controller;

import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.Service.ClientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Client API", description = "API для работы с клиентами")
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private final ClientServiceImpl clientServiceImpl;

    public Controller(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @GetMapping("/getClient")
    @Operation(
            summary = "Получить список всех клиентов",
            description = "Возвращает список всех клиентов, которые соответствуют критериям",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public List<ClientsInfo> getClients() {
        log.info("Запрос на получение списка клиентов");
        return clientServiceImpl.findAllClientsByPhoneNumber();
    }

    @GetMapping("/getClient/{clientId}")
    @Operation(
            summary = "Получить клиента по ID",
            description = "Возвращает информацию о клиенте по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос"),
                    @ApiResponse(responseCode = "404", description = "Клиент не найден"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ClientsInfo getClientById(
            @Parameter(description = "ID клиента", required = true, example = "123-QWE")
            @PathVariable("clientId") String clientId) {
        log.info("Запрос на получение клиента с ID: {}", clientId);
        return clientServiceImpl.findClientById(clientId);
    }
}
