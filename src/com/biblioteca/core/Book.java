package com.biblioteca.core;

import javafx.scene.image.Image;

import java.util.List;

public interface Book {

    // 0, Title, Subtitle, Description, [Authors IDs], Year ID, Publisher ID, ISBN, Quantity

    int getId();

    String getTitle();

    String getSubtitle();

    String getDescription();

    String getISBN();

    int getYear();

    int getQuantity();

    Image getImage();

    Publisher getPublisher();

    List<? extends Author> getAuthors();

    void addAuthor(Author author);

    class Builder {
        private int id;
        private String title;
        private String subTitle;
        private String description;
        private String isbn;
        private Publisher publisher;
        private Image image;
        private List<? extends Author> authors;
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

        public Builder setImage(Image image) {
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

        public Book build() {
            var book = new BookImpl(title, isbn, description, quantity);

            book.setId(id);
            book.setSubTitle(subTitle);
            book.setPublisher(publisher);
            book.setImage(image);
            book.setYear(year);
            authors.forEach(book::addAuthor);

            return book;
        }
    }
}
