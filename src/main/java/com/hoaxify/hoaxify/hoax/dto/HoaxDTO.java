package com.hoaxify.hoaxify.hoax.dto;

import com.hoaxify.hoaxify.hoax.models.Hoax;
import com.hoaxify.hoaxify.person.dto.PersonDTOForGetUsers;
import com.hoaxify.hoaxify.person.utils.PersonConvert;

import java.time.ZoneId;
import java.util.Objects;

public class HoaxDTO {
    private int id;

    private String content;

    private long date;

    private PersonDTOForGetUsers person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public PersonDTOForGetUsers getPerson() {
        return person;
    }

    public void setPerson(PersonDTOForGetUsers person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoaxDTO hoaxDto = (HoaxDTO) o;
        return id == hoaxDto.id && date == hoaxDto.date && Objects.equals(content, hoaxDto.content) && Objects.equals(person, hoaxDto.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, date, person);
    }

    public HoaxDTO() {
    }

    public HoaxDTO(Hoax hoax) {
        this.setId(hoax.getId());
        this.setContent(hoax.getContent());
        this.setDate(hoax.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        this.setPerson(PersonConvert.convertPersonToPersonDTOForUserGet(hoax.getPerson()));
    }
}
