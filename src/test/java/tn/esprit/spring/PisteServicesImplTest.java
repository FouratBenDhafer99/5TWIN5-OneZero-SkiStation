package tn.esprit.spring;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.IPisteServices;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class PisteServicesImplTest {

    @Mock
    private IPisteRepository pisteRepository;
    @InjectMocks
    private PisteServicesImpl pisteServices;


    @Test
    public void testRetrieveAllPistes() {
        // Mock the behavior of pisteRepository.findAll()
        List<Piste> mockPistes = new ArrayList<>();
        mockPistes.add(new Piste(1L, "piste1", Color.GREEN, 2, 1, null));
        mockPistes.add(new Piste(2L, "piste2", Color.RED, 3, 2, null));
        Mockito.when(pisteRepository.findAll()).thenReturn(mockPistes);

        // Call the actual service method
        List<Piste> result = pisteServices.retrieveAllPistes();

        // Assertions
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(mockPistes, result);
    }

    @Test
    public void testAddPiste() {
        // Create a Piste to add
        Piste pisteToAdd = new Piste(3L, "piste3", Color.BLUE, 4, 3, null);

        // Mock the behavior of pisteRepository.save()
        Mockito.when(pisteRepository.save(pisteToAdd)).thenReturn(pisteToAdd);

        // Call the actual service method
        Piste result = pisteServices.addPiste(pisteToAdd);

        // Assertions
        Assertions.assertEquals(pisteToAdd, result);
    }
    @Test
    public Piste testRetrievePisteById() {
        // Mock the behavior of pisteRepository.findById()
        Piste mockPiste = new Piste(4L, "piste4", Color.BLACK, 5, 4, null);
        Mockito.when(pisteRepository.findById(4L)).thenReturn(Optional.of(mockPiste));

        // Call the actual service method
        Optional<Piste> result = Optional.ofNullable(pisteServices.retrievePiste(4L));

        // Assertions
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockPiste, result.get());
        return result.get();
    }

    @Test
    public void testUpdatePiste() {
        // Create a Piste to update
        Piste pisteToUpdate = new Piste(5L, "piste5", Color.GREEN, 6, 5, null);

        // Call the actual service method
        Piste result = pisteServices.updatePiste(pisteToUpdate);

        // Assertions
        Assertions.assertEquals(pisteToUpdate, result);
    }


    @Test
    public void testDeletePiste() {
        // Mock the behavior of pisteRepository.deleteById()
        Mockito.doNothing().when(pisteRepository).deleteById(5L);
        // Call the actual service method
        pisteServices.removePiste(5L);

        // Assertions (Optional: Verify that the delete method was called)
        Mockito.verify(pisteRepository, Mockito.times(1)).deleteById(5L);
    }




}
