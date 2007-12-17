/* Generated by Freestyle Learning Group */

package freestyleLearningGroup.independent.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Vector;

import javax.swing.JTextPane;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLWriter;

public class FLGHtmlUtilities {
    // Removes all start tag and end tags with the name tagName in the String htmlText.
    // The text between these start tags and end tags will be kept
    // This method recognizes also start tags with attributes. This operation is not case sensitive.
    public static String removeTag(String tagName, String attribute, String htmlText) {
        int startTagStartIndex = htmlText.toLowerCase().indexOf("<" + tagName + " ");
        while (startTagStartIndex >= 0) {
            int startTagEndIndex = htmlText.indexOf(">", startTagStartIndex + 1);
            if (attribute != null) {
                int attributeIndex = htmlText.toLowerCase().indexOf(attribute, startTagStartIndex + 1);
                if (attributeIndex < 0 || attributeIndex > startTagEndIndex) {
                    startTagStartIndex = htmlText.toLowerCase().indexOf("<" + tagName + " ", startTagStartIndex + 1);
                    continue;
                }
            }
            int endTagStartIndex = htmlText.toLowerCase().indexOf("</" + tagName + ">", startTagEndIndex + 1);
            int endTagEndIndex = htmlText.indexOf(">", endTagStartIndex + 1);
            htmlText = htmlText.substring(0, startTagStartIndex) + htmlText.substring(startTagEndIndex + 1, endTagStartIndex) +
            htmlText.substring(endTagEndIndex + 1);
            startTagStartIndex = htmlText.toLowerCase().indexOf("<" + tagName + " ");
        }
        startTagStartIndex = htmlText.toLowerCase().indexOf("<" + tagName + ">");
        while (startTagStartIndex >= 0) {
            int startTagEndIndex = htmlText.indexOf(">", startTagStartIndex + 1);
            int endTagStartIndex = htmlText.toLowerCase().indexOf("</" + tagName + ">", startTagEndIndex + 1);
            int endTagEndIndex = htmlText.indexOf(">", endTagStartIndex + 1);
            htmlText = htmlText.substring(0, startTagStartIndex) + htmlText.substring(startTagEndIndex + 1, endTagStartIndex) +
            htmlText.substring(endTagEndIndex + 1);
            startTagStartIndex = htmlText.toLowerCase().indexOf("<" + tagName + ">");
        }
        return htmlText;
    }
    
