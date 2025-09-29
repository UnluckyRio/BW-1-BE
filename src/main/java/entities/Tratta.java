package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Tratta")
public class Tratta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;
    
    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;
    
    @Column(name = "data_fine", nullable = false)
    private LocalDate dataFine;
    
    public Tratta() {
    }
    
    public Tratta(Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine) {
        this.mezzo = mezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }
    
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
    
    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", mezzo=" + mezzo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}