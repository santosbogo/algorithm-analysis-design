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
        Map<Integer, Bits> symbolTable = createSymbolTable(table);

        //Write the size of the symbol table
        output.write(symbolTable.size());

        //Write the symbol table
        for (final Map.Entry<Integer, Bits> current : symbolTable.entrySet()) {
            final Integer character = current.getKey();
            output.write(character);
            final Bits bits = current.getValue();
            bits.writeInto(output);
        }

        // Write the size of the message
        byte[] size = ByteBuffer.allocate(4).putInt(chars.size()).array();
        for (byte byteLength : size) {
            output.write(byteLength);
        }

        // Write the message
        final BitsOutputStream contenido = new BitsOutputStream();
        for (int character : chars) {
            final Bits code = symbolTable.get(character);
            contenido.write(code);
        }
        output.write(contenido.toByteArray());

    }

    private List<Character> inputToString(InputStream input) throws IOException {
        List<Character> chars = new ArrayList<>();
        char current = (char) input.read();

        while (current != -1){
            chars.add(current);
            current = (char) input.read();
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
            Node node = new Node(left, right, left.value + right.value);
            table.insert(node);
        }

        return table;
    }


    private Map<Integer, Bits> createSymbolTable(PriorityQueue<Node> table) {
        //No symbols
        if (table.isEmpty()) return Collections.emptyMap();

        //Just one symbol
        if (table.peek().isLeaf()) return Collections.singletonMap(table.pop().value, new Bits().add(false));

        //Many symbols
        final Node root = table.pop();
        final Map<Integer, Bits> result = new LinkedHashMap<>();
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
                currentBits.add(bitAt(input.read(), 7)); // Asumiendo que lees un bit a la vez.
                character = symbolTable.get(currentBits);
            }
            output.write(character);
        }
    }

    private Map<Bits, Integer> readSymbolTable(InputStream input) throws IOException {
        int symbols = input.read();
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
            if (bitAt(bytes[i / 8], i % 8)) {
                bits.add(true);
            } else {
                bits.add(false);
            }
        }

        return bits;
    }

    public static boolean bitAt(int value, int position) {
        // Create a mask with a 1 at the specific position
        int mask = 1 << (7 - position); // Assuming 8 bits in a byte (0 to 7)
        // Use bitwise AND to check if the bit at the specified position is 1 or 0
        return (value & mask) != 0;
    }
    private boolean bitAt(byte[] bytes, int nth) {
        final int pos = nth / 8;
        return pos < bytes.length && (bytes[pos] >> ((8 * (pos + 1) - (nth + 1)) % 8) & 1 ) != 0;
    }

}

