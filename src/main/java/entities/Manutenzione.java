package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "manutenzione")
public class Manutenzione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_manutenzione")
    private Long idManutenzione;

    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    public Manutenzione() {
    }

    public Manutenzione(LocalDate dataInizio, Mezzo mezzo) {
        this.dataInizio = dataInizio;
        this.mezzo = mezzo;
    }

    public Manutenzione(LocalDate dataInizio, LocalDate dataFine, Mezzo mezzo) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.mezzo = mezzo;
    }

    public Long getIdManutenzione() {
        return idManutenzione;
    }

    public void setIdManutenzione(Long idManutenzione) {
        this.idManutenzione = idManutenzione;
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

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Manutenzione{" +
                "idManutenzione=" + idManutenzione +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + (mezzo != null ? mezzo.getId() : null) +
                '}';
    }
}
