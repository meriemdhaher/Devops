package tn.esprit.spring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.services.PisteServicesImpl;
import tn.esprit.spring.repositories.IPisteRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PisteServicesImplIntegrationTest {

    @Autowired
    private PisteServicesImpl pisteServices;

    @Autowired
    private IPisteRepository pisteRepository;

    @Test
    void testAddPiste() {
        // Arrange
        Piste piste = new Piste();
        piste.setNamePiste("Blue Mountain");
        piste.setColor(Color.BLUE);
        piste.setLength(500);
        piste.setSlope(30);

        // Act
        Piste savedPiste = pisteServices.addPiste(piste);

        // Assert
        assertNotNull(savedPiste.getNumPiste());
        assertEquals("Blue Mountain", savedPiste.getNamePiste());
        assertEquals(Color.BLUE, savedPiste.getColor());
    }

    @Test
    void testRetrieveAllPistes() {
        // Arrange
        Piste piste1 = new Piste();
        piste1.setNamePiste("Green Valley");
        piste1.setColor(Color.GREEN);
        piste1.setLength(300);
        piste1.setSlope(15);

        Piste piste2 = new Piste();
        piste2.setNamePiste("Red Rock");
        piste2.setColor(Color.RED);
        piste2.setLength(700);
        piste2.setSlope(40);

        pisteRepository.save(piste1);
        pisteRepository.save(piste2);

        // Act
        List<Piste> pistes = pisteServices.retrieveAllPistes();

        // Assert
        assertFalse(pistes.isEmpty());
        assertEquals(2, pistes.size());
    }

    @Test
    void testRetrievePiste_WhenPisteExists() {
        // Arrange
        Piste piste = new Piste();
        piste.setNamePiste("Black Diamond");
        piste.setColor(Color.BLACK);
        piste.setLength(800);
        piste.setSlope(50);

        Piste savedPiste = pisteRepository.save(piste);

        // Act
        Piste foundPiste = pisteServices.retrievePiste(savedPiste.getNumPiste());

        // Assert
        assertNotNull(foundPiste);
        assertEquals("Black Diamond", foundPiste.getNamePiste());
        assertEquals(Color.BLACK, foundPiste.getColor());
    }

    @Test
    void testRetrievePiste_WhenPisteDoesNotExist() {
        // Act
        Piste foundPiste = pisteServices.retrievePiste(999L);

        // Assert
        assertNull(foundPiste);
    }

    @Test
    void testRemovePiste() {
        // Arrange
        Piste piste = new Piste();
        piste.setNamePiste("Test Piste");
        piste.setColor(Color.GREEN);
        piste.setLength(350);
        piste.setSlope(25);

        Piste savedPiste = pisteRepository.save(piste);
        Long pisteId = savedPiste.getNumPiste();

        // Act
        pisteServices.removePiste(pisteId);

        // Assert
        Optional<Piste> deletedPiste = pisteRepository.findById(pisteId);
        assertTrue(deletedPiste.isEmpty());
    }
}
