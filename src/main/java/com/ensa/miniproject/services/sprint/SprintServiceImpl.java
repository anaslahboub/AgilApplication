package com.ensa.miniproject.services.sprint;

import com.ensa.miniproject.dto.SprintDto;
import com.ensa.miniproject.entities.Sprint;
import com.ensa.miniproject.entities.SprintBacklog;
import com.ensa.miniproject.mapping.SprintMapper;
import com.ensa.miniproject.repository.SprintBacklogRepository;
import com.ensa.miniproject.repository.SprintRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;
    private final SprintBacklogRepository sprintBacklogRepository;
    private static final String SPRING_NOT_FOUND = "Sprint not found with ID: ";

    private final SprintMapper sprintMapper;
    @Override
    public SprintDto saveSprint(SprintDto sprintDto) {
        Sprint sprint = sprintMapper.mapToEntity(sprintDto);
        Sprint saved = sprintRepository.save(sprint);
        return sprintMapper.mapToDto(saved);
    }

    @Override
    public List<SprintDto> getAllSprints() {
        return sprintRepository.findAll().stream()
                .map(sprintMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<SprintDto> getSprintById(int id) {
        return sprintRepository.findById(id).map(sprintMapper::mapToDto);
    }

    @Override
    public SprintDto updateSprint(int id, SprintDto sprintDto) {
        Optional<Sprint> existingSprintOpt = sprintRepository.findById(id);
        if (existingSprintOpt.isPresent()) {
            Sprint sprint = existingSprintOpt.get();
            sprint.setName(sprintDto.getName());
            sprint.setDays(sprintDto.getDays());

            if (sprintDto.getSprintBacklog().getId() != null) {
                SprintBacklog backlog = sprintBacklogRepository.findById(sprintDto.getSprintBacklog().getId())
                        .orElse(null);
                sprint.setSprintBacklog(backlog);
            } else {
                sprint.setSprintBacklog(null);
            }

            return sprintMapper.mapToDto(sprintRepository.save(sprint));
        } else {
            throw new RuntimeException(SPRING_NOT_FOUND + id);
        }
    }

    @Override
    public void deleteSprint(int id) {
        sprintRepository.deleteById(id);
    }

    // Business logic
    @Override
    public List<SprintDto> findSprintsByDuration(Long days) {
        return sprintRepository.findByDays(days).stream()
                .map(sprintMapper::mapToDto)
                .toList();
    }

    @Override
    public SprintDto assignSprintBacklog(int sprintId, Long backlogId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new EntityNotFoundException(SPRING_NOT_FOUND +sprintId ));
        SprintBacklog backlog = sprintBacklogRepository.findById(backlogId)
                .orElseThrow(() -> new EntityNotFoundException("sprintbacklog not found"));

        sprint.setSprintBacklog(backlog);
        return sprintMapper.mapToDto(sprintRepository.save(sprint));
    }

    @Override
    public List<SprintDto> getSprintsWithoutBacklog() {
        return sprintRepository.findBySprintBacklogIsNull().stream()
                .map(sprintMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean isSprintActive(int sprintId) {
        return sprintRepository.findById(sprintId)
                .map(s -> s.getDays() != null && s.getDays() > 0) // exemple basique d’un critère d’activité
                .orElse(false);
    }
}
