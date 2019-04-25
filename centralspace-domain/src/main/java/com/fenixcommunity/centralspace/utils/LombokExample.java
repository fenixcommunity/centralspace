package com.fenixcommunity.centralspace.utils;

import lombok.Cleanup;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Log
public class LombokExample {
    public void exampleService() throws IOException {
//    InputStream is = new FileInputStream(new File("/Accounts/Khanh/text.txt"));
////        if(Collections.singletonList(is).get(0) != null) {
////        is.close();
        //zastąpić
        @Cleanup(value = "close") InputStream is = new FileInputStream(new File("/Accounts/Example/text.txt"));
        log.info("exampleService finished");
    }
}
