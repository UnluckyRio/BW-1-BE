package entities;

import java.time.LocalDate;

public class Tratta {
    private Long id;
    private LocalDate dataOraInizio;
    private int tempo_effettivo;

    public Tratta() {
    }


    public Tratta(Long id, LocalDate dataOraInizio, int tempo_effettivo) {
        this.id = id;
        this.dataOraInizio = dataOraInizio;
        this.tempo_effettivo = tempo_effettivo;
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
        return tempo_effettivo;
    }

    public void setTempo_effettivo(int tempo_effettivo) {
        this.tempo_effettivo = tempo_effettivo;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", dataOraInizio=" + dataOraInizio +
                ", tempo_effettivo=" + tempo_effettivo +
                '}';
    }
}
