package com.example.pp.Service;


import com.example.pp.ClientsDto.Clients;
import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.ClientsDto.Message;
import com.example.pp.FeignClientss.FeignClients;
import com.example.pp.ClientMap.ClientMap;
import com.example.pp.Repository.ClientsRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final Month monthNow = LocalDate.now().getMonth();
    private final FeignClients fcl;
    private final ClientsRepository clientsRepository;
    private final ClientMap mapper;
    private final KafkaTemplate<String, Message> kafkaTemplate;
    @Override
    public void findAllClientsByPhoneNumber() {
        try{
            List<ClientsInfo> listClietns = fcl.getClients();
            for (ClientsInfo client : listClietns) {
                if (client != null && client.getPhone().endsWith("7")
                        && client.getBirthday().getMonth() == monthNow
                        && clientsRepository.phoneFind(client.getPhone()) == null) {
                    Clients clients = mapper.clientsInfo(client);
                    clientsRepository.saveAndFlush(clients);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void findClientById(String id) {
        try {
            ClientsInfo clientsInfo = fcl.getClientById(id);
            if (clientsInfo != null && clientsInfo.getPhone().endsWith("7")
                    && clientsInfo.getBirthday().getMonth() == monthNow) {
                Clients clients = mapper.clientsInfo(clientsInfo);
                clientsRepository.saveAndFlush(clients);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
//sdgsdgsdgsdg