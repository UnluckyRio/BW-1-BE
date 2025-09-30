package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "biglietto")
public class Biglietto extends TitoloViaggio {

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @Column(name = "validato", nullable = false)
    private boolean validato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distributore_automatico")
    private DistributoreAutomatico distributoreAutomatico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rivenditore")
    private Rivenditore rivenditore;

    public Biglietto() {
        super();
    }

    public Biglietto(LocalDate dataEmissione, LocalDate dataScadenza, DistributoreAutomatico distributoreAutomatico) {
        super();
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
        this.distributoreAutomatico = distributoreAutomatico;
        this.validato = false;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public boolean isValidato() {
        return validato;
    }

    public void setValidato(boolean validato) {
        this.validato = validato;
    }

    public DistributoreAutomatico getDistributoreAutomatico() {
        return distributoreAutomatico;
    }

    public void setDistributoreAutomatico(DistributoreAutomatico distributoreAutomatico) {
        this.distributoreAutomatico = distributoreAutomatico;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + getId() +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", validato=" + validato +
                '}';
    }
}
