package com.fenixcommunity.centralspace.utilities.common;

import java.io.File;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class SerializableObjToTest implements Serializable {
    private static final long serialVersionUID = 7600996945650740532L;

    private String country;
    private final String city;
    private final File file;

    //to testing
    public void setCountry(String country) {
        this.country = country;
    }
}
