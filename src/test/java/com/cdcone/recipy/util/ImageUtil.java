package com.cdcone.recipy.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {
    public static byte[] randomImage() throws IOException {
        BufferedImage random = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                random.setRGB(x, y, 255);
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(random, "jpg", outputStream);
        return outputStream.toByteArray();
    }
}
