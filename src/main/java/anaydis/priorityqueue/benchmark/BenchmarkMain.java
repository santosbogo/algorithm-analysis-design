package anaydis.priorityqueue.benchmark;

import anaydis.priorityqueue.HeapPriorityQueue;
import anaydis.priorityqueue.OrderedArrayPriorityQueue;
import anaydis.priorityqueue.UnorderedArrayPriorityQueue;
import anaydis.search.*;
import anaydis.sort.DataSetGenerator;
import anaydis.sorting.IntegerDataSetGenerator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchmarkMain {
    public static void main(String[] args) {
        int[] sizes = {50, 100, 1000, 5000, 10000};
        final DataSetGenerator<Integer> dataSetGenerator = new IntegerDataSetGenerator();
        Comparator<Integer> comp = Comparator.naturalOrder();

        Map<String, PriorityQueue<Integer>> priorityQueues = new HashMap<>();
        priorityQueues.put("Ordered Array Priority Queue", new OrderedArrayPriorityQueue<>(comp, 10000));
        priorityQueues.put("Unordered Array Priority Queue", new UnorderedArrayPriorityQueue<>(comp, 10000));
        priorityQueues.put("Heap Priority Queue", new HeapPriorityQueue<>(comp));

        for (Map.Entry<String, PriorityQueue<Integer>> entry : priorityQueues.entrySet()) {
            System.out.println("\n" + entry.getKey());
            for (int N : sizes) {
                System.out.println("\nFor " + N + " elements:");
                benchmarkQueue(entry.getValue(), N, dataSetGenerator);
            }
        }
    }

    private static void benchmarkQueue(PriorityQueue<Integer> pq, int N, DataSetGenerator<Integer> generator) {
        long startTime, insertionTime, removalTime;
        List<Integer> elements = generator.createRandom(N);

        // Medición del tiempo de inserción
        startTime = System.nanoTime();
        for (int element : elements) {
            pq.insert(element);
        }
        insertionTime = System.nanoTime() - startTime;

        // Medición del tiempo de eliminación
        startTime = System.nanoTime();
        for (int i = 0; i < N; i++) {
            pq.pop();
        }
        removalTime = System.nanoTime() - startTime;

        // Mostrar resultados alineados con el otro benchmark
        System.out.println("Insertion Time = " + (insertionTime / 1e6) + " ms\tRemoval Time = " + (removalTime / 1e6) + " ms");
    }
}