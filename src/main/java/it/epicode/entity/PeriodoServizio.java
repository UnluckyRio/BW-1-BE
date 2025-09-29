package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Entità che rappresenta un periodo di servizio o manutenzione di un mezzo di trasporto.
 * Permette di tenere traccia dello stato operativo dei mezzi nel tempo.
 */
@Entity
@Table(name = "periodi_servizio")
public class PeriodoServizio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_periodo", nullable = false)
    private TipoPeriodo tipoPeriodo;
    
    @Column(name = "data_inizio", nullable = false)
    private LocalDateTime dataInizio;
    
    @Column(name = "data_fine")
    private LocalDateTime dataFine;
    
    @Column(name = "descrizione", length = 500)
    private String descrizione;
    
    @Column(name = "costo_manutenzione", precision = 10, scale = 2)
    private java.math.BigDecimal costoManutenzione;
    
    @Column(name = "km_percorsi")
    private Integer kmPercorsi;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relazione molti-a-uno con Mezzo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;
    
    /**
     * Enum per i tipi di periodo
     */
    public enum TipoPeriodo {
        SERVIZIO("In Servizio"),
        MANUTENZIONE_ORDINARIA("Manutenzione Ordinaria"),
        MANUTENZIONE_STRAORDINARIA("Manutenzione Straordinaria"),
        RIPARAZIONE("Riparazione"),
        REVISIONE("Revisione"),
        FERMO_TECNICO("Fermo Tecnico");
        
        private final String descrizione;
        
        TipoPeriodo(String descrizione) {
            this.descrizione = descrizione;
        }
        
        public String getDescrizione() {
            return descrizione;
        }
        
        public boolean isServizio() {
            return this == SERVIZIO;
        }
        
        public boolean isManutenzione() {
            return this == MANUTENZIONE_ORDINARIA || 
                   this == MANUTENZIONE_STRAORDINARIA || 
                   this == RIPARAZIONE || 
                   this == REVISIONE || 
                   this == FERMO_TECNICO;
        }
    }
    
    // Costruttori
    public PeriodoServizio() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public PeriodoServizio(Mezzo mezzo, TipoPeriodo tipoPeriodo, LocalDateTime dataInizio) {
        this();
        this.mezzo = mezzo;
        this.tipoPeriodo = tipoPeriodo;
        this.dataInizio = dataInizio;
    }
    
    public PeriodoServizio(Mezzo mezzo, TipoPeriodo tipoPeriodo, 
                          LocalDateTime dataInizio, String descrizione) {
        this(mezzo, tipoPeriodo, dataInizio);
        this.descrizione = descrizione;
    }
    
    // Metodi di utilità
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verifica se il periodo è attualmente attivo (non ha data fine)
     */
    public boolean isAttivo() {
        return dataFine == null;
    }
    
    /**
     * Verifica se il periodo è completato
     */
    public boolean isCompletato() {
        return dataFine != null;
    }
    
    /**
     * Termina il periodo impostando la data fine
     */
    public void termina() {
        termina(LocalDateTime.now());
    }
    
    /**
     * Termina il periodo con una data specifica
     */
    public void termina(LocalDateTime dataFine) {
        this.dataFine = dataFine;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Calcola la durata del periodo in giorni
     */
    public Long getDurataGiorni() {
        LocalDateTime fine = dataFine != null ? dataFine : LocalDateTime.now();
        return ChronoUnit.DAYS.between(dataInizio, fine);
    }
    
    /**
     * Calcola la durata del periodo in ore
     */
    public Long getDurataOre() {
        LocalDateTime fine = dataFine != null ? dataFine : LocalDateTime.now();
        return ChronoUnit.HOURS.between(dataInizio, fine);
    }
    
    /**
     * Verifica se il periodo è di servizio
     */
    public boolean isServizio() {
        return tipoPeriodo != null && tipoPeriodo.isServizio();
    }
    
    /**
     * Verifica se il periodo è di manutenzione
     */
    public boolean isManutenzione() {
        return tipoPeriodo != null && tipoPeriodo.isManutenzione();
    }
    
    /**
     * Verifica se il periodo si sovrappone con un altro periodo
     */
    public boolean siSovrappone(PeriodoServizio altro) {
        if (altro == null) return false;
        
        LocalDateTime questoInizio = this.dataInizio;
        LocalDateTime questoFine = this.dataFine != null ? this.dataFine : LocalDateTime.now();
        LocalDateTime altroInizio = altro.dataInizio;
        LocalDateTime altroFine = altro.dataFine != null ? altro.dataFine : LocalDateTime.now();
        
        return questoInizio.isBefore(altroFine) && questoFine.isAfter(altroInizio);
    }
    
    /**
     * Restituisce una descrizione completa del periodo
     */
    public String getDescrizioneCompleta() {
        StringBuilder desc = new StringBuilder();
        desc.append(tipoPeriodo.getDescrizione());
        desc.append(" dal ").append(dataInizio.toLocalDate());
        if (dataFine != null) {
            desc.append(" al ").append(dataFine.toLocalDate());
            desc.append(" (").append(getDurataGiorni()).append(" giorni)");
        } else {
            desc.append(" (in corso - ").append(getDurataGiorni()).append(" giorni)");
        }
        
        if (descrizione != null && !descrizione.trim().isEmpty()) {
            desc.append(" - ").append(descrizione);
        }
        
        if (costoManutenzione != null && isManutenzione()) {
            desc.append(" - Costo: €").append(costoManutenzione);
        }
        
        if (kmPercorsi != null && isServizio()) {
            desc.append(" - Km percorsi: ").append(kmPercorsi);
        }
        
        return desc.toString();
    }
    
    /**
     * Calcola il costo giornaliero (per periodi di manutenzione)
     */
    public java.math.BigDecimal getCostoGiornaliero() {
        if (costoManutenzione == null || !isManutenzione()) return null;
        
        Long giorni = getDurataGiorni();
        if (giorni == null || giorni == 0) return costoManutenzione;
        
        return costoManutenzione.divide(
            java.math.BigDecimal.valueOf(giorni), 
            2, 
            java.math.RoundingMode.HALF_UP
        );
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public TipoPeriodo getTipoPeriodo() {
        return tipoPeriodo;
    }
    
    public void setTipoPeriodo(TipoPeriodo tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }
    
    public LocalDateTime getDataInizio() {
        return dataInizio;
    }
    
    public void setDataInizio(LocalDateTime dataInizio) {
        this.dataInizio = dataInizio;
    }
    
    public LocalDateTime getDataFine() {
        return dataFine;
    }
    
    public void setDataFine(LocalDateTime dataFine) {
        this.dataFine = dataFine;
    }
    
    public String getDescrizione() {
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public java.math.BigDecimal getCostoManutenzione() {
        return costoManutenzione;
    }
    
    public void setCostoManutenzione(java.math.BigDecimal costoManutenzione) {
        this.costoManutenzione = costoManutenzione;
    }
    
    public Integer getKmPercorsi() {
        return kmPercorsi;
    }
    
    public void setKmPercorsi(Integer kmPercorsi) {
        this.kmPercorsi = kmPercorsi;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Mezzo getMezzo() {
        return mezzo;
    }
    
    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }
    
    @Override
    public String toString() {
        return "PeriodoServizio{" +
                "id=" + id +
                ", tipoPeriodo=" + tipoPeriodo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + (mezzo != null ? mezzo.getNumeroMezzo() : null) +
                ", durataGiorni=" + getDurataGiorni() +
                '}';
    }
}