package entities;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "tempoMedioPercorrenza")
public class PercorrenzaMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtempoMedioPercorrenza")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idtratta")
    private Tratta tratta;

    @Column(name = "tempoEffettivo")
    private LocalTime tempoEffettivo;

    @Column(name = "id_mezzo")
    private Long idMezzo;

    public PercorrenzaMedia() {}

    public PercorrenzaMedia(Tratta tratta, LocalTime tempoEffettivo) {
        this.tratta = tratta;
        this.tempoEffettivo = tempoEffettivo;
        if (tratta != null && tratta.getMezzo() != null) {
            this.idMezzo = tratta.getMezzo().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
        if (tratta != null && tratta.getMezzo() != null) {
            this.idMezzo = tratta.getMezzo().getId();
        }
    }

    public LocalTime getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(LocalTime tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public Long getIdMezzo() {
        return idMezzo;
    }

    @Override
    public String toString() {
        return "PercorrenzaMedia{" +
                "id=" + id +
                ", tratta=" + (tratta != null ? tratta.getId() : null) +
                ", idMezzo=" + idMezzo +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }
}
