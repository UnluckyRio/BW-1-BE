package entities;

import enums.StatoMezzo;
import enums.TipoMezzo;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Mezzo")
public class Mezzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id mezzo")
    private long id;

    @Column(name = "targa", nullable = false, length = 7)
    private String targa;

    @Column(name = "capienza")
    private int capienza;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo mezzo", nullable = false)
    private TipoMezzo tipoMezzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Stato Mezzo", nullable = false)
    private StatoMezzo statoMezzo;

    @OneToMany(mappedBy = "mezzoValidante")
    private List<Biglietto> bigliettiValidati;

    public Mezzo() {
    }

    public Mezzo(String targa, TipoMezzo tipoMezzo, int capienza, StatoMezzo statoMezzo) {
        this.targa = targa;
        this.tipoMezzo = tipoMezzo;
        this.capienza = capienza;
        this.statoMezzo = statoMezzo;
    }

    public long getId() {
        return id;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
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

    public List<Biglietto> getBigliettiValidati() {
        return bigliettiValidati;
    }

    public void setBigliettiValidati(List<Biglietto> bigliettiValidati) {
        this.bigliettiValidati = bigliettiValidati;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", targa='" + targa + '\'' +
                ", capienza=" + capienza +
                ", tipoMezzo=" + tipoMezzo +
                ", statoMezzo=" + statoMezzo +
                '}';
    }
}
