package com.halmeida.clientapi.repositories;

import com.halmeida.clientapi.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDate;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select x from Client x where " +
            "(?1 is null or x.firstName like ?1 or x.lastName like ?1) and " +
            "(?2 is null or x.birthDate >= ?2) and " +
            "(?3 is null or x.birthDate <= ?2) and " +
            "(?4 is null or x.activated = ?4)")
    Page<Client> findByQueryAndBirthDatePeriodAndActivated(
            String query,
            LocalDate birthDateStart,
            LocalDate birthDateEnd,
            Boolean activated,
            @PageableDefault(sort = "id") Pageable pageable
    );
}
