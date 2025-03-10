package com.example.pp.ClientMap;

import com.example.pp.ClientsDto.Clients;
import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.ClientsDto.Message;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMap {
    Clients clientsInfo(ClientsInfo clientsInfo);

    Message messageInfo(Clients clients, String message);
}
