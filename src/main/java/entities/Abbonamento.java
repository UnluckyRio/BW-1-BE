package entities;

import enums.TipoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Abbonamento")


public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;


    @Column(name = "Data Emissione")
    protected LocalDate dataEmissione;

    @Column(name = "prezzo")
    protected double prezzo;


    @Column(name = "id_rivenditore", nullable = false)
    protected Rivenditore idrivenditore;


    @Column(name = "id_distributore", nullable = false)
    protected DistributoreAutomatico iddistributore;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo abbonamento", nullable = false)
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "Data inizio iscrizione", nullable = false)
    private LocalDate datainiziovalidita;

    @Column(name = "Data scadenza iscrizione", nullable = false)
    private LocalDate datafinevalidita;


    public Abbonamento() {

    }

    public Abbonamento(LocalDate dataEmissione, double prezzo, TipoAbbonamento tipoAbbonamento, LocalDate datainiziovalidita, LocalDate datafinevalidita) {

        this.id = id;
        this.tipoAbbonamento = tipoAbbonamento;
        this.datainiziovalidita = datainiziovalidita;
        this.datafinevalidita = datafinevalidita;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public LocalDate getDataiscrizione() {
        return datainiziovalidita;
    }

    public void setDataiscrizione(LocalDate dataiscrizione) {
        this.datainiziovalidita = dataiscrizione;
    }

    public LocalDate getDatafinevalidita() {
        return datafinevalidita;
    }

    public void setDatafinevalidita(LocalDate datafinevalidita) {
        this.datafinevalidita = datafinevalidita;
    }


    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", prezzo=" + prezzo +
                ", idrivenditore=" + idrivenditore +
                ", iddistributore=" + iddistributore +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", datainiziovalidita=" + datainiziovalidita +
                ", datafinevalidita=" + datafinevalidita +
                '}';
    }
}
