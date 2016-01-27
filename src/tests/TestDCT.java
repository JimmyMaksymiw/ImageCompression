package tests;

import io.Megatron;
import operations.DCT;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class TestDCT {
    public static void main(String[] args) throws FileNotFoundException {

        String[] files = new String[]{"cartoon", "green_boat", "yellow_flower"};

        for (String file : files) {
            try {
                // Read .mtg
                System.out.print("Read file: " + file + ".mtg...");
                BufferedImage inputImage = Megatron.read("resources/" + file + ".mtg");
                System.out.println(" DONE");

                // Compress with DCT
                System.out.print("DCT...");
                byte[] dct = DCT.compress(inputImage);
                System.out.println(" DONE");

                // Decompress with DCT
                System.out.print("Inverse DCT...");
                BufferedImage outputImage = DCT.decompress(dct, inputImage.getWidth(), inputImage.getHeight());
                System.out.println(" DONE");

                // Save image
                System.out.print("Save image...");
                ImageIO.write(outputImage, "PNG", new File("resources/" + file + "_test_DCT.png"));
                System.out.println(" DONE");

                // print info
                System.out.println("Original size: " + (inputImage.getWidth() * inputImage.getHeight() * 3) / 1024);
                System.out.println("Compressed size: " + dct.length / 1024);
                System.out.println("Decompressed size: " + (outputImage.getWidth() * outputImage.getHeight() * 3) / 1024);

                for (int i = 0; i < 30; i++) {
                    System.out.print("-");
                }
                System.out.println();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
