package com.fenixcommunity.centralspace.utilities.logger;

import static lombok.AccessLevel.PUBLIC;

import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@FieldDefaults(level = PUBLIC, makeFinal = true)
public class MarkersVar {
    public static final Marker ADMIN = MarkerManager.getMarker("ADMIN");
    public static final Marker GENERAL_USER = MarkerManager.getMarker("GENERAL");
//  todo   inside xml congif  <MarkerFilter marker="ADMIN" onMatch="ACCEPT"
//    onMismatch="DENY" />

}