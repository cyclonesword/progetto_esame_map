package com.biblioteca.ui.model;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BookImage extends Image {

    private File file;
    private String location;
    private String name;

    public String getLocation() {
        return location;
    }

    public BookImage setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getName() {
        return name;
    }

    public BookImage setName(String name) {
        this.name = name;
        return this;
    }

    public BookImage(File selectedImage) throws FileNotFoundException {
        this(new FileInputStream(selectedImage), "filesystem", selectedImage.getName());
        this.file = selectedImage;
    }

    public BookImage(String url, String location, String name) {
        super(url);
        this.location = location;
        this.name = name;
    }

    public BookImage(InputStream is,String location, String name) {
        super(is);
        this.location = location;
        this.name = name;
    }

    public File getFile() {
        return file;
    }
}
