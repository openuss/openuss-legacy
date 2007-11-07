/*
 * FLGNameDocument.java
 *
 * Created on 14. Juni 2005, 16:08
 */

package freestyleLearningGroup.independent.gui.documents;

import java.awt.*;
import javax.swing.text.*;

public class FLGNameDocument extends PlainDocument {
    private boolean spaceAllowed = false;
    
    public FLGNameDocument() {
        // no spaces allowed
    }
    
    public FLGNameDocument(boolean spaceAllowed) {
        this.spaceAllowed = spaceAllowed;
    }
    
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        char[] source = str.toCharArray();
        char[] result = new char[source.length];
        int j = 0;
        for (int i = 0; i < result.length; i++) {
            if (Character.isLetter(source[i]) || Character.isDigit(source[i]) || (source[i] == ' ' && spaceAllowed)) result[j++] = source[i];
            else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
        super.insertString(offs, new String(result, 0, j), a);
    }

    public void setText(String str) {
        if (str != null) {
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;
            for (int i = 0; i < result.length; i++) {
                if (Character.isLetter(source[i]) || Character.isDigit(source[i]) || (source[i] == ' ' && spaceAllowed)) result[j++] = source[i];
            }
            try {
                super.insertString(0, new String(result, 0, j), null);
            }
            catch (BadLocationException ble) { };
        }
    }
}

