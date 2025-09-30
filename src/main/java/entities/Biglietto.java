package entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Biglietto")


public class Biglietto {

    @Column(name = "Data Emissione")
    protected LocalDate dataEmissione;
    @Column(name = "prezzo")
    protected double prezzo;
    @Column(name = "id_rivenditore", nullable = false)
    protected Rivenditore idrivenditore;
    @Column(name = "id_distributore", nullable = false)
    protected DistributoreAutomatico iddistributore;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Timbrato", nullable = false)
    private boolean timbrato;
    @Column(name = "durata validazione", nullable = false)
    private int durataValidazione;


    public Biglietto() {

    }


    public Biglietto(LocalDate dataEmissione, double prezzo, boolean timbrato, int durataValidazione) {

        this.id = id;
        this.timbrato = timbrato;
        this.durataValidazione = durataValidazione;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Rivenditore getIdrivenditore() {
        return idrivenditore;
    }

    public void setIdrivenditore(Rivenditore idrivenditore) {
        this.idrivenditore = idrivenditore;
    }

    public DistributoreAutomatico getIddistributore() {
        return iddistributore;
    }

    public void setIddistributore(DistributoreAutomatico iddistributore) {
        this.iddistributore = iddistributore;
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


    @Override
    public String toString() {
        return "Biglietto{" +
                "dataEmissione=" + dataEmissione +
                ", prezzo=" + prezzo +
                ", idrivenditore=" + idrivenditore +
                ", iddistributore=" + iddistributore +
                ", id=" + id +
                ", timbrato=" + timbrato +
                ", durataValidazione=" + durataValidazione +
                '}';
    }

}
