package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rivenditore")
public class Rivenditore extends PuntoEmissione {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "indirizzo", nullable = false)
    private String indirizzo;

    public Rivenditore() {
        super();
    }

    public Rivenditore(String nome, String indirizzo) {
        super();
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "Rivenditore{" +
                "id=" + getId() +
                ", nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }
}
