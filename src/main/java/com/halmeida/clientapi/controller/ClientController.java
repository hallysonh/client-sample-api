package com.halmeida.clientapi.controller;

import com.halmeida.clientapi.models.Client;
import com.halmeida.clientapi.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ClientController {
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/client")
    public ResponseEntity<List<Client>> getClientList(
            @RequestParam(required = false) String query,
            @RequestParam(name = "birth-date-start", required = false) LocalDate birthDateStart,
            @RequestParam(name = "birth-date-end", required = false) LocalDate birthDateEnd,
            @RequestParam(required = false) Boolean activated,
            Pageable pageable
    ) {
        return service.getList(query, birthDateStart, birthDateEnd, activated, pageable);
    }

    @PostMapping("/client")
    public Client addClient(@RequestBody Client client) {
        return service.save(client);
    }

    @GetMapping("/client/{id}")
    public Client getClientById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/client/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client newClient) {
        return service.update(id, newClient);
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable Long id) {
        service.delete(id);
    }
}
