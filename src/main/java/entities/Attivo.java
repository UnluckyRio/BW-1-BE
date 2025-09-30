package entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Entità Attivo - Rappresenta i periodi di servizio attivo per i veicoli
 * Memorizza i record storici dei periodi di servizio attivo per i mezzi
 */
@Entity
@Table(name = "attivo")
public class Attivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    /**
     * Relazione Many-to-One con l'entità Mezzo
     * Un mezzo può avere molti periodi di attività
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;
    
    /**
     * Data di inizio del periodo di servizio attivo
     */
    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;
    
    /**
     * Data di fine del periodo di servizio attivo
     */
    @Column(name = "data_fine", nullable = false)
    private LocalDate dataFine;
    
    /**
     * Costruttore vuoto richiesto da JPA
     */
    public Attivo() {
    }
    
    /**
     * Costruttore con parametri per creare un nuovo periodo di attività
     * 
     * @param mezzo Il mezzo associato al periodo di attività
     * @param dataInizio La data di inizio del periodo
     * @param dataFine La data di fine del periodo
     */
    public Attivo(Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine) {
        this.mezzo = mezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }
    
    // Getter e Setter
    
    public long getId() {
        return id;
    }
    
    public Mezzo getMezzo() {
        return mezzo;
    }
    
    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }
    
    public LocalDate getDataInizio() {
        return dataInizio;
    }
    
    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }
    
    public LocalDate getDataFine() {
        return dataFine;
    }
    
    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }
    
    /**
     * Metodo per verificare se il periodo è attualmente attivo
     * 
     * @return true se il periodo è attivo oggi, false altrimenti
     */
    public boolean isCurrentlyActive() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(dataInizio) && !today.isAfter(dataFine);
    }
    
    /**
     * Metodo per calcolare la durata del periodo in giorni
     * 
     * @return il numero di giorni del periodo
     */
    public long getDurationInDays() {
        return ChronoUnit.DAYS.between(dataInizio, dataFine) + 1; // +1 per includere entrambi i giorni
    }
    
    @Override
    public String toString() {
        return "Attivo{" +
                "id=" + id +
                ", mezzo=" + (mezzo != null ? mezzo.getId() : "null") +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attivo that = (Attivo) o;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}