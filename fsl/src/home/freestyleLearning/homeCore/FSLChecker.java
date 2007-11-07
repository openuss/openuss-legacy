package freestyleLearning.homeCore;

import java.io.*;
import java.util.*;


// TODO:
// 1. notes manager checken
// 2. link check beim check up: target unknown links werden noch nicht auf Nutzung geprueft
// 3. Methode readLearningUnits funktioniert derzeit nur mit Pfadangaben, die ein "/" enthalten
// 4. FSLChecker funktioniert derzeit nur, wenn alle Inhalte direkt unter dem LearningUnits-Dir sind

public class FSLChecker {
    private Hashtable learningUnitDirs = new Hashtable();
    private Hashtable learningUnitTitles = new Hashtable();
    private Hashtable viewManagerDirs = new Hashtable();
    private String lastHTMLFileName =null;
    private String lastViewElementLinkId = null;
    private String lastViewElementTitle = null;
    
    public void readLearningUnits(File learningUnitsDir) {
        File descriptor = new File(learningUnitsDir, "learningUnitsDescriptor.xml");
        try {
            BufferedReader in =	new BufferedReader(new InputStreamReader(new FileInputStream(descriptor), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                if (contains(str, "<learningUnitDescriptor ")) {
                    String id = get(str, "id");
                    String dir = get(str, "path");
                    // get the local dir name
                    int index = dir.lastIndexOf("/");
                    learningUnitDirs.put(id, dir.substring(index + 1));
                    String title = get(str, "title");
                    learningUnitTitles.put(dir.substring(index + 1), title);
                }
            }
            in.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }
        
    }
    
    public void readViewManagers(File viewManagersDescriptorFile) {
        try {
            BufferedReader in =	new BufferedReader(new InputStreamReader(new FileInputStream(viewManagersDescriptorFile), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                if (contains(str, "<learningUnitViewManagerDescriptor ")) {
                    String id = get(str, "id");
                    String dir = get(str, "directoryName");
                    viewManagerDirs.put(id, dir);
                }
            }
            in.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    // glossary, caseStudy, etc.
    public void checkManagersTypA(File learningUnitsDir, File errorProtocolFile, String managerTitle, String fileTag) {
        File[] subFiles = learningUnitsDir.listFiles();
        for (int i = 0; i < subFiles.length; i++) {
            File subFile = subFiles[i];
            if (subFile.isDirectory()) {
                String learningUnitDirName = subFile.getName();
                String learningUnitTitle = (String) learningUnitTitles.get(learningUnitDirName);
                File managerDir = new File(subFile, managerTitle);
                if (managerDir.exists()) {
                    checkManagerTypA(learningUnitTitle, learningUnitsDir, managerDir, errorProtocolFile, managerTitle, fileTag);
                }
            }
        }
    }
    
    private BufferedWriter getWriter(File file) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file, true));
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        return out;
    }
    
    private void closeWriter(BufferedWriter out) {
        try {
            out.close();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private File writePrologAndGetContentsFile(BufferedWriter out, String learningUnitTitle, String managerTitle, File managerDir) {
        File contentsFile = null;
        System.out.println("Checking " + learningUnitTitle + ", " + managerTitle + ": " + managerDir);
        try {
            out.write("Checking " + learningUnitTitle + ", " + managerTitle + ": " + managerDir);
            out.newLine();
            File file = new File(managerDir, "contents.xml");
            if (file.exists())
                contentsFile = file;
            else {
                out.write("  content.xml missing");
                out.newLine();
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        return contentsFile;
    }
    
    public void checkManagerTypA(String learningUnitTitle, File learningUnitsDir, File managerDir, File errorProtocolFile, String managerTitle, String fileTag) {        
        Vector fileTagMissing = new Vector();
        Vector fileMissing = new Vector();
        Vector linkTagsMissing = new Vector();
        Vector linkTargetUnknown = new Vector();
        
        BufferedWriter out = getWriter(errorProtocolFile);
        if (out == null) {
            return;
        }
        
        File contentsFile = writePrologAndGetContentsFile(out, learningUnitTitle, managerTitle, managerDir);
        if (contentsFile == null) {
            return;
        }
        
        try {
            BufferedReader in =	new BufferedReader(new InputStreamReader(new FileInputStream(contentsFile), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                checkElement(str, "viewElement ", fileTag, fileTagMissing, fileMissing, managerDir);
                checkLinks(str,	linkTagsMissing,linkTargetUnknown, learningUnitsDir, managerDir);
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        };
        
        writeErrors("  file tag missing:", fileTagMissing, out);
        writeErrors("  file missing: ", fileMissing, out);
        writeErrors("  link tag(s) missing: ", linkTagsMissing, out);
        writeErrors("  link target unknown: ", linkTargetUnknown, out);
        closeWriter(out);
    }
    
    public void checkCheckUps(File learningUnitsDir, File errorProtocolFile) {
        File[] subFiles = learningUnitsDir.listFiles();
        for (int i = 0; i < subFiles.length; i++) {
            File subFile = subFiles[i];
            if (subFile.isDirectory()) {
                String learningUnitDirName = subFile.getName();
                String learningUnitTitle = (String) learningUnitTitles.get(learningUnitDirName);
                File managerDir = new File(subFile, "checkUp");
                if (managerDir.exists()) {
                    checkCheckUp(learningUnitTitle, learningUnitsDir, managerDir, errorProtocolFile);
                }
            }
        }
    }
    
    public void checkCheckUp(String learningUnitTitle, File learningUnitsDir, File managerDir, File errorProtocolFile) {
        Vector fileTagMissing = new Vector();
        Vector fileMissing = new Vector();
        Vector linkTagsMissing = new Vector();
        Vector linkTargetUnknown = new Vector();
        
        BufferedWriter out = getWriter(errorProtocolFile);
        if (out == null) {
            return;
        }
        
        File contentsFile = writePrologAndGetContentsFile(out, learningUnitTitle, "checkUp", managerDir);
        if (contentsFile == null) {
            return;
        }
        
        try {
            BufferedReader in =	new BufferedReader(new InputStreamReader(new FileInputStream(contentsFile), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                checkElement(str, "multipleChoice ",	"questionHtmlFileName", fileTagMissing, fileMissing, managerDir);
                checkElement(str, "multipleChoiceAnswer ", "htmlFileName", fileTagMissing,	fileMissing, managerDir);
                checkElement(str, "relatorStartPoint ", "htmlFileName", fileTagMissing, fileMissing, managerDir);
                checkElement(str, "relatorEndPoint ", "htmlFileName", fileTagMissing, fileMissing, managerDir);
                checkElement(str, "relator ", "questionHtmlFileName", fileTagMissing, fileMissing, managerDir);
                checkLinks(str, linkTagsMissing, linkTargetUnknown, learningUnitsDir, managerDir);
            }
            in.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }
        writeErrors("  file tag missing:", fileTagMissing, out);
        writeErrors("  file missing: ", fileMissing, out);
        writeErrors("  link tag(s) missing: ", linkTagsMissing, out);
        writeErrors("  link target unknown: ", linkTargetUnknown, out);
        closeWriter(out);
        
    }
    private void checkElement(String str, String elementName, String fileTag, Vector fileTagMissing, Vector fileMissing, File managerDir) {
        if (contains(str, elementName)) {
            if (!contains(str, "folder=\"true\"") && !contains(str, "type=\"slideShow\"")) {
                if (!contains(str, fileTag)) {
                    fileTagMissing.add(elementDescription(str));
                } 
                else {
                    String fileName = get(str, fileTag);
                    if (fileName.length() == 0) {
                        fileMissing.add(elementDescription(str));
                    }
                    else {
                        File file = new File(managerDir, fileName);
                        if (!file.exists()) {
                            fileMissing.add(elementDescription(str));
                            System.out.println(elementDescription(str));
                        }
                    }
                }
            }
        }
    }
    
    private void checkLinks(String str, Vector linkTagsMissing, Vector linkTargetUnknown, File learningUnitsDir, File managerDir) {
        if (contains(str, "viewElement ")) {
            if (contains(str, "htmlFileName")) {
                lastHTMLFileName = get(str, "htmlFileName");
            } 
            else {
                lastHTMLFileName = null;
            }
            lastViewElementTitle = get(str, "title");
        }
        if (contains(str, "viewElementLink ")) {
            if (contains(str, "id=")) {
                lastViewElementLinkId = get(str, "id");
            } 
            else {
                lastViewElementLinkId = null;
            }
        }
        
        if (contains(str, "viewElementLinkTarget")) {
            if (!contains(str, "targetViewManagerId") || !contains(str, "targetViewElementId") || !contains(str, "targetViewElementId")) {
                linkTagsMissing.add(str);
            } 
            else {
                boolean targetUnknown = false;
                String manager = get(str, "targetViewManagerId");
                String unit = get(str, "targetLearningUnitId");
                String id = get(str, "targetViewElementId");
                String unitDirStr = (String) learningUnitDirs.get(unit);
                String targetManagerDirStr =
                (String) viewManagerDirs.get(manager);
                if (unitDirStr == null || targetManagerDirStr == null) {
                    targetUnknown = true;
                } 
                else {
                    File unitDir = new File(learningUnitsDir, unitDirStr);
                    if (!unitDir.exists()) {
                        targetUnknown =true;
                    } 
                    else {
                        File targetDir = new File(unitDir, targetManagerDirStr);
                        if (!targetDir.exists()) {
                            targetUnknown = true;
                        } 
                        else {
                            File targetContentsXML =
                            new File(targetDir, "contents.xml");
                            if (!targetContentsXML.exists()) {
                                targetUnknown = true;
                            } 
                            else {
                                String searchStr =
                                "<viewElement id=\"" + id + "\"";
                                if (!fileContains(targetContentsXML, searchStr)) {
                                    targetUnknown = true;
                                }
                            }
                        }
                    }
                }
                if (targetUnknown) {
                    if (lastHTMLFileName != null && lastViewElementLinkId != null) {
                        File file = new File(managerDir, lastHTMLFileName);
                        if (file.exists()) {
                            if (fileContains(file, "a href=\"" + lastViewElementLinkId + "\"")) {
                                linkTargetUnknown.add(lastViewElementTitle + ", " + lastHTMLFileName + ", " + lastViewElementLinkId + ": " + str);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean fileContains(File targetContentsXML, String searchStr) {
        boolean found = false;
        try {
            BufferedReader in =	new BufferedReader(new InputStreamReader(new FileInputStream(targetContentsXML), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null && !found) {
                if (contains(str, searchStr)) {
                    found = true;
                }
            }
            in.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }
        return found;
    }
    
    private void writeErrors(String title, Vector errors, BufferedWriter out) {
        try {
            if (errors.size() > 0) {
                out.write(title);
                out.newLine();
                for (int i = 0; i < errors.size(); i++) {
                    out.write("    " + errors.get(i));
                    out.newLine();
                }
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private String elementDescription(String str) {
        if (contains(str, "id=") && contains(str, "title=")) {
            return get(str, "id") + ": " + get(str, "title");
        }
        else {
            return lastViewElementTitle + ": " + str;
        }
    }
    
    private boolean contains(String str, String subString) {
        return (str.indexOf(subString) >= 0);
    }
    
    private String get(String str, String type) {
        int index = str.indexOf(type + "=");
        if (index < 0) {
            return "";
        }
        int start = index + type.length() + 2;
        int stop = str.indexOf("\"", start);
        return str.substring(start, stop);        
    }
    
    public static void main(String[] args) {
        File learningUnitsDir = new File("D:\\WNC\\LearningUnits");
        File errorProtocolFile = new File("D:\\Java\\FSLfslErrors2.txt");
        File viewManagersDescriptorFile = new File("C:\\Programme\\Freestyle Learning WNC\\learningUnitViewManagers\\learningUnitViewManagersDescriptor.xml");
        if (learningUnitsDir.isDirectory()) {
            FSLChecker checker = new FSLChecker();
            try {
                BufferedWriter out =
                new BufferedWriter(new FileWriter(errorProtocolFile));
                out.write("error protocol for freestyle learning");
                out.newLine();
                out.newLine();
                out.close();
            } 
            catch (IOException e) {
                System.out.println(e);
            }
            checker.readLearningUnits(learningUnitsDir);
            checker.readViewManagers(viewManagersDescriptorFile);
            
            System.out.println("Start");
            
            checker.checkManagersTypA(learningUnitsDir,	errorProtocolFile, "textStudy", "htmlFileName");
            checker.checkManagersTypA(learningUnitsDir,	errorProtocolFile, "glossary", "htmlFileName");
            checker.checkManagersTypA(learningUnitsDir,	errorProtocolFile, "caseStudy", "htmlFileName");
            checker.checkManagersTypA(learningUnitsDir, errorProtocolFile,"mediaPool","mediaFileName");
            checker.checkManagersTypA(learningUnitsDir, errorProtocolFile, "learningByDoing","executableFileName");
            checker.checkManagersTypA(learningUnitsDir, errorProtocolFile, "slideShow",	"imageFileName");
            checker.checkManagersTypA(learningUnitsDir, errorProtocolFile,"intro","videoFileName");
            checker.checkCheckUps(learningUnitsDir, errorProtocolFile);
            
            System.out.println("Finish");
            
        } 
        else {
            System.out.println("Wrong path to learning units: " + learningUnitsDir);
        }
    }
}
