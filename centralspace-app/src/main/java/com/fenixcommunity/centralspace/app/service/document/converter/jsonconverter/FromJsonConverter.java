package com.fenixcommunity.centralspace.app.service.document.converter.jsonconverter;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fenixcommunity.centralspace.app.globalexception.DocumentServiceException;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;
import com.fenixcommunity.centralspace.utilities.resourcehelper.ResourceLoaderTool;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;

@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class FromJsonConverter {
    private final ResourceLoaderTool resourceTool;

    public File fromJsonTo(final Resource resource, final FileFormat to) throws IOException {
        if (!resource.isFile()) {
            return null;
        }

        if (FileFormat.CSV == to) {
            final JsonNode jsonTree = new ObjectMapper().readTree(resource.getFile());
            final CsvSchema csvSchema = createCsvSchemaWithHeader(jsonTree);

            final File csvOutputFile = JsonConverterHelper.createCsvOutputFile(resource.getFilename(),
                    resourceTool.getResourceProperties().getConvertedCsvPath());
            final CsvMapper csvMapper = new CsvMapper();
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(csvOutputFile, jsonTree);

            return csvOutputFile;
        }

        throw new DocumentServiceException("Unsupported conversion format");
    }

    private CsvSchema createCsvSchemaWithHeader(final JsonNode jsonTree) {
        final CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        final JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
        return csvSchemaBuilder.build().withHeader();
    }

}
