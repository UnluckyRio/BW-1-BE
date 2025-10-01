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

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @Column(name = "dataInizio")
    private LocalDate dataInizio;

    @Column(name = "dataFine")
    private LocalDate dataFine;

    public Manutenzione() {}

    public Manutenzione(Long idManutenzione, Mezzo mezzo, LocalDate dataInizio, LocalDate dataFine) {
        this.idManutenzione = idManutenzione;
        this.mezzo = mezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Long getIdManutenzione() {
        return idManutenzione;
    }

    public void setIdManutenzione(Long idManutenzione) {
        this.idManutenzione = idManutenzione;
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
        return "Manutenzione{" +
                "idManutenzione=" + idManutenzione +
                ", mezzo=" + mezzo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}
