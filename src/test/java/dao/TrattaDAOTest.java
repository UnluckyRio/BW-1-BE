package dao;

import entities.Tratta;
import entities.Mezzo;
import exceptions.NotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Test class per TrattaDAO
 * Verifica la funzionalità di tutti i metodi implementati
 */
public class TrattaDAOTest {
    
    private TrattaDAO trattaDAO;
    private Mezzo mezzoTest;
    
    @BeforeEach
    void setUp() {
        // Inizializza il DAO per ogni test
        trattaDAO = new TrattaDAO();
        
        // Crea un mezzo di test con dati validi
        mezzoTest = new Mezzo("Autobus", 50, "Attivo");
    }
    
    @AfterEach
    void tearDown() {
        // Chiude le risorse dopo ogni test
        if (trattaDAO != null) {
            trattaDAO.close();
        }
    }
    
    @Test
    @DisplayName("Test salvataggio tratta valida")
    void testSaveValidTratta() {
        // Arrange
        Tratta tratta = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        
        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            Tratta savedTratta = trattaDAO.save(tratta);
            Assertions.assertNotNull(savedTratta);
            Assertions.assertTrue(savedTratta.getId() > 0);
        });
    }
    
    @Test
    @DisplayName("Test salvataggio tratta null - deve lanciare IllegalArgumentException")
    void testSaveNullTratta() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.save(null);
        });
    }
    
    @Test
    @DisplayName("Test ricerca per ID valido")
    void testFindByIdValid() {
        // Arrange
        Tratta tratta = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        Tratta savedTratta = trattaDAO.save(tratta);
        
        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            Tratta foundTratta = trattaDAO.findById(savedTratta.getId());
            Assertions.assertNotNull(foundTratta);
            Assertions.assertEquals(savedTratta.getId(), foundTratta.getId());
        });
    }
    
    @Test
    @DisplayName("Test ricerca per ID non esistente - deve lanciare NotFoundException")
    void testFindByIdNotFound() {
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            trattaDAO.findById(99999L);
        });
    }
    
    @Test
    @DisplayName("Test ricerca per ID invalido - deve lanciare IllegalArgumentException")
    void testFindByIdInvalid() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.findById(-1L);
        });
    }
    
    @Test
    @DisplayName("Test recupero di tutte le tratte")
    void testFindAll() {
        // Arrange
        Tratta tratta1 = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        Tratta tratta2 = new Tratta(mezzoTest, LocalDate.now().plusDays(1), LocalDate.now().plusDays(8));
        
        trattaDAO.save(tratta1);
        trattaDAO.save(tratta2);
        
        // Act
        List<Tratta> tratte = trattaDAO.findAll();
        
        // Assert
        Assertions.assertNotNull(tratte);
        Assertions.assertTrue(tratte.size() >= 2);
    }
    
    @Test
    @DisplayName("Test ricerca per mezzo")
    void testFindByMezzo() {
        // Arrange
        Tratta tratta = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        trattaDAO.save(tratta);
        
        // Act
        List<Tratta> tratte = trattaDAO.findByMezzo(mezzoTest);
        
        // Assert
        Assertions.assertNotNull(tratte);
        Assertions.assertTrue(tratte.size() >= 1);
    }
    
    @Test
    @DisplayName("Test ricerca per mezzo null - deve lanciare IllegalArgumentException")
    void testFindByMezzoNull() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.findByMezzo(null);
        });
    }
    
    @Test
    @DisplayName("Test ricerca per periodo")
    void testFindByPeriodo() {
        // Arrange
        LocalDate dataInizio = LocalDate.now();
        LocalDate dataFine = LocalDate.now().plusDays(10);
        Tratta tratta = new Tratta(mezzoTest, dataInizio.plusDays(2), dataFine.minusDays(2));
        trattaDAO.save(tratta);
        
        // Act
        List<Tratta> tratte = trattaDAO.findByPeriodo(dataInizio, dataFine);
        
        // Assert
        Assertions.assertNotNull(tratte);
        Assertions.assertTrue(tratte.size() >= 1);
    }
    
    @Test
    @DisplayName("Test ricerca per periodo con date null - deve lanciare IllegalArgumentException")
    void testFindByPeriodoNullDates() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.findByPeriodo(null, LocalDate.now());
        });
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.findByPeriodo(LocalDate.now(), null);
        });
    }
    
    @Test
    @DisplayName("Test ricerca per periodo con date invertite - deve lanciare IllegalArgumentException")
    void testFindByPeriodoInvertedDates() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.findByPeriodo(LocalDate.now().plusDays(5), LocalDate.now());
        });
    }
    
    @Test
    @DisplayName("Test ricerca tratte attive")
    void testFindTratteAttive() {
        // Arrange
        LocalDate oggi = LocalDate.now();
        Tratta trattaAttiva = new Tratta(mezzoTest, oggi.minusDays(1), oggi.plusDays(1));
        trattaDAO.save(trattaAttiva);
        
        // Act
        List<Tratta> tratteAttive = trattaDAO.findTratteAttive();
        
        // Assert
        Assertions.assertNotNull(tratteAttive);
        Assertions.assertTrue(tratteAttive.size() >= 1);
    }
    
    @Test
    @DisplayName("Test aggiornamento tratta")
    void testUpdate() {
        // Arrange
        Tratta tratta = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        Tratta savedTratta = trattaDAO.save(tratta);
        
        // Modifica la tratta
        savedTratta.setDataFine(LocalDate.now().plusDays(14));
        
        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            Tratta updatedTratta = trattaDAO.update(savedTratta);
            Assertions.assertNotNull(updatedTratta);
            Assertions.assertEquals(LocalDate.now().plusDays(14), updatedTratta.getDataFine());
        });
    }
    
    @Test
    @DisplayName("Test aggiornamento tratta null - deve lanciare IllegalArgumentException")
    void testUpdateNullTratta() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.update(null);
        });
    }
    
    @Test
    @DisplayName("Test eliminazione tratta")
    void testDelete() {
        // Arrange
        Tratta tratta = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        Tratta savedTratta = trattaDAO.save(tratta);
        
        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            trattaDAO.delete(savedTratta.getId());
        });
        
        // Verifica che la tratta sia stata eliminata
        Assertions.assertThrows(NotFoundException.class, () -> {
            trattaDAO.findById(savedTratta.getId());
        });
    }
    
    @Test
    @DisplayName("Test eliminazione tratta non esistente - deve lanciare NotFoundException")
    void testDeleteNotFound() {
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            trattaDAO.delete(99999L);
        });
    }
    
    @Test
    @DisplayName("Test conteggio tratte")
    void testCountTratte() {
        // Arrange
        long countBefore = trattaDAO.countTratte();
        
        Tratta tratta = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        trattaDAO.save(tratta);
        
        // Act
        long countAfter = trattaDAO.countTratte();
        
        // Assert
        Assertions.assertEquals(countBefore + 1, countAfter);
    }
    
    @Test
    @DisplayName("Test verifica esistenza tratta")
    void testExists() {
        // Arrange
        Tratta tratta = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        Tratta savedTratta = trattaDAO.save(tratta);
        
        // Act & Assert
        Assertions.assertTrue(trattaDAO.exists(savedTratta.getId()));
        Assertions.assertFalse(trattaDAO.exists(99999L));
        Assertions.assertFalse(trattaDAO.exists(-1L));
    }
    
    @Test
    @DisplayName("Test salvataggio batch")
    void testSaveAll() {
        // Arrange
        Tratta tratta1 = new Tratta(mezzoTest, LocalDate.now(), LocalDate.now().plusDays(7));
        Tratta tratta2 = new Tratta(mezzoTest, LocalDate.now().plusDays(1), LocalDate.now().plusDays(8));
        List<Tratta> tratte = Arrays.asList(tratta1, tratta2);
        
        // Act & Assert
        Assertions.assertDoesNotThrow(() -> {
            List<Tratta> savedTratte = trattaDAO.saveAll(tratte);
            Assertions.assertNotNull(savedTratte);
            Assertions.assertEquals(2, savedTratte.size());
        });
    }
    
    @Test
    @DisplayName("Test salvataggio batch con lista null - deve lanciare IllegalArgumentException")
    void testSaveAllNullList() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trattaDAO.saveAll(null);
        });
    }
    
    @Test
    @DisplayName("Test ricerca paginata")
    void testFindWithPagination() {
        // Arrange
        for (int i = 0; i < 5; i++) {
            Tratta tratta = new Tratta(mezzoTest, LocalDate.now().plusDays(i), LocalDate.now().plusDays(i + 7));
            trattaDAO.save(tratta);
        }
        
        // Act
        List<Tratta> firstPage = trattaDAO.findWithPagination(0, 3);
        List<Tratta> secondPage = trattaDAO.findWithPagination(1, 3);
        
        // Assert
        Assertions.assertNotNull(firstPage);
        Assertions.assertNotNull(secondPage);
        Assertions.assertTrue(firstPage.size() <= 3);
        Assertions.assertTrue(secondPage.size() <= 3);
    }
    
    @Test
    @DisplayName("Test ricerca per mezzo e periodo")
    void testFindByMezzoAndPeriodo() {
        // Arrange
        LocalDate dataInizio = LocalDate.now();
        LocalDate dataFine = LocalDate.now().plusDays(10);
        Tratta tratta = new Tratta(mezzoTest, dataInizio.plusDays(2), dataFine.minusDays(2));
        trattaDAO.save(tratta);
        
        // Act
        List<Tratta> tratte = trattaDAO.findByMezzoAndPeriodo(mezzoTest, dataInizio, dataFine);
        
        // Assert
        Assertions.assertNotNull(tratte);
        Assertions.assertTrue(tratte.size() >= 1);
    }
}