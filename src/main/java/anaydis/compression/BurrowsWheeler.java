package anaydis.compression;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BurrowsWheeler implements Compressor {

    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        String message = inputToString(input);
        int size = message.length();

        Integer[] indices = new Integer[size];

        for (int i = 0; i < size; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                for (int offset = 0; offset < size; offset++) {
                    char c1 = message.charAt((i1 + offset) % message.length());
                    char c2 = message.charAt((i2 + offset) % message.length());
                    if (c1 != c2) {
                        return Character.compare(c1, c2);
                    }
                }
                return 0;
            }
        });

        //Construct the coded message
        StringBuilder codedMessage = new StringBuilder();
        for (int i = 0; i < size; i++) {
            char character = message.charAt((indices[i] - 1 + size) % size);
            codedMessage.append(character);
        }

        //Write the position of the original message in the last 4 bytes
        int originalPosition = Arrays.asList(indices).indexOf(0);
        byte[] positionBytes = ByteBuffer.allocate(4).putInt(originalPosition).array();
        output.write(positionBytes);

        //Write the coded message
        output.write(codedMessage.toString().getBytes());

    }

    private String inputToString(InputStream input) throws IOException {
        StringBuilder chars = new StringBuilder();
        int current = input.read();

        while (current != -1){
            chars.append((char) current);
            current = input.read();
        }

        return chars.toString();
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        int originalMessagePosition = readOriginalMessagePosition(input);
        String encodedMessage = inputToString(input);
        int messageSize = encodedMessage.length();

        char[] firstColumn = encodedMessage.toCharArray();
        Arrays.sort(firstColumn);

        int[] t = buildT(firstColumn, encodedMessage.toCharArray());

        StringBuilder originalMessage = new StringBuilder();
        int index = originalMessagePosition;
        for (int i = 0; i < messageSize; i++) {
            originalMessage.append(encodedMessage.charAt(index));
            index = t[index];
        }

        output.write(originalMessage.toString().getBytes());
    }

    private Integer readOriginalMessagePosition(InputStream input) throws IOException {
        byte[] sizeBytes = new byte[4];
        input.read(sizeBytes);
        return ByteBuffer.wrap(sizeBytes).getInt();
    }

    private int[] buildT(char[] firstColumn, char[] lastColumn) {
        int[] t = new int[firstColumn.length];
        int[] nextAvailable = new int[256];  // Assuming extended ASCII

        for (int i = 0; i < firstColumn.length; i++) {
            char c = lastColumn[i];
            t[i] = indexOf(firstColumn, c, nextAvailable[c]++);
        }

        return t;
    }

    private int indexOf(char[] column, char c, int fromIndex) {
        for (int i = fromIndex; i < column.length; i++) {
            if (column[i] == c) return i;
        }
        return -1;
    }
}
