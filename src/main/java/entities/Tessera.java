package entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "Tessera")
public class Tessera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Data Emissione", nullable = false)
    private LocalDate dataEmissione;
    @Column(name = "Data Scadenza", nullable = false)
    private LocalDate dataScadenza;
    @OneToOne
    @JoinColumn(name = "Utente_Id")
    private Utente idUtente;

    public Tessera() {
    }

    public Tessera(LocalDate dataEmissione, LocalDate dataScadenza) {
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
    }

    public long getId() {
        return id;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", idUtente=" + idUtente +
                '}';
    }
}