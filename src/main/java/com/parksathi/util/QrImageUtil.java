package com.parksathi.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QrImageUtil {

    public static String generateBase64Qr(String text) {
        try {
            QRCodeWriter writer = new QRCodeWriter();

            var bitMatrix = writer.encode(
                    text,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );

            BufferedImage image = new BufferedImage(
                    300,
                    300,
                    BufferedImage.TYPE_INT_RGB
            );

            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    image.setRGB(
                            x,
                            y,
                            bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF
                    );
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", outputStream);

            String base64 = Base64.getEncoder()
                    .encodeToString(outputStream.toByteArray());

            return "data:image/png;base64," + base64;

        } catch (Exception e) {
            throw new RuntimeException("QR image generation failed");
        }
    }
}
