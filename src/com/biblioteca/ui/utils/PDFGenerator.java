package com.biblioteca.ui.utils;

import java.io.File;
import java.io.IOException;

/**
 * Defines a common interface for all pdf generation strategies.
 */
public interface PDFGenerator {
    File generatePdfFile() throws IOException;
}
