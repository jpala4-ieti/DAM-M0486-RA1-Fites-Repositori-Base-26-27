package cat.iesesteveterradas.fites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Dificultat: baixa
 *
 * Objectius:
 *   Implementa els mètodes: comprovaEsDirectori, determinaSiEsPotLlegir, obtenirMidaEnBytes,
 *   obtenirNomDirectoriPare, llistarArxiusDirectori
 *
 *   La funcionalitat que s'espera que realitzin és:
 *
 * - Comprovar si `dirPath` existeix i és un directori.
 * - Determinar si el directori `dirPath` es pot llegir.
 * - Obtenir la mida en bytes del fitxer `filePath`.
 * - Obtenir el nom (només el nom, no la ruta) del directori pare de `dirPath`.
 * - Llistar els noms de tots els arxius i carpetes que conté `dirPath`.
 *
 * Classes obligatòries (els tests de tècnica comproven que cada mètode les fa servir):
 *
 * - Path:  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html
 * - Files: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html
 *
 * En aquest exercici NO es pot fer servir la classe java.io.File.
 */

public class Exercici0 {

    private String dirPath;
    private String filePath;

    public static void main(String args[]) {
        Exercici0 exercici = new Exercici0();
        // Configurar la ruta del directori i la del fitxer
        exercici.configuraRutaDirectori(System.getProperty("user.dir") + "/data/exercici0");
        exercici.configuraRutaFitxer(System.getProperty("user.dir") + "/data/exercici0/Exercici0.dat");
        // Executar la lògica principal
        exercici.executa();
    }

    // Mètode que conté la lògica principal
    public void executa() {
        boolean esDirectori = comprovaEsDirectori();
        System.out.println("La ruta és un directori: " + esDirectori);

        if (esDirectori) {
            boolean esPotLlegir = determinaSiEsPotLlegir();
            System.out.println("El directori es pot llegir: " + esPotLlegir);

            String nomPare = obtenirNomDirectoriPare();
            System.out.println("El directori pare es diu: " + nomPare);

            ArrayList<String> llistaArxius = llistarArxiusDirectori();
            System.out.println("El directori conté: " + llistaArxius);
        } else {
            System.out.println("La ruta no existeix o no és un directori.");
        }

        Long mida = obtenirMidaEnBytes();
        System.out.println("El fitxer de dades ocupa " + mida + " bytes");
    }

    // Mètode per comprovar que la ruta del directori existeix i és un directori
    public Boolean comprovaEsDirectori() {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    // Mètode per determinar si el directori es pot llegir
    public Boolean determinaSiEsPotLlegir() {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    // Mètode per obtenir la mida en bytes del fitxer de dades (filePath).
    // Si no es pot obtenir (per exemple, perquè el fitxer no existeix), mostra l'excepció i retorna null.
    public Long obtenirMidaEnBytes() {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    // Mètode per obtenir el nom del directori pare del directori (només el nom)
    public String obtenirNomDirectoriPare() {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    // Mètode per llistar els noms dels arxius i carpetes que conté el directori.
    // Si la ruta no existeix o no és un directori, retorna null.
    public ArrayList<String> llistarArxiusDirectori() {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    /****************************************************************************/
    /*                          NO CAL MODIFICAR                                */
    /****************************************************************************/
    // Mètode per configurar la ruta del directori
    public void configuraRutaDirectori(String dirPath) {
        this.dirPath = dirPath;
    }

    // Mètode per configurar la ruta del fitxer de dades
    public void configuraRutaFitxer(String filePath) {
        this.filePath = filePath;
    }
}