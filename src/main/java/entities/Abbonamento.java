package entities;

import enums.TipoAbbonamento;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "abbonamento")
public class Abbonamento extends TitoloViaggio {

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_abbonamento", nullable = false)
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "prezzo", nullable = false)
    private Double prezzo;

    @Column(name = "attivo", nullable = false)
    private boolean attivo;

    @Column(name = "zona_validita", nullable = false)
    private String zonaValidita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tessera", nullable = false)
    private Tessera tessera;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distributore_automatico")
    private DistributoreAutomatico distributoreAutomatico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rivenditore")
    private Rivenditore rivenditore;

    public Abbonamento() {
        super();
    }

    public Abbonamento(LocalDate dataEmissione, LocalDate dataScadenza, TipoAbbonamento tipoAbbonamento, 
                      Double prezzo, String zonaValidita, Tessera tessera) {
        super();
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
        this.tipoAbbonamento = tipoAbbonamento;
        this.prezzo = prezzo;
        this.zonaValidita = zonaValidita;
        this.tessera = tessera;
        this.attivo = true;
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

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public String getZonaValidita() {
        return zonaValidita;
    }

    public void setZonaValidita(String zonaValidita) {
        this.zonaValidita = zonaValidita;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
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
        return "Abbonamento{" +
                "id=" + getId() +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", prezzo=" + prezzo +
                ", attivo=" + attivo +
                ", zonaValidita='" + zonaValidita + '\'' +
                ", tessera=" + tessera +
                '}';
    }
}
