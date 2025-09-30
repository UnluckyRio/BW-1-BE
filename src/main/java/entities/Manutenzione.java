package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "manutenzione")

public class Manutenzione {
    @Id
    @ManyToOne
    @JoinColumn(name = "manutenzione_id")
    private long idManutenzione;
    @OneToMany(mappedBy = "idMezzo")
    private long idMezzo;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    // Costruttore


    public Manutenzione(long idManutenzione, long idMezzo, LocalDate dataInizio, LocalDate dataFine) {
        this.idManutenzione = idManutenzione;
        this.idMezzo = idMezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    // Getter & Setter

    public long getIdManutenzione() {
        return idManutenzione;
    }

    public void setIdManutenzione(long idManutenzione) {
        this.idManutenzione = idManutenzione;
    }

    public long getIdMezzo() {
        return idMezzo;
    }

    public void setIdMezzo(long idMezzo) {
        this.idMezzo = idMezzo;
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

    // toString

    @Override
    public String toString() {
        return "Manutenzione{" +
                "idManutenzione=" + idManutenzione +
                ", idMezzo=" + idMezzo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}
