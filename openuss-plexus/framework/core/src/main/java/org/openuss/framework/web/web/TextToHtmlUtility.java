/**
 * Title:        OpenUSS - Open Source University Support System
 * Description:  Utility for OpenUSS
 * Copyright:    Copyright (c) B. Lofi Dewanto
 * Company:      University of Muenster
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
package org.openuss.framework.web.utility;

import java.util.Vector;

import org.enhydra.xml.xmlc.html.HTMLObjectImpl;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLAnchorElement;
import org.w3c.dom.html.HTMLElement;


/**
 * Utility for classes.
 *
 * @author  B. Lofi Dewanto
 * @version 1.0
 */
public class TextToHtmlUtility {
    /**
     * Format a normal text to html.
     * - Cut a long sentence in pieces
     * - Change all the \n in <br>
     * - Change all the empty space in " " and &nbsp;
     * - Check for http://
     * Return an element.
     */
    public static Element formatTextToHtml(String inputText, 
                                           HTMLObjectImpl inputPage, int width) {
        // Just cut a long sentence in pieces
        // and format them to fit the given width
        // We ignore http:// first... to get the link                                            
        inputText = formatTextToWidthIgnoreHttp(inputText, width);

        // Set the message
        // Change all the \n in <br>
        // Change all the empty space in " "
        String stringResult = new String();
        String linkResult = new String();
        StringBuffer sb = new StringBuffer(inputText);
        int index = 0;

        Element topElement = inputPage.createElement("span");

        // Go through the characters
        while (index < sb.length()) {
            stringResult = stringResult + sb.charAt(index);

            // --- Check for \n new line ---
            if (sb.charAt(index) == '\n') {
                createElementNewLine(stringResult, inputPage, topElement);
                stringResult = "";
            }

            // --- Check for space ---
            if (sb.charAt(index) == ' ') {
                index = createElementSpace(index, sb, stringResult, inputPage, 
                                           topElement);
                stringResult = "";
            }

            // --- Check for http:// ---
            if (isTextHttp(sb, index)) {
                // Yep, the next is a http://
                // We have to create all the chars before,
                // and delete the last one...
                String strToAdd = stringResult.substring(0, 
                                                         stringResult.length() - 1);
                topElement.appendChild(inputPage.createTextNode(strToAdd));

                // Go forward with the http...
                Vector result = getTextHttp(sb, index);
                Integer indexInteger = (Integer) result.get(0);
                index = indexInteger.intValue();
                stringResult = (String) result.get(1);


                // The link must be complete, but the string text should
                // be formatted with the width first...
                linkResult = (String) result.get(1);
                stringResult = formatTextToWidth(stringResult, width);

                createElementHttp(stringResult, linkResult, inputPage, 
                                  topElement);
                stringResult = "";
                linkResult = "";
            }


            // Increase the resultIndex
            index++;
        }


        // Add the last string
        topElement.appendChild(inputPage.createTextNode(stringResult));

        return topElement;
    }

    /**
     * Create a new line element.
     */
    private static void createElementNewLine(String strInput, 
                                             HTMLObjectImpl inputPage, 
                                             Element topElement) {
        // Delete the last empty space
        strInput = strInput.trim();

        topElement.appendChild(inputPage.createTextNode(strInput));
        topElement.appendChild(inputPage.createElement("br"));
    }

