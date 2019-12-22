package com.biblioteca.ui.items;

import com.biblioteca.core.Author;
import com.biblioteca.core.Category;
import com.biblioteca.core.Publisher;
import javafx.scene.image.Image;

import java.util.List;

/**
 * This interface is used to display items of type Book or Magazine.
 * in the ListView of the MainWindow window.
 */
public interface ListItem {

    /**
     *
     * @return The index position of the item (starting from 0)
     */
    int getPosition();

    /**
     *
     * @return true if it is selected, false otherwise
     */
    boolean isSelected();

    /**
     *
     * @return The title of the item
     */
    String getItemTitle();

    /**
     *
     * @return The description of the item
     */
    String getItemDescription();

    /**
     *
     * @return The item's image
     */
    Image getImage();

    /**
     * Get the available quantity of this item.
     * @return the item's quantity
     */
    int getQuantity();

    /**
     * Returns true if there are available items of this type.
     *
     * @return true if the quantity is > 0
     */
    boolean isAvailable();

    /**
     *
     * @return The author's of the item
     */
    List<? extends Author> getAuthors();

    void setPosition(int index);

    /**
     *
     * @return The publisher of this item.
     */
    Publisher getPublisher();

    /**
     *
     * @return The categories of this items.
     */
    List<? extends Category> getCategories();

    /**
     * The format type of this item. (Paper Book, Audiobook etc) <br>
     *     See {@link com.biblioteca.core.Book} for all available formats.
     * @return
     */
    String getFormat();
}
