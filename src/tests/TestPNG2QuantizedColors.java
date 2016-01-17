package tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import tools.QuantizeColors;

/**
 * Created by Kalle Bornemark on 2016-01-17.
 */
public class TestPNG2QuantizedColors {
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("resources/green_boat.png"));
            ImageIO.write(QuantizeColors.apply(img), "PNG", new File("resources/green_boat_quantized.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}