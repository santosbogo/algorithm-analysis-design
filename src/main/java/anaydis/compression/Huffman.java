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

public class Huffman implements Compressor {

    private static final int EOF = -1;  // End-of-file marker

    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        List<Character> chars = inputToString(input);
        HashMap<Character, Integer> concurrenceMap = concurrenceMap(chars);
        PriorityQueue<Node> table = generateHuffmanTree(concurrenceMap);
        Map<Integer, Bits> symbolTable = createSymbolTable(table);

        // Write the size of the symbol table (using 2 bytes)
        output.write(symbolTable.size() >> 8);
        output.write(symbolTable.size());

        // Write the symbol table
        for (Map.Entry<Integer, Bits> current : symbolTable.entrySet()) {
            Integer character = current.getKey();
            output.write(character);
            Bits bits = current.getValue();
            bits.writeInto(output);
        }

        // Write the size of the message
        byte[] size = ByteBuffer.allocate(4).putInt(chars.size()).array();
        for (byte byteLength : size) {
            output.write(byteLength);
        }

        // Write the message
        BitsOutputStream contenido = new BitsOutputStream();
        for (char character : chars) {
            Bits code = symbolTable.get((int) character);
            contenido.write(code);
        }
        output.write(contenido.toByteArray());
    }

    private List<Character> inputToString(InputStream input) throws IOException {
        List<Character> chars = new ArrayList<>();
        int readChar;
        while ((readChar = input.read()) != EOF) {
            chars.add((char) readChar);
        }
        return chars;
    }

    private HashMap<Character, Integer> concurrenceMap(List<Character> chars) {
        HashMap<Character, Integer> concurrenceMap = new HashMap<>();
        for (char c : chars) {
            concurrenceMap.put(c, concurrenceMap.getOrDefault(c, 0) + 1);
        }
        return concurrenceMap;
    }

    private PriorityQueue<Node> generateHuffmanTree(HashMap<Character, Integer> concurrenceMap) {
        PriorityQueue<Node> table = new OrderedArrayPriorityQueue<>(Node::compareTo);
        for (Map.Entry<Character, Integer> entry : concurrenceMap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            table.insert(node);
        }
        while (table.size() > 1) {
            Node left = table.pop();
            Node right = table.pop();
            Node node = new Node(left, right, left.value + right.value);
            table.insert(node);
        }
        return table;
    }

    private Map<Integer, Bits> createSymbolTable(PriorityQueue<Node> table) {
        if (table.isEmpty()) return Collections.emptyMap();
        if (table.peek().isLeaf()) return Collections.singletonMap((int) table.pop().character, new Bits().add(false));
        Node root = table.pop();
        Map<Integer, Bits> result = new LinkedHashMap<>();
        root.collect(result, new Bits());
        return result;
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        Map<Bits, Integer> symbolTable = readSymbolTable(input);

        // Read Message Size
        byte[] sizeBytes = new byte[4];
        input.read(sizeBytes);
        int total = ByteBuffer.wrap(sizeBytes).getInt();

        // Decode Message
        Bits currentBits = new Bits();
        for (int i = 0; i < total; i++) {
            Integer character = null;
            while (character == null) {
                currentBits.add(bitAt(input.read(), 7));  // Read bit-by-bit
                character = symbolTable.get(currentBits);
            }
            output.write(character);
        }
    }

    private Map<Bits, Integer> readSymbolTable(InputStream input) throws IOException {
        int symbols = (input.read() << 8) + input.read();
        Map<Bits, Integer> symbolTable = new HashMap<>();
        for (int i = 0; i < symbols; i++) {
            int character = input.read();
            Bits bits = readBits(input);
            symbolTable.put(bits, character);
        }
        return symbolTable;
    }

    private Bits readBits(InputStream input) throws IOException {
        int length = input.read();
        byte[] bytes = new byte[(length + 7) / 8];
        input.read(bytes);
        Bits bits = new Bits();
        for (int i = 0; i < length; i++) {
            if (bitAt(bytes[i / 8], i % 8)) bits.add(true);
            else bits.add(false);
        }
        return bits;
    }

    public static boolean bitAt(int value, int position) {
        int mask = 1 << (7 - position);
        return (value & mask) != 0;
    }
}
