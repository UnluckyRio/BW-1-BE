package entities;

import enums.StatoDistributore;
import jakarta.persistence.*;

@Entity
@Table(name = "distriburoreAutomatico")
public class DistributoreAutomatico {
    @Id
    @Column(name = "id_distributoreAutomatico")
    private long id_distributoreAutomatico;

    @Enumerated(EnumType.STRING)
    @Column(name = "statoDistributore")
    private StatoDistributore statoDistributore;
    @Column(name = "id_biglietto")
    private long id_biglietto;
    @Column(name = "id_abbonamento")
    private long id_abbonamento;

    public DistributoreAutomatico() {
    }

    public DistributoreAutomatico(long id_distributoreAutomatico, StatoDistributore statoDistributore, long id_biglietto, long id_abbonamento) {
        this.id_distributoreAutomatico = id_distributoreAutomatico;
        this.statoDistributore = statoDistributore;
        this.id_biglietto = id_biglietto;
        this.id_abbonamento = id_abbonamento;
    }

    public long getId_distributoreAutomatico() {
        return id_distributoreAutomatico;
    }

    public void setId_distributoreAutomatico(long id_distributoreAutomatico) {
        this.id_distributoreAutomatico = id_distributoreAutomatico;
    }

    public StatoDistributore getStatoDistributore() {
        return statoDistributore;
    }

    public void setStatoDistributore(StatoDistributore statoDistributore) {
        this.statoDistributore = statoDistributore;
    }

    public long getId_biglietto() {
        return id_biglietto;
    }

    public void setId_biglietto(long id_biglietto) {
        this.id_biglietto = id_biglietto;
    }

    public long getId_abbonamento() {
        return id_abbonamento;
    }

    public void setId_abbonamento(long id_abbonamento) {
        this.id_abbonamento = id_abbonamento;
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "id_distributoreAutomatico=" + id_distributoreAutomatico +
                ", statoDistributore=" + statoDistributore +
                ", id_biglietto=" + id_biglietto +
                ", id_abbonamento=" + id_abbonamento +
                '}';
    }
}