package com.biblioteca.core;

import com.biblioteca.ui.utils.BookImage;

import java.util.List;

// 0, Title, Subtitle, Description, [Authors IDs], Year ID, Publisher ID, ISBN, Quantity

/**
 * Base interface for the Book entity. Extends the <code>{@link Comparable}</code> interface to provide sorting capabilities, based on the id of the book.
 * The default strategy for sorting purpose is to use the book's id in ascending order.
 */
public interface Book extends Comparable<Book> {

    String FORMAT_PAPER = "Paper Book";
    String FORMAT_EPUB = "ePub";
    String FORMAT_PDF = "PDF";
    String FORMAT_AUDIOBOOK = "Audiobook";

    // This is an Unmodifiable list.
    List<String> ALL_BOOK_FORMATS = List.of(FORMAT_PAPER, FORMAT_EPUB, FORMAT_AUDIOBOOK, FORMAT_PDF);

    /**
     * Gets the id of this book
     * @return The unique identifier of the Book
     */
    int getId();

    /**
     * Sets a new id. It must be unique
     * @param id A unique identifier for the book
     */
    void setId(int id);

    /**
     *  Returns the book's title
     * @return The book's title
     */
    String getTitle();

    /**
     * Sets the book title
     * @param title The book title
     */
    void setTitle(String title);

    /**
     * If present, return the book subtitle, an empty string otherwise.
     * @return The subtitle
     */
    String getSubtitle();

    /**
     * Sets the book subtitle. It must be short.
     * @param subtitle The book's subtitle.
     */
    void setSubtitle(String subtitle);

    /**
     * Returns description of the book
     * @return The book description, an empty string otherwise.
     */
    String getDescription();

    /**
     *  Sets the book description. It should be short
     * @param description The book description
     */
    void setDescription(String description);

    /**
     * Gets the ISBN code of the book.
     * @return The ISBN code
     */
    String getISBN();

    /**
     * Set the ISBN code of the book
     * @param isbn The exact ISBN code of the book.
     */
    void setISBN(String isbn);

    /**
     * Returns the year of publication of the book
     * @return The book's publication year
     */
    int getYear();

    /**
     * Sets the year of publication for this book
     * @param year The year of publication
     */
    void setYear(int year);

    /**
     * Returns the available quantity of this book
     * @return The stock quantity
     */
    int getQuantity();

    /**
     * Sets how much copies of the book are in stock.
     * @param quantity
     */
    void setQuantity(int quantity);

    /**
     * Returns the image associated with this book.
     * @return The Image representing this book. A default image is returned if no image was specified.
     */
    BookImage getImage();

    /**
     * Sets the image to be displayed for this book
     * @param image The image of the book
     */
    void setImage(BookImage image);

    /**
     * Gets the format of this book. (Paper book, ePub ecc)
     * @return The book format.<br> One of the possible values defined in the Book interface:
     * <br> {@link Book#FORMAT_PAPER }
     * <br> {@link Book#FORMAT_EPUB }
     * <br> {@link Book#FORMAT_AUDIOBOOK }
     * <br> {@link Book#FORMAT_PDF }
     */
    String getFormat();

    /**
     * Sets the book's format . It must be one of the following formats:
     * <br> {@link Book#FORMAT_PAPER }
     * <br> {@link Book#FORMAT_EPUB }
     * <br> {@link Book#FORMAT_AUDIOBOOK }
     * <br> {@link Book#FORMAT_PDF }
     * @param format
     */
    void setFormat(String format);

    /**
     * The publisher of this book
     * @return The book's publisher
     */
    Publisher getPublisher();

    /**
     * Returns the authors of this book
     * @return A list of the book authors.
     */
    List<? extends Author> getAuthors();

    /**
     * Add an author to the list of authors of this book.
     * @param author The author to add
     */
    void addAuthor(Author author);

    /**
     *  Get the list of categories of this book.
     * @return A list of the book's categories.
     */
    List<? extends Category> getCategories();

    /**
     * Add a category to the list of categories of this book.
     * @param categories The category to add
     */
    void addCategories(List<? extends Category> categories);

    /**
     * Sets the book publisher
     * @param publisher
     */
    void setPublisher(Publisher publisher);

    /**
     * Decrement the stock quantity by 1.
     * @param num
     */
    void decrementQuantityBy(int num);

    /**
     * Adds the given quantity as available
     * @param i the quantity to be incremented
     */
    void incrementQuantityBy(int i);
}
