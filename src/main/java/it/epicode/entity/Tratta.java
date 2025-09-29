package it.epicode.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità che rappresenta una tratta del trasporto pubblico.
 * Ogni tratta ha una zona di partenza, un capolinea e un tempo previsto di percorrenza.
 */
@Entity
@Table(name = "tratte")
public class Tratta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "zona_partenza", nullable = false, length = 100)
    private String zonaPartenza;
    
    @Column(name = "capolinea", nullable = false, length = 100)
    private String capolinea;
    
    @Column(name = "tempo_previsto_minuti", nullable = false)
    private Integer tempoPrevisteMinuti;
    
    @Column(name = "distanza_km", precision = 5, scale = 2)
    private BigDecimal distanzaKm;
    
    @Column(name = "attiva")
    private Boolean attiva = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relazione uno-a-molti con PercorrenzaTratta
    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PercorrenzaTratta> percorrenze = new ArrayList<>();
    
    // Relazione uno-a-molti con Vidimazione
    @OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vidimazione> vidimazioni = new ArrayList<>();
    
    // Costruttori
    public Tratta() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Tratta(String nome, String zonaPartenza, String capolinea, Integer tempoPrevisteMinuti) {
        this();
        this.nome = nome;
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevisteMinuti = tempoPrevisteMinuti;
    }
    
    public Tratta(String nome, String zonaPartenza, String capolinea, 
                 Integer tempoPrevisteMinuti, BigDecimal distanzaKm) {
        this(nome, zonaPartenza, capolinea, tempoPrevisteMinuti);
        this.distanzaKm = distanzaKm;
    }
    
    // Metodi di utilità
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verifica se la tratta è attiva
     */
    public boolean isAttiva() {
        return attiva != null && attiva;
    }
    
    /**
     * Attiva la tratta
     */
    public void attiva() {
        this.attiva = true;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Disattiva la tratta
     */
    public void disattiva() {
        this.attiva = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Aggiunge una percorrenza alla tratta
     */
    public void addPercorrenza(PercorrenzaTratta percorrenza) {
        percorrenze.add(percorrenza);
        percorrenza.setTratta(this);
    }
    
    /**
     * Aggiunge una vidimazione alla tratta
     */
    public void addVidimazione(Vidimazione vidimazione) {
        vidimazioni.add(vidimazione);
        vidimazione.setTratta(this);
    }
    
    /**
     * Calcola il tempo medio effettivo di percorrenza
     */
    public Double getTempoMedioEffettivo() {
        List<PercorrenzaTratta> percorrenzeCompletate = percorrenze.stream()
                .filter(PercorrenzaTratta::isCompletata)
                .filter(p -> p.getTempoEffettivoMinuti() != null)
                .toList();
        
        if (percorrenzeCompletate.isEmpty()) {
            return null;
        }
        
        return percorrenzeCompletate.stream()
                .mapToInt(PercorrenzaTratta::getTempoEffettivoMinuti)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Calcola il tempo medio effettivo per un mezzo specifico
     */
    public Double getTempoMedioEffettivo(Mezzo mezzo) {
        List<PercorrenzaTratta> percorrenzeCompletate = percorrenze.stream()
                .filter(p -> p.getMezzo().equals(mezzo))
                .filter(PercorrenzaTratta::isCompletata)
                .filter(p -> p.getTempoEffettivoMinuti() != null)
                .toList();
        
        if (percorrenzeCompletate.isEmpty()) {
            return null;
        }
        
        return percorrenzeCompletate.stream()
                .mapToInt(PercorrenzaTratta::getTempoEffettivoMinuti)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Restituisce il numero di volte che la tratta è stata percorsa
     */
    public long getNumeroPercorrenze() {
        return percorrenze.stream()
                .filter(PercorrenzaTratta::isCompletata)
                .count();
    }
    
    /**
     * Restituisce il numero di volte che un mezzo ha percorso la tratta
     */
    public long getNumeroPercorrenze(Mezzo mezzo) {
        return percorrenze.stream()
                .filter(p -> p.getMezzo().equals(mezzo))
                .filter(PercorrenzaTratta::isCompletata)
                .count();
    }
    
    /**
     * Calcola la velocità media in km/h (se disponibile la distanza)
     */
    public Double getVelocitaMedia() {
        if (distanzaKm == null) return null;
        
        Double tempoMedio = getTempoMedioEffettivo();
        if (tempoMedio == null || tempoMedio == 0) return null;
        
        // Velocità = distanza / tempo (convertendo minuti in ore)
        return distanzaKm.doubleValue() / (tempoMedio / 60.0);
    }
    
    /**
     * Restituisce la descrizione completa della tratta
     */
    public String getDescrizioneCompleta() {
        StringBuilder desc = new StringBuilder();
        desc.append(nome).append(": ");
        desc.append(zonaPartenza).append(" → ").append(capolinea);
        desc.append(" (").append(tempoPrevisteMinuti).append(" min");
        if (distanzaKm != null) {
            desc.append(", ").append(distanzaKm).append(" km");
        }
        desc.append(")");
        return desc.toString();
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getZonaPartenza() {
        return zonaPartenza;
    }
    
    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }
    
    public String getCapolinea() {
        return capolinea;
    }
    
    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }
    
    public Integer getTempoPrevisteMinuti() {
        return tempoPrevisteMinuti;
    }
    
    public void setTempoPrevisteMinuti(Integer tempoPrevisteMinuti) {
        this.tempoPrevisteMinuti = tempoPrevisteMinuti;
    }
    
    public BigDecimal getDistanzaKm() {
        return distanzaKm;
    }
    
    public void setDistanzaKm(BigDecimal distanzaKm) {
        this.distanzaKm = distanzaKm;
    }
    
    public Boolean getAttiva() {
        return attiva;
    }
    
    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
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
    
    public List<PercorrenzaTratta> getPercorrenze() {
        return percorrenze;
    }
    
    public void setPercorrenze(List<PercorrenzaTratta> percorrenze) {
        this.percorrenze = percorrenze;
    }
    
    public List<Vidimazione> getVidimazioni() {
        return vidimazioni;
    }
    
    public void setVidimazioni(List<Vidimazione> vidimazioni) {
        this.vidimazioni = vidimazioni;
    }
    
    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoPrevisteMinuti=" + tempoPrevisteMinuti +
                ", distanzaKm=" + distanzaKm +
                ", attiva=" + attiva +
                '}';
    }
}