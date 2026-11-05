# Autocomplete Trie

Progetto per l'esame di **Laboratorio di Algoritmi** (Corso di Laurea in Informatica, Università degli Studi di Palermo).

Il sistema implementa un motore di completamento automatico basato sulla struttura dati **Trie**. 
Il programma consente di memorizzare un dizionario di vocaboli, inserire dinamicamente nuove parole e restituire tutti i 
termini che iniziano con un dato prefisso, presentandoli automaticamente in ordine alfabetico e senza effettuare una 
scansione sequenziale dell'intero dizionario.

---

## Descrizione delle Classi

Il codice sorgente è organizzato nelle seguenti classi principali:

* **`TrieNode`**: rappresenta il singolo nodo dell'albero. Contiene un array di 26 puntatori ai nodi figli 
(corrispondente all'alfabeto `'a'..'z'`) e un flag booleano (`isRepresentative`) che indica se il cammino dalla radice 
fino a quel nodo individua una parola completa del dizionario.
* **`Trie`**: implementa la logica dell'albero. Fornisce l'operazione di inserimento (`insert`) e l'operazione di 
autocompletamento (`searchByPrefix`), che individua il nodo corrispondente al prefisso e raccoglie tutti i suggerimenti 
ordinati alfabeticamente tramite visita in profondità (DFS).
* **`Main`**: legge il numero iniziale di parole, popola il Trie ed esegue i comandi `PREFIX <prefisso>` e 
`INSERT <parola>`, leggendo da file di testo o direttamente da tastiera.
* **`TrieTest`** (in `src/test/`): suite di test automatici con **JUnit 5** che verifica il corretto funzionamento di 
inserimento, autocompletamento, ordinamento, gestione di duplicati e assenza di risultati.

---

## Struttura del Repository

```text
autocomplete-trie/
├── pom.xml                         # Configurazione Maven e dipendenze (JUnit 5)
├── README.md                       # Documentazione del progetto
├── input/                          # File di input per i test
│   ├── input1_traccia.txt          # Input d'esempio della traccia d'esame
│   └── input2_completo.txt         # Input di prova con casi limite (duplicati, no match)
└── src/
    ├── main/java/it/unipa/algoritmi/
    │   ├── TrieNode.java           # Struttura del nodo del Trie
    │   ├── Trie.java               # Logica dell'albero e algoritmi di ricerca
    │   └── Main.java               # Gestione I/O ed elaborazione comandi
    └── test/java/it/unipa/algoritmi/
        └── TrieTest.java           # Test unitari (JUnit 5)
```

---

## Prerequisiti

* **Java Development Kit (JDK)**: versione 18 o superiore.
* **Apache Maven**: per la gestione della compilazione e delle dipendenze (oppure IntelliJ IDEA con Maven integrato).

---

## Compilazione ed Esecuzione

### 1. Compilazione del Progetto
Dal terminale nella cartella del progetto:
```bash
mvn clean compile
```

### 2. Esecuzione dei Test (JUnit 5)
Per lanciare i test automatici:
```bash
mvn test
```

### 3. Esecuzione con File di Input (Opzione A - Consigliata)
Per eseguire il programma utilizzando uno dei due file di test preconfigurati e visualizzare subito l'output:

* **Test con input1_traccia.txt (esempio dato della traccia d'esame):**:
  ```powershell
  java -cp target/classes it.unipa.algoritmi.Main input/input1_traccia.txt
  ```

* **Test con input2_completo.txt (casi limite: prefissi completi, duplicati, ricerche vuote):**:

  ```powershell
  java -cp target/classes it.unipa.algoritmi.Main input/input2_completo.txt
  ```

### Esecuzione Interattiva da Console (Opzione B - Alternativa manuale)
In alternativa all'uso dei file, è possibile avviare il programma e digitare i comandi direttamente da tastiera:

```powershell
java -cp target/classes it.unipa.algoritmi.Main
```

Formato atteso in console:
1. Prima riga: numero intero $N$ di parole iniziali.
2. Successive $N$ righe: una parola per riga.
3. Righe seguenti: comandi `PREFIX <prefisso>` oppure `INSERT <parola>`.