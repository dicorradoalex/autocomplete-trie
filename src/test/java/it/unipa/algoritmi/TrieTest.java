package it.unipa.algoritmi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    @DisplayName("Inizializzazione Nodo: Un nuovo nodo u ha tutti i figli inizializzati a null e non è marcato")
    void testNewNodeInitialization() {
        TrieNode u = new TrieNode();

        assertEquals(26, TrieNode.ALPHABET_SIZE);
        assertFalse(u.isRepresentative(), "Un nuovo nodo non deve essere marcato come rappresentativo");

        for (int i = 0; i < TrieNode.ALPHABET_SIZE; i++) {
            assertNull(u.children[i], "Il figlio in posizione " + i + " deve essere inizializzato a null");
        }
    }

    @Test
    @DisplayName("Rappresentatività: Marcatura di un nodo v come rappresentativo di una parola")
    void testMarkAsRepresentative() {
        TrieNode v = new TrieNode();
        assertFalse(v.isRepresentative());

        v.markAsRepresentative();
        assertTrue(v.isRepresentative(), "Il nodo v deve risultare marcato dopo markAsRepresentative()");
    }

    @Test
    @DisplayName("Convalida caratteri: Conversione caratteri ['a'..'z'] e rifiuto caratteri non validi")
    void testAlphabetMappingAndValidation() {
        assertEquals(0, TrieNode.charToIndex('a'));
        assertEquals(25, TrieNode.charToIndex('z'));
        assertEquals('a', TrieNode.indexToChar(0));
        assertEquals('z', TrieNode.indexToChar(25));

        assertTrue(TrieNode.isValidChar('c'));
        assertFalse(TrieNode.isValidChar('A'));
        assertFalse(TrieNode.isValidChar('1'));
        assertFalse(TrieNode.isValidChar('$'));

        assertThrows(IllegalArgumentException.class, () -> TrieNode.charToIndex('!'));
    }

    @Test
    @DisplayName("Esempio della Traccia: inserimento iniziale, PREFIX ca, INSERT camion, PREFIX cam")
    void testExamTraceExample() {
        Trie trie = new Trie();
        trie.insert("cane");
        trie.insert("casa");
        trie.insert("casale");
        trie.insert("castello");
        trie.insert("gatto");

        // Comando PREFIX ca
        List<String> suggestionsCa = trie.searchByPrefix("ca");
        assertEquals(List.of("cane", "casa", "casale", "castello"), suggestionsCa);

        // Comando INSERT camion
        trie.insert("camion");

        // Comando PREFIX cam
        List<String> suggestionsCam = trie.searchByPrefix("cam");
        assertEquals(List.of("camion"), suggestionsCam);
    }

    @Test
    @DisplayName("Teorema sul Numero di Nodi: R = {ali, alice, anna, eliana, elias, elisa} ha esattamente 28 - 11 + 1 = 18 nodi")
    void testNodeCountTheorem() {
        Trie trie = new Trie();
        String[] R = {"ali", "alice", "anna", "eliana", "elias", "elisa"};
        for (String s : R) {
            trie.insert(s);
        }

        // Verifica del Teorema: ||R|| = 28, ELCP(R) = 11 -> Nodi = 18
        assertEquals(18, trie.countNodes());
        assertEquals(List.of("ali", "alice"), trie.searchByPrefix("ali"));
        assertEquals(List.of("eliana", "elias", "elisa"), trie.searchByPrefix("eli"));
    }

    @Test
    @DisplayName("Requisito: Gestione del caso in cui il prefisso coincida con una parola completa")
    void testPrefixIsCompleteWord() {
        Trie trie = new Trie();
        trie.insert("casale");
        trie.insert("casa"); // Inserita anche dopo una parola più lunga
        trie.insert("castello");

        List<String> results = trie.searchByPrefix("casa");
        assertEquals(List.of("casa", "casale"), results);
    }

    @Test
    @DisplayName("Punto 3: Ordinamento alfabetico garantito anche con inserimenti in ordine inverso")
    void testAlphabeticalOrdering() {
        Trie trie = new Trie();
        trie.insert("castello");
        trie.insert("casale");
        trie.insert("casa");
        trie.insert("cane");
        trie.insert("camion");

        List<String> results = trie.searchByPrefix("ca");
        assertEquals(List.of("camion", "cane", "casa", "casale", "castello"), results);
    }

    @Test
    @DisplayName("Punto 5: Gestione parole duplicate (nessun nodo extra e nessun suggerimento duplicato)")
    void testDuplicateWordsHandling() {
        Trie trie = new Trie();
        trie.insert("cane");
        trie.insert("casa");
        int nodesBeforeDuplicate = trie.countNodes();

        // Reinserimento di parole già presenti
        trie.insert("cane");
        trie.insert("casa");

        assertEquals(nodesBeforeDuplicate, trie.countNodes(), "I duplicati non devono allocare nuovi nodi");
        assertEquals(List.of("cane", "casa"), trie.searchByPrefix("ca"));
    }

    @Test
    @DisplayName("Punto 5: Gestione ricerche senza risultati")
    void testSearchWithNoResults() {
        Trie trie = new Trie();
        trie.insert("cane");
        trie.insert("casa");

        // Prefisso assente dalla radice
        assertTrue(trie.searchByPrefix("z").isEmpty());
        // Prefisso che diverge a metà cammino
        assertTrue(trie.searchByPrefix("cab").isEmpty());
        // Prefisso più lungo della parola memorizzata
        assertTrue(trie.searchByPrefix("canile").isEmpty());
    }
}