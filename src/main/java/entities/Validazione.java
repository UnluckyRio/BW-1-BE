package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "validazione")
public class Validazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_validazione")
    private Long id;

    @Column(name = "data_ora_validazione", nullable = false)
    private LocalDateTime dataOraValidazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_biglietto")
    private Biglietto biglietto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    public Validazione() {
    }

    public Validazione(LocalDateTime dataOraValidazione, Mezzo mezzo, Biglietto biglietto) {
        this.dataOraValidazione = dataOraValidazione;
        this.mezzo = mezzo;
        this.biglietto = biglietto;
    }

    public Validazione(LocalDateTime dataOraValidazione, Mezzo mezzo, Tessera tessera) {
        this.dataOraValidazione = dataOraValidazione;
        this.mezzo = mezzo;
        this.tessera = tessera;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataOraValidazione() {
        return dataOraValidazione;
    }

    public void setDataOraValidazione(LocalDateTime dataOraValidazione) {
        this.dataOraValidazione = dataOraValidazione;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public Biglietto getBiglietto() {
        return biglietto;
    }

    public void setBiglietto(Biglietto biglietto) {
        this.biglietto = biglietto;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Validazione{" +
                "id=" + id +
                ", dataOraValidazione=" + dataOraValidazione +
                ", mezzo=" + (mezzo != null ? mezzo.getId() : null) +
                ", biglietto=" + (biglietto != null ? biglietto.getId() : null) +
                ", tessera=" + (tessera != null ? tessera.getId() : null) +
                '}';
    }
}
