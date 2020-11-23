package com.fenixcommunity.centralspace.app.service.document.converter.jsonconverter;

import static com.fenixcommunity.centralspace.utilities.common.Var.DOT;

import java.io.File;

import com.fenixcommunity.centralspace.utilities.common.FileDevTool;
import com.fenixcommunity.centralspace.utilities.common.FileFormat;

public class JsonConverterHelper {
    public static File createCsvOutputFile(String fileName, String basicPath) {
        return FileDevTool.createNewOutputFile(basicPath + fileName + DOT + FileFormat.CSV.getSubtype());
    }

    public static File createJsonOutputFile(String fileName, String basicPath) {
        return FileDevTool.createNewOutputFile(basicPath + fileName + DOT + FileFormat.JSON.getSubtype());
    }
}
