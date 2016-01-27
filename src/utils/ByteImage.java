package utils;

/**
 * Class that represent a container that hold information about a image.
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class ByteImage {
    private int width;
    private int height;
    private byte[] bytes;

    /**
     * Creates a new ByteImage-object with the provided values.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param bytes The byte-array that holds the pixel information.
     */
    public ByteImage(int width, int height, byte[] bytes) {
        this.width = width;
        this.height = height;
        this.bytes = bytes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
