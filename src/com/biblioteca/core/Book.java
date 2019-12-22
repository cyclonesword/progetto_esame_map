package com.biblioteca.core;

import com.biblioteca.ui.utils.BookImage;

import java.util.List;

// 0, Title, Subtitle, Description, [Authors IDs], Year ID, Publisher ID, ISBN, Quantity

/**
 * Base interface for the Book entity.
 */
public interface Book {

    String FORMAT_PAPER = "Paper Book";
    String FORMAT_EPUB = "ePub";
    String FORMAT_PDF = "PDF";
    String FORMAT_AUDIOBOOK = "Audiobook";

    // This is an Unmodifiable list.
    List<String> ALL_BOOK_FORMATS = List.of(FORMAT_PAPER, FORMAT_EPUB, FORMAT_AUDIOBOOK, FORMAT_PDF);

    /**
     *
     * @return The unique identifier of the Book
     */
    int getId();

    /**
     * Sets a new id
     * @param id A unique identifier for the book
     */
    void setId(int id);

    /**
     *
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
     * @return
     */
    String getSubtitle();

    /**
     * Sets the book subtitle. It must be short.
     * @param subtitle The book's subtitle.
     */
    void setSubtitle(String subtitle);

    /**
     * @return The book description, an empty string otherwise.
     */
    String getDescription();

    /**
     *
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
     *
     * @return The book's publication year
     */
    int getYear();

    /**
     * @param year The year of publication
     */
    void setYear(int year);

    /**
     * @return The stock quantity
     */
    int getQuantity();

    /**
     * Sets how much copies of the book are in stock.
     * @param quantity
     */
    void setQuantity(int quantity);

    /**
     * @return The Image representing this book. A default image is returned if no image was specified.
     */
    BookImage getImage();

    /**
     *
     * @param image The image of the book
     */
    void setImage(BookImage image);

    /**
     *
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
     * @return The book's publisher
     */
    Publisher getPublisher();

    /**
     *
     * @return A list of the book authors.
     */
    List<? extends Author> getAuthors();

    /**
     * Add an author to the list of authors of this book.
     * @param author The author to add
     */
    void addAuthor(Author author);

    /**
     *
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
     */
    void decrementQuantity();


    /** **
     * The <code>Book</code> Builder. Refer to the {@link Book} javadoc for the getter and setter explanation.
     */
    class Builder {
        private int id;
        private String title;
        private String subTitle;
        private String description;
        private String isbn;
        private String format;
        private Publisher publisher;
        private BookImage image;
        private List<? extends Author> authors;
        private List<? extends Category> categories;
        private int quantity;
        private int year;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder setPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder setImage(BookImage image) {
            this.image = image;
            return this;
        }

        public Builder setAuthors(List<? extends Author> authors) {
            this.authors = authors;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setYear(int value) {
            this.year = value;
            return this;
        }

        public Builder setCategories(List<? extends Category> categories) {
            this.categories = categories;
            return this;
        }
        public Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        /**
         *
         * @return A new instance of the Book interface using the default {@link BookImpl} implementation.
         */
        public Book build() {
            var book = new BookImpl();

            book.setTitle(title);
            book.setISBN(isbn);
            book.setDescription(description);
            book.setQuantity(quantity);
            book.setId(id);
            book.setSubtitle(subTitle);
            book.setPublisher(publisher);
            book.setImage(image);
            book.setYear(year);
            book.setFormat(format);
            book.addCategories(categories);
            authors.forEach(book::addAuthor);

            return book;
        }
    }

}
