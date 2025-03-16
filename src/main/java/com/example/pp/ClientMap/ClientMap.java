package com.example.pp.ClientMap;

import com.example.pp.ClientsDto.Clients;
import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.ClientsDto.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMap {

    @Mapping(target = "fullName", expression = "java(clientsInfo.getName() + \" \" + clientsInfo.getSurname())") // Объединяем имя и фамилию
    @Mapping(target = "phone", source = "phone") // Маппинг номера телефона
    @Mapping(target = "birthday", source = "birthday") // Маппинг даты рождения
    @Mapping(target = "messageSend", constant = "false") // Устанавливаем значение по умолчанию
    Clients clientsInfo(ClientsInfo clientsInfo);

    default String toSmsMessage(Clients clients, String discount) {
        return String.format("%s, в этом месяце для вас действует скидка %s", clients.getFullName(), discount);
    }
}
