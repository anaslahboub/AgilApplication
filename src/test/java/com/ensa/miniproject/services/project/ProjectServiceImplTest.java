package com.ensa.miniproject.services.project;

import com.ensa.miniproject.dto.EquipeDevelopementDTO;
import com.ensa.miniproject.dto.ProductOwnerDTO;
import com.ensa.miniproject.dto.ProjectDTO;
import com.ensa.miniproject.dto.ScrumMasterDTO;
import com.ensa.miniproject.entities.EquipeDevelopement;
import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.entities.ScrumMaster;
import com.ensa.miniproject.execptions.InvalidDateException;
import com.ensa.miniproject.mapping.EquipeDevelopementMapper;
import com.ensa.miniproject.mapping.ProductOwnerMapper;
import com.ensa.miniproject.mapping.ProjectMapper;
import com.ensa.miniproject.mapping.ScrumMasterMapper;
import com.ensa.miniproject.repository.ProjectRepository;
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
    @Mock
    private ScrumMasterMapper scrumMasterMapper;
    @Mock
    private ProductOwnerMapper productOwnerMapper;
    @Mock
    private EquipeDevelopementMapper equipeDevelopementMapper;

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

    // ------------------------- ADD -------------------------

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

    // ------------------------- UPDATE -------------------------

    @Test
    @DisplayName("Update Project - Success Full Update")
    void updateProject_Success() {
        // Arrange
        ProjectDTO updateDto = new ProjectDTO(1L, "New Name", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), null, null, null, null);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.fromEntity(project)).thenReturn(updateDto);

        // Act
        ProjectDTO result = projectService.updateProject(updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", project.getNom()); // Verify setter called
    }

    @Test
    @DisplayName("Update Project - Partial Update (Dates valid, other fields null)")
    void updateProject_PartialUpdate() {
        // Arrange
        // Note: Your service checks dates first, so they must be valid/non-null to reach the partial update logic for other fields
        ProjectDTO partialDto = new ProjectDTO(1L, "New Name Only", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), null, null, null, null);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.fromEntity(project)).thenReturn(partialDto);

        // Act
        projectService.updateProject(partialDto);

        // Assert
        assertEquals("New Name Only", project.getNom());
        // Verify other fields (like Owner) remained null/unchanged if they were null in DTO
        assertNull(project.getOwner());
    }

    @Test
    @DisplayName("Update Project - Not Found")
    void updateProject_NotFound() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> projectService.updateProject(validProjectDTO));
    }

    @Test
    @DisplayName("Update Project - Invalid dates - Should throw InvalidDateException")
    void updateProject_WithInvalidDates_ShouldThrowException() {
        // Act & Assert
        assertThrows(InvalidDateException.class, () ->
                projectService.updateProject(invalidDateProjectDTO));

        verify(projectRepository, never()).save(any());
    }

    // ------------------------- GETTERS -------------------------

    @Test
    @DisplayName("Get Project by ID - When exists")
    void getProjectById_WhenExists() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.fromEntity(any(Project.class))).thenReturn(validProjectDTO);

        // Act
        ProjectDTO result = projectService.getProjectById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    @DisplayName("Get Project by ID - Not Exists")
    void getProjectById_WhenNotExists() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> projectService.getProjectById(1L));
    }

    @Test
    @DisplayName("Get all Projects")
    void getProjects_ShouldReturnList() {
        // Arrange
        when(projectRepository.findAll()).thenReturn(List.of(project));
        when(projectMapper.fromEntity(any(Project.class))).thenReturn(validProjectDTO);

        // Act
        List<ProjectDTO> results = projectService.getProjects();

        // Assert
        assertEquals(1, results.size());
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Delete Project")
    void deleteProject_ShouldCallRepositoryDelete() {
        // Act
        projectService.deleteProject(1L);

        // Assert
        verify(projectRepository).deleteById(1L);
    }

    // ------------------------- ASSOCIATIONS (METIER) -------------------------

    @Test
    @DisplayName("Add ScrumMaster to Project")
    void addScrumMasterToProject_Success() {
        // Arrange
        ScrumMasterDTO smDto = new ScrumMasterDTO(10L, "SM", "Master", "pass", "mail");
        ScrumMaster smEntity = new ScrumMaster();

        // Note: your service uses getProjectById (custom method?) or standard proxy.
        // Assuming standard Spring Data behavior where we might simulate getById behavior.
        when(projectRepository.getProjectById(1L)).thenReturn(project);
        when(scrumMasterMapper.toEntity(smDto)).thenReturn(smEntity);

        // Act
        ScrumMasterDTO result = projectService.addScrumMasterToProject(1L, smDto);

        // Assert
        assertNotNull(result);
        assertEquals(smEntity, project.getScrumMaster());
    }

    @Test
    @DisplayName("Add ProductOwner to Project")
    void addProductOwnerToProject_Success() {
        // Arrange
        ProductOwnerDTO poDto = new ProductOwnerDTO(20L, "PO", "Owner", "pass", "mail");
        ProductOwner poEntity = new ProductOwner();

        when(projectRepository.getProjectById(1L)).thenReturn(project);
        when(productOwnerMapper.toEntity(poDto)).thenReturn(poEntity);

        // Act
        ProductOwnerDTO result = projectService.addProductOwnerToProject(1L, poDto);

        // Assert
        assertNotNull(result);
        assertEquals(poEntity, project.getOwner());
    }

    @Test
    @DisplayName("Add Equipe to Project")
    void addEquipeDevelopementToProject_Success() {
        // Arrange
        EquipeDevelopementDTO equipeDto = new EquipeDevelopementDTO(30L, null);
        EquipeDevelopement equipeEntity = new EquipeDevelopement();

        when(projectRepository.getProjectById(1L)).thenReturn(project);
        when(equipeDevelopementMapper.toEntity(equipeDto)).thenReturn(equipeEntity);

        // Act
        EquipeDevelopementDTO result = projectService.addEquipeDevelopementToProject(1L, equipeDto);

        // Assert
        assertNotNull(result);
        assertEquals(equipeEntity, project.getEquipeDevelopement());
    }
}