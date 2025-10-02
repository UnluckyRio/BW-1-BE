package entities;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "tratta")
public class Tratta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @Column(name = "tempoPrevisto", nullable = false)
    private LocalTime tempoPrevisto;

    @Column(name = "partenza", nullable = false)
    private String partenza;

    @Column(name = "arrivo", nullable = false)
    private String arrivo;

    public Tratta() {}

    public Tratta(Mezzo mezzo, LocalTime tempoPrevisto, String partenza, String arrivo) {
        this.mezzo = mezzo;
        this.tempoPrevisto = tempoPrevisto;
        this.partenza = partenza;
        this.arrivo = arrivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalTime getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(LocalTime tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public String getPartenza() {
        return partenza;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public String getArrivo() {
        return arrivo;
    }

    public void setArrivo(String arrivo) {
        this.arrivo = arrivo;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", mezzo=" + mezzo +
                ", tempoPrevisto=" + tempoPrevisto +
                ", partenza='" + partenza + '\'' +
                ", arrivo='" + arrivo + '\'' +
                '}';
    }
}
