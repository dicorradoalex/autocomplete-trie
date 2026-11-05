package it.unipa.algoritmi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principale per l'esecuzione del sistema di completamento automatico basato su Trie.
 */
public class Main {

    /**
     * Classe Main.
     * Se viene fornito un argomento da riga di comando, lo interpreta come percorso di un file di input;
     * altrimenti legge dallo standard input (System.in).
     *
     * @param args argomenti da riga di comando (opzionale: percorso del file di input)
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            try (InputStream fileIn = new FileInputStream(args[0])) {
                processInput(fileIn, System.out);
            } catch (FileNotFoundException e) {
                System.err.println("Errore: file di input non trovato: " + args[0]);
            } catch (Exception e) {
                System.err.println("Errore durante la lettura del file: " + e.getMessage());
            }
        } else {
            processInput(System.in, System.out);
        }
    }

    /**
     * Elabora l'input costruendo il Trie ed eseguendo i comandi PREFIX e INSERT.
     *
     * @param in  flusso di input (es. System.in o FileInputStream)
     * @param out flusso di output (es. System.out)
     */
    public static void processInput(InputStream in, PrintStream out) {
        Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
        Trie trie = new Trie();

        // Lettura della prima riga: numero iniziale di parole
        int initialWordsCount = 0;
        while (scanner.hasNextLine()) {
            String firstLine = scanner.nextLine().trim();
            if (!firstLine.isEmpty()) {
                try {
                    initialWordsCount = Integer.parseInt(firstLine);
                } catch (NumberFormatException e) {
                    out.println("Errore di formato: la prima riga deve contenere un numero intero " +
                            "(numero di parole iniziali). Valore ricevuto: '" + firstLine + "'");
                    return;
                }
                break;
            }
        }

        // Inserimento delle N parole iniziali nel Trie
        int inserted = 0;
        while (inserted < initialWordsCount && scanner.hasNextLine()) {
            String word = scanner.nextLine().trim();
            if (!word.isEmpty()) {
                trie.insert(word);
                inserted++;
            }
        }

        // Elaborazione dei comandi successivi: PREFIX <prefisso> e INSERT <parola>
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toUpperCase();
            String argument = (parts.length > 1) ? parts[1].trim() : "";

            switch (command) {
                case "PREFIX":
                    out.println("Suggerimenti per " + argument + ":");
                    List<String> suggestions = trie.searchByPrefix(argument);
                    if (suggestions.isEmpty()) {
                        out.println("(nessun suggerimento)");
                    } else {
                        for (String suggestion : suggestions) {
                            out.println(suggestion);
                        }
                    }
                    break;

                case "INSERT":
                    if (!argument.isEmpty()) {
                        trie.insert(argument);
                        out.println("Parola inserita: " + argument);
                    }
                    break;

                default:
                    out.println("Comando non riconosciuto: " + command);
                    break;
            }
        }
    }
}