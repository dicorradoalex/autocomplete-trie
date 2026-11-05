package it.unipa.algoritmi;

/**
 * Rappresenta un singolo nodo del Trie.
 */
public class TrieNode {

    // Alfabeto supportato: 26 lettere minuscole da 'a' a 'z'
    public static final char FIRST_CHAR = 'a';
    public static final char LAST_CHAR = 'z';
    public static final int ALPHABET_SIZE = LAST_CHAR - FIRST_CHAR + 1; // 26 lettere

    // Array dei 26 nodi figli (il valore null indica assenza di arco uscente)
    final TrieNode[] children;

    // Indica se il cammino dalla radice a questo nodo forma una parola completa
    private boolean isRepresentative;

    /**
     * Costruttore: crea un nuovo nodo con tutti i 26 figli inizializzati a null.
     */
    public TrieNode() {
        this.children = new TrieNode[ALPHABET_SIZE];
        this.isRepresentative = false;
    }

    /**
     * Verifica se il carattere appartiene all'alfabeto supportato ('a'..'z').
     *
     * @param c il carattere da controllare
     * @return true se appartiene all'alfabeto, false altrimenti
     */
    public static boolean isValidChar(char c) {
        return c >= FIRST_CHAR && c <= LAST_CHAR;
    }

    /**
     * Converte un carattere nel corrispondente indice dell'array [0..25]
     *
     * @param c il carattere da convertire ('a'..'z')
     * @return l'indice nell'array children (0 per 'a', 25 per 'z')
     * @throws IllegalArgumentException se il carattere non appartiene all'alfabeto
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
     * Converte un indice [0..25] nel corrispondente carattere ('a'..'z')
     *
     * @param index l'indice dell'array children (da 0 a 25)
     * @return il carattere corrispondente
     */
    public static char indexToChar(int index) {
        if (index < 0 || index >= ALPHABET_SIZE) {
            throw new IndexOutOfBoundsException("Indice fuori dal range dell'alfabeto [0..25]: " + index);
        }
        return (char) (FIRST_CHAR + index);
    }

    /**
     * Marca il nodo indicando che qui termina una parola del dizionario.
     */
    public void markAsRepresentative() {
        this.isRepresentative = true;
    }

    /**
     * Restituisce true se in questo nodo termina una parola completa.
     *
     * @return true se il nodo rappresenta una parola valida, false altrimenti
     */
    public boolean isRepresentative() {
        return this.isRepresentative;
    }
}
