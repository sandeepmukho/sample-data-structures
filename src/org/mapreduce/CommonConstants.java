package org.mapreduce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface CommonConstants {
        // Constants for separator , delimiters etc
        public static final String TAB = "\t";
        public static final String NEW_LINE = "\n";
        public static final String COMMA = ",";
        public static final String COLON = ":";
        public static final String EMPTY_STRING = "";
        public static final String PIPE = "\\|";
        public static final String UNDERSCORE = "_";
        
        public static final String DELIMITER = "DELIMITER";
        public static final String SEPARATOR = "SEPARATOR";

        // Constants used in code for filters, formatting etc
        public static final String DATE_FORMAT = "yyyy-MM-dd";
        public static final String MINUS_ONE = "-1";
        public static final DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);


}
