package io;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by Kalle Bornemark on 2016-01-17.
 */
public class Segatron {
    final static byte[] magic = "segatronROFLboll".getBytes(StandardCharsets.US_ASCII);

    public final static class SegatronException extends IOException {}

    public static void write(BufferedImage img, String fnam) throws IOException {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] pxl = new int[3];
        Raster imgr = img.getRaster();
        OutputStream out = new FileOutputStream(fnam);
        out.write(magic);
        write4bytes(width, out);
        write4bytes(height, out);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imgr.getPixel(i, j, pxl);
                out.write(pxl[0]);
                out.write(pxl[1]);
                out.write(pxl[2]);
            }
        }
        out.close();
    }

    public static BufferedImage read(String fnam) throws IOException {
        InputStream in = new FileInputStream(fnam);

        // Check magic value.
        for (int i = 0; i < magic.length; i++) {
            if (in.read() != magic[i]) {
                throw new SegatronException();
            }
        }
        int width = read4bytes(in);
        int height = read4bytes(in);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        byte[] pxlBytes = new byte[3];
        int[] pxl = new int[3];
        WritableRaster imgr = img.getRaster();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (in.read(pxlBytes) != 3) {
                    throw new EOFException();
                }
                pxl[0] = pxlBytes[0];
                pxl[1] = pxlBytes[1];
                pxl[2] = pxlBytes[2];
                imgr.setPixel(i, j, pxl);
            }
        }
        in.close();
        return img;
    }

    /**
     * Writes an int as 4 bytes, big endian.
     */
    private static void write4bytes(int v, OutputStream out) throws IOException {
        out.write(v >>> 3 * 8);
        out.write(v >>> 2 * 8 & 255);
        out.write(v >>> 1 * 8 & 255);
        out.write(v & 255);
    }

    /**
     * Reads an int as 4 bytes, big endian.
     */
    private static int read4bytes(InputStream in) throws IOException {
        int b, v;
        b = in.read();
        if (b < 0) {
            throw new EOFException();
        }
        v = b << 3 * 8;
        b = in.read();
        if (b < 0) {
            throw new EOFException();
        }
        v |= b << 2 * 8;
        b = in.read();
        if (b < 0) {
            throw new EOFException();
        }
        v |= b << 1 * 8;
        b = in.read();
        if (b < 0) {
            throw new EOFException();
        }
        v |= b;
        return v;
    }
}
