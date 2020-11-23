package com.fenixcommunity.centralspace.app.service.document.pdfcreator;

import static com.fenixcommunity.centralspace.utilities.common.FileDevTool.createFileDirectories;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.JPG;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.PDF;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.PNG;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.MESSAGE;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static com.fenixcommunity.centralspace.utilities.common.Var.UNDERSCORE;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import com.fenixcommunity.centralspace.app.globalexception.DocumentServiceException;
import com.fenixcommunity.centralspace.utilities.document.PdfDocumentComposer;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResourceLoader;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ITextPdfCreator implements IPdfCreator {

    private static final String imageName = "image_to_pdf";
    private static final String imageLogoName = "it_logo";
    private static final String ENCRYPTED_SURFIX = "encrypted";

    private final String fileName;
    private final InternalResourceLoader resourceTool;

    @Override
    public void createPdf() {
        final String outputPdfPath = createOutputPdfFile();
        final Document document = PdfDocumentComposer.composeNewA4Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPdfPath));
            document.open();

            final var font = PdfDocumentComposer.composeFont();
            PdfDocumentComposer.composeChunk(MESSAGE, document, font);
            document.add(Chunk.NEWLINE);

            final PdfPTable table = PdfDocumentComposer.composeTable(5);
            PdfDocumentComposer.composeTableHeader(table);
            final Image imageLogo = Image.getInstance(readFileToByteArray(getImageLogoFile()));
            PdfDocumentComposer.composeRowsWithLogo(table, imageLogo);
            document.add(table);

            document.add(Chunk.NEWLINE);

            final Image image = Image.getInstance(readFileToByteArray(getImageFile()));
            PdfDocumentComposer.composeImage(document, Collections.singletonList(image));
        } catch (IOException | DocumentException e) {
            //todo log.error
            throw new DocumentServiceException(e.getMessage(), e);
        } finally {
            document.close();
        }

        encryptCreatedPdf(outputPdfPath);
    }

    private void encryptCreatedPdf(String dest) throws DocumentServiceException {
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        try {
            pdfReader = new PdfReader(dest);
            final String encryptedPdfPath = dest.replace(fileName, fileName + UNDERSCORE + ENCRYPTED_SURFIX);
            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(encryptedPdfPath));
            //todo PASSWORD from autosecurity.properties
            pdfStamper.setEncryption(PASSWORD.getBytes(), PASSWORD.getBytes(),
                    PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
        } catch (IOException | DocumentException e) {
            throw new DocumentServiceException(e.getMessage(), e);
        } finally {
            if (pdfStamper != null) {
                try {
                    pdfStamper.close();
                } catch (IOException | DocumentException e) {
                    //ignore
                }
            }
            if (pdfReader != null) pdfReader.close();
        }
    }

    private String createOutputPdfFile() {
        final var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + PDF.getSubtype();
        createFileDirectories(outputPdfPath);
        return outputPdfPath;
    }

    private File getImageFile() throws IOException {
        final var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(imageName, PNG));
        return resource.getFile();
    }

    private File getImageLogoFile() throws IOException {
        final var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(imageLogoName, JPG));
        return resource.getFile();
    }

}