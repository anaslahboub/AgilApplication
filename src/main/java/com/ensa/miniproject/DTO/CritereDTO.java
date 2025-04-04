package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Etat;

public record CritereDTO(
        Long id ,
        String Scenario,
        String whenn,
        String given,
        String Thenn,
        String andd
) {

}
