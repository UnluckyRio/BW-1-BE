package entities;

import jakarta.persistence.*;


@Entity
@Table(name = "tempoMedioPercorrenza")
public class PercorrenzaMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tempoMedioPercorrenza")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @Column(name = "tempoEffettivo")
    private double tempoEffettivo;

    public PercorrenzaMedia() {}

    public PercorrenzaMedia(Tratta tratta, double tempoEffettivo) {
        this.tratta = tratta;
        this.tempoEffettivo = tempoEffettivo;
    }

    public Long getId() {
        return id;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public double getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(double tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    @Override
    public String toString() {
        return "PercorrenzaMedia{" +
                "id=" + id +
                ", tratta=" + tratta +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }
}

