package tn.esprit.spring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PisteServicesImplTest {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllPistes() {
        // Arrange
        Piste piste1 = new Piste();
        Piste piste2 = new Piste();
        List<Piste> pistes = Arrays.asList(piste1, piste2);
        when(pisteRepository.findAll()).thenReturn(pistes);

        // Act
        List<Piste> result = pisteServices.retrieveAllPistes();

        // Assert
        assertEquals(2, result.size());
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void testAddPiste() {
        // Arrange
        Piste piste = new Piste();
        when(pisteRepository.save(piste)).thenReturn(piste);

        // Act
        Piste result = pisteServices.addPiste(piste);

        // Assert
        assertNotNull(result);
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    void testRemovePiste() {
        // Arrange
        Long numPiste = 1L;
        doNothing().when(pisteRepository).deleteById(numPiste);

        // Act
        pisteServices.removePiste(numPiste);

        // Assert
        verify(pisteRepository, times(1)).deleteById(numPiste);
    }

    @Test
    void testRetrievePiste_WhenPisteExists() {
        // Arrange
        Long numPiste = 1L;
        Piste piste = new Piste();
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(piste));

        // Act
        Piste result = pisteServices.retrievePiste(numPiste);

        // Assert
        assertNotNull(result);
        verify(pisteRepository, times(1)).findById(numPiste);
    }

    @Test
    void testRetrievePiste_WhenPisteDoesNotExist() {
        // Arrange
        Long numPiste = 1L;
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.empty());

        // Act
        Piste result = pisteServices.retrievePiste(numPiste);

        // Assert
        assertNull(result);
        verify(pisteRepository, times(1)).findById(numPiste);
    }
}
