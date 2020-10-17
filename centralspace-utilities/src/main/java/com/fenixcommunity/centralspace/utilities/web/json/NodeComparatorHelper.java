package com.fenixcommunity.centralspace.utilities.web.json;

import java.util.Comparator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class NodeComparatorHelper implements Comparator<JsonNode> {

    @Override
    public int compare(final JsonNode o1, final JsonNode o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        if ((o1 instanceof NumericNode) && (o2 instanceof NumericNode)) {
            final Double d1 = o1.asDouble();
            final  Double d2 = o2.asDouble();
            if (d1.compareTo(d2) == 0) {
                return 0;
            }
        } else if ((o1 instanceof TextNode) && (o2 instanceof TextNode)) {
            final String s1 = o1.asText();
            final String s2 = o2.asText();
            if (s1.equalsIgnoreCase(s2)) {
                return 0;
            }
        }

        return 1;
    }
}