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

}
