package io.biker.management.biker.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.biker.dto.BikerCreationDTO;
import io.biker.management.biker.dto.BikerReadingDTO;
import io.biker.management.biker.entity.Biker;

@Component
public class BikerMapper {
    // To Dto
    public BikerReadingDTO toDto(Biker biker) {
        BikerReadingDTO dto = new BikerReadingDTO(biker.getId(), biker.getName(), biker.getEmail(),
                biker.getPhoneNumber(), biker.getCurrentLocation());
        return dto;
    }

    public List<BikerReadingDTO> toDtos(List<Biker> bikers) {
        List<BikerReadingDTO> dtos = new ArrayList<>();
        for (Biker biker : bikers) {
            dtos.add(toDto(biker));
        }

        return dtos;
    }

    // To Entity
    public Biker toBiker(BikerCreationDTO dto) {
        Biker biker = new Biker();
        biker.setName(dto.name());
        biker.setEmail(dto.email());
        biker.setPassword(dto.password());
        biker.setPhoneNumber(dto.phoneNum());

        return biker;
    }
}
