package anaydis.compression;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;


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
    }

    public static void main(String[] args) throws IOException {
        // Convert binary strings to bytes
        String[] binaryStrings = {
                "1100001", "1100001", "1100001", "1100001", "1100001", "1100001", "1100001", "1100001", "1100001",
                "100000", "1100001", "1101101", "1100001", "1110011", "1100001", "100000", "1100001", "1101101",
                "1100001", "1110011", "1100001", "100000", "1100001", "1101101", "1100001", "1110011", "1100001"
        };

        byte[] inputData = new byte[binaryStrings.length];
        for (int i = 0; i < binaryStrings.length; i++) {
            inputData[i] = (byte) Integer.parseInt(binaryStrings[i], 2);
        }

        ByteArrayInputStream input = new ByteArrayInputStream(inputData);

        // Step 2: Encode the input
        BurrowsWheeler bwt = new BurrowsWheeler();
        ByteArrayOutputStream encodedOutput = new ByteArrayOutputStream();
        bwt.encode(input, encodedOutput);

        byte[] encodedBytes = encodedOutput.toByteArray();
        System.out.println("Encoded Bytes:");
        for (byte b : encodedBytes) {
            System.out.print(b + " ");
        }
        System.out.println();

        // Step 3: Decode the encoded message
        ByteArrayInputStream encodedInput = new ByteArrayInputStream(encodedBytes);
        ByteArrayOutputStream decodedOutput = new ByteArrayOutputStream();
        bwt.decode(encodedInput, decodedOutput);

        byte[] decodedBytes = decodedOutput.toByteArray();
        System.out.println("Decoded Bytes:");
        for (byte b : decodedBytes) {
            System.out.print(b + " ");
        }
        System.out.println();


    }
}
