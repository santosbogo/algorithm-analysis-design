package anaydis.search.PriorityQueueBenchmark;

import anaydis.search.*;
import anaydis.sort.DataSetGenerator;
import anaydis.sort.IntegerDataSetGenerator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Benchmark {
    private Class<? extends PriorityQueue<Integer>> pqClass;

    public Benchmark(Class<? extends PriorityQueue<Integer>> pqClass) {
        this.pqClass = pqClass;
    }

    public static void main(String[] args) {
        int[] sizes = {50, 100, 1000, 5000, 10000};
        final DataSetGenerator<Integer> dataSetGenerator = new IntegerDataSetGenerator();


        Comparator comp = Comparator.naturalOrder();


        for (int N : sizes) {


            Map<PriorityQueue<Integer>, String> priorityQueues = new HashMap<>();

            //Add the PQ to the list
            priorityQueues.put(new OrderedArrayPriorityQueue<>(comp, N), "Ordered Array Priority Queue");
            priorityQueues.put(new UnorderedArrayPriorityQueue<>(comp, N), "Unordered Array Priority Queue");
            priorityQueues.put(new HeapPriorityQueue<>(comp), "Heap Priority Queue");

            // Genera N elementos aleatorios
            long startTime, insertionTime, removalTime;
            List<Integer> elements = dataSetGenerator.createRandom(N);

            for (PriorityQueue<Integer> pq : priorityQueues.keySet()) {

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

                System.out.println(priorityQueues.get(pq));
                System.out.println("N: " + N);
                System.out.println("Tiempo de inserción: " + (insertionTime / 1e6) + " milisegundos");
                System.out.println("Tiempo de eliminación: " + (removalTime / 1e6) + " milisegundos");
                System.out.println();
            }
        }
    }
}