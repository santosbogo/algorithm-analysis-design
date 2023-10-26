package anaydis.compression;

import anaydis.bit.Bits;
import anaydis.bit.BitsOutputStream;
import anaydis.search.OrderedArrayPriorityQueue;
import anaydis.search.PriorityQueue;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.*;

public class Huffman implements Compressor{
    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        List<Character> chars = inputToString(input);
        HashMap<Character, Integer> concurrenceMap = concurrenceMap(chars);
        PriorityQueue<Node> table = generateHuffmanTree(concurrenceMap);
        Map<Character, Bits> symbolTable = createSymbolTable(table);

        //Write the size of the symbol table
        output.write(symbolTable.size());

        //Write the symbol table
        for (final Map.Entry<Character, Bits> current : symbolTable.entrySet()) {
            final Character character = current.getKey();
            output.write(character);
            final Bits bits = current.getValue();
            bits.writeInto(output);
        }

        // Write the size of the message
        byte[] size = ByteBuffer.allocate(4).putInt(chars.size()).array();
        for (byte byteLength : size) {
            output.write(byteLength);
        }

        // Escribo el contenido en el Output
        final BitsOutputStream contenido = new BitsOutputStream();
        for (Character character : chars) {
            final Bits code = symbolTable.get(character);
            contenido.write(code);
        }
        output.write(contenido.toByteArray());

    }

    //todo: REVISAR: EL INPUT.READ() LEE DE -1 A 256 (ASCII) Y CUANDO CASTEO EL INT A CHAR SE HACE EN UNICODE
    private List<Character> inputToString(InputStream input) throws IOException {
        List<Character> chars = new ArrayList<>();
        int current = input.read();

        while (current != -1){
            chars.add((char) current);
            current = input.read();
        }

        return chars;
    }

    private HashMap<Character, Integer> concurrenceMap(List<Character> chars){
        HashMap<Character, Integer> concurrenceMap = new HashMap<>();

        for (char c: chars)
            if (concurrenceMap.containsKey(c))
                concurrenceMap.put(c, concurrenceMap.get(c) + 1);
            else
                concurrenceMap.put(c, 1);

        return concurrenceMap;
    }

    private PriorityQueue<Node> generateHuffmanTree(HashMap<Character, Integer> concurrenceMap){
        PriorityQueue<Node> table = new OrderedArrayPriorityQueue<>(Node::compareTo);

        for (char i : concurrenceMap.keySet()) {
            Node node = new Node(i, concurrenceMap.get(i));
            table.insert(node);
        }

        while (table.size() != 1) {
            Node left = table.pop();
            Node right = table.pop();
            Node node = new Node(left, right);
            table.insert(node);
        }

        return table;
    }


    private Map<Character, Bits> createSymbolTable(PriorityQueue<Node> table) {
        //No symbols
        if (table.isEmpty()) return Collections.emptyMap();

        //Just one symbol
        if (table.peek().isLeaf()) return Collections.singletonMap((char) table.pop().frequency, new Bits().add(false));

        //Many symbols
        final Node root = table.pop();
        final Map<Character, Bits> result = new LinkedHashMap<>();
        root.collect(result, new Bits());
        return result;
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {

    }
}
