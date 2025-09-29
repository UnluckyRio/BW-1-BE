package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità base che rappresenta un mezzo di trasporto pubblico.
 * Viene estesa da Autobus e Tram.
 */
@Entity
@Table(name = "mezzi")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Mezzo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;
    
    @Column(name = "numero_mezzo", unique = true, nullable = false, length = 20)
    private String numeroMezzo;
    
    @Column(name = "capienza", nullable = false)
    private Integer capienza;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "stato")
    private StatoMezzo stato = StatoMezzo.IN_SERVIZIO;
    
    @Column(name = "anno_immatricolazione")
    private Integer annoImmatricolazione;
    
    @Column(name = "modello", length = 100)
    private String modello;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relazione uno-a-molti con Vidimazione
    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vidimazione> vidimazioni = new ArrayList<>();
    
    // Relazione uno-a-molti con PeriodoServizio
    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PeriodoServizio> periodiServizio = new ArrayList<>();
    
    // Relazione uno-a-molti con PercorrenzaTratta
    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PercorrenzaTratta> percorrenze = new ArrayList<>();
    
    // Enum per lo stato del mezzo
    public enum StatoMezzo {
        IN_SERVIZIO, IN_MANUTENZIONE
    }
    
    // Costruttori
    public Mezzo() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Mezzo(String numeroMezzo, Integer capienza) {
        this();
        this.numeroMezzo = numeroMezzo;
        this.capienza = capienza;
    }
    
    public Mezzo(String numeroMezzo, Integer capienza, Integer annoImmatricolazione, String modello) {
        this(numeroMezzo, capienza);
        this.annoImmatricolazione = annoImmatricolazione;
        this.modello = modello;
    }
    
    // Metodi di utilità
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verifica se il mezzo è in servizio
     */
    public boolean isInServizio() {
        return stato == StatoMezzo.IN_SERVIZIO;
    }
    
    /**
     * Verifica se il mezzo è in manutenzione
     */
    public boolean isInManutenzione() {
        return stato == StatoMezzo.IN_MANUTENZIONE;
    }
    
    /**
     * Mette il mezzo in manutenzione
     */
    public void mettiInManutenzione() {
        this.stato = StatoMezzo.IN_MANUTENZIONE;
        this.updatedAt = LocalDateTime.now();
        
        // Crea un nuovo periodo di manutenzione
        PeriodoServizio periodoManutenzione = new PeriodoServizio(this, PeriodoServizio.TipoPeriodo.MANUTENZIONE);
        addPeriodoServizio(periodoManutenzione);
    }
    
    /**
     * Rimette il mezzo in servizio
     */
    public void rimettiInServizio() {
        this.stato = StatoMezzo.IN_SERVIZIO;
        this.updatedAt = LocalDateTime.now();
        
        // Chiude l'eventuale periodo di manutenzione in corso
        PeriodoServizio periodoManutenzioneAttivo = getPeriodoManutenzioneAttivo();
        if (periodoManutenzioneAttivo != null) {
            periodoManutenzioneAttivo.termina();
        }
        
        // Crea un nuovo periodo di servizio
        PeriodoServizio periodoServizio = new PeriodoServizio(this, PeriodoServizio.TipoPeriodo.SERVIZIO);
        addPeriodoServizio(periodoServizio);
    }
    
    /**
     * Aggiunge un periodo di servizio/manutenzione
     */
    public void addPeriodoServizio(PeriodoServizio periodo) {
        periodiServizio.add(periodo);
        periodo.setMezzo(this);
    }
    
    /**
     * Aggiunge una vidimazione
     */
    public void addVidimazione(Vidimazione vidimazione) {
        vidimazioni.add(vidimazione);
        vidimazione.setMezzo(this);
    }
    
    /**
     * Aggiunge una percorrenza di tratta
     */
    public void addPercorrenza(PercorrenzaTratta percorrenza) {
        percorrenze.add(percorrenza);
        percorrenza.setMezzo(this);
    }
    
    /**
     * Restituisce il periodo di manutenzione attivo (se presente)
     */
    public PeriodoServizio getPeriodoManutenzioneAttivo() {
        return periodiServizio.stream()
                .filter(p -> p.getTipoPeriodo() == PeriodoServizio.TipoPeriodo.MANUTENZIONE && p.isAttivo())
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Restituisce il numero di vidimazioni in un periodo
     */
    public long getNumeroVidimazioni(LocalDateTime dataInizio, LocalDateTime dataFine) {
        return vidimazioni.stream()
                .filter(v -> !v.getDataVidimazione().isBefore(dataInizio) && 
                           !v.getDataVidimazione().isAfter(dataFine))
                .count();
    }
    
    /**
     * Restituisce il numero totale di vidimazioni
     */
    public int getNumeroTotaleVidimazioni() {
        return vidimazioni.size();
    }
    
    // Metodo astratto per il tipo specifico di mezzo
    public abstract String getTipoSpecifico();
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getNumeroMezzo() {
        return numeroMezzo;
    }
    
    public void setNumeroMezzo(String numeroMezzo) {
        this.numeroMezzo = numeroMezzo;
    }
    
    public Integer getCapienza() {
        return capienza;
    }
    
    public void setCapienza(Integer capienza) {
        this.capienza = capienza;
    }
    
    public StatoMezzo getStato() {
        return stato;
    }
    
    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }
    
    public Integer getAnnoImmatricolazione() {
        return annoImmatricolazione;
    }
    
    public void setAnnoImmatricolazione(Integer annoImmatricolazione) {
        this.annoImmatricolazione = annoImmatricolazione;
    }
    
    public String getModello() {
        return modello;
    }
    
    public void setModello(String modello) {
        this.modello = modello;
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
    
    public List<Vidimazione> getVidimazioni() {
        return vidimazioni;
    }
    
    public void setVidimazioni(List<Vidimazione> vidimazioni) {
        this.vidimazioni = vidimazioni;
    }
    
    public List<PeriodoServizio> getPeriodiServizio() {
        return periodiServizio;
    }
    
    public void setPeriodiServizio(List<PeriodoServizio> periodiServizio) {
        this.periodiServizio = periodiServizio;
    }
    
    public List<PercorrenzaTratta> getPercorrenze() {
        return percorrenze;
    }
    
    public void setPercorrenze(List<PercorrenzaTratta> percorrenze) {
        this.percorrenze = percorrenze;
    }
    
    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", numeroMezzo='" + numeroMezzo + '\'' +
                ", capienza=" + capienza +
                ", stato=" + stato +
                ", modello='" + modello + '\'' +
                '}';
    }
}