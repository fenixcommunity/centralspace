package com.fenixcommunity.centralspace.utilities.resourcehelper;

import static lombok.AccessLevel.PRIVATE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import com.fenixcommunity.centralspace.utilities.common.FileDevTool;
import com.fenixcommunity.centralspace.utilities.globalexception.ResourceLoadingException;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class ExternalResourceLoader {
    final int downloadFileTimeout;

    @Autowired
    public ExternalResourceLoader(@Value("${web.downloadFileTimeout}") final int downloadFileTimeout) {
        this.downloadFileTimeout = downloadFileTimeout;
    }

    public File downloadFileFromUrl(final String downloadUrl, final String destinationFilePath) {
        final File outputFile = FileDevTool.createNewOutputFile(destinationFilePath);
        if (outputFile == null) {
            return null;
        }

        URL url;
        try {
            url = new URL(downloadUrl);
        } catch (MalformedURLException e) {
            log.warn(e.getMessage());
            return null;
        }

        try {
            FileUtils.copyURLToFile(
                    url,
                    outputFile,
                    downloadFileTimeout,
                    downloadFileTimeout);
        } catch (IOException e) {
            log.warn(e.getMessage());
            return null;
        }

        return outputFile;
    }


    public File downloadFileWithResume(final String downloadUrl, final String savedFilePath) {
        final File outputFile = new File(savedFilePath);

        try {
            final URLConnection downloadFileConnection = addFileResumeConnection(downloadUrl, outputFile);
            transferDataAndGetBytesDownloaded(downloadFileConnection, outputFile);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }

        return outputFile;
    }

    private URLConnection addFileResumeConnection(final String downloadUrl, final File outputFile) throws URISyntaxException, IOException {
        final URLConnection downloadFileConnection = new URI(downloadUrl).toURL().openConnection();
        long existingFileSize = 0L;
        if (outputFile.exists() && downloadFileConnection instanceof HttpURLConnection) {
            final HttpURLConnection httpFileConnection = (HttpURLConnection) downloadFileConnection;

            final HttpURLConnection tmpFileConn = (HttpURLConnection) new URI(downloadUrl).toURL().openConnection();
            tmpFileConn.setRequestMethod("HEAD");
            final long fileLength = tmpFileConn.getContentLengthLong();
            existingFileSize = outputFile.length();

            if (existingFileSize < fileLength) {
                httpFileConnection.setRequestProperty("Range", "bytes=" + existingFileSize + "-" + fileLength);
            } else {
                throw new ResourceLoadingException("File Download already completed.");
            }
        }

        return downloadFileConnection;
    }

    private void transferDataAndGetBytesDownloaded(final URLConnection downloadFileConnection, final File outputFile) throws IOException {
        long bytesDownloaded = 0;
        try (final InputStream is = downloadFileConnection.getInputStream();
             final OutputStream os = new FileOutputStream(outputFile, true)) {

            final byte[] buffer = new byte[1024];

            int bytesCount;
            while ((bytesCount = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesCount);
                bytesDownloaded += bytesCount;
            }
        }
    }

}
