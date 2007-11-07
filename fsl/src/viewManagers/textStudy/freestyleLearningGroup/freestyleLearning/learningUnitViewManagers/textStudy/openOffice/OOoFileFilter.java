package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.openOffice;


import java.io.File;

import javax.swing.filechooser.FileFilter;


/**
 * The file filter for OOoImporter FileChooser.
 *
 * @author Edith Schewe
 * @see freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.elementInteractionPanel.FLGTextStudyElementInteractionPanel#startOOoImport()
 */
public class OOoFileFilter extends FileFilter {
    private String description;
    private String acceptedExtension;

    /**
     * creates a SXWFileFilter
     *
     * @param description Textual description of this Filter
     * @param acceptedExtension file extension to accept
     */
    public OOoFileFilter(String description, String acceptedExtension) {
        super();
        this.description = description;
        this.acceptedExtension = acceptedExtension;
    }

    /**
     * Get the extension of a file.
     *
     * @param f The file
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if ((i > 0) && (i < (s.length() - 1))) {
            ext = s.substring(i + 1).toLowerCase();
        }

        return ext;
    }

    /**
     * Checks if this File should be accepted. <BR>
     * All accepted files and directories are displayed in the FileDialog.
     *
     * @param f File to be checked
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);

        if (extension != null) {
            if (extension.equals(acceptedExtension)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * The description of this filter
     */
    public String getDescription() {
        return description;
    }
}
