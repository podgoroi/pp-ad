package com.example.pp.Controller;


import com.example.pp.Service.ClientService;
import com.example.pp.Service.ClientServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client")
public class Controller {

    private final ClientServiceImpl clientServiceImpl;

    public Controller(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @GetMapping("getClient")
    public void getClients() {
        clientServiceImpl.findAllClientsByPhoneNumber();
    }

    @GetMapping("/{clientId}")
    public void getClientId(@PathVariable String clientId) {
        clientServiceImpl.findClientById(clientId);
    }
}