    /**
     * Create an http element.
     */
    private static void createElementHttp(String strInput, String linkInput, 
                                          HTMLObjectImpl inputPage, 
                                          Element topElement) {
        // Delete the last empty space
        strInput = strInput.trim();
        linkInput = linkInput.trim();

        // Check whether the str and link the same size?
        // Yes, just make a link
        // No, we have to make 2 links!
        if (strInput.equalsIgnoreCase(linkInput)) {
            // Same size, the same string
            HTMLAnchorElement httpNode = (HTMLAnchorElement) inputPage.createElement(
                                                 "a");
            httpNode.setHref(linkInput);
            httpNode.appendChild(inputPage.createTextNode(strInput));
            topElement.appendChild(httpNode);
        } else {
            // Not the same string, we have to divide this into 2 links
            StringBuffer sb = new StringBuffer(strInput);
            String stringResult = new String("");
            Node input1 = null;
            Node input2 = null;
            int index = 0;

            // Go through the text to find the \n
            while (index < sb.length()) {
                stringResult = stringResult + sb.charAt(index);

                // --- Check for \n new line ---
                if (sb.charAt(index) == '\n') {
                    input1 = inputPage.createTextNode(stringResult);
                    stringResult = "";
                }

                index++;
            }


            // The rest text...
            input2 = inputPage.createTextNode(stringResult);

            // Now we create the anchor...
            // First line
            HTMLAnchorElement httpNode1 = (HTMLAnchorElement) inputPage.createElement(
                                                  "a");
            httpNode1.setHref(linkInput);
            httpNode1.appendChild(input1);
            topElement.appendChild(httpNode1);

            createElementNewLine("", inputPage, topElement);

            // Second line
            HTMLAnchorElement httpNode2 = (HTMLAnchorElement) inputPage.createElement(
                                                  "a");
            httpNode2.setHref(linkInput);
            httpNode2.appendChild(input2);
            topElement.appendChild(httpNode2);
        }
    }

    /**
     * Create a space or blanks element.
     */
    private static int createElementSpace(int index, StringBuffer sb, 
                                          String strInput, 
                                          HTMLObjectImpl inputPage, 
                                          Element topElement) {
        // Delete the last empty space
        strInput = strInput.trim();

        // Check whether the space " " is more than one?
        int spaceIndex = index + 1;

        if ((spaceIndex < sb.length()) && (sb.charAt(spaceIndex) == ' ')) {
            // More than one spaces
            // The first space will be changed with the " "
            topElement.appendChild(inputPage.createTextNode(strInput));
            topElement.appendChild(inputPage.createCDATASection(" "));


            // The next spaces have to be changed with &nbsp;
            topElement.appendChild(inputPage.createCDATASection("&nbsp;"));

            spaceIndex++;

            // The rest of the spaces have to be changed with &nbsp;
            while ((spaceIndex < sb.length()) && 
                   (sb.charAt(spaceIndex) == ' ')) {
                topElement.appendChild(inputPage.createCDATASection("&nbsp;"));

                spaceIndex++;
            }
        } else {
            // Only one space
            topElement.appendChild(inputPage.createTextNode(strInput));
            topElement.appendChild(inputPage.createCDATASection(" "));
        }


        // Change the index to begin with
        spaceIndex = spaceIndex - 1;

        return spaceIndex;
    }

    /**
     * Format a normal text in a given width for html formatting.
     */
    private static String formatTextToWidthForHtml(String inputText, int width) {
        StringBuffer sb = new StringBuffer(inputText);
        int counter = 0;
        int emptySpacePos = -1;

        for (int index = 0; index < sb.length(); index++) {
            // --- Save the last empty space ---
            if (sb.charAt(index) == ' ') {
                // Save the position for later use
                // in the break position
                emptySpacePos = index;
            }

            // --- Check counter == width? ---
            if (counter == width) {
                // Make a break ;-) at the right position
                // Check first, whether this is in the middle of a word?
                // Yes, go back to find an empty space and make a break
                // at that point
                if (emptySpacePos == -1) {
                    // No empty space before...
                    // Make a break directly at the place
                    sb.insert(index, '\n');
                } else {
                    // Okay, there was an empty space
                    sb.insert(emptySpacePos, '\n');

                    // After a break delete the empty space
                    if ((sb.charAt(emptySpacePos + 1)) == ' ') {
                        sb.replace(emptySpacePos + 1, emptySpacePos + 2, "");
                    }
                }


                // Reset the counter
                counter = 0;
                emptySpacePos = -1;
            }

            // --- Check for newline? ---
            if (sb.charAt(index) == '\n') {
                // Reset the counter
                counter = 0;
                emptySpacePos = -1;
            }

            counter++;
        }

        return sb.toString();
    }

