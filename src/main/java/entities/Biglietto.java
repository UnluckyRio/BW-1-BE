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
    @JoinColumn(name = "id_punto_emissione")
    private PuntoDiEmissione puntoEmissione;

    @Column(name= "Data validazione")
    private LocalDate dataValidazione;

    @ManyToOne
    @JoinColumn(name = "id_mezzo_validante")
    private Mezzo mezzoValidante;

    public Biglietto() {
    }

    public Biglietto(int durataBiglietto, double prezzo, LocalDate dataEmissione, PuntoDiEmissione puntoEmissione, LocalDate dataValidazione, Mezzo mezzoValidante) {
        this.prezzo = prezzo;
        this.durataBiglietto = durataBiglietto;
        this.dataEmissione = dataEmissione;
        this.puntoEmissione = puntoEmissione;
        this.dataValidazione = dataValidazione;
        this.mezzoValidante = mezzoValidante;
    }

    public long getId() {
        return id;
    }

    public int getDurataBiglietto() {
        return durataBiglietto;
    }

    public void setDurataBiglietto(int durataBiglietto) {
        this.durataBiglietto = durataBiglietto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public PuntoDiEmissione getPuntoEmissione() {
        return puntoEmissione;
    }

    public void setPuntoEmissione(PuntoDiEmissione puntoEmissione) {
        this.puntoEmissione = puntoEmissione;
    }

    public LocalDate getDataValidazione() {
        return dataValidazione;
    }

    public void setDataValidazione(LocalDate dataValidazione) {
        this.dataValidazione = dataValidazione;
    }

    public Mezzo getMezzoValidante() {
        return mezzoValidante;
    }

    public void setMezzoValidante(Mezzo mezzoValidante) {
        this.mezzoValidante = mezzoValidante;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", durataBiglietto=" + durataBiglietto +
                ", prezzo=" + prezzo +
                ", dataEmissione=" + dataEmissione +
                ", puntoEmissione=" + puntoEmissione +
                ", dataValidazione=" + dataValidazione +
                ", mezzoValidante=" + mezzoValidante +
                '}';
    }
}
