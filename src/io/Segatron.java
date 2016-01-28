package io;

import utils.ByteImage;
import utils.ByteUtil;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Segatron file format.
 * Class that reads and writes .stg files. Uses a HEADER to identify that the file is correct.
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Segatron {
    // Header to signify Segatron file format.
    final static byte[] HEADER = "segaSADNeZ".getBytes(StandardCharsets.US_ASCII);

    public final static class InvalidSegatronException extends IOException {
    }

    /**
     * Writes image information to a file (.stg) with the provided values.
     * @param bytes The array that contains the pixel information.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param filePath The filepath to write the file.
     * @throws IOException
     */
    public static void write(byte[] bytes, int width, int height, String filePath) throws IOException {
        OutputStream outputStream = new FileOutputStream(filePath);

        // Write header
        outputStream.write(HEADER);

        // Write width & height
        ByteUtil.write4bytes(width, outputStream);
        ByteUtil.write4bytes(height, outputStream);

        // Write pixel data
        outputStream.write(bytes);

        // Close the stream
        outputStream.close();
    }

    /**
     * Reads image information from a file (.stg).
     * @param filePath The filepath to read the file (.stg) from.
     * @return A ByteImage-object that contains the image information.
     * @throws IOException
     */
    public static ByteImage read(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);

        // Read header
        for (byte aHEADER : HEADER) if (inputStream.read() != aHEADER) throw new InvalidSegatronException();

        // Read width & height
        int width = ByteUtil.read4bytes(inputStream);
        int height = ByteUtil.read4bytes(inputStream);

        // Read pixel data
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        inputStream.close();
        return new ByteImage(width, height, buffer.toByteArray());
    }
}
