package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "validazione")

public class Validazione {
    @Id
    @GeneratedValue
    private long idValidazione;
    @OneToMany(mappedBy = "idMezzo")
    private long idMezzo;
    @ManyToOne
    @JoinColumn(name = "biglietto_id")
    private long idBiglietto;
    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private long idTessera;
    private LocalDate dataOraValidazione;


    public Validazione() {
    }

    public Validazione(long idValidazione, long idMezzo, long idBiglietto, long idTessera, LocalDate dataOraValidazione) {
        this.idValidazione = idValidazione;
        this.idMezzo = idMezzo;
        this.idBiglietto = idBiglietto;
        this.idTessera = idTessera;
        this.dataOraValidazione = dataOraValidazione;
    }


    public long getIdValidazione() {
        return idValidazione;
    }

    public void setIdValidazione(long idValidazione) {
        this.idValidazione = idValidazione;
    }

    public long getIdMezzo() {
        return idMezzo;
    }

    public void setIdMezzo(long idMezzo) {
        this.idMezzo = idMezzo;
    }

    public long getIdBiglietto() {
        return idBiglietto;
    }

    public void setIdBiglietto(long idBiglietto) {
        this.idBiglietto = idBiglietto;
    }

    public long getIdTessera() {
        return idTessera;
    }

    public void setIdTessera(long idTessera) {
        this.idTessera = idTessera;
    }

    public LocalDate getDataOraValidazione() {
        return dataOraValidazione;
    }

    public void setDataOraValidazione(LocalDate dataOraValidazione) {
        this.dataOraValidazione = dataOraValidazione;
    }


    @Override
    public String toString() {
        return "Validazione{" +
                "idValidazione=" + idValidazione +
                ", idMezzo=" + idMezzo +
                ", idBiglietto=" + idBiglietto +
                ", idTessera=" + idTessera +
                ", dataOraValidazione=" + dataOraValidazione +
                '}';
    }
}