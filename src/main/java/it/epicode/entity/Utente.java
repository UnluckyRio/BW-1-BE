package it.epicode.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entità che rappresenta un utente del sistema di trasporto pubblico.
 * Ogni utente può possedere una o più tessere per gli abbonamenti.
 */
@Entity
@Table(name = "utenti")
public class Utente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "cognome", nullable = false, length = 100)
    private String cognome;
    
    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;
    
    @Column(name = "email", unique = true, length = 150)
    private String email;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relazione uno-a-molti con Tessera
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tessera> tessere = new ArrayList<>();
    
    // Costruttori
    public Utente() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Utente(String nome, String cognome, LocalDate dataNascita) {
        this();
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }
    
    public Utente(String nome, String cognome, LocalDate dataNascita, String email, String telefono) {
        this(nome, cognome, dataNascita);
        this.email = email;
        this.telefono = telefono;
    }
    
    // Metodi di utilità
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Aggiunge una tessera all'utente
     */
    public void addTessera(Tessera tessera) {
        tessere.add(tessera);
        tessera.setUtente(this);
    }
    
    /**
     * Rimuove una tessera dall'utente
     */
    public void removeTessera(Tessera tessera) {
        tessere.remove(tessera);
        tessera.setUtente(null);
    }
    
    /**
     * Restituisce il nome completo dell'utente
     */
    public String getNomeCompleto() {
        return nome + " " + cognome;
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
    
    public String getCognome() {
        return cognome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public LocalDate getDataNascita() {
        return dataNascita;
    }
    
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
    
    public List<Tessera> getTessere() {
        return tessere;
    }
    
    public void setTessere(List<Tessera> tessere) {
        this.tessere = tessere;
    }
    
    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}