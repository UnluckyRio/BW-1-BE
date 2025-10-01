package entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tessera")
public class Tessera extends TitoloViaggio {

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @Column(name = "attiva", nullable = false)
    private boolean attiva;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Abbonamento> abbonamenti;

    public Tessera() {
        super();
    }

    public Tessera(LocalDate dataEmissione, LocalDate dataScadenza, boolean attiva, Utente utente) {
        super();
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
        this.attiva = attiva;
        this.utente = utente;
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

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + getId() +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", attiva=" + attiva +
                ", utente=" + (utente != null ? utente.getId() : null) +
                '}';
    }
}
