package entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Biglietto")


public class Biglietto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "durata validazione", nullable = false)
    private int durataValidazione;
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


    public Biglietto() {

    }


    public Biglietto(double prezzo, int durataValidazione, LocalDate dataEmissione) {

        this.id = id;
        this.durataValidazione = durataValidazione;
        this.prezzo = prezzo;
        this.dataEmissione = dataEmissione;


    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDurataValidazione() {
        return durataValidazione;
    }

    public void setDurataValidazione(int durataValidazione) {
        this.durataValidazione = durataValidazione;
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
                "prezzo=" + prezzo +
                ", id=" + id +
                ", durataValidazione=" + durataValidazione +
                ", dataEmissione=" + dataEmissione +
                ", distributore=" + distributore +
                ", rivenditore=" + rivenditore +
                '}';
    }
}
