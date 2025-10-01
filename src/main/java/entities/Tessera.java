package entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "tessera")
public class Tessera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @OneToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    public Tessera() {}

    public Tessera(LocalDate dataEmissione, LocalDate dataScadenza, Utente utente) {
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
        this.utente = utente;
    }

    public Tessera(String dataEmissioneStr, String dataScadenzaStr, Utente utente) {
        this(LocalDate.parse(dataEmissioneStr), LocalDate.parse(dataScadenzaStr), utente);
    }


    public Long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
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

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", utente=" + utente +
                '}';
    }
}
