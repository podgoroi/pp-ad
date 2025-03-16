package com.example.pp.FeignClientss;

import com.example.pp.ClientsDto.ClientsInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(url = "http://localhost:8081", name = "clients", path = "/api/v1")
public interface FeignClients {
    @PostMapping("/getClient")
    List<ClientsInfo> getClients();

    @PostMapping("/getClient/{clientid}")
    ClientsInfo getClientById(@PathVariable("clientid") String clientid);
}