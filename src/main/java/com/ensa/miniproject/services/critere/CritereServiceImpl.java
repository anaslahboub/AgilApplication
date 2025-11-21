package com.ensa.miniproject.services.critere;

import com.ensa.miniproject.dto.CritereDTO;
import com.ensa.miniproject.entities.Critere;
import com.ensa.miniproject.mapping.CritereMapper;
import com.ensa.miniproject.repository.CritereRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CritereServiceImpl implements CritereService {

    private final CritereRepository critereRepository;
    private final CritereMapper critereMapper;
    private static final String CRITERE_NOT_FOUND = "Critere not found with id: ";

    @Override
    public CritereDTO createCritere(CritereDTO critereDTO) {
        Critere critere = critereMapper.toEntity(critereDTO);
        critere = critereRepository.save(critere);
        return critereMapper.fromEntity(critere);
    }

    @Override
    public CritereDTO getCritereById(Long id) {
        Critere critere = critereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CRITERE_NOT_FOUND + id));
        return critereMapper.fromEntity(critere);
    }

    @Override
    public CritereDTO updateCritere(CritereDTO critereDTO) {
        Critere existingCritere = critereRepository.findById(critereDTO.id())
                .orElseThrow(() -> new EntityNotFoundException(CRITERE_NOT_FOUND + critereDTO.id()));

        // Update fields
        existingCritere.setScenario(critereDTO.Scenario());
        existingCritere.setWhenn(critereDTO.whenn());
        existingCritere.setGiven(critereDTO.given());
        existingCritere.setThenn(critereDTO.Thenn());
        existingCritere.setAndd(critereDTO.andd());

        // Save the updated entity
        existingCritere = critereRepository.save(existingCritere);
        return critereMapper.fromEntity(existingCritere);
    }

    @Override
    public void deleteCritere(Long id) {
        Critere critere = critereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CRITERE_NOT_FOUND + id));
        critereRepository.delete(critere);
    }

    @Override
    public List<CritereDTO> getAllCriteres() {
        return critereRepository.findAll()
                .stream()
                .map(critereMapper::fromEntity)
                .toList();
    }
}