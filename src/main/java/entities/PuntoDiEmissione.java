package entities;
import jakarta.persistence.*;

@Entity
@Table(name = "Punto emissione")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoDiEmissione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_punto_emissione")
    private Long idPuntoEmissione;

    @Column(nullable = false)
    private String indirizzo;

    public PuntoDiEmissione() {}

    public PuntoDiEmissione(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Long getIdPuntoEmissione() {
        return idPuntoEmissione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "PuntoDiEmissione{" +
                "idPuntoEmissione=" + idPuntoEmissione +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }
}
