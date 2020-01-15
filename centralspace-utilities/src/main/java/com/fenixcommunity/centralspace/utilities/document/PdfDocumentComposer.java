package com.fenixcommunity.centralspace.utilities.document;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Stream;

import static com.fenixcommunity.centralspace.utilities.common.Var.MESSAGE;
import static com.fenixcommunity.centralspace.utilities.common.Var.SUBJECT;
import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PdfDocumentComposer {

    public static Document composeNewA4Document() {
        return new Document(PageSize.A4, 20, 20, 20, 20);
    }

    public static Document composeDocument(final Document document) throws DocumentException {
        document.newPage();
        final Paragraph paragraph1 = new Paragraph("This is composeDocument");
        document.add(paragraph1);
        document.setPageCount(10);
        document.setMargins(5, 5, 10, 10);
        document.addTitle(MESSAGE);
        document.addSubject(SUBJECT);
        document.newPage();
        return document;
    }

    public static Document composeImage(final Document document, final List<Image> images) {
        float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
        images.forEach(i -> i.scaleToFit(documentWidth / 2, documentHeight / 2));
        images.forEach(i -> {
            try {
                document.add(i);
            } catch (DocumentException e) {
                log.error("composeImage error", e);
            }
        });
        return document;
    }

    public static Font composeFont() {
        final var font = new Font();
        font.setStyle(Font.BOLD);
        font.setSize(15);
        return font;
    }

    public static Document composeChunk(final String message, final Document document, final Font font) throws DocumentException {
        var chunk = new Chunk(message, font);
        document.add(chunk);
        return document;
    }

    public static PdfPTable composeTable(int columnsNo) {
        return new PdfPTable(columnsNo);
    }

    public static PdfPTable composeTableHeader(final PdfPTable table) {
        Stream.of("column header 1", "column header 2", "column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
        return table;
    }

    public static PdfPTable composeRowsWithLogo(final PdfPTable table, final Image imageLogo) {
        imageLogo.scalePercent(10);
        final PdfPCell imageCell = new PdfPCell(imageLogo);
        table.addCell(imageCell);

        final PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        final PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);

        return table;
    }


}
