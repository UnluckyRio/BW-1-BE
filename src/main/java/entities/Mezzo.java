package entities;

import enums.StatoMezzo;
import enums.TipoMezzo;
import jakarta.persistence.*;

@Entity
@Table(name = "mezzo")
public class Mezzo {
    @Id
    @Column(name = "id")
    long id;

    @Column(name = "targa", nullable = false, length = 30)
    private int tatga;
    @Column(name = "capienza")
    private int capienza;
    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo mezzo", nullable = false, length = 30)
    private TipoMezzo tipoMezzo;
    @Enumerated(EnumType.STRING)
    @Column(name = "Stato Mezzo", nullable = false, length = 30)
    private StatoMezzo statoMezzo;


    public Mezzo() {
    }

    public Mezzo(int tatga, TipoMezzo tipoMezzo, int capienza, StatoMezzo statoMezzo) {
        this.tatga = tatga;
        this.tipoMezzo = tipoMezzo;
        this.capienza = capienza;
        this.statoMezzo = statoMezzo;
    }

    public long getId() {
        return id;
    }

    public int getTatga() {
        return tatga;
    }

    public void setTatga(int tatga) {
        this.tatga = tatga;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public StatoMezzo getStatoMezzo() {
        return statoMezzo;
    }

    public void setStatoMezzo(StatoMezzo statoMezzo) {
        this.statoMezzo = statoMezzo;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tatga=" + tatga +
                ", capienza=" + capienza +
                ", tipoMezzo=" + tipoMezzo +
                ", statoMezzo=" + statoMezzo +
                '}';
    }
}
