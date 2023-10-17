package anaydis.compression;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class RunLengthEncoding implements Compressor {

    @Override
    public void encode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        int counter = 1;
        int data = input.read();
        if (data == -1) return; //empty stream

        int next;

        while (true) {
            next = input.read();
            if (next == data) {
                counter++;
            } else {
                output.write(ByteBuffer.allocate(4).putInt(counter).array());
                output.write(data);

                if (next == -1) break; // End of stream

                data = next;
                counter = 1;
            }
        }
    }

    @Override
    public void decode(@NotNull InputStream input, @NotNull OutputStream output) throws IOException {
        byte[] countBytes = new byte[4];
        int value;

        while (input.read(countBytes) != -1) {
            int count = ByteBuffer.wrap(countBytes).getInt();
            value = input.read();
            for (int i = 0; i < count; i++) {
                output.write(value);
            }
        }
    }
}
