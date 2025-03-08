package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Developer;
import com.ensa.miniproject.entities.EquipeDevelopement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EquipeDevelopementDTO {
    private Long id;
    private List<Developer> developers;

    public EquipeDevelopement toEntity() {
        EquipeDevelopement e = new EquipeDevelopement();
        e.setId(id);
        e.setDevelopers(developers);
        return e;
    }

    public static EquipeDevelopementDTO fromEntity(EquipeDevelopement e) {
        EquipeDevelopementDTO dto = new EquipeDevelopementDTO();
        dto.setId(e.getId());
        dto.setDevelopers(e.getDevelopers());
        return dto;
    }
}
