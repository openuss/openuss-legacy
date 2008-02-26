package freestyleLearning.homeCore.learningUnitsManager.dialogs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.document.DateField;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/** A utility for making Lucene Documents from a File. */
public class FSLFileDocument {
    /**
     * Makes a document for a File. <p> The document has three fields: <ul>
     * <li><code>path</code>--containing the pathname of the file, as a stored, tokenized field;
     * <li><code>modified</code>--containing the last modified date of the file as a keyword field as encoded by <a
     * href="lucene.document.DateField.html">DateField</a>; and <li><code>contents</code>--containing the full contents of the
     * file, as a Reader field;
     */
    public static Document Document(File f) throws java.io.FileNotFoundException {
        // make a new, empty document
        Document doc = new Document();
        // Add the path of the file as a field named "path".  Use a Text field, so
        // that the index stores the path, and so that the path is searchable
        doc.add(Field.Text("path", f.getPath()));
        // Add the last modified date of the file a field named "modified".  Use a
        // Keyword field, so that it's searchable, but so that no attempt is made
        // to tokenize the field into words.
        doc.add(Field.Keyword("modified", DateField.timeToString(f.lastModified())));
        // Add the contents of the file a field named "contents".  Use a Text
        // field, specifying a Reader, so that the text of the file is tokenized.
        // ?? why doesn't FileReader work here ??
        FileInputStream is = new FileInputStream(f);
        Reader reader = new BufferedReader(new InputStreamReader(is));
        doc.add(Field.Text("contents", reader));
        // return the document
        return doc;
    }

    private FSLFileDocument() { }
}
