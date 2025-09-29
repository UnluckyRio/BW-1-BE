package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Entità che rappresenta la percorrenza di una tratta da parte di un mezzo.
 * Tiene traccia del tempo effettivo di percorrenza e permette di calcolare statistiche.
 */
@Entity
@Table(name = "percorrenze_tratte")
public class PercorrenzaTratta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_inizio", nullable = false)
    private LocalDateTime dataInizio;
    
    @Column(name = "data_fine")
    private LocalDateTime dataFine;
    
    @Column(name = "tempo_effettivo_minuti")
    private Integer tempoEffettivoMinuti;
    
    @Column(name = "ritardo_minuti")
    private Integer ritardoMinuti;
    
    @Column(name = "completata")
    private Boolean completata = false;
    
    @Column(name = "note", length = 500)
    private String note;
    
    @Column(name = "passeggeri_saliti")
    private Integer passeggeriSaliti;
    
    @Column(name = "passeggeri_scesi")
    private Integer passeggeriScesi;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relazione molti-a-uno con Mezzo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;
    
    // Relazione molti-a-uno con Tratta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tratta_id", nullable = false)
    private Tratta tratta;
    
    // Costruttori
    public PercorrenzaTratta() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public PercorrenzaTratta(Mezzo mezzo, Tratta tratta) {
        this();
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.dataInizio = LocalDateTime.now();
    }
    
    public PercorrenzaTratta(Mezzo mezzo, Tratta tratta, LocalDateTime dataInizio) {
        this();
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.dataInizio = dataInizio;
    }
    
    // Metodi di utilità
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Completa la percorrenza impostando la data fine e calcolando i tempi
     */
    public void completa() {
        completa(LocalDateTime.now());
    }
    
    /**
     * Completa la percorrenza con una data specifica
     */
    public void completa(LocalDateTime dataFine) {
        this.dataFine = dataFine;
        this.completata = true;
        
        // Calcola il tempo effettivo in minuti
        if (dataInizio != null) {
            this.tempoEffettivoMinuti = (int) ChronoUnit.MINUTES.between(dataInizio, dataFine);
            
            // Calcola il ritardo rispetto al tempo previsto
            if (tratta != null && tratta.getTempoPrevisteMinuti() != null) {
                this.ritardoMinuti = this.tempoEffettivoMinuti - tratta.getTempoPrevisteMinuti();
            }
        }
        
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verifica se la percorrenza è completata
     */
    public boolean isCompletata() {
        return completata != null && completata;
    }
    
    /**
     * Verifica se la percorrenza è in corso
     */
    public boolean isInCorso() {
        return !isCompletata() && dataInizio != null;
    }
    
    /**
     * Verifica se c'è stato ritardo
     */
    public boolean isInRitardo() {
        return ritardoMinuti != null && ritardoMinuti > 0;
    }
    
    /**
     * Verifica se è stata completata in anticipo
     */
    public boolean isInAnticipo() {
        return ritardoMinuti != null && ritardoMinuti < 0;
    }
    
    /**
     * Verifica se è stata completata in orario (±2 minuti di tolleranza)
     */
    public boolean isInOrario() {
        return ritardoMinuti != null && Math.abs(ritardoMinuti) <= 2;
    }
    
    /**
     * Calcola la durata attuale della percorrenza
     */
    public Long getDurataAttualeMinuti() {
        if (dataInizio == null) return null;
        
        LocalDateTime fine = dataFine != null ? dataFine : LocalDateTime.now();
        return ChronoUnit.MINUTES.between(dataInizio, fine);
    }
    
    /**
     * Calcola la percentuale di puntualità rispetto al tempo previsto
     */
    public Double getPercentualePuntualita() {
        if (tempoEffettivoMinuti == null || tratta == null || 
            tratta.getTempoPrevisteMinuti() == null) {
            return null;
        }
        
        double tempoPrevisto = tratta.getTempoPrevisteMinuti().doubleValue();
        if (tempoPrevisto == 0) return null;
        
        return (tempoPrevisto / tempoEffettivoMinuti) * 100.0;
    }
    
    /**
     * Aggiorna il numero di passeggeri saliti
     */
    public void aggiungiPasseggeriSaliti(int numero) {
        if (passeggeriSaliti == null) {
            passeggeriSaliti = numero;
        } else {
            passeggeriSaliti += numero;
        }
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Aggiorna il numero di passeggeri scesi
     */
    public void aggiungiPasseggeriScesi(int numero) {
        if (passeggeriScesi == null) {
            passeggeriScesi = numero;
        } else {
            passeggeriScesi += numero;
        }
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Calcola il saldo passeggeri (saliti - scesi)
     */
    public Integer getSaldoPasseggeri() {
        if (passeggeriSaliti == null && passeggeriScesi == null) return null;
        
        int saliti = passeggeriSaliti != null ? passeggeriSaliti : 0;
        int scesi = passeggeriScesi != null ? passeggeriScesi : 0;
        
        return saliti - scesi;
    }
    
    /**
     * Restituisce una descrizione completa della percorrenza
     */
    public String getDescrizioneCompleta() {
        StringBuilder desc = new StringBuilder();
        
        if (mezzo != null) {
            desc.append(mezzo.getTipo()).append(" ").append(mezzo.getNumeroMezzo());
        }
        
        if (tratta != null) {
            desc.append(" - Tratta: ").append(tratta.getNome());
        }
        
        desc.append(" - Inizio: ").append(dataInizio.toLocalDate())
            .append(" ").append(dataInizio.toLocalTime());
        
        if (isCompletata()) {
            desc.append(" - Fine: ").append(dataFine.toLocalDate())
                .append(" ").append(dataFine.toLocalTime());
            desc.append(" - Durata: ").append(tempoEffettivoMinuti).append(" min");
            
            if (ritardoMinuti != null) {
                if (isInRitardo()) {
                    desc.append(" (Ritardo: +").append(ritardoMinuti).append(" min)");
                } else if (isInAnticipo()) {
                    desc.append(" (Anticipo: ").append(Math.abs(ritardoMinuti)).append(" min)");
                } else {
                    desc.append(" (In orario)");
                }
            }
        } else {
            desc.append(" - IN CORSO (").append(getDurataAttualeMinuti()).append(" min)");
        }
        
        if (passeggeriSaliti != null || passeggeriScesi != null) {
            desc.append(" - Passeggeri: ");
            if (passeggeriSaliti != null) desc.append("saliti ").append(passeggeriSaliti);
            if (passeggeriScesi != null) desc.append(" scesi ").append(passeggeriScesi);
        }
        
        return desc.toString();
    }
    
    /**
     * Restituisce lo stato della percorrenza
     */
    public String getStato() {
        if (isCompletata()) {
            if (isInOrario()) return "COMPLETATA IN ORARIO";
            if (isInRitardo()) return "COMPLETATA IN RITARDO";
            if (isInAnticipo()) return "COMPLETATA IN ANTICIPO";
            return "COMPLETATA";
        } else if (isInCorso()) {
            return "IN CORSO";
        } else {
            return "NON INIZIATA";
        }
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public Integer getTempoEffettivoMinuti() {
        return tempoEffettivoMinuti;
    }
    
    public void setTempoEffettivoMinuti(Integer tempoEffettivoMinuti) {
        this.tempoEffettivoMinuti = tempoEffettivoMinuti;
    }
    
    public Integer getRitardoMinuti() {
        return ritardoMinuti;
    }
    
    public void setRitardoMinuti(Integer ritardoMinuti) {
        this.ritardoMinuti = ritardoMinuti;
    }
    
    public Boolean getCompletata() {
        return completata;
    }
    
    public void setCompletata(Boolean completata) {
        this.completata = completata;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public Integer getPasseggeriSaliti() {
        return passeggeriSaliti;
    }
    
    public void setPasseggeriSaliti(Integer passeggeriSaliti) {
        this.passeggeriSaliti = passeggeriSaliti;
    }
    
    public Integer getPasseggeriScesi() {
        return passeggeriScesi;
    }
    
    public void setPasseggeriScesi(Integer passeggeriScesi) {
        this.passeggeriScesi = passeggeriScesi;
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
    
    public Tratta getTratta() {
        return tratta;
    }
    
    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }
    
    @Override
    public String toString() {
        return "PercorrenzaTratta{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", tempoEffettivoMinuti=" + tempoEffettivoMinuti +
                ", ritardoMinuti=" + ritardoMinuti +
                ", completata=" + completata +
                ", mezzo=" + (mezzo != null ? mezzo.getNumeroMezzo() : null) +
                ", tratta=" + (tratta != null ? tratta.getNome() : null) +
                '}';
    }
}