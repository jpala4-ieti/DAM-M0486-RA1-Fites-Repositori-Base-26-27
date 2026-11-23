package cat.iesesteveterradas.fites;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Utilitat per als tests: executa una acció i captura tot el que escriu per pantalla
 * (sortida estàndard i sortida d'error), de manera que no apareix a la consola.
 *
 * Es fa servir als tests que provoquen un error expressament o que imprimeixen dades,
 * per no omplir la sortida de `mvn test` de traces i per poder comprovar què s'ha mostrat.
 *
 * NO MODIFIQUIS AQUESTA CLASSE.
 */
final class Consola {

    private Consola() {
    }

    /** Executa l'acció i retorna el text que ha escrit a System.out i a System.err. */
    static String captura(Runnable accio) {
        PrintStream sortidaOriginal = System.out;
        PrintStream errorOriginal = System.err;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream captura = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        System.setOut(captura);
        System.setErr(captura);
        try {
            accio.run();
        } finally {
            captura.flush();
            System.setOut(sortidaOriginal);
            System.setErr(errorOriginal);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
