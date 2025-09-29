package it.epicode.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entità che rappresenta un abbonamento (settimanale o mensile) per il trasporto pubblico.
 * Gli abbonamenti sono nominativi e legati ad una tessera.
 */
@Entity
@Table(name = "abbonamenti")
public class Abbonamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codice_abbonamento", unique = true, nullable = false, length = 30)
    private String codiceAbbonamento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAbbonamento tipo;
    
    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;
    
    @Column(name = "data_inizio_validita", nullable = false)
    private LocalDate dataInizioValidita;
    
    @Column(name = "data_fine_validita", nullable = false)
    private LocalDate dataFineValidita;
    
    @Column(name = "prezzo", nullable = false, precision = 6, scale = 2)
    private BigDecimal prezzo;
    
    @Column(name = "attivo")
    private Boolean attivo = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Relazione molti-a-uno con Tessera
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tessera_id", nullable = false)
    private Tessera tessera;
    
    // Relazione molti-a-uno con PuntoEmissione
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punto_emissione_id", nullable = false)
    private PuntoEmissione puntoEmissione;
    
    // Enum per il tipo di abbonamento
    public enum TipoAbbonamento {
        SETTIMANALE(7, new BigDecimal("15.00")),
        MENSILE(30, new BigDecimal("35.00"));
        
        private final int giorni;
        private final BigDecimal prezzoBase;
        
        TipoAbbonamento(int giorni, BigDecimal prezzoBase) {
            this.giorni = giorni;
            this.prezzoBase = prezzoBase;
        }
        
        public int getGiorni() {
            return giorni;
        }
        
        public BigDecimal getPrezzoBase() {
            return prezzoBase;
        }
    }
    
    // Costruttori
    public Abbonamento() {
        this.createdAt = LocalDateTime.now();
        this.dataEmissione = LocalDate.now();
    }
    
    public Abbonamento(String codiceAbbonamento, TipoAbbonamento tipo, Tessera tessera, 
                      PuntoEmissione puntoEmissione) {
        this();
        this.codiceAbbonamento = codiceAbbonamento;
        this.tipo = tipo;
        this.tessera = tessera;
        this.puntoEmissione = puntoEmissione;
        this.prezzo = tipo.getPrezzoBase();
        
        // Imposta le date di validità
        this.dataInizioValidita = LocalDate.now();
        this.dataFineValidita = this.dataInizioValidita.plusDays(tipo.getGiorni());
    }
    
    public Abbonamento(String codiceAbbonamento, TipoAbbonamento tipo, Tessera tessera, 
                      PuntoEmissione puntoEmissione, LocalDate dataInizio) {
        this();
        this.codiceAbbonamento = codiceAbbonamento;
        this.tipo = tipo;
        this.tessera = tessera;
        this.puntoEmissione = puntoEmissione;
        this.prezzo = tipo.getPrezzoBase();
        
        // Imposta le date di validità
        this.dataInizioValidita = dataInizio;
        this.dataFineValidita = dataInizio.plusDays(tipo.getGiorni());
    }
    
    // Metodi di utilità
    
    /**
     * Verifica se l'abbonamento è valido (attivo e nel periodo di validità)
     */
    public boolean isValido() {
        LocalDate oggi = LocalDate.now();
        return attivo && 
               !oggi.isBefore(dataInizioValidita) && 
               !oggi.isAfter(dataFineValidita) &&
               tessera != null && tessera.isValida();
    }
    
    /**
     * Verifica se l'abbonamento è scaduto
     */
    public boolean isScaduto() {
        return LocalDate.now().isAfter(dataFineValidita);
    }
    
    /**
     * Verifica se l'abbonamento deve ancora iniziare
     */
    public boolean isInAttesa() {
        return LocalDate.now().isBefore(dataInizioValidita);
    }
    
    /**
     * Disattiva l'abbonamento
     */
    public void disattiva() {
        this.attivo = false;
    }
    
    /**
     * Riattiva l'abbonamento (se non scaduto)
     */
    public void riattiva() {
        if (!isScaduto()) {
            this.attivo = true;
        }
    }
    
    /**
     * Restituisce i giorni rimanenti di validità
     */
    public long getGiorniRimanenti() {
        if (isScaduto()) return 0;
        if (isInAttesa()) return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dataInizioValidita);
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dataFineValidita) + 1;
    }
    
    /**
     * Restituisce lo stato dell'abbonamento come stringa
     */
    public String getStato() {
        if (!attivo) return "DISATTIVATO";
        if (isInAttesa()) return "IN_ATTESA";
        if (isScaduto()) return "SCADUTO";
        if (isValido()) return "VALIDO";
        return "NON_VALIDO";
    }
    
    /**
     * Genera un codice abbonamento univoco
     */
    public static String generaCodiceAbbonamento(TipoAbbonamento tipo) {
        String prefisso = tipo == TipoAbbonamento.SETTIMANALE ? "ABB_S" : "ABB_M";
        return prefisso + System.currentTimeMillis() + 
               String.format("%03d", (int)(Math.random() * 1000));
    }
    
    // Getter e Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodiceAbbonamento() {
        return codiceAbbonamento;
    }
    
    public void setCodiceAbbonamento(String codiceAbbonamento) {
        this.codiceAbbonamento = codiceAbbonamento;
    }
    
    public TipoAbbonamento getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoAbbonamento tipo) {
        this.tipo = tipo;
    }
    
    public LocalDate getDataEmissione() {
        return dataEmissione;
    }
    
    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }
    
    public LocalDate getDataInizioValidita() {
        return dataInizioValidita;
    }
    
    public void setDataInizioValidita(LocalDate dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }
    
    public LocalDate getDataFineValidita() {
        return dataFineValidita;
    }
    
    public void setDataFineValidita(LocalDate dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }
    
    public BigDecimal getPrezzo() {
        return prezzo;
    }
    
    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
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
    
    public Tessera getTessera() {
        return tessera;
    }
    
    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }
    
    public PuntoEmissione getPuntoEmissione() {
        return puntoEmissione;
    }
    
    public void setPuntoEmissione(PuntoEmissione puntoEmissione) {
        this.puntoEmissione = puntoEmissione;
    }
    
    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", codiceAbbonamento='" + codiceAbbonamento + '\'' +
                ", tipo=" + tipo +
                ", dataInizioValidita=" + dataInizioValidita +
                ", dataFineValidita=" + dataFineValidita +
                ", prezzo=" + prezzo +
                ", attivo=" + attivo +
                ", stato='" + getStato() + '\'' +
                '}';
    }
}