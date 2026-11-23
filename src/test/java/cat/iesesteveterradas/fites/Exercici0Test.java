package cat.iesesteveterradas.fites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Exercici0Test {

    private Exercici0 exercici;
    private String dirPath;
    private String filePath;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        exercici = new Exercici0();
        dirPath = System.getProperty("user.dir") + "/data/exercici0";
        filePath = System.getProperty("user.dir") + "/data/exercici0/Exercici0.dat";
        exercici.configuraRutaDirectori(dirPath);
        exercici.configuraRutaFitxer(filePath);
    }

    @Test
    public void testComprovaEsDirectori() {
        assertEquals(true, exercici.comprovaEsDirectori(),
                "El mètode comprovaEsDirectori ha de retornar true per a un directori que existeix.");

        // Un fitxer existeix però no és un directori
        exercici.configuraRutaDirectori(filePath);
        assertEquals(false, exercici.comprovaEsDirectori(),
                "El mètode comprovaEsDirectori ha de retornar false si la ruta és un fitxer.");

        // Una ruta que no existeix
        exercici.configuraRutaDirectori(dirPath + "/noExisteix");
        assertEquals(false, exercici.comprovaEsDirectori(),
                "El mètode comprovaEsDirectori ha de retornar false si la ruta no existeix.");
    }

    @Test
    public void testDeterminaSiEsPotLlegir() {
        File directori = new File(dirPath);
        Boolean expected = directori.canRead();
        Boolean actual = exercici.determinaSiEsPotLlegir();
        assertEquals(expected, actual, "El mètode determinaSiEsPotLlegir ha de retornar si el directori es pot llegir.");
    }

    @Test
    public void testObtenirMidaEnBytes() {
        File fitxer = new File(filePath);
        Long expected = fitxer.length();
        Long actual = exercici.obtenirMidaEnBytes();
        assertTrue(expected > 0, "El fitxer de dades no hauria d'estar buit.");
        assertEquals(expected, actual, "El mètode obtenirMidaEnBytes ha de retornar la mida del fitxer en bytes.");
    }

    @Test
    public void testObtenirNomDirectoriPare() {
        String actual = exercici.obtenirNomDirectoriPare();
        assertEquals("data", actual, "El mètode obtenirNomDirectoriPare ha de retornar només el nom del directori pare.");
    }

    @Test
    public void testLlistarArxiusDirectori() {
        File directori = new File(dirPath);
        ArrayList<String> expected = new ArrayList<>();
        File[] fitxers = directori.listFiles();
        if (fitxers != null) {
            for (File f : fitxers) {
                expected.add(f.getName());
            }
        }

        ArrayList<String> actual = exercici.llistarArxiusDirectori();
        assertNotNull(actual, "El mètode llistarArxiusDirectori no ha de retornar null per a un directori que existeix.");

        // L'ordre del llistat no està garantit: es comparen ordenats
        ArrayList<String> actualOrdenat = new ArrayList<>(actual);
        Collections.sort(expected);
        Collections.sort(actualOrdenat);
        assertEquals(expected, actualOrdenat, "El mètode llistarArxiusDirectori ha de retornar els noms dels arxius i carpetes del directori.");
        assertTrue(actual.contains("Exercici0.dat"), "El llistat ha d'incloure Exercici0.dat.");
        assertTrue(actual.contains("arxiu"), "El llistat ha d'incloure la carpeta arxiu.");

        // Si la ruta no és un directori, ha de retornar null
        exercici.configuraRutaDirectori(dirPath + "/noExisteix");
        assertEquals(null, exercici.llistarArxiusDirectori(), "El mètode llistarArxiusDirectori ha de retornar null si la ruta no és un directori.");
    }

    // ------------------------------------------------------------------------
    // Tests addicionals: treballen sobre un directori temporal, de manera que
    // no depenen del contingut de data/ i no es poden resoldre amb valors fixos.
    // ------------------------------------------------------------------------

    // Crea un fitxer amb el contingut indicat dins de la carpeta
    private File creaFitxer(File carpeta, String nom, byte[] contingut) throws IOException {
        File fitxer = new File(carpeta, nom);
        try (FileOutputStream sortida = new FileOutputStream(fitxer)) {
            sortida.write(contingut);
        }
        return fitxer;
    }

    @Test
    public void testDeterminaSiEsPotLlegirRutaInexistent() {
        exercici.configuraRutaDirectori(tempDir.toString());
        assertEquals(true, exercici.determinaSiEsPotLlegir(),
                "El mètode determinaSiEsPotLlegir ha de retornar true per a un directori temporal acabat de crear.");

        exercici.configuraRutaDirectori(new File(tempDir.toFile(), "noExisteix").getAbsolutePath());
        assertEquals(false, exercici.determinaSiEsPotLlegir(),
                "El mètode determinaSiEsPotLlegir ha de retornar false si la ruta no existeix.");
    }

    @Test
    public void testObtenirMidaEnBytesFitxersConeguts() throws IOException {
        // Fitxer binari de mida coneguda
        File fitxerBinari = creaFitxer(tempDir.toFile(), "mida.dat", new byte[1234]);
        exercici.configuraRutaFitxer(fitxerBinari.getAbsolutePath());
        assertEquals(Long.valueOf(1234), exercici.obtenirMidaEnBytes(),
                "El mètode obtenirMidaEnBytes ha de retornar 1234 per a un fitxer de 1234 bytes.");

        // La mida són bytes, no caràcters: "pingüí" té 6 caràcters però ocupa 8 bytes en UTF-8
        File fitxerText = creaFitxer(tempDir.toFile(), "text.txt", "pingüí".getBytes(StandardCharsets.UTF_8));
        exercici.configuraRutaFitxer(fitxerText.getAbsolutePath());
        assertEquals(Long.valueOf(8), exercici.obtenirMidaEnBytes(),
                "El mètode obtenirMidaEnBytes ha de retornar la mida en bytes, no el nombre de caràcters.");

        // Fitxer buit
        File fitxerBuit = creaFitxer(tempDir.toFile(), "buit.dat", new byte[0]);
        exercici.configuraRutaFitxer(fitxerBuit.getAbsolutePath());
        assertEquals(Long.valueOf(0), exercici.obtenirMidaEnBytes(),
                "El mètode obtenirMidaEnBytes ha de retornar 0 per a un fitxer buit.");
    }

    @Test
    public void testObtenirNomDirectoriPareDirectoriTemporal() {
        File pare = new File(tempDir.toFile(), "pare");
        File fill = new File(pare, "fill");
        assertTrue(fill.mkdirs(), "No s'han pogut crear els directoris de prova.");

        exercici.configuraRutaDirectori(fill.getAbsolutePath());
        assertEquals("pare", exercici.obtenirNomDirectoriPare(),
                "El mètode obtenirNomDirectoriPare ha de retornar el nom del directori que conté dirPath.");

        exercici.configuraRutaDirectori(pare.getAbsolutePath());
        assertEquals(tempDir.toFile().getName(), exercici.obtenirNomDirectoriPare(),
                "El mètode obtenirNomDirectoriPare ha de retornar només el nom, sense la ruta.");
    }

    @Test
    public void testLlistarArxiusDirectoriTemporal() throws IOException {
        creaFitxer(tempDir.toFile(), "a.txt", new byte[]{'a'});
        creaFitxer(tempDir.toFile(), "b.dat", new byte[]{'b'});
        File subcarpeta = new File(tempDir.toFile(), "sub");
        assertTrue(subcarpeta.mkdir(), "No s'ha pogut crear la subcarpeta de prova.");
        creaFitxer(subcarpeta, "dins.txt", new byte[]{'d'});

        exercici.configuraRutaDirectori(tempDir.toString());
        ArrayList<String> actual = exercici.llistarArxiusDirectori();
        assertNotNull(actual, "El mètode llistarArxiusDirectori no ha de retornar null per a un directori que existeix.");

        ArrayList<String> actualOrdenat = new ArrayList<>(actual);
        Collections.sort(actualOrdenat);
        assertEquals(List.of("a.txt", "b.dat", "sub"), actualOrdenat,
                "El llistat ha de contenir només els noms (no les rutes) dels arxius i carpetes directes, sense entrar a les subcarpetes.");
    }

    @Test
    public void testLlistarArxiusDirectoriBuit() {
        File buit = new File(tempDir.toFile(), "buit");
        assertTrue(buit.mkdir(), "No s'ha pogut crear el directori de prova.");
        exercici.configuraRutaDirectori(buit.getAbsolutePath());

        ArrayList<String> actual = exercici.llistarArxiusDirectori();
        assertNotNull(actual, "Per a un directori buit que existeix, llistarArxiusDirectori ha de retornar una llista buida, no null.");
        assertTrue(actual.isEmpty(), "Per a un directori buit, la llista retornada no ha de tenir elements.");
    }

    // ------------------------------------------------------------------------
    // Tests de tècnica: comproven que cada mètode fa servir les classes
    // obligatòries de l'enunciat (Path i Files) i no la classe java.io.File.
    // ------------------------------------------------------------------------

    private void comprovaTecnica(String metode, String... classesObligatories) throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici0", metode);
        assertTrue(Tecnica.usaAlgunaClasse(codi, classesObligatories),
                "Tècnica: el mètode " + metode + " ha de fer servir la classe " + String.join(" o ", classesObligatories) + ".");
        assertFalse(Tecnica.usaClasse(codi, "File") || Tecnica.cridaMetode(codi, "toFile"),
                "Tècnica: el mètode " + metode + " no pot fer servir la classe java.io.File.");
    }

    @Test
    public void testTecnicaComprovaEsDirectori() throws IOException {
        comprovaTecnica("comprovaEsDirectori", "Files");
    }

    @Test
    public void testTecnicaDeterminaSiEsPotLlegir() throws IOException {
        comprovaTecnica("determinaSiEsPotLlegir", "Files");
    }

    @Test
    public void testTecnicaObtenirMidaEnBytes() throws IOException {
        comprovaTecnica("obtenirMidaEnBytes", "Files");
    }

    @Test
    public void testTecnicaObtenirNomDirectoriPare() throws IOException {
        comprovaTecnica("obtenirNomDirectoriPare", "Path", "Paths");
    }

    @Test
    public void testTecnicaLlistarArxiusDirectori() throws IOException {
        comprovaTecnica("llistarArxiusDirectori", "Files");
    }
}
