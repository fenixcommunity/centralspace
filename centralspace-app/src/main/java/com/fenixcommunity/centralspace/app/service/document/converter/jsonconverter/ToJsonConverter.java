package com.fenixcommunity.centralspace.app.service.document.converter.jsonconverter;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fenixcommunity.centralspace.app.globalexception.DocumentServiceException;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResourceLoader;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;

@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class ToJsonConverter<T> {
    private final InternalResourceLoader resourceTool;

    public File toJsonFrom(final Resource resource,
                           final FileFormat from,
                           final Class<T> jsonClass) throws IOException {
        if (!resource.isFile()) {
            return null;
        }

        if (FileFormat.CSV == from && jsonClass != null) {
            final MappingIterator<T> orderLines = prepareOrderLines(resource.getFile(), jsonClass);

            final File jsonOutputFile = JsonConverterHelper.createJsonOutputFile(resource.getFilename(),
                    resourceTool.getResourceProperties().getConvertedJsonPath());
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)
                    .writeValue(jsonOutputFile, orderLines.readAll());

            return jsonOutputFile;
        }

        throw new DocumentServiceException("Unsupported conversion format");
    }

    private MappingIterator<T> prepareOrderLines(final File csvFile, final Class<T> jsonObjectRepresentation) throws IOException {
        final CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
        final CsvMapper csvMapper = new CsvMapper();
        return csvMapper.readerFor(jsonObjectRepresentation)
                .with(orderLineSchema)
                .readValues(csvFile);
    }
}
