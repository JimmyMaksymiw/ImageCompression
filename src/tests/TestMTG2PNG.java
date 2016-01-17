package tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.Megatron;

/**
 * Created by Kalle Bornemark on 2016-01-17.
 */
public class TestMTG2PNG {
    public static void main(String[] args) {
        try {
            BufferedImage img = Megatron.read("resources/cartoon.mtg");
            ImageIO.write(img, "PNG", new File("resources/cartoon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
