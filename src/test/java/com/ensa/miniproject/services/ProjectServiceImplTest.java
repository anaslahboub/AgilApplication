package com.ensa.miniproject.services;

import com.ensa.miniproject.dto.ProjectDTO;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.execptions.InvalidDateException;
import com.ensa.miniproject.mapping.ProjectMapper;
import com.ensa.miniproject.repository.ProjectRepository;
import com.ensa.miniproject.services.project.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private ProjectDTO validProjectDTO;
    private ProjectDTO invalidDateProjectDTO;

    @BeforeEach
    void setUp() {
        // Setup valid project
        project = new Project();
        project.setId(1L);
        project.setNom("Test Project");
        project.setDateDebut(LocalDate.of(2023, 1, 1));
        project.setDateFin(LocalDate.of(2023, 12, 31));

        // Valid DTO
        validProjectDTO = new ProjectDTO(
                1L,
                "Test Project",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31),
                null, null, null, null
        );

        // Invalid date DTO (start after end)
        invalidDateProjectDTO = new ProjectDTO(
                1L,
                "Invalid Date Project",
                LocalDate.of(2023, 12, 31),
                LocalDate.of(2023, 1, 1),
                null, null, null, null
        );
    }

    @Test
    @DisplayName("Add Project - Valid dates - Should return saved DTO")
    void addProject_WithValidDates_ShouldReturnSavedProjectDTO() {
        // Arrange
        when(projectMapper.toEntity(any(ProjectDTO.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.fromEntity(any(Project.class))).thenReturn(validProjectDTO);

        // Act
        ProjectDTO result = projectService.addProject(validProjectDTO);

        // Assert
        assertNotNull(result);
        assertEquals(validProjectDTO.id(), result.id());
        assertEquals(validProjectDTO.nom(), result.nom());
        verify(projectRepository).save(project);
    }

    @Test
    @DisplayName("Add Project - Invalid dates - Should throw InvalidDateException")
    void addProject_WithInvalidDates_ShouldThrowException() {
        // Act & Assert
        assertThrows(InvalidDateException.class, () ->
                projectService.addProject(invalidDateProjectDTO));

        verify(projectRepository, never()).save(any());
    }

    @Test
    @DisplayName("Update Project - Valid dates - Should return updated DTO")
    void updateProject_WithValidDates_ShouldReturnUpdatedProjectDTO() {
        // Arrange
        ProjectDTO updatedDTO = new ProjectDTO(
                1L,
                "Updated Project",
                LocalDate.of(2023, 6, 1),
                LocalDate.of(2023, 12, 31),
                null, null, null, null
        );

        when(projectMapper.toEntity(any(ProjectDTO.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectMapper.fromEntity(any(Project.class))).thenReturn(updatedDTO);

        // Act
        ProjectDTO result = projectService.updateProject(updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Project", result.nom());
        verify(projectRepository).save(project);
    }

    @Test
    @DisplayName("Update Project - Invalid dates - Should throw InvalidDateException")
    void updateProject_WithInvalidDates_ShouldThrowException() {
        // Act & Assert
        assertThrows(InvalidDateException.class, () ->
                projectService.updateProject(invalidDateProjectDTO));

        verify(projectRepository, never()).save(any());
    }

    @Test
    @DisplayName("Get Project by ID - When exists - Should return DTO")
    void getProjectById_WhenExists_ShouldReturnProjectDTO() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.fromEntity(any(Project.class))).thenReturn(validProjectDTO);

        // Act
        ProjectDTO result = projectService.getProjectById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Project", result.nom());
        verify(projectRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Project by ID - When not exists - Should throw EntityNotFoundException")
    void getProjectById_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                projectService.getProjectById(1L));
        verify(projectRepository).findById(1L);
    }

    @Test
    @DisplayName("Get all Projects - Should return list of DTOs")
    void getProjects_ShouldReturnListOfProjectDTOs() {
        // Arrange
        when(projectRepository.findAll()).thenReturn(List.of(project));
        when(projectMapper.fromEntity(any(Project.class))).thenReturn(validProjectDTO);

        // Act
        List<ProjectDTO> results = projectService.getProjects();

        // Assert
        assertEquals(1, results.size());
        assertEquals(1L, results.get(0).id());
        verify(projectRepository).findAll();
    }

    @Test
    @DisplayName("Delete Project - Should call repository delete")
    void deleteProject_ShouldCallRepositoryDelete() {
        // Act
        projectService.deleteProject(1L);

        // Assert
        verify(projectRepository).deleteById(1L);
    }

}