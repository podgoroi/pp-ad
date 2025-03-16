package com.example.pp.Sheduler;

import com.example.pp.Service.ClientService;
import com.example.pp.Service.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

public class Sheduler {
    private final ClientServiceImpl clientServiceImpl;

    public Sheduler(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @Scheduled(cron = "0 1 * * * *", zone = "Europe/Moscow")
    public void findClientsToPhone () {
        clientServiceImpl.findAllClientsByPhoneNumber();
    }
}
