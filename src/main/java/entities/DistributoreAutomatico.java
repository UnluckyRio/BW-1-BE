package entities;

import enums.StatoDistributore;
import jakarta.persistence.*;

@Entity
@Table(name = "Distriburore automatico")
@PrimaryKeyJoinColumn(name = "id_punto_emissione")
public class DistributoreAutomatico extends PuntoDiEmissione {
    @Enumerated(EnumType.STRING)
    @Column(name = "statoDistributore")
    private StatoDistributore statoDistributore;

    public DistributoreAutomatico() {
    }

    public DistributoreAutomatico(String indirizzo, long id_distributoreAutomatico, StatoDistributore statoDistributore) {
        super(indirizzo);
        this.statoDistributore = statoDistributore;

    }

    public StatoDistributore getStatoDistributore() {
        return statoDistributore;
    }

    public void setStatoDistributore(StatoDistributore statoDistributore) {
        this.statoDistributore = statoDistributore;
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "statoDistributore=" + statoDistributore +
                '}';
    }
}