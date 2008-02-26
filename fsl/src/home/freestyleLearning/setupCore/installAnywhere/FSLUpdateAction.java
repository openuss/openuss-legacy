/* Generated by Freestyle Learning Group */

package freestyleLearning.setupCore.installAnywhere;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateIdentifierException;
import javax.xml.bind.InvalidAttributeException;

import com.zerog.ia.api.pub.CustomCodeAction;
import com.zerog.ia.api.pub.InstallException;
import com.zerog.ia.api.pub.InstallerProxy;
import com.zerog.ia.api.pub.UninstallerProxy;

import freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding.LearningUnitDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBinding.LearningUnitsDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitDescriptor;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitsDescriptor;
import freestyleLearning.homeCore.programConfigurationManager.xmlDocumentClasses.Paths;
import freestyleLearning.homeCore.programConfigurationManager.xmlDocumentClasses.ProgramConfiguration;
import freestyleLearning.homeCore.usersManager.data.xmlBinding.UserDescriptor;
import freestyleLearning.homeCore.usersManager.data.xmlBinding.UsersDescriptor;
import freestyleLearning.homeCore.usersManager.data.xmlBindingSubclasses.FSLUserDescriptor;
import freestyleLearning.homeCore.usersManager.data.xmlBindingSubclasses.FSLUsersDescriptor;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGFileUtility;
import freestyleLearningGroup.independent.util.FLGInternationalization;
import freestyleLearningGroup.independent.util.FLGUtilities;

public class FSLUpdateAction extends CustomCodeAction {
    private FLGInternationalization internationalization;
    private String luInstallDirName = "learningUnits";
    private String descriptorString = "learningUnitsDescriptor.xml";
    private String programConfigurationString = "programConfiguration.xml";
    private String fileSeparator;
    private String userInstallDir;

    public FSLUpdateAction() {
        internationalization = new FLGInternationalization("freestyleLearning.setupCore.installAnywhere.internationalization",
            getClass().getClassLoader());
    }

