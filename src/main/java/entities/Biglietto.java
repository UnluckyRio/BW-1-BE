package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "biglietto")
public class Biglietto extends TitoloViaggio {

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

public class Biglietto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Durata biglietto", nullable = false)
    private int durataBiglietto;
    @Column(name = "prezzo")
    private double prezzo;
    @Column(name = "Data Emissione")
    private LocalDate dataEmissione;

    @OneToOne
    @JoinColumn(name = "id_distributore", nullable = false)
    private DistributoreAutomatico distributore;

    @OneToOne
    @JoinColumn(name = "id_rivenditore", nullable = false)
    private Rivenditore rivenditore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distributore_automatico")
    private DistributoreAutomatico distributoreAutomatico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rivenditore")
    private Rivenditore rivenditore;

    public Biglietto() {
        super();
    }


    public Biglietto(double prezzo, int durataBiglietto, LocalDate dataEmissione) {

        this.id = id;
        this.durataBiglietto = durataBiglietto;
        this.prezzo = prezzo;
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

    public int getDurataBiglietto() {
        return durataBiglietto;
    }

    public void setDurataBiglietto(int durataBiglietto) {
        this.durataBiglietto = durataBiglietto;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public DistributoreAutomatico getDistributore() {
        return distributore;
    }

    public void setDistributore(DistributoreAutomatico distributore) {
        this.distributore = distributore;
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
                "id=" + id +
                ", durataBiglietto=" + durataBiglietto +
                ", prezzo=" + prezzo +
                ", dataEmissione=" + dataEmissione +
                ", distributore=" + distributore +
                ", rivenditore=" + rivenditore +
                '}';
    }
}