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
     * Se viene fornito un argomento da riga di comando, lo interpreta come percorso di un file di input;
     * altrimenti legge dallo standard input (System.in).
     *
     * @param args argomenti da riga di comando (opzionale: percorso del file di input)
     */
    public static void main(String[] args) {
        // Se e' stato specificato un argomento, lo interpreta come file di input
        if (args.length > 0) {
            // try-with-resources per la chiusura automatica del file anche in caso di errori
            try (InputStream fileIn = new FileInputStream(args[0])) {
                processInput(fileIn, System.out);
            } catch (FileNotFoundException e) {
                // se il percorso indicato non corrisponde ad alcun file esistente
                System.err.println("Errore: file di input non trovato: " + args[0]);
            } catch (Exception e) {
                // Cattura eventuali altri errori generici durante la lettura del file
                System.err.println("Errore durante la lettura del file: " + e.getMessage());
            }
        } else {
            // Se non è stato specificato nessun file, legge da tastiera con System.in
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
        out.println();
        out.println("Avviato Progetto Laboratorio di Algoritmi - Autocomplete Trie");
        out.println();

        // Inizializza lo scanner per leggere l'input e crea un nuovo albero Trie
        Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
        Trie trie = new Trie();

        // Lettura del numero iniziale di parole (prima riga dell'input)
        int initialWordsCount = 0;
        while (scanner.hasNextLine()) {
            String firstLine = scanner.nextLine().trim();
            if (!firstLine.isEmpty()) {
                try {
                    initialWordsCount = Integer.parseInt(firstLine);
                } catch (NumberFormatException e) {
                    // Se la prima riga non contiene un numero valido, stampa errore e termina
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
                try {
                    trie.insert(word);
                    inserted++;
                } catch (IllegalArgumentException e) {
                    // Se la parola contiene caratteri non supportati stampa l'errore
                    out.println("Errore: " + e.getMessage());
                }
            }
        }

        // Elaborazione dei comandi PREFIX <prefisso> e INSERT <parola>
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            // Divide la riga in due parti -> il nome del comando e il relativo argomento
            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toUpperCase();
            String argument = (parts.length > 1) ? parts[1].trim() : "";

            switch (command) {
                case "PREFIX":
                    // Cerca nel Trie tutte le parole che iniziano con il prefisso indicato
                    out.println("Suggerimenti per " + argument + ":");
                    List<String> suggestions = trie.searchByPrefix(argument);
                    if (suggestions.isEmpty()) {
                        out.println("(nessun suggerimento)");
                    } else {
                        // Stampa i suggerimenti trovati in ordine alfabetico
                        for (String suggestion : suggestions) {
                            out.println(suggestion);
                        }
                    }
                    break;

                case "INSERT":
                    // Inserisce una nuova parola nel Trie
                    if (!argument.isEmpty()) {
                        try {
                            trie.insert(argument);
                            out.println("Parola inserita: " + argument);
                        } catch (IllegalArgumentException e) {
                            // Se la parola da inserire non è valida, mostra l'errore
                            out.println("Errore: " + e.getMessage());
                        }
                    }
                    break;

                default:
                    // Se il comando inserito non è né PREFIX né INSERT
                    out.println("Comando non riconosciuto: " + command);
                    break;
            }
        }
    }
}
