package com.biblioteca.ui.start;

/**
 * This class is necessary to make the JAR executable.<br>
 * To launch the application, open a terminal in the same directory of the Jar file and type:<br>
 *     <pre>
 * java -jar [nomejar].jar
 * </pre>
 * substituting [nomejar] with the jar name.
 */
public class Launcher {

    public static void main(String[] args) {
        ApplicationStart.begin(args);
    }
}