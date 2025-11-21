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
import static com.ensa.miniproject.execptions.ErrorMessages.*;

@Service
@AllArgsConstructor
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;
    private final SprintBacklogRepository sprintBacklogRepository;
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

            // CORRECTION ICI : On vérifie d'abord si getSprintBacklog() n'est pas null
            if (sprintDto.getSprintBacklog() != null && sprintDto.getSprintBacklog().getId() != null) {
                SprintBacklog backlog = sprintBacklogRepository.findById(sprintDto.getSprintBacklog().getId())
                        .orElse(null);
                sprint.setSprintBacklog(backlog);
            } else {
                // Si le backlog est null OU si l'ID est null, on détache le backlog
                sprint.setSprintBacklog(null);
            }

            return sprintMapper.mapToDto(sprintRepository.save(sprint));
        } else {
            throw new EntityNotFoundException(SPRINT_NOT_FOUND + id);
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
                .orElseThrow(() -> new EntityNotFoundException(SPRINT_NOT_FOUND +sprintId ));
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
