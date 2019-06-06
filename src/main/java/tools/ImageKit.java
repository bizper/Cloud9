package tools;

import javafx.scene.image.Image;

public class ImageKit {

    public static Image get(String code) {
        return new Image(ImageKit.class.getResourceAsStream("/images/" + code + ".png"));
    }

}
