package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test") // Utilise le profil de test

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
        // Given
        Piste piste1 = new Piste(1L, "Piste A", Color.RED, 1000, 30, null);
        Piste piste2 = new Piste(2L, "Piste B", Color.BLUE, 1200, 40, null);
        List<Piste> expectedPistes = Arrays.asList(piste1, piste2);
        when(pisteRepository.findAll()).thenReturn(expectedPistes);

        // When
        List<Piste> actualPistes = pisteServices.retrieveAllPistes();

        // Then
        assertEquals(expectedPistes.size(), actualPistes.size());
        assertTrue(actualPistes.containsAll(expectedPistes));
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void testAddPiste() {
        // Given
        Piste piste = new Piste(null, "Piste C", Color.GREEN, 1500, 35, null);
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        // When
        Piste savedPiste = pisteServices.addPiste(piste);

        // Then
        assertEquals(piste.getNamePiste(), savedPiste.getNamePiste());
        assertEquals(piste.getColor(), savedPiste.getColor());
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    void testRemovePiste() {
        // Given
        Long numPiste = 1L;

        // When
        pisteServices.removePiste(numPiste);

        // Then
        verify(pisteRepository, times(1)).deleteById(numPiste);
    }

    @Test
    void testRetrievePiste() {
        // Given
        Long numPiste = 1L;
        Piste piste = new Piste(numPiste, "Piste A", Color.RED, 1000, 30, null);
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(piste));

        // When
        Piste retrievedPiste = pisteServices.retrievePiste(numPiste);

        // Then
        assertEquals(piste, retrievedPiste);
        verify(pisteRepository, times(1)).findById(numPiste);
    }

    @Test
    void testRetrievePisteNotFound() {
        // Given
        Long numPiste = 1L;
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.empty());

        // When
        Piste retrievedPiste = pisteServices.retrievePiste(numPiste);

        // Then
        assertNull(retrievedPiste);
        verify(pisteRepository, times(1)).findById(numPiste);
    }
}
