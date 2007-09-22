package org.openuss.framework.jsfcontrols.tags.security;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.facelets.tag.AbstractTagLibrary;

public class JsfSecurityLibrary extends AbstractTagLibrary {
	
    /**
     *  Namespace used to import this library in Facelets pages.
     **/
    
	public static final String NAMESPACE = "http://www.openuss.org/jsf-security";

    /**
     * Current instance of library. 
     **/
    public static final JsfSecurityLibrary INSTANCE = new JsfSecurityLibrary();

    /**
     * Creates a new JstlSecurityLibrary object.
     */
    public JsfSecurityLibrary() {
        super(NAMESPACE);
        
        this.addTagHandler("authorize", AuthorizeHandler.class);
        this.addTagHandler("acl", AclHandler.class);
        
        try {
            Method[] methods = JsfSecurityFunctions.class.getMethods();

            for (int i = 0; i < methods.length; i++) {
                if (Modifier.isStatic(methods[i].getModifiers())) {
                    this.addFunction(methods[i].getName(), methods[i]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
