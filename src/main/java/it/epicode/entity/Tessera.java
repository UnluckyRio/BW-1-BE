package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità che rappresenta una tessera per abbonamenti.
 * Ogni tessera ha validità annuale e appartiene ad un utente.
 */
@Entity
@Table(name = "tessere")
public class Tessera {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_tessera", unique = true, nullable = false, length = 20)
    private String numeroTessera;
    
    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;
    
    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;
    
    @Column(name = "attiva")
    private Boolean attiva = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relazione molti-a-uno con Utente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;
    
    // Relazione uno-a-molti con Abbonamento
    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Abbonamento> abbonamenti = new ArrayList<>();
    
    // Costruttori
    public Tessera() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Tessera(String numeroTessera, Utente utente) {
        this();
        this.numeroTessera = numeroTessera;
        this.utente = utente;
        this.dataEmissione = LocalDate.now();
        this.dataScadenza = LocalDate.now().plusYears(1); // Validità annuale
    }
    
    public Tessera(String numeroTessera, Utente utente, LocalDate dataEmissione) {
        this();
        this.numeroTessera = numeroTessera;
        this.utente = utente;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusYears(1); // Validità annuale
    }
    
    // Metodi di utilità
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verifica se la tessera è valida (attiva e non scaduta)
     */
    public boolean isValida() {
        return attiva && LocalDate.now().isBefore(dataScadenza.plusDays(1));
    }
    
    /**
     * Verifica se la tessera è scaduta
     */
    public boolean isScaduta() {
        return LocalDate.now().isAfter(dataScadenza);
    }
    
    /**
     * Rinnova la tessera per un altro anno
     */
    public void rinnova() {
        this.dataScadenza = LocalDate.now().plusYears(1);
        this.attiva = true;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Disattiva la tessera
     */
    public void disattiva() {
        this.attiva = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Aggiunge un abbonamento alla tessera
     */
    public void addAbbonamento(Abbonamento abbonamento) {
        abbonamenti.add(abbonamento);
        abbonamento.setTessera(this);
    }
    
    /**
     * Rimuove un abbonamento dalla tessera
     */
    public void removeAbbonamento(Abbonamento abbonamento) {
        abbonamenti.remove(abbonamento);
        abbonamento.setTessera(null);
    }
    
    /**
     * Restituisce l'abbonamento attivo (se presente)
     */
    public Abbonamento getAbbonamentoAttivo() {
        return abbonamenti.stream()
                .filter(Abbonamento::isValido)
                .findFirst()
                .orElse(null);
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroTessera() {
        return numeroTessera;
    }
    
    public void setNumeroTessera(String numeroTessera) {
        this.numeroTessera = numeroTessera;
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
    
    public Utente getUtente() {
        return utente;
    }
    
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
    public List<Abbonamento> getAbbonamenti() {
        return abbonamenti;
    }
    
    public void setAbbonamenti(List<Abbonamento> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }
    
    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", numeroTessera='" + numeroTessera + '\'' +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", attiva=" + attiva +
                ", utente=" + (utente != null ? utente.getNomeCompleto() : "null") +
                '}';
    }
}