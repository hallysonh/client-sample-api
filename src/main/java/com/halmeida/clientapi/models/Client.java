package com.halmeida.clientapi.models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private LocalDate birthDate;
    private Boolean activated = true;
    @Column(updatable = false)
    @CreationTimestamp
    private Date createdAt;

    public Client() {
    }

    public Client(String firstName, String lastName, String email, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
    }

    public Client(String firstName, String lastName, String email, LocalDate birthDate, boolean activated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActivated() {
        return activated == null || activated;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Calc and returns the difference in years between the current date and the client's birthDate
     *
     * @return age of the client
     */
    public int getAge() {
        final LocalDate now = LocalDate.now();
        // if no birthDate is provided or birthDate is on future then returns 0
        if (this.birthDate == null || this.birthDate.compareTo(now) > 0) return 0;

        final Period diff = Period.between(this.birthDate, now);
        return diff.getYears();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", activated=" + activated +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return activated == client.activated && Objects.equals(id, client.id) && firstName.equals(client.firstName) && lastName.equals(client.lastName) && email.equals(client.email) && birthDate.equals(client.birthDate) && createdAt.equals(client.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, birthDate, activated, createdAt);
    }
}