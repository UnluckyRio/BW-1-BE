package entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Biglietto")


public class Biglietto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "durata biglietto", nullable = false)
    private int durataBiglietto;
    @Column(name = "prezzo")
    private double prezzo;
    @Column(name = "Data Emissione")
    private LocalDate dataEmissione;

    @ManyToOne
    @JoinColumn(name = "id_distributore", nullable = false)
    private DistributoreAutomatico distributore;

    @ManyToOne
    @JoinColumn(name = "id_rivenditore", nullable = false)
    private Rivenditore rivenditore;

    @Column(name= "Validazione", nullable = false)
    private boolean validazione;
    @Column(name= "Data validazione", nullable = false)
    private LocalDate dataValidazione;

    @ManyToOne
    @JoinColumn(name = "id_mezzo_validante", nullable = false)
    private Mezzo mezzo;


    public Biglietto() {

    }

    public Biglietto( int durataBiglietto, double prezzo,  LocalDate dataEmissione,DistributoreAutomatico distributore, Rivenditore rivenditore, boolean validazione, LocalDate dataValidazione, Mezzo mezzo ) {
        this.prezzo = prezzo;
        this.durataBiglietto = durataBiglietto;
        this.dataEmissione = dataEmissione;
        this.distributore = distributore;
        this.rivenditore = rivenditore;
        this.validazione = validazione;
        this.dataValidazione = dataValidazione;
        this.mezzo = mezzo;


    }

    public long getId() {
        return id;
    }

    public Mezzo getMezzoId() {
        return mezzo;
    }

    public void setMezzoId(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDate getDataValidazione() {
        return dataValidazione;
    }

    public void setDataValidazione(LocalDate dataValidazione) {
        this.dataValidazione = dataValidazione;
    }

    public boolean isValidazione() {
        return validazione;
    }

    public void setValidazione(boolean validazione) {
        this.validazione = validazione;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getDurataBiglietto() {
        return durataBiglietto;
    }

    public void setDurataBiglietto(int durataBiglietto) {
        this.durataBiglietto = durataBiglietto;
    }

    public DistributoreAutomatico getDistributore() {
        return distributore;
    }

    public void setDistributore(DistributoreAutomatico distributore) {
        this.distributore = distributore;
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
                ", validazione=" + validazione +
                ", dataValidazione=" + dataValidazione +
                ", mezzo=" + mezzo +
                '}';
    }
}