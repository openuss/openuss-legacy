package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.openOffice;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

import freestyleLearningGroup.independent.gui.*;


/**
* Contains all methods for doing import of OpenOffice Writer document into FSL.
*
*  A directory called openOffice must exist as subdirectory of FSL install dir and
*  contain all OpenOffice DTDs and both xsl stylsheets:
*  stylesheet-files.xsl , stylesheet-descriptor.xsl
*
*  After import data have to reloaded via F5!
*
*  The temporary generated xml files in bin/openOffice use no pretty printing
*  to improve performance.
*  Temp files will not be deleted automatically after import, but overwritten at next import.
*
* @author Edith Schewe
*/
public class OOoTextStudyImporter {
    LogFrame log = LogFrame.getInstance();

    public OOoTextStudyImporter() {
        super();
    }

    /**
* Identifies FSL install directory (bin)
*
* @author Edith Schewe
* @param textStudy directory without end slash/backslash (bin/learningUnits/XXX/textStudy)
* @param platform depended pathSeparator (win32 backslash, linux slash)
* @return FSL install dir (bin)
*/
    private String getFSLInstallDirectory(String textStudyDirectory, char pathSeparator) {
        StringBuffer erg = new StringBuffer();
        int lastSeparator = textStudyDirectory.lastIndexOf(pathSeparator);
        erg.append(textStudyDirectory.substring(0, lastSeparator));

        String sPathSeparator = String.valueOf(pathSeparator);
        lastSeparator = erg.lastIndexOf(sPathSeparator);
        erg.delete(lastSeparator, erg.length());
        lastSeparator = erg.lastIndexOf(sPathSeparator);
        erg.delete(lastSeparator - 1, erg.length());

        return erg.toString();
    }

    /**
* Identifies Id (name) of learning unit.
*
* @author Edith Schewe
* @param textStudy directory without end slash/backslash (bin/learningUnits/XXX/testStudy)
* @param platform depended pathSeparator (win32 backslash, linux slash)
* @return learning unit Id
*/
    private String getLearningUnitId(String textStudyDirectory,
        char pathSeparator) {
        StringBuffer erg = new StringBuffer();
        int lastSeparator = textStudyDirectory.lastIndexOf(pathSeparator);
        erg.append(textStudyDirectory.substring(0, lastSeparator));

        String sPathSeparator = String.valueOf(pathSeparator);
        lastSeparator = erg.lastIndexOf(sPathSeparator);
        erg.delete(0, lastSeparator + 1);

        return erg.toString();
    }

