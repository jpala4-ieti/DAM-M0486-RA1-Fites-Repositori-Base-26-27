package cat.iesesteveterradas.fites;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;


/**
 * Dificultat: mitjana-alta
 *
 * Implementa el codi que realitzi el següent:
 *
 * - Llegeix el document XML 'Exercici3.xml', que conté informació sobre videojocs.
 * - N'extreu una llista amb el títol, el gènere, l'any i la plataforma de cada videojoc,
 *   en aquest ordre i en el mateix ordre en què apareixen al document.
 * - Guarda aquesta informació en un fitxer JSON a la ruta especificada, amb el nom 'Exercici3.json'.
 * - Per crear el fitxer JSON cal usar Jakarta.
 * - Gestió d'errors: si hi ha algun problema en llegir l'XML o en escriure el JSON, mostra
 *   l'excepció a la consola.
 *
 * Classes obligatòries (els tests de tècnica comproven que cada mètode les fa servir):
 *
 * - llegirDocumentXML:
 *     DocumentBuilderFactory: https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/javax/xml/parsers/DocumentBuilderFactory.html
 *     DocumentBuilder:        https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/javax/xml/parsers/DocumentBuilder.html
 *     Document:               https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/org/w3c/dom/Document.html
 * - extreureLlista:
 *     NodeList: https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/org/w3c/dom/NodeList.html
 *     Element:  https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/org/w3c/dom/Element.html
 * - guardarJSON:
 *     Json:              https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/Json.html
 *     JsonObjectBuilder: https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/JsonObjectBuilder.html
 *     JsonArrayBuilder:  https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/JsonArrayBuilder.html
 *     JsonWriter:        https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/JsonWriter.html
 *
 * El JSON només es pot generar amb Jakarta: NO es pot fer servir cap altra biblioteca.

 Document d'entrada (data/exercici3/Exercici3.xml):

<?xml version="1.0" encoding="UTF-8"?>
<videojocs>
    <videojoc any="1985" plataforma="NES">
        <titol>Super Mario Bros.</titol>
        <genere>Plataformes</genere>
    </videojoc>
    <videojoc any="2011" plataforma="PC">
        <titol>Portal 2</titol>
        <genere>Puzle</genere>
    </videojoc>
    ...
</videojocs>

 Document esperat (el contingut és important, el format bonic no es té en compte).
 Fixa't que "total" i "any" són números, no cadenes de text:

{
    "total": 5,
    "videojocs": [
        {
            "titol": "Super Mario Bros.",
            "genere": "Plataformes",
            "any": 1985,
            "plataforma": "NES"
        },
        {
            "titol": "Portal 2",
            "genere": "Puzle",
            "any": 2011,
            "plataforma": "PC"
        },
        {
            "titol": "The Legend of Zelda: Breath of the Wild",
            "genere": "Aventura",
            "any": 2017,
            "plataforma": "Switch"
        },
        {
            "titol": "Hollow Knight",
            "genere": "Metroidvania",
            "any": 2017,
            "plataforma": "PC"
        },
        {
            "titol": "Celeste",
            "genere": "Plataformes",
            "any": 2018,
            "plataforma": "PC"
        }
    ]
}
*/

public class Exercici3 {
    private String filePathXML;
    private String filePathJson;

    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + "/data/exercici3/";
        String filePathXML = basePath + "Exercici3.xml";
        String filePathJson = basePath + "Exercici3.json";

        Exercici3 exercici = new Exercici3();
        exercici.configuraPathFitxerEntradaXML(filePathXML);
        exercici.configuraPathFitxerSortidaJson(filePathJson);
        exercici.executa();
    }

    // Mètode que executa la lògica de la classe
    public void executa() {
        // Llegir el document XML
        Document doc = llegirDocumentXML(filePathXML);

        // Extreure'n la llista de videojocs: {títol, gènere, any, plataforma}
        ArrayList<String[]> llista = extreureLlista(doc);

        // Crear i guardar el document JSON
        guardarJSON(filePathJson, llista);
    }

    // Llegeix el fitxer XML i retorna el Document (null si hi ha algun error)
    public Document llegirDocumentXML(String path) {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    // Retorna una llista amb un String[] per videojoc: {títol, gènere, any, plataforma}
    public ArrayList<String[]> extreureLlista(Document doc) {
        // *************** CODI EXERCICI FITA **********************/
        return null; // A substituir
    }

    // Escriu la llista en un fitxer JSON utilitzant Jakarta
    public void guardarJSON(String path, ArrayList<String[]> llista) {
        // *************** CODI EXERCICI FITA **********************/
    }

    /****************************************************************************/
    /*                              NO CAL TOCAR                                */
    /****************************************************************************/
    // Retorna els nodes d'una expressió XPath
    public static NodeList getNodeList(Document doc, String expression) {
        NodeList llista = null;
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            llista = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return llista;
    }

    // Setter per definir el path XML
    public void configuraPathFitxerEntradaXML(String filePath) {
        this.filePathXML = filePath;
    }

    // Setter per definir el path JSON
    public void configuraPathFitxerSortidaJson(String filePath) {
        this.filePathJson = filePath;
    }

    // Getter per obtenir el path XML
    public String obtenirRutaFitxerEntradaXML() {
        return this.filePathXML;
    }

    // Getter per obtenir el path JSON
    public String obtenirRutaFitxerSortidaJson() {
        return this.filePathJson;
    }
}