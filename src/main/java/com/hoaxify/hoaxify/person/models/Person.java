package com.hoaxify.hoaxify.person.models;

import com.hoaxify.hoaxify.hoax.models.Hoax;
import com.hoaxify.hoaxify.person.constraints.UniqueUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "username")
    @UniqueUsername
    private String username;

    @NotEmpty(message = "Отображаемое имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Отображаемое имя должно быть от 2 до 100 символов длиной")
    @Column(name = "display_name")
    private String displayName;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 5, max = 100, message = "Пароль должен быть от 5 до 100 символов длиной")
    @Column(name = "password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "В пароле должны быть хотя" +
            " бы 1 заглавная буква, строчная и одна цифра ")
    private String password;

    private String avatar;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hoax> hoaxes;

    public List<Hoax> getHoaxes() {
        return hoaxes;
    }

    public void setHoaxes(List<Hoax> hoaxes) {
        this.hoaxes = hoaxes;
    }

    public Person() {
    }

    public Person(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(username, person.username) && Objects.equals(displayName, person.displayName) && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, displayName);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
