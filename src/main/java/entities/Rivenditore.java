package entities;

import enums.StatoRivenditore;
import jakarta.persistence.*;

@Entity
@Table(name = "rivenditore")

public class Rivenditore {
    @Id
    @Column(name = "id_rivenditore")
    private long id_rivenditore;
    @Column(name = "nomeRivenditore", nullable = false, length = 30)
    private String nomeRivenditore;
    @Enumerated(EnumType.STRING)
    @Column(name = "Stato apertura", nullable = false)
    private StatoRivenditore statoRivenditore;
    @Column(name = "nomeRivenditore")
    private Rivenditore rivenditore;
    @Column(name = "id_biglietto")
    private long id_biglietto;
    @Column(name = "id_abbonamento")
    private long getId_biglietto;

    public Rivenditore() {
    }

    public Rivenditore(long id_rivenditore, String nomeRivenditore, StatoRivenditore statoRivenditore, Rivenditore rivenditore, long id_biglietto, long getId_biglietto) {
        this.id_rivenditore = id_rivenditore;
        this.nomeRivenditore = nomeRivenditore;
        this.statoRivenditore = statoRivenditore;
        this.rivenditore = rivenditore;
        this.id_biglietto = id_biglietto;
        this.getId_biglietto = getId_biglietto;
    }

    public long getId_rivenditore() {
        return id_rivenditore;
    }

    public void setId_rivenditore(long id_rivenditore) {
        this.id_rivenditore = id_rivenditore;
    }

    public String getNomeRivenditore() {
        return nomeRivenditore;
    }

    public void setNomeRivenditore(String nomeRivenditore) {
        this.nomeRivenditore = nomeRivenditore;
    }

    public StatoRivenditore getStatoRivenditore() {
        return statoRivenditore;
    }

    public void setStatoRivenditore(StatoRivenditore statoRivenditore) {
        this.statoRivenditore = statoRivenditore;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    public long getId_biglietto() {
        return id_biglietto;
    }

    public void setId_biglietto(long id_biglietto) {
        this.id_biglietto = id_biglietto;
    }

    public long getGetId_biglietto() {
        return getId_biglietto;
    }

    public void setGetId_biglietto(long getId_biglietto) {
        this.getId_biglietto = getId_biglietto;
    }

    @Override
    public String toString() {
        return "Rivenditore{" +
                "id_rivenditore=" + id_rivenditore +
                ", nomeRivenditore='" + nomeRivenditore + '\'' +
                ", statoRivenditore=" + statoRivenditore +
                ", rivenditore=" + rivenditore +
                ", id_biglietto=" + id_biglietto +
                ", getId_biglietto=" + getId_biglietto +
                '}';
    }
}
