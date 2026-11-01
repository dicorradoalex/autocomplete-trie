package it.unipa.algoritmi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrieTest {

    @Test
    void testSkeletonInitialization() {
        Trie trie = new Trie();
        assertNotNull(trie);
    }
}