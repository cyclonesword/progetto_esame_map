package com.biblioteca.core;

import com.biblioteca.ui.model.ListItem;

public interface Filter {
 //   enum Type { AUTHOR, PUBLISHER, TAG }

    String getName();
    String getValue();
   // Type getType();
    boolean apply(ListItem item);
}
