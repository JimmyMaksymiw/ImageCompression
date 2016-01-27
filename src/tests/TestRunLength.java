package tests;

import operations.RunLength;

/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class TestRunLength {
    public static void main(String[] args) {

        byte[] stringBytes = "aaaaaabbbbbcaaabbcddddddddddddddddddddddddddeeeffeefefefefe".getBytes();
//        byte[] stringBytes = "abcdefghijklmnopqrstuvxyz".getBytes(); // output is bigger then input
        byte[] compressedBytes = RunLength.compress(stringBytes);
        byte[] decompressedBytes = RunLength.decompress(compressedBytes);

        System.out.println(
                "Input size: " + stringBytes.length + "B\n" +
                        "Compressed size: " + compressedBytes.length + "B\n" +
                        "Decompressed size: " + decompressedBytes.length + "B\n" +
                        "Input:\n" + new String(stringBytes) + "\n" +
                        "Decompressed output:\n" + new String(decompressedBytes)
        );
    }
}
