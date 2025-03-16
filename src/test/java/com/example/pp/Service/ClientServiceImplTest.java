package com.example.pp.Service;

import com.example.pp.ClientMap.ClientMap;
import com.example.pp.ClientsDto.Clients;
import com.example.pp.ClientsDto.ClientsInfo;
import com.example.pp.ClientsDto.Message;
import com.example.pp.FeignClientss.FeignClients;
import com.example.pp.Repository.ClientsRepository;
import com.example.pp.Service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private FeignClients feignClients;

    @Mock
    private ClientMap mapper;

    @Mock
    private ClientsRepository clientsRepository;

    @Mock
    private KafkaTemplate<String, Message> kafkaTemplate;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    private ClientsInfo testClient;
    private Clients testClientEntity;

    @BeforeEach
    void setUp() {
        testClient = ClientsInfo.builder()
                .clientId("123-QWE")
                .name("Иван")
                .surname("Иванов")
                .phone("79001234567")
                .birthday(LocalDate.now().withMonth(LocalDate.now().getMonthValue()))
                .build();

        testClientEntity = new Clients();
        testClientEntity.setPhone("79001234567");
        testClientEntity.setMessageSend(false);
    }

    @Test
    void findAllClientsByPhoneNumber_ShouldSaveClientsAndSendMessage() {
        when(feignClients.getClients()).thenReturn(Collections.singletonList(testClient));
        when(clientsRepository.findClientsByPhone(anyString())).thenReturn(null);
        when(mapper.clientsInfo(any(ClientsInfo.class))).thenReturn(testClientEntity);

        // Вызываем метод
        List<ClientsInfo> result = clientServiceImpl.findAllClientsByPhoneNumber();

        // Проверяем результат
        assertNotNull(result);
        assertEquals(1, result.size());

        // Проверяем, что клиент был сохранен
        verify(clientsRepository, times(1)).save(testClientEntity);
    }
        @Test
        void testFindClientById () {
            // Мокируем FeignClient
            when(feignClients.getClientById("123-QWE")).thenReturn(testClient);

            // Вызываем метод
            ClientsInfo result = clientServiceImpl.findClientById("123-QWE");

            // Проверяем результат
            assertEquals(testClient, result);

            // Проверяем, что методы были вызваны
            verify(feignClients, times(1)).getClientById("123-QWE");
            verify(clientsRepository, times(1)).findClientsByPhone(anyString());
        }

        @Test
        void testFindClientById_NotFound () {
            // Мокируем FeignClient
            when(feignClients.getClientById("123-QWE")).thenReturn(null);

            // Вызываем метод
            ClientsInfo result = clientServiceImpl.findClientById("123-QWE");

            // Проверяем результат
            assertEquals(null, result);

            // Проверяем, что методы были вызваны
            verify(feignClients, times(1)).getClientById("123-QWE");
            verify(clientsRepository, never()).findClientsByPhone(anyString());
        }
    }
