package it.unipa.algoritmi;

import java.util.List;

public class Trie {
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    // Restituisce il figlio di v associato al carattere c (child(v, S[j]) di Slide 17)
    public TrieNode child(TrieNode v, char c) {
        return null; // Da implementare
    }

    // Inserimento di una stringa S[0..m) nel Trie (Slide 17)
    public void insert(String S) {
        // Da implementare
    }

    // Prefix query: restituisce tutte le parole ordinate alfabeticamente che iniziano con prefix
    public List<String> searchByPrefix(String prefix) {
        return List.of(); // Da implementare
    }
}