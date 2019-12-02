package tools;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

public class ImageKit {

    public static Image get(String code) {
        return new Image(ImageKit.class.getResourceAsStream("/images/" + code + ".png"));
    }

    private static final String CHARSET = "utf-8";

    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 400;

    public static Image create(String content) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if(bitMatrix == null) return null;
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        WritableImage image = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.getPixelWriter().setArgb(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    public static String parse(BufferedImage image) {
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = null;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        try {
            result = new MultiFormatReader().decode(bitmap, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return result == null ? "" : result.getText();
    }

    public static String parse(Image image) {
        BufferedImage bf = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        PixelReader pr = image.getPixelReader();
        for (int x = 0; x < bf.getWidth(); x++) {
            for (int y = 0; y < bf.getHeight(); y++) {
                bf.setRGB(x, y, pr.getArgb(x, y));
            }
        }
        return parse(bf);
    }

}
