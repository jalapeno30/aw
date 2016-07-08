/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: StringUtils.java
 */
package com.ilts.anywhere.logging.utils;

import com.ilts.anywhere.logging.log.CustomLogger.LogType;

import org.w3c.dom.Node;

/**
 *
 * @author jnguyen
 */
public class StringUtils {
    /**
     * Function - isNullOrEmpty
     * @param checkObj
     * @return status of string whether null or empty (true, false)
     */
    public static Boolean isNullOrEmpty(String checkObj) {
        Boolean bIsNullOrEmpty = true;

        if ((checkObj != null) && (checkObj.isEmpty() == false)) {
            bIsNullOrEmpty = false;
        }

        return bIsNullOrEmpty;
    }

    /**
     * Function - isNumber
     * @param numbStr
     * @return status verification whether the input string is a Number (true, false)
     */
    public static Boolean isNumber(String numbStr) {
        String regex = "^\\d+$";

        return numbStr.matches(regex);
    }

    /**
     * Function - isFloatNumber
     * @param numbStr
     * @return status verification whether the input string is a Number (true, false)
     */
    public static Boolean isFloatNumber(String numbStr) {
        String regex = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";    // (-|\\+)?[0-9]+(\\.[0-9]{1,2}+)?

        return numbStr.matches(regex);
    }

    /**
     * Function - getSubstring
     * @param wholeStr
     * @param delimeter
     * @param index
     * @return status verification whether the input string is a Number (true, false)
     */
    public static String getSubstring(String wholeStr, String delimiter, int index) {
        String result = "";

        String[] tokens = wholeStr.split(delimiter);

        if ((tokens != null) && ((tokens.length > 0) && (tokens.length < 3))) {
            if (index == 1) {
                result = tokens[0];
            } else if (tokens.length == 2) {
                result = tokens[1];
            }
        }

        return result;
    }

    /**
     * Function - stripExtension
     * @param fileName
     * @return file name without extension
     */
    public static String stripExtension(String fileName) {
        // Handle null case specially.
        if (fileName == null) return null;

        // Get position of last '.'.
        int pos = fileName.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return fileName;

        // Otherwise return the string, up to the dot.
        return fileName.substring(0, pos);
    }

    /**
     * Function - getExtension
     * @param fileName
     * @return file extension
     */
    public static String getExtension(String fileName) {
        // Handle null case specially.
        if (fileName == null) return null;

        // Get position of last '.'.
        int pos = fileName.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.
        if (pos == -1) return fileName;

        // Otherwise return the string, from the character after the dot to end of string.
        return fileName.substring(pos + 1, fileName.length());
    }

    /**
     * Function - extractXMLElement
     * @param elementTagName
     * @param data
     * @return element value in String
     */
    public static String extractXMLElement(String elementTagName, String data) {
        String elementValue = "";
        String trimmedData = data.trim().replace(System.lineSeparator(), "");

        if (StringUtils.isNullOrEmpty(elementTagName) == false) {
            int pos1, pos2, offSetPos1;

            offSetPos1 = elementTagName.length() + 2;
            pos1 = trimmedData.indexOf("<" + elementTagName);
            pos2 = trimmedData.indexOf("</" + elementTagName + ">", pos1);

            elementValue = trimmedData.substring(pos1 + offSetPos1, pos2);
        }

        return elementValue;
    }
}