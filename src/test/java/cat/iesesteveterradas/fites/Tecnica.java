package cat.iesesteveterradas.fites;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitats per als tests de tècnica.
 *
 * Un test de tècnica no executa el codi de l'exercici: llegeix el fitxer .java,
 * en treu els comentaris i el contingut de les cadenes de text, i comprova quines
 * classes fa servir cada mètode. Així es verifica que l'exercici s'ha resolt amb
 * les classes obligatòries de l'enunciat.
 *
 * NO MODIFIQUIS AQUESTA CLASSE.
 */
final class Tecnica {

    private static final Pattern CAPCALERA_METODE =
            Pattern.compile("(\\w+)\\s*\\([^()]*\\)\\s*(?:throws\\s+[\\w.,\\s]+)?$");

    private Tecnica() {
    }

    /**
     * Retorna el codi del mètode indicat i el dels mètodes de la mateixa classe que
     * crida (directament o indirectament), sense comentaris ni text de cadenes.
     * Retorna una cadena buida si el mètode no existeix.
     */
    static String codiDelMetode(String classe, String metode) throws IOException {
        Path fitxer = Path.of(System.getProperty("user.dir"), "src", "main", "java",
                "cat", "iesesteveterradas", "fites", classe + ".java");
        String font;
        try (FileInputStream entrada = new FileInputStream(fitxer.toFile())) {
            font = netejar(new String(entrada.readAllBytes(), StandardCharsets.UTF_8));
        }
        Map<String, String> metodes = metodesDeLaClasse(font);

        StringBuilder codi = new StringBuilder();
        Set<String> visitats = new LinkedHashSet<>();
        ArrayDeque<String> pendents = new ArrayDeque<>();
        pendents.add(metode);
        while (!pendents.isEmpty()) {
            String nom = pendents.poll();
            if (!visitats.add(nom) || !metodes.containsKey(nom)) {
                continue;
            }
            String cos = metodes.get(nom);
            codi.append(cos).append('\n');
            for (String altre : metodes.keySet()) {
                if (!visitats.contains(altre) && Pattern.compile("(?<!\\w)" + altre + "\\s*\\(").matcher(cos).find()) {
                    pendents.add(altre);
                }
            }
        }
        return codi.toString();
    }

    /** Indica si el codi fa servir la classe (pel seu nom simple, com a paraula sencera). */
    static boolean usaClasse(String codi, String classe) {
        return Pattern.compile("(?<!\\w)" + Pattern.quote(classe) + "(?!\\w)").matcher(codi).find();
    }

    /** Indica si el codi fa servir alguna de les classes indicades. */
    static boolean usaAlgunaClasse(String codi, String... classes) {
        for (String classe : classes) {
            if (usaClasse(codi, classe)) {
                return true;
            }
        }
        return false;
    }

    /** Indica si el codi crida un mètode amb aquest nom sobre qualsevol objecte. */
    static boolean cridaMetode(String codi, String metode) {
        return Pattern.compile("(?<!\\w)" + Pattern.quote(metode) + "\\s*\\(").matcher(codi).find();
    }

    // Treu els comentaris i buida el contingut de les cadenes i dels caràcters literals
    private static String netejar(String font) {
        StringBuilder net = new StringBuilder(font.length());
        int i = 0;
        int n = font.length();
        while (i < n) {
            char c = font.charAt(i);
            if (font.startsWith("//", i)) {
                while (i < n && font.charAt(i) != '\n') {
                    i++;
                }
            } else if (font.startsWith("/*", i)) {
                int fi = font.indexOf("*/", i + 2);
                i = (fi < 0) ? n : fi + 2;
                net.append(' ');
            } else if (font.startsWith("\"\"\"", i)) {
                int fi = font.indexOf("\"\"\"", i + 3);
                i = (fi < 0) ? n : fi + 3;
                net.append("\"\"");
            } else if (c == '"' || c == '\'') {
                i++;
                while (i < n && font.charAt(i) != c && font.charAt(i) != '\n') {
                    i += (font.charAt(i) == '\\') ? 2 : 1;
                }
                i++;
                net.append(c).append(c);
            } else {
                net.append(c);
                i++;
            }
        }
        return net.toString();
    }

    // Retorna els mètodes declarats directament a la classe: nom -> cos
    private static Map<String, String> metodesDeLaClasse(String font) {
        Map<String, String> metodes = new LinkedHashMap<>();
        int profunditat = 0;
        int iniciMembre = 0;
        int i = 0;
        int n = font.length();
        while (i < n) {
            char c = font.charAt(i);
            if (c == '{') {
                if (profunditat == 1) {
                    Matcher m = CAPCALERA_METODE.matcher(font.substring(iniciMembre, i).strip());
                    int fi = clauDeTancament(font, i);
                    if (m.find()) {
                        metodes.merge(m.group(1), font.substring(i + 1, fi), (a, b) -> a + "\n" + b);
                    }
                    i = fi + 1;
                    iniciMembre = i;
                    continue;
                }
                profunditat++;
                iniciMembre = i + 1;
            } else if (c == '}') {
                profunditat--;
                iniciMembre = i + 1;
            } else if (c == ';' && profunditat == 1) {
                iniciMembre = i + 1;
            }
            i++;
        }
        return metodes;
    }

    // Posició de la clau que tanca el bloc que comença a la posició indicada
    private static int clauDeTancament(String font, int obertura) {
        int profunditat = 0;
        for (int i = obertura; i < font.length(); i++) {
            if (font.charAt(i) == '{') {
                profunditat++;
            } else if (font.charAt(i) == '}') {
                profunditat--;
                if (profunditat == 0) {
                    return i;
                }
            }
        }
        return font.length() - 1;
    }
}
