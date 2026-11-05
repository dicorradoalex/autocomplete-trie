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
AutocompleteTrie/
├── pom.xml                         # Configurazione Maven e dipendenze (JUnit 5)
├── readme.md                       # Documentazione del progetto
├── input/                          # File di input per i test
│   ├── input1_traccia.txt          # Esempio fornito dalla traccia d'esame
│   └── input2_completo.txt         # Casi limite (duplicati, no match, prefisso completo)
├── Relazione Progetto/             # relazione
│   ├── main.pdf                    # Relazione finale compilata in formato PDF
│   ├── main.tex                    # Sorgente LaTeX della relazione
│   └── common.sty                  # Foglio di stile e pacchetti LaTeX
└── src/
    ├── main/java/it/unipa/algoritmi/
    │   ├── TrieNode.java           # Struttura del nodo del Trie (|Sigma| = 26)
    │   ├── Trie.java               # Logica dell'albero e algoritmi (insert, searchByPrefix, DFS)
    │   └── Main.java               # Gestione I/O ed elaborazione comandi (PREFIX, INSERT)
    └── test/java/it/unipa/algoritmi/
        └── TrieTest.java           # Suite di test automatizzati (JUnit 5)
```

---

## Prerequisiti

* **Java Development Kit (JDK)**: versione 18 (o compatibile con Java 17 LTS).
* **Apache Maven**: versione 3.6 o superiore (oppure IntelliJ IDEA con supporto Maven integrato).

---

## Compilazione ed Esecuzione

### 1. Compilazione del Progetto
Dal terminale nella cartella radice del progetto:
```bash
mvn clean compile
```

### 2. Esecuzione dei Test (JUnit 5)
Per lanciare la suite di test automatizzati:
```bash
mvn test
```

### 3. Esecuzione del Programma
L'applicazione supporta tre modalità di esecuzione, a seconda di come viene fornito il flusso di input:

* **Modalità 1 – Esempio della Traccia d'Esame:**
  Esegue l'esempio fornito nella traccia per verificare il flusso base di popolamento e completamento:
  ```bash
  java -cp target/classes it.unipa.algoritmi.Main input/input1_traccia.txt
  ```

* **Modalità 2 – Casi Limite (Prefissi Completi, Duplicati, No Match):**
  Testa le casistiche limite, inclusi duplicati, ricerche senza corrispondenza e prefissi coincidenti con parole complete:
  ```bash
  java -cp target/classes it.unipa.algoritmi.Main input/input2_completo.txt
  ```

* **Modalità 3 – Interattiva da Console:**
  Avviando il programma senza argomenti, l'input viene acquisito direttamente da tastiera tramite lo standard input:
  ```bash
  java -cp target/classes it.unipa.algoritmi.Main
  ```
  Formato atteso:
  1. Prima riga: numero intero *N* di parole iniziali.
  2. Successive *N* righe: una parola per riga da inserire nel Trie.
  3. Righe seguenti: comandi `PREFIX <prefisso>` oppure `INSERT <parola>`.