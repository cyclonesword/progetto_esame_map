package com.biblioteca.ui;

import com.biblioteca.ui.model.BookImage;
import javafx.scene.image.Image;

/**
 * Utility class to get static default images.
 */
public class Images {

    private static Images instance  = new Images();

    public static final Image GREEN_CIRCLE = new Image(Images.class.getResourceAsStream("/images/green_circle.png"));
    public static final Image RED_CIRCLE = new Image(Images.class.getResourceAsStream("/images/red_circle.png"));
    public static final Image ORANGE_CIRCLE = new Image(Images.class.getResourceAsStream("/images/orange_circle.png"));

    //It is necessary to use an instance of this class because the getResourceAsStream method does not work in a static context.
    public static final BookImage BOOK_DEFAULT = new BookImage(getInstance().getClass().getResourceAsStream("/images/book_default.png"), "local", "book_default.png");

    private static Images getInstance() {
        return instance;
    }
}
