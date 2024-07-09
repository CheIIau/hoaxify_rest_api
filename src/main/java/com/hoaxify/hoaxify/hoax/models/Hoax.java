package com.hoaxify.hoaxify.hoax.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.hoaxify.person.models.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "hoax")
public class Hoax {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Содержание не должно быть пустым")
    @Size(min = 10, max=5000, message = "Содержание должно быть от 10 до 5000 символов длиной")
    @Column(name = "content", length = 5000, nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", referencedColumnName = "username", nullable = false)
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
