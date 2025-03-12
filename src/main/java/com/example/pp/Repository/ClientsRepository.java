package com.example.pp.Repository;

import com.example.pp.ClientsDto.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
    String phoneFind(String phone);
    List<Clients> findByMessageSendFalse();
}
