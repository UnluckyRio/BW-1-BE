package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mezzo")
public class Mezzo {
    @Id
    @Column(name = "id")
    long id;

    @Column(name = "targa", nullable = false, length = 30)
    private int tatga;
    @Column(name = "tipoMezzo", nullable = false, length = 30)
    private String tipoMezzo;
    @Column(name = "capienza")
    private int capienza;
    @Column(name = "statoMezzo", nullable = false, length = 30)
    private String statoMezzo;


    public Mezzo() {
    }

    public Mezzo(int tatga, String tipoMezzo, int capienza, String statoMezzo) {
        this.tatga = tatga;
        this.tipoMezzo = tipoMezzo;
        this.capienza = capienza;
        this.statoMezzo = statoMezzo;
    }


    public int getTatga() {
        return tatga;
    }

    public void setTatga(int tatga) {
        this.tatga = tatga;
    }

    public String getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(String tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public String getStatoMezzo() {
        return statoMezzo;
    }

    public void setStatoMezzo(String statoMezzo) {
        this.statoMezzo = statoMezzo;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "tatga=" + tatga +
                ", tipoMezzo='" + tipoMezzo + '\'' +
                ", capienza=" + capienza +
                ", statoMezzo='" + statoMezzo + '\'' +
                '}';
    }
}
