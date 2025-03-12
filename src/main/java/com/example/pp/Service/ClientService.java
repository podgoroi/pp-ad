package com.example.pp.Service;

import com.example.pp.ClientsDto.Clients;
import com.example.pp.ClientsDto.ClientsInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public interface ClientService {
    List<ClientsInfo> findAllClientsByPhoneNumber ();
    ClientsInfo findClientById (String id);
}
