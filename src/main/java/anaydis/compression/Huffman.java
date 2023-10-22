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
        List<Integer> text = inputToString(input);

        HashMap<Character, Integer> concurrenceMap = concurrenceMap(text);

        PriorityQueue<Node> table = generateHuffmanTree(concurrenceMap);

        Map<Integer, Bits> symbolTable = createSymbolTable(table);

        //Write the size of the symbol table
        output.write(symbolTable.size());

        //Write the symbol table
        for (final Map.Entry<Integer, Bits> current : symbolTable.entrySet()) {
            Integer character = current.getKey();
            output.write(character);
            Bits bits = current.getValue();
            bits.writeInto(output);
        }

        // Write the size of the message
        byte[] size = ByteBuffer.allocate(4).putInt(text.size()).array();
        for (byte byteLength : size) {
            output.write(byteLength);
        }

        // Write the message
        BitsOutputStream content = new BitsOutputStream();
        for (int character : text) {
            Bits code = symbolTable.get(character);
            content.write(code);
        }

        output.write(content.toByteArray());

    }

    private List<Integer> inputToString(InputStream input) throws IOException {
        List<Integer> text = new ArrayList<>();
        int current = input.read();

        while (current != -1){
            text.add(current);
            current = input.read();
        }

        return text;
    }

    private HashMap<Character, Integer> concurrenceMap(List<Integer> text){
        HashMap<Character, Integer> concurrenceMap = new HashMap<>();

        for (int i : text) {
            char c = (char) i;
            if (concurrenceMap.containsKey(c))
                concurrenceMap.put(c, concurrenceMap.get(c) + 1);
            else
                concurrenceMap.put(c, 1);
        }

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
        byte[] bytes = new byte[(length - 1 )/ Byte.SIZE + 1];
        input.read(bytes);
        Bits bits = new Bits();

        for (int bit = 0; bit < length; bit++) {
            bits.add(bitAt(bytes, bit));
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



