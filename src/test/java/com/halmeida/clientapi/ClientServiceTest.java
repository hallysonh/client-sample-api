package com.halmeida.clientapi;

import com.halmeida.clientapi.models.Client;
import com.halmeida.clientapi.repositories.ClientRepository;
import com.halmeida.clientapi.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.halmeida.clientapi.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClientServiceTest {
    private final TestEntityManager entityManager;
    private final ClientService clientService;

    @Autowired
    public ClientServiceTest(TestEntityManager entityManager, ClientRepository repository) {
        this.entityManager = entityManager;
        this.clientService = new ClientService(repository);
    }

    @BeforeEach()
    public void beforeEach() {
        entityManager.clear();
    }

    @Test
    void testSave() {
        // Save Client
        final Client client = new Client(FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE, ACTIVATED);
        final Client newClient = clientService.save(client);

        // Check if it was saved correctly
        assertThat(newClient.getId()).isNotNull();
        assertThat(newClient.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(newClient.getLastName()).isEqualTo(LAST_NAME);
        assertThat(newClient.getEmail()).isEqualTo(EMAIL);
        assertThat(newClient.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(newClient.isActivated()).isEqualTo(ACTIVATED);
    }

    @Test
    void testGetById() {
        // Save Client
        final Client client = new Client(FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE, ACTIVATED);
        final Client newClient = entityManager.persistAndFlush(client);

        // Retrieve saved client
        final Client retrievedClient = clientService.getById(newClient.getId());

        // Check if all provided fields was saved correctly
        assertThat(retrievedClient).isNotNull();
        assertThat(retrievedClient.getId()).isEqualTo(newClient.getId());
        assertThat(retrievedClient.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(retrievedClient.getLastName()).isEqualTo(LAST_NAME);
        assertThat(retrievedClient.getEmail()).isEqualTo(EMAIL);
        assertThat(retrievedClient.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(retrievedClient.isActivated()).isEqualTo(ACTIVATED);
        assertThat(retrievedClient.getCreatedAt()).isNotNull();
        assertThat(retrievedClient.getAge()).isNotNull();
    }

    @Test
    void testUpdate() {
        // Save Client
        final Client client = new Client(FIRST_NAME, LAST_NAME, EMAIL, BIRTH_DATE, ACTIVATED);
        final Client newClient = entityManager.persist(client);

        // Check first name field value
        assertThat(newClient.getFirstName()).isEqualTo(FIRST_NAME);

        // Create update client and changes just the firstName
        final String newFirstName = FIRST_NAME + NOISE;
        final Client updateClient = new Client();
        updateClient.setFirstName(newFirstName);

        final Client updatedClient = clientService.update(newClient.getId(), updateClient);

        // Check if updated client was returned
        assertThat(updatedClient).isNotNull();
        assertThat(updatedClient.getId()).isEqualTo(newClient.getId());

        // Check if firstName changed
        assertThat(updatedClient.getFirstName()).isEqualTo(newFirstName);

        // Check if the others fields did not change
        assertThat(updatedClient.getLastName()).isEqualTo(LAST_NAME);
        assertThat(updatedClient.getEmail()).isEqualTo(EMAIL);
        assertThat(updatedClient.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(updatedClient.isActivated()).isEqualTo(ACTIVATED);
    }

    @Test
    void testGetList() {
        final int LIST_SIZE = 3;
        // Save 3 Clients
        for (int i = 0; i < LIST_SIZE; i++) {
            entityManager.persist(new Client(FIRST_NAME + i, LAST_NAME, EMAIL, BIRTH_DATE, i % 2 == 0));
        }

        // Test list with no filter
        final Page<Client> page1 = clientService.getList(
                null, null, null, null, null);
        assertThat(page1.getContent().size()).isEqualTo(LIST_SIZE);
        assertThat(page1.getContent().get(0).getFirstName()).isEqualTo(FIRST_NAME + 0);

        // Test list with query filter
        final Page<Client> page2 = clientService.getList(
                FIRST_NAME + 1, null, null, null, null);
        assertThat(page2.getContent().size()).isEqualTo(1);
        assertThat(page2.getContent().get(0).getFirstName()).isEqualTo(FIRST_NAME + 1);

        // Test list with page limit
        final int PAGE_MAX = LIST_SIZE - 1;
        final Page<Client> page3 = clientService.getList(
                null, null, null, null, PageRequest.of(0, PAGE_MAX));
        assertThat(page3.getContent().size()).isEqualTo(PAGE_MAX);

        final Page<Client> page4 = clientService.getList(
                null, null, null, null, PageRequest.of(1, PAGE_MAX));
        assertThat(page4.getContent().size()).isEqualTo(1);

        // Test list with Sort
        final Pageable pageable = PageRequest.of(0, 10)
                .withSort(Sort.Direction.DESC, "firstName");
        final Page<Client> page5 = clientService.getList(
                null, null, null, null, pageable);
        assertThat(page5.getContent().get(0).getFirstName()).isEqualTo(FIRST_NAME + (LIST_SIZE - 1));
    }
}
