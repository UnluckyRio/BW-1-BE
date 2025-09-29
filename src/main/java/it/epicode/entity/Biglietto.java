package it.epicode.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità che rappresenta un biglietto singolo per il trasporto pubblico.
 * Ogni biglietto ha un codice univoco e può essere vidimato una sola volta.
 */
@Entity
@Table(name = "biglietti")
public class Biglietto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codice_biglietto", unique = true, nullable = false, length = 30)
    private String codiceBiglietto;
    
    @Column(name = "data_emissione", nullable = false)
    private LocalDateTime dataEmissione;
    
    @Column(name = "data_scadenza", nullable = false)
    private LocalDateTime dataScadenza;
    
    @Column(name = "prezzo", nullable = false, precision = 5, scale = 2)
    private BigDecimal prezzo;
    
    @Column(name = "validato")
    private Boolean validato = false;
    
    @Column(name = "data_validazione")
    private LocalDateTime dataValidazione;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Relazione molti-a-uno con PuntoEmissione
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punto_emissione_id", nullable = false)
    private PuntoEmissione puntoEmissione;
    
    // Relazione uno-a-molti con Vidimazione
    @OneToMany(mappedBy = "biglietto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vidimazione> vidimazioni = new ArrayList<>();
    
    // Costruttori
    public Biglietto() {
        this.createdAt = LocalDateTime.now();
        this.dataEmissione = LocalDateTime.now();
        // Biglietto valido per 90 minuti dall'emissione
        this.dataScadenza = this.dataEmissione.plusMinutes(90);
    }
    
    public Biglietto(String codiceBiglietto, BigDecimal prezzo, PuntoEmissione puntoEmissione) {
        this();
        this.codiceBiglietto = codiceBiglietto;
        this.prezzo = prezzo;
        this.puntoEmissione = puntoEmissione;
    }
    
    public Biglietto(String codiceBiglietto, BigDecimal prezzo, PuntoEmissione puntoEmissione, 
                    LocalDateTime dataEmissione) {
        this();
        this.codiceBiglietto = codiceBiglietto;
        this.prezzo = prezzo;
        this.puntoEmissione = puntoEmissione;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusMinutes(90);
    }
    
    // Metodi di utilità
    
    /**
     * Verifica se il biglietto è valido (non scaduto e non ancora validato)
     */
    public boolean isValido() {
        return !validato && LocalDateTime.now().isBefore(dataScadenza);
    }
    
    /**
     * Verifica se il biglietto è scaduto
     */
    public boolean isScaduto() {
        return LocalDateTime.now().isAfter(dataScadenza);
    }
    
    /**
     * Valida il biglietto su un mezzo
     */
    public boolean valida(Mezzo mezzo) {
        if (!isValido()) {
            return false;
        }
        
        this.validato = true;
        this.dataValidazione = LocalDateTime.now();
        
        // Crea una vidimazione
        Vidimazione vidimazione = new Vidimazione(this, mezzo);
        addVidimazione(vidimazione);
        
        return true;
    }
    
    /**
     * Aggiunge una vidimazione al biglietto
     */
    public void addVidimazione(Vidimazione vidimazione) {
        vidimazioni.add(vidimazione);
        vidimazione.setBiglietto(this);
    }
    
    /**
     * Restituisce la prima vidimazione (dovrebbe essere l'unica)
     */
    public Vidimazione getVidimazione() {
        return vidimazioni.isEmpty() ? null : vidimazioni.get(0);
    }
    
    /**
     * Restituisce il tempo rimanente prima della scadenza in minuti
     */
    public long getMinutiRimanenti() {
        if (isScaduto()) return 0;
        return java.time.Duration.between(LocalDateTime.now(), dataScadenza).toMinutes();
    }
    
    /**
     * Genera un codice biglietto univoco
     */
    public static String generaCodiceBiglietto() {
        return "BIG" + System.currentTimeMillis() + 
               String.format("%03d", (int)(Math.random() * 1000));
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodiceBiglietto() {
        return codiceBiglietto;
    }
    
    public void setCodiceBiglietto(String codiceBiglietto) {
        this.codiceBiglietto = codiceBiglietto;
    }
    
    public LocalDateTime getDataEmissione() {
        return dataEmissione;
    }
    
    public void setDataEmissione(LocalDateTime dataEmissione) {
        this.dataEmissione = dataEmissione;
    }
    
    public LocalDateTime getDataScadenza() {
        return dataScadenza;
    }
    
    public void setDataScadenza(LocalDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
    
    public BigDecimal getPrezzo() {
        return prezzo;
    }
    
    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
    
    public Boolean getValidato() {
        return validato;
    }
    
    public void setValidato(Boolean validato) {
        this.validato = validato;
    }
    
    public LocalDateTime getDataValidazione() {
        return dataValidazione;
    }
    
    public void setDataValidazione(LocalDateTime dataValidazione) {
        this.dataValidazione = dataValidazione;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public PuntoEmissione getPuntoEmissione() {
        return puntoEmissione;
    }
    
    public void setPuntoEmissione(PuntoEmissione puntoEmissione) {
        this.puntoEmissione = puntoEmissione;
    }
    
    public List<Vidimazione> getVidimazioni() {
        return vidimazioni;
    }
    
    public void setVidimazioni(List<Vidimazione> vidimazioni) {
        this.vidimazioni = vidimazioni;
    }
    
    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", codiceBiglietto='" + codiceBiglietto + '\'' +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", prezzo=" + prezzo +
                ", validato=" + validato +
                ", dataValidazione=" + dataValidazione +
                '}';
    }
}