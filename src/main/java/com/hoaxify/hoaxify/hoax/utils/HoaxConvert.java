package com.hoaxify.hoaxify.hoax.utils;

import com.hoaxify.hoaxify.hoax.dto.HoaxDTO;
import com.hoaxify.hoaxify.hoax.models.Hoax;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HoaxConvert {
    private static ModelMapper modelMapper;

    @Autowired
    public HoaxConvert(ModelMapper modelMapper) {
        HoaxConvert.modelMapper = modelMapper;
    }

    public static HoaxDTO convertHoaxToHoaxDTO(Hoax hoax) {
        return modelMapper.map(hoax, HoaxDTO.class);
    }
}
