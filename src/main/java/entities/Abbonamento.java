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


    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo abbonamento", nullable = false)
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "Data inizio validità", nullable = false)
    private LocalDate datainiziovalidita;

    @Column(name = "Data scadenza validità", nullable = false)
    private LocalDate datafinevalidita;


    @Column(name = "Data Emissione")
    private LocalDate dataEmissione;


    @Column(name = "prezzo")
    private double prezzo;

    @ManyToOne
    @JoinColumn(name = "Id_tessera")
    private Tessera tessera;

    @OneToOne
    @JoinColumn(name = "id_rivenditore", nullable = false)
    private Rivenditore rivenditore;

    @OneToOne
    @JoinColumn(name = "id_distributore", nullable = false)
    private DistributoreAutomatico distributore;


    public Abbonamento() {

    }

    public Abbonamento(TipoAbbonamento tipoAbbonamento, LocalDate datainiziovalidita, LocalDate datafinevalidita, LocalDate dataEmissione, double prezzo) {

        this.id = id;
        this.tipoAbbonamento = tipoAbbonamento;
        this.datainiziovalidita = datainiziovalidita;
        this.datafinevalidita = datafinevalidita;
        this.dataEmissione = dataEmissione;
        this.prezzo = prezzo;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public LocalDate getDatainiziovalidita() {
        return datainiziovalidita;
    }

    public void setDatainiziovalidita(LocalDate datainiziovalidita) {
        this.datainiziovalidita = datainiziovalidita;
    }

    public LocalDate getDatafinevalidita() {
        return datafinevalidita;
    }

    public void setDatafinevalidita(LocalDate datafinevalidita) {
        this.datafinevalidita = datafinevalidita;
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

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    public DistributoreAutomatico getDistributore() {
        return distributore;
    }

    public void setDistributore(DistributoreAutomatico distributore) {
        this.distributore = distributore;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", datainiziovalidita=" + datainiziovalidita +
                ", datafinevalidita=" + datafinevalidita +
                ", dataEmissione=" + dataEmissione +
                ", prezzo=" + prezzo +
                ", tessera=" + tessera +
                ", rivenditore=" + rivenditore +
                ", distributore=" + distributore +
                '}';
    }
}