    /**
     * Format a normal text in a given width just by adding an \n.
     */
    public static String formatTextToWidth(String inputText, int width) {
        StringBuffer sb = new StringBuffer(inputText);
        int counter = 0;

        for (int index = 0; index < sb.length(); index++) {
            // --- The empty space means ok ---
            if (sb.charAt(index) == ' ') {
                counter = 0;
            }

            // --- Check counter == width? ---
            if (counter == width) {
                // Make a break ;-) at the right position
                sb.insert(index, '\n');


                // Reset the counter
                counter = 0;
            }

            counter++;
        }

        return sb.toString();
    }

    /**
     * Format a normal text in a given width just by adding an \n.
     * This method ignore http input.
     */
    public static String formatTextToWidthIgnoreHttp(String inputText, 
                                                     int width) {
        StringBuffer sb = new StringBuffer(inputText);
        int counter = 0;

        for (int index = 0; index < sb.length(); index++) {
            // --- The empty space means ok ---
            if (sb.charAt(index) == ' ') {
                counter = 0;
            }

            // --- Check for http at the beginning and ignore ---
            if (isTextHttp(sb, index)) {
                // We found http, we have to go until the end of this
                // http
                Vector result = getTextHttp(sb, index);
                Integer indexInteger = (Integer) result.get(0);
                index = indexInteger.intValue();

                counter = 0;
            }

            // --- Check counter == width? ---
            if (counter == width) {
                // Not http we can make a break ;-) at the right position
                sb.insert(index, '\n');


                // Reset the counter
                counter = 0;
            }

            counter++;
        }

        return sb.toString();
    }

    /**
     * Check whether this "h" ends up with http://.
     */
    private static boolean isTextHttp(StringBuffer inputStringBuffer, 
                                      int inputIndex) {
        // Check for the "h"
        if (inputStringBuffer.charAt(inputIndex) != 'h') {
            // Not h!
            return false;
        } else {
            // This is h and check further
            try {
                String httpStr = new String(inputStringBuffer.substring(
                                                    inputIndex, inputIndex + 7));

                // Check for the rest if no error... until this line
                // You can be sure that the line is more or equal to 7 digits
                if (httpStr.equalsIgnoreCase("http://")) {
                    // Yes http://
                    return true;
                } else {
                    // Nope
                    return false;
                }
            } catch (StringIndexOutOfBoundsException ex) {
                // No chars any longer! This can't be http://
                return false;
            }
        }
    }

    /**
     * Get the http://.
     */
    private static Vector getTextHttp(StringBuffer inputStringBuffer, 
                                      int inputIndex) {
        String httpStr = new String();
        int index = inputIndex;
        boolean notEndHttp = true;

        // Get the http until an empty space
        while ((index < inputStringBuffer.length()) && notEndHttp) {
            httpStr = httpStr + inputStringBuffer.charAt(index);

            if (inputStringBuffer.charAt(index) == ' ') {
                // End of http
                httpStr = httpStr.trim();
                notEndHttp = false;
            }

            if (inputStringBuffer.charAt(index) == '\n') {
                // End of http
                httpStr = httpStr.trim();
                notEndHttp = false;
            }

            index++;
        }

        // Check for the end of buffer?
        if (index >= inputStringBuffer.length()) {
            // End
            index = index - 1;
        } else {
            // Not yet
            index = index - 2;
        }

        // Result1: int
        // Result2: String
        Vector result = new Vector();
        result.add(new Integer(index));
        result.add(httpStr);

        return result;
    }

    /**
     * Here the BODY-tag is design, first removing old attribs, then set new
     * attribs
     *
     * @param  list         List of all nodes found by the NAME-param
     * @param  bodyBgColor
     * @param  bodyLink
     * @param  bodyALink
     * @param  bodyVLink
     */
    public static void bodySetter(NodeList list, String bodyBgColor, 
                                  String bodyLink, String bodyALink, 
                                  String bodyVLink) {
        int length = list.getLength();
        int i;

        for (i = 0; i < length; i++) {
            HTMLElement element = (HTMLElement) list.item(i);
            element.removeAttribute("BGCOLOR");
            element.removeAttribute("LINK");
            element.removeAttribute("ALINK");
            element.removeAttribute("VLINK");
            element.setAttribute("BGCOLOR", bodyBgColor);
            element.setAttribute("LINK", bodyLink);
            element.setAttribute("ALINK", bodyALink);
            element.setAttribute("VLINK", bodyVLink);
        }
    }

