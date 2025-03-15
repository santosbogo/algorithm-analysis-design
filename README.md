# Algorithm Analysis and Design

📚 **Course:** Algorithm Analysis and Design

🎓 **University:** Austral Univerity

👤 **Author:** Santos Bogo (https://github.com/santosbogo)

## 📖 Repository Overview
This repository contains implementations of sorting, searching, compression algorithms, and advanced data structures, along with performance benchmarks.

### 🔹 Sorting Algorithms
- Selection Sort
- Insertion Sort
- Bubble Sort
- Shell Sort
- Quicksort (Recursive, Non-Recursive, Hybrid, Median of Three, Three-Way Partitioning)
- Merge Sort (Top-Down, Bottom-Up)

**🔍 Analysis:**
Performance evaluation measuring **swaps, comparisons, and execution time** for different input sizes.

### 🔹 Searching Algorithms
- **Tries:** Binary Search Trie, R-Way Trie, Ternary Search Trie
- **Benchmark:** Inserted all words from *Don Quixote* into a trie, then reversed the letters and searched them to measure the average miss and hit times.

### 🔹 Priority Queues
- OrderedArrayPriorityQueue
- UnorderedArrayPriorityQueue
- HeapPriorityQueue
**🔍 Benchmark:** Compared efficiency with different input sizes.

### 🔹 Immutability
- BankersQueue
- Immutable BinaryTree

### 🔹 Compression Algorithms
- Run-Length Encoding
- Burrows-Wheeler
- Huffman

### 🔹 String Searching Algorithms
- Brute Force
- Rabin-Karp

📑 **Reports and Benchmarks:**  
You can view the analysis results in the following spreadsheet:  
🔗 [Google Sheets - Reports](https://docs.google.com/spreadsheets/d/1VtNNalax8IUBDprILhsCsBJfaXS_gK31qDMdjbVJewE/edit?usp=sharing)

## 🚀 Installation & Usage
```sh
git clone https://github.com/santosbogo/algorithm-analysis-design.git
cd algorithm-analysis-design
````

### 🔹 Run Benchmarks  
The following Gradle tasks are available to execute benchmarks:

- **Sorting Algorithms Benchmark:**  
  ```sh
  ./gradlew runSortingBenchmark
  ```
- **Searching Algorithms Benchmark:**
  ```sh
  ./gradlew runSearchingBenchmark
  ```
- **Priority Queue Benchmark:**
  ```sh
  ./gradlew runPriorityQueueBenchmark
  ```

---