    // Returns the selected text with html tags. The result is free of html-, body- and leading p-tags.
    public static String getSelectedHTMLText(JTextPane editor) {
        String selectedText = null;
        String selectedTextLowerCase = null;
        int p0 = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
        int p1 = Math.max(editor.getCaret().getDot(), editor.getCaret().getMark());
        HTMLDocument doc = (HTMLDocument)editor.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit)editor.getEditorKit();
        if (p0 != p1) {
            StringWriter stringWriter = new StringWriter();
            try {
                NoSpacesHTMLWriter htmlWriter = new NoSpacesHTMLWriter(stringWriter, (HTMLDocument)doc, p0, p1 - p0);
                htmlWriter.write();
                selectedText = stringWriter.toString();
                selectedTextLowerCase = selectedText.toLowerCase();
            }
            catch (Exception ex) { System.out.println(ex); }
        }
        if (selectedText != null && selectedText.length() > 0) {
            // getting rid of the html, body and leading p-Tag
            int indexA = selectedTextLowerCase.indexOf("<body>") + 6;
            int beginParagraphIndex = selectedTextLowerCase.indexOf("<p>");
            if (beginParagraphIndex == indexA)
                indexA = selectedTextLowerCase.indexOf(">", beginParagraphIndex + 1) + 1;
            else {
                beginParagraphIndex = selectedTextLowerCase.indexOf("<p ");
                if (beginParagraphIndex == indexA)
                    indexA = selectedTextLowerCase.indexOf(">", beginParagraphIndex + 1) + 1;
            }
            int indexB;
            if (beginParagraphIndex != -1) {
                indexB = selectedTextLowerCase.indexOf("</p");
                while (selectedTextLowerCase.indexOf("</p", indexB + 1) != -1)
                    indexB = selectedTextLowerCase.indexOf("</p", indexB + 1);
            }
            else {
                indexB = selectedTextLowerCase.indexOf("</body>");
            }
            selectedText = selectedText.substring(indexA, indexB);
        }
        return selectedText;
    }
    
    public static boolean isTagInText(String tagName, String text) {
        if (text != null) {
            String lowerCaseText = text.toLowerCase();
            boolean isStartTagInText = (lowerCaseText.indexOf("<" + tagName + ">") != -1) ||
            (lowerCaseText.indexOf("<" + tagName + " ") != -1);
            boolean isEndTagInText = lowerCaseText.indexOf("</" + tagName + ">") != -1;
            return isStartTagInText || isEndTagInText;
        }
        else {
            return false;
        }
    }
    
    public static Object[] getImageTagResources(String text) {
        Vector resources = new Vector();
        if (text != null) {
            String lowerCaseText = text.toLowerCase();
            while (lowerCaseText.indexOf("<img src=") > 0) {
                int ixResourceStart = lowerCaseText.indexOf("<img src=") + 10;
                int ixResourceEnd = lowerCaseText.indexOf("\"", ixResourceStart);
                resources.add(lowerCaseText.substring(ixResourceStart, ixResourceEnd));
                lowerCaseText = lowerCaseText.substring(ixResourceEnd + 1);
            }
        }
        return resources.toArray();
    }
    
    public static boolean isAnyTagInText(String[] tagNames, String text) {
        boolean returnValue = false;
        for (int i = 0; i < tagNames.length; i++)
            returnValue = returnValue || isTagInText(tagNames[i], text);
        return returnValue;
    }
    
    public static boolean isTagInSelection(String tagName, JTextPane editor) {
        return isTagInText(tagName, getSelectedHTMLText(editor));
    }
    
    public static String[] getAllRelativeFileNamesToHtmlFile(String relativeHtmlFileName, File htmlFile) {
        Vector paths = new Vector();
        paths.add(relativeHtmlFileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(htmlFile));
            String str = "";
            while ((str = in.readLine()) != null) {
                int imgTagStartPos = str.toLowerCase().indexOf("<img");
                if (imgTagStartPos != -1) {
                    // image tag found
                    int fileNameStartPos = str.indexOf("\"", str.toLowerCase().indexOf("src", imgTagStartPos + 1)) + 1;
                    int fileNameEndPos = str.indexOf("\"", fileNameStartPos + 1);
                    String imageFileName = str.substring(fileNameStartPos, fileNameEndPos);
                    paths.add(imageFileName);
                }
            }
            in.close();
        }
        catch (Exception e) { System.out.println(e); }
        return (String[]) paths.toArray(
        new String[] { });
    }
    
    public static String getAttributeValueOfTagInSelection(HTML.Attribute attribute, HTML.Tag tag, JTextPane editor) {
        int p0 = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
        int p1 = Math.max(editor.getCaret().getDot(), editor.getCaret().getMark());
        HTMLDocument myDocument = (HTMLDocument)editor.getDocument();
        HTMLDocument.Iterator tagIterator = myDocument.getIterator(tag);
        String attributeValue = null;
        if (tagIterator != null) {
            while (tagIterator.isValid() && attributeValue == null) {
                if (tagIterator.getStartOffset() <= p0 && p1 <= tagIterator.getEndOffset()) {
                    attributeValue = tagIterator.getAttributes().getAttribute(attribute).toString();
                }
                else
                    tagIterator.next();
            }
        }
        return attributeValue;
    }
    
    /**
     * This method removes all HTML-tags that may cause formatting problems with printing
     * @param <code>htmlText</code>: original HTML-Text
     * @return printable HTML-Text
     */
    public static String createPrintableHtmlText(String htmlText) {
        // remove FONT-COLOR Tags
        String printableHtmlText = removeTag("font", "color", htmlText);
        // remove Links
        printableHtmlText = removeTag("a", "href", printableHtmlText);
        return printableHtmlText;
    }
    
    /**
     * This method replaces all Entities by the corresponding symbol
     * Only lower cases currently supported.
     * @param <code>htmlTextWithEntities</code>: original HTML-Text containing entities
     * @return HTML-Text containing symbols
     */
    public static String createHTMLwithSymbols(String htmlTextWithEntities) {
        String htmlTextWithSymbols = htmlTextWithEntities;
        try {
            while (htmlTextWithEntities.lastIndexOf("&") >= 0) {
                String htmlTextWithSymbols_leading =
                htmlTextWithSymbols.substring(0, htmlTextWithEntities.lastIndexOf("&"));
                
                htmlTextWithSymbols = htmlTextWithSymbols_leading
                + replaceEntityBySymbol(htmlTextWithEntities.substring(htmlTextWithEntities.lastIndexOf("&")));
                
                System.out.println(htmlTextWithSymbols);
                return createHTMLwithSymbols(htmlTextWithSymbols);
            }
        }
        catch(Exception e) {
            System.out.println("Error converting HTML Entities: " + e);
        }
        return htmlTextWithEntities;
    }
    /**
     * This method replaces german "Umlaute"-symbols by the corresponding entity
     * @param <code>htmlTextWithSymbols</code>: original HTML-Text containing symbols
     * @return HTML-Text containing symbols
     */
    public static String createHTMLwithEntities(String htmlTextWithSymbols) {
        String htmlTextWithEntities = htmlTextWithSymbols;
        try {
            String symbol = "�";
            int lastIndex = htmlTextWithSymbols.lastIndexOf(symbol);
            while (lastIndex >= 0) {
                String htmlTextWithEntities_leading =
                htmlTextWithSymbols.substring(0, lastIndex);
                htmlTextWithEntities = htmlTextWithEntities_leading + "&#228;" + htmlTextWithEntities.substring(lastIndex + 1);
                return createHTMLwithEntities(htmlTextWithEntities);
            }
            symbol = "�";
            lastIndex = htmlTextWithSymbols.lastIndexOf(symbol);
            while (lastIndex >= 0) {
                String htmlTextWithEntities_leading =
                htmlTextWithSymbols.substring(0, lastIndex);
                htmlTextWithEntities = htmlTextWithEntities_leading + "&#196;" + htmlTextWithEntities.substring(lastIndex + 1);
                return createHTMLwithEntities(htmlTextWithEntities);
            }
            symbol = "�";
            lastIndex = htmlTextWithSymbols.lastIndexOf(symbol);
            while (lastIndex >= 0) {
                String htmlTextWithEntities_leading =
                htmlTextWithSymbols.substring(0, lastIndex);
                htmlTextWithEntities = htmlTextWithEntities_leading + "&#252;" + htmlTextWithEntities.substring(lastIndex + 1);
                return createHTMLwithEntities(htmlTextWithEntities);
            }
            symbol = "�";
            lastIndex = htmlTextWithSymbols.lastIndexOf(symbol);
            while (lastIndex >= 0) {
                String htmlTextWithEntities_leading =
                htmlTextWithSymbols.substring(0, lastIndex);
                htmlTextWithEntities = htmlTextWithEntities_leading + "&#220;" + htmlTextWithEntities.substring(lastIndex + 1);
                return createHTMLwithEntities(htmlTextWithEntities);
            }
            symbol = "�";
            lastIndex = htmlTextWithSymbols.lastIndexOf(symbol);
            while (lastIndex >= 0) {
                String htmlTextWithEntities_leading =
                htmlTextWithSymbols.substring(0, lastIndex);
                htmlTextWithEntities = htmlTextWithEntities_leading + "&#246;" + htmlTextWithEntities.substring(lastIndex + 1);
                return createHTMLwithEntities(htmlTextWithEntities);
            }
            symbol = "�";
            lastIndex = htmlTextWithSymbols.lastIndexOf(symbol);
            while (lastIndex >= 0) {
                String htmlTextWithEntities_leading =
                htmlTextWithSymbols.substring(0, lastIndex);
                htmlTextWithEntities = htmlTextWithEntities_leading + "&#214;" + htmlTextWithEntities.substring(lastIndex + 1);
                return createHTMLwithEntities(htmlTextWithEntities);
            }
            symbol = "�";
            lastIndex = htmlTextWithSymbols.lastIndexOf(symbol);
            while (lastIndex >= 0) {
                String htmlTextWithEntities_leading =
                htmlTextWithSymbols.substring(0, lastIndex);
                htmlTextWithEntities = htmlTextWithEntities_leading + "&#223;" + htmlTextWithEntities.substring(lastIndex + 1);
                return createHTMLwithEntities(htmlTextWithEntities);
            }
        }
        catch(Exception e) {
            System.out.println("Error converting HTML Symbols: " + e);
        }
        return htmlTextWithEntities;
    }
    
    private static String replaceEntityBySymbol(String stringWithEntity) {
        stringWithEntity = stringWithEntity + " ";
        if (stringWithEntity.substring(1, 2).equalsIgnoreCase("a")) {
            return "�" + stringWithEntity.substring(6);
        }
        if (stringWithEntity.substring(1, 2).equalsIgnoreCase("o")) {
            return "�" + stringWithEntity.substring(6);
        }
        if (stringWithEntity.substring(1, 2).equalsIgnoreCase("u")) {
            return "�" + stringWithEntity.substring(6);
        }
        if (stringWithEntity.substring(1, 2).equalsIgnoreCase("s")) {
            return "�" + stringWithEntity.substring(6);
        }
        return " und " + stringWithEntity.substring(1);
    }
    
    public static class NoSpacesHTMLWriter extends HTMLWriter {
        public NoSpacesHTMLWriter(Writer w, HTMLDocument doc, int pos, int len) {
            super(w, doc, pos, len);
            setCanWrapLines(false);
            setIndentSpace(0);
            setLineSeparator("");
        }
    }
}