    /**
     * Here the TABLE-tag is design, first removing old attribs, then set new
     * attribs
     *
     * @param  list          List of all nodes found by the NAME-param
     * @param  tableBgColor
     */
    public static void tableSetter(NodeList list, String tableBgColor) {
        int length = list.getLength();
        int i;

        for (i = 0; i < length; i++) {
            HTMLElement element = (HTMLElement) list.item(i);
            element.removeAttribute("BGCOLOR");
            element.setAttribute("BGCOLOR", tableBgColor);
        }
    }

    /**
     * Here the TD/TR-tag is design, first removing old attribs, then set new
     * attribs
     *
     * @param  list          List of all nodes found by the NAME-param
     * @param  tableBgColor
     */
    public static void cellSetter(NodeList list, String tableBgColor) {
        int length = list.getLength();
        int i;

        for (i = 0; i < length; i++) {
            HTMLElement element = (HTMLElement) list.item(i);
            element.removeAttribute("BGCOLOR");
            element.setAttribute("BGCOLOR", tableBgColor);
        }
    }

    /**
     * Here the FONT-tag is design, first removing old attribs, then set new
     * attribs
     *
     * @param  list       List of all nodes found by the NAME-param
     * @param  fontFace
     * @param  fontSize
     * @param  fontColor
     */
    public static void fontSetter(NodeList list, String fontFace, 
                                  String fontSize, String fontColor) {
        int length = list.getLength();
        int i;

        for (i = 0; i < length; i++) {
            HTMLElement element = (HTMLElement) list.item(i);
            element.removeAttribute("FACE");
            element.removeAttribute("SIZE");
            element.removeAttribute("COLOR");
            element.setAttribute("FACE", fontFace);
            element.setAttribute("SIZE", fontSize);
            element.setAttribute("COLOR", fontColor);
        }
    }

    /**
     * Checks the startIndex and Count.
     */
    public static Vector checkStartIndexAndCount(String startIndexStr, 
                                                 String countStr, 
                                                 int listAmount) {
        // Definition
        int startIndex;
        int count;

        if ((startIndexStr == null) || startIndexStr.equals("")) {
            // Use default value
            startIndex = 0;
        } else {
            startIndex = Integer.valueOf(startIndexStr).intValue();
        }

        if ((countStr == null) || countStr.equals("")) {
            // Use default value
            count = listAmount;
        } else {
            count = Integer.valueOf(countStr).intValue();
        }

        // Save the result in a vector:
        // - startIndex
        // - count
        Vector result = new Vector();
        result.addElement(new Integer(startIndex));
        result.addElement(new Integer(count));

        return result;
    }

    /**
     * Show previous and next bar.
     */
    public static void showPreviousAndNextBar(String pageName, 
                                              String extraParam, 
                                              HTMLAnchorElement linkNext, 
                                              HTMLAnchorElement linkPrev, 
                                              int startIndex, int count, 
                                              int listAmount, 
                                              int currentListAmount) {
        // Don't show previous if:
        // - The startIndex variable = 0
        if (startIndex == 0) {
            // Delete the cell in the table
            Node cellElement = linkPrev.getParentNode().getParentNode();
            cellElement.getParentNode().removeChild(cellElement);
        } else {
            // Calculate the startIndex for Previous
            int startIndexPrev = startIndex - listAmount;


            // Show the link before
            linkPrev.setHref(pageName + "?StartIndex=" + 
                             String.valueOf(startIndexPrev) + "&Count=" + 
                             listAmount + extraParam);
        }

        // Don't show next if:
        // - The real amount of the list != the count variable
        if (currentListAmount != count) {
            // Delete the cell in the table
            Node cellElement = linkNext.getParentNode().getParentNode();
            cellElement.getParentNode().removeChild(cellElement);
        } else {
            // Calculate the startIndex for Next
            int startIndexNext = startIndex + listAmount;


            // Show the link after
            linkNext.setHref(pageName + "?StartIndex=" + 
                             String.valueOf(startIndexNext) + "&Count=" + 
                             listAmount + extraParam);
        }
    }
}