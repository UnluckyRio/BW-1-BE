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


    @Column(name = "Tipo abbonamento", nullable = false)
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "Data inizio iscrizione", nullable = false)
    private LocalDate datainiziovalidita;

    @Column(name = "Data scadenza iscrizione", nullable = false)
    private LocalDate datafinevalidita;

    @OneToOne
    @JoinColumn(name = "id_validazione")
    private Validazione validazione;


    public Abbonamento() {

    }

    public Abbonamento(LocalDate dataEmissione, double prezzo, TipoAbbonamento tipoAbbonamento, LocalDate datainiziovalidita, LocalDate datafinevalidita, Validazione validazione) {

        this.id = id;
        this.tipoAbbonamento = tipoAbbonamento;
        this.datainiziovalidita = datainiziovalidita;
        this.datafinevalidita = datafinevalidita;
        this.validazione = validazione;
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

    public Validazione getValidazione() {
        return validazione;
    }

    public void setValidazione(Validazione validazione) {
        this.validazione = validazione;
    }
}
