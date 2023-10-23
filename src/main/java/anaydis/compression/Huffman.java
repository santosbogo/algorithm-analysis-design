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

    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        HashMap<Character, Integer> concurrenceMap = concurrenceMap(input);

        PriorityQueue<Node> table = generateHuffmanTree(concurrenceMap);

        Map<Integer, Bits> symbolTable = createSymbolTable(table);

        // Write the size of the symbol table
        output.write(symbolTable.size());

        // Write the symbol table
        for (final Map.Entry<Integer, Bits> current : symbolTable.entrySet()) {
            Integer character = current.getKey();
            output.write(character);
            Bits bits = current.getValue();
            bits.writeInto(output);
        }

        // Write the message
        BitsOutputStream content = new BitsOutputStream();
        int currentChar;
        while ((currentChar = input.read()) != -1) {
            Bits code = symbolTable.get(currentChar);
            content.write(code);
        }

        output.write(content.toByteArray());
    }

    private HashMap<Character, Integer> concurrenceMap(InputStream input) throws IOException {
        HashMap<Character, Integer> concurrenceMap = new HashMap<>();
        int i;
        char c;

        while ((i = input.read()) != -1) {
            c = (char) i;
            concurrenceMap.put(c, concurrenceMap.getOrDefault(c, 0) + 1);
        }

        input.reset(); // Resetting the input stream to read it again. This assumes the input supports reset.

        return concurrenceMap;
    }

    private PriorityQueue<Node> generateHuffmanTree(HashMap<Character, Integer> concurrenceMap) {
        PriorityQueue<Node> table = new OrderedArrayPriorityQueue<>(Node::compareTo);

        for (char i : concurrenceMap.keySet()) {
            Node node = new Node(i, concurrenceMap.get(i));
            table.insert(node);
        }

        while (table.size() != 1) {
            Node left = table.pop();
            Node right = table.pop();
            Node node = new Node(left, right, left.value + right.value);
            table.insert(node);
        }

        return table;
    }

    private Map<Integer, Bits> createSymbolTable(PriorityQueue<Node> table) {
        // No symbols
        if (table.isEmpty()) return Collections.emptyMap();

        // Just one symbol
        if (table.peek().isLeaf()) return Collections.singletonMap(table.pop().value, new Bits().add(false));

        // Many symbols
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
        int messageSize = ByteBuffer.wrap(sizeBytes).getInt();

        // Decode Message
        int pos = 0;
        int current = -1;
        for (int i = 0; i < messageSize; i++) {
            Integer character = null;
            final Bits bits = new Bits();
            while (character == null) {
                pos = pos % 8;
                if (pos == 0) current = input.read();
                bits.add(bitAt(current, pos++));
                character = symbolTable.get(bits);
            }
            output.write(character);
        }
    }

    private Map<Bits, Integer> readSymbolTable(InputStream input) throws IOException {
        int stSize = input.read();
        Map<Bits, Integer> symbolTable = new HashMap<>();

        for (int i = 0; i < stSize; i++) {
            int character = input.read();
            Bits bits = readBits(input);
            symbolTable.put(bits, character);
        }

        return symbolTable;
    }

    private Bits readBits(InputStream input) throws IOException {
        int length = input.read();
        byte[] bytes = new byte[(length - 1) / Byte.SIZE + 1];
        input.read(bytes);
        Bits bits = new Bits();

        for (int bit = 0; bit < length; bit++) {
            bits.add(bitAt(bytes, bit));
        }

        return bits;
    }

    public static boolean bitAt(int value, int position) {
        int mask = 1 << (7 - position);
        return (value & mask) != 0;
    }

    private boolean bitAt(byte[] bytes, int nth) {
        final int pos = nth / 8;
        return pos < bytes.length && (bytes[pos] >> ((8 * (pos + 1) - (nth + 1)) % 8) & 1) != 0;
    }
}
