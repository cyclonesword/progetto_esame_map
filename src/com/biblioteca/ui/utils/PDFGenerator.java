package com.biblioteca.ui.utils;

import java.io.File;
import java.io.IOException;

public interface PDFGenerator {
    File generatePdfFile() throws IOException;
}
