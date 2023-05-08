package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.ClientMapper;
import com.btl_web.btl_web.model.Entity.Client;
import com.btl_web.btl_web.model.dto.ClientRequestDto;
import com.btl_web.btl_web.model.dto.ClientResponseDto;
import com.btl_web.btl_web.repository.ClientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(clientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        return clientMapper.toDto(client);
    }

    @Override
    public List<ClientResponseDto> searchClientsByName(String name) {
        List<Client> clients = clientRepository.findByFullNameContainingIgnoreCase(name);
        return clients.stream().map(clientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ClientResponseDto createClient(ClientRequestDto requestDto) {
        Client client = clientMapper.toEntity(requestDto);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public ClientResponseDto updateClient(Long id, ClientRequestDto requestDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        client.setFullName(requestDto.getFullName());
        client.setAddress(requestDto.getAddress());
        client.setPhoneNumber(requestDto.getPhoneNumber());
        client.setEmail(requestDto.getEmail());
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
}
