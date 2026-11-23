package cat.iesesteveterradas.fites;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Dificultat: baixa-mitjana
 *
 * Implementa codi que compleixi el següent:
 *
 * - Llegeix un fitxer de text d'entrada amb codificació UTF-8.
 * - Compta les vocals de cada línia utilitzant el mètode 'comptaVocals'.
 * - Escriu al fitxer de sortida, amb codificació UTF-8, cada línia precedida del seu
 *   recompte entre claudàtors i un espai. Per exemple, la línia "Hola món" s'escriu:
 *       [3] Hola món
 *   Les línies buides també s'escriuen, amb el recompte [0].
 * - Després de l'última línia n'escriu una més amb el total de vocals del fitxer:
 *       TOTAL: 123
 * - El fitxer de sortida es crea o sobrescriu si ja existeix.
 * - Si hi ha un error en llegir o escriure els fitxers, l'excepció es mostra a la consola.
 *
 * Es consideren vocals: a, e, i, o, u, i també amb accent o dièresi
 * (à, á, è, é, ì, í, ï, ò, ó, ù, ú, ü). Totes compten tant en minúscules com en
 * majúscules (per exemple, À i Ü també són vocals).
 *
 * Classes obligatòries (els tests de tècnica comproven que el mètode executa les fa servir):
 *
 * - Path:  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html
 * - Files: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html
 *
 * Tant la lectura del fitxer d'entrada com l'escriptura del fitxer de sortida s'han de fer
 * amb mètodes de la classe Files. En aquest exercici NO es poden fer servir les classes
 * File, Scanner, FileReader, FileWriter, FileInputStream, FileOutputStream ni PrintWriter.
 */
public class Exercici1 {

    private String filePathIn;
    private String filePathOut;


    public static void main(String args[]) {
        Exercici1 exercici = new Exercici1();
        // Configurar els fitxers d'entrada i sortida
        exercici.configuraRutaFitxerEntrada(System.getProperty("user.dir") + "/data/exercici1/Exercici1Entrada.txt");
        exercici.configuraRutaFitxerSortida(System.getProperty("user.dir") + "/data/exercici1/Exercici1Solucio.txt");
        // Executar la lògica principal
        exercici.executa();
    }

    // Processa el fitxer d'entrada i genera el fitxer de sortida.
    public void executa() {
        // *************** CODI EXERCICI FITA **********************/
    }

    // Mètode per comptar les vocals d'un text
    public static int comptaVocals(String text) {
        // *************** CODI EXERCICI FITA **********************/
        return -1; // A substituir
    }

    /****************************************************************************/
    /*                          NO CAL MODIFICAR                                */
    /****************************************************************************/
    // Mètode per configurar el fitxer d'entrada
    public void configuraRutaFitxerEntrada(String filePathIn) {
        this.filePathIn = filePathIn;
    }

    // Mètode per configurar el fitxer de sortida
    public void configuraRutaFitxerSortida(String filePathOut) {
        this.filePathOut = filePathOut;
    }
}