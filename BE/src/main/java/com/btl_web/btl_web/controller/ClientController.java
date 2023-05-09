package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.ClientRequestDto;
import com.btl_web.btl_web.model.dto.ClientResponseDto;
import com.btl_web.btl_web.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    // Lấy tất cả client
    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        List<ClientResponseDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
    // Lấy client theo id
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        ClientResponseDto client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }
    // Lấy client theo tên
    @GetMapping("/search")
    public ResponseEntity<List<ClientResponseDto>> searchClientsByName(@RequestParam String name) {
        List<ClientResponseDto> clients = clientService.searchClientsByName(name);
        return ResponseEntity.ok(clients);
    }
    // Tạo client mới
    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@RequestBody ClientRequestDto requestDto) {
        ClientResponseDto responseDto = clientService.createClient(requestDto);
        return ResponseEntity.created(URI.create("/api/clients/" + responseDto.getId()))
                .body(responseDto);
    }
    // Sửa client
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @RequestBody ClientRequestDto requestDto) {
        ClientResponseDto client = clientService.updateClient(id, requestDto);
        return ResponseEntity.ok(client);
    }
    // Xóa client
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
