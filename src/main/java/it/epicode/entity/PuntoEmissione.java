package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità base che rappresenta un punto di emissione di biglietti e abbonamenti.
 * Viene estesa da DistributoreAutomatico e RivenditoreAutorizzato.
 */
@Entity
@Table(name = "punti_emissione")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class PuntoEmissione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;
    
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "indirizzo", length = 200)
    private String indirizzo;
    
    @Column(name = "attivo")
    private Boolean attivo = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relazione uno-a-molti con Biglietto
    @OneToMany(mappedBy = "puntoEmissione", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Biglietto> bigliettiEmessi = new ArrayList<>();
    
    // Relazione uno-a-molti con Abbonamento
    @OneToMany(mappedBy = "puntoEmissione", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Abbonamento> abbonamentiEmessi = new ArrayList<>();
    
    // Costruttori
    public PuntoEmissione() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public PuntoEmissione(String nome, String indirizzo) {
        this();
        this.nome = nome;
        this.indirizzo = indirizzo;
    }
    
    // Metodi di utilità
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verifica se il punto di emissione è operativo
     */
    public boolean isOperativo() {
        return attivo;
    }
    
    /**
     * Attiva il punto di emissione
     */
    public void attiva() {
        this.attivo = true;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Disattiva il punto di emissione
     */
    public void disattiva() {
        this.attivo = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Aggiunge un biglietto emesso
     */
    public void addBigliettoEmesso(Biglietto biglietto) {
        bigliettiEmessi.add(biglietto);
        biglietto.setPuntoEmissione(this);
    }
    
    /**
     * Aggiunge un abbonamento emesso
     */
    public void addAbbonamentoEmesso(Abbonamento abbonamento) {
        abbonamentiEmessi.add(abbonamento);
        abbonamento.setPuntoEmissione(this);
    }
    
    /**
     * Restituisce il numero totale di biglietti emessi
     */
    public int getNumeroBigliettiEmessi() {
        return bigliettiEmessi.size();
    }
    
    /**
     * Restituisce il numero totale di abbonamenti emessi
     */
    public int getNumeroAbbonamentiEmessi() {
        return abbonamentiEmessi.size();
    }
    
    // Metodo astratto per il tipo specifico di punto emissione
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
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getIndirizzo() {
        return indirizzo;
    }
    
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
    
    public Boolean getAttivo() {
        return attivo;
    }
    
    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
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
    
    public List<Biglietto> getBigliettiEmessi() {
        return bigliettiEmessi;
    }
    
    public void setBigliettiEmessi(List<Biglietto> bigliettiEmessi) {
        this.bigliettiEmessi = bigliettiEmessi;
    }
    
    public List<Abbonamento> getAbbonamentiEmessi() {
        return abbonamentiEmessi;
    }
    
    public void setAbbonamentiEmessi(List<Abbonamento> abbonamentiEmessi) {
        this.abbonamentiEmessi = abbonamentiEmessi;
    }
    
    @Override
    public String toString() {
        return "PuntoEmissione{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", attivo=" + attivo +
                '}';
    }
}