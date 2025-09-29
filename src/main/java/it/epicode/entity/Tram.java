package it.epicode.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entità che rappresenta un tram.
 * Estende Mezzo e aggiunge caratteristiche specifiche dei tram.
 */
@Entity
@Table(name = "tram")
@DiscriminatorValue("TRAM")
@PrimaryKeyJoinColumn(name = "id")
public class Tram extends Mezzo {
    
    @Column(name = "numero_carrozze")
    private Integer numeroCarrozze = 1;
    
    @Column(name = "lunghezza_metri", precision = 4, scale = 2)
    private BigDecimal lunghezzaMetri;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alimentazione")
    private TipoAlimentazioneTram tipoAlimentazione = TipoAlimentazioneTram.ELETTRICO;
    
    // Enum per il tipo di alimentazione del tram
    public enum TipoAlimentazioneTram {
        ELETTRICO("Elettrico - Linea aerea"),
        ELETTRICO_BATTERIA("Elettrico - Batteria"),
        IBRIDO("Ibrido elettrico-diesel");
        
        private final String descrizione;
        
        TipoAlimentazioneTram(String descrizione) {
            this.descrizione = descrizione;
        }
        
        public String getDescrizione() {
            return descrizione;
        }
    }
    
    // Costruttori
    public Tram() {
        super();
    }
    
    public Tram(String numeroMezzo, Integer capienza) {
        super(numeroMezzo, capienza);
    }
    
    public Tram(String numeroMezzo, Integer capienza, Integer annoImmatricolazione, String modello) {
        super(numeroMezzo, capienza, annoImmatricolazione, modello);
    }
    
    public Tram(String numeroMezzo, Integer capienza, Integer numeroCarrozze, 
               BigDecimal lunghezzaMetri, TipoAlimentazioneTram tipoAlimentazione) {
        super(numeroMezzo, capienza);
        this.numeroCarrozze = numeroCarrozze;
        this.lunghezzaMetri = lunghezzaMetri;
        this.tipoAlimentazione = tipoAlimentazione;
    }
    
    // Metodi di utilità
    
    /**
     * Calcola la capacità per carrozza
     */
    public double getCapacitaPerCarrozza() {
        if (numeroCarrozze == null || numeroCarrozze == 0) return 0;
        return (double) getCapienza() / numeroCarrozze;
    }
    
    /**
     * Calcola la densità di passeggeri per metro (passeggeri/metro)
     */
    public double getDensitaPasseggeri() {
        if (lunghezzaMetri == null || lunghezzaMetri.compareTo(BigDecimal.ZERO) == 0) return 0;
        return getCapienza() / lunghezzaMetri.doubleValue();
    }
    
    /**
     * Verifica se il tram è articolato (più di una carrozza)
     */
    public boolean isArticolato() {
        return numeroCarrozze != null && numeroCarrozze > 1;
    }
    
    /**
     * Verifica se il tram è completamente elettrico
     */
    public boolean isElettrico() {
        return tipoAlimentazione == TipoAlimentazioneTram.ELETTRICO || 
               tipoAlimentazione == TipoAlimentazioneTram.ELETTRICO_BATTERIA;
    }
    
    @Override
    public String getTipoSpecifico() {
        return "Tram " + tipoAlimentazione.getDescrizione();
    }
    
    /**
     * Restituisce una descrizione completa del tram
     */
    public String getDescrizioneCompleta() {
        StringBuilder desc = new StringBuilder();
        desc.append("Tram ").append(getNumeroMezzo());
        desc.append(" - Capienza: ").append(getCapienza()).append(" posti");
        desc.append(" - Carrozze: ").append(numeroCarrozze);
        if (lunghezzaMetri != null) {
            desc.append(" - Lunghezza: ").append(lunghezzaMetri).append("m");
        }
        desc.append(" - Alimentazione: ").append(tipoAlimentazione.getDescrizione());
        if (isArticolato()) {
            desc.append(" - Articolato");
        }
        return desc.toString();
    }
    
    /**
     * Restituisce la categoria del tram basata sulla lunghezza
     */
    public String getCategoria() {
        if (lunghezzaMetri == null) return "Non specificata";
        
        double lunghezza = lunghezzaMetri.doubleValue();
        if (lunghezza < 20) return "Tram corto";
        else if (lunghezza < 35) return "Tram standard";
        else if (lunghezza < 50) return "Tram lungo";
        else return "Tram extra-lungo";
    }
    
    // Getter e Setter
    public Integer getNumeroCarrozze() {
        return numeroCarrozze;
    }
    
    public void setNumeroCarrozze(Integer numeroCarrozze) {
        this.numeroCarrozze = numeroCarrozze;
    }
    
    public BigDecimal getLunghezzaMetri() {
        return lunghezzaMetri;
    }
    
    public void setLunghezzaMetri(BigDecimal lunghezzaMetri) {
        this.lunghezzaMetri = lunghezzaMetri;
    }
    
    public TipoAlimentazioneTram getTipoAlimentazione() {
        return tipoAlimentazione;
    }
    
    public void setTipoAlimentazione(TipoAlimentazioneTram tipoAlimentazione) {
        this.tipoAlimentazione = tipoAlimentazione;
    }
    
    @Override
    public String toString() {
        return "Tram{" +
                "id=" + getId() +
                ", numeroMezzo='" + getNumeroMezzo() + '\'' +
                ", capienza=" + getCapienza() +
                ", numeroCarrozze=" + numeroCarrozze +
                ", lunghezzaMetri=" + lunghezzaMetri +
                ", tipoAlimentazione=" + tipoAlimentazione +
                ", stato=" + getStato() +
                '}';
    }
}