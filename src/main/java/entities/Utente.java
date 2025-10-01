package entities;

import enums.RuoloUtente;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "utente")
public class Utente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false)
    private RuoloUtente ruolo;

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Tessera tessera;

    public Utente() {
    }

    public Utente(String nome, String cognome, LocalDate dataNascita, RuoloUtente ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.ruolo = ruolo;
    }

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

    public RuoloUtente getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloUtente ruolo) {
        this.ruolo = ruolo;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", ruolo=" + ruolo +
                '}';
    }
}