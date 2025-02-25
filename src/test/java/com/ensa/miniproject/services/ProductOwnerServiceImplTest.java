package com.ensa.miniproject.services;

import com.ensa.miniproject.entities.Project;
import com.ensa.miniproject.execptions.InvalidDateException;
import com.ensa.miniproject.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductOwnerServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;


    @InjectMocks
    private ProductOwnerServiceImpl productOwnerService;

    private Project project1;
    private Project project2;


    @BeforeEach
    void setUp() {
        project1 = new Project();
        project1.setId(1L);
        project1.setDateDebut(LocalDate.of(2024, 2, 1));
        project1.setDateFin(LocalDate.of(2024, 5, 1));

        project2 = new Project();
        project2.setId(2L);
        project2.setDateDebut(LocalDate.of(2024, 3, 1));
        project2.setDateFin(LocalDate.of(2024, 6, 1));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProject() {
//        assertEquals(project1,productOwnerService.addProject(project1),()->"erreur dans insrting ");
        when(projectRepository.save(project1)).thenReturn(project1);

        Project savedProject = productOwnerService.addProject(project1);

        assertEquals(project1, savedProject);
    }

    @Test
    void addProject_ShouldThrowException_WhenDateDebutIsAfterDateFin() {
        project1.setDateDebut(LocalDate.of(2024, 6, 1)); // Date début après la date fin

        Exception exception = assertThrows(InvalidDateException.class, () -> {
            productOwnerService.addProject(project1);
        });

        assertEquals("la date debut doit etre inf a date fin ", exception.getMessage());
        verify(projectRepository, never()).save(project1);
    }

    @Test
    void updateProject() {
//        assertEquals(project2,productOwnerService.updateProject(project1),()->"erreur dans updating ");
        when(projectRepository.save(project1)).thenReturn(project1);

        Project updatedProject = productOwnerService.updateProject(project1);

        assertEquals(project1, updatedProject);
        verify(projectRepository, times(1)).save(project1);
    }

    @Test
    void getProject() {
//        assertEquals(project1,productOwnerService.getProjectById(1L),()->"erreur dans getting ");

    }

    @Test
    void getProjects() {

    }

    @Test
    void deleteProject() {
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