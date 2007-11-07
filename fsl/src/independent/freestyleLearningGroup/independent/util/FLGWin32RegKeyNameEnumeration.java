package freestyleLearningGroup.independent.util;

import java.util.Enumeration;

public class FLGWin32RegKeyNameEnumeration implements Enumeration {
    private int root;
    private String path;
    private int index = -1;
    private int hkey = 0;
    private int maxsize;
    private int count;

    FLGWin32RegKeyNameEnumeration(int theRoot, String thePath) {
        root = theRoot;
        path = thePath;
    }

    public native Object nextElement();

    public native boolean hasMoreElements();
}
