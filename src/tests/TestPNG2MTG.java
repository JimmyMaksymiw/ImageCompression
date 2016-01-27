package tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import io.Megatron;

/**
 * @author Jimmy Maksymiw & Kalle Bornemark
 */
public class TestPNG2MTG {
    public static void main(String[] args) {

        String[] files = new String[]{"cartoon", "green_boat", "yellow_flower"};

        for (String file : files) {
            try {
                // Read .png
                System.out.print("Read file: " + file + ".png...");
                BufferedImage img = ImageIO.read(new File("resources/" + file + ".png"));
                System.out.println(" DONE");

                // save as .mtg
                System.out.print("Write file: " + file + ".mtg...");
                Megatron.write(img, "resources/" + file + ".mtg");
                System.out.println(" DONE");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
