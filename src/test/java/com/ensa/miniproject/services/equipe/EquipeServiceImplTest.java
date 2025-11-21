package com.ensa.miniproject.services.equipe;
import com.ensa.miniproject.dto.EquipeDevelopementDTO;
import com.ensa.miniproject.entities.Developer;
import com.ensa.miniproject.entities.EquipeDevelopement;
import com.ensa.miniproject.execptions.ResourceNotFoundException;
import com.ensa.miniproject.mapping.EquipeDevelopementMapper;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipeServiceImplTest {

    @Mock
    private EquipeDevelopementRepository equipeRepository;
    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private EquipeDevelopementMapper equipeDevelopementMapper;

    @InjectMocks
    private EquipeDevelopementServiceImpl equipeService;

    private EquipeDevelopement equipe;
    private EquipeDevelopementDTO equipeDto;
    private Developer developer;

    @BeforeEach
    void setUp() {
        // Setup Developer
        developer = new Developer();
        developer.setId(1L);
        developer.setUsername("dev_one");

        // Setup Equipe Entity
        // IMPORTANT: Initialize the list to avoid NullPointerException when adding developers
        equipe = new EquipeDevelopement();
        equipe.setId(10L);
        equipe.setDevelopers(new ArrayList<>());

        // Setup Equipe DTO (using Record constructor)
        equipeDto = new EquipeDevelopementDTO(10L, new ArrayList<>());
    }

    // ------------------------- CREATE -------------------------

    @Test
    @DisplayName("Test create new equipe with empty developers list")
    void createEquipeTest() {
        // Arrange
        when(equipeRepository.save(any(EquipeDevelopement.class))).thenReturn(equipe);
        when(equipeDevelopementMapper.fromEntity(equipe)).thenReturn(equipeDto);

        // Act
        EquipeDevelopementDTO result = equipeService.createEquipe(equipeDto);

        // Assert
        assertNotNull(result);
        assertEquals(equipeDto.id(), result.id());
        verify(equipeRepository, times(1)).save(any(EquipeDevelopement.class));
    }

    @Test
    @DisplayName("Test create new equipe with existing developers")
    void createEquipeWithDevelopersTest() {
        // Arrange
        List<Developer> devList = List.of(developer);
        EquipeDevelopementDTO dtoWithDevs = new EquipeDevelopementDTO(null, devList);

        EquipeDevelopement savedEquipe = new EquipeDevelopement(10L, devList);
        EquipeDevelopementDTO expectedResult = new EquipeDevelopementDTO(10L, devList);

        when(equipeRepository.save(any(EquipeDevelopement.class))).thenReturn(savedEquipe);
        when(equipeDevelopementMapper.fromEntity(savedEquipe)).thenReturn(expectedResult);

        // Act
        EquipeDevelopementDTO result = equipeService.createEquipe(dtoWithDevs);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.developers().size());
        verify(equipeRepository).save(any(EquipeDevelopement.class));
    }

    // ------------------------- GET BY ID -------------------------

    @Test
    @DisplayName("Test get equipe by ID - Success")
    void getEquipeByIdSuccessTest() {
        // Arrange
        when(equipeRepository.findById(10L)).thenReturn(Optional.of(equipe));
        when(equipeDevelopementMapper.fromEntity(equipe)).thenReturn(equipeDto);

        // Act
        EquipeDevelopementDTO result = equipeService.getEquipeById(10L);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.id());
    }

    @Test
    @DisplayName("Test get equipe by ID - Not Found Exception")
    void getEquipeByIdNotFoundTest() {
        // Arrange
        when(equipeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            equipeService.getEquipeById(99L);
        });

        assertTrue(exception.getMessage().contains("Equipe not found"));
    }

    // ------------------------- GET ALL -------------------------

    @Test
    @DisplayName("Test get all equipes")
    void getAllEquipesTest() {
        // Arrange
        when(equipeRepository.findAll()).thenReturn(Collections.singletonList(equipe));
        when(equipeDevelopementMapper.fromEntity(equipe)).thenReturn(equipeDto);

        // Act
        List<EquipeDevelopementDTO> result = equipeService.getAllEquipes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipeRepository).findAll();
    }

    // ------------------------- UPDATE -------------------------

    @Test
    @DisplayName("Test update equipe - Success")
    void updateEquipeSuccessTest() {
        // Arrange
        when(equipeRepository.findById(equipeDto.id())).thenReturn(Optional.of(equipe));
        when(equipeRepository.save(equipe)).thenReturn(equipe);
        when(equipeDevelopementMapper.fromEntity(equipe)).thenReturn(equipeDto);

        // Act
        EquipeDevelopementDTO result = equipeService.updateEquipe(equipeDto);

        // Assert
        assertNotNull(result);
        verify(equipeRepository).save(equipe);
    }

    @Test
    @DisplayName("Test update equipe - Not Found")
    void updateEquipeNotFoundTest() {
        // Arrange
        when(equipeRepository.findById(equipeDto.id())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            equipeService.updateEquipe(equipeDto);
        });
        verify(equipeRepository, never()).save(any());
    }

    // ------------------------- DELETE -------------------------

    @Test
    @DisplayName("Test delete equipe - Success")
    void deleteEquipeSuccessTest() {
        // Arrange
        when(equipeRepository.findById(10L)).thenReturn(Optional.of(equipe));

        // Act
        equipeService.deleteEquipe(10L);

        // Assert
        verify(equipeRepository).delete(equipe);
    }

    @Test
    @DisplayName("Test delete equipe - Not Found")
    void deleteEquipeNotFoundTest() {
        // Arrange
        when(equipeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            equipeService.deleteEquipe(99L);
        });
        verify(equipeRepository, never()).delete(any());
    }

    // ------------------------- ADD DEVELOPER TO EQUIPE -------------------------

    @Test
    @DisplayName("Test add developer to equipe - Success")
    void addDeveloperToEquipeSuccessTest() {
        // Arrange
        Long equipeId = 10L;
        Long devId = 1L;

        when(equipeRepository.findById(equipeId)).thenReturn(Optional.of(equipe));
        when(developerRepository.findById(devId)).thenReturn(Optional.of(developer));

        // Mock save returns (not strictly necessary for void/void-like logic but good practice)
        when(equipeRepository.save(any(EquipeDevelopement.class))).thenReturn(equipe);
        when(developerRepository.save(any(Developer.class))).thenReturn(developer);

        when(equipeDevelopementMapper.fromEntity(equipe)).thenReturn(equipeDto);

        // Act
        EquipeDevelopementDTO result = equipeService.addDeveloperToEquipe(equipeId, devId);

        // Assert
        // 1. Verify developer was added to equipe list
        assertTrue(equipe.getDevelopers().contains(developer));
        // 2. Verify equipe was set on developer
        assertEquals(equipe, developer.getEquipe());
        // 3. Verify saves called
        verify(equipeRepository).save(equipe);
        verify(developerRepository).save(developer);
    }

    @Test
    @DisplayName("Test add developer to equipe - Equipe Not Found")
    void addDeveloperToEquipeEquipeNotFoundTest() {
        // Arrange
        when(equipeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                equipeService.addDeveloperToEquipe(99L, 1L)
        );
        assertTrue(ex.getMessage().contains("Equipe not found"));
    }

    @Test
    @DisplayName("Test add developer to equipe - Developer Not Found")
    void addDeveloperToEquipeDevNotFoundTest() {
        // Arrange
        when(equipeRepository.findById(10L)).thenReturn(Optional.of(equipe));
        when(developerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                equipeService.addDeveloperToEquipe(10L, 99L)
        );
        assertTrue(ex.getMessage().contains("Equipe not found")); // Based on your service message for Dev
    }
}