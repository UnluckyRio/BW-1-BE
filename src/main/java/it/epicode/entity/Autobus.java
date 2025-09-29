package it.epicode.entity;

import jakarta.persistence.*;

/**
 * Entità che rappresenta un autobus.
 * Estende Mezzo e aggiunge caratteristiche specifiche degli autobus.
 */
@Entity
@Table(name = "autobus")
@DiscriminatorValue("AUTOBUS")
@PrimaryKeyJoinColumn(name = "id")
public class Autobus extends Mezzo {
    
    @Column(name = "numero_porte")
    private Integer numeroPorte = 2;
    
    @Column(name = "accessibile_disabili")
    private Boolean accessibileDisabili = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "alimentazione")
    private TipoAlimentazione alimentazione = TipoAlimentazione.DIESEL;
    
    // Enum per il tipo di alimentazione
    public enum TipoAlimentazione {
        DIESEL("Diesel"),
        METANO("Metano"),
        ELETTRICO("Elettrico"),
        IBRIDO("Ibrido");
        
        private final String descrizione;
        
        TipoAlimentazione(String descrizione) {
            this.descrizione = descrizione;
        }
        
        public String getDescrizione() {
            return descrizione;
        }
    }
    
    // Costruttori
    public Autobus() {
        super();
    }
    
    public Autobus(String numeroMezzo, Integer capienza) {
        super(numeroMezzo, capienza);
    }
    
    public Autobus(String numeroMezzo, Integer capienza, Integer annoImmatricolazione, String modello) {
        super(numeroMezzo, capienza, annoImmatricolazione, modello);
    }
    
    public Autobus(String numeroMezzo, Integer capienza, Integer numeroPorte, 
                  Boolean accessibileDisabili, TipoAlimentazione alimentazione) {
        super(numeroMezzo, capienza);
        this.numeroPorte = numeroPorte;
        this.accessibileDisabili = accessibileDisabili;
        this.alimentazione = alimentazione;
    }
    
    // Metodi di utilità
    
    /**
     * Verifica se l'autobus è accessibile ai disabili
     */
    public boolean isAccessibileDisabili() {
        return accessibileDisabili != null && accessibileDisabili;
    }
    
    /**
     * Verifica se l'autobus è ecologico (elettrico, ibrido o metano)
     */
    public boolean isEcologico() {
        return alimentazione == TipoAlimentazione.ELETTRICO || 
               alimentazione == TipoAlimentazione.IBRIDO || 
               alimentazione == TipoAlimentazione.METANO;
    }
    
    /**
     * Calcola la capacità per porta
     */
    public double getCapacitaPerPorta() {
        if (numeroPorte == null || numeroPorte == 0) return 0;
        return (double) getCapienza() / numeroPorte;
    }
    
    @Override
    public String getTipoSpecifico() {
        return "Autobus " + alimentazione.getDescrizione();
    }
    
    /**
     * Restituisce una descrizione completa dell'autobus
     */
    public String getDescrizioneCompleta() {
        StringBuilder desc = new StringBuilder();
        desc.append("Autobus ").append(getNumeroMezzo());
        desc.append(" - Capienza: ").append(getCapienza()).append(" posti");
        desc.append(" - Porte: ").append(numeroPorte);
        desc.append(" - Alimentazione: ").append(alimentazione.getDescrizione());
        if (isAccessibileDisabili()) {
            desc.append(" - Accessibile disabili");
        }
        return desc.toString();
    }
    
    // Getter e Setter
    public Integer getNumeroPorte() {
        return numeroPorte;
    }
    
    public void setNumeroPorte(Integer numeroPorte) {
        this.numeroPorte = numeroPorte;
    }
    
    public Boolean getAccessibileDisabili() {
        return accessibileDisabili;
    }
    
    public void setAccessibileDisabili(Boolean accessibileDisabili) {
        this.accessibileDisabili = accessibileDisabili;
    }
    
    public TipoAlimentazione getAlimentazione() {
        return alimentazione;
    }
    
    public void setAlimentazione(TipoAlimentazione alimentazione) {
        this.alimentazione = alimentazione;
    }
    
    @Override
    public String toString() {
        return "Autobus{" +
                "id=" + getId() +
                ", numeroMezzo='" + getNumeroMezzo() + '\'' +
                ", capienza=" + getCapienza() +
                ", numeroPorte=" + numeroPorte +
                ", accessibileDisabili=" + accessibileDisabili +
                ", alimentazione=" + alimentazione +
                ", stato=" + getStato() +
                '}';
    }
}