/**
 * This class allows system indepent access to operating system specific functios like
 * <code>getApplicationPathToExtension</code>. <p> Every public system-specific function -
 * provided by this class - determines the actual running operation system and delegates the call
 * to a private function with the name and a short system suffix
 * e.g. public function <code>getApplicationPathToExtension()</code> delegates the call on windows plattforms to the function
 * <code>getApplicationPathToExtensionWIN()</code>. <p> To add support for other operating systems, the public functions need
 * a new if statement and the appropriate private function. <p> Implemented Support: Windows
 */

package freestyleLearningGroup.independent.util;

import java.awt.Component;
import java.io.*;
import java.util.jar.*;
import javax.swing.JOptionPane;

public class FLGPlatformSpecifics {
    // startable programmes
    private static final String EXE = "exe";
    private static final String CLASS = "class";
    private static final String JAR = "jar";
    // long system ids
    private static final String LINUX = "Linux";
    private static final String MACOS_X = "Mac OS X";
    
    // short system ids
    private static final String WIN = "win";
    private static String WIN_REGISTRY_SEPARATOR = "\\";
    private static String WIN_REGISTRY_PATH_SOFTWARE = "Software";
    private static String WIN_REGISTRY_KEY_VERSION = "Version";
    
    /** Starts an external application which is associated with the given file. */
    public static void startExternalApplication(String filename) {
        if (getShortOSID().equals(FLGPlatformSpecifics.WIN)) {
            startExternalApplicationWIN(filename);
        }
        else if (getShortOSID().equals(FLGPlatformSpecifics.MACOS_X)) {
            startExternalApplicationWIN(filename);
        }
        else {
            FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
            myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_OSNOTSUPPORTED", "FLG_TITLE_ERROR",
            JOptionPane.ERROR_MESSAGE);
            System.out.println("OS not supported!");
        }
    }
    
    /** Starts an external application which is associated with the given file. */
    public static void startExternalApplication(String filename, String paramLine) {
        if (getShortOSID().equals(FLGPlatformSpecifics.WIN)) {
            startExternalApplicationWIN(filename, paramLine);
        }
        else if (getShortOSID().equals(FLGPlatformSpecifics.MACOS_X) || getShortOSID().equals(FLGPlatformSpecifics.LINUX)) {
            startExternalApplicationLinux(filename, paramLine);
        }
        else {
            FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
            myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_OSNOTSUPPORTED", "FLG_TITLE_ERROR",
            JOptionPane.ERROR_MESSAGE);
            System.out.println("OS not supported!");
        }
    }
    
    /** Returns the application path to an extension or <code>null</code>. */
    public static String getApplicationPathToExtension(String extension) {
        String applicationPath = null;
        if (getShortOSID().equals(FLGPlatformSpecifics.WIN)) {
            applicationPath = getApplicationPathToExtensionWIN(extension);
        }
        return applicationPath;
    }
    
    private static void startExternalApplicationLinux(String commandLine, String paramLine) {
        if (paramLine == null) paramLine = "";
        String extension = FLGFileUtility.getExtension(commandLine);
        if (extension.equalsIgnoreCase(JAR)) {
            int i = commandLine.length() - 1;
            while (!commandLine.substring(i, i + 1).equals(System.getProperty("file.separator"))) {
                i--;
            }
            String dir = commandLine.substring(0, i);
            String fullCmd = "java -jar " + commandLine + " " + paramLine;
            new Thread(new FLGPlatformSpecifics_Runnable(fullCmd, "\"" + dir + "\"")).start();
        }        
        else {
            FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
            myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_OSNOTSUPPORTED", "FLG_TITLE_ERROR", JOptionPane.ERROR_MESSAGE);            
        }
    }
    
    private static void startExternalApplicationWIN(String commandLine, String paramLine) {
        if (paramLine == null) paramLine = "";
        String extension = FLGFileUtility.getExtension(commandLine);
        if (commandLine.startsWith("http://")) {
            extension = "html";
        }
        else {
            paramLine = "\"" + paramLine + "\"";
        }
        if (extension == null) {
            System.out.println("invalid extension!");
            return;
        }
        if (extension.equalsIgnoreCase(EXE)) {
            if (isWin95Compatible()) {
                try {
                    new Thread(
                    new FLGPlatformSpecifics_Runnable("command.com /c " + commandLine + " " + paramLine, "")).start();
                }
                catch (Exception e) {
                    System.out.println("Exception: " + e);
                }
            }
            else if (isWinXPCompatible()) {
                try {
                    new Thread(
                    new FLGPlatformSpecifics_Runnable("cmd.exe /c " + "\"" + commandLine + " " + paramLine + "\"", "")).start();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            else {
                FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
                myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_EXE", "FLG_TITLE_ERROR",
                JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (extension.equalsIgnoreCase(CLASS)) {
            // Installationsverzeichnis der Java-Runtime holen
            String jdkDir = System.getProperty("java.home");
            // und ins bin Vezeichnis wechsel und java starten
            jdkDir = jdkDir + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "java.exe"; //
            // CommandLine in Parameter zerlegen und ".class" abschneiden
            commandLine = commandLine.substring(0, commandLine.length() - 6);
            int i = commandLine.length() - 1;
            while (!commandLine.substring(i, i + 1).equals(System.getProperty("file.separator"))) {
                i--;
            }
            String dir = commandLine.substring(0, i); // NL
            commandLine = commandLine.substring(i + 1, commandLine.length());
            String fullCmd = "";
            if (isWin95Compatible()) {
                fullCmd = "command.com /c " + jdkDir + " -cp \"" + dir + "\" -Duser.dir=\"" + dir + "\" " + "\"" + commandLine + "\"" + paramLine;
            }
            else if (isWinXPCompatible()) {
                fullCmd = "cmd.exe /c " + jdkDir + " -cp \"" + dir + "\" -Duser.dir=\"" + dir + "\" " + "\"" + commandLine + "\"" + paramLine;
            }
            else {
                FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
                myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_CLASS", "FLG_TITLE_ERROR",
                JOptionPane.ERROR_MESSAGE);
            }
            new Thread(new FLGPlatformSpecifics_Runnable(fullCmd, "\"" + dir + "\"")).start();
        }
        
        else if (extension.equalsIgnoreCase(JAR)) {
            // Installationsverzeichnis der Java-Runtime holen
            String jdkDir = System.getProperty("java.home");
            // und ins bin Vezeichnis wechsel und java starten
            jdkDir = jdkDir + System.getProperty("file.separator") + "bin" + System.getProperty("file.separator") + "java.exe"; //
            int i = commandLine.length() - 1;
            while (!commandLine.substring(i, i + 1).equals(System.getProperty("file.separator"))) {
                i--;
            }
            String dir = commandLine.substring(0, i);
            String fullCmd = "";
            if (isWin95Compatible()) {
                fullCmd = "command.com /c " + jdkDir + " -jar " + "\"" + commandLine + " \" " + paramLine;
            }
            else if (isWinXPCompatible()) {
                fullCmd = "cmd.exe /c " + jdkDir + " -jar " + "\"" + commandLine + " \" " + paramLine;
            }
            else {
                FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
                myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_CLASS", "FLG_TITLE_ERROR",
                    JOptionPane.ERROR_MESSAGE);
            }
            System.out.println(fullCmd);
            new Thread(new FLGPlatformSpecifics_Runnable(fullCmd, "\"" + dir + "\"")).start();
        }        
        else {
            // any other file type (pdf, xls, doc, html, ...)
            String appPath = getApplicationPathToExtension(extension);
            if (appPath != null) {
            	
                appPath = "\"" + appPath + "\"";
                if (isWin95Compatible()) {
                    commandLine = appPath + " \"" + commandLine + " " + paramLine + "\"";
                    try {
                        new Thread(new FLGPlatformSpecifics_Runnable("command.com /c " + commandLine, "")).start();
                    }
                    catch (Exception e) { e.printStackTrace(); }
                } else if (isWinXPCompatible()) {
                	if (extension.equals("pps")) {
                        commandLine = "\"" + appPath + " /s \"" + commandLine + " " + paramLine + "\"" + "\"";
                	} else {
                		if (extension.equals("html")) {
                			commandLine = appPath + commandLine;
                	  	} else { 
                	  		commandLine = "\"" + appPath + " \"" + commandLine + " " + paramLine + "\"" + "\"";
                	  	}
                	}
                	try {
                        new Thread(new FLGPlatformSpecifics_Runnable("cmd.exe /c " + commandLine, "")).start();
                    }
                    catch (Exception e) { e.printStackTrace(); }
                } else {
                    FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
                    myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_OTHERAPP", "FLG_TITLE_ERROR",
                    JOptionPane.ERROR_MESSAGE);
                }
            } else {
                FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
                myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_NOEXTAPP", "FLG_TITLE_ERROR",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }   

    public static void run(Class aClass, String[] args) {
            try {
                aClass.getDeclaredMethod("main", new Class[]{String[].class}).invoke(null, new Object[]{args});
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
    }

    private static boolean isWin95Compatible() {
    	String os_name = System.getProperty("os.name");
    	return os_name.equalsIgnoreCase("Windows 98") || os_name.equalsIgnoreCase("Windows 95") || os_name.equalsIgnoreCase("Windows ME");
    }
    
    private static boolean isWinXPCompatible() {
    	String os_name = System.getProperty("os.name");
    	return (os_name.contains("Windows") ||  os_name.contains("windows")) && !isWin95Compatible();
    }

    /**
     * This extension can be used to separate language propeties in a property file.
     * When asking for the property the key has to be build by combining the prefix with the
     * platform specific id. <p> Example: <code>internationalization.getString("dialog.button.reset." +
     * FSL_PlatformSpecifics.getOSID()));</code>
     */
    public static String getShortOSID() {
        String os_name = System.getProperty("os.name");
        if (os_name.equalsIgnoreCase("Windows 2000") || os_name.equalsIgnoreCase("Windows NT") ||
        os_name.equalsIgnoreCase("Windows XP") || os_name.equalsIgnoreCase("Windows 98") ||
        os_name.equalsIgnoreCase("Windows 95") || os_name.contains("Windows") ||  os_name.contains("windows"))
            return FLGPlatformSpecifics.WIN;
        return os_name;
    }
    
    /** Returns the application path on Windows OS */
    private static String getApplicationPathToExtensionWIN(String extension) {
        String applicationPathWIN;
        FLGWin32RegKey regKey = new FLGWin32RegKey(FLGWin32RegKey.HKEY_CLASSES_ROOT, "." + extension);
        try {
            String applicationName = (String)regKey.getValue("");
            if(extension.equals("html")) {
            	applicationPathWIN = "rundll32 url.dll, FileProtocolHandler ";
            } 
            else {
           		regKey = new FLGWin32RegKey(FLGWin32RegKey.HKEY_CLASSES_ROOT, applicationName + "\\shell\\open\\command");
           		applicationPathWIN = (String)regKey.getValue("");
           		int endQuotationmarkIndex = applicationPathWIN.indexOf("\"", 1);
           		applicationPathWIN = applicationPathWIN.substring(1, endQuotationmarkIndex);
           	}
        }
        catch (Exception e) {
            applicationPathWIN = null;
        }
        return applicationPathWIN;
    }
    
    /**
     * Starts an external application on Windows OS by first determining the application type and
     * invoking OS specific run command
     */
    private static void startExternalApplicationWIN(String commandLine) {
        startExternalApplication(commandLine, "");
    }
    
    /** The <code>cmdLine</<code> is executed by the dos-shell. */
    public static void runCommand(String cmdLine) {
        if (getShortOSID().equals(FLGPlatformSpecifics.WIN)) {
            runCommandWIN(cmdLine);
        }
        else {
            System.out.println("OS not supported!");
        }
    }
    
    private static void runCommandWIN(String commandLine) {
        if (isWin95Compatible()) {
            try {
                new Thread(new FLGPlatformSpecifics_Runnable("command.com /c " + commandLine, "")).start();
            }
            catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }
        else if (isWinXPCompatible()) {
            try {
                String commandString = "cmd.exe /c " + "\"" + commandLine + "\"";
                new Thread(new FLGPlatformSpecifics_Runnable(commandLine, "")).start();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            FLGPlatformSpecifics_UserDialog myJOptionPane = new FLGPlatformSpecifics_UserDialog();
            myJOptionPane.showInternatilizedMessageDialog(null, "MESSAGE_ERROR_RUNNING_OSNOTSUPPORTED", "FLG_TITLE_ERROR",
            JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /** This class starts a shell in a new process. */
    private static class FLGPlatformSpecifics_Runnable implements Runnable {
        private String fullCmd, homeDirectory;
        
        public FLGPlatformSpecifics_Runnable(String fullCmd, String homeDirectory) {
            this.fullCmd = fullCmd;
            this.homeDirectory = homeDirectory;
        }
        
        public void run() {
            try {
                Runtime.getRuntime().exec(fullCmd).waitFor();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    
    /**
     * This class provides a internationilzed Version of <code>JOptionPane</code>. In the
     * directory of this class <b>MUST</b> exist a file called <code> internationalization.properties</code>
     * or for different languages for example <code>internationalization_de.properties</code>.
     */
    private static class FLGPlatformSpecifics_UserDialog extends JOptionPane {
        public void showInternatilizedMessageDialog(Component parentComponent, String message, String title, int messageType) {
            try {
                FLGInternationalization internationalization =
                    new FLGInternationalization("freestyleLearningGroup.independent.util.internationalization",
                    FLGPlatformSpecifics.class.getClassLoader());
                JOptionPane.showMessageDialog(parentComponent, 
                    internationalization.getString(message) + "\n" + internationalization.getString("MESSAGE_HINT_MANUALLY"),
                    internationalization.getString(title), messageType);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        String test = "HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Java Web Start";
        FLGWin32RegKey regKey = new FLGWin32RegKey(FLGWin32RegKey.HKEY_LOCAL_MACHINE, "SOFTWARE\\JavaSoft\\Java Web Start");
        System.out.println(regKey.getValue("CurrentVersion"));
    }
    
}