    /**
 * Main method for doing import of OpenOffice Writer document into FSL.
 *
 *  A directory called openOffice must exist as subdirectory of FSL install dir and
 *  contain all OpenOffice DTDs and both xsl stylsheets:
 *  stylesheet-files.xsl , stylesheet-descriptor.xsl
 *
 * After import FSL must be restartet!
 *
 *  @author Edith Schewe
 * @param File oooSXW the OpenOffice Writer file
 * @param textStudy directory without end slash/backslash (bin/learningUnits/XXX/textStudy)
 */
    public void importTextStudy(File oooSXW, String textStudyDirectory) throws Exception {
//        try {
            log.add("starting importTextStudyFile...");

            char pathSeparator = File.separatorChar;

            String fslInstallDirectory = getFSLInstallDirectory(textStudyDirectory,pathSeparator);
            log.add("fslInstallDirectory is" + fslInstallDirectory);

            String learningUnitId = getLearningUnitId(textStudyDirectory, pathSeparator);
            log.add("learningUnitId is " + learningUnitId);

            String oooSXWFile = oooSXW.getAbsolutePath();

            String extractedOOoFile = fslInstallDirectory + "openOffice" + pathSeparator + "extracted_ooo_content.xml";

            extractContentXMLFromSXWFile(oooSXWFile, extractedOOoFile);

            String filesXMLFile = fslInstallDirectory + "openOffice" +
                pathSeparator + "files.xml"; // for splitting in html files
            String fslContentsXMLFile = fslInstallDirectory + "openOffice" +
                pathSeparator + "contents.xml"; // fsl descriptor file
            String stylesheetFilesXSL = fslInstallDirectory + "openOffice" +
                pathSeparator + "stylesheet-files.xsl";
            String stylesheetDescriptorXSL = fslInstallDirectory + "openOffice" +
                pathSeparator + "stylesheet-descriptor.xsl";

            xslTransformation(extractedOOoFile, fslContentsXMLFile, stylesheetDescriptorXSL); // generate contents.xml

            
            setAttributeTargetLearningUnitId(fslContentsXMLFile, learningUnitId); // manipulate and save contents.xml

   
            
            xslTransformation(extractedOOoFile, filesXMLFile, stylesheetFilesXSL); // generate files.xml

            makeDirectory(textStudyDirectory);

            textStudyDirectory = textStudyDirectory + String.valueOf(pathSeparator);
            splitFilesXMLtoFSLHtmlFiles(filesXMLFile, textStudyDirectory);
            extractPicturesFromSXWFile(oooSXWFile, textStudyDirectory);

            copyFile(fslContentsXMLFile, textStudyDirectory + "contents.xml");

            log.add("importTextStudyFile ended successfully.");
//        }
//        catch(Exception e) {
//            String message = "Das Dokument konnte nicht transformiert werden:\n\n" + e.getMessage();
//            String title = "Fehler beim Transformieren";
//            FLGOptionPane.showMessageDialog(message, title, FLGOptionPane.ERROR_MESSAGE);
//        }
    }

    /**
* copies file from input to output
*
* @author Edith Schewe
*/
    private void copyFile(String input, String output)
        throws Exception {
        copyFile(new FileInputStream(input), new FileOutputStream(output));
    }

    /**
* copies file from input to output
*
* @author Edith Schewe
*/
    private void copyFile(InputStream input, OutputStream output)
        throws Exception {
        log.add("copyFile...");

        byte[] buffer = new byte[100000];
        int read = 0;
        InputStream in = input;
        OutputStream out = output;

        try {
            while (true) {
                read = in.read(buffer);

                if (read == -1) //-1  is  EOF
                 {
                    break;
                }

                out.write(buffer, 0, read);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            }
        }

        log.add("...done");
    }

    /**
* Extracts file content.xml from OOo sxw file (standard zip archive) to file extracted_ooo_content.xml
*
* @author Edith Schewe
*/
    private void extractContentXMLFromSXWFile(String oooSXWFile,
        String outputFile) throws Exception {
        log.add("extracting content.xml from sxw file " + oooSXWFile + " to " +
            outputFile + " ...");

        InputStream input = null;
        ZipFile zipFile = new ZipFile(oooSXWFile); // e.g. "test.sxw"
        log.add(zipFile.getName());

        ZipEntry zipEntry = zipFile.getEntry("content.xml"); // name is standardized by OOo Team
        input = zipFile.getInputStream(zipEntry);

        copyFile(input, new FileOutputStream(outputFile)); // "extracted_ooo_content.xml"

        log.add("...done.");
    }

    /**
* Extracts all pictures from OOo sxw file (standard zip archive) to FSL TextStudy directory
*
* @author Edith Schewe
*/
    private void extractPicturesFromSXWFile(String oooSXWFile,
        String outputDirectory) throws Exception {
        log.add("extracting pictures from sxw file " + oooSXWFile +
            " to directory" + outputDirectory + " ...");

        InputStream input = null;

        ZipFile zipFile = new ZipFile(oooSXWFile);

        // listes zip content
        Enumeration enumeration = zipFile.entries();

        while (enumeration.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();

            String filename = zipEntry.getName();

            if (filename.startsWith("Pictures/")) {
                log.add("extracting picture " + filename);

                int pos = filename.indexOf("/");
                filename = filename.substring(pos + 1); // without /

                log.add("to file " + filename);
                input = zipFile.getInputStream(zipEntry);
                copyFile(input, new FileOutputStream(outputDirectory +
                        filename)); // "extracted_ooo_content.xml"
            }
        }

        log.add("...done.");
    }

