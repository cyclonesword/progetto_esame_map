package com.biblioteca.ui.model;

import com.biblioteca.core.Author;
import javafx.scene.image.Image;

public class AuthorFilterItem extends AbstractFilterItem {

   private Author author;

   public AuthorFilterItem(Author author) {
       super(author.getName());
       this.author = author;
   }

    @Override
    public boolean applyTo(ListItem item) {
        System.out.println("Invoked applyTo of Author filter item (author="+author.getName());
        return item.getAuthors().contains(author);
    }
}
