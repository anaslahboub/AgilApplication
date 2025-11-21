package com.ensa.miniproject.services.dev;

import com.ensa.miniproject.dto.DeveloperDto;
import com.ensa.miniproject.entities.Developer;
import com.ensa.miniproject.entities.EquipeDevelopement;
import com.ensa.miniproject.mapping.DeveloperMapper;
import com.ensa.miniproject.repository.DeveloperRepository;
import com.ensa.miniproject.repository.EquipeDevelopementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceImplTest {

    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private DeveloperMapper developerMapper;
    @Mock
    private EquipeDevelopementRepository equipeRepository;

    @InjectMocks
    private DeveloperServiceImpl developerServiceImpl;

    private Developer developer;
    private DeveloperDto developerDto;
    private EquipeDevelopement equipe;

    @BeforeEach
    public void setUp() {
        // Setup Developer Entity
        developer = new Developer();
        developer.setId(1L);
        developer.setEmail("email@test.com");
        developer.setPassword("password");
        developer.setSpeciality("JAVA");
        developer.setPrenom("John");
        developer.setUsername("john_doe");
        developer.setExperienceYears(5);

        // Setup Developer DTO
        developerDto = DeveloperDto.builder()
                .id(1L)
                .email("email@test.com")
                .password("password")
                .speciality("JAVA")
                .prenom("John")
                .username("john_doe")
                .experienceYears(5)
                .build();

        // Setup Equipe Entity
        equipe = new EquipeDevelopement();
        equipe.setId(100L);
        equipe.setDevelopers(new ArrayList<>());
    }

    // ------------------------- SAVE -------------------------

    @Test
    @DisplayName("Test add a new developer")
    void addDeveloperAndReturnDeveloperDto() {
        // Arrange
        when(developerMapper.toEntity(any(DeveloperDto.class))).thenReturn(developer);
        when(developerRepository.save(developer)).thenReturn(developer);
        when(developerMapper.fromEntity(any(Developer.class))).thenReturn(developerDto);

        // Act
        DeveloperDto result = developerServiceImpl.saveDeveloper(developerDto);

        // Assert
        assertNotNull(result);
        assertEquals(developerDto.getEmail(), result.getEmail());
        verify(developerRepository, times(1)).save(developer);
    }

    // ------------------------- GET ALL -------------------------

    @Test
    @DisplayName("Test get all developers")
    void getAllDevelopersTest() {
        // Arrange
        when(developerRepository.findAll()).thenReturn(List.of(developer));
        when(developerMapper.fromEntity(developer)).thenReturn(developerDto);

        // Act
        List<DeveloperDto> result = developerServiceImpl.getAllDevelopers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(developerDto.getId(), result.get(0).getId());
        verify(developerRepository, times(1)).findAll();
    }

    // ------------------------- GET BY ID -------------------------

    @Test
    @DisplayName("Test get developer by ID - Found")
    void getDeveloperByIdFoundTest() {
        // Arrange
        when(developerRepository.findById(1L)).thenReturn(Optional.of(developer));
        when(developerMapper.fromEntity(developer)).thenReturn(developerDto);

        // Act
        Optional<DeveloperDto> result = developerServiceImpl.getDeveloperById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(developerDto.getUsername(), result.get().getUsername());
    }

    @Test
    @DisplayName("Test get developer by ID - Not Found")
    void getDeveloperByIdNotFoundTest() {
        // Arrange
        when(developerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<DeveloperDto> result = developerServiceImpl.getDeveloperById(99L);

        // Assert
        assertTrue(result.isEmpty());
    }

    // ------------------------- UPDATE -------------------------

    @Test
    @DisplayName("Test update developer - Success")
    void updateDeveloperSuccessTest() {
        // Arrange
        DeveloperDto updateDto = DeveloperDto.builder()
                .username("updated_name")
                .prenom("updated_prenom")
                .email("updated@mail.com")
                .build();

        when(developerRepository.findById(1L)).thenReturn(Optional.of(developer));
        when(developerRepository.save(developer)).thenReturn(developer);
        when(developerMapper.fromEntity(developer)).thenReturn(updateDto); // Return the updated DTO simulation

        // Act
        DeveloperDto result = developerServiceImpl.updateDeveloper(1L, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("updated_name", result.getUsername());

        // Verify that setters were called on the entity
        assertEquals("updated_name", developer.getUsername());
        verify(developerRepository).save(developer);
    }

    @Test
    @DisplayName("Test update developer - Not Found Exception")
    void updateDeveloperNotFoundTest() {
        // Arrange
        when(developerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            developerServiceImpl.updateDeveloper(99L, developerDto);
        });

        assertEquals("Developer not found", exception.getMessage());
        verify(developerRepository, never()).save(any());
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Test delete developer")
    void deleteDeveloperTest() {
        // Act
        developerServiceImpl.deleteDeveloper(1L);

        // Assert
        verify(developerRepository, times(1)).deleteById(1L);
    }

    // ------------------------- FIND BY SPECIALITY -------------------------

    @Test
    @DisplayName("Test find developers by speciality")
    void findDevelopersBySpecialityTest() {
        // Arrange
        String speciality = "JAVA";
        when(developerRepository.findBySpecialityIgnoreCase(speciality)).thenReturn(List.of(developer));
        when(developerMapper.fromEntity(developer)).thenReturn(developerDto);

        // Act
        List<DeveloperDto> result = developerServiceImpl.findDevelopersBySpeciality(speciality);

        // Assert
        assertEquals(1, result.size());
        assertEquals("JAVA", result.get(0).getSpeciality());
    }

    // ------------------------- ASSIGN TO EQUIPE -------------------------

    @Test
    @DisplayName("Test assign developer to equipe - Success")
    void assignToEquipeSuccessTest() {
        // Arrange
        Long devId = 1L;
        Long equipeId = 100L;

        when(developerRepository.findById(devId)).thenReturn(Optional.of(developer));
        when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(equipe));
        when(developerRepository.save(developer)).thenReturn(developer);
        when(developerMapper.fromEntity(developer)).thenReturn(developerDto);

        // Act
        DeveloperDto result = developerServiceImpl.assignToEquipe(devId, equipeId);

        // Assert
        assertNotNull(result);
        // Verify relationship was established in memory
        assertEquals(equipe, developer.getEquipe());
        assertTrue(equipe.getDevelopers().contains(developer));

        verify(developerRepository).save(developer);
    }

    @Test
    @DisplayName("Test assign developer to equipe - Developer Not Found")
    void assignToEquipeDevNotFoundTest() {
        // Arrange
        when(developerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                developerServiceImpl.assignToEquipe(99L, 100L)
        );
        assertEquals("Developer not found", ex.getMessage());
    }

    @Test
    @DisplayName("Test assign developer to equipe - Equipe Not Found")
    void assignToEquipeEquipeNotFoundTest() {
        // Arrange
        when(developerRepository.findById(1L)).thenReturn(Optional.of(developer));
        when(equipeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                developerServiceImpl.assignToEquipe(1L, 999L)
        );
        assertEquals("Equipe not found", ex.getMessage());
    }

    // ------------------------- GET BY EQUIPE -------------------------

    @Test
    @DisplayName("Test get developers by equipe ID")
    void getDevelopersByEquipeTest() {
        // Arrange
        Long equipeId = 100L;
        when(developerRepository.findByEquipe_Id(equipeId)).thenReturn(List.of(developer));
        when(developerMapper.fromEntity(developer)).thenReturn(developerDto);

        // Act
        List<DeveloperDto> result = developerServiceImpl.getDevelopersByEquipe(equipeId);

        // Assert
        assertEquals(1, result.size());
        verify(developerRepository).findByEquipe_Id(equipeId);
    }
}