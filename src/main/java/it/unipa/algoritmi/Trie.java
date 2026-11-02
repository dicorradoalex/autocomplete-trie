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
     * Inizializza un Trie vuoto con il solo nodo radice (Slide 19).
     */
    public Trie() {
        this.root = new TrieNode();
    }

    /**
     * Restituisce la radice del Trie.
     *
     * @return il nodo root
     */
    public TrieNode getRoot() {
        return this.root;
    }

    /**
     * Restituisce il figlio del nodo v associato al carattere c, ovvero child(v, c).
     * Ritorna null se non esiste un arco uscente da v etichettato con c.
     * Costo: O(1).
     *
     * @param v il nodo corrente
     * @param c il carattere dell'alfabeto Sigma = {'a'..'z'}
     * @return il nodo figlio corrispondente, oppure null (\perp)
     */
    public TrieNode child(TrieNode v, char c) {
        int index = TrieNode.charToIndex(c);
        return v.children[index];
    }

    /**
     * Imposta il nodo u come figlio di v associato al carattere c: child(v, c) <- u.
     * Costo: O(1).
     *
     * @param v il nodo padre
     * @param c il carattere dell'arco
     * @param u il nuovo nodo figlio
     */
    private void setChild(TrieNode v, char c, TrieNode u) {
        int index = TrieNode.charToIndex(c);
        v.children[index] = u;
    }

    /**
     * Inserisce una stringa S[0..m) nel Trie.
     * Costo temporale: O(|S|).
     * Se S e' gia' presente (parola duplicata), non vengono creati nuovi nodi.
     *
     * @param S la parola da inserire
     * @throws IllegalArgumentException se S e' null, vuota o contiene caratteri fuori da Sigma
     */
    public void insert(String S) {
        if (S == null) {
            throw new IllegalArgumentException("La stringa da inserire non puo' essere null.");
        }

        String normalized = S.trim().toLowerCase();
        int m = normalized.length();
        if (m == 0) {
            throw new IllegalArgumentException("La stringa da inserire non puo' essere vuota.");
        }

        // Validazione preliminare che tutti i caratteri appartengano a Sigma = {'a'..'z'}
        for (int i = 0; i < m; i++) {
            if (!TrieNode.isValidChar(normalized.charAt(i))) {
                throw new IllegalArgumentException(
                        "La parola '" + S + "' contiene il carattere non supportato: '" + normalized.charAt(i) + "'"
                );
            }
        }

        TrieNode v = this.root;
        int j = 0;

        while (j < m && child(v, normalized.charAt(j)) != null) {
            v = child(v, normalized.charAt(j));
            j = j + 1;
        }

        while (j < m) {
            TrieNode u = new TrieNode();
            setChild(v, normalized.charAt(j), u);
            v = u;
            j = j + 1;
        }
        v.markAsRepresentative();
    }

    /**
     * Prefix query: Trova tutte le stringhe di R che hanno prefix come prefisso,
     * restituendole ordinate alfabeticamente senza effettuare una scansione sequenziale dell'intero dizionario.
     *
     * @param prefix il prefisso da cercare
     * @return lista ordinata alfabeticamente delle parole che iniziano con prefix (vuota se nessun match)
     */
    public List<String> searchByPrefix(String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }

        String normalizedPrefix = prefix.trim().toLowerCase();
        int pLen = normalizedPrefix.length();
        if (pLen == 0) {
            return Collections.emptyList();
        }

        // Fase 1: Discesa nel Trie lungo i caratteri del prefisso in O(|prefix|)
        TrieNode v = this.root;
        for (int j = 0; j < pLen; j++) {
            char c = normalizedPrefix.charAt(j);
            if (!TrieNode.isValidChar(c)) {
                // Se il prefisso contiene un carattere fuori dall'alfabeto, non ci sono risultati
                return Collections.emptyList();
            }
            v = child(v, c);
            if (v == null) {
                // Ricerca senza risultati: il cammino si interrompe (child(v, c) == null)
                return Collections.emptyList();
            }
        }

        // Fase 2: Raccolta ordinata delle parole nel sottoalbero radicato in v tramite visita DFS in pre-ordine
        List<String> suggestions = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder(normalizedPrefix);
        collectWordsDFS(v, currentWord, suggestions);
        return suggestions;
    }

    /**
     * Visita ricorsivamente in pre-ordine il sottoalbero radicato nel nodo u.
     * Poiche' l'array children e' indicizzato secondo l'ordine alfabetico ['a'..'z'],
     * i suggerimenti vengono raccolti nativamente in ordine lessicografico crescente.
     *
     * @param u           il nodo corrente nella visita del sottoalbero
     * @param currentWord il buffer contenente i caratteri sul cammino dalla radice a u
     * @param suggestions la lista in cui accumulare le parole rappresentative trovate
     */
    private void collectWordsDFS(TrieNode u, StringBuilder currentWord, List<String> suggestions) {
        // Se il nodo corrente e' marcato (incluso il nodo stesso del prefisso), aggiungiamo la parola
        if (u.isRepresentative()) {
            suggestions.add(currentWord.toString());
        }

        // Esplorazione ordinata di tutti i possibili archi uscenti per c in Sigma
        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++) {
            TrieNode nextNode = u.children[i];
            if (nextNode != null) {
                currentWord.append(TrieNode.indexToChar(i));
                collectWordsDFS(nextNode, currentWord, suggestions);
                currentWord.deleteCharAt(currentWord.length() - 1); // Backtracking
            }
        }
    }

    /**
     * Conta il numero totale di nodi presenti nel Trie:
     * numero di nodi = ||R|| - ELCP(R) + 1).
     *
     * @return il numero totale di nodi in trie(R)
     */
    public int countNodes() {
        return countNodesRecursive(this.root);
    }

    private int countNodesRecursive(TrieNode u) {
        if (u == null) {
            return 0;
        }
        int count = 1;
        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++) {
            if (u.children[i] != null) {
                count += countNodesRecursive(u.children[i]);
            }
        }
        return count;
    }
}