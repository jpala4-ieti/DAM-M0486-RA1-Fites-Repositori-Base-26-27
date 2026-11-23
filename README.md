# MP0486 RA1 Fites (curs 26-27) #

[![Compilació](https://github.com/jpala4-ieti/DAM-M0486-RA1-Fites-Repositori-Base-26-27/actions/workflows/maven.yml/badge.svg)](https://github.com/jpala4-ieti/DAM-M0486-RA1-Fites-Repositori-Base-26-27/actions/workflows/maven.yml)

Repositori base de la fita del RA1 (gestió de fitxers). Cal completar els mètodes marcats amb
`CODI EXERCICI FITA` a `Exercici0`, `Exercici1`, `Exercici2` i `Exercici3`.
L'enunciat és a [ENUNCIAT.md](ENUNCIAT.md) i les condicions de lliurament a [INSTRUCCIONS.md](INSTRUCCIONS.md).

## Comprovació automàtica ##

La comprovació de GitHub Actions verifica que el projecte compila. Els tests també s'hi
executen, però només de manera informativa, perquè al repositori base fallen fins que
s'implementen els exercicis.

## Requisits ##

Java 21 i Maven.

## Arrencada ràpida ##

### Windows ###
```bash
.\run.ps1 cat.iesesteveterradas.fites.FitesMain
.\run.ps1 cat.iesesteveterradas.fites.Exercici0
.\run.ps1 cat.iesesteveterradas.fites.Exercici1
.\run.ps1 cat.iesesteveterradas.fites.Exercici2
.\run.ps1 cat.iesesteveterradas.fites.Exercici3
```

### Linux / macOS ###
```bash
./run.sh cat.iesesteveterradas.fites.FitesMain
./run.sh cat.iesesteveterradas.fites.Exercici0
./run.sh cat.iesesteveterradas.fites.Exercici1
./run.sh cat.iesesteveterradas.fites.Exercici2
./run.sh cat.iesesteveterradas.fites.Exercici3
```

`FitesMain` mostra un menú per triar l'exercici. Els scripts compilen, passen els tests i
executen la classe indicada.

## Execució pas a pas ##

```bash
mvn clean compile
mvn exec:java -PrunMain "-Dexec.mainClass=cat.iesesteveterradas.fites.Exercici0"
```

## Tests ##

```bash
# Tots els tests
mvn test
# Els tests d'un exercici
mvn test -Dtest=Exercici0Test
# Un sol test
mvn test -Dtest="Exercici1Test#testComptaVocals"
# Només els tests de tècnica d'un exercici
mvn test -Dtest="Exercici0Test#testTecnica*"
# Diversos exercicis
mvn test -Dtest="Exercici2Test,Exercici3Test"
```

Hi ha 15 tests per a l'exercici 0, 9 per a l'1, 8 per al 2 i 9 per al 3. Al repositori
base fallen tots fins que s'implementen els mètodes.

Els tests anomenats `testTecnica...` són tests de tècnica: no executen el codi, sinó que
llegeixen el fitxer `.java` i comproven que cada mètode fa servir les classes obligatòries
de l'enunciat. Les classes `Tecnica` i `Consola` (a `src/test`) donen suport als tests i no
s'han de modificar.

## Versions ##

| Component | Versió |
|---|---|
| Java | 21 |
| Jakarta JSON Processing (API) | 2.1.3 |
| Eclipse Parsson | 1.1.7 |
| org.json (només tests) | 20240303 |
| JUnit Jupiter | 6.1.3 |