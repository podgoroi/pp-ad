package com.example.pp.Service;

import org.springframework.stereotype.Component;

@Component
public interface ClientService {
    void findAllClientsByPhoneNumber ();
    void findClientById (String id);
}
