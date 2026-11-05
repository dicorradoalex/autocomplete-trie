package it.unipa.algoritmi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementazione della struttura dati Trie per il completamento automatico.
 */
public class Trie {

    private final TrieNode root;

    /**
     * Inizializza un Trie vuoto creando il solo nodo radice.
     */
    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Restituisce la radice del Trie.
     *
     * @return il nodo radice (root)
     */
    public TrieNode getRoot() {
        return this.root;
    }

    /**
     * Restituisce il nodo figlio associato al carattere c (null se l'arco non esiste).
     *
     * @param v il nodo di partenza
     * @param c il carattere dell'arco
     * @return il nodo figlio corrispondente oppure null
     */
    public TrieNode child(TrieNode v, char c) {
        int index = TrieNode.charToIndex(c);
        return v.children[index];
    }

    /**
     * Collega il nodo u come figlio di v per il carattere c.
     *
     * @param v il nodo padre
     * @param c il carattere dell'arco
     * @param u il nodo figlio da collegare
     */
    private void setChild(TrieNode v, char c, TrieNode u) {
        int index = TrieNode.charToIndex(c);
        v.children[index] = u;
    }

    /**
     * Inserisce una parola nel Trie.
     *
     * @param S la parola da inserire
     * @throws IllegalArgumentException se S e' null, vuota o contiene caratteri non validi
     */
    public void insert(String S) {
        // Controllo che la stringa non sia null
        if (S == null) {
            throw new IllegalArgumentException("La stringa da inserire non puo' essere null.");
        }

        // Rimuove spazi bianchi all'inizio e alla fine e converte in minuscolo
        String normalized = S.trim().toLowerCase();
        int m = normalized.length();
        if (m == 0) {
            throw new IllegalArgumentException("La stringa da inserire non puo' essere vuota.");
        }

        // Verifica che ogni carattere appartenga all'alfabeto supportato ['a'..'z']
        for (int i = 0; i < m; i++) {
            if (!TrieNode.isValidChar(normalized.charAt(i))) {
                throw new IllegalArgumentException(
                        "La parola '" + S + "' contiene il carattere non supportato: '" + normalized.charAt(i) + "'"
                );
            }
        }

        TrieNode v = this.root;
        int j = 0;

        // Scorre i nodi già esistenti finché trova i caratteri del prefisso comune
        while (j < m && child(v, normalized.charAt(j)) != null) {
            v = child(v, normalized.charAt(j));
            j = j + 1;
        }

        // Alloca i nuovi nodi per i caratteri rimanenti della parola
        while (j < m) {
            TrieNode u = new TrieNode();
            setChild(v, normalized.charAt(j), u);
            v = u;
            j = j + 1;
        }

        // Marca il nodo finale per indicare che rappresenta una parola completa
        v.markAsRepresentative();
    }

    /**
     * Restituisce tutte le parole presenti nel Trie che iniziano con il prefisso indicato,
     * ordinate alfabeticamente.
     *
     * @param prefix il prefisso da cercare
     * @return lista ordinata dei suggerimenti (vuota se non ci sono corrispondenze)
     */
    public List<String> searchByPrefix(String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }

        // Rimuove spazi esterni e converte in minuscolo
        String normalizedPrefix = prefix.trim().toLowerCase();
        int pLen = normalizedPrefix.length();
        if (pLen == 0) {
            return Collections.emptyList();
        }

        // Fase 1: Segue il cammino dei caratteri del prefisso partendo dalla radice
        TrieNode v = this.root;
        for (int j = 0; j < pLen; j++) {
            char c = normalizedPrefix.charAt(j);
            if (!TrieNode.isValidChar(c)) {
                // Se contiene un carattere fuori dall'alfabeto, non ci sono risultati
                return Collections.emptyList();
            }
            v = child(v, c);
            if (v == null) {
                // Il prefisso non e' presente nel dizionario: restituisce lista vuota
                return Collections.emptyList();
            }
        }

        // Fase 2: Raccoglie in ordine alfabetico le parole nel sottoalbero con visita DFS
        List<String> suggestions = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder(normalizedPrefix);
        collectWordsDFS(v, currentWord, suggestions);
        return suggestions;
    }

    /**
     * Visita ricorsivamente in pre-ordine il sottoalbero per estrarre le parole.
     * L'esplorazione dei figli in ordine ['a'..'z'] garantisce l'ordinamento alfabetico.
     *
     * @param u           il nodo corrente nella visita
     * @param currentWord buffer contenente i caratteri accumulati dalla radice a u
     * @param suggestions lista su cui accumulare i suggerimenti trovati
     */
    private void collectWordsDFS(TrieNode u, StringBuilder currentWord, List<String> suggestions) {
        // Se il nodo corrente rappresenta una parola completa, la aggiunge ai suggerimenti
        if (u.isRepresentative()) {
            suggestions.add(currentWord.toString());
        }

        // Esplora i figli in ordine alfabetico (da 'a' a 'z')
        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++) {
            TrieNode nextNode = u.children[i];
            if (nextNode != null) {
                // Aggiunge la lettera corrispondente all'arco
                currentWord.append(TrieNode.indexToChar(i));

                // Visita ricorsiva del sottoalbero
                collectWordsDFS(nextNode, currentWord, suggestions);

                // Backtracking: rimuove l'ultimo carattere per esplorare gli altri rami
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    /**
     * Conta ricorsivamente il numero totale di nodi presenti nel Trie.
     *
     * @return il numero complessivo di nodi dell'albero
     */
    public int countNodes() {
        return countNodesRecursive(this.root);
    }

    private int countNodesRecursive(TrieNode u) {
        if (u == null) {
            return 0;
        }
        int count = 1; // conta il nodo corrente
        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++) {
            if (u.children[i] != null) {
                count += countNodesRecursive(u.children[i]);
            }
        }
        return count;
    }
}
