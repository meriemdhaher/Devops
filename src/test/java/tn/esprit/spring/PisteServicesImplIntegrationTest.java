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



}
