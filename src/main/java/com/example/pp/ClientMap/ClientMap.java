package com.example.pp.ClientMap;

import com.example.pp.ClientsDto.Clients;
import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.ClientsDto.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMap {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "messageSend", ignore = true)
    Clients clientsInfo(ClientsInfo clientsInfo);

    Message messageInfo(Clients clients, String message);

    default String toSmsMessage(Clients clients, String discount) {
        return String.format("%s, в этом месяце для вас действует скидка %s", clients.getFirstName(), discount);
    }
}
