package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.dto.ClientRequestDto;
import com.btl_web.btl_web.model.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {
    List<ClientResponseDto> getAllClients();
    ClientResponseDto getClientById(Long id);
    List<ClientResponseDto> searchClientsByName(String name);
    ClientResponseDto createClient(ClientRequestDto requestDto);
    ClientResponseDto updateClient(Long id, ClientRequestDto requestDto);
    void deleteClient(Long id);
}
