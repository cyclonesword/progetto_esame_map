package com.biblioteca.datasource;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.Category;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serialize Books to a String in CSV format.
 */
class BookConverter implements Converter<Book> {
    @Override
    public String convert(List<? extends Book> entities) {
        return entities.stream()
                .map(book -> {
                    StringBuilder sb = new StringBuilder();

                    var authors = book.getAuthors().stream()
                            .map(Author::getId)
                            .map(String::valueOf)
                            .collect(Collectors.joining(";"));

                    var categories = book.getCategories().stream()
                            .map(Category::getId)
                            .map(String::valueOf)
                            .collect(Collectors.joining(";"));

                    sb.append(book.getId());
                    sb.append(",");
                    sb.append(book.getTitle());
                    sb.append(",");
                    sb.append(book.getSubtitle());
                    sb.append(",");
                    sb.append(book.getDescription());
                    sb.append(",");
                    sb.append("[" + authors + "]");
                    sb.append(",");
                    sb.append(book.getYear());
                    sb.append(",");
                    sb.append(book.getPublisher().getId());
                    sb.append(",");
                    sb.append(book.getISBN());
                    sb.append(",");
                    sb.append(book.getQuantity());
                    sb.append(",");
                    sb.append(book.getImage().getLocation() + "=" + book.getImage().getName());
                    sb.append(",");
                    sb.append("[" + categories + "]");
                    sb.append(",");
                    sb.append(book.getFormat());

                    return sb.toString();
                })
                .collect(Collectors.joining("\n"));
    }
}