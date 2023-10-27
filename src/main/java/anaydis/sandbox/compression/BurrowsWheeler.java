package anaydis.sandbox.compression;

import java.io.*;

public class BurrowsWheeler {

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
        anaydis.compression.BurrowsWheeler bwt = new anaydis.compression.BurrowsWheeler();
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
