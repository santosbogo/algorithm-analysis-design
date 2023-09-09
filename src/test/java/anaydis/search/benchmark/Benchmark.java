package anaydis.search.benchmark;

import anaydis.search.BinaryTrieMap;
import anaydis.search.Map;
import anaydis.search.RWayTrieMap;
import anaydis.search.TSTTrieMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Benchmark {
    public static void main(String[] args) {
        final int[] N = {5000, 50000, 100000, 150000, 200000};
        final String[] triesNames = {"RWay", "TST", "Binary"};

        //Create a list with the maps i want to try
        List<anaydis.search.Map<String, Integer>> maps = new ArrayList<>();

        //Initialize the tries
        anaydis.search.Map<String, Integer> RWay = new RWayTrieMap<>();
        anaydis.search.Map<String, Integer> TST = new TSTTrieMap<>();
        Map<String, Integer> Binary = new BinaryTrieMap<>();

        //Add the maps to the list of maps
        maps. add (RWay);
        maps.add (TST);
        maps.add(Binary);

        Set<String> orderedWords = readAllWords("src/test/resources/books/quijote.txt");
        Set<String> reversedWords = readAllWords("src/test/resources/books/etojiuq.txt");

        //Put the ordered words in the map
        for (String word: orderedWords){
            int value;
            //If the word already exist in the map, then increment its value
            if(RWay.containsKey(word)) value = RWay.get(word) + 1;
            else value = 1;

            //Now that we now the value, put in each of the tries
            RWay.put(word, value);
            TST.put(word, value);
            Binary.put(word, value);
        }

        for (Integer n: N) {
            for (String name : triesNames) {

            }
        }


    }

    private static Set<String> readAllWords(String path){

        Set<String> words = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line = reader.readLine();

            while (line != null) {
                String[] lineWords = line.split(" ");
                for( String word: lineWords){
                    words.add(word);
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return words;
    }
}
