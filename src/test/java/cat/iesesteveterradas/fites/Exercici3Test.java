package cat.iesesteveterradas.fites;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class Exercici3Test {

    private Exercici3 exercici;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        exercici = new Exercici3();
        String filePathXML = System.getProperty("user.dir") + "/data/exercici3/Exercici3.xml";
        String tempFilePathJson = tempDir.resolve("Exercici3Test.json").toString();
        exercici.configuraPathFitxerEntradaXML(filePathXML);
        exercici.configuraPathFitxerSortidaJson(tempFilePathJson);
    }

    @Test
    public void testExecuta() {
        // Comprovar la lectura del document XML
        Document doc = exercici.llegirDocumentXML(exercici.obtenirRutaFitxerEntradaXML());
        assertNotNull(doc, "El document XML no s'ha pogut llegir.");

        // Comprovar la llista extreta del document
        ArrayList<String[]> llista = exercici.extreureLlista(doc);
        assertNotNull(llista, "La llista extreta del document XML no hauria de ser nul·la.");
        assertEquals(5, llista.size(), "La llista hauria de tenir 5 videojocs.");
        assertArrayEquals(new String[]{"Super Mario Bros.", "Plataformes", "1985", "NES"}, llista.get(0),
                "El primer videojoc hauria de ser {títol, gènere, any, plataforma} de Super Mario Bros.");
        assertArrayEquals(new String[]{"Celeste", "Plataformes", "2018", "PC"}, llista.get(4),
                "L'últim videojoc hauria de ser {títol, gènere, any, plataforma} de Celeste.");

        // Executa la lògica completa de la classe
        exercici.executa();

        // Comprovar que el fitxer JSON s'ha creat
        File fitxerJson = new File(exercici.obtenirRutaFitxerSortidaJson());
        assertTrue(fitxerJson.exists(), "El fitxer JSON de sortida no existeix.");

        // Carregar el fitxer JSON generat per validar-ne el contingut
        try {
            String jsonContent = Files.readString(Path.of(exercici.obtenirRutaFitxerSortidaJson()), StandardCharsets.UTF_8);
            JSONObject arrel = new JSONObject(jsonContent);

            // "total" ha de ser un número
            assertTrue(arrel.get("total") instanceof Number, "El camp total hauria de ser un número, no una cadena.");
            assertEquals(5, arrel.getInt("total"), "El camp total hauria de ser 5.");

            // Comprovar que l'array té 5 elements
            JSONArray videojocs = arrel.getJSONArray("videojocs");
            assertEquals(5, videojocs.length(), "Hi hauria d'haver 5 elements a l'array videojocs.");

            // Comprovar el tercer element
            JSONObject tercer = videojocs.getJSONObject(2);
            assertEquals("The Legend of Zelda: Breath of the Wild", tercer.getString("titol"), "El títol del tercer videojoc no és correcte.");
            assertEquals("Aventura", tercer.getString("genere"), "El gènere del tercer videojoc hauria de ser Aventura.");
            assertTrue(tercer.get("any") instanceof Number, "El camp any hauria de ser un número, no una cadena.");
            assertEquals(2017, tercer.getInt("any"), "L'any del tercer videojoc hauria de ser 2017.");
            assertEquals("Switch", tercer.getString("plataforma"), "La plataforma del tercer videojoc hauria de ser Switch.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error en llegir o analitzar el fitxer JSON.");
        }
    }

    // ------------------------------------------------------------------------
    // Tests addicionals: comproven cada mètode per separat
    // ------------------------------------------------------------------------

    private static final String[][] VIDEOJOCS_ESPERATS = {
        {"Super Mario Bros.", "Plataformes", "1985", "NES"},
        {"Portal 2", "Puzle", "2011", "PC"},
        {"The Legend of Zelda: Breath of the Wild", "Aventura", "2017", "Switch"},
        {"Hollow Knight", "Metroidvania", "2017", "PC"},
        {"Celeste", "Plataformes", "2018", "PC"}
    };

    @Test
    public void testLlegirDocumentXMLEstructura() {
        Document doc = exercici.llegirDocumentXML(exercici.obtenirRutaFitxerEntradaXML());
        assertNotNull(doc, "El document XML no s'ha pogut llegir.");
        assertEquals("videojocs", doc.getDocumentElement().getNodeName(), "L'element arrel del document hauria de ser videojocs.");
        assertEquals(5, doc.getElementsByTagName("videojoc").getLength(), "El document hauria de tenir 5 elements videojoc.");
    }

    @Test
    public void testLlegirDocumentXMLAmbError() throws IOException {
        // Un fitxer correcte s'ha de poder llegir
        Document correcte = exercici.llegirDocumentXML(exercici.obtenirRutaFitxerEntradaXML());
        assertNotNull(correcte, "El document XML no s'ha pogut llegir.");

        // Fitxer que no existeix (l'excepció que es mostra no apareix a la consola)
        String rutaInexistent = tempDir.resolve("noExisteix.xml").toString();
        Document[] inexistent = new Document[1];
        String sortida = Consola.captura(() -> inexistent[0] = exercici.llegirDocumentXML(rutaInexistent));
        assertNull(inexistent[0], "Si el fitxer no existeix, llegirDocumentXML ha de retornar null.");
        assertFalse(sortida.isBlank(), "Si el fitxer no existeix, llegirDocumentXML ha de mostrar l'excepció a la consola.");

        // Fitxer que no és XML ben format
        Path malFormat = tempDir.resolve("malFormat.xml");
        Files.writeString(malFormat, "<videojocs><videojoc></videojocs>", StandardCharsets.UTF_8);
        Document[] incorrecte = new Document[1];
        Consola.captura(() -> incorrecte[0] = exercici.llegirDocumentXML(malFormat.toString()));
        assertNull(incorrecte[0], "Si l'XML no està ben format, llegirDocumentXML ha de retornar null.");
    }

    @Test
    public void testExtreureLlistaAltreDocument() throws IOException {
        // Un document diferent, amb la mateixa estructura
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<videojocs>\n"
                + "    <videojoc any=\"1996\" plataforma=\"Game Boy\">\n"
                + "        <titol>Pokémon Vermell</titol>\n"
                + "        <genere>Rol</genere>\n"
                + "    </videojoc>\n"
                + "    <videojoc any=\"1984\" plataforma=\"Electronika 60\">\n"
                + "        <titol>Tetris</titol>\n"
                + "        <genere>Puzle</genere>\n"
                + "    </videojoc>\n"
                + "    <videojoc any=\"2011\" plataforma=\"PC\">\n"
                + "        <titol>Minecraft</titol>\n"
                + "        <genere>Construcció</genere>\n"
                + "    </videojoc>\n"
                + "</videojocs>\n";
        Path altreXML = tempDir.resolve("altres.xml");
        Files.writeString(altreXML, xml, StandardCharsets.UTF_8);

        Document doc = exercici.llegirDocumentXML(altreXML.toString());
        assertNotNull(doc, "El document XML no s'ha pogut llegir.");

        ArrayList<String[]> llista = exercici.extreureLlista(doc);
        assertNotNull(llista, "La llista extreta del document XML no hauria de ser nul·la.");
        assertEquals(3, llista.size(), "La llista hauria de tenir 3 videojocs.");
        assertArrayEquals(new String[]{"Pokémon Vermell", "Rol", "1996", "Game Boy"}, llista.get(0), "El primer videojoc no és correcte.");
        assertArrayEquals(new String[]{"Tetris", "Puzle", "1984", "Electronika 60"}, llista.get(1), "El segon videojoc no és correcte.");
        assertArrayEquals(new String[]{"Minecraft", "Construcció", "2011", "PC"}, llista.get(2), "El tercer videojoc no és correcte.");
    }

    @Test
    public void testGuardarJSONLlistaPropia() throws IOException {
        // guardarJSON es comprova sense passar per l'XML
        ArrayList<String[]> llista = new ArrayList<>();
        llista.add(new String[]{"Pokémon Vermell", "Rol", "1996", "Game Boy"});
        llista.add(new String[]{"Tetris", "Puzle", "1984", "Electronika 60"});

        String ruta = tempDir.resolve("propi.json").toString();
        exercici.guardarJSON(ruta, llista);
        assertTrue(new File(ruta).exists(), "El fitxer JSON de sortida no existeix.");

        JSONObject arrel = new JSONObject(Files.readString(Path.of(ruta), StandardCharsets.UTF_8));
        assertTrue(arrel.get("total") instanceof Number, "El camp total hauria de ser un número, no una cadena.");
        assertEquals(2, arrel.getInt("total"), "El camp total hauria de ser el nombre de videojocs de la llista.");

        JSONArray videojocs = arrel.getJSONArray("videojocs");
        assertEquals(2, videojocs.length(), "Hi hauria d'haver 2 elements a l'array videojocs.");

        JSONObject primer = videojocs.getJSONObject(0);
        assertEquals("Pokémon Vermell", primer.getString("titol"), "El títol del primer videojoc no és correcte (revisa la codificació UTF-8).");
        assertEquals("Rol", primer.getString("genere"), "El gènere del primer videojoc no és correcte.");
        assertTrue(primer.get("any") instanceof Number, "El camp any hauria de ser un número, no una cadena.");
        assertEquals(1996, primer.getInt("any"), "L'any del primer videojoc no és correcte.");
        assertEquals("Game Boy", primer.getString("plataforma"), "La plataforma del primer videojoc no és correcta.");

        JSONObject segon = videojocs.getJSONObject(1);
        assertEquals("Tetris", segon.getString("titol"), "El títol del segon videojoc no és correcte.");
        assertEquals(1984, segon.getInt("any"), "L'any del segon videojoc no és correcte.");
    }

    @Test
    public void testJSONTotsElsVideojocs() throws IOException {
        exercici.executa();

        File fitxerJson = new File(exercici.obtenirRutaFitxerSortidaJson());
        assertTrue(fitxerJson.exists(), "El fitxer JSON de sortida no existeix.");

        JSONObject arrel = new JSONObject(Files.readString(fitxerJson.toPath(), StandardCharsets.UTF_8));
        assertEquals(2, arrel.length(), "L'objecte arrel només ha de tenir els camps total i videojocs.");

        JSONArray videojocs = arrel.getJSONArray("videojocs");
        assertEquals(VIDEOJOCS_ESPERATS.length, videojocs.length(), "Hi hauria d'haver 5 elements a l'array videojocs.");

        // Tots els videojocs, en el mateix ordre que al document XML
        for (int i = 0; i < VIDEOJOCS_ESPERATS.length; i++) {
            String[] esperat = VIDEOJOCS_ESPERATS[i];
            JSONObject videojoc = videojocs.getJSONObject(i);
            assertEquals(4, videojoc.length(), "Cada videojoc només ha de tenir els camps titol, genere, any i plataforma (posició " + i + ").");
            assertEquals(esperat[0], videojoc.getString("titol"), "El títol del videojoc de la posició " + i + " no és correcte.");
            assertEquals(esperat[1], videojoc.getString("genere"), "El gènere del videojoc de la posició " + i + " no és correcte.");
            assertTrue(videojoc.get("any") instanceof Number, "El camp any hauria de ser un número, no una cadena (posició " + i + ").");
            assertEquals(Integer.parseInt(esperat[2]), videojoc.getInt("any"), "L'any del videojoc de la posició " + i + " no és correcte.");
            assertEquals(esperat[3], videojoc.getString("plataforma"), "La plataforma del videojoc de la posició " + i + " no és correcta.");
        }
    }

    // ------------------------------------------------------------------------
    // Tests de tècnica: comproven que cada mètode fa servir les classes
    // obligatòries de l'enunciat (DOM per a l'XML i Jakarta per al JSON).
    // ------------------------------------------------------------------------

    @Test
    public void testTecnicaLlegirDocumentXML() throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici3", "llegirDocumentXML");
        assertTrue(Tecnica.usaClasse(codi, "DocumentBuilderFactory"),
                "Tècnica: llegirDocumentXML ha de fer servir la classe DocumentBuilderFactory.");
        assertTrue(Tecnica.usaClasse(codi, "DocumentBuilder"),
                "Tècnica: llegirDocumentXML ha de fer servir la classe DocumentBuilder.");
    }

    @Test
    public void testTecnicaExtreureLlista() throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici3", "extreureLlista");
        assertTrue(Tecnica.usaClasse(codi, "NodeList"),
                "Tècnica: extreureLlista ha de fer servir la classe NodeList.");
        assertTrue(Tecnica.usaClasse(codi, "Element"),
                "Tècnica: extreureLlista ha de fer servir la classe Element.");
    }

    @Test
    public void testTecnicaGuardarJSON() throws IOException {
        String codi = Tecnica.codiDelMetode("Exercici3", "guardarJSON");
        assertTrue(Tecnica.usaClasse(codi, "Json"),
                "Tècnica: guardarJSON ha de generar el JSON amb les classes de Jakarta (Json, JsonObjectBuilder, JsonArrayBuilder i JsonWriter).");
        assertFalse(Tecnica.usaAlgunaClasse(codi, "JSONObject", "JSONArray", "ObjectMapper", "JsonMapper", "Gson"),
                "Tècnica: guardarJSON només pot fer servir Jakarta per generar el JSON.");
    }
}
