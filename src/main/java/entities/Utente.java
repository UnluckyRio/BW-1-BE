package entities;

import enums.Ruolo;
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

    @Column(name = "Password", nullable = false, length = 12)
    private int password;

    @Enumerated(EnumType.STRING)
    @Column(name = "Ruolo", nullable = false)
    private Ruolo ruolo;

    @JoinColumn(name = "Titolo_viaggio_id")
    private TitoloDiViaggio titoloViaggioId;

    public Utente() {
    }

    public Utente(String nome, String cognome, String username, int password, Ruolo ruolo, TitoloDiViaggio titoloViaggioId) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
        this.titoloViaggioId = titoloViaggioId;
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

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public TitoloDiViaggio getTitoloViaggioId() {
        return titoloViaggioId;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", ruolo=" + ruolo +
                ", titoloViaggioId=" + titoloViaggioId +
                '}';
    }
}
