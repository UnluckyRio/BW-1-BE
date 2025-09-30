package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity

public class Manutenzione {
    @Id
    @GeneratedValue
    protected long idManutenzione;
    protected long idMezzo;
    protected LocalDate dataInizio;
    protected LocalDate dataFine;

    // Costruttore

    public Manutenzione() {
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
