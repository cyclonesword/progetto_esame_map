package com.biblioteca.ui.utils;

import com.biblioteca.core.Loan;
import com.biblioteca.datasource.DataSource;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * The default Loan PDF generator that uses the external Library "itextpdf" to build the PDF file.
 */
public class LoanPDFGenerator implements PDFGenerator {

    private Loan loan;

    public LoanPDFGenerator(Loan loan) {
        this.loan = loan;
    }

    @Override
    public File generatePdfFile() throws IOException {
        String path = DataSource.getInstance().getApplicationFilesRootPath() + "pdf";
        Path target = Paths.get(path);

        if (Files.notExists(target))
            Files.createDirectory(target);

        File file = new File(path + File.separator + loan.getLoanId() + ".pdf");

        if (file.exists())
            return file;

        Document document = new Document();

        Font helveticaBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Font helveticaNormal = FontFactory.getFont(FontFactory.HELVETICA, 15, BaseColor.BLACK);
        Font helveticaNormalSmall = FontFactory.getFont(FontFactory.HELVETICA, 13, BaseColor.DARK_GRAY);

        Paragraph title = new Paragraph("Riepilogo prestito", helveticaBold);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(new float[]{25, 125, 200, 90, 90});
        table.setTotalWidth(530);
        table.setLockedWidth(true);

        // INSERIRE DATE FORMATTATE NEL FORMATO ITALIANO

        var headers = List.of(new Phrase("#Id", helveticaNormal),
                new Phrase("Tesserato", helveticaNormal),
                new Phrase("Libro", helveticaNormal),
                new Phrase("Data Inizio", helveticaNormal),
                new Phrase("Data Fine", helveticaNormal));


        headers.forEach(table::addCell);

        table.addCell(new Phrase(String.valueOf(loan.getLoanId()), helveticaNormalSmall));
        table.addCell(new Phrase(loan.getCustomer().getFullName(), helveticaNormalSmall));
        table.addCell(new Phrase("#" + loan.getBook().getId() + "-" + loan.getBook().getTitle(), helveticaNormalSmall));
        table.addCell(new Phrase(loan.getLoanDate().toString(), helveticaNormalSmall));
        table.addCell(new Phrase(loan.getExpectedReturnDate().toString(), helveticaNormalSmall));

        Paragraph data = new Paragraph("Data", helveticaBold);
        data.setAlignment(Paragraph.ALIGN_LEFT);

        var dataValue = new Paragraph(LocalDate.now().toString(), helveticaNormal);
        dataValue.setAlignment(Element.ALIGN_LEFT);

        Paragraph signature = new Paragraph("Firma del tesserato", helveticaBold);
        signature.setAlignment(Paragraph.ALIGN_RIGHT);

        var underscores = new Paragraph("_____________________", helveticaNormal);
        underscores.setAlignment(Element.ALIGN_RIGHT);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(title);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(data);
            document.add(signature);
            document.add(dataValue);
            document.add(underscores);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.close();
        return file;
    }
}
