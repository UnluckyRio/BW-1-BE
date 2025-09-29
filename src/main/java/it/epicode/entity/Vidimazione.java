package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entità che rappresenta la vidimazione di un biglietto su un mezzo di trasporto.
 * Quando un biglietto viene vidimato, viene annullato e registrata la vidimazione.
 */
@Entity
@Table(name = "vidimazioni")
public class Vidimazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "data_vidimazione", nullable = false)
    private LocalDateTime dataVidimazione;
    
    @Column(name = "valida", nullable = false)
    private Boolean valida = true;
    
    @Column(name = "note", length = 500)
    private String note;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Relazione molti-a-uno con Biglietto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biglietto_id", nullable = false)
    private Biglietto biglietto;
    
    // Relazione molti-a-uno con Mezzo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;
    
    // Relazione molti-a-uno con Tratta (opzionale)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;
    
    // Costruttori
    public Vidimazione() {
        this.dataVidimazione = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
    
    public Vidimazione(Biglietto biglietto, Mezzo mezzo) {
        this();
        this.biglietto = biglietto;
        this.mezzo = mezzo;
    }
    
    public Vidimazione(Biglietto biglietto, Mezzo mezzo, Tratta tratta) {
        this(biglietto, mezzo);
        this.tratta = tratta;
    }
    
    // Metodi di utilità
    
    /**
     * Verifica se la vidimazione è valida
     */
    public boolean isValida() {
        return valida != null && valida;
    }
    
    /**
     * Invalida la vidimazione
     */
    public void invalida(String motivo) {
        this.valida = false;
        this.note = motivo;
    }
    
    /**
     * Verifica se la vidimazione è avvenuta oggi
     */
    public boolean isOggi() {
        return dataVidimazione.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }
    
    /**
     * Verifica se la vidimazione è avvenuta in un periodo specifico
     */
    public boolean isInPeriodo(LocalDateTime inizio, LocalDateTime fine) {
        return !dataVidimazione.isBefore(inizio) && !dataVidimazione.isAfter(fine);
    }
    
    /**
     * Restituisce una descrizione della vidimazione
     */
    public String getDescrizione() {
        StringBuilder desc = new StringBuilder();
        desc.append("Vidimazione del ").append(dataVidimazione.toLocalDate());
        desc.append(" alle ").append(dataVidimazione.toLocalTime());
        desc.append(" su mezzo ").append(mezzo.getNumeroMezzo());
        if (tratta != null) {
            desc.append(" (tratta: ").append(tratta.getNome()).append(")");
        }
        if (!isValida()) {
            desc.append(" - INVALIDA");
            if (note != null) {
                desc.append(": ").append(note);
            }
        }
        return desc.toString();
    }
    
    /**
     * Restituisce informazioni sul biglietto vidimato
     */
    public String getInfoBiglietto() {
        if (biglietto == null) return "N/A";
        return "Biglietto " + biglietto.getCodiceBiglietto() + 
               " (emesso il " + biglietto.getDataEmissione().toLocalDate() + ")";
    }
    
    /**
     * Restituisce informazioni sul mezzo utilizzato
     */
    public String getInfoMezzo() {
        if (mezzo == null) return "N/A";
        return mezzo.getTipo() + " " + mezzo.getNumeroMezzo() + 
               " (" + mezzo.getModello() + ")";
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataVidimazione() {
        return dataVidimazione;
    }
    
    public void setDataVidimazione(LocalDateTime dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }
    
    public Boolean getValida() {
        return valida;
    }
    
    public void setValida(Boolean valida) {
        this.valida = valida;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Biglietto getBiglietto() {
        return biglietto;
    }
    
    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
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
        return "Vidimazione{" +
                "id=" + id +
                ", dataVidimazione=" + dataVidimazione +
                ", valida=" + valida +
                ", biglietto=" + (biglietto != null ? biglietto.getCodiceBiglietto() : null) +
                ", mezzo=" + (mezzo != null ? mezzo.getNumeroMezzo() : null) +
                ", tratta=" + (tratta != null ? tratta.getNome() : null) +
                '}';
    }
}