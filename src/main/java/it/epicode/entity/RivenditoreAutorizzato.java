package it.epicode.entity;

import jakarta.persistence.*;

/**
 * Entità che rappresenta un rivenditore autorizzato di biglietti e abbonamenti.
 * Estende PuntoEmissione e aggiunge informazioni specifiche per i rivenditori.
 */
@Entity
@Table(name = "rivenditori_autorizzati")
@DiscriminatorValue("RIVENDITORE")
@PrimaryKeyJoinColumn(name = "id")
public class RivenditoreAutorizzato extends PuntoEmissione {
    
    @Column(name = "partita_iva", unique = true, nullable = false, length = 20)
    private String partitaIva;
    
    @Column(name = "ragione_sociale", nullable = false, length = 150)
    private String ragioneSociale;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "email", length = 150)
    private String email;
    
    // Costruttori
    public RivenditoreAutorizzato() {
        super();
    }
    
    public RivenditoreAutorizzato(String nome, String indirizzo, String partitaIva, String ragioneSociale) {
        super(nome, indirizzo);
        this.partitaIva = partitaIva;
        this.ragioneSociale = ragioneSociale;
    }
    
    public RivenditoreAutorizzato(String nome, String indirizzo, String partitaIva, 
                                String ragioneSociale, String telefono, String email) {
        this(nome, indirizzo, partitaIva, ragioneSociale);
        this.telefono = telefono;
        this.email = email;
    }
    
    // Metodi di utilità
    
    /**
     * Verifica se il rivenditore ha tutti i dati necessari per essere operativo
     */
    public boolean hasDatiCompleti() {
        return partitaIva != null && !partitaIva.trim().isEmpty() &&
               ragioneSociale != null && !ragioneSociale.trim().isEmpty() &&
               getNome() != null && !getNome().trim().isEmpty();
    }
    
    /**
     * Verifica se la partita IVA è valida (controllo di base sulla lunghezza)
     */
    public boolean hasPartitaIvaValida() {
        return partitaIva != null && partitaIva.length() == 11 && partitaIva.matches("\\d+");
    }
    
    @Override
    public String getTipoSpecifico() {
        return "Rivenditore Autorizzato";
    }
    
    @Override
    public boolean isOperativo() {
        return super.isOperativo() && hasDatiCompleti() && hasPartitaIvaValida();
    }
    
    /**
     * Restituisce le informazioni di contatto complete
     */
    public String getInfoContatto() {
        StringBuilder info = new StringBuilder();
        if (telefono != null && !telefono.trim().isEmpty()) {
            info.append("Tel: ").append(telefono);
        }
        if (email != null && !email.trim().isEmpty()) {
            if (info.length() > 0) info.append(" - ");
            info.append("Email: ").append(email);
        }
        return info.toString();
    }
    
    // Getter e Setter
    public String getPartitaIva() {
        return partitaIva;
    }
    
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }
    
    public String getRagioneSociale() {
        return ragioneSociale;
    }
    
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "RivenditoreAutorizzato{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", ragioneSociale='" + ragioneSociale + '\'' +
                ", partitaIva='" + partitaIva + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", attivo=" + getAttivo() +
                '}';
    }
}