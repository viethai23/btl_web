package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Client;
import com.btl_web.btl_web.model.dto.ClientRequestDto;
import com.btl_web.btl_web.model.dto.ClientResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

@Component
public class ClientMapper {
    private final ModelMapper modelMapper;

    public ClientMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Client toEntity(ClientRequestDto dto) {
        return modelMapper.map(dto, Client.class);
    }

    public ClientResponseDto toDto(Client entity) {
        return modelMapper.map(entity, ClientResponseDto.class);
    }
}
