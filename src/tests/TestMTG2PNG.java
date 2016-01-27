package tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.Megatron;

/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class TestMTG2PNG {
    public static void main(String[] args) {

        String[] files = new String[]{"cartoon", "green_boat", "yellow_flower"};

        for (String file : files) {
            try {
                // Read .mtg
                System.out.print("Read file: " + file + ".mtg...");
                BufferedImage img = Megatron.read("resources/" + file + ".mtg");
                System.out.println(" DONE");

                // save as .png
                System.out.print("Write file: " + file + ".png...");
                ImageIO.write(img, "PNG", new File("resources/" + file + ".png"));
                System.out.println(" DONE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
