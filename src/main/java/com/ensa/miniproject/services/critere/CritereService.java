package com.ensa.miniproject.services.critere;

import com.ensa.miniproject.DTO.CritereDTO;

import java.util.List;

public interface CritereService {
    CritereDTO createCritere(CritereDTO critereDTO);
    CritereDTO getCritereById(Long id);
    CritereDTO updateCritere(CritereDTO critereDTO);
    void deleteCritere(Long id);
    List<CritereDTO> getAllCriteres();
}