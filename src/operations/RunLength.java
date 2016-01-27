package operations;

import java.io.ByteArrayOutputStream;

/**
 * Class that does a simple RunLength encoding on a byte-array. Useful if there are many repetitions in the array.
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class RunLength {

    /**
     * Compress the provided byte-array. Useful if there are many repetitions in the array. otherwise it may be bigger.
     * The first byte is number of repetition and the second is the symbol.
     * Example: "aaaaaaaaa" is compressed to [9,a]
     * @param bytes the byte-array to be compressed
     * @return the compressed byte-array
     */
    public static byte[] compress(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte lastByte = bytes[0];
        int matchCount = 1;
        for (int i = 1; i < bytes.length; i++) {
            byte thisByte = bytes[i];
            if (lastByte == thisByte && matchCount < 127) {
                matchCount++;
            } else {
                out.write((byte) matchCount);
                out.write((byte) lastByte);
                matchCount = 1;
                lastByte = thisByte;
            }
        }
        out.write((byte) matchCount);
        out.write((byte) lastByte);
        // TODO: check if out is bigger then input? Needs to be handled in decompress() to.
        return out.toByteArray();
    }

    /**
     * Decompressing the provided byte-array.
     * The first byte is number of repetition and the second is the symbol.
     * Example: [9,a] is decompressed as "aaaaaaaaa"
     * @param bytes The byte-array to be decompressed
     * @return The decompressed byte-array
     */
    public static byte[] decompress(byte[] bytes) {
        ByteArrayOutputStream dest = new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length; i += 2) {
            byte tmp = bytes[i];
            while (tmp > 0) {
                dest.write(bytes[i + 1]);
                tmp--;
            }
        }
        return dest.toByteArray();
    }
}
