package cat.iesesteveterradas.fites;

import cat.iesesteveterradas.fites.objectes.Exercici2planeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Exercici2Test {

    private Exercici2 exercici;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        exercici = new Exercici2();
        // Establim el path temporal per al fitxer de prova
        String tempFilePath = tempDir.resolve("Exercici2Test.dat").toString();
        exercici.configurarRutaFitxerSortida(tempFilePath);
    }

    @Test
    public void testExecuta() {
        // Executar la lògica de la classe (el que imprimeix no es mostra a la consola)
        Consola.captura(() -> exercici.executa());

        // Comprovar que el fitxer existeix i té contingut
        File fitxerSortida = new File(exercici.obtenirRutaFitxerSortida());
        assertTrue(fitxerSortida.exists(), "El fitxer de sortida no existeix.");
        assertTrue(fitxerSortida.length() > 0, "El fitxer de sortida és buit.");

        // Llegir el fitxer i comprovar el contingut
        ArrayList<Exercici2planeta> llista = exercici.llegeixLlista(exercici.obtenirRutaFitxerSortida());
        assertNotNull(llista, "La llista no hauria de ser nul·la.");
        assertEquals(8, llista.size(), "La llista hauria de tenir 8 elements.");

        // Comprovació d'alguns valors
        assertEquals(new Exercici2planeta("Mercuri", "rocós", 2439.7, 1), llista.get(0));
        assertEquals(new Exercici2planeta("Júpiter", "gasós", 69911.0, 5), llista.get(4));
        assertEquals(new Exercici2planeta("Neptú", "gegant de gel", 24622.0, 8), llista.get(7));
    }

    // ------------------------------------------------------------------------
    // Tests addicionals: criden directament escriuLlista, llegeixLlista i
    // imprimeixLlista (no passen per executa, així que no fan la pausa d'1 segon).
    // ------------------------------------------------------------------------

    private ArrayList<Exercici2planeta> llistaDeProva() {
        ArrayList<Exercici2planeta> llista = new ArrayList<>();
        llista.add(new Exercici2planeta("Plutó", "planeta nan", 1188.3, 9));
        llista.add(new Exercici2planeta("Ceres", "planeta nan", 469.73, 10));
        llista.add(new Exercici2planeta("Eris", "planeta nan", 1163.0, 11));
        return llista;
    }

    @Test
    public void testEscriuILlegeixLlistaPropia() {
        String ruta = exercici.obtenirRutaFitxerSortida();
        ArrayList<Exercici2planeta> original = llistaDeProva();

        exercici.escriuLlista(ruta, original);
        ArrayList<Exercici2planeta> llegida = exercici.llegeixLlista(ruta);

        assertNotNull(llegida, "La llista llegida no hauria de ser nul·la.");
        assertEquals(original, llegida, "La llista llegida ha de tenir els mateixos planetes, amb les mateixes dades i en el mateix ordre, que la que s'ha escrit.");
    }

    @Test
    public void testEscriuILlegeixLlistaBuida() {
        String ruta = exercici.obtenirRutaFitxerSortida();

        exercici.escriuLlista(ruta, new ArrayList<>());
        assertTrue(new File(ruta).exists(), "El fitxer s'ha de crear encara que la llista sigui buida.");

        ArrayList<Exercici2planeta> llegida = exercici.llegeixLlista(ruta);
        assertNotNull(llegida, "Per a un fitxer amb zero planetes, llegeixLlista ha de retornar una llista buida, no null.");
        assertTrue(llegida.isEmpty(), "Per a un fitxer amb zero planetes, la llista llegida no ha de tenir elements.");
    }

    // Bytes que ha de contenir el fitxer segons el format de l'enunciat: el nombre de planetes (int)
    // i, per a cada planeta, nom (UTF), tipus (UTF), radi (double) i posició (int)
    private byte[] bytesEsperats(ArrayList<Exercici2planeta> llista) {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        buffer.putInt(llista.size());
        for (Exercici2planeta planeta : llista) {
            posaText(buffer, planeta.getNom());
            posaText(buffer, planeta.getTipus());
            buffer.putDouble(planeta.getRadiKm());
            buffer.putInt(planeta.getPosicio());
        }
        return Arrays.copyOf(buffer.array(), buffer.position());
    }

    // Un text en format UTF: dos bytes amb la longitud i, després, els bytes del text
    private void posaText(ByteBuffer buffer, String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        buffer.putShort((short) bytes.length);
        buffer.put(bytes);
    }

    @Test
    public void testFormatDelFitxerBinari() throws IOException {
        String ruta = exercici.obtenirRutaFitxerSortida();
        ArrayList<Exercici2planeta> original = llistaDeProva();
        exercici.escriuLlista(ruta, original);
        assertTrue(new File(ruta).exists(), "El fitxer de sortida no existeix.");

        byte[] contingut = Files.readAllBytes(Path.of(ruta));
        assertArrayEquals(bytesEsperats(original), contingut,
                "El fitxer no té exactament el format de l'enunciat: nombre de planetes (int) i, per a cada planeta, "
                + "nom (String UTF), tipus (String UTF), radi (double) i posició (int), sense res més.");
    }

    @Test
    public void testLlegeixFitxerAmbElFormatDeLEnunciat() throws IOException {
        String ruta = exercici.obtenirRutaFitxerSortida();
        ArrayList<Exercici2planeta> original = llistaDeProva();

        // El fitxer l'escriu el test, no escriuLlista
        Files.write(Path.of(ruta), bytesEsperats(original));

        ArrayList<Exercici2planeta> llegida = exercici.llegeixLlista(ruta);
        assertNotNull(llegida, "La llista llegida no hauria de ser nul·la.");
        assertEquals(original, llegida, "llegeixLlista ha de reconstruir la llista a partir d'un fitxer amb el format de l'enunciat.");
    }

    @Test
    public void testImprimeixLlista() {
        ArrayList<Exercici2planeta> llista = llistaDeProva();

        // Capturar el que s'escriu per pantalla
        String sortida = Consola.captura(() -> exercici.imprimeixLlista(llista));

        ArrayList<String> linies = new ArrayList<>();
        for (String linia : sortida.split("\\R")) {
            if (!linia.isBlank()) {
                linies.add(linia.strip());
            }
        }

        assertEquals(llista.size(), linies.size(), "imprimeixLlista ha d'escriure una línia per planeta.");
        assertEquals("Plutó (planeta nan): 1188.3 km, posició 9", linies.get(0), "El format de la primera línia no és correcte.");
        assertEquals("Ceres (planeta nan): 469.73 km, posició 10", linies.get(1), "El format de la segona línia no és correcte.");
        assertEquals("Eris (planeta nan): 1163.0 km, posició 11", linies.get(2), "El format de la tercera línia no és correcte.");
    }

    // ------------------------------------------------------------------------
    // Tests de tècnica: comproven que el fitxer s'escriu i es llegeix amb les
    // classes obligatòries de l'enunciat (DataOutputStream i DataInputStream).
    // ------------------------------------------------------------------------

    @Test
    public void testTecnicaEscriuLlista() throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici2", "escriuLlista");
        assertTrue(Tecnica.usaClasse(codi, "DataOutputStream"),
                "Tècnica: escriuLlista ha de fer servir la classe DataOutputStream.");
        assertFalse(Tecnica.usaAlgunaClasse(codi, "ObjectOutputStream", "RandomAccessFile"),
                "Tècnica: escriuLlista no pot fer servir ObjectOutputStream ni RandomAccessFile.");
    }

    @Test
    public void testTecnicaLlegeixLlista() throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici2", "llegeixLlista");
        assertTrue(Tecnica.usaClasse(codi, "DataInputStream"),
                "Tècnica: llegeixLlista ha de fer servir la classe DataInputStream.");
        assertFalse(Tecnica.usaAlgunaClasse(codi, "ObjectInputStream", "RandomAccessFile"),
                "Tècnica: llegeixLlista no pot fer servir ObjectInputStream ni RandomAccessFile.");
    }
}
