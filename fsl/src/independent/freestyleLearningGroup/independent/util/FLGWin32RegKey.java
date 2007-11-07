package freestyleLearningGroup.independent.util;

import java.util.Enumeration;

public class FLGWin32RegKey {
    private int root;
    private String path;
    public static final int HKEY_CLASSES_ROOT = 0x80000000;
    public static final int HKEY_CURRENT_USER = 0x80000001;
    public static final int HKEY_LOCAL_MACHINE = 0x80000002;
    public static final int HKEY_USERS = 0x80000003;
    public static final int HKEY_CURRENT_CONFIG = 0x80000005;
    public static final int HKEY_DYN_DATA = 0x80000006;

    public FLGWin32RegKey(int theRoot, String thePath) {
        System.loadLibrary("Win32RegKey");
        root = theRoot;
        path = thePath;
    }

    public Enumeration names() {
        return new FLGWin32RegKeyNameEnumeration(root, path);
    }

    public native Object getValue(String name);

    public native void setValue(String name, Object value);

    public String toString() {
        return "FLGWin32RegKey:\n\tRoot: " + root + "\n\tPath: " + path;
    }
}
