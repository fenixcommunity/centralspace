package com.fenixcommunity.centralspace.app.service.document.converter;

import com.fenixcommunity.centralspace.app.exception.DocumentServiceException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

import java.util.List;

import static com.fenixcommunity.centralspace.utilities.common.Var.MESSAGE;
import static com.fenixcommunity.centralspace.utilities.common.Var.SUBJECT;

public class DocumentComposer {

    public static Document composeDocument(Document document) throws DocumentException {
        document.newPage();
        Paragraph paragraph1 = new Paragraph("This is composeDocument");
        document.add(paragraph1);
        document.setPageCount(10);
        document.setMargins(5, 5, 10, 10);
        document.addTitle(MESSAGE);
        document.addSubject(SUBJECT);
        document.newPage();
        return document;
    }

    public static Document composeImage(Document document, List<Image> images) {
        float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
        images.forEach(i -> i.scaleToFit(documentWidth / 2, documentHeight / 2));
        images.forEach(i -> {
            try {
                document.add(i);
            } catch (DocumentException e) {
                throw new DocumentServiceException(e.getMessage(), e);
            }
        });
        return document;
    }

    public static Font composeFont() {
        var font = new Font();
        font.setStyle(Font.BOLD);
        font.setSize(15);
        return font;
    }

}
