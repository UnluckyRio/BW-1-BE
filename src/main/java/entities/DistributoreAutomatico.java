package entities;

import enums.StatoDistributore;
import jakarta.persistence.*;

@Entity
@Table(name = "distributore_automatico")
public class DistributoreAutomatico extends PuntoEmissione {

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_distributore", nullable = false)
    private StatoDistributore statoDistributore;

    public DistributoreAutomatico() {
        super();
    }

    public DistributoreAutomatico(StatoDistributore statoDistributore) {
        super();
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
                "id=" + getId() +
                ", statoDistributore=" + statoDistributore +
                '}';
    }
}
