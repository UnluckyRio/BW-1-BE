package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tratta")

public class Tratta {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "dataOraInizio", nullable = false, length = 30)
    private LocalDate dataOraInizio;
    @Column(name = "tempoEffettivo")
    private int tempoEffettivo;

    public Tratta() {
    }


    public Tratta(Long id, LocalDate dataOraInizio, int tempo_effettivo) {
        this.id = id;
        this.dataOraInizio = dataOraInizio;
        this.tempoEffettivo = tempo_effettivo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataOraInizio() {
        return dataOraInizio;
    }

    public void setDataOraInizio(LocalDate dataOraInizio) {
        this.dataOraInizio = dataOraInizio;
    }

    public int getTempo_effettivo() {
        return tempoEffettivo;
    }

    public void setTempo_effettivo(int tempo_effettivo) {
        this.tempoEffettivo = tempo_effettivo;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", dataOraInizio=" + dataOraInizio +
                ", tempo_effettivo=" + tempoEffettivo +
                '}';
    }
}
