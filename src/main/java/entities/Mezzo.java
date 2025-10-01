package entities;

import enums.StatoMezzo;
import enums.TipoMezzo;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "mezzo")
public class Mezzo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mezzo")
    private Long id;

    @Column(name = "targa", nullable = false, unique = true)
    private String targa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mezzo", nullable = false)
    private TipoMezzo tipoMezzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_mezzo", nullable = false)
    private StatoMezzo statoMezzo;

    @Column(name = "capienza", nullable = false)
    private int capienza;

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tratta> tratte;

    public Mezzo() {
    }

    public Mezzo(String targa, TipoMezzo tipoMezzo, StatoMezzo statoMezzo, int capienza) {
        this.targa = targa;
        this.tipoMezzo = tipoMezzo;
        this.statoMezzo = statoMezzo;
        this.capienza = capienza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
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

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public List<Tratta> getTratte() {
        return tratte;
    }

    public void setTratte(List<Tratta> tratte) {
        this.tratte = tratte;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", targa='" + targa + '\'' +
                ", tipoMezzo=" + tipoMezzo +
                ", statoMezzo=" + statoMezzo +
                ", capienza=" + capienza +
                '}';
    }
}
