package entities;

import enums.StatoRivenditore;
import jakarta.persistence.*;

@Entity
@Table(name = "rivenditore")
@PrimaryKeyJoinColumn(name = "id_punto_emissione")

public class Rivenditore extends PuntoDiEmissione {
    @Column(name = "nomeRivenditore", nullable = false, length = 30)
    private String nomeRivenditore;
    @Enumerated(EnumType.STRING)
    @Column(name = "Stato apertura", nullable = false)
    private StatoRivenditore statoRivenditore;

    public Rivenditore() {
    }

    public Rivenditore(String indirizzo,String nomeRivenditore, StatoRivenditore statoRivenditore) {
        super(indirizzo);
        this.nomeRivenditore = nomeRivenditore;
        this.statoRivenditore = statoRivenditore;
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

    @Override
    public String toString() {
        return "Rivenditore{" +
                "nomeRivenditore='" + nomeRivenditore + '\'' +
                ", statoRivenditore=" + statoRivenditore +
                '}';
    }
}