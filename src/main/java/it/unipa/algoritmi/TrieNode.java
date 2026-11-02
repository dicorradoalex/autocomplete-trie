package it.unipa.algoritmi;

/**
 * Rappresenta un nodo della struttura dati Trie.
 */
public class TrieNode {

    /**
     * Dichiarazione esplicita dell'insieme di caratteri supportato Sigma = {'a'..'z'}.
     */
    public static final char FIRST_CHAR = 'a';
    public static final char LAST_CHAR = 'z';
    public static final int ALPHABET_SIZE = LAST_CHAR - FIRST_CHAR + 1; // |Sigma| = 26

    /**
     * Array di dimensione |Sigma| contenente i puntatori ai nodi figli.
     * Il valore null corrisponde al simbolo di terminazione (assenza di arco uscente).
     */
    final TrieNode[] children;

    /**
     * Indica se il cammino dalla radice a questo nodo rappresenta una parola completa in R.
     */
    private boolean isRepresentative;

    /**
     * Costruisce un nuovo nodo u inizializzando child(u, c) a \perp (null) per ogni c in Sigma.
     */
    public TrieNode() {
        this.children = new TrieNode[ALPHABET_SIZE];
        this.isRepresentative = false;
    }

    /**
     * Verifica se un carattere appartiene all'alfabeto supportato Sigma = {'a'..'z'}.
     *
     * @param c il carattere da verificare
     * @return true se c appartiene a Sigma, false altrimenti
     */
    public static boolean isValidChar(char c) {
        return c >= FIRST_CHAR && c <= LAST_CHAR;
    }

    /**
     * Converte un carattere c in Sigma nel corrispondente indice dell'array [0 .. |Sigma|-1].
     * Costo: O(1).
     *
     * @param c il carattere dell'alfabeto
     * @return l'indice nell'array children
     * @throws IllegalArgumentException se il carattere non appartiene a Sigma
     */
    public static int charToIndex(char c) {
        if (!isValidChar(c)) {
            throw new IllegalArgumentException(
                    "Carattere '" + c + "' non supportato. L'alfabeto supportato e' ['a'..'z']."
            );
        }
        return c - FIRST_CHAR;
    }

    /**
     * Converte un indice [0 .. |Sigma|-1] nel corrispondente carattere c in Sigma.
     * Costo: O(1).
     *
     * @param index l'indice dell'array children
     * @return il carattere corrispondente nell'alfabeto Sigma
     */
    public static char indexToChar(int index) {
        if (index < 0 || index >= ALPHABET_SIZE) {
            throw new IndexOutOfBoundsException("Indice fuori dal range dell'alfabeto [0..25]: " + index);
        }
        return (char) (FIRST_CHAR + index);
    }

    /**
     * Marca il nodo corrente come rappresentativo di una stringa S dell'insieme R
     */
    public void markAsRepresentative() {
        this.isRepresentative = true;
    }

    /**
     * Restituisce true se il nodo e' marcato come rappresentativo di una parola in R.
     *
     * @return true se il nodo rappresenta una parola inserita, false altrimenti
     */
    public boolean isRepresentative() {
        return this.isRepresentative;
    }
}