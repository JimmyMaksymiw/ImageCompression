package tests;

import io.Megatron;
import io.Segatron;
import operations.DCT;
import operations.RunLength;
import utils.ByteImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class TestAll {
    public static void main(String[] args) {

        String[] files = new String[]{"cartoon", "green_boat", "yellow_flower"};

        for (String file : files) {
            try {
                // Read .mtg
                System.out.print("Read file: " + file + ".mtg...");
                BufferedImage inputImage = Megatron.read("resources/" + file + ".mtg");
                System.out.println(" DONE");

                // Save original .png
                System.out.print("Write file: " + file + ".png...");
                ImageIO.write(inputImage, "PNG", new File("resources/" + file + ".png"));
                System.out.println(" DONE");

                // Compress with DCT(Discrete Cosine Transform).
                System.out.print("Compress DCT...");
                byte[] dct = DCT.compress(inputImage);
                System.out.println(" DONE");

                // Compress with RunLength
                System.out.print("Compress RunLength...");
                byte[] compressedBytes = RunLength.compress(dct);
                System.out.println(" DONE");

                // Save as .stg
                System.out.print("Save as '" + file + "_compressed.stg'...");
                Segatron.write(compressedBytes, inputImage.getWidth(), inputImage.getHeight(), "resources/" + file + "_compressed.stg");
                System.out.println(" DONE");

                // Read .stg
                System.out.print("Read file: " + file + "_compressed.stg...");
                ByteImage segatron = Segatron.read("resources/" + file + "_compressed.stg");
                System.out.println(" DONE");

                // Decompress with RunLength
                System.out.print("Decompress RunLength...");
                byte[] decompressedBytes = RunLength.decompress(segatron.getBytes());
                System.out.println(" DONE");

                // Decompress with DCT
                System.out.print("Decompress DCT...");
                BufferedImage decompressedImage = DCT.decompress(decompressedBytes, segatron.getWidth(), segatron.getHeight());
                System.out.println(" DONE");

                // Save as .png
                System.out.print("Save image as '" + file + "_decompressed.png'...");
                ImageIO.write(decompressedImage, "PNG", new File("resources/" + file + "_decompressed.png"));
                System.out.println(" DONE");

                // save as .mtg
                System.out.print("Write file: " + file + ".mtg...");
                Megatron.write(decompressedImage, "resources/" + file + "_new.mtg");
                System.out.println(" DONE");

                // Read the new .mtg
                System.out.print("Read file: " + file + "_new.mtg...");
                BufferedImage newMTG = Megatron.read("resources/" + file + "_new.mtg");
                System.out.println(" DONE");

                // Save as .png
                System.out.print("Write file: " + file + "_new.png...");
                ImageIO.write(newMTG, "PNG", new File("resources/" + file + "_new.png"));
                System.out.println(" DONE");

                //  print info
                System.out.println("\nINFO:\n" +
                        "Original size: " + ((inputImage.getWidth() * inputImage.getHeight() * 3) / 1024) + "\n" +
                        "Compressed size: " + (compressedBytes.length / 1024) + "\n" +
                        "Compress ratio: " + (double) compressedBytes.length / (double) (inputImage.getWidth() * inputImage.getHeight() * 3) + " %\n"
                );

                for (int i = 0; i < 30; i++) System.out.print("-");
                System.out.println();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
