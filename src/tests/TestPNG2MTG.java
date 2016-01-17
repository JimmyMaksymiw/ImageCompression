package tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import io.Megatron;

/**
 * Created by Kalle Bornemark on 2016-01-17.
 */
public class TestPNG2MTG {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("resources/cartoon.png"));
            Megatron.write(img, "resources/cartoon.mtg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
