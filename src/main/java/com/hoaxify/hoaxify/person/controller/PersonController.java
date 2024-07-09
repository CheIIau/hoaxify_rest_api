package com.hoaxify.hoaxify.person.controller;

import com.hoaxify.hoaxify.common.security.PersonDetails;
import com.hoaxify.hoaxify.common.utils.responses.CommonSuccessResponse;
import com.hoaxify.hoaxify.person.dto.PersonDTOForGetUsers;
import com.hoaxify.hoaxify.person.dto.PersonDTOForUpdate;
import com.hoaxify.hoaxify.person.models.Person;
import com.hoaxify.hoaxify.person.services.PeopleService;
import com.hoaxify.hoaxify.person.utils.exceptions.PersonNotUpdated;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.hoaxify.hoaxify.person.utils.PersonConvert;

import java.io.IOException;
import java.util.List;

import static com.hoaxify.hoaxify.common.utils.Utils.fieldChecker;

@RestController
@RequestMapping("/api/users")
public class PersonController {

    private final PeopleService peopleService;

    @Autowired
    public PersonController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    //test
    @PostMapping("/create")
    public ResponseEntity<CommonSuccessResponse> createPerson(@RequestBody @Valid Person person,
                                                              BindingResult bindingResult) {
//        fieldChecker(bindingResult, PersonNotCreatedException.class);

        var response = new CommonSuccessResponse("User has been created");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<PersonDTOForGetUsers> getUserByName(@PathVariable String username) {
        var person =
                PersonConvert.convertPersonToPersonDTOForUserGet(peopleService.getByUsername(username));
        return ResponseEntity.ok(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTOForGetUsers> getUserById(@PathVariable int id) {
        var person = PersonConvert.convertPersonToPersonDTOForUserGet(peopleService.getById(id));
        return ResponseEntity.ok(person);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<CommonSuccessResponse> updateUser(@PathVariable int id,
                                                            @Valid @RequestBody PersonDTOForUpdate person,
                                                            BindingResult bindingResult) throws IOException {

        fieldChecker(bindingResult, PersonNotUpdated.class);
        peopleService.update(id, person);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // все юзеры, включая текущего
    @GetMapping("/all")
    public ResponseEntity<Page<PersonDTOForGetUsers>> getUsers(@PageableDefault(size = 10) Pageable pageable) {
        var personsPage = peopleService.getUsers(pageable);

        var dtoPage = this.getDtoPage(personsPage);
        return ResponseEntity.ok(dtoPage);
    }
    // исключая текущего
    @GetMapping("/users")
    public ResponseEntity<Page<PersonDTOForGetUsers>> getUsersWithoutCurrentOne(@AuthenticationPrincipal PersonDetails currentUser,
                                                                                @PageableDefault(size = 10) Pageable pageable) {
        var personsPage = peopleService.getPeopleWithoutCurrentOne(currentUser.person(), pageable);

        var dtoPage = this.getDtoPage(personsPage);
        return ResponseEntity.ok(dtoPage);
    }



    private PageImpl<PersonDTOForGetUsers> getDtoPage(Page<Person> personPage) {
        List<PersonDTOForGetUsers> personDTOs = personPage.stream()
                .map(PersonConvert::convertPersonToPersonDTOForUserGet)
                .toList();
        return new PageImpl<>(personDTOs, personPage.getPageable(),
                personPage.getTotalElements());
    }

}
