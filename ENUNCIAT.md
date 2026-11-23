# Fita RA1 (curs 26-27): enunciat #

Cal completar els mètodes marcats amb `CODI EXERCICI FITA`. L'enunciat detallat de cada
exercici és al comentari inicial de la seva classe; aquest document en fa el resum.
No es poden canviar les signatures dels mètodes ni el codi marcat com a "NO CAL MODIFICAR".

Tots els exercicis puntuen el mateix.

## Classes obligatòries i tests de tècnica ##

Cada exercici indica les **classes obligatòries** amb l'enllaç a la seva documentació.
Cal resoldre'l amb aquestes classes: busca-hi els mètodes que necessites.

Cada exercici té dos tipus de tests:

- **Tests de comportament**: executen el teu codi i comproven que el resultat és correcte.
- **Tests de tècnica** (`testTecnica...`): llegeixen el teu fitxer `.java` i comproven que cada
  mètode fa servir les classes obligatòries i no les prohibides. Miren el codi del mètode i el
  dels mètodes propis que cridi; els comentaris i el text de les cadenes no compten.

Si resols un exercici amb unes altres classes, el programa pot funcionar i passar els tests de
comportament, però els tests de tècnica fallaran.

## Exercici 0: informació d'un directori i d'un fitxer ##

**Dificultat: baixa**

Classe `Exercici0`. Treballa amb el directori `data/exercici0` i el fitxer `data/exercici0/Exercici0.dat`.

| Mètode | Què ha de retornar |
|---|---|
| `comprovaEsDirectori()` | Si la ruta del directori existeix i és un directori |
| `determinaSiEsPotLlegir()` | Si el directori es pot llegir |
| `obtenirMidaEnBytes()` | La mida en bytes del fitxer de dades (`null` si no es pot obtenir) |
| `obtenirNomDirectoriPare()` | El nom (no la ruta) del directori pare del directori |
| `llistarArxiusDirectori()` | Els noms dels arxius i carpetes del directori, o `null` si no és un directori |

Classes obligatòries:

- `Path`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html
- `Files`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html

No es pot fer servir la classe `java.io.File`.

## Exercici 1: lectura i escriptura de text ##

**Dificultat: baixa-mitjana**

Classe `Exercici1`. Llegeix `data/exercici1/Exercici1Entrada.txt` i genera `Exercici1Solucio.txt`, tots dos en UTF-8.

| Mètode | Què ha de fer |
|---|---|
| `comptaVocals(String text)` | Retorna el nombre de vocals del text. Compten a, e, i, o, u, amb accent o dièresi o sense, en minúscules i en majúscules |
| `executa()` | Escriu cada línia precedida del seu recompte (`[3] Hola món`; una línia buida s'escriu amb `[0]`) i una última línia `TOTAL: n`. Si hi ha un error en llegir o escriure, mostra l'excepció a la consola i no la propaga |

Classes obligatòries:

- `Path`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html
- `Files`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html

Tant la lectura com l'escriptura s'han de fer amb mètodes de la classe `Files`. No es poden fer
servir `File`, `Scanner`, `FileReader`, `FileWriter`, `FileInputStream`, `FileOutputStream` ni `PrintWriter`.

## Exercici 2: fitxer binari de dades ##

**Dificultat: mitjana**

Classe `Exercici2`. La classe `Exercici2planeta` no és serialitzable i no es pot modificar.

| Mètode | Què ha de fer |
|---|---|
| `escriuLlista(filePath, llista)` | Desa el nombre de planetes i les dades de cada un |
| `llegeixLlista(filePath)` | Reconstrueix la llista a partir del fitxer |
| `imprimeixLlista(llista)` | Mostra un planeta per línia: `Nom (tipus): radi km, posició n` |

Format del fitxer: el nombre de planetes (`int`) i, per a cada planeta i en aquest ordre, el nom
(`String` en format UTF), el tipus (`String` en format UTF), el radi (`double`) i la posició (`int`).

Classes obligatòries:

- `DataOutputStream`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataOutputStream.html
- `DataInputStream`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/DataInputStream.html
- `FileOutputStream`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileOutputStream.html
- `FileInputStream`: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileInputStream.html

No es poden fer servir `ObjectOutputStream`, `ObjectInputStream` ni `RandomAccessFile`.

## Exercici 3: d'XML a JSON ##

**Dificultat: mitjana-alta**

Classe `Exercici3`. Llegeix `data/exercici3/Exercici3.xml` i genera `Exercici3.json`.

| Mètode | Què ha de fer | Classes obligatòries |
|---|---|---|
| `llegirDocumentXML(path)` | Retorna el `Document` del fitxer XML. Si hi ha un error, mostra l'excepció a la consola i retorna `null` | `DocumentBuilderFactory`, `DocumentBuilder`, `Document` |
| `extreureLlista(doc)` | Retorna un `String[]` per videojoc: `{títol, gènere, any, plataforma}` | `NodeList`, `Element` |
| `guardarJSON(path, llista)` | Escriu un objecte JSON amb `total` (número) i l'array `videojocs`, on `any` és un número | `Json`, `JsonObjectBuilder`, `JsonArrayBuilder`, `JsonWriter` |

Documentació de les classes obligatòries:

- `DocumentBuilderFactory`: https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/javax/xml/parsers/DocumentBuilderFactory.html
- `DocumentBuilder`: https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/javax/xml/parsers/DocumentBuilder.html
- `Document`: https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/org/w3c/dom/Document.html
- `NodeList`: https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/org/w3c/dom/NodeList.html
- `Element`: https://docs.oracle.com/en/java/javase/21/docs/api/java.xml/org/w3c/dom/Element.html
- `Json`: https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/Json.html
- `JsonObjectBuilder`: https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/JsonObjectBuilder.html
- `JsonArrayBuilder`: https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/JsonArrayBuilder.html
- `JsonWriter`: https://jakarta.ee/specifications/jsonp/2.1/apidocs/jakarta.json/jakarta/json/JsonWriter.html

El JSON només es pot generar amb Jakarta: no es pot fer servir cap altra biblioteca.

## Comprovació ##

```bash
mvn test
```

| Exercici | Tests de comportament | Tests de tècnica | Total |
|---|---|---|---|
| 0 | 10 | 5 | 15 |
| 1 | 7 | 2 | 9 |
| 2 | 6 | 2 | 8 |
| 3 | 6 | 3 | 9 |

Les condicions de lliurament són a [INSTRUCCIONS.md](INSTRUCCIONS.md).