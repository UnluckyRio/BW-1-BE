package entities;

import enums.TipoTitoloViaggio;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Biglietto")


public class Biglietto {

    @Column(name = "Data Emissione")
    protected LocalDate dataEmissione;
    @Column(name = "prezzo")
    protected double prezzo;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "biglietto_id", sequenceName = "biglietto_id", allocationSize = 1)
    private long id;


    // @Column(name = "id_rivenditore", nullable = false)
    //  protected Rivenditore idrivenditore;


    // @Column(name = "id_distributore", nullable = false)
    //  protected Rivenditore iddistributore;
    @Column(name = "Timbrato", nullable = false)
    private boolean timbrato;

    @Column(name = "durata validazione", nullable = false)
    private int durataValidazione;

    @OneToOne
    @JoinColumn(name = "id_validazione")
    private Validazione validazione;


    public Biglietto() {

    }


    public Biglietto(TipoTitoloViaggio tipoTitoloViaggio, LocalDate dataEmissione, double prezzo, boolean timbrato, int durataValidazione, Validazione validazione) {

        this.id = id;
        this.timbrato = timbrato;
        this.durataValidazione = durataValidazione;
        this.validazione = validazione;
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isTimbrato() {
        return timbrato;
    }

    public void setTimbrato(boolean timbrato) {
        this.timbrato = timbrato;
    }

    public int getDurataValidazione() {
        return durataValidazione;
    }

    public void setDurataValidazione(int durataValidazione) {
        this.durataValidazione = durataValidazione;
    }

    public Validazione getValidazione() {
        return validazione;
    }

    public void setValidazione(Validazione validazione) {
        this.validazione = validazione;
    }


}
