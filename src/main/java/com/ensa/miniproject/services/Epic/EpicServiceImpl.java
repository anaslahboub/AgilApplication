package com.ensa.miniproject.services.Epic;

import com.ensa.miniproject.DTO.EpicDTO;
import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.mapping.EpicMapper;
import com.ensa.miniproject.repository.EpicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EpicServiceImpl implements EpicService{
    private final EpicRepository epicRepository;
    private final EpicMapper epicMapper;

    @Override
    public EpicDTO createEpic(EpicDTO epicDTO) {
        Epic epic = EpicMapper.INSTANCE.toEntity(epicDTO);
        epic = epicRepository.save(epic);
        return EpicMapper.INSTANCE.fromEntity(epic);
    }

    @Override
    public EpicDTO getEpicById(Long id) {
        return EpicMapper.INSTANCE.fromEntity(epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id)));
    }

    @Override
    public EpicDTO updateEpic(EpicDTO epicDTO) {
        Epic existingEpic = epicRepository.findById(epicDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + epicDTO.getId()));

        // Update fields
        existingEpic.setTitle(epicDTO.getTitle());
        existingEpic.setDescription(epicDTO.getDescription());
        existingEpic.setProductBacklog(epicDTO.getProductBacklog());

        // Save the updated entity
        existingEpic = epicRepository.save(existingEpic);
        return EpicMapper.INSTANCE.fromEntity(existingEpic);
    }

    @Override
    public void deleteEpic(Long id) {
        Epic epic = epicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic not found with id: " + id));
        epicRepository.delete(epic);
    }

    @Override
    public List<EpicDTO> getEpics() {
        return epicRepository.findAll()
                .stream() // Convert the list to a stream
                .map(epicMapper::fromEntity) // Use the injected mapper to map entities to DTOs
                .collect(Collectors.toList()); // Collect the results into a list
    }
}
