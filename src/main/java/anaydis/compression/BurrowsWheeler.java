package anaydis.compression;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class BurrowsWheeler implements Compressor {

    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        String inputString = new String(input.readAllBytes());
        int originalIndex = 0;

        // Create an array of indices
        Integer[] indices = new Integer[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            indices[i] = i;
        }

        // Sort the indices based on the lexicographic order of the rotations they represent
        Arrays.sort(indices, (i1, i2) -> {
            for (int offset = 0; offset < inputString.length(); offset++) {
                int pos1 = (i1 + offset) % inputString.length();
                int pos2 = (i2 + offset) % inputString.length();
                if (inputString.charAt(pos1) != inputString.charAt(pos2)) {
                    return Character.compare(inputString.charAt(pos1), inputString.charAt(pos2));
                }
            }
            return 0;
        });

        // Construct the BWT output using the sorted indices
        StringBuilder bwtBuilder = new StringBuilder();
        for (int index : indices) {
            int lastIndex = (index - 1 + inputString.length()) % inputString.length();
            bwtBuilder.append(inputString.charAt(lastIndex));
            if (index == 0) {
                originalIndex = bwtBuilder.length() - 1;
            }
        }

        output.write(bwtBuilder.toString().getBytes());
        output.write(originalIndex);
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        String bwtString = new String(input.readAllBytes(), 0, input.available() - 1); // read all but the last byte
        int originalIndex = input.read(); // read the last byte as the index
        String[] decoding = new String[bwtString.length()];
        for (int i = 0; i < decoding.length; i++) {
            decoding[i] = "";
        }
        for (int j = 0; j < bwtString.length(); j++) {
            for (int i = 0; i < bwtString.length(); i++) {
                decoding[i] = bwtString.charAt(i) + decoding[i];
            }
            Arrays.sort(decoding);
        }
        String decodedString = decoding[originalIndex];
        output.write(decodedString.getBytes());
    }
}
