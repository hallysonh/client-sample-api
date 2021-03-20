package com.halmeida.clientapi.service;

import com.halmeida.clientapi.exceptions.ClientNotFoundException;
import com.halmeida.clientapi.models.Client;
import com.halmeida.clientapi.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client save(Client client) {
        return repository.save(client);
    }

    public Client getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Client update(Long id, Client newClient) {
        return repository.findById(id).map(client -> {
            client.setFirstName(nvl(newClient.getFirstName(), client.getFirstName()));
            client.setLastName(nvl(newClient.getLastName(), client.getLastName()));
            client.setEmail(nvl(newClient.getEmail(), client.getEmail()));
            client.setBirthDate(nvl(newClient.getBirthDate(), client.getBirthDate()));
            client.setActivated(nvl(newClient.getActivated(), client.isActivated()));
            return repository.save(client);
        }).orElseThrow(() -> new ClientNotFoundException(id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Client> getList(
            String query,
            LocalDate birthDateStart,
            LocalDate birthDateEnd,
            Boolean activated,
            Pageable pageable
    ) {
        final String sqlQuery = query != null && !query.isBlank() ? "%" + query + "%" : null;
        return repository.findByQueryAndBirthDatePeriodAndActivated(
                sqlQuery, birthDateStart, birthDateEnd, activated, pageable);
    }

    private <T> T nvl(T a, T b) {
        return a == null ? b : a;
    }
}
