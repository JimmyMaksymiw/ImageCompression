package utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class that is used to read/write bytes.
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class ByteUtil {

    /**
     * Writes an int as 4 bytes, big endian.
     * @param value The int-value to be written to 4 bytes.
     * @param outputStream The stream to write the int to.
     * @throws IOException
     */
    public static void write4bytes(int value, OutputStream outputStream) throws IOException {
        outputStream.write(value >>> 3 * 8);
        outputStream.write(value >>> 2 * 8 & 255);
        outputStream.write(value >>> 1 * 8 & 255);
        outputStream.write(value & 255);
    }

    /**
     * Reads an int as 4 bytes, big endian.
     * @param inputStream the stream to read the int-value.
     * @return Int-value read from 4 bytes..
     * @throws IOException
     */
    public static int read4bytes(InputStream inputStream) throws IOException {
        int b, value = 0;
        for (int i = 3; i >= 0; i--) {
            b = inputStream.read();
            if (b < 0) throw new EOFException();
            value |= b << i * 8;
        }
        return value;
    }
}
