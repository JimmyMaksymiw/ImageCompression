package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

/**
 * Created by Kalle Bornemark on 2016-01-17.
 */
public class QuantizeColors {
    public static BufferedImage apply(BufferedImage src) {
        // Create empty destination image
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);

        // Fill with some colors to make transparent
        Graphics g = dest.getGraphics();
        g.setColor(new Color(231, 20, 189));
        g.fillRect(0, 0, dest.getWidth(), dest.getHeight());

        ColorModel cm = dest.getColorModel();

        // Error handling
        if (!(cm instanceof IndexColorModel)) {
            return dest; // sorry...
        }

        // Get IndexColorModel
        IndexColorModel icm = (IndexColorModel) cm;
        WritableRaster raster = dest.getRaster();

        int pixel = raster.getSample(0, 0, 0);
        int size = icm.getMapSize();

        // Create byte arrays for colors
        byte[] reds = new byte[size];
        byte[] greens = new byte[size];
        byte[] blues = new byte[size];

        // Get colors
        icm.getReds(reds);
        icm.getGreens(greens);
        icm.getBlues(blues);

        IndexColorModel icm2 = new IndexColorModel(8, size, reds, greens, blues, pixel);
        dest = new BufferedImage(icm2, raster, dest.isAlphaPremultiplied(), null);

        dest.createGraphics().drawImage(src, 0, 0, null);
        return dest;
    }
}
