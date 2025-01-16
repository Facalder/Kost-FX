package com.kost.utils;

import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import static com.kost.utils.OthersUtils.showAlert;

public class ImageUtils {
    public static boolean validateImageFileSize(File image, long maxFileSize) throws IOException {
        if (image != null && image.length() > maxFileSize) {
            showAlert(Alert.AlertType.ERROR, "File size too large", "The selected file is too large. Maximum size allowed is 3 MB.", "");

            return false;
        }
        return true;
    }

    public static Blob compressImage(File image, float scaleFactor) throws IOException, SQLException {
        BufferedImage originalImage = ImageIO.read(image);

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        int targetWidth = (int) (originalWidth * scaleFactor);
        int targetHeight = (int) (originalHeight * scaleFactor);

        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);

        byte[] imageData = byteArrayOutputStream.toByteArray();
        return new SerialBlob(imageData);
    }
}
