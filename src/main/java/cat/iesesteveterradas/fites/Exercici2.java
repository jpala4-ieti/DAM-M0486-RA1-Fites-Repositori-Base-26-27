package cat.iesesteveterradas.fites;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import cat.iesesteveterradas.fites.objectes.Exercici2planeta;

/**
 * Dificultat: mitjana
 *
 * Implementa el codi que realitzi el següent:
 *
 * - Desa la llista de planetes en un fitxer binari anomenat 'Exercici2.dat' al directori de
 *   treball especificat. La classe Exercici2planeta NO és serialitzable i no es pot modificar:
 *   cal escriure les dades de cada planeta amb un DataOutputStream.
 * - El fitxer ha de tenir exactament aquest format: primer el nombre de planetes (int) i, a
 *   continuació, per a cada planeta i en aquest ordre: el nom (String en format UTF), el tipus
 *   (String en format UTF), el radi (double) i la posició (int). No hi pot haver res més.
 * - Després d'una pausa d'1 segon, llegeix el fitxer binari amb un DataInputStream, reconstrueix
 *   la llista i la imprimeix per pantalla, un planeta per línia, amb el format
 *   "Nom (tipus): radi km, posició n".
 * - Gestió d'errors: si hi ha algun problema en escriure o llegir el fitxer (ex. fitxer no trobat),
 *   mostra l'excepció a la consola amb e.printStackTrace()
 *
 * Classes obligatòries (els tests de tècnica comproven que escriuLlista i llegeixLlista les fan servir):
 *
 * - DataOutputStream: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataOutputStream.html
 * - DataInputStream:  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html
 * - FileOutputStream: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileOutputStream.html
 * - FileInputStream:  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileInputStream.html
 *
 * En aquest exercici NO es poden fer servir ObjectOutputStream, ObjectInputStream ni RandomAccessFile.
 */
public class Exercici2 {
    private String filePath;

    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + "/data/exercici2/";
        String filePath = basePath + "Exercici2.dat";

        Exercici2 exercici = new Exercici2();
        exercici.configurarRutaFitxerSortida(filePath);
        exercici.executa();
    }

    // Mètode que executa la lògica de la classe
    public void executa() {
        ArrayList<Exercici2planeta> llista0 = new ArrayList<>();
        llista0.add(new Exercici2planeta("Mercuri", "rocós", 2439.7, 1));
        llista0.add(new Exercici2planeta("Venus", "rocós", 6051.8, 2));
        llista0.add(new Exercici2planeta("Terra", "rocós", 6371.0, 3));
        llista0.add(new Exercici2planeta("Mart", "rocós", 3389.5, 4));
        llista0.add(new Exercici2planeta("Júpiter", "gasós", 69911.0, 5));
        llista0.add(new Exercici2planeta("Saturn", "gasós", 58232.0, 6));
        llista0.add(new Exercici2planeta("Urà", "gegant de gel", 25362.0, 7));
        llista0.add(new Exercici2planeta("Neptú", "gegant de gel", 24622.0, 8));

        escriuLlista(filePath, llista0);

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Exercici2planeta> llista1 = llegeixLlista(filePath);
        imprimeixLlista(llista1);
    }

    // Mètode per escriure la llista en un fitxer binari
    public void escriuLlista(String filePath, ArrayList<Exercici2planeta> llista) {
        // *************** CODI EXERCICI FITA **********************/
    }

    // Mètode per llegir la llista del fitxer binari
    public ArrayList<Exercici2planeta> llegeixLlista(String filePath) {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    // Mètode per imprimir la llista
    public void imprimeixLlista(ArrayList<Exercici2planeta> llista) {
        // *************** CODI EXERCICI FITA **********************/
    }

    /****************************************************************************/
    /*                          NO CAL MODIFICAR                                */
    /****************************************************************************/
    // Setter per definir el path
    public void configurarRutaFitxerSortida(String filePath) {
        this.filePath = filePath;
    }

    // Getter per obtenir el path
    public String obtenirRutaFitxerSortida() {
        return this.filePath;
    }
}