package com.example.pp.Controller;


import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.Service.ClientService;
import com.example.pp.Service.ClientServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class Controller {

    private final ClientServiceImpl clientServiceImpl;

    public Controller(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @GetMapping("http://localhost:8081/api/v1/client/getClient")
    public List<ClientsInfo> getClients() {
        return clientServiceImpl.findAllClientsByPhoneNumber();
    }

    @GetMapping("http://localhost:8081/api/v1/client/{clientId}")
    public ClientsInfo getClientId(@PathVariable String clientId) {
        return clientServiceImpl.findClientById(clientId);
    }
}
