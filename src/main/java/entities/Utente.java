package entities;

import enums.RuoloUtente;
import jakarta.persistence.*;

@Entity
@Table(name = "Utente")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Nome", nullable = false, length = 10)
    private String nome;

    @Column(name = "Cognome", nullable = false, length = 10)
    private String cognome;

    @Column(name = "Username", nullable = false, length = 10)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "Ruolo", nullable = false)
    private RuoloUtente ruoloUtente;

    public Utente() {
    }

    public Utente(String nome, String cognome, String username, RuoloUtente ruoloUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.ruoloUtente = ruoloUtente;
    }

    public long getId() {
        return id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RuoloUtente getRuolo() {
        return ruoloUtente;
    }

    public void setRuolo(RuoloUtente ruoloUtente) {
        this.ruoloUtente = ruoloUtente;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", ruoloUtente=" + ruoloUtente +
                '}';
    }
}