package com.example.pp.Service;


import com.example.pp.ClientsDto.Clients;
import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.ClientsDto.Message;
import com.example.pp.FeignClientss.FeignClients;
import com.example.pp.ClientMap.ClientMap;
import com.example.pp.Repository.ClientsRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

@Service
@Getter
@Slf4j
public class ClientServiceImpl implements ClientService{

    private final Month monthNow = LocalDate.now().getMonth();
    private final FeignClients fcl;
    private final ClientsRepository clientsRepository;
    private final ClientMap mapper;
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public ClientServiceImpl(FeignClients fcl, ClientsRepository clientsRepository, ClientMap mapper, KafkaTemplate<String, Message> kafkaTemplate) {
        this.fcl = fcl;
        this.clientsRepository = clientsRepository;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${discount}")
    private String discount;

    private boolean shouldSendMessage() {
        return LocalTime.now(ZoneId.of("Europe/Moscow")).isBefore(LocalTime.of(19, 0));
    }

    @Override
    public List<ClientsInfo> findAllClientsByPhoneNumber() {
        try{
            List<ClientsInfo> listClietns = fcl.getClients();
            for (ClientsInfo client : listClietns) {
                if (client != null && client.getPhone().endsWith("7")
                        && client.getBirthday().getMonth() == monthNow
                        && clientsRepository.findClientsByPhone(client.getPhone()) == null) {
                    Clients clients = mapper.clientsInfo(client);
                    clientsRepository.save(clients);
                }
            }
            if (shouldSendMessage()) {
                List<Clients> messageSendFalse = clientsRepository.findByMessageSendFalse();
                for (Clients client : messageSendFalse) {
                    String message = mapper.toSmsMessage(client, discount);
                    Message smsMessage = new Message(message, client.getPhone());
                    kafkaTemplate.send("topic",smsMessage);
                    client.setMessageSend(true);
                    clientsRepository.save(client);
                }
            }
            return listClietns;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientsInfo findClientById(String id) {
        try {
            ClientsInfo clientsInfo = fcl.getClientById(id);
            if (clientsInfo != null && clientsInfo.getPhone().endsWith("7")
                    && clientsInfo.getBirthday().getMonth() == monthNow) {
                Clients clients = mapper.clientsInfo(clientsInfo);
                clientsRepository.save(clients);
            }
            return clientsInfo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
//sdgsdgsdgsdg