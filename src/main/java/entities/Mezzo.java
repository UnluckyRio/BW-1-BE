package entities;

import jakarta.persistence.*;

/**
 * Entità Mezzo per rappresentare i mezzi di trasporto
 */
@Entity
@Table(name = "Mezzo")
public class Mezzo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;
    
    @Column(name = "capacita", nullable = false)
    private int capacita;
    
    @Column(name = "stato", nullable = false, length = 20)
    private String stato;
    
    // Costruttore vuoto richiesto da JPA
    public Mezzo() {
    }
    
    // Costruttore con parametri
    public Mezzo(String tipo, int capacita, String stato) {
        this.tipo = tipo;
        this.capacita = capacita;
        this.stato = stato;
    }
    
    // Getter e Setter
    public long getId() {
        return id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getCapacita() {
        return capacita;
    }
    
    public void setCapacita(int capacita) {
        this.capacita = capacita;
    }
    
    public String getStato() {
        return stato;
    }
    
    public void setStato(String stato) {
        this.stato = stato;
    }
    
    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", capacita=" + capacita +
                ", stato='" + stato + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mezzo mezzo = (Mezzo) o;
        return id == mezzo.id;
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
