package entities;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "tratta")
public class Tratta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratta")
    private Long id;

    @Column(name = "zona_partenza", nullable = false)
    private String zonaPartenza;

    @Column(name = "capolinea", nullable = false)
    private String capolinea;

    @Column(name = "tempo_previsto", nullable = false)
    private LocalTime tempoPrevisto;

    @Column(name = "tempo_effettivo")
    private LocalTime tempoEffettivo;

    @Column(name = "completata", nullable = false)
    private boolean completata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, LocalTime tempoPrevisto, Mezzo mezzo) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
        this.mezzo = mezzo;
        this.completata = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public LocalTime getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(LocalTime tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public LocalTime getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(LocalTime tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public boolean isCompletata() {
        return completata;
    }

    public void setCompletata(boolean completata) {
        this.completata = completata;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoPrevisto=" + tempoPrevisto +
                ", tempoEffettivo=" + tempoEffettivo +
                ", completata=" + completata +
                '}';
    }
}
