package it.unipa.algoritmi;

public class TrieNode {
    // Dichiarazione della dimensione dell'alfabeto supportato |Σ| = 26 ('a' - 'z')
    public static final int ALPHABET_SIZE = 26;

    // Array di figli di dimensione |Σ| (inizializzato a null, ovvero ⊥ nella teoria)
    TrieNode[] children;

    // Flag per marcare il nodo v come rappresentativo di una stringa S in R (Slide 17, riga 8)
    boolean isRepresentative;

    public TrieNode() {
        // Implementeremo il costruttore nel prossimo passo
    }
}