package entities;

import enums.TipoAbbonamento;
import enums.TipoTitoloViaggio;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Abbonamento")


public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "abbonamento_id", sequenceName = "abbonamento_id", allocationSize = 1)
    protected long id;


    @Column(name = "Data Emissione")
    protected LocalDate dataEmissione;

    @Column(name = "prezzo")
    protected double prezzo;


    // @Column(name = "id_rivenditore", nullable = false)
    //  protected Rivenditore idrivenditore;


    // @Column(name = "id_distributore", nullable = false)
    //  protected Rivenditore iddistributore;


    @Column(name = "Tipo abbonamento", nullable = false)
    private TipoAbbonamento tipoAbbonamento;

    @Column(name = "Data inizio iscrizione", nullable = false)
    private LocalDate dataiscrizione;

    @Column(name = "Data scadenza iscrizione", nullable = false)
    private LocalDate datascadenza;

    @OneToOne
    @JoinColumn(name = "id_validazione")
    private Validazione validazione;

    public Abbonamento(TipoTitoloViaggio tipoTitoloViaggio, LocalDate dataEmissione, double prezzo, TipoAbbonamento tipoAbbonamento, LocalDate dataiscrizione, LocalDate datascadenza, Validazione validazione) {

        this.id = id;
        this.tipoAbbonamento = tipoAbbonamento;
        this.dataiscrizione = dataiscrizione;
        this.datascadenza = datascadenza;
        this.validazione = validazione;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public LocalDate getDataiscrizione() {
        return dataiscrizione;
    }

    public void setDataiscrizione(LocalDate dataiscrizione) {
        this.dataiscrizione = dataiscrizione;
    }

    public LocalDate getDatascadenza() {
        return datascadenza;
    }

    public void setDatascadenza(LocalDate datascadenza) {
        this.datascadenza = datascadenza;
    }

    public Validazione getValidazione() {
        return validazione;
    }

    public void setValidazione(Validazione validazione) {
        this.validazione = validazione;
    }

}
