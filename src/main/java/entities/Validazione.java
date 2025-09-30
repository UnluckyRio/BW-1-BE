package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity

public class Validazione {
    @Id
    @GeneratedValue
    protected long idValidazione;
    protected long idTitoloDiViaggio;
    protected long idMezzo;
    protected LocalDate dataValidazione;

    // Costruttore


    public Validazione(long idValidazione, long idTitoloDiViaggio, long idMezzo, LocalDate dataValidazione) {
        this.idValidazione = idValidazione;
        this.idTitoloDiViaggio = idTitoloDiViaggio;
        this.idMezzo = idMezzo;
        this.dataValidazione = dataValidazione;
    }

    // Getter & Setter

    public long getIdValidazione() {
        return idValidazione;
    }

    public void setIdValidazione(long idValidazione) {
        this.idValidazione = idValidazione;
    }

    public long getIdTitoloDiViaggio() {
        return idTitoloDiViaggio;
    }

    public void setIdTitoloDiViaggio(long idTitoloDiViaggio) {
        this.idTitoloDiViaggio = idTitoloDiViaggio;
    }

    public long getIdMezzo() {
        return idMezzo;
    }

    public void setIdMezzo(long idMezzo) {
        this.idMezzo = idMezzo;
    }

    public LocalDate getDataValidazione() {
        return dataValidazione;
    }

    public void setDataValidazione(LocalDate dataValidazione) {
        this.dataValidazione = dataValidazione;
    }

    // toString

    @Override
    public String toString() {
        return "Validazione{" +
                "idValidazione=" + idValidazione +
                ", idTitoloDiViaggio=" + idTitoloDiViaggio +
                ", idMezzo=" + idMezzo +
                ", dataValidazione=" + dataValidazione +
                '}';
    }
}
