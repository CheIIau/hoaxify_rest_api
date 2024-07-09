package com.hoaxify.hoaxify.auth.models;

import com.hoaxify.hoaxify.person.models.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Токен не должен быть пустым")
    @NotNull
    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotEmpty(message = "Токен не должен быть пустым") @NotNull String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(@NotEmpty(message = "Токен не должен быть пустым") @NotNull String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public RefreshToken() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return id == that.id && Objects.equals(refreshToken, that.refreshToken) && Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, refreshToken, person);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", refreshToken='" + refreshToken + '\'' +
                ", person=" + person +
                '}';
    }
}
