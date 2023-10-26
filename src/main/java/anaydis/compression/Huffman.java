package anaydis.compression;

import anaydis.bit.Bits;
import anaydis.bit.BitsOutputStream;
import anaydis.search.OrderedArrayPriorityQueue;
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
        HashMap<Character, Integer> concurrenceMap = concurrenceMap(chars); // The char as key, its frequency as value
        PriorityQueue<Node> table = generateHuffmanTrie(concurrenceMap);
        Map<Character, Bits> symbolTable = createSymbolTable(table);

        //Write the size of the symbol table
        output.write(symbolTable.size());

        //Write the symbol table
        for (Map.Entry<Character, Bits> current : symbolTable.entrySet()) {
            Character character = current.getKey();
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
        final BitsOutputStream content = new BitsOutputStream();
        for (Character character : chars) {
            final Bits code = symbolTable.get(character);
            content.write(code);
        }
        output.write(content.toByteArray());

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

    private PriorityQueue<Node> generateHuffmanTrie(HashMap<Character, Integer> concurrenceMap){
        PriorityQueue<Node> table = new PriorityQueue<>();

        //Insert characters into the priority queue
        for (char i : concurrenceMap.keySet()) {
            Node node = new Node(i, concurrenceMap.get(i));
            table.add(node);
        }

        //Create the huffman trie
        while (table.size() != 1) {
            Node left = table.poll();
            Node right = table.poll();
            Node node = new Node(left, right); //New frequency is left + right
            table.add(node);
        }

        return table;
    }

    private Map<Character, Bits> createSymbolTable(PriorityQueue<Node> table) {
        //No symbols
        if (table.isEmpty()) return Collections.emptyMap();

        //Just one symbol
        if (table.peek().isLeaf()) return Collections.singletonMap((char) table.poll().frequency, new Bits().add(false));

        //Many symbols
        final Node root = table.poll();
        final Map<Character, Bits> result = new LinkedHashMap<>();
        root.collect(result, new Bits());
        return result;
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        Map<Bits, Character> symbolTable = readSymbolTable(input);
        int messageSize = readMessageSize(input);

        //Decode message
        int pos = 0;
        int current = -1;

        for (int i = 0; i < messageSize; i++) {
            Character character = null;
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

    private Map<Bits, Character> readSymbolTable(InputStream input) throws IOException {
        int STSize = input.read();
        final Map<Bits, Character> symbolTable = new TreeMap<>(Huffman::compareBits);
        for (int i = 0; i < STSize; i++) {
            final int character = input.read();
            final Bits code = readBits(input);
            symbolTable.put(code, (char) character);
        }
        return symbolTable;
    }

    private Bits readBits(InputStream input) throws IOException {
        final int length = input.read();
        final byte[] bytes = new byte[(length - 1 )/ Byte.SIZE + 1];
        input.read(bytes);
        final Bits result = new Bits();
        for (int bit = 0; bit < length; bit++) { result.add(bitAt(bytes, bit)); }
        return result;
    }

    private Integer readMessageSize(InputStream input) throws IOException {
        byte[] sizeBytes = new byte[4];
        input.read(sizeBytes);
        return ByteBuffer.wrap(sizeBytes).getInt();
    }

    public static boolean bitAt(int value, int position) {
        int mask = 1 << (7 - position);
        return (value & mask) != 0;
    }
    private boolean bitAt(byte[] bytes, int nth) {
        final int pos = nth / 8;
        return pos < bytes.length && (bytes[pos] >> ((8 * (pos + 1) - (nth + 1)) % 8) & 1 ) != 0;
    }

    private static <T> int compareBits(T bit1, T bit2) {
        String bits1 = bit1.toString();
        String bits2 = bit2.toString();
        return bits1.compareTo(bits2);
    }

}
