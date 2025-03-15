package anaydis.search.benchmark;

import anaydis.search.BinaryTrieMap;
import anaydis.search.Map;
import anaydis.search.RWayTrieMap;
import anaydis.search.TSTTrieMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BenchmarkMain {
    public static void main(String[] args) {
        final int[] N = {5000, 50000, 100000, 150000, 200000};
        final String[] triesNames = {"RWay", "TST", "Binary"};

        List<Map<String, Integer>> maps = new ArrayList<>();
        Map<String, Integer> RWay = new RWayTrieMap<>();
        Map<String, Integer> TST = new TSTTrieMap<>();
        Map<String, Integer> Binary = new BinaryTrieMap<>();

        maps.add(RWay);
        maps.add(TST);
        maps.add(Binary);

        List<String> wordsFromQuijote = readAllWords("src/test/resources/books/quijote.txt");
        Set<String> orderedWords = new HashSet<>(wordsFromQuijote);
        List<String> reversedWords = readAllWords("src/test/resources/books/etojiuq.txt");

        for (String word : orderedWords) {
            int value = RWay.containsKey(word) ? RWay.get(word) + 1 : 1;
            RWay.put(word, value);
            TST.put(word, value);
            Binary.put(word, value);
        }

        System.out.println("\n\nWorst Cases: All Misses\n");
        for (Integer n : N) {
            System.out.println("\nFor " + n + " elements:");
            for (int i = 0; i < maps.size(); i++) {
                benchmarkSearch(maps.get(i), triesNames[i], reversedWords, n, true);
            }
        }

        System.out.println("\n\nBest Cases: All Hits\n");
        for (Integer n : N) {
            System.out.println("\nFor " + n + " elements:");
            for (int i = 0; i < maps.size(); i++) {
                benchmarkSearch(maps.get(i), triesNames[i], orderedWords, n, false);
            }
        }
    }

    private static void benchmarkSearch(Map<String, Integer> trie, String trieName, Collection<String> words, int n, boolean worstCase) {
        Iterator<String> iter = words.iterator();
        List<String> searchWords = new ArrayList<>();

        if (!worstCase) {
            for (String word : words) {
                Collections.addAll(searchWords, word, word, word, word, word, word);
            }
            Collections.shuffle(searchWords);
            iter = searchWords.iterator();
        }

        long start = System.currentTimeMillis();
        int misses = 0, successes = 0;

        for (int i = 0; i < n; i++) {
            if (trie.containsKey(iter.next() + (worstCase ? "Ñ" : ""))) successes++;
            else misses++;
        }

        long time = System.currentTimeMillis() - start;

        System.out.println(trieName + ": Misses = " + misses + " \tSuccesses = " + successes + "\tTime (ms) = " + time);
    }

    private static List<String> readAllWords(String path) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : line.split(" ")) {
                    if (!word.isEmpty() && !word.contains("\uFEFF")) words.add(word);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }
}