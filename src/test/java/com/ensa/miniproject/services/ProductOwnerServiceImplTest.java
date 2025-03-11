package com.ensa.miniproject.services;

import com.ensa.miniproject.DTO.ProjectCreateDTO;
import com.ensa.miniproject.DTO.ProjectRespDTO;
import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.execptions.InvalidDateException;
import com.ensa.miniproject.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductOwnerServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProductOwnerServiceImpl productOwnerService;

    private ProjectCreateDTO project1;
    private ProjectRespDTO project2;
    private Project projectEntity;

    @BeforeEach
    void setUp() {
        project1 = new ProjectCreateDTO("p1", LocalDate.of(2024, 2, 1), LocalDate.of(2024, 5, 1));
        project2 = new ProjectRespDTO(2L, "p2", LocalDate.of(2024, 3, 1), LocalDate.of(2024, 1, 1));
        projectEntity = project1.toEntity();
        projectEntity.setId(1L);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(projectEntity);

        ProjectRespDTO savedProject = productOwnerService.addProject(project1);

        assertNotNull(savedProject);
        assertEquals(project1.nom(), savedProject.nom());
        assertEquals(project1.dateDebut(), savedProject.dateDebut());
        assertEquals(project1.dateFin(), savedProject.dateFin());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void addProject_ShouldThrowException_WhenDateDebutIsAfterDateFin() {
        ProjectCreateDTO invalidProject = new ProjectCreateDTO("invalid", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 5, 1));

        Exception exception = assertThrows(InvalidDateException.class, () -> {
            productOwnerService.addProject(invalidProject);
        });

        assertEquals("la date debut doit etre inf a date fin ", exception.getMessage());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void updateProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectEntity));
        when(projectRepository.save(any(Project.class))).thenReturn(projectEntity);

        ProjectRespDTO updatedProject = productOwnerService.updateProject(ProjectRespDTO.toProjectRespDTO(projectEntity));

        assertNotNull(updatedProject);
        assertEquals(projectEntity.getNom(), updatedProject.nom());
        assertEquals(projectEntity.getDateDebut(), updatedProject.dateDebut());
        assertEquals(projectEntity.getDateFin(), updatedProject.dateFin());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void getProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectEntity));

        ProjectRespDTO result = productOwnerService.getProjectById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void getProjects() {
        List<Project> projects = Arrays.asList(projectEntity);
        when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectRespDTO> result = productOwnerService.getProjects();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(projectEntity.getNom(), result.get(0).nom());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void deleteProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(projectEntity));
        doNothing().when(projectRepository).delete(projectEntity);

        productOwnerService.deleteProject(1L);

        verify(projectRepository, times(1)).delete(projectEntity);
    }
    @Test
    void addProductOwner() {

    }

    @Test
    void updateProductOwner() {
    }

    @Test
    void getProductOwnerById() {
    }

    @Test
    void getProductOwners() {
    }

    @Test
    void addProductBacklog() {
    }

    @Test
    void updateProductBacklog() {
    }

    @Test
    void getProductBacklog() {
    }

    @Test
    void getProductBacklogs() {
    }

    @Test
    void deleteProductBacklog() {
    }

    @Test
    void affectBacklogToProject() {
    }

    @Test
    void affectProjrctToBacklog() {
    }
}