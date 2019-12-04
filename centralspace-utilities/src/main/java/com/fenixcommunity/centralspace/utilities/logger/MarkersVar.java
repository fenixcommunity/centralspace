package com.fenixcommunity.centralspace.utilities.logger;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MarkersVar {
    public static final Marker ADMIN_USER = MarkerManager.getMarker("ADMIN");
    public static final Marker GENERAL_USER = MarkerManager.getMarker("GENERAL");
//  todo   inside xml congif  <MarkerFilter marker="ADMIN" onMatch="ACCEPT"
//    onMismatch="DENY" />

}