    /**
     * This is the method that is called at install-time.  The InstallerProxy
     * instance provides methods to access information in the installer, set status, and control flow.
     */
    public void install(InstallerProxy ip) throws InstallException {
        /* check if older version exists and import previous
         * - Learning Units
         * - Learning Unit View Managers
         * - User Data
         */

        try {
            userInstallDir = ip.substitute("$USER_INSTALL_DIR$");
            fileSeparator = ip.substitute("$prop.file.separator$");
            File oldVersionDataDirectory = new File(userInstallDir + fileSeparator + "data");
            File oldVersionProgramDirectory = new File(userInstallDir + fileSeparator + "program");
            if (!oldVersionProgramDirectory.exists()) {
                oldVersionProgramDirectory = new File(userInstallDir + fileSeparator + "prog");
            }
            File oldProgramConfigurationFile = new File(oldVersionProgramDirectory.getAbsolutePath() + fileSeparator +
                programConfigurationString);
            if (oldProgramConfigurationFile.exists()) {
                if (FLGOptionPane.showConfirmDialog(internationalization.getString("message.label.dataFound") + "\n\n" +
                    internationalization.getString("message.label.doImport"), internationalization.getString("message.title.dataFound"),
                    FLGOptionPane.YES_NO_OPTION, FLGOptionPane.QUESTION_MESSAGE) == FLGOptionPane.YES_OPTION) {
                        File newProgramConfigurationFile =
                            new File(userInstallDir + fileSeparator + programConfigurationString);
                        ProgramConfiguration newProgramConfiguration =
                            loadProgramConfigurationFile(newProgramConfigurationFile);
                        Paths newPaths = newProgramConfiguration.getPaths();
                        String newLearningUnitsDirectoryName = newPaths.getFullPathToLearningUnitsDirectory();
                        String newLearningUnitViewManagerDirectoryName =
                            newPaths.getFullPathToLearningUnitViewManagersDirectory();
                        String newUsersDirectoryName = newPaths.getFullPathToUsersDirectory();
                        if (oldProgramConfigurationFile.exists()) {
                            ProgramConfiguration oldProgramConfiguration =
                                loadProgramConfigurationFile(oldProgramConfigurationFile);
                            Paths oldPaths = oldProgramConfiguration.getPaths();
                            String oldLearningUnitsDirectoryName = oldPaths.getFullPathToLearningUnitsDirectory();
                            String oldLearningUnitViewManagerDirectorynName =
                                oldPaths.getFullPathToLearningUnitViewManagersDirectory();
                            String oldUsersDirectoryName = oldPaths.getFullPathToUsersDirectory();
                            // Import (Link to) existing LearningUnits
                            File newUnitsDescriptorFile = new
                                File(userInstallDir + fileSeparator + luInstallDirName + fileSeparator + descriptorString);
                            FSLLearningUnitsDescriptor newUnitsDescriptor =
                                loadLearningUnitsDescriptor(newUnitsDescriptorFile);
                            String luString = "lu";
                            File oldUnitsDescriptorFile = new
                                File(userInstallDir + fileSeparator + "data" + fileSeparator + "lu" +
                                fileSeparator + descriptorString);
                            if (!oldUnitsDescriptorFile.exists()) {
                                oldUnitsDescriptorFile = new File(userInstallDir + fileSeparator + "data" + fileSeparator +
                                    "learningUnits" + fileSeparator + descriptorString);
                                luString = "learningUnits";
                            }
                            FSLLearningUnitsDescriptor oldUnitsDescriptor =
                                loadLearningUnitsDescriptor(oldUnitsDescriptorFile);
                            java.util.List oldDescriptors = oldUnitsDescriptor.getLearningUnitsDescriptors();
                            java.util.List newDescriptors = newUnitsDescriptor.getLearningUnitsDescriptors();
                            for (int i = 0; i < oldDescriptors.size(); i++) {
                                FSLLearningUnitDescriptor learningUnitDescriptor =
                                    (FSLLearningUnitDescriptor)oldDescriptors.get(i);
                                String learningUnitPath;
                                if (isDirectoryName(learningUnitDescriptor.getPath()) ||
                                    isRelativePath(learningUnitDescriptor.getPath())) {
                                        learningUnitPath = userInstallDir + fileSeparator + "data" + fileSeparator + luString +
                                            fileSeparator + getLearningUnitDirectoryName(learningUnitDescriptor.getPath());
                                }
                                else {
                                    learningUnitPath = (new File(learningUnitDescriptor.getPath())).getAbsolutePath();
                                }
                                try {
                                    // add descriptor entry
                                    learningUnitDescriptor.setPath(learningUnitPath);
                                    newUnitsDescriptor.getLearningUnitsDescriptors().add(learningUnitDescriptor);
                                    saveLearningUnitsDescriptor(newUnitsDescriptor, newUnitsDescriptorFile);
                                }
                                catch (DuplicateIdentifierException e) {
                                    System.out.println(e);
                                    JOptionPane.showMessageDialog(
                                        new JLabel(internationalization.getString("message.title.dublicateUnit")),
                                        internationalization.getString("message.label.dublicateUnit1") + "\n\n" +
                                        learningUnitDescriptor.getTitle() + "\n\n" +
                                        internationalization.getString("message.label.dublicateUnit2"),
                                        internationalization.getString("message.title.dublicateUnit"),
                                        JOptionPane.ERROR_MESSAGE);
                                    newUnitsDescriptor.getLearningUnitsDescriptors().remove(learningUnitDescriptor);
                                    try {
                                        saveLearningUnitsDescriptor(newUnitsDescriptor, newUnitsDescriptorFile);
                                    }
                                    catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                            }
                            // import user data
                            // - users
                            // - user data
                            File oldUsersDirectory;
                            if (isRelativePath(oldUsersDirectoryName)) {
                                oldUsersDirectory = new File(userInstallDir + fileSeparator + "program" + fileSeparator +
                                    oldUsersDirectoryName);
                                if (!oldUsersDirectory.exists())
                                    oldUsersDirectory = new File(userInstallDir + fileSeparator + "program" +
                                        fileSeparator + "user data");
                            }
                            else {
                                oldUsersDirectory = new File(oldUsersDirectoryName);
                            }
                            File oldUsersDescriptorFile = new
                                File(oldUsersDirectory.getAbsolutePath() + fileSeparator + "usersDescriptor.xml");
                            FSLUsersDescriptor oldUsersDescriptor = loadUsersDescriptor(oldUsersDescriptorFile);
                            File newUsersDirectory = new File(userInstallDir + fileSeparator + "users");
                            File newUsersDescriptorFile = new
                                File(newUsersDirectory.getAbsolutePath() + fileSeparator + "usersDescriptor.xml");
                            FSLUsersDescriptor newUsersDescriptor = loadUsersDescriptor(newUsersDescriptorFile);
                            // read old users
                            java.util.List oldUsersList = oldUsersDescriptor.getUsersDescriptors();
                            java.util.List newUsersList = newUsersDescriptor.getUsersDescriptors();
                            for (int i = 0; i < oldUsersList.size(); i++) {
                                FSLUserDescriptor oldUser = (FSLUserDescriptor)oldUsersList.get(i);
                                if (contains(newUsersDescriptor, oldUser)) {
                                    remove(newUsersDescriptor, oldUser);
                                }
                                newUsersList.add(oldUser);
                            }
                            saveUsersDescriptor(newUsersDescriptor, newUsersDescriptorFile);
                            // copy user data
                            File[] oldUsersDirectoryContent = oldUsersDirectory.listFiles();
                            for (int i = 0; i < oldUsersDirectoryContent.length; i++) {
                                if (oldUsersDirectoryContent[i].isDirectory()) {
                                    String directoryName = oldUsersDirectoryContent[i].getName();
                                    File targetDirectory = new File(newUsersDirectory + fileSeparator + directoryName);
                                    targetDirectory.mkdirs();
                                    System.out.println("copying " + oldUsersDirectoryContent[i].getName());
                                    FLGFileUtility.copyFileStructure(oldUsersDirectoryContent[i], targetDirectory, true);
                                }
                            }
                        }
                }
                else {
                    System.out.println("no");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void remove(FSLUsersDescriptor usersDescriptor, FSLUserDescriptor user) {
        for (int i = 0; i < usersDescriptor.getUsersDescriptors().size(); i++) {
            FSLUserDescriptor currentUser = (FSLUserDescriptor)usersDescriptor.getUsersDescriptors().get(i);
            if (currentUser.getId().equals(user.getId())) usersDescriptor.getUsersDescriptors().remove(currentUser);
        }
    }

    private boolean contains(FSLUsersDescriptor usersDescriptor, FSLUserDescriptor user) {
        for (int i = 0; i < usersDescriptor.getUsersDescriptors().size(); i++) {
            FSLUserDescriptor currentUser = (FSLUserDescriptor)usersDescriptor.getUsersDescriptors().get(i);
            if (currentUser.getId().equals(user.getId())) return true;
        }
        return false;
    }

    private boolean isDirectoryName(String path) {
        int separatorIndex = path.indexOf(fileSeparator);
        if (separatorIndex < 0)
            separatorIndex = path.indexOf("/");
        return (separatorIndex < 0);
    }

    private boolean isRelativePath(String path) {
        int relativePathStartsAt = path.indexOf(".");
        if (relativePathStartsAt >= 0 && relativePathStartsAt < 2) return true;
        return false;
    }

    private String getLearningUnitDirectoryName(String learningUnitPath) {
        int lastSeparatorIndex = learningUnitPath.lastIndexOf(fileSeparator);
        if (lastSeparatorIndex < 0) lastSeparatorIndex = learningUnitPath.lastIndexOf("/");
        return learningUnitPath.substring(lastSeparatorIndex + 1, learningUnitPath.length());
    }

    private ProgramConfiguration loadProgramConfigurationFile(File programConfigurationFile) {
        ProgramConfiguration programConfiguration = null;
        FileInputStream programConfigurationFileInputStream;
        try {
            programConfigurationFileInputStream = new FileInputStream(programConfigurationFile);
            programConfiguration = ProgramConfiguration.unmarshal(programConfigurationFileInputStream);
            programConfigurationFileInputStream.close();
        }
        catch (Exception e) {
            // invalid file
            System.out.println("***error loading configuration ***");
            JOptionPane.showMessageDialog(null, "Error Loading Configuration File:\n" + e, "Error loading Configuration File!",
                JOptionPane.INFORMATION_MESSAGE);
        }
        return programConfiguration;
    }

    private FSLLearningUnitsDescriptor loadLearningUnitsDescriptor(File learningUnitsDescriptorFile) {
        try {
            FSLLearningUnitsDescriptor learningUnitsDescriptor = null;
            Dispatcher dispatcher = LearningUnitsDescriptor.newDispatcher();
            dispatcher.register(LearningUnitsDescriptor.class, FSLLearningUnitsDescriptor.class);
            dispatcher.register(LearningUnitDescriptor.class, FSLLearningUnitDescriptor.class);
            FileInputStream learningUnitsDescriptorFileInputStream = null;
            learningUnitsDescriptorFileInputStream = new FileInputStream(learningUnitsDescriptorFile);
            learningUnitsDescriptor = (FSLLearningUnitsDescriptor)dispatcher.unmarshal(learningUnitsDescriptorFileInputStream);
            learningUnitsDescriptorFileInputStream.close();
            return learningUnitsDescriptor;
        }
        catch (InvalidAttributeException iaex) {
            // deprecated attribute?
            System.out.println("***error loading descriptor***\n\t" + iaex);
            if (iaex.getMessage().equals("directoryName")) {
                System.out.println("\tdeprecated version found, trying recovery...");
                try {
                    BufferedReader br = new BufferedReader(new FileReader(learningUnitsDescriptorFile));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (FLGUtilities.contains(line, "directoryName")) {
                            line = FLGUtilities.replaceInString(line, "directoryName", "path");
                        }
                        sb.append(line + "\n");
                    }
                    File convertedDescriptorFile = new File(learningUnitsDescriptorFile.getAbsolutePath() + "_new");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(convertedDescriptorFile));
                    bw.write(new String(sb));
                    bw.flush();
                    bw.close();
                    System.out.println("\tconverting done, trying again...\n");
                    return loadLearningUnitsDescriptor(convertedDescriptorFile);
                }
                catch (IOException ioex) {
                    System.out.println(ioex);
                }
            }
        }
        catch (Exception e) {
            // invalid file
            System.out.println("***error loading descriptor***\n\t" + e);
            JOptionPane.showMessageDialog(null, "Error Loading Descriptor File:\n" + e, "Error loading Descriptor File!",
                JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    private boolean saveLearningUnitsDescriptor(FSLLearningUnitsDescriptor installedUnitsDescriptor, File installedUnitsDescriptorFile)
        throws DuplicateIdentifierException {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(installedUnitsDescriptorFile);
                installedUnitsDescriptor.validate();
                installedUnitsDescriptor.marshal(fileOutputStream);
                fileOutputStream.close();
                return true;
            }
            catch (DuplicateIdentifierException die) {
                throw die;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return false;
    }

    private FSLUsersDescriptor loadUsersDescriptor(File usersDescriptorFile) {
        FSLUsersDescriptor usersDescriptor = null;
        Dispatcher dispatcher = UsersDescriptor.newDispatcher();
        dispatcher.register(UsersDescriptor.class, FSLUsersDescriptor.class);
        dispatcher.register(UserDescriptor.class, FSLUserDescriptor.class);
        FileInputStream usersDescriptorFileInputStream = null;
        try {
            usersDescriptorFileInputStream = new FileInputStream(usersDescriptorFile);
            usersDescriptor = (FSLUsersDescriptor)dispatcher.unmarshal(usersDescriptorFileInputStream);
            usersDescriptorFileInputStream.close();
        }
        catch (Exception e) {
            // invalid file
            System.out.println("***error loading user configuration ***");
            JOptionPane.showMessageDialog(null, "Error loading User Configuration File:\n" + e,
                "Error loading User Configuration File!", JOptionPane.INFORMATION_MESSAGE);
        }
        return usersDescriptor;
    }

    private void saveUsersDescriptor(FSLUsersDescriptor usersDescriptor, File usersDescriptorFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(usersDescriptorFile);
            usersDescriptor.validate();
            usersDescriptor.marshal(fileOutputStream);
            fileOutputStream.close();
        }
        catch (Exception e) {
            // invalid file
            System.out.println("***error saving user configuration ***");
            JOptionPane.showMessageDialog(null, "Error saving User Configuration File:\n" + e,
                "Error saving User Configuration File!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     *  This is the method that is called at uninstall-time.  The DataInput
     * instance provides access to any information written at install-time
     * with the instance of DataOutput provided by UninstallerProxy.getLogOutput().
     */
    public void uninstall(UninstallerProxy up) throws InstallException {
    }

    /** This method will be called to display a status message during the installation. */
    public String getInstallStatusMessage() {
        return internationalization.getString("install.statusMessage");
    }

    /** This method will be called to display a status message during the uninstall. */
    public String getUninstallStatusMessage() {
        return null;
    }
}
