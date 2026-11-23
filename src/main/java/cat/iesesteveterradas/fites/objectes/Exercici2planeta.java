package cat.iesesteveterradas.fites.objectes;

import java.util.Objects;

/**
 * Planeta del sistema solar.
 *
 * NO MODIFIQUIS AQUESTA CLASSE. No és serialitzable expressament:
 * les seves dades s'han de desar camp a camp.
 */

public class Exercici2planeta {

    private final String nom;
    private final String tipus;
    private final double radiKm;
    private final int posicio;

    public Exercici2planeta(String nom, String tipus, double radiKm, int posicio) {
        this.nom = nom;
        this.tipus = tipus;
        this.radiKm = radiKm;
        this.posicio = posicio;
    }

    public String getNom() {
        return nom;
    }

    public String getTipus() {
        return tipus;
    }

    public double getRadiKm() {
        return radiKm;
    }

    public int getPosicio() {
        return posicio;
    }

    @Override
    public String toString() {
        return this.nom + " (" + this.tipus + "): " + this.radiKm + " km, posició " + this.posicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercici2planeta)) return false;
        Exercici2planeta that = (Exercici2planeta) o;
        return Double.compare(radiKm, that.radiKm) == 0 &&
                posicio == that.posicio &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(tipus, that.tipus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, tipus, radiKm, posicio);
    }
}
