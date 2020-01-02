package com.biblioteca.datasource;

import java.util.List;

/**
 * A standard way to serialize the objects in memory in a well-structured CSV file format.<br>
 *  For example, this code fragment creates a lambda for converting Author objects to a String CSV representation:<br><br>
              <code><pre>
         authors -> authors.stream()
             .map(author -> author.getId() + "," + author.getName())
              .collect(Collectors.joining("\n"));
              </pre></code>
 * @param <T> The data type of the objects to be serialized
 */
@FunctionalInterface
public interface Converter<T> {

    /**
     * Serialize the given entities to its CSV String representation.
     * @param entities The entities to be serialized
     * @return a String representing the serialized entities in CSV format.
     */
    String convert(List<? extends T> entities);
}
