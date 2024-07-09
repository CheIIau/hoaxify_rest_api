package com.hoaxify.hoaxify.hoax.controllers;

import com.hoaxify.hoaxify.common.security.PersonDetails;
import com.hoaxify.hoaxify.hoax.dto.HoaxDTO;
import com.hoaxify.hoaxify.hoax.models.Hoax;
import com.hoaxify.hoaxify.hoax.services.HoaxService;
import com.hoaxify.hoaxify.hoax.utils.HoaxConvert;
import com.hoaxify.hoaxify.hoax.utils.exceptions.HoaxNotCreated;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hoaxify.hoaxify.common.utils.Utils.fieldChecker;

@RestController
@RequestMapping("/api/hoaxes")
public class HoaxController {
    HoaxService hoaxService;

    public HoaxController(HoaxService hoaxService) {
        this.hoaxService = hoaxService;
    }

    @PostMapping("/create")
    void createHoax(@Valid @RequestBody Hoax hoax, BindingResult bindingResult, @AuthenticationPrincipal PersonDetails currentUser) {
        fieldChecker(bindingResult, HoaxNotCreated.class);
        hoaxService.save(hoax, currentUser);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<HoaxDTO>> getAllHoaxes(@PageableDefault(size = 10) Pageable pageable) {
        var hoaxesPage = hoaxService.getAllHoaxes(pageable);
        var dtoPage = this.getDtoPage(hoaxesPage);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Page<HoaxDTO>> getHoaxesOfUser(@PathVariable String username, @PageableDefault(size = 10) Pageable pageable) {
        var hoaxesPage = hoaxService.getHoaxesByPerson(username, pageable);
        var dtoPage = this.getDtoPage(hoaxesPage);

        return ResponseEntity.ok(dtoPage);
    }

    private PageImpl<HoaxDTO> getDtoPage(Page<Hoax> hoaxPage) {
        List<HoaxDTO> hoaxDTOS = hoaxPage.stream()
                .map(HoaxConvert::convertHoaxToHoaxDTO)
                .toList();
        return new PageImpl<>(hoaxDTOS, hoaxPage.getPageable(),
                hoaxPage.getTotalElements());
    }
}
