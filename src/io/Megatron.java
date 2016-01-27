package io;

import utils.ByteUtil;
import java.awt.image.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Megatron file format.
 * Class that reads and writes .mtg files. Uses a HEADER too identify that the file is correct.
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class Megatron {
    // Header to signify Megatron file format.
    final static byte[] HEADER = "mEgaMADNZ!".getBytes(StandardCharsets.US_ASCII);

    public final static class InvalidMegatronFileException extends IOException {
    }

    /**
     * Writes image information to a file (.mtg).
     * @param bufferedImage The BufferedImage-object.
     * @param filePath The filepath to write the file.
     * @throws IOException
     */
    public static void write(BufferedImage bufferedImage, String filePath) throws IOException {
        OutputStream outputStream = new FileOutputStream(filePath);

        // Write header
        outputStream.write(HEADER);

        // Write width & height
        ByteUtil.write4bytes(bufferedImage.getWidth(), outputStream);
        ByteUtil.write4bytes(bufferedImage.getHeight(), outputStream);

        // Write pixel data
        Raster raster = bufferedImage.getRaster();
        int[] pxl = new int[3];

        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                raster.getPixel(i, j, pxl);
                outputStream.write(pxl[0]);
                outputStream.write(pxl[1]);
                outputStream.write(pxl[2]);
            }
        }
        outputStream.close();
    }

    /**
     * Reads image information from a file (.mtg).
     * @param filePath the filepath to read the file (.stg) from.
     * @return A BufferedImage-object that contains the image information.
     * @throws IOException
     */
    public static BufferedImage read(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);

        // Read header
        for (byte aHEADER : HEADER) if (inputStream.read() != aHEADER) throw new InvalidMegatronFileException();

        // Read width & height
        int width = ByteUtil.read4bytes(inputStream);
        int height = ByteUtil.read4bytes(inputStream);

        // Read pixel data
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        byte[] pxlBytes = new byte[3];
        int[] pxl = new int[3];
        WritableRaster imgRaster = image.getRaster();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (inputStream.read(pxlBytes) != 3) throw new EOFException();

                pxl[0] = pxlBytes[0];
                pxl[1] = pxlBytes[1];
                pxl[2] = pxlBytes[2];
                imgRaster.setPixel(i, j, pxl);
            }
        }
        inputStream.close();
        return image;
    }
}