    /**
* Does an XML transformation via standard JAXP methods.
*
* @author Edith Schewe
*/
    private void xslTransformation(String inputFile, String outputFile,
    		String xslFile) throws TransformerException, TransformerConfigurationException,
            	FileNotFoundException, IOException {
        log.add("xslTransformation from " + inputFile + " to " + outputFile +
            " using stylesheet " + xslFile + "  please wait...");

        TransformerFactory tFactory = TransformerFactory.newInstance();

        
        // hier steigt der OOImport aus...
        Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
        
    	
    	
        transformer.transform(new StreamSource(inputFile),
            new StreamResult(new FileOutputStream(outputFile)));
        log.add("...done");
    }

    /**
*  Transforms DOM tree of input file and sets attribute targetLearningUnitId, because this is only know in FSL and not in XSL
* stylesheets.
*
* @author Edith Schewe
*/
    private void setAttributeTargetLearningUnitId(String inputFile,
        String learningUnitId) throws Exception {
        log.add("setAttributeTargetLearningUnitId of file " + inputFile +
            " to " + learningUnitId + "...");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);

        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(inputFile);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        // manipulate DOM tree
        NodeList nl = document.getElementsByTagName("viewElementLinkTarget"); // find all nodes

        for (int j = 0; j < nl.getLength(); j++) {
            Node oneNode = nl.item(j);
            NamedNodeMap attributes = oneNode.getAttributes();
            Node targetLearningUnitIdAttribute = ((Node) attributes.getNamedItem(
                    "targetLearningUnitId"));
            targetLearningUnitIdAttribute.setNodeValue(learningUnitId);
        }

        FileOutputStream out = new FileOutputStream(inputFile);

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(out);

        transformer.transform(source, result);
        log.add("...done.");
    }

    /**
* Splits files.xml into FSL TextStudy html files.
* stylesheets.
*
* @author Edith Schewe
* @param absolute path of file files.xml
* @param outputDirectory always with last slash or backslash
*/
    private void splitFilesXMLtoFSLHtmlFiles(String inputFile,
        String outputDirectory) throws Exception {
        log.add("splitFilesXMLtoFSLHtmlFiles from " + inputFile +
            " to directory " + outputDirectory + "...");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(inputFile);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        NodeList nl = document.getElementsByTagName("file"); // find all file marker nodes

        // generates one html file per iteration
        for (int j = 0; j < nl.getLength(); j++) {
            Node fileNode = nl.item(j);

            NamedNodeMap attribute = fileNode.getAttributes();
            String htmlFileName = ((Node) attribute.getNamedItem("htmlFileName")).getNodeValue();

            NodeList fileNodeList = fileNode.getChildNodes();
            Node htmlNode = null;

            for (int i = 0; i < fileNodeList.getLength(); i++) {
                Node n = fileNodeList.item(i);

                if (n.getNodeName().equals("html")) {
                    htmlNode = n;

                    break;
                }
            }

            log.add("writing file " + htmlFileName + "...");

            Node outputNode = htmlNode;

            FileOutputStream out = new FileOutputStream(outputDirectory +
                    htmlFileName);

            DOMSource source = new DOMSource(outputNode);
            StreamResult result = new StreamResult(out);

            transformer.transform(source, result);
            log.add("...done.");
        }

        log.add("... splitting done.");
    }

    /**
* Creates whole directory tree up to given sub directory.
*
* @author Edith Schewe
* @param absolute path
*/
    private void makeDirectory(String path) {
        log.add("makeDirectory " + path + "...");

        File toCreateDir = new File(path);

        if (!toCreateDir.exists()) {
            toCreateDir.mkdirs();
        }

        log.add("...done.");
    }
}

