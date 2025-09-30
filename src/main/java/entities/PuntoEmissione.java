package entities;

import enums.StatoEmittente;
import enums.TipoEmittente;
import jakarta.persistence.*;

@Entity
@Table(name = "Punto di emissione")
public class PuntoEmissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Nome emittente", nullable = false, length = 20)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo emittente", nullable = false)
    private TipoEmittente tipoEmittente;

    @Enumerated(EnumType.STRING)
    @Column(name = "Stato emittente", nullable = false)
    private StatoEmittente statoEmittente;

    public PuntoEmissione() {
    }

    public PuntoEmissione(String nome, TipoEmittente tipoEmittente, StatoEmittente statoEmittente) {
        this.nome = nome;
        this.tipoEmittente = tipoEmittente;
        this.statoEmittente = statoEmittente;
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

    public TipoEmittente getTipoEmittente() {
        return tipoEmittente;
    }

    public void setTipoEmittente(TipoEmittente tipoEmittente) {
        this.tipoEmittente = tipoEmittente;
    }

    public StatoEmittente getStatoEmittente() {
        return statoEmittente;
    }

    public void setStatoEmittente(StatoEmittente statoEmittente) {
        this.statoEmittente = statoEmittente;
    }

    @Override
    public String toString() {
        return "PuntoEmissione{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipoEmittente=" + tipoEmittente +
                ", statoEmittente=" + statoEmittente +
                '}';
    }
}
