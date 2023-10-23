package anaydis.compression;

import anaydis.bit.Bits;
import anaydis.bit.BitsOutputStream;
import anaydis.search.OrderedArrayPriorityQueue;
import anaydis.search.PriorityQueue;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Huffman implements Compressor {

    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        byte[] inputData = captureInput(input);
        HashMap<Character, Integer> concurrenceMap = concurrenceMap(inputData);

        PriorityQueue<Node> table = generateHuffmanTree(concurrenceMap);

        Map<Integer, Bits> symbolTable = createSymbolTable(table);

        // Write the size of the symbol table as an integer
        output.write(ByteBuffer.allocate(4).putInt(symbolTable.size()).array());

        // Write the symbol table
        for (final Map.Entry<Integer, Bits> current : symbolTable.entrySet()) {
            Integer character = current.getKey();
            output.write(character);
            Bits bits = current.getValue();
            bits.writeInto(output);
        }

        // Write the message
        BitsOutputStream content = new BitsOutputStream();
        for (byte b : inputData) {
            char currentChar = (char) b;
            Bits code = symbolTable.get((int) currentChar);
            content.write(code);
        }

        output.write(content.toByteArray());
    }

    private byte[] captureInput(InputStream input) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }


    private HashMap<Character, Integer> concurrenceMap(byte[] inputData) {
        HashMap<Character, Integer> concurrenceMap = new HashMap<>();
        for (byte b : inputData) {
            char c = (char) b;
            concurrenceMap.put(c, concurrenceMap.getOrDefault(c, 0) + 1);
        }
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
            Node node = new Node(left, right);
            table.insert(node);
        }

        return table;
    }

    private Map<Integer, Bits> createSymbolTable(PriorityQueue<Node> table) {
        // No symbols
        if (table.isEmpty()) return Collections.emptyMap();

        // Just one symbol
        if (table.peek().isLeaf()) return Collections.singletonMap(table.pop().frequency, new Bits().add(false));

        // Many symbols
        Node root = table.pop();
        Map<Integer, Bits> result = new LinkedHashMap<>();
        root.collect(result, new Bits());

        return result;
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        // Fixed the reading of symbol table size
        byte[] sizeBytes = new byte[4];
        input.read(sizeBytes);
        int stSize = ByteBuffer.wrap(sizeBytes).getInt();

        Map<Bits, Integer> symbolTable = readSymbolTable(input, stSize);
        Node root = buildDecodingTree(symbolTable);

        // Decode Message
        Node currentNode = root;
        int currentByte = input.read();
        int bitPos = 0;
        while (currentByte != -1) {
            boolean bit = bitAt(currentByte, bitPos);
            bitPos++;
            if (bitPos == 8) {
                bitPos = 0;
                currentByte = input.read();
            }
            currentNode = bit ? currentNode.right : currentNode.left;
            if (currentNode.isLeaf()) {
                output.write(currentNode.character);
                currentNode = root;  // Reset for the next character
            }
        }
    }

    private Node buildDecodingTree(Map<Bits, Integer> symbolTable) {
        Node root = new Node(null, 0);
        for (Map.Entry<Bits, Integer> entry : symbolTable.entrySet()) {
            Bits encodedBits = entry.getKey();
            Integer character = entry.getValue();
            Node currentNode = root;
            for (int i = 0; i < encodedBits.getLength(); i++) {
                boolean bit = ((encodedBits.getValue() >> (encodedBits.getLength() - i - 1)) & 1) == 1;
                if (bit) {
                    if (currentNode.right == null) {
                        currentNode.right = new Node(null, 0);
                    }
                    currentNode = currentNode.right;
                } else {
                    if (currentNode.left == null) {
                        currentNode.left = new Node(null, 0);
                    }
                    currentNode = currentNode.left;
                }
            }
            currentNode.character = character;
        }
        return root;
    }

    private Map<Bits, Integer> readSymbolTable(InputStream input, int stSize) throws IOException {
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

    public static boolean bitAt(int frequency, int position) {
        int mask = 1 << (7 - position);
        return (frequency & mask) != 0;
    }

    private boolean bitAt(byte[] bytes, int nth) {
        final int pos = nth / 8;
        return pos < bytes.length && (bytes[pos] >> ((8 * (pos + 1) - (nth + 1)) % 8) & 1) != 0;
    }
}

