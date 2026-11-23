package cat.iesesteveterradas.fites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Exercici1Test {

    private Exercici1 exercici;
    private File fileEntrada;
    private File fileSortida;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        exercici = new Exercici1();

        try {
            // Crear fitxer d'entrada al directori temporal
            fileEntrada = new File(tempDir.toFile(), "entradaTest.txt");
            try (FileWriter writer = new FileWriter(fileEntrada, StandardCharsets.UTF_8)) {
                writer.write("Aquesta és la primera línia.\n");
                writer.write("Aquí n'hi ha una altra, amb dièresi: pingüí.\n");
            }

            // Establir el fitxer d'entrada i sortida en Exercici1
            fileSortida = new File(tempDir.toFile(), "sortidaTest.txt");
            exercici.configuraRutaFitxerEntrada(fileEntrada.getAbsolutePath());
            exercici.configuraRutaFitxerSortida(fileSortida.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            fail("No s'ha pogut configurar el fitxer d'entrada per al test.");
        }
    }

    @Test
    public void testExecuta() {
        exercici.executa();

        // Comprova que el fitxer de sortida existeix
        assertTrue(fileSortida.exists(), "El fitxer de sortida hauria de ser creat.");

        // Llegeix el contingut del fitxer de sortida
        try {
            List<String> liniesSortida = llegeixLinies(fileSortida);
            assertEquals(3, liniesSortida.size(), "El fitxer de sortida hauria de tenir tres línies: les dues d'entrada i el total.");

            assertEquals("[12] Aquesta és la primera línia.", liniesSortida.get(0),
                         "La primera línia de sortida ha de ser el recompte entre claudàtors, un espai i la línia original.");
            assertEquals("[17] Aquí n'hi ha una altra, amb dièresi: pingüí.", liniesSortida.get(1),
                         "La segona línia de sortida ha de ser el recompte entre claudàtors, un espai i la línia original.");
            assertEquals("TOTAL: 29", liniesSortida.get(2),
                         "L'última línia ha de ser el total de vocals del fitxer.");
        } catch (IOException e) {
            fail("No s'ha pogut llegir el fitxer de sortida.");
        }
    }

    @Test
    public void testComptaVocals() {
        assertEquals(3, Exercici1.comptaVocals("Hola món"), "El mètode comptaVocals ha de comptar les vocals accentuades.");
        assertEquals(6, Exercici1.comptaVocals("ÀRBRE i pingüí"), "El mètode comptaVocals ha de comptar majúscules i vocals amb dièresi.");
        assertEquals(0, Exercici1.comptaVocals("xyz 123 ç"), "El mètode comptaVocals ha de retornar 0 si no hi ha vocals.");
    }

    // ------------------------------------------------------------------------
    // Tests addicionals
    // ------------------------------------------------------------------------

    // Llegeix totes les línies d'un fitxer de text en UTF-8
    private List<String> llegeixLinies(File fitxer) throws IOException {
        List<String> linies = new ArrayList<>();
        try (Scanner lector = new Scanner(fitxer, StandardCharsets.UTF_8)) {
            while (lector.hasNextLine()) {
                linies.add(lector.nextLine());
            }
        }
        return linies;
    }

    // Escriu un text en un fitxer en UTF-8, sobreescrivint-lo si ja existeix
    private void escriuText(File fitxer, String text) throws IOException {
        try (FileWriter escriptor = new FileWriter(fitxer, StandardCharsets.UTF_8)) {
            escriptor.write(text);
        }
    }

    @Test
    public void testComptaVocalsSenseAccent() {
        assertEquals(10, Exercici1.comptaVocals("aeiouAEIOU"), "El mètode comptaVocals ha de comptar les cinc vocals en minúscules i en majúscules.");
        assertEquals(0, Exercici1.comptaVocals(""), "El mètode comptaVocals ha de retornar 0 per a una cadena buida.");
        assertEquals(0, Exercici1.comptaVocals("   "), "El mètode comptaVocals ha de retornar 0 per a una cadena només amb espais.");
    }

    @Test
    public void testComptaVocalsAmbAccentIDieresi() {
        assertEquals(12, Exercici1.comptaVocals("àáèéìíïòóùúü"), "El mètode comptaVocals ha de comptar totes les vocals amb accent o dièresi en minúscules.");
        assertEquals(12, Exercici1.comptaVocals("ÀÁÈÉÌÍÏÒÓÙÚÜ"), "El mètode comptaVocals ha de comptar totes les vocals amb accent o dièresi en majúscules.");
    }

    @Test
    public void testExecutaLiniesSenseVocalsIBuides() throws IOException {
        escriuText(fileEntrada, "Hola\n\nxyz\nAdéu\n");

        exercici.executa();

        assertTrue(fileSortida.exists(), "El fitxer de sortida hauria de ser creat.");
        List<String> liniesSortida = llegeixLinies(fileSortida);
        assertEquals(5, liniesSortida.size(), "Cada línia d'entrada, també les buides, ha de generar una línia de sortida, més la del total.");
        assertEquals("[2] Hola", liniesSortida.get(0), "La primera línia no és correcta.");
        assertEquals("[0]", liniesSortida.get(1).strip(), "Una línia buida s'ha d'escriure amb el recompte [0].");
        assertEquals("[0] xyz", liniesSortida.get(2), "Una línia sense vocals s'ha d'escriure amb el recompte [0].");
        assertEquals("[3] Adéu", liniesSortida.get(3), "La quarta línia no és correcta.");
        assertEquals("TOTAL: 5", liniesSortida.get(4), "L'última línia ha de ser el total de vocals del fitxer.");
    }

    @Test
    public void testExecutaSobreescriuFitxerSortida() throws IOException {
        // El fitxer de sortida ja existeix i té més línies que el resultat esperat
        escriuText(fileSortida, "contingut antic\n".repeat(10));

        exercici.executa();
        List<String> liniesSortida = llegeixLinies(fileSortida);
        assertEquals(3, liniesSortida.size(), "El fitxer de sortida s'ha de sobreescriure: no hi pot quedar contingut antic.");
        assertEquals("[12] Aquesta és la primera línia.", liniesSortida.get(0), "La primera línia no és correcta després de sobreescriure.");
        assertEquals("TOTAL: 29", liniesSortida.get(2), "L'última línia ha de ser el total de vocals del fitxer.");

        // Una segona execució no ha d'afegir línies al final
        exercici.executa();
        liniesSortida = llegeixLinies(fileSortida);
        assertEquals(3, liniesSortida.size(), "Executar dues vegades no ha d'afegir línies al fitxer de sortida.");
    }

    @Test
    public void testExecutaFitxerEntradaInexistent() {
        // Amb un fitxer d'entrada correcte, executa ha de generar el fitxer de sortida
        exercici.executa();
        assertTrue(fileSortida.exists(), "El fitxer de sortida hauria de ser creat.");

        // Amb un fitxer d'entrada que no existeix, no ha de propagar l'excepció
        exercici.configuraRutaFitxerEntrada(new File(tempDir.toFile(), "noExisteix.txt").getAbsolutePath());
        String sortida = Consola.captura(() -> assertDoesNotThrow(() -> exercici.executa(),
                "Si el fitxer d'entrada no existeix, executa ha de capturar l'excepció, no propagar-la."));
        assertFalse(sortida.isBlank(),
                "Si el fitxer d'entrada no existeix, executa ha de mostrar l'excepció a la consola.");
    }

    // ------------------------------------------------------------------------
    // Tests de tècnica: comproven que executa treballa amb els fitxers fent
    // servir la classe obligatòria de l'enunciat (Files) i cap de les prohibides.
    // ------------------------------------------------------------------------

    @Test
    public void testTecnicaExecutaUsaFiles() throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici1", "executa");
        assertTrue(Tecnica.usaClasse(codi, "Files"),
                "Tècnica: executa ha de llegir i escriure els fitxers amb la classe Files.");
    }

    @Test
    public void testTecnicaExecutaSenseClassesProhibides() throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici1", "executa");
        assertTrue(Tecnica.usaClasse(codi, "Files"),
                "Tècnica: executa ha de llegir i escriure els fitxers amb la classe Files.");
        assertFalse(Tecnica.usaAlgunaClasse(codi, "File", "Scanner", "FileReader", "FileWriter",
                        "FileInputStream", "FileOutputStream", "PrintWriter"),
                "Tècnica: executa no pot fer servir File, Scanner, FileReader, FileWriter, FileInputStream, FileOutputStream ni PrintWriter.");
    }
}
