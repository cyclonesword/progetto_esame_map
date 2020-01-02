package com.biblioteca.core.builder;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.Category;
import com.biblioteca.core.Publisher;
import com.biblioteca.ui.utils.BookImage;

import java.util.List;

/**
 * The <code>Book</code> Builder (simplified version). Refer to the {@link Book} javadoc for the getter and setter explanation.
 */
public interface BookBuilder {

    /**
     * Sets the title of the Book
     * @param title
     * @return The BookBuilder instance to allow chaining.
     */
    BookBuilder setTitle(String title);

    /**
     * Sets the book subtitle
     * @param subTitle The book subtitle
     * @return The BookBuilder instance to allow chaining.
     */
    BookBuilder setSubTitle(String subTitle);

    /**
     * Sets the Book's short description.
     * @param description
     * @return The BookBuilder instance to allow chaining.
     */
    BookBuilder setDescription(String description);

    /**
     * Sets the ISBN code of the book
     * @param isbn
     * @return The BookBuilder instance to allow chaining.
     */
    BookBuilder setIsbn(String isbn);

    /**
     * Sets the publisher of the book. It must not be null
     * @param publisher
     * @return The BookBuilder instance to allow chaining.
     */
    BookBuilder setPublisher(Publisher publisher);

    /**
     * Sets the book image, if any.
     * @param image
     * @return The book builder instance to allow chaining.
     */
    BookBuilder setImage(BookImage image);

    /**
     * Sets the list of authors of this book. It must not be null
     * @param authors
     * @return The book builder instance to allow chaining.
     */
    BookBuilder setAuthors(List<? extends Author> authors);

    /**
     * Sets how much copies of this book are available.
     * @param quantity The book quantity
     * @return The book builder instance to allow chaining.
     */
    BookBuilder setQuantity(int quantity);

    /**
     * Sets the book's id
     * @param id A unique identifier for this book
     * @return The book builder instance to allow chaining.
     */
    BookBuilder setId(int id);

    /**
     * Sets the publishing year of this book
     * @param value A valid year value
     * @return The book builder instance to allow chaining.
     */
    BookBuilder setYear(int value);

    /**
     * Sets the list of categories of this book. It must not be null
     * @param categories The list of categories
     * @return The book builder instance to allow chaining.
     */
    BookBuilder setCategories(List<? extends Category> categories);

    /**
     * Sets the book's format . It must be one of the following formats:
     * <br> {@link Book#FORMAT_PAPER }
     * <br> {@link Book#FORMAT_EPUB }
     * <br> {@link Book#FORMAT_AUDIOBOOK }
     * <br> {@link Book#FORMAT_PDF }
     * @param format The chosen format
     */
    BookBuilder setFormat(String format);


    /**
     * Creates a new instance of Book with its default implementation.
     * @return A new Book instance
     */
    Book build() throws BuilderException;

    /**
     * Factory method to get an instance of the default implementation of the BookBuilder interface.
     * @return A newly created BookBuilder instance .
     */
    static BookBuilder getDefault() {
        return new StandardBookBuilder();
    }

    class BuilderException extends RuntimeException {
        public BuilderException() {
        }

        public BuilderException(String message) {
            super(message);
        }
    }

}
