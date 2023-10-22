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
        List<Integer> text = new ArrayList<>();
        inputToString(input, text);
        PriorityQueue<Node> table = getTable(text);

        Map<Integer, Bits> symbolTable = createSymbolTable(table);
        output.write(symbolTable.size());

        for (final Map.Entry<Integer, Bits> current : symbolTable.entrySet()) {
            Integer character = current.getKey();
            output.write(character);
            Bits bits = current.getValue();
            bits.writeInto(output);
        }

        byte[] size = ByteBuffer.allocate(4).putInt(text.size()).array();
        for (byte byteLength : size) {
            output.write(byteLength);
        }

        BitsOutputStream content = new BitsOutputStream();
        for (final Integer character : text) {
            final Bits code = symbolTable.get(character);
            content.write(code);
        }
        output.write(content.toByteArray());
    }

    @NotNull
    private static PriorityQueue<Node> getTable(List<Integer> text) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i : text) {
            char c = (char) i;
            if (map.containsKey(c)) { map.put(c, map.get(c) + 1); } else { map.put(c, 1); }
        }
        PriorityQueue<Node> table = new OrderedArrayPriorityQueue<>(Node::compareTo);
        for (char i : map.keySet()) {
            Node node = new Node(i, map.get(i));
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

    private void inputToString(InputStream input, List<Integer> text) throws IOException {
        int current = input.read();
        while (current != -1) {
            text.add(current);
            current = input.read();
        }
    }

    private Map<Integer, Bits> createSymbolTable(PriorityQueue<Node> table) {
        // Primero trato los casos base, si est� vac�a retorno mapa vac�o.
        if (table.isEmpty()) return Collections.emptyMap();
        // Si solo tengo un nodo, retorno un mapa inmutable de un solo elemento
        if (table.peek().isLeaf()) return Collections.singletonMap(table.pop().value, new Bits().add(false));
        // �ltima opci�n, la m�s com�n, tengo varios s�mbolos y con el collect los voy recolectando y asignando bits
        final Node root = table.pop();
        final Map<Integer, Bits> result = new LinkedHashMap<>();
        root.collect(result, new Bits());
        return result;
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
         Map<Bits, Integer> symbolTable = readSymbolTable(input);
        byte[] sizeBytes = new byte[4]; 
        input.read(sizeBytes);
        int total = ByteBuffer.wrap(sizeBytes).getInt();
        
        int pos = 0;
        int current = -1;
        for (int i = 0; i < total; i++) {
            Integer character = null;
            Bits bits = new Bits();
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
        int symbols = input.read();
        Map<Bits, Integer> symbolTable = new TreeMap<>(Huffman::compareBits);
        for (int i = 0; i < symbols; i++) {
            int character = input.read();
            Bits code = readBits(input);
            symbolTable.put(code, character);
        }
        return symbolTable;
    }

    private static <T> int compareBits(T bit1, T bit2) {
        String bits1 = bit1.toString();
        String bits2 = bit2.toString();
        return bits1.compareTo(bits2);
    }

    private Bits readBits(InputStream input) throws IOException {
        int length = input.read();
        byte[] bytes = new byte[(length - 1 )/ Byte.SIZE + 1];
        input.read(bytes);
        Bits result = new Bits();
        for (int bit = 0; bit < length; bit++) {
            result.add(bitAt(bytes, bit));
        }
        return result;
    }
    public static boolean bitAt(int value, int position) {
        int mask = 1 << (7 - position);
        return (value & mask) != 0;
    }
    private boolean bitAt(byte[] bytes, int nth) {
        int pos = nth / 8;
        return pos < bytes.length && (bytes[pos] >> ((8 * (pos + 1) - (nth + 1)) % 8) & 1 ) != 0;
    }
}
