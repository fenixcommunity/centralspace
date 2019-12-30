package com.fenixcommunity.centralspace.app.service.document.pdfcreator;

import com.fenixcommunity.centralspace.app.exception.DocumentServiceException;
import com.fenixcommunity.centralspace.utilities.document.PdfDocumentComposer;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import static com.fenixcommunity.centralspace.utilities.common.DevTool.createFileDirectories;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.JPG;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.PDF;
import static com.fenixcommunity.centralspace.utilities.common.FileFormat.PNG;
import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;
import static com.fenixcommunity.centralspace.utilities.common.Var.MESSAGE;
import static com.fenixcommunity.centralspace.utilities.common.Var.PASSWORD;
import static com.fenixcommunity.centralspace.utilities.common.Var.UNDERSCORE;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

public class ITextPdfCreator implements IPdfCreator {

    private static final String imageName = "image_to_pdf";
    private static final String imageLogoName = "it_logo";
    private static final String ENCRYPTED_SURFIX = "encrypted";

    private String fileName;
    private final ResourceLoaderTool resourceTool;

    public ITextPdfCreator(String fileName, ResourceLoaderTool resourceTool) {
        this.fileName = fileName;
        this.resourceTool = resourceTool;
    }

    @Override
    public void createPdf() {
        String outputPdfPath = createOutputPdfFile();
        Document document = PdfDocumentComposer.composeNewA4Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPdfPath));
            document.open();

            var font = PdfDocumentComposer.composeFont();
            document = PdfDocumentComposer.composeChunk(MESSAGE, document, font);
            document.add(Chunk.NEWLINE);

            PdfPTable table = PdfDocumentComposer.composeTable(5);
            table = PdfDocumentComposer.composeTableHeader(table);
            Image imageLogo = Image.getInstance(readFileToByteArray(getImageLogoFile()));
            table = PdfDocumentComposer.composeRowsWithLogo(table, imageLogo);
            document.add(table);

            document.add(Chunk.NEWLINE);

            Image image = Image.getInstance(readFileToByteArray(getImageFile()));
            document = PdfDocumentComposer.composeImage(document, Collections.singletonList(image));
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
            String encryptedPdfPath = dest.replace(fileName, fileName + UNDERSCORE + ENCRYPTED_SURFIX);
            pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(encryptedPdfPath));
            //todo PASSWORD from security.properties
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
        var outputPdfPath = resourceTool.getResourceProperties().getConvertedPdfPath()
                + fileName + DOT + PDF.getSubtype();
        createFileDirectories(outputPdfPath);
        return outputPdfPath;
    }

    private File getImageFile() throws IOException {
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(imageName, PNG));
        return resource.getFile();
    }

    private File getImageLogoFile() throws IOException {
        var resource = resourceTool.loadResourceFile(InternalResource.resourceByNameAndType(imageLogoName, JPG));
        return resource.getFile();
    }

}