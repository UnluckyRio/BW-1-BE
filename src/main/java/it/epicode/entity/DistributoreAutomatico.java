package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entità che rappresenta un distributore automatico di biglietti.
 * Estende PuntoEmissione e aggiunge funzionalità specifiche per i distributori.
 */
@Entity
@Table(name = "distributori_automatici")
@DiscriminatorValue("DISTRIBUTORE")
@PrimaryKeyJoinColumn(name = "id")
public class DistributoreAutomatico extends PuntoEmissione {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "stato_servizio")
    private StatoServizio statoServizio = StatoServizio.ATTIVO;
    
    @Column(name = "ultimo_controllo")
    private LocalDateTime ultimoControllo;
    
    @Column(name = "codice_distributore", unique = true, nullable = false, length = 20)
    private String codiceDistributore;
    
    // Enum per lo stato del servizio
    public enum StatoServizio {
        ATTIVO, FUORI_SERVIZIO
    }
    
    // Costruttori
    public DistributoreAutomatico() {
        super();
    }
    
    public DistributoreAutomatico(String nome, String indirizzo, String codiceDistributore) {
        super(nome, indirizzo);
        this.codiceDistributore = codiceDistributore;
        this.ultimoControllo = LocalDateTime.now();
    }
    
    // Metodi di utilità
    
    /**
     * Verifica se il distributore è in servizio
     */
    public boolean isInServizio() {
        return getAttivo() && statoServizio == StatoServizio.ATTIVO;
    }
    
    /**
     * Mette il distributore fuori servizio
     */
    public void mettiFuoriServizio() {
        this.statoServizio = StatoServizio.FUORI_SERVIZIO;
        setUpdatedAt(LocalDateTime.now());
    }
    
    /**
     * Rimette il distributore in servizio
     */
    public void rimettiInServizio() {
        this.statoServizio = StatoServizio.ATTIVO;
        this.ultimoControllo = LocalDateTime.now();
        setUpdatedAt(LocalDateTime.now());
    }
    
    /**
     * Esegue un controllo di manutenzione
     */
    public void eseguiControlloManutenzione() {
        this.ultimoControllo = LocalDateTime.now();
        // Se era fuori servizio e il controllo va bene, rimetti in servizio
        if (statoServizio == StatoServizio.FUORI_SERVIZIO) {
            this.statoServizio = StatoServizio.ATTIVO;
        }
        setUpdatedAt(LocalDateTime.now());
    }
    
    /**
     * Verifica se il distributore necessita di controllo
     * (più di 7 giorni dall'ultimo controllo)
     */
    public boolean necessitaControllo() {
        if (ultimoControllo == null) return true;
        return ultimoControllo.isBefore(LocalDateTime.now().minusDays(7));
    }
    
    @Override
    public String getTipoSpecifico() {
        return "Distributore Automatico";
    }
    
    @Override
    public boolean isOperativo() {
        return super.isOperativo() && isInServizio();
    }
    
    // Getter e Setter
    public StatoServizio getStatoServizio() {
        return statoServizio;
    }
    
    public void setStatoServizio(StatoServizio statoServizio) {
        this.statoServizio = statoServizio;
    }
    
    public LocalDateTime getUltimoControllo() {
        return ultimoControllo;
    }
    
    public void setUltimoControllo(LocalDateTime ultimoControllo) {
        this.ultimoControllo = ultimoControllo;
    }
    
    public String getCodiceDistributore() {
        return codiceDistributore;
    }
    
    public void setCodiceDistributore(String codiceDistributore) {
        this.codiceDistributore = codiceDistributore;
    }
    
    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", codiceDistributore='" + codiceDistributore + '\'' +
                ", statoServizio=" + statoServizio +
                ", ultimoControllo=" + ultimoControllo +
                ", attivo=" + getAttivo() +
                '}';
    }
}