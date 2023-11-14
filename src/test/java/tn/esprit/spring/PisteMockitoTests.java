package tn.esprit.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class PisteMockitoTests {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @Test
    public void testRetrieveAllPistes() {
        List<Piste> mockPistes = new ArrayList<>();
        mockPistes.add(new Piste(1L, "piste1", Color.GREEN, 2, 1, null));
        mockPistes.add(new Piste(2L, "piste2", Color.RED, 3, 2, null));
        Mockito.when(pisteRepository.findAll()).thenReturn(mockPistes);

        List<Piste> result = pisteServices.retrieveAllPistes();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(mockPistes, result);
    }

    @Test
    public void testAddPiste() {
        Piste pisteToAdd = new Piste(3L, "piste3", Color.BLUE, 4, 3, null);

        Mockito.when(pisteRepository.save(pisteToAdd)).thenReturn(pisteToAdd);

        Piste result = pisteServices.addPiste(pisteToAdd);

        Assertions.assertEquals(pisteToAdd, result);
    }

    @Test
    public void testRetrievePisteById() {
        Piste mockPiste = new Piste(4L, "piste4", Color.BLACK, 5, 4, null);
        Mockito.when(pisteRepository.findById(4L)).thenReturn(Optional.of(mockPiste));

        Optional<Piste> result = Optional.ofNullable(pisteServices.retrievePiste(4L));

        // Assertions
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockPiste, result.get());
    }

    @Test
    public void testUpdatePiste() {
        Piste pisteToUpdate = new Piste(5L, "piste5", Color.GREEN, 6, 5, null);
        Piste result = pisteServices.updatePiste(pisteToUpdate);
        Assertions.assertEquals(pisteToUpdate, result);
    }

    @Test
    public void testDeletePiste() {
        Mockito.doNothing().when(pisteRepository).deleteById(5L);
        pisteServices.removePiste(5L);

        Mockito.verify(pisteRepository, Mockito.times(1)).deleteById(5L);
    